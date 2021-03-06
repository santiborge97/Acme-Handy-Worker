
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------
	@Test
	public void savePhase() {
		super.authenticate("handyWorker1");
		final FixUpTask fixUp = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask5"));

		final Phase phase = this.phaseService.create();

		phase.setDescription("Hola mundo");
		final Date start = new GregorianCalendar(2019, Calendar.AUGUST, 16).getTime();
		final Date end = new GregorianCalendar(2019, Calendar.SEPTEMBER, 21).getTime();

		phase.setStartMoment(start);
		phase.setEndMoment(end);
		phase.setFixUpTask(fixUp);
		phase.setTitle("Phase de prueba");

		final Phase result = this.phaseService.save(phase);
		final Collection<Phase> phases = this.phaseService.findAll();
		Assert.isTrue(phases.contains(result));
		super.unauthenticate();

	}

	@Test
	public void createPhase() {
		super.authenticate("handyWorker1");
		final Phase phase = this.phaseService.create();
		Assert.isNull(phase.getDescription());
		Assert.isNull(phase.getEndMoment());
		Assert.isNull(phase.getFixUpTask());
		Assert.isNull(phase.getStartMoment());
		Assert.isNull(phase.getTitle());
		super.unauthenticate();
	}

	@Test
	public void deletePhase() {
		super.authenticate("handyWorker1");
		final Phase phase = this.phaseService.findOne(super.getEntityId("phase4"));

		this.phaseService.delete(phase);

		final Collection<Phase> phases = this.phaseService.findAll();
		Assert.isTrue(!phases.contains(phase));
		super.unauthenticate();

	}

	@Test
	public void findAllPhase() {
		Collection<Phase> result = new ArrayList<>();
		result = this.phaseService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOnePhase() {
		Phase result = null;
		result = this.phaseService.findOne(super.getEntityId("phase1"));
		Assert.isTrue(result != null);
	}
}
