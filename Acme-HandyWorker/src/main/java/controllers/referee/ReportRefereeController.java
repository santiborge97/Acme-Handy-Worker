
package controllers.referee;

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
import services.ComplaintService;
import services.ConfigurationService;
import services.RefereeService;
import services.ReportService;
import domain.Referee;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class ReportRefereeController {

	@Autowired
	private ReportService			reportService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


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
				result.addObject("banner", banner);
				result.addObject("autoridad", "referee");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("report/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "referee");
			result.addObject("banner", banner);

		}

		return result;

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Report> reports;

		final Referee referee = this.refereeService.findByPrincipal();

		reports = this.reportService.findAllByReferee(referee.getId());

		result = new ModelAndView("report/listReport");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/referee/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int complaintId) {
		ModelAndView result;
		Report report;
		Boolean security;

		security = this.complaintService.ComplaintSecurity(complaintId);

		if (security) {
			report = this.reportService.create(complaintId);
			result = this.createEditModelAndView(report);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;
		Boolean security;

		security = this.reportService.ReportSecurity(reportId);

		report = this.reportService.findOne(reportId);

		if (security && report != null)
			result = this.createEditModelAndView(report);
		else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(report, "report.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Report report, final BindingResult binding) {
		ModelAndView result;

		try {
			this.reportService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(report, "report.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report) {

		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Report report, final String messageCode) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("report/editReport");
		result.addObject("report", report);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}

}
