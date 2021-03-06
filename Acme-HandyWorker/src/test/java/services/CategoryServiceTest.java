
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------

	// Simple CRUD methods -----------------------
	@Test
	public void testCreateCategory() {
		super.authenticate("admin");

		Category c;
		c = this.categoryService.create();
		Assert.notNull(c.getParent());
		Assert.isNull(c.getNameEn());

		super.authenticate(null);
	}

	@Test
	public void testSaveCategory() {
		super.authenticate("admin");

		Category c, saved;
		c = this.categoryService.create();
		c.setNameEn("shoes"); /* NO POPULAR ESTA CATEGORY */
		c.setNameSp("zapatos");

		saved = this.categoryService.save(c);

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSaveDuplicate() {
		super.authenticate("admin");

		Category c1, c2, saved1;

		c1 = this.categoryService.create();
		c1.setNameEn("shoes"); /* NO POPULAR ESTA CATEGORY */
		c1.setNameSp("zapatos");

		c2 = this.categoryService.create();
		c2.setNameEn("zapatos");
		c2.setNameSp("shoes");

		saved1 = this.categoryService.save(c1);

		final Collection<Category> categories = this.categoryService.findAll();

		Assert.isTrue(categories.contains(saved1));

		try {
			this.categoryService.save(c2);
		} catch (final Exception e) {

		}
		final Collection<Category> categories2 = this.categoryService.findAll();

		for (final Category c : categories2)
			Assert.isTrue(!(c.getNameEn() == saved1.getNameEn() && c.getId() != saved1.getId()));

		super.authenticate(null);

	}

	@Test
	public void testDeleteCategory() {
		super.authenticate("admin");

		Category c, saved;
		c = this.categoryService.create();
		c.setNameEn("shoes"); /* NO POPULAR ESTA CATEGORY */
		c.setNameSp("zapatos");

		saved = this.categoryService.save(c);

		this.categoryService.delete(saved);

		super.authenticate(null);

	}

	@Test
	public void testDeleteCATEGORY() {
		super.authenticate("admin");
		Category c;

		c = this.categoryService.findByName("CATEGORY");

		try {

			this.categoryService.delete(c);

		} catch (final Exception e) {

		}

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(c));

		super.authenticate(null);

	}

	@Test
	public void testDeleteCategoryUnlogged() { /* Con este test vamos a comprobar la seguridad de autenticaci�n */
		super.authenticate("customer1"); /* para poder acceder al findOne */

		final Category category2 = this.categoryService.findOne(super.getEntityId("category2")); /* Category 2 en el populate */

		super.authenticate(null);
		/* ahora estando deslogeado */

		try {
			this.categoryService.delete(category2);
		} catch (final Exception e) {

		}

		/* un personaje se logea y busca nuestra category v�ctima */
		super.authenticate("admin");
		final Category categoryFind = this.categoryService.findOne(super.getEntityId("category2"));

		Assert.notNull(categoryFind);

		super.authenticate(null);

	}

	@Test
	public void testDeleteCategoryWithChildrenAndFixUpAssociated() {
		super.authenticate("admin");

		final Category category1 = this.categoryService.findOne(super.getEntityId("category1"));/* categoria padre */
		final Category category2 = this.categoryService.findOne(super.getEntityId("category2")); /* categor�a hija */
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1")); /* FixUp que tiene como categor�a la categor2 */

		this.categoryService.delete(category2);

		Assert.isTrue(fixUpTask.getCategory().equals(category1));

		super.authenticate(null);

	}

}
