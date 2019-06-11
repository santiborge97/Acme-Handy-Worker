
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SponsorService	sponsorService;


	//Tests -------------------------------------------------------s
	@Test
	public void createSponsor() {
		final Sponsor sp = this.sponsorService.create();
		Assert.isNull(sp.getAddress());
		Assert.notNull(sp.getSuspicious());
		Assert.isNull(sp.getName());
		Assert.isNull(sp.getMiddleName());
		Assert.isNull(sp.getSurname());
		Assert.isNull(sp.getPhone());
		Assert.isNull(sp.getPhoto());
		Assert.isNull(sp.getEmail());
	}

	@Test
	public void updateSponsor() {
		super.authenticate("sponsor1");
		final Sponsor s = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		final String newName = "Paco";
		s.setName(newName);
		final Sponsor saved = this.sponsorService.save(s);

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		Assert.isTrue(sponsors.contains(saved));
		super.authenticate(null);

	}

	@Test
	public void findAllSponsor() {
		Collection<Sponsor> result = new ArrayList<>();
		result = this.sponsorService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOneSponsor() {
		Sponsor result = null;
		result = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.isTrue(result != null);
	}

}
