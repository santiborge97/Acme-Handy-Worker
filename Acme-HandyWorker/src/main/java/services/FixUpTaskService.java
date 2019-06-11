
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Money;
import domain.Phase;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository

	@Autowired
	private FixUpTaskRepository		fixUpTaskRepository;

	// Suporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private PhaseService			phaseService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Simple CRUD methods

	public FixUpTask create() {

		final Customer customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(customer.getUserAccount().getAuthorities().contains(authority));

		final FixUpTask result = new FixUpTask();

		final Collection<Complaint> complaints = new HashSet<>();
		result.setComplaints(complaints);
		final Collection<Application> applications = new ArrayList<Application>();
		result.setApplications(applications);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(currentMoment);

		//ticker autom�tico
		final String ticker = this.generateTicker(currentMoment);
		result.setTicker(ticker);

		final Money maximumPrice = new Money();
		maximumPrice.setCurrency("euros");
		result.setMaximumPrice(maximumPrice);

		return result;

	}

	private String generateTicker(final Date currentMoment) {

		final DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		final String dateString = dateFormat.format(currentMoment);

		final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = dateString + "-" + randomAlphaNumeric;

		final int fixUpSameTicker = this.fixUpTaskRepository.countFixUpTaskWithTicker(ticker);

		//nos aseguramos que que sea �nico
		while (fixUpSameTicker > 0)
			this.generateTicker(currentMoment);

		return ticker;

	}
	public Collection<FixUpTask> findAll() {

		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskRepository.findAll();

		Assert.notNull(fixUpTasks);

		return fixUpTasks;
	}

	public FixUpTask findOne(final int fixUpTaskID) {

		final FixUpTask fixUpTask = this.fixUpTaskRepository.findOne(fixUpTaskID);

		Assert.notNull(fixUpTask);

		return fixUpTask;

	}

	public FixUpTask save(final FixUpTask fixUpTask) {

		Assert.notNull(fixUpTask);

		final FixUpTask result;
		Customer customer = null;
		HandyWorker handyWorker = null;
		Administrator admin = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.HANDYWORKER);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.ADMIN);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			handyWorker = this.handyWorkerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			admin = this.administratorService.findByPrincipal();

		Assert.isTrue(customer != null || handyWorker != null || admin != null);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		final Date startDate = fixUpTask.getStartDate();
		final Date endDate = fixUpTask.getEndDate();
		Assert.isTrue(startDate.compareTo(endDate) <= 0);
		Assert.isTrue(startDate.after(currentMoment));
		if (fixUpTask.getId() != 0) {

			if (customer != null)
				Assert.isTrue(customer.getFixUpTasks().contains(fixUpTask));

			this.configurationService.spamContent(fixUpTask.getDescription());

			result = this.fixUpTaskRepository.save(fixUpTask);

		} else {

			result = this.fixUpTaskRepository.save(fixUpTask);
			final Integer num = customer.getFixUpTasks().size();
			final Collection<FixUpTask> fixUpTasks = customer.getFixUpTasks();
			fixUpTasks.add(result);
			customer.setFixUpTasks(fixUpTasks);
			final Customer customer2 = this.customerService.save(customer);
			final Integer num2 = customer2.getFixUpTasks().size();
			Assert.isTrue(num + 1 == num2);
		}

		return result;

	}
	public void delete(final FixUpTask fixUpTask) {

		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);

		final Actor customer = this.actorService.findByPrincipal();
		Assert.notNull(customer);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(customer.getUserAccount().getAuthorities().contains(authority));
		final Customer c = (Customer) customer;
		Assert.isTrue(c.getFixUpTasks().contains(fixUpTask));

		//Applications (Si tiene applications, no puede borrarse)
		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		final Application a = this.applicationService.findApplicationByFixUpTaskId(fixUpTask.getId());
		Assert.isTrue(a == null);

		//Phase (si tiene phase tiene application, no puede borrarse)
		final Collection<Phase> phasesByFixUp = this.phaseService.findPhasesByFixUpTaskId(fixUpTask.getId());
		Assert.isTrue(phasesByFixUp.isEmpty());

		//Complaints (Si tiene complaints, es una actividad finalizada que tuvo application y phase,
		//no puede borrarse)
		Assert.isTrue(fixUpTask.getComplaints().isEmpty());

		//Finder
		Collection<Finder> findersByFixUp = this.finderService.findFindersByFixUpTaskId(fixUpTask.getId());
		if (!findersByFixUp.isEmpty())
			for (final Finder f : findersByFixUp) {

				f.getFixUpTasks().remove(fixUpTask);
				this.finderService.save(f);
			}
		findersByFixUp = this.finderService.findFindersByFixUpTaskId(fixUpTask.getId());
		Assert.isTrue(findersByFixUp.isEmpty());
		//Customer
		final Collection<FixUpTask> f = c.getFixUpTasks();
		f.remove(fixUpTask);
		c.setFixUpTasks(f);
		this.customerService.save(c);
		Assert.isTrue(!c.getFixUpTasks().contains(fixUpTask));

		this.fixUpTaskRepository.delete(fixUpTask);
	}
	// Other business methods

	public Collection<Double> statsOfApplicationsPerFixUpTask() {

		final Collection<Double> result = this.fixUpTaskRepository.statsOfApplicationsPerFixUpTask();
		Assert.notNull(result);
		return result;

	}

	public Collection<Double> statsOfMaximumPricePerFixUpTask() {

		final Collection<Double> result = this.fixUpTaskRepository.statsOfMaximumPricePerFixUpTask();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsOfComplaintsPerFixUpTask() {

		final Collection<Double> result = this.fixUpTaskRepository.statsOfComplaintsPerFixUpTask();
		Assert.notNull(result);
		return result;

	}

	public Double ratioOfFixUpTasksWithComplaint() {

		final Double result = this.fixUpTaskRepository.ratioOfFixUpTasksWithComplaint();
		//		Assert.notNull(result);
		return result;

	}

	public Collection<FixUpTask> findFixUpTaskPerCategory(final int categoryId) {

		final Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findFixUpTaskPerCategory(categoryId);

		Assert.notNull(result);

		return result;
	}

	public FixUpTask findFixUpTaskPerComplaint(final int complaintId) {

		FixUpTask result;

		result = this.fixUpTaskRepository.findFixUpTaskPerComplaint(complaintId);

		Assert.notNull(result);

		return result;

	}

	public Integer countFixUpTaskByWarrantyId(final int warrantyId) {

		Integer result;

		result = this.fixUpTaskRepository.countFixUpTaskByWarrantyId(warrantyId);

		Assert.notNull(result);

		return result;
	}

	public Collection<FixUpTask> findAcceptedFixUpTasks() {
		final Collection<FixUpTask> result = this.fixUpTaskRepository.findAcceptedFixUpTasks(this.handyWorkerService.findByPrincipal().getId());

		Assert.notNull(result);

		return result;
	}

	public Boolean fixUpTaskCustomerSecurity(final int fixUpTaksId) {
		Boolean res = false;

		final Customer owner = this.customerService.findByTaskId(fixUpTaksId);

		final Customer login = this.customerService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}

}
