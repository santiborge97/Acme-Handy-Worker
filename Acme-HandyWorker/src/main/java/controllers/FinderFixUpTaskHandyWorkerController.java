
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.FinderService;
import services.HandyWorkerService;
import domain.Actor;
import domain.Finder;
import domain.FixUpTask;

@Controller
@RequestMapping("/finderFixUpTask/handyworker")
public class FinderFixUpTaskHandyWorkerController extends AbstractController {

	@Autowired
	HandyWorkerService				handyWorkerService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/filter", method = RequestMethod.POST, params = "filter")
	public ModelAndView filter(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder, null);
		else
			try {
				final Collection<FixUpTask> fixUpTaskSearched = this.finderService.fixUpTaskSearchedList(finder);
				result = new ModelAndView("fixUpTask/filterFixUpTask");
				final Double vatTax = this.configurationService.findConfiguration().getVatTax();
				result.addObject("vatTax", vatTax);
				result.addObject("fixUpTasks", fixUpTaskSearched);
				result.addObject("finder", finder);
				result.addObject("banner", banner);
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(finder, "fixUpTask.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ModelAndView showFinder() {
		ModelAndView result;
		final Finder finder;
		Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();

		final Actor actor = this.actorService.findByPrincipal();
		finder = this.finderService.findFinderByHW(actor);

		final Date currentTime = new Date(System.currentTimeMillis() - 1000);
		final Interval interval = new Interval(finder.getLastUpdate().getTime(), currentTime.getTime());

		final Integer timeOut = this.configurationService.findConfiguration().getFinderTime();
		final Integer pagesize = this.configurationService.findConfiguration().getFinderResult();

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (interval.toDuration().getStandardHours() < timeOut)
			fixUpTasks = finder.getFixUpTasks();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("finder", finder);
		result.addObject("requestURI", "finderFixUpTask/handyworker/find.do");
		result.addObject("requestAction", "finderFixUpTask/handyworker/find.do");
		result.addObject("vatTax", vatTax);
		result.addObject("banner", banner);
		result.addObject("pagesize", pagesize);

		return result;

	}

	@RequestMapping(value = "/find", method = RequestMethod.POST, params = "find")
	public ModelAndView editFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder, null);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:find.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(finder, "fixUpTask.commit.error");

			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();

		result = new ModelAndView("fixUpTask/list");

		final String banner = this.configurationService.findConfiguration().getBanner();

		result.addObject("vatTax", vatTax);
		result.addObject("finder", finder);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}

}
