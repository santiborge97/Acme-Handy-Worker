
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	//Managed repository---------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	//Suporting services---------------------------------
	@Autowired
	private PersonalRecordService	personalRecordService;

	//	@Autowired
	//	private EducationRecordService		educationRecordService;
	//
	//	@Autowired
	//	private ProfessionalRecordService	professionalRecordService;
	//
	//	@Autowired
	//	private EndorserRecordService		endorserRecordService;
	//
	//	@Autowired
	//	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Simple CRUD methods--------------------------------
	public Curriculum create(final PersonalRecord personalRecord) {

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(handyWorker.getUserAccount().getAuthorities().contains(authority));

		final Curriculum c = new Curriculum();
		final Date date = new Date();

		c.setHandyWorker(handyWorker);
		c.setPersonalRecord(personalRecord);

		c.setTicker(this.generateTicker(date));
		c.setEducationRecords(new ArrayList<EducationRecord>());
		c.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		c.setEndorserRecords(new ArrayList<EndorserRecord>());
		c.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());

		return c;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;
		result = this.curriculumRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum c;
		c = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(c);
		return c;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Actor owner = curriculum.getHandyWorker();
		Assert.isTrue(actor.getId() == owner.getId());
		if (curriculum.getId() == 0)
			this.personalRecordService.save(curriculum.getPersonalRecord());

		final Curriculum c = this.curriculumRepository.save(curriculum);

		return c;
	}

	//Other business methods----------------------------

	public Curriculum findByHandyWorkerId(final int handyWorkerId) {

		final Curriculum result = this.curriculumRepository.findByHandyWorkerId(handyWorkerId);

		return result;
	}

	public Curriculum findByPersonalRecordId(final int personalRecordId) {

		Assert.notNull(personalRecordId);

		final Curriculum result = this.curriculumRepository.findByPersonalRecordId(personalRecordId);

		Assert.notNull(result);

		return result;
	}

	private String generateTicker(final Date moment) {

		final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		final String dateString = dateFormat.format(moment);

		final String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final StringBuilder salt = new StringBuilder();
		final Random rnd = new Random();
		while (salt.length() < 6) {
			final int index = (int) (rnd.nextFloat() * alphaNumeric.length());
			salt.append(alphaNumeric.charAt(index));
		}
		final String randomAlphaNumeric = salt.toString();

		final String ticker = dateString + "-" + randomAlphaNumeric;

		final int curriculumSameTicker = this.curriculumRepository.countCurriculumWithTicker(ticker);

		//nos aseguramos que que sea único
		while (curriculumSameTicker > 0)
			this.generateTicker(moment);

		return ticker;

	}

	public Boolean securityCurriculum() {

		Boolean res = false;

		final HandyWorker login = this.handyWorkerService.findByPrincipal();

		final Curriculum owner = this.curriculumRepository.findByHandyWorkerId(login.getId());

		if (owner != null)
			res = true;

		return res;

	}
}
