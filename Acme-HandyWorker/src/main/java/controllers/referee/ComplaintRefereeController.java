
package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ComplaintService;
import services.ConfigurationService;
import services.RefereeService;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController {

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {

		ModelAndView result;
		final Complaint complaint;
		Boolean security;

		final String banner = this.configurationService.findConfiguration().getBanner();

		try {

			complaint = this.complaintService.findOne(complaintId);

			security = this.complaintService.ComplaintSecurity(complaintId);

			if (security) {
				result = new ModelAndView("complaint/display");
				result.addObject("complaint", complaint);
				result.addObject("banner", banner);
				result.addObject("autoridad", "referee");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("complaint/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "referee");
			result.addObject("banner", banner);

		}

		return result;

	}

	@RequestMapping(value = "/unassigned", method = RequestMethod.GET)
	public ModelAndView listUnassigned() {
		ModelAndView result;

		final Collection<Complaint> complaints;

		complaints = this.complaintService.findAllUnassigned();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("complaint/unassigned");
		result.addObject("complaints", complaints);
		result.addObject("banner", banner);
		result.addObject("RequestURI", "complaint/referee/unassigned.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Complaint> complaints;
		final UserAccount ua = LoginService.getPrincipal();

		complaints = this.complaintService.findAllComplaintsOfReferee(this.refereeService.findByUserAccount(ua).getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("banner", banner);
		result.addObject("RequestURI", "complaint/referee/list.do");

		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(@RequestParam final int complaintId) {
		ModelAndView result;

		Complaint complaint = this.complaintService.findOne(complaintId);

		final Collection<Complaint> complaints;
		final UserAccount ua = LoginService.getPrincipal();

		if (complaint.getReferee() == null && complaint != null) {
			complaint = this.complaintService.findOne(complaintId);
			complaint.setReferee(this.refereeService.findByUserAccount(ua));
			this.complaintService.save(complaint);

			complaints = this.complaintService.findAllComplaintsOfReferee(this.refereeService.findByUserAccount(ua).getId());

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("complaint/list");
			result.addObject("complaints", complaints);
			result.addObject("banner", banner);
			result.addObject("RequestURI", "complaint/referee/list.do");

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
}
