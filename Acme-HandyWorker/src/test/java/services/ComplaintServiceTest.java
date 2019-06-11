
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerservice;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		this.authenticate("customer1");

		Complaint complaint;
		complaint = this.complaintService.create();

		Assert.isTrue(complaint.getAttachments().isEmpty());
		Assert.isNull(complaint.getDescription());
		Assert.isNull(complaint.getReferee());
		Assert.isNull(complaint.getTicker());

		super.authenticate(null);

	}
}
