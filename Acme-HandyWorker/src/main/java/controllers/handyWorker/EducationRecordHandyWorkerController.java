
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CurriculumService;
import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CurriculumService		curriculumService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Boolean security;

		security = this.curriculumService.securityCurriculum();

		if (security) {

			final EducationRecord educationRecord;
			educationRecord = this.educationRecordService.create();
			result = this.createEditModelAndView(educationRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;
		Boolean security;
		security = this.educationRecordService.securityEducation(educationRecordId);

		if (security) {

			educationRecord = this.educationRecordService.findOne(educationRecordId);
			Assert.notNull(educationRecord);
			result = this.createEditModelAndView(educationRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("educationRecord/handyWorker/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
