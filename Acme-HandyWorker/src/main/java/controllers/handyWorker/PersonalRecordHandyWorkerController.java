
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CurriculumService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ConfigurationService	configurationService;


	// Create -------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PersonalRecord personalRecord;
		Boolean security;

		security = this.curriculumService.securityCurriculum();

		if (!security) {

			final String banner = this.configurationService.findConfiguration().getBanner();

			personalRecord = this.personalRecordService.create();
			result = this.create2ModelAndView(personalRecord);
			result.addObject("banner", banner);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save1")
	public ModelAndView save1(@Valid final PersonalRecord personalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.create2ModelAndView(personalRecord);
		else
			try {
				final PersonalRecord pr = this.personalRecordService.save(personalRecord);
				final Curriculum curriculum;
				curriculum = this.curriculumService.create(pr);
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.create2ModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save2")
	public ModelAndView save2(@Valid final PersonalRecord personalRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord, curriculumId);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error", curriculumId);
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final int curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null, curriculumId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String message, final int curriculumId) {

		ModelAndView result;
		result = new ModelAndView("personalRecord/handyWorker/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView create2ModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.create2ModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView create2ModelAndView(final PersonalRecord personalRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("personalRecord/handyWorker/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
