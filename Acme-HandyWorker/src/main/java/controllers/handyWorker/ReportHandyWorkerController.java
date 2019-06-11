
package controllers.handyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ConfigurationService;
import services.ReportService;
import domain.Report;

@Controller
@RequestMapping("/report/handyWorker")
public class ReportHandyWorkerController {

	@Autowired
	private ReportService			reportService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ComplaintService		complaintService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {

		ModelAndView result;
		final Report report;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		try {

			report = this.reportService.findOneByComplaintId(complaintId);

			security = this.complaintService.ComplaintSecurity(complaintId);

			if (security) {
				result = new ModelAndView("report/display");
				result.addObject("report", report);
				result.addObject("autoridad", "handyWorker");
				result.addObject("banner", banner);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("report/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "handyWorker");
			result.addObject("banner", banner);

		}

		return result;

	}

}
