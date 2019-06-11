
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import domain.Actor;
import domain.Application;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/customer,handyWorker")
public class FixUpTaskCustomerHandyWorkerController extends AbstractController {

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ApplicationService		applicationService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		FixUpTask fixUpTask;
		final Actor actor;

		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);

		actor = this.actorService.findByPrincipal();

		if (actor.getUserAccount().getAuthorities().contains(authority)) {
			Boolean security;

			security = this.fixUpTaskService.fixUpTaskCustomerSecurity(fixUpTaskId);

			if (security) {
				fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
				result = this.createEditModelAndView(fixUpTask, null);

			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} else {
			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			result = this.createEditModelAndView(fixUpTask, null);
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode) {
		final ModelAndView result;

		final Collection<Application> applications;
		final Collection<Complaint> complaints;

		applications = fixUpTask.getApplications();
		complaints = fixUpTask.getComplaints();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("fixUpTask/view");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("applications", applications);
		result.addObject("complaints", complaints);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		final boolean isNotStarted = fixUpTask.getStartDate().compareTo(currentMoment) >= 0;

		result.addObject("isNotStarted", isNotStarted);

		final boolean notHasApplicationAccepted = this.applicationService.countApplicationAcceptedByFixUpTask(fixUpTask.getId());
		result.addObject("notHasApplicationAccepted", notHasApplicationAccepted);

		return result;
	}
}
