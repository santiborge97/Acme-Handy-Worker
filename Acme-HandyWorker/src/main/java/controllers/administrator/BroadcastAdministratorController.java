
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Message;

@Controller
@RequestMapping("/broadcast/administrator")
public class BroadcastAdministratorController extends AbstractController {

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Message message2 = this.messageService.create2();

		result = this.createEditModelAndView(message2);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message2, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(message2);
		else
			try {
				this.messageService.broadcastSystem(message2);
				this.messageService.save2(message2);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message2, "message.commit.error");
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

		result = new ModelAndView("broadcast/create");
		result.addObject("messageError", errorText);
		result.addObject("message", message2);
		result.addObject("banner", banner);

		return result;
	}
}
