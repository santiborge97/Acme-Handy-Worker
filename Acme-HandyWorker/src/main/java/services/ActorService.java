
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	//Managed Repository ---------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	//Supporting services --------------------------------------------------
	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods --------------------------------------------------

	public Collection<Actor> findAll() {

		final Collection<Actor> actors = this.actorRepository.findAll();

		Assert.notNull(actors);

		return actors;
	}

	public Actor findOne(final int ActorId) {

		final Actor actor = this.actorRepository.findOne(ActorId);

		Assert.notNull(actor);

		return actor;
	}

	public Actor save(final Actor a) {

		final Actor actor = this.actorRepository.save(a);

		Assert.notNull(actor);

		return actor;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		this.actorRepository.delete(actor);
	}

	//Other business methods----------------------------

	public Actor findByPrincipal() {
		Actor a;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		a = this.findByUserAccount(userAccount);
		Assert.notNull(a);

		return a;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);
		UserAccount result;
		result = this.userAccountService.findByActor(actor);
		return result;
	}

	public Actor editPersonalData(final Actor actor) {
		Assert.notNull(actor);
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(userAccount));
		final Actor result = this.save(actor);

		return result;
	}

	public void sendMessage(final Message message) {
		Assert.notNull(message);

		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		Assert.isTrue(message.getSender().getUserAccount().equals(userAccount));

		message.setMoment(new Date());

		final Box inBoxReceiver = this.boxService.findInBoxByActorId(message.getRecipient().getId());
		final Box outBoxSender = this.boxService.findInBoxByActorId(message.getSender().getId());
		final Collection<Box> c = new ArrayList<Box>(message.getBoxes());
		c.add(outBoxSender);
		c.add(inBoxReceiver);
		message.setBoxes(c);

	}

	public Collection<Actor> suspiciousActors() {

		final Actor actor = this.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Collection<Actor> result;

		result = this.actorRepository.suspiciousActors();

		return result;

	}

	public void convertToSuspiciousActor() {
		final Actor actor = this.findByPrincipal();
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final Authority authCustomer = new Authority();
		authCustomer.setAuthority(Authority.CUSTOMER);

		final Authority authHandyWorker = new Authority();
		authHandyWorker.setAuthority(Authority.HANDYWORKER);

		final Authority authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);

		final Authority authSponsor = new Authority();
		authSponsor.setAuthority(Authority.SPONSOR);

		if (authorities.contains(authAdmin)) {
			final Administrator administrator = this.administratorService.findByPrincipal();
			administrator.setSuspicious(true);
			this.administratorService.save(administrator);

		} else if (authorities.contains(authCustomer)) {
			final Customer customer = this.customerService.findByPrincipal();
			customer.setSuspicious(true);
			this.customerService.save(customer);

		} else if (authorities.contains(authHandyWorker)) {
			final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
			handyWorker.setSuspicious(true);
			this.handyWorkerService.save(handyWorker);

		} else if (authorities.contains(authReferee)) {
			final Referee referee = this.refereeService.findByPrincipal();
			referee.setSuspicious(true);
			this.refereeService.save(referee);

		} else if (authorities.contains(authSponsor)) {
			final Sponsor sponsor = this.sponsorService.findByPrincipal();
			sponsor.setSuspicious(true);
			this.sponsorService.save(sponsor);
		}

	}

	public void banOrUnBanActor(final Actor actor) {
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final Authority authCustomer = new Authority();
		authCustomer.setAuthority(Authority.CUSTOMER);

		final Authority authHandyWorker = new Authority();
		authHandyWorker.setAuthority(Authority.HANDYWORKER);

		final Authority authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);

		final Authority authSponsor = new Authority();
		authSponsor.setAuthority(Authority.SPONSOR);

		if (authorities.contains(authAdmin)) {
			final Administrator administrator = this.administratorService.findOne(actor.getId());
			final UserAccount userAccount = administrator.getUserAccount();
			userAccount.setIsNotBanned(!userAccount.getIsNotBanned());
			this.userAccountService.save(userAccount);

		} else if (authorities.contains(authCustomer)) {
			final Customer customer = this.customerService.findOne(actor.getId());
			final UserAccount userAccount = customer.getUserAccount();
			userAccount.setIsNotBanned(!userAccount.getIsNotBanned());
			this.userAccountService.save(userAccount);

		} else if (authorities.contains(authHandyWorker)) {
			final HandyWorker handyWorker = this.handyWorkerService.findOne(actor.getId());
			final UserAccount userAccount = handyWorker.getUserAccount();
			userAccount.setIsNotBanned(!userAccount.getIsNotBanned());
			this.userAccountService.save(userAccount);

		} else if (authorities.contains(authReferee)) {
			final Referee referee = this.refereeService.findOne(actor.getId());
			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setIsNotBanned(!userAccount.getIsNotBanned());
			this.userAccountService.save(userAccount);

		} else if (authorities.contains(authSponsor)) {
			final Sponsor sponsor = this.sponsorService.findOne(actor.getId());
			final UserAccount userAccount = sponsor.getUserAccount();
			userAccount.setIsNotBanned(!userAccount.getIsNotBanned());
			this.userAccountService.save(userAccount);
		}
	}

	public void checkEmail(final String email, final Boolean isAdmin) {

		if (!isAdmin) {
			final boolean checkEmailOthers = email.matches("^[\\w]+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+>$");
			Assert.isTrue(checkEmailOthers);
		} else {
			final boolean checkEmailAdmin = email.matches("^[\\w]+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+){0,1}|(([\\w]\\s)*[\\w])+<\\w+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+){0,1}>$");
			Assert.isTrue(checkEmailAdmin);
		}
	}

	public String checkPhone(final String phone) {
		String res = phone;

		//Esto es para contar el número de dígitos que contiene 
		int count = 0;
		for (int i = 0, len = phone.length(); i < len; i++)
			if (Character.isDigit(phone.charAt(i)))
				count++;

		if (StringUtils.isNumericSpace(phone) && count == 4) {
			res.replaceAll("\\s+", ""); //quitar espacios
			res = this.configurationService.findConfiguration().getCountryCode() + " " + phone;
		}
		return res;

	}
	public String authorityAuthenticated() {
		String res = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);
		final Authority authority4 = new Authority();
		authority3.setAuthority(Authority.ADMIN);
		final Authority authority5 = new Authority();
		authority3.setAuthority(Authority.SPONSOR);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			res = "customer";
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			res = "referee";
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			res = "handyWorker";
		else if (LoginService.getPrincipal().getAuthorities().contains(authority4))
			res = "administrator";
		else if (LoginService.getPrincipal().getAuthorities().contains(authority5))
			res = "sponsor";

		return res;

	}
}
