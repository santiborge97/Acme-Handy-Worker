
package controllers.handyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.CustomerService;
import services.FinderService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUpTaskHandyWorkerController extends AbstractController {

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private CustomerService			customerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Finder finder;

		final Collection<FixUpTask> fixUpTasks;

		finder = this.finderService.create();
		fixUpTasks = this.fixUpTaskService.findAll();
		final Double vatTax = this.configurationService.findConfiguration().getVatTax();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("finder", finder);
		result.addObject("pagesize", 5);
		result.addObject("requestURI", "fixUpTask/handyWorker/list.do");
		result.addObject("requestAction", "finderFixUpTask/handyworker/filter.do");
		result.addObject("vatTax", vatTax);
		result.addObject("banner", banner);
		return result;

	}

	@RequestMapping(value = "/showMeFixUpCustomer", method = RequestMethod.GET)
	public ModelAndView showMe(@RequestParam final int customerId) {
		final ModelAndView result;
		final Collection<FixUpTask> fixUpTasks;
		final Finder finder;
		Customer c;

		c = this.customerService.findOne(customerId);
		finder = this.finderService.create();

		fixUpTasks = c.getFixUpTasks();

		final Double vatTax = this.configurationService.findConfiguration().getVatTax();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/handyWorker/showMeFixUpCustomer.do");
		result.addObject("requestAction", "finderFixUpTask/handyworker/filter.do");
		result.addObject("pagesize", 5);
		result.addObject("vatTax", vatTax);
		result.addObject("banner", banner);
		result.addObject("finder", finder);
		result.addObject("language", LocaleContextHolder.getLocale().getLanguage());

		return result;
	}
}
