
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ReportService		reportSevice;

	@Autowired
	private RefereeService		refereeservice;

	@Autowired
	private CustomerService		customerservice;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		Report report;
		final int complaintId = this.getEntityId("complaint1");

		super.authenticate("referee1");
		report = this.reportSevice.create(complaintId);

		Assert.notNull(report.getComplaint());
		Assert.isNull(report.getDescription());
		Assert.isNull(report.getFinalMode());
		Assert.notNull(report.getMoment());
		Assert.notNull(report.getReferee());
		Assert.notNull(report.getAttachments().isEmpty());
		Assert.isTrue(report.getNotes().isEmpty());

		super.authenticate(null);

	}

	@Test
	public void testFindOne() {

		Report report, saved, finded;
		final int complaintId = this.getEntityId("complaint1");

		super.authenticate("referee1");

		report = this.reportSevice.create(complaintId);

		report.setDescription("XXXXX");

		saved = this.reportSevice.save(report);
		finded = this.reportSevice.findOne(saved.getId());
		Assert.isTrue(finded.equals(saved));

		super.authenticate(null);

	}
}
