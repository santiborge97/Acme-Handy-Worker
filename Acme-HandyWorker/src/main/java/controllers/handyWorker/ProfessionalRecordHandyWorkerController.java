
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
import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private CurriculumService			curriculumService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Boolean security;

		security = this.curriculumService.securityCurriculum();

		if (security) {

			final ProfessionalRecord professionalRecord;
			professionalRecord = this.professionalRecordService.create();
			result = this.createEditModelAndView(professionalRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		final ModelAndView result;
		final ProfessionalRecord professionalRecord;

		Boolean security;
		security = this.professionalRecordService.securityProfessional(professionalRecordId);

		if (security) {

			professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
			Assert.notNull(professionalRecord);
			result = this.createEditModelAndView(professionalRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("professionalRecord/handyWorker/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
