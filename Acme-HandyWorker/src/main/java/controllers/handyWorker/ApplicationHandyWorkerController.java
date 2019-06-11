
package controllers.handyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ConfigurationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyWorker")
public class ApplicationHandyWorkerController {

	@Autowired
	ApplicationService				applicationService;

	@Autowired
	HandyWorkerService				handyWorkerService;

	@Autowired
	FixUpTaskService				fixUpTaskService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors-------------------------------------------------------
	public ApplicationHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {

		ModelAndView result;
		final Application application;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		try {

			application = this.applicationService.findOne(applicationId);

			security = this.applicationService.ApplicationSecurity(applicationId);

			if (security) {
				result = new ModelAndView("application/display");
				result.addObject("application", application);
				result.addObject("banner", banner);
				result.addObject("autoridad", "handyWorker");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("application/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "handyWorker");
			result.addObject("banner", banner);

		}

		return result;

	}

	// Listing------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();

		applications = this.applicationService.findApplicationsByHandyWorker(handyWorker.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/handyWorker/list.do");
		result.addObject("autoridad", "handyWorker");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Application application;

		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(fixUpTask);

		application = this.applicationService.createNew(fixUpTask);

		final boolean hasNotApplicationAccepted = this.applicationService.countApplicationAcceptedByFixUpTask(fixUpTaskId);
		if (!hasNotApplicationAccepted)
			result = new ModelAndView("redirect:/welcome/index.do");
		else
			result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	private ModelAndView createEditModelAndView(final Application application) {

		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;

	}

	private ModelAndView createEditModelAndView(final Application application, final String messageCode) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/create");
		result.addObject("application", application);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}

}
