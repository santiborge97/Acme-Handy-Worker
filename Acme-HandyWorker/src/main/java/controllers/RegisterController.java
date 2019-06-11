
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Credentials;
import services.ConfigurationService;
import services.CustomerService;
import services.HandyWorkerService;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	//Customer

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		final ModelAndView result;
		final Customer customer;

		customer = this.customerService.create();
		result = this.createEditModelAndViewCustomer(customer);

		return result;
	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewCustomer(customer);
		else
			try {
				this.customerService.save(customer);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(customer.getUserAccount().getUsername());
				credentials.setPassword(customer.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewCustomer(customer, "customer.commit.error");
			}
		return result;
	}

	//HandyWorker

	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {
		final ModelAndView result;
		final HandyWorker hw;

		hw = this.handyWorkerService.create();
		result = this.createEditModelAndViewHandyWorker(hw);

		return result;
	}

	@RequestMapping(value = "/editHandyWorker", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHandyWorker(@Valid final HandyWorker hw, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewHandyWorker(hw);
		else
			try {
				this.handyWorkerService.save(hw);
				final Credentials credentials = new Credentials();
				credentials.setJ_username(hw.getUserAccount().getUsername());
				credentials.setPassword(hw.getUserAccount().getPassword());
				result = new ModelAndView("redirect:/security/login.do");
				result.addObject("credentials", credentials);
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewHandyWorker(hw, "customer.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewCustomer(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndViewCustomer(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCustomer(final Customer customer, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpCustomer");
		result.addObject("customer", customer);
		result.addObject("banner", banner);
		result.addObject("message", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewHandyWorker(final HandyWorker hw) {
		ModelAndView result;

		result = this.createEditModelAndViewHandyWorker(hw, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewHandyWorker(final HandyWorker hw, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpHandyWorker");
		result.addObject("handyWorker", hw);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
