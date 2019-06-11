
package controllers.administrator;

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
import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyAdministratorController {

	// Services

	@Autowired
	private WarrantyService			warrantyService;

	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int warrantyId) {

		ModelAndView result;
		final Warranty warranty;

		final String banner = this.configurationService.findConfiguration().getBanner();

		try {

			warranty = this.warrantyService.findOne(warrantyId);

			result = new ModelAndView("administrator/displayWarranty");
			result.addObject("warranty", warranty);
			result.addObject("banner", banner);

		} catch (final Throwable oops) {

			result = new ModelAndView("administrator/displayWarranty");
			result.addObject("noReport", "error");
			result.addObject("banner", banner);

		}

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Warranty> warranties;

		warranties = this.warrantyService.findAll();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/listWarranty");
		result.addObject("warranties", warranties);
		result.addObject("banner", banner);
		result.addObject("requestURI", "warranty/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.create();

		result = this.createEditModelAndView(warranty);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);
		Boolean security;

		security = warranty.getFinalMode();
		
		if (!security) {
			result = this.createEditModelAndView(warranty);
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(warranty);
		else
			try {
				this.warrantyService.save(warranty);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warranty, "warranty.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty) {

		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Warranty warranty, final String messageCode) {

		ModelAndView result;

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("administrator/editWarranty");
		result.addObject("warranty", warranty);
		result.addObject("messageError", messageCode);
		result.addObject("banner", banner);

		return result;
	}

}
