
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Application;
import domain.Box;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository---------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	//Suporting services---------------------------------
	@Autowired
	private FinderService			finderService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		userAccountService;


	//Simple CRUD methods--------------------------------
	public HandyWorker create() {

		final HandyWorker hw = new HandyWorker();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = this.userAccountService.createHandyWorker();
		hw.setUserAccount(userAccount);

		final Collection<Application> app = new HashSet<>();
		hw.setApplications(app);

		hw.setSuspicious(false);

		return hw;
	}
	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;
		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker hw;
		hw = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(hw);
		return hw;
	}

	public HandyWorker save(final HandyWorker handyWorker) {

		HandyWorker hw = null;
		Assert.notNull(handyWorker);

		if (handyWorker.getId() != 0) {
			final Authority admin = new Authority();
			admin.setAuthority(Authority.ADMIN);

			final int id = LoginService.getPrincipal().getId();
			final Actor actor = this.actorService.findByPrincipal();

			Assert.isTrue(id == handyWorker.getUserAccount().getId() || actor.getUserAccount().getAuthorities().contains(admin));

			this.actorService.checkEmail(handyWorker.getEmail(), false);

			final String phone = this.actorService.checkPhone(handyWorker.getPhone());
			handyWorker.setPhone(phone);

			hw = this.handyWorkerRepository.save(handyWorker);
		} else {

			handyWorker.setSuspicious(false);

			String pass = handyWorker.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final UserAccount userAccount = handyWorker.getUserAccount();
			userAccount.setPassword(pass);

			handyWorker.setUserAccount(userAccount);

			if (handyWorker.getMake().isEmpty())
				handyWorker.setMake(handyWorker.getName() + " " + handyWorker.getMiddleName() + " " + handyWorker.getSurname());

			this.actorService.checkEmail(handyWorker.getEmail(), false);

			final String phone = this.actorService.checkPhone(handyWorker.getPhone());
			handyWorker.setPhone(phone);

			hw = this.handyWorkerRepository.save(handyWorker);

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

			inBox.setActor(hw);
			outBox.setActor(hw);
			trashBox.setActor(hw);
			spamBox.setActor(hw);

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

			final Finder finder = this.finderService.create();
			finder.setHandyWorker(hw);
			this.finderService.save(finder);

		}

		Assert.isTrue(hw.getMake() != null);

		return hw;
	}
	//Other business methods----------------------------

	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = this.findByUserAccount(userAccount);
		Assert.notNull(hw);

		return hw;
	}

	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		HandyWorker result;

		result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<HandyWorker> handyWorkersTenPerCentMore() {

		final Collection<HandyWorker> result = this.handyWorkerRepository.handyWorkersTenPerCentMore();
		Assert.notNull(result);
		return result;

	}

	public Collection<HandyWorker> topThreeHandyWorkersComplaints() {

		final Collection<HandyWorker> handyWorkers = this.handyWorkerRepository.rankingHandyWorkersComplaints();
		Assert.notNull(handyWorkers);

		final List<HandyWorker> ranking = new ArrayList<HandyWorker>();
		ranking.addAll(handyWorkers);

		Collection<HandyWorker> result = new HashSet<HandyWorker>();
		if (ranking.size() > 0)
			result = ranking.subList(0, 3);
		return result;

	}

	public HandyWorker findHandyWorkerByApplicationId(final int applicationId) {

		final HandyWorker result = this.handyWorkerRepository.findHandyWorkerByApplicationId(applicationId);
		Assert.notNull(result);

		return result;

	}

}
