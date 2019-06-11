
package controllers.actor;

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

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box/actor")
public class BoxActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		final Collection<Box> boxes;
		Actor a;

		a = this.actorService.findByPrincipal();

		final String banner = this.configurationService.findConfiguration().getBanner();

		boxes = this.boxService.findAllBoxByActor(a.getId());

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("banner", banner);
		result.addObject("requestURI", "box/actor/list.do");
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Box box;

		box = this.boxService.create();

		box.setByDefault(false);

		result = this.createEditModelAndView(box);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int boxId) {
		ModelAndView result;
		Box box;
		Boolean security;

		security = this.boxService.boxSecurity(boxId);

		if (security) {
			box = this.boxService.findOne(boxId);
			Assert.notNull(box);

			result = this.createEditModelAndView(box);

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {

				this.boxService.save(box);
				result = new ModelAndView("redirect:../actor/list.do");
			} catch (final Throwable oops) {

				result = this.createEditModelAndView(box, "box.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Box box, final BindingResult binding) {
		ModelAndView result;

		try {
			this.boxService.delete(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(box, "box.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box) {

		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Box box, final String messageCode) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}

}
