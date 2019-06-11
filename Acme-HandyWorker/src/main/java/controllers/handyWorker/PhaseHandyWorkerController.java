
package controllers.handyWorker;

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

import services.ConfigurationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyWorker")
public class PhaseHandyWorkerController extends AbstractController {

	@Autowired
	private PhaseService			phaseService;

	@Autowired
	private FixUpTaskService		fixUpTasksService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Collection<FixUpTask> acceptedFixUpTasks;

		acceptedFixUpTasks = this.fixUpTasksService.findAcceptedFixUpTasks();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("phase/list");
		result.addObject("fixUpTasks", acceptedFixUpTasks);
		result.addObject("banner", banner);
		result.addObject("RequestURI", "phase/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/workplan", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
		final ModelAndView result;
		//final UserAccount userAccount = LoginService.getPrincipal();
		//		final FixUpTask fixUpTask = this.fixUpTasksService.findOne(fixUpTaskId);
		//
		//		final Collection<Application> applications = this.handyWorkerService.findByUserAccount(userAccount).getApplications();

		final String banner = this.configurationService.findConfiguration().getBanner();

		final boolean isHisHW = this.phaseService.phaseHandyWorkerSecurity(fixUpTaskId);

		if (isHisHW) {
			final Collection<Phase> phases;

			phases = this.phaseService.findPhasesByFixUpTaskId(fixUpTaskId);

			result = new ModelAndView("phase/workplan");
			result.addObject("phases", phases);
			result.addObject("banner", banner);
			result.addObject("RequestURI", "phase/handyWorker/workplan.do");
		} else {
			result = new ModelAndView("welcome/index");
			result.addObject("banner", banner);
		}

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Phase phase;
		FixUpTask fixUpTask;

		phase = this.phaseService.create();
		fixUpTask = this.fixUpTasksService.findOne(fixUpTaskId);
		phase.setFixUpTask(fixUpTask);

		final boolean isHisHW = this.phaseService.phaseHandyWorkerSecurity(fixUpTaskId);

		if (isHisHW)
			result = this.createEditModelAndView(phase, null);
		else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int phaseId) {
		final ModelAndView result;
		final Phase phase;

		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);

		final String banner = this.configurationService.findConfiguration().getBanner();

		final int fixUpTaskId = phase.getFixUpTask().getId();

		final boolean isHisHW = this.phaseService.phaseHandyWorkerSecurity(fixUpTaskId);

		if (isHisHW)
			result = this.createEditModelAndView(phase, null);
		else {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("banner", banner);
		}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		final ModelAndView result;

		final FixUpTask fixUpTask;

		fixUpTask = phase.getFixUpTask();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("phase/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("phase", phase);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}

	@RequestMapping(value = "workplan/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase, null);
		else
			try {
				this.phaseService.save(phase);
				final int id = phase.getFixUpTask().getId();
				result = new ModelAndView("redirect:/phase/handyWorker/workplan.do?fixUpTaskId=" + id);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "workplan/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {
		ModelAndView result;

		try {
			final int id = phase.getFixUpTask().getId();
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:/phase/handyWorker/workplan.do?fixUpTaskId=" + id);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}
		return result;
	}
}
