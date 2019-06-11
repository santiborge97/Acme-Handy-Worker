
package controllers.customer;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CategoryService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private WarrantyService			warrantyService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ApplicationService		applicationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<FixUpTask> fixUpTasks;
		int idCustomer;
		Customer c;

		idCustomer = this.actorService.findByPrincipal().getId();

		c = this.customerService.findOne(idCustomer);

		fixUpTasks = c.getFixUpTasks();

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");
		result.addObject("pagesize", 5);
		result.addObject("vatTax", vatTax);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.create();
		result = this.createEditModelAndView(fixUpTask, null);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		Boolean security;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		security = this.fixUpTaskService.fixUpTaskCustomerSecurity(fixUpTaskId);
		final boolean NothasApplicationAccepted = this.applicationService.countApplicationAcceptedByFixUpTask(fixUpTaskId);
		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		final boolean isNotStarted = fixUpTask.getStartDate().compareTo(currentMoment) >= 0;

		if (security && NothasApplicationAccepted && isNotStarted)
			result = this.createEditModelAndView(fixUpTask, null);
		else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (fixUpTask.getApplications().contains(null))
			fixUpTask.getApplications().remove(null);

		if (fixUpTask.getComplaints().contains(null))
			fixUpTask.getComplaints().remove(null);

		if (binding.hasErrors())
			result = this.createEditModelAndView(fixUpTask, null);
		else
			try {
				this.fixUpTaskService.save(fixUpTask);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (fixUpTask.getApplications().contains(null))
			fixUpTask.getApplications().clear();

		if (fixUpTask.getComplaints().contains(null))
			fixUpTask.getComplaints().clear();

		try {
			this.fixUpTaskService.delete(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode) {
		final ModelAndView result;

		Collection<Category> categories;
		Collection<Warranty> warranties;
		final Collection<Application> applications;
		final Collection<Complaint> complaints;

		categories = this.categoryService.findAll();
		warranties = this.warrantyService.findWarrantiesFinalMode();
		applications = fixUpTask.getApplications();
		complaints = fixUpTask.getComplaints();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);
		result.addObject("applications", applications);
		result.addObject("complaints", complaints);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		if (!applications.isEmpty())
			result.addObject("hasApplications", true);

		return result;
	}

}
