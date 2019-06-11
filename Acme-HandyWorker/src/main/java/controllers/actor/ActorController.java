
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services-----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();

		Collection<Customer> customers;
		Collection<HandyWorker> handyWorkers;
		Collection<Administrator> administrators;
		Collection<Referee> referees;
		Collection<Sponsor> sponsors;

		customers = this.customerService.findAll();
		handyWorkers = this.handyWorkerService.findAll();
		administrators = this.administratorService.findAll();
		referees = this.refereeService.findAll();
		sponsors = this.sponsorService.findAll();

		if (customers.contains(actor))
			customers.remove(actor);
		else if (handyWorkers.contains(actor))
			handyWorkers.remove(actor);
		else if (administrators.contains(actor))
			administrators.remove(actor);
		else if (referees.contains(actor))
			referees.remove(actor);
		else if (sponsors.contains(actor))
			sponsors.remove(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/list");
		result.addObject("customers", customers);
		result.addObject("handyWorkers", handyWorkers);
		result.addObject("administrators", administrators);
		result.addObject("referees", referees);
		result.addObject("sponsors", sponsors);
		result.addObject("banner", banner);

		result.addObject("requestURI", "actor/list.do");

		return result;

	}

}
