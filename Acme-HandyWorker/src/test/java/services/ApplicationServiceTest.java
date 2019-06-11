
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------
	@Test
	public void testSave() {
		super.authenticate("handyWorker1");
		Application a, saved;

		a = this.applicationService.create();
		a.setComment("Application1234");

		final Money m = new Money();
		m.setAmount(23.5);
		m.setCurrency("$");
		a.setOfferedPrice(m);

		final FixUpTask f = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask3"));
		a.setFixUpTask(f);

		saved = this.applicationService.save(a);
		Assert.isTrue(this.applicationService.findAll().contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testCreate() {

		super.authenticate("handyWorker1");

		final Application a = this.applicationService.create();

		Assert.isNull(a.getComment());
		Assert.isNull(a.getCreditCard());
		Assert.isNull(a.getFixUpTask());
		Assert.isNull(a.getMoment());
		Assert.isNull(a.getOfferedPrice());
		Assert.isNull(a.getStatus());

		super.authenticate(null);
	}

	@Test
	public void testFindOne() {

		super.authenticate("handyWorker1");
		Application a, saved;

		a = this.applicationService.create();
		a.setComment("Application1234");

		final Money m = new Money();
		m.setAmount(23.5);
		m.setCurrency("$");
		a.setOfferedPrice(m);

		final FixUpTask f = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask3"));
		a.setFixUpTask(f);

		saved = this.applicationService.save(a);
		Assert.isTrue(this.applicationService.findOne(saved.getId()).equals(saved));

		super.authenticate(null);
	}

}
