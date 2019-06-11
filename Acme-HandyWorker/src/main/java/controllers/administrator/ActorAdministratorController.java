
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Edition-----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(administrator, null);
		else
			try {
				this.administratorService.save(administrator);
				if (administrator.getId() == 0)
					result = new ModelAndView("redirect: security/login.do");
				else
					result = new ModelAndView("redirect:/actor/displayPrincipal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listSuspiciousActors", method = RequestMethod.GET)
	public ModelAndView listSuspiciousActors() {
		final ModelAndView result;
		Collection<Actor> suspiciousActors;

		final String banner = this.configurationService.findConfiguration().getBanner();

		suspiciousActors = this.actorService.suspiciousActors();

		result = new ModelAndView("actor/suspicious");
		result.addObject("actors", suspiciousActors);
		result.addObject("banner", banner);
		result.addObject("requestURI", "actor/administrator/listSuspiciousActors.do");

		return result;
	}

	@RequestMapping(value = "/banActor", method = RequestMethod.GET)
	public ModelAndView displayCustomer(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		final String banner = this.configurationService.findConfiguration().getBanner();

		actor = this.actorService.findOne(actorId);
		this.actorService.banOrUnBanActor(actor);

		result = new ModelAndView("redirect:/actor/administrator/listSuspiciousActors.do");
		result.addObject("banner", banner);

		return result;

	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");
		result.addObject("administrator", actor);
		result.addObject("authority", "administrator");
		result.addObject("banner", banner);
		result.addObject("messageError", message);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
}
