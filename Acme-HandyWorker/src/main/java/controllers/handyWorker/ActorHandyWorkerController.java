
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.HandyWorkerService;
import domain.Actor;
import domain.HandyWorker;

@Controller
@RequestMapping("/actor/handyWorker")
public class ActorHandyWorkerController {

	// Services ---------------------------------------------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	// Edition-----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(handyWorker, null);
		else
			try {
				this.handyWorkerService.save(handyWorker);
				if (handyWorker.getId() == 0)
					result = new ModelAndView("redirect: security/login.do");
				else
					result = new ModelAndView("redirect:/actor/displayPrincipal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker, "actor.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/edit");
		result = new ModelAndView("actor/edit");
		result.addObject("handyWorker", actor);
		result.addObject("authority", "handyWorker");
		result.addObject("banner", banner);
		result.addObject("messageError", message);

		return result;
	}
}
