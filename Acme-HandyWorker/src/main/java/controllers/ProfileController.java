/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/actor")
public class ProfileController extends AbstractController {

	// Services-----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ConfigurationService	configurationService;


	// Creation-------------------------------------------------------
	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		ModelAndView result;
		Customer customer;

		final String banner = this.configurationService.findConfiguration().getBanner();

		customer = this.customerService.create();
		result = new ModelAndView("actor/edit");
		result.addObject("customer", customer);
		result.addObject("authority", "customer");
		result.addObject("banner", banner);

		return result;
	}

	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {
		ModelAndView result;
		HandyWorker handyWorker;

		final String banner = this.configurationService.findConfiguration().getBanner();

		handyWorker = this.handyWorkerService.create();
		result = new ModelAndView("actor/edit");
		result.addObject("handyWorker", handyWorker);
		result.addObject("authority", "handyWorker");
		result.addObject("banner", banner);

		return result;
	}

	// Edition--------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		final Actor actor;

		final Actor principal = this.actorService.findByPrincipal();
		actor = this.actorService.findOne(principal.getId());
		Assert.isTrue(actor.equals(principal));

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);

		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.HANDYWORKER);

		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.ADMIN);

		final Authority authority4 = new Authority();
		authority4.setAuthority(Authority.REFEREE);

		String auth = null;
		if (actor.getUserAccount().getAuthorities().contains(authority2))
			auth = "handyWorker";
		else if (actor.getUserAccount().getAuthorities().contains(authority1))
			auth = "customer";
		else if (actor.getUserAccount().getAuthorities().contains(authority3))
			auth = "administrator";
		else if (actor.getUserAccount().getAuthorities().contains(authority4))
			auth = "referee";

		final String banner = this.configurationService.findConfiguration().getBanner();
		final String defaultCountry = this.configurationService.findConfiguration().getCountryCode();

		result = new ModelAndView("actor/edit");
		result.addObject(auth, actor);
		result.addObject("authority", auth);
		result.addObject("banner", banner);
		result.addObject("defaultCountry", defaultCountry);

		return result;
	}
	// Display--------------------------------------------------------
	@RequestMapping(value = "/displayPrincipal", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(this.actorService.findByPrincipal().getId());
		Assert.notNull(actor);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("banner", banner);

		return result;

	}

	// Display Customer from Fix-up Task--------------------------------------------------------
	@RequestMapping(value = "/handyworker/displayCustomer", method = RequestMethod.GET)
	public ModelAndView displayCustomer(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		final Customer customer;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		customer = this.customerService.findByTask(fixUpTask);
		Assert.notNull(customer);

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("actor/displayCustomer");
		result.addObject("actor", customer);
		result.addObject("banner", banner);

		return result;

	}

	// Ancillary methods --------------------------------------------------

}
