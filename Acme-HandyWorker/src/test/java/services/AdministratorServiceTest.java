
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	//Tests -------------------------------------------------------
	@Test
	public void testCreate() {

		super.authenticate("admin");

		final Administrator administrator = this.administratorService.create();

		Assert.isNull(administrator.getAddress());
		Assert.isNull(administrator.getEmail());
		Assert.isNull(administrator.getMiddleName());
		Assert.isNull(administrator.getName());
		Assert.isNull(administrator.getPhone());
		Assert.isNull(administrator.getPhoto());
		Assert.isNull(administrator.getSurname());
		Assert.notNull(administrator.getSuspicious());
		Assert.isNull(administrator.getUserAccount().getUsername());
		Assert.isNull(administrator.getUserAccount().getPassword());

		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(authority));

		super.authenticate(null);
	}

	@Test
	public void testSave() {

		super.authenticate("admin");

		this.createNewActorAndLogIn();

		super.authenticate(null);
	}

	@Test
	public void testFindOne() {

		super.authenticate("admin");

		final Administrator administrator = this.createNewActorAndLogIn();

		final Administrator finded = this.administratorService.findOne(administrator.getId());

		Assert.isTrue(finded.equals(administrator));

		super.authenticate(null);

	}

	private Administrator createNewActorAndLogIn() {

		Administrator administrator, saved1;
		Collection<Administrator> administrators;

		administrator = this.administratorService.create();
		administrator.setName("nnnn");
		administrator.setMiddleName("mnmn");
		administrator.setPhone("");
		administrator.setSurname("ssss");
		administrator.setAddress("aaaa");
		administrator.setEmail("identifier@domain.com");

		final UserAccount userAccount = administrator.getUserAccount();
		userAccount.setUsername("userAdmin");
		userAccount.setPassword("123456");

		administrator.setUserAccount(userAccount);

		saved1 = this.administratorService.save(administrator);
		administrators = this.administratorService.findAll();
		Assert.isTrue(administrators.contains(saved1));

		return saved1;

	}
}
