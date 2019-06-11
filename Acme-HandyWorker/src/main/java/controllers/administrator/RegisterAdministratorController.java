
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ConfigurationService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Referee;

@Controller
@RequestMapping("/administrator")
public class RegisterAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndViewAdministrator(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewAdministrator(administrator);
		else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewAdministrator(administrator, "administrator.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/editReferee", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewReferee(referee);
		else
			try {
				this.refereeService.save(referee);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReferee(referee, "referee.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndViewAdministrator(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndViewAdministrator(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAdministrator(final Administrator administrator, final String messageCode) {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/signUpAdministrator");
		result.addObject("administrator", administrator);
		result.addObject("banner", banner);
		result.addObject("message", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}

	@RequestMapping(value = "/createReferee", method = RequestMethod.GET)
	public ModelAndView createReferee() {
		final ModelAndView result;
		final Referee referee;
		referee = this.refereeService.create();
		result = this.createEditModelAndViewReferee(referee);

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
		result.addObject("messageError", messageCode);
		final String countryCode = this.configurationService.findConfiguration().getCountryCode();
		result.addObject("defaultCountry", countryCode);

		return result;
	}
}
