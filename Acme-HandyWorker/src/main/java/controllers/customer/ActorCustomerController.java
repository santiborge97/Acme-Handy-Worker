
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;

@Controller
@RequestMapping("/actor/customer")
public class ActorCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Edition-----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(customer, null);
		else
			try {
				this.customerService.save(customer);
				if (customer.getId() == 0)
					result = new ModelAndView("redirect: security/login.do");
				else
					result = new ModelAndView("redirect:/actor/displayPrincipal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer, "actor.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");
		result.addObject("customer", actor);
		result.addObject("authority", "customer");
		result.addObject("banner", banner);
		result.addObject("messageError", message);

		return result;
	}
}
