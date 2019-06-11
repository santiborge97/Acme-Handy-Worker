
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	// Managed repository -------------------------------------------

	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Simple CRUD methods ------------------------------------------

	public Finder create() {

		Finder result;

		result = new Finder();

		result.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		return result;

	}

	public Collection<Finder> findAll() {

		final Collection<Finder> result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Finder findOne(final int finderId) {

		final Finder result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);
		return result;

	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		if (finder.getId() != 0) {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			final Authority hW = new Authority();
			hW.setAuthority(Authority.HANDYWORKER);
			if (actor.getUserAccount().getAuthorities().contains(hW)) {
				final HandyWorker owner = finder.getHandyWorker();
				Assert.notNull(owner);
				Assert.isTrue(actor.getId() == owner.getId());
				finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
				final Collection<FixUpTask> fixUpTasksSearchedList = this.fixUpTaskSearchedList(finder);
				finder.setFixUpTasks(fixUpTasksSearchedList);
			}
		} else
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		final Finder result = this.finderRepository.save(finder);

		return result;

	}
	// Other business methods -----------------------------------------
	public Collection<Finder> findFindersByFixUpTaskId(final int fixUpTaskId) {
		final Collection<Finder> result = this.finderRepository.findFindersByFixUpTaskId(fixUpTaskId);
		Assert.notNull(result);
		return result;
	}

	public Finder findFinderByHW(final Actor actor) {
		final Authority hW = new Authority();
		hW.setAuthority(Authority.HANDYWORKER);

		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(hW));
		final Finder finder = this.finderRepository.findFinderByHW(actor.getId());
		return finder;

	}

	public Collection<FixUpTask> fixUpTaskSearchedList(final Finder finder) {
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();
		final Collection<FixUpTask> result = new ArrayList<FixUpTask>();

		for (final FixUpTask f : fixUpTasks)
			if (this.testFixUp(finder, f) == true)
				result.add(f);

		return result;

	}

	private Boolean testFixUp(final Finder finder, final FixUpTask f) {

		//filters
		final String keyWord = finder.getKeyWord();
		final String category = finder.getCategory();
		final String warranty = finder.getWarranty();
		final Double minPrice = finder.getMinPrice();
		final Double maxPrice = finder.getMaxPrice();
		Date maxDateConvert = null;
		Date minDateConvert = null;

		if (!finder.getMaxDate().isEmpty() && finder.getMaxDate() != null)
			maxDateConvert = this.convertStringToDate(finder.getMaxDate());

		if (!finder.getMinDate().isEmpty() && finder.getMinDate() != null)
			minDateConvert = this.convertStringToDate(finder.getMinDate());

		//inicializamos a true porque en cualquier caso lo vamos a mostrar todo

		boolean keyWordFind;
		boolean categoryFind;
		boolean warrantyFind;
		boolean prices;
		boolean dates;

		//Casos de keyWord
		if (finder.getKeyWord() != null && finder.getKeyWord() != "")
			keyWordFind = (f.getTicker().contains(keyWord) || f.getAddress().contains(keyWord) || f.getDescription().contains(keyWord));
		else
			keyWordFind = true;

		//Casos de category
		if (finder.getCategory() != null && finder.getCategory() != "")
			categoryFind = f.getCategory().getNameEn().contains(category) || f.getCategory().getNameSp().contains(category);
		else
			categoryFind = true;

		//Casos de warranty
		if (finder.getWarranty() != null && finder.getWarranty() != "")
			warrantyFind = f.getWarranty().getTitle().contains(warranty);
		else
			warrantyFind = true;

		//Casos de prices
		if (finder.getMinPrice() != null && finder.getMaxPrice() != null)
			prices = f.getMaximumPrice().getAmount().compareTo(minPrice) >= 0 && f.getMaximumPrice().getAmount().compareTo(maxPrice) <= 0;
		else if (finder.getMinPrice() == null && finder.getMaxPrice() != null)
			prices = f.getMaximumPrice().getAmount().compareTo(maxPrice) <= 0;
		else if (finder.getMinPrice() != null && finder.getMaxPrice() == null)
			prices = f.getMaximumPrice().getAmount().compareTo(minPrice) >= 0;
		else
			prices = true;

		//Casos de dates

		if (minDateConvert != null && maxDateConvert != null)
			dates = f.getStartDate().compareTo(minDateConvert) >= 0 && f.getStartDate().compareTo(maxDateConvert) <= 0;
		else if (minDateConvert == null && maxDateConvert != null)
			dates = f.getStartDate().compareTo(maxDateConvert) <= 0;
		else if (minDateConvert != null && maxDateConvert == null)
			dates = f.getStartDate().compareTo(minDateConvert) >= 0;
		else
			dates = true;

		//---------------comparamos todos los parámetros---------------
		boolean isSearched = false;
		if (keyWordFind && categoryFind && warrantyFind && prices && dates)
			isSearched = true;

		return isSearched;

	}

	public Date convertStringToDate(final String dateString) {
		Date date = null;
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = df.parse(dateString);
		} catch (final Exception ex) {
			System.out.println(ex);
		}
		return date;
	}
}
