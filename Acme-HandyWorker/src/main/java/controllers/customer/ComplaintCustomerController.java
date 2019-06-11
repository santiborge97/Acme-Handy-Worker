
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ComplaintService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController {

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomerService			customerService;

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
				result.addObject("autoridad", "customer");
			} else
				result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("complaint/display");
			result.addObject("noReport", "error");
			result.addObject("autoridad", "customer");
			result.addObject("banner", banner);

		}

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Complaint> complaints;
		final UserAccount ua = LoginService.getPrincipal();

		complaints = this.complaintService.findAllComplaintsOfCustomer(this.customerService.findByUserAccount(ua).getId());

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("banner", banner);
		result.addObject("RequestURI", "complaint/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {
		ModelAndView result;

		Complaint complaint;
		//TODO: Controlar mirones

		complaint = this.complaintService.findOne(complaintId);
		Assert.notNull(complaint);
		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		FixUpTask fixUpTask;
		Complaint complaint;
		Boolean security;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		security = this.fixUpTaskService.fixUpTaskCustomerSecurity(fixUpTaskId);

		if (security) {
			complaint = this.complaintService.create();
			complaint.setFixUpTask(fixUpTask);
			result = this.createEditModelAndView(complaint, null);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint, null);
		else
			try {
				this.complaintService.saveNewComplaint(complaint, complaint.getFixUpTask().getId());
				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}
}
