
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.NoteService;
import services.ReportService;
import domain.Note;

@Controller
@RequestMapping("/note/customer,handyWorker,referee")
public class NoteCustomeHandyWorkerRefereerController {

	// Services

	@Autowired
	private NoteService				noteService;

	@Autowired
	private ReportService			reportService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;


	// Methods

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId) {
		final ModelAndView result;
		final Collection<Note> notes;
		Integer complaintId;
		final Boolean security = this.reportService.ReportSecurity(reportId);
		if (security) {
			notes = this.noteService.findByReportId(reportId);
			complaintId = this.reportService.findOne(reportId).getComplaint().getId();

			final String banner = this.configurationService.findConfiguration().getBanner();

			result = new ModelAndView("note/list");
			result.addObject("notes", notes);
			result.addObject("reportId", reportId);
			result.addObject("complaintId", complaintId);
			result.addObject("banner", banner);
			result.addObject("requestURI", "note/customer,handyWorker,referee/list.do");
			result.addObject("autoridad", this.actorService.authorityAuthenticated());
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/writeNew", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId) {
		final ModelAndView result;
		final Note note;
		Boolean security;

		security = this.reportService.ReportSecurity(reportId);

		if (security) {
			note = this.noteService.create(reportId);
			result = this.createEditModelAndView(note);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}
	@RequestMapping(value = "/writeComment", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		final ModelAndView result;
		final Note note;
		Boolean security;

		security = this.noteService.NoteSecurity(noteId);

		if (security) {
			note = this.noteService.findOne(noteId);
			result = this.createEditModelAndView(note);
		} else
			result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/writeComment", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:list.do?reportId=" + note.getReport().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String messageCode) {
		ModelAndView result;
		Integer reportId;

		reportId = note.getReport().getId();

		final String banner = this.configurationService.findConfiguration().getBanner();

		result = new ModelAndView("note/write");
		result.addObject("note", note);
		result.addObject("reportId", reportId);
		result.addObject("banner", banner);
		result.addObject("messageError", messageCode);

		return result;
	}
}
