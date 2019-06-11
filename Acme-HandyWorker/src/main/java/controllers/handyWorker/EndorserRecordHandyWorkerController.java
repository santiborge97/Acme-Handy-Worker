
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
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

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
			final EndorserRecord endorserRecord;
			endorserRecord = this.endorserRecordService.create();

			result = this.createEditModelAndView(endorserRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		Boolean security;
		security = this.endorserRecordService.securityEndorser(endorserRecordId);

		if (security) {

			endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
			Assert.notNull(endorserRecord);
			result = this.createEditModelAndView(endorserRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("endorserRecord/handyWorker/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
