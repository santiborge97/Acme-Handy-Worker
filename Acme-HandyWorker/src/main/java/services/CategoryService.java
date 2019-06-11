
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Managed Repository ------------------------

	@Autowired
	private CategoryRepository		categoryRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomerService			customerService;


	// Simple CRUD methods -----------------------

	public Category create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authority));

		Category result;

		final Category root = this.findByName("CATEGORY");

		result = new Category();

		result.setParent(root);

		return result;

	}

	public Collection<Category> findAll() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Authority authority1 = new Authority();
		final Authority authority2 = new Authority();
		authority1.setAuthority(Authority.ADMIN);
		authority2.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority1) || actor.getUserAccount().getAuthorities().contains(authority2));

		Assert.notNull(this.categoryRepository);

		final Collection<Category> res = this.categoryRepository.findAll();

		Assert.notNull(res);

		return res;

	}
	public Category findOne(final int categoryId) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.isTrue(categoryId != 0);

		Assert.notNull(this.categoryRepository);

		final Category result = this.categoryRepository.findOne(categoryId);

		Assert.notNull(result);

		return result;
	}

	public Category save(final Category category) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authority));

		if (category.getId() == 0) {
			final Category categorySameNameEn = this.findByName(category.getNameEn());
			Assert.isTrue(categorySameNameEn == null);

			final Category categorySameNameEs = this.findByName(category.getNameSp());
			Assert.isTrue(categorySameNameEs == null);
		} else if (category.getId() != 0) {
			final Category find = this.categoryRepository.findOne(category.getId());
			Assert.isTrue(!(find.getNameEn().equals("CATEGORY")));
			Assert.isTrue(!(find.getNameSp().equals("CATEGORY")));
		}

		final Category result = this.categoryRepository.save(category);
		Assert.notNull(result);

		return result;
	}
	public void delete(final Category category) {
		Assert.notNull(category);

		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authority));

		Assert.isTrue(category.getId() != 0);

		final Category find = this.categoryRepository.findOne(category.getId());

		Assert.isTrue(!(find.getNameEn().equals("CATEGORY")));
		Assert.isTrue(!(find.getNameSp().equals("CATEGORY")));

		final Collection<Category> children = this.categoryRepository.findChildren(category.getId());
		final Collection<FixUpTask> fixUp = this.fixUpTaskService.findFixUpTaskPerCategory(category.getId());

		if (!children.isEmpty())
			for (final Category c : children) {
				final Category cparent = category.getParent();
				c.setParent(cparent);
				this.save(c);
			}

		if (!fixUp.isEmpty())
			for (final FixUpTask f : fixUp) {
				final Category cfix = category.getParent();
				f.setCategory(cfix);
				this.fixUpTaskService.save(f);
			}

		this.categoryRepository.delete(category);

	}
	// Other business methods -----------------------

	public Collection<Category> findChildren(final int categoryId) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authority));

		final Collection<Category> result = this.categoryRepository.findChildren(categoryId);

		Assert.notNull(result);

		return result;
	}

	public Category findByName(final String categoryName) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authority));

		final Category result = this.categoryRepository.findByName(categoryName);

		return result;
	}
}
