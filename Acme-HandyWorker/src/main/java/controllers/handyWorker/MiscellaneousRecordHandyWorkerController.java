
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyWorker")
public class MiscellaneousRecordHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

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
			final MiscellaneousRecord miscellaneousRecord;
			miscellaneousRecord = this.miscellaneousRecordService.create();

			result = this.createEditModelAndView(miscellaneousRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		Boolean security;
		security = this.miscellaneousRecordService.securityMiscellaneous(miscellaneousRecordId);

		if (security) {

			miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Assert.notNull(miscellaneousRecord);
			result = this.createEditModelAndView(miscellaneousRecord);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/curriculum/handyWorker/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String message) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("miscellaneousRecord/handyWorker/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("messageError", message);
		result.addObject("banner", banner);

		return result;
	}

}
