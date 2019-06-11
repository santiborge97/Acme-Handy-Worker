
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	//Managed repository---------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	//Suporting services---------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods--------------------------------
	public Application create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Application result;
		result = new Application();
		return result;
	}

	public Application createNew(final FixUpTask fixUpTask) {

		Assert.notNull(fixUpTask);
		final Application result = this.create();
		result.setFixUpTask(fixUpTask);
		final Date now = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(now);
		result.setStatus("PENDING");

		return result;

	}

	public Collection<Application> findAll() {
		Collection<Application> result;
		result = this.applicationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Application findOne(final int applicationId) {
		Application application;
		application = this.applicationRepository.findOne(applicationId);
		Assert.notNull(application);
		return application;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Application app = null;

		if (application.getId() == 0) {
			final HandyWorker actor = (HandyWorker) this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.HANDYWORKER);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

			application.setStatus("PENDING");
			final Date now = new Date(System.currentTimeMillis() - 1000);
			application.setMoment(now);

			app = this.applicationRepository.save(application);

			Collection<Application> applications = actor.getApplications();
			applications.add(app);

			actor.setApplications(applications);

			this.handyWorkerService.save(actor);

			applications = application.getFixUpTask().getApplications();
			applications.add(application);

			final FixUpTask fixUpTask = application.getFixUpTask();
			fixUpTask.setApplications(applications);

			this.fixUpTaskService.save(fixUpTask);

		} else {

			Customer customer = null;
			HandyWorker handyWorker = null;

			final Authority authority1 = new Authority();
			authority1.setAuthority(Authority.CUSTOMER);
			final Authority authority3 = new Authority();
			authority3.setAuthority(Authority.HANDYWORKER);

			if (LoginService.getPrincipal().getAuthorities().contains(authority1))
				customer = this.customerService.findByPrincipal();
			else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
				handyWorker = this.handyWorkerService.findByPrincipal();

			Assert.isTrue(customer != null || handyWorker != null);

			if (customer != null) {

				final Application old = this.findOne(application.getId());

				final Actor actor = this.actorService.findByPrincipal();
				Assert.notNull(actor);
				final Authority authority = new Authority();
				authority.setAuthority(Authority.CUSTOMER);
				Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

				Assert.isTrue(old.getStatus().equals("PENDING"));
				final FixUpTask fixUp = application.getFixUpTask();
				Assert.isTrue(customer.getFixUpTasks().contains(fixUp));

				Collection<Application> c = new ArrayList<>();
				c = application.getFixUpTask().getApplications();
				c.remove(old);
				if (application.getStatus().equals("ACCEPTED")) {

					final Date now = new Date(System.currentTimeMillis() - 1000);

					Assert.isTrue(this.configurationService.creditCardMakesContent(application.getCreditCard().getBrandName()));

					Assert.isTrue(application.getCreditCard().getExpYear() - 1900 >= now.getYear());
					Assert.isTrue(application.getCreditCard().getExpMonth() - 1 >= now.getMonth() || application.getCreditCard().getExpYear() - 1900 > now.getYear());

					for (final Application application2 : c) {

						application2.setStatus("REJECTED");
						this.applicationRepository.save(application2);
					}

					app = this.applicationRepository.save(application);

				} else if (application.getCommentReject() != null)

					app = this.applicationRepository.save(application);

			}
		}
		return app;
	}
	//Other business methods----------------------------
	public Collection<Double> statsOfOfferedPricePerApplication() {

		final Collection<Double> result = this.applicationRepository.statsOfOfferedPricePerApplication();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPending() {

		final Double result = this.applicationRepository.ratioOfApplicationsPending();
		//		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsAccepted() {
		final Double result = this.applicationRepository.ratioOfApplicationsAccepted();
		//		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsRejected() {

		final Double result = this.applicationRepository.ratioOfApplicationsRejected();
		//		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPendingElapsedPeriod() {

		final Double result = this.applicationRepository.ratioOfApplicationsPendingElapsedPeriod();
		//		Assert.notNull(result);
		return result;
	}
	//Devuelve las applications dado un Customer
	public Collection<Application> findApplicationsByCustomer(final int customerId) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByCustomer(customerId);
		Assert.notNull(result);
		return result;

	}
	//Devuelve las applications dado un HandyWorker
	public Collection<Application> findApplicationsByHandyWorker(final int handyWorkerId) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByHandyWorker(handyWorkerId);
		Assert.notNull(result);
		return result;

	}

	public Application findApplicationByFixUpTaskId(final int fixUpTaskId) {
		final Application result = this.applicationRepository.findApplicationByFixUpTaskId(fixUpTaskId);
		return result;
	}

	public Collection<Application> findAcceptedApplications() {
		final Collection<Application> result = this.applicationRepository.findAcceptedApplications(this.handyWorkerService.findByPrincipal().getId());

		Assert.notNull(result);

		return result;
	}

	public Boolean ApplicationSecurity(final int applicationId) {
		Boolean res = false;

		final int fixUpTaskId = this.applicationRepository.findOne(applicationId).getFixUpTask().getId();

		final Customer owner = this.customerService.findByTaskId(fixUpTaskId);
		final HandyWorker owner2 = this.handyWorkerService.findHandyWorkerByApplicationId(applicationId);

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner) || login.equals(owner2))
			res = true;

		return res;
	}

	public Boolean countApplicationAcceptedByFixUpTask(final int fixUpTaskId) {
		Boolean res = false;
		final int applicationAccepted = this.applicationRepository.countApplicationAcceptedByFixUpTask(fixUpTaskId);

		if (applicationAccepted == 0)
			res = true;

		return res;
	}
}
