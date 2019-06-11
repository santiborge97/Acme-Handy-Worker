
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository---------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;

	//Suporting services---------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	//Simple CRUD methods--------------------------------
	public Sponsor create() {
		Sponsor result;
		result = new Sponsor();

		final UserAccount userAccount = this.userAccountService.createSponsor();
		result.setUserAccount(userAccount);
		result.setSuspicious(false);

		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor sponsor;
		sponsor = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(sponsor);
		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		if (sponsor.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == sponsor.getId() || actor.getUserAccount().getAuthorities().contains(admin));
		}

		sponsor.setSuspicious(false);

		String pass = sponsor.getUserAccount().getPassword();

		final Md5PasswordEncoder code = new Md5PasswordEncoder();

		pass = code.encodePassword(pass, null);

		final UserAccount userAccount = sponsor.getUserAccount();
		userAccount.setPassword(pass);

		sponsor.setUserAccount(userAccount);

		final Sponsor result = this.sponsorRepository.save(sponsor);

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

		inBox.setActor(sponsor);
		outBox.setActor(sponsor);
		trashBox.setActor(sponsor);
		spamBox.setActor(sponsor);

		final Collection<Box> boxes = new ArrayList<>();
		boxes.add(spamBox);
		boxes.add(trashBox);
		boxes.add(inBox);
		boxes.add(outBox);

		inBox = this.boxService.saveNewActor(inBox);
		outBox = this.boxService.saveNewActor(outBox);
		trashBox = this.boxService.saveNewActor(trashBox);
		spamBox = this.boxService.saveNewActor(spamBox);

		return result;
	}

	//Other business methods----------------------------

	public Sponsor findByPrincipal() {
		Sponsor s;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		s = this.findByUserAccount(userAccount);
		Assert.notNull(s);

		return s;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
