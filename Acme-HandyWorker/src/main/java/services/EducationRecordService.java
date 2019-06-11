
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Suporting services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods

	public EducationRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final EducationRecord result;

		result = new EducationRecord();

		return result;

	}

	public Collection<EducationRecord> findAll() {

		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public EducationRecord findOne(final int educationRecordId) {

		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);

		return result;

	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);

		if (educationRecord.getId() == 0) {

			final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
			Assert.notNull(handyWorker);

			final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(handyWorker.getId());
			Assert.notNull(curriculum);
			final Collection<EducationRecord> er = curriculum.getEducationRecords();
			er.add(result);
			curriculum.setEducationRecords(er);
			this.curriculumService.save(curriculum);
		}

		return result;

	}

	// Other business methods

	public Boolean securityEducation(final int educationId) {

		Boolean res = false;
		Collection<EducationRecord> loginEducation = null;

		final EducationRecord owner = this.findOne(educationId);

		final HandyWorker login = this.handyWorkerService.findByPrincipal();
		final Curriculum loginCurriculum = this.curriculumService.findByHandyWorkerId(login.getId());

		if (loginCurriculum != null)
			loginEducation = loginCurriculum.getEducationRecords();

		if (loginEducation != null && owner != null)
			if (loginEducation.contains(owner))
				res = true;

		return res;
	}
}
