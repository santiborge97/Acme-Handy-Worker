
package controllers.referee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Referee;

@Controller
@RequestMapping("/actor/referee")
public class ActorRefereeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewReferee(referee);
		else
			try {
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:/actor/displayPrincipal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReferee(referee, "referee.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndViewReferee(final Referee referee) {
		ModelAndView result;

		result = this.createEditModelAndViewReferee(referee, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewReferee(final Referee referee, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("security/signUpReferee");
		result.addObject("referee", referee);
		result.addObject("banner", banner);
		result.addObject("message", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

}
