
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -------------------------------------------

	@Autowired
	ComplaintRepository	complaintRepository;

	// Supporting services ------------------------------------------

	@Autowired
	ActorService		actorService;

	@Autowired
	CustomerService		customerservice;

	@Autowired
	FixUpTaskService	fixUpTaskService;

	@Autowired
	HandyWorkerService	handyWorkerService;


	// Simple CRUD methods ------------------------------------------

	public Boolean ComplaintSecurity(final int complaintId) {
		Boolean res = false;
		HandyWorker owner2 = null;

		final Complaint complaint = this.complaintRepository.findOne(complaintId);

		final Customer owner = this.customerservice.findByComplaintId(complaintId);

		final Collection<Application> application = complaint.getFixUpTask().getApplications();

		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);

		if (actor.getUserAccount().getAuthorities().contains(authority))
			for (final Application application2 : application)
				if (application2.getStatus().equals("ACCEPTED") && this.handyWorkerService.findByPrincipal().getApplications().contains(application2)) {
					owner2 = this.handyWorkerService.findHandyWorkerByApplicationId(application2.getId());
					break;
				}

		final Referee owner3 = complaint.getReferee();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner) || login.equals(owner2) || login.equals(owner3))
			res = true;

		return res;
	}
	public Complaint create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Complaint result;

		result = new Complaint();

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(currentMoment);

		final Collection<String> attachments = new HashSet<>();

		result.setAttachments(attachments);

		return result;

	}

	public Collection<Complaint> findAll() {

		final Collection<Complaint> complaints;

		Assert.notNull(this.complaintRepository);

		complaints = this.complaintRepository.findAll();

		Assert.notNull(complaints);

		return complaints;

	}

	public Complaint findOne(final int complaintId) {

		Assert.isTrue(complaintId != 0);

		Assert.notNull(this.complaintRepository);

		final Complaint result = this.complaintRepository.findOne(complaintId);

		Assert.notNull(result);

		return result;

	}

	public Complaint save(final Complaint complaint) {

		Assert.notNull(complaint);
		Complaint result = null;

		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.CUSTOMER);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority) || actor.getUserAccount().getAuthorities().contains(authority2) || actor.getUserAccount().getAuthorities().contains(authority3));

		if (complaint.getReferee() == null && actor.getUserAccount().getAuthorities().contains(authority)) {
			final Referee referee = (Referee) actor;
			complaint.setReferee(referee);
		}

		result = this.complaintRepository.save(complaint);

		return result;

	}

	public Complaint saveNewComplaint(final Complaint complaint, final int fixUpTaskId) {

		Assert.notNull(complaint);
		Complaint result = null;
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Customer customer = (Customer) actor;

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		complaint.setMoment(currentMoment);

		complaint.setTicker(this.generateTicker(new Date(System.currentTimeMillis())));

		this.checkAttachments(complaint.getAttachments());

		result = this.complaintRepository.save(complaint);

		Collection<Complaint> complaints;
		complaints = customer.getComplaints();
		complaints.add(result);
		customer.setComplaints(complaints);

		Collection<Complaint> complaints2;
		complaints2 = fixUpTask.getComplaints();
		complaints2.add(result);
		fixUpTask.setComplaints(complaints2);

		this.customerservice.save(customer);

		this.fixUpTaskService.save(fixUpTask);

		return result;

	}

	public Collection<Complaint> findAllComplaintsOfCustomer(final int customerId) {
		Collection<Complaint> result;
		result = this.complaintRepository.findAllComplaintsOfCustomer(customerId);
		return result;
	}

	public Collection<Complaint> findAllUnassigned() {
		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Collection<Complaint> result;
		result = this.complaintRepository.findAllUnassigned();
		return result;
	}

	public Collection<Complaint> findAllComplaintsOfReferee(final int refereeId) {
		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Collection<Complaint> result;
		result = this.complaintRepository.findAllComplaintsOfReferee(refereeId);
		return result;
	}

	public Collection<Complaint> findAllComplaintsOfHandyWorker(final int handyWorkerId) {
		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Collection<Complaint> result;
		result = this.complaintRepository.findAllComplaintsOfHandyWorker(handyWorkerId);
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

		return ticker;

	}

	public void checkAttachments(final Collection<String> attachments) {

		for (final String url : attachments) {
			final boolean checkUrl = url.matches("^http(s*)://(?:[a-zA-Z0-9-]+[\\.\\:])+[a-zA-Z0-9/]+$");
			Assert.isTrue(checkUrl);

		}
	}

}
