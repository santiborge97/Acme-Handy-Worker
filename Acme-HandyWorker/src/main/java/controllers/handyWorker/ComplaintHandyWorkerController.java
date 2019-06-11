
package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ComplaintService;
import services.ConfigurationService;
import services.HandyWorkerService;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController {

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

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
				result.addObject("autoridad", "handyWorker");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("complaint/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "handyWorker");
			result.addObject("banner", banner);

		}

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Complaint> complaints;
		final UserAccount ua = LoginService.getPrincipal();

		complaints = this.complaintService.findAllComplaintsOfHandyWorker(this.handyWorkerService.findByUserAccount(ua).getId());

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("RequestURI", "complaint/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {
		ModelAndView result;

		Complaint complaint;
		//TODO: Controlar mirones

		complaint = this.complaintService.findOne(complaintId);
		Assert.notNull(complaint);
		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("messageError", messageCode);

		return result;
	}
}
