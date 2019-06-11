
package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int boxId) {
		ModelAndView result;
		final Collection<Message> messages;
		Boolean security;

		security = this.boxService.boxSecurity(boxId);

		if (security) {
			messages = this.messageService.findMessagesByBoxId(boxId);

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("banner", banner);
			result.addObject("boxId", boxId);
			result.addObject("requestURI", "message/actor/list.do");

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId, @RequestParam final int boxId) {
		ModelAndView result;
		final Message message1;
		final Boolean security;

		security = this.messageService.securityMessage(boxId);

		if (security) {
			final String banner = this.configurationService.findConfiguration().getBanner();

			message1 = this.messageService.findOne(messageId);

			result = new ModelAndView("message/display");
			result.addObject("message1", message1);
			result.addObject("banner", banner);
			result.addObject("requestURI", "message/actor/display.do");

		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		final ModelAndView result;

		final Message message2 = this.messageService.create(actorId);

		message2.setSpam(false);

		result = this.createEditModelAndView(message2);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute @Valid final Message message2, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(message2);
		else
			try {
				this.messageService.save(message2);
				result = new ModelAndView("redirect:/box/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message2, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message message1, final BindingResult binding) {
		ModelAndView result;

		try {

			this.messageService.delete(message1);
			result = new ModelAndView("redirect:/box/actor/list.do");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(message1, "message.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message2) {
		final ModelAndView result;
		result = this.createEditModelAndView(message2, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message2, final String errorText) {
		final ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("message/create");
		result.addObject("messageError", errorText);
		result.addObject("message", message2);
		result.addObject("banner", banner);

		return result;
	}
}
