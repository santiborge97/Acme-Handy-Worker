
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private HandyWorkerService				handyWorkerService;


	// Simple CRUD methods -----------------------

	public MiscellaneousRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		if (miscellaneousRecord.getId() == 0) {

			final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
			Assert.notNull(handyWorker);

			final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(handyWorker.getId());
			Assert.notNull(curriculum);
			final Collection<MiscellaneousRecord> mr = curriculum.getMiscellaneousRecords();
			mr.add(result);
			curriculum.setMiscellaneousRecords(mr);
			this.curriculumService.save(curriculum);
		}

		return result;
	}

	// Other business methods -----------------------

	public Boolean securityMiscellaneous(final int miscellaneousId) {

		Boolean res = false;
		Collection<MiscellaneousRecord> loginMiscellaneous = null;

		final MiscellaneousRecord owner = this.findOne(miscellaneousId);

		final HandyWorker login = this.handyWorkerService.findByPrincipal();
		final Curriculum loginCurriculum = this.curriculumService.findByHandyWorkerId(login.getId());

		if (loginCurriculum != null)
			loginMiscellaneous = loginCurriculum.getMiscellaneousRecords();

		if (loginMiscellaneous != null && owner != null)
			if (loginMiscellaneous.contains(owner))
				res = true;

		return res;
	}
}
