
package controllers.customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import services.CustomerService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	@Autowired
	ApplicationService		applicationService;

	@Autowired
	CustomerService			customerService;

	@Autowired
	ConfigurationService	configurationService;


	// Constructors-------------------------------------------------------
	public ApplicationCustomerController() {
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
				result.addObject("autoridad", "customer");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("application/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "customer");
			result.addObject("banner", banner);

		}

		return result;

	}
	// Listing------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;

		final Customer customer = this.customerService.findByPrincipal();

		applications = this.applicationService.findApplicationsByCustomer(customer.getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("banner", banner);
		result.addObject("requestURI", "application/customer/list.do");
		result.addObject("autoridad", "customer");

		return result;
	}

	// Accept------------------------------------------------------------
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		Boolean security;

		final Customer customer = this.customerService.findByPrincipal();

		security = this.applicationService.ApplicationSecurity(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Collection<String> makes = this.configurationService.findConfiguration().getCreditCardMakes();
		final Map<String, String> makesMap = new HashMap<>();

		for (final String string : makes)
			makesMap.put(string, string);

		if (security) {
			result = new ModelAndView("application/accept");
			result.addObject("makes", makesMap);
			result.addObject("application", application);
			result.addObject("banner", banner);
			result.addObject("authority", customer.getUserAccount().getAuthorities().toArray()[0].toString().toLowerCase());
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	// Reject------------------------------------------------------------
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		Boolean security;

		final Customer customer = this.customerService.findByPrincipal();

		security = this.applicationService.ApplicationSecurity(applicationId);

		final String banner = this.configurationService.findConfiguration().getBanner();

		if (security) {
			result = new ModelAndView("application/reject");
			result.addObject("application", application);
			result.addObject("banner", banner);
			result.addObject("authority", customer.getUserAccount().getAuthorities().toArray()[0].toString().toLowerCase());
		} else
			result = new ModelAndView("redirect:/welcome/index.do");
		return result;
	}
	//Save --------------------------------------------------------------------
	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "saveAccept")
	public ModelAndView saveAccept(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application, null, "accept");
		else
			try {
				Assert.isTrue(application.getCommentReject() == null);
				application.setStatus("ACCEPTED");
				this.applicationService.save(application);
				this.configurationService.notificationStatus(application.getId());
				result = new ModelAndView("redirect:/application/customer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.error", "accept");//cambiar ese mensaje de error
			}
		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "saveReject")
	public ModelAndView saveReject(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application, null, "reject");
		else
			try {
				Assert.isTrue(application.getCommentReject() != null);
				application.setStatus("REJECTED");
				this.applicationService.save(application);
				this.configurationService.notificationStatus(application.getId());
				result = new ModelAndView("redirect:/application/customer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.error", "reject");//cambiar ese mensaje de error
			}
		return result;
	}
	// Ancillary methods -------------------------------------------
	protected ModelAndView createEditModelAndView(final Application application, final String messageCode, final String function) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();
		final Collection<String> makes = this.configurationService.findConfiguration().getCreditCardMakes();
		final Map<String, String> makesMap = new HashMap<>();

		for (final String string : makes)
			makesMap.put(string, string);

		result = new ModelAndView("application/" + function);
		result.addObject("application", application);
		result.addObject("authority", "customer");
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("makes", makesMap);

		return result;
	}
}
