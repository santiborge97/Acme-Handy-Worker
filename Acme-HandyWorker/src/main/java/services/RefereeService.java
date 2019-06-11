
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	// Simple CRUD methods ------------------------------------------

	public Referee create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		Referee result;
		result = new Referee();

		final Authority authorityReferee = new Authority();
		authorityReferee.setAuthority(Authority.REFEREE);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authorityReferee);

		final UserAccount userAccount = this.userAccountService.createReferee();
		result.setUserAccount(userAccount);

		result.setSuspicious(false);

		return result;

	}

	public Collection<Referee> findAll() {

		final Collection<Referee> result = this.refereeRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Referee findOne(final int refereeId) {

		final Referee result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);
		return result;

	}

	public Referee save(final Referee referee) {

		Assert.notNull(referee);
		Referee result;

		if (referee.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == referee.getId() || actor.getUserAccount().getAuthorities().contains(admin));

			this.actorService.checkEmail(referee.getEmail(), false);

			final String phone = this.actorService.checkPhone(referee.getPhone());
			referee.setPhone(phone);

			result = this.refereeRepository.save(referee);
		} else {

			String pass = referee.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setPassword(pass);

			referee.setUserAccount(userAccount);

			this.actorService.checkEmail(referee.getEmail(), false);

			final String phone = this.actorService.checkPhone(referee.getPhone());
			referee.setPhone(phone);

			result = this.refereeRepository.save(referee);

			Box inBox, outBox, trashBox, spamBox;

			inBox = this.boxService.create();
			outBox = this.boxService.create();
			trashBox = this.boxService.create();
			spamBox = this.boxService.create();

			inBox.setName("in box");
			outBox.setName("out box");
			trashBox.setName("trash box");
			spamBox.setName("spam box");

			inBox.setByDefault(true);
			outBox.setByDefault(true);
			trashBox.setByDefault(true);
			spamBox.setByDefault(true);

			inBox.setActor(result);
			outBox.setActor(result);
			trashBox.setActor(result);
			spamBox.setActor(result);

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

		}

		return result;

	}

	// Other business methods -----------------------------------------

	public Referee findByPrincipal() {
		Referee referee;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		referee = this.findByUserAccount(userAccount);
		Assert.notNull(referee);

		return referee;
	}

	public Referee findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Referee result;

		result = this.refereeRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
