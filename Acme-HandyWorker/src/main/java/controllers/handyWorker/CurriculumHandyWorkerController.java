
package controllers.handyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.CurriculumService;
import controllers.AbstractController;
import domain.Actor;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum/handyWorker")
public class CurriculumHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	// Display ----------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Curriculum curriculum;
		Actor actor;
		Boolean security;

		security = this.curriculumService.securityCurriculum();

		if (security) {
			actor = this.actorService.findByPrincipal();

			curriculum = this.curriculumService.findByHandyWorkerId(actor.getId());

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("curriculum/display");
			result.addObject("curriculum", curriculum);
			result.addObject("banner", banner);
			result.addObject("requestURI", "curriculum/handyWorker/display.do");
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;

	}

	//Auxuliar

	@RequestMapping(value = "/aux", method = RequestMethod.GET)
	public ModelAndView aux() {
		ModelAndView result;
		Boolean existCurriculum;

		existCurriculum = this.curriculumService.securityCurriculum();

		if (existCurriculum)
			result = new ModelAndView("redirect:display.do");
		else
			result = new ModelAndView("redirect:/personalRecord/handyWorker/create.do");

		return result;
	}
}
