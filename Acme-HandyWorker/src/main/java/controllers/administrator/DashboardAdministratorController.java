
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.ReportService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("dashboard/display");

		final Collection<Double> statsOfFixUpTasksPerCustomer = this.customerService.statsOfFixUpTasksPerCustomer();
		result.addObject("statsOfFixUpTasksPerCustomer", statsOfFixUpTasksPerCustomer);

		final Collection<Double> statsOfApplicationsPerFixUpTask = this.fixUpTaskService.statsOfApplicationsPerFixUpTask();
		result.addObject("statsOfApplicationsPerFixUpTask", statsOfApplicationsPerFixUpTask);

		final Collection<Double> statsOfMaximumPricePerFixUpTask = this.fixUpTaskService.statsOfMaximumPricePerFixUpTask();
		result.addObject("statsOfMaximumPricePerFixUpTask", statsOfMaximumPricePerFixUpTask);

		final Collection<Double> statsOfOfferedPricePerApplication = this.applicationService.statsOfOfferedPricePerApplication();
		result.addObject("statsOfOfferedPricePerApplication", statsOfOfferedPricePerApplication);

		final Double ratioOfApplicationsPending = this.applicationService.ratioOfApplicationsPending();
		result.addObject("ratioOfApplicationsPending", ratioOfApplicationsPending);

		final Double ratioOfApplicationsAccepted = this.applicationService.ratioOfApplicationsAccepted();
		result.addObject("ratioOfApplicationsAccepted", ratioOfApplicationsAccepted);

		final Double ratioOfApplicationsRejected = this.applicationService.ratioOfApplicationsRejected();
		result.addObject("ratioOfApplicationsRejected", ratioOfApplicationsRejected);

		final Double ratioOfApplicationsPendingElapsedPeriod = this.applicationService.ratioOfApplicationsPendingElapsedPeriod();
		result.addObject("ratioOfApplicationsPendingElapsedPeriod", ratioOfApplicationsPendingElapsedPeriod);

		final Collection<Customer> customers = this.customerService.customersTenPerCentMore();
		result.addObject("customers", customers);

		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.handyWorkersTenPerCentMore();
		result.addObject("handyWorkers", handyWorkers);
		//b
		final Collection<Double> statsOfComplaintsPerFixUpTask = this.fixUpTaskService.statsOfComplaintsPerFixUpTask();
		result.addObject("statsOfComplaintsPerFixUpTask", statsOfComplaintsPerFixUpTask);

		final Collection<Double> statsOfNotesPerReport = this.reportService.statsOfNotesPerReport();
		result.addObject("statsOfNotesPerReport", statsOfNotesPerReport);

		final Double ratioOfFixUpTasksWithComplaint = this.fixUpTaskService.ratioOfFixUpTasksWithComplaint();
		result.addObject("ratioOfFixUpTasksWithComplaint", ratioOfFixUpTasksWithComplaint);

		final Collection<Customer> top3customers = this.customerService.topThreeCustomersComplaints();
		result.addObject("top3customers", top3customers);

		final Collection<HandyWorker> top3handyWorkers = this.handyWorkerService.topThreeHandyWorkersComplaints();
		result.addObject("top3handyWorkers", top3handyWorkers);

		result.addObject("banner", banner);
		result.addObject("requestURI", "dashboard/administrator/display.do");

		return result;

	}
}
