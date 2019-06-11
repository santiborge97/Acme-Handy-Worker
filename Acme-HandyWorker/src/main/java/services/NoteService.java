
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class NoteService {

	// Managed Repository ------------------------

	@Autowired
	private NoteRepository		noteRepository;

	// Suporting services ------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private ReportService		reportService;

	@Autowired
	private ActorService		actorService;


	public Boolean NoteSecurity(final int noteId) {
		Boolean res = false;
		HandyWorker owner2 = null;

		final Note note = this.noteRepository.findOne(noteId);

		final Customer owner = this.customerService.findByComplaintId(note.getReport().getComplaint().getId());

		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);

		if (actor.getUserAccount().getAuthorities().contains(authority)) {

			final Collection<Application> application = note.getReport().getComplaint().getFixUpTask().getApplications();
			for (final Application application2 : application)
				if (application2.getStatus().equals("ACCEPTED") && this.handyWorkerService.findByPrincipal().getApplications().contains(application2)) {
					owner2 = this.handyWorkerService.findHandyWorkerByApplicationId(application2.getId());
					break;
				}
		}
		final Referee owner3 = note.getReport().getReferee();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner) || login.equals(owner2) || login.equals(owner3))
			res = true;

		return res;
	}

	// Simple CRUD methods -----------------------

	public Note create(final int reportId) {

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Note result;

		result = new Note();

		final Date moment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(moment);

		final Report report = this.reportService.findOne(reportId);
		Assert.isTrue(report.getFinalMode());
		result.setReport(report);

		result.setCommentCustomer(null);
		result.setCommentHandyWorker(null);
		result.setCommentReferee(null);

		return result;
	}
	public Collection<Note> findAll() {

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Collection<Note> result;

		result = this.noteRepository.findAll();

		Assert.isTrue(result.size() != 0);

		return result;
	}

	public Note findOne(final int noteId) {

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Note result;

		result = this.noteRepository.findOne(noteId);

		return result;
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		Note result = null;

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		if (note.getId() != 0) {

			final Note find = this.noteRepository.findOne(note.getId());

			final String notehw = note.getCommentHandyWorker();
			final String findhw = find.getCommentHandyWorker();
			final String notere = note.getCommentReferee();
			final String findre = find.getCommentReferee();
			final String notecu = note.getCommentCustomer();
			final String findcu = find.getCommentCustomer();

			if (LoginService.getPrincipal().getAuthorities().contains(authority1)) {
				Assert.isTrue(((findhw == null && notehw == null) || (findhw.equals(notehw))) && ((findre == null && notere == null) || (findre.equals(notere))));
				if (!findcu.isEmpty())
					Assert.isTrue(findcu.equals(notecu));
			} else if (LoginService.getPrincipal().getAuthorities().contains(authority2)) {
				Assert.isTrue(((findhw == null && notehw == null) || (findhw.equals(notehw))) && ((findcu == null && notecu == null) || (findcu.equals(notecu))));
				if (!findre.isEmpty())
					Assert.isTrue(findre.equals(notere));
			} else if (LoginService.getPrincipal().getAuthorities().contains(authority3)) {
				Assert.isTrue(((findcu == null && notecu == null) || (findcu.equals(notecu))) && ((findre == null && notere == null) || (findre.equals(notere))));
				if (!findhw.isEmpty())
					Assert.isTrue(findhw.equals(notehw));
			}

			result = this.noteRepository.save(note);

		} else if (note.getId() == 0) {

			if (LoginService.getPrincipal().getAuthorities().contains(authority1)) {
				Assert.isTrue(!note.getCommentCustomer().isEmpty());
				Assert.isTrue(note.getCommentHandyWorker().isEmpty());
				Assert.isTrue(note.getCommentReferee().isEmpty());
			} else if (LoginService.getPrincipal().getAuthorities().contains(authority2)) {
				Assert.isTrue(note.getCommentCustomer().isEmpty());
				Assert.isTrue(!note.getCommentReferee().isEmpty());
				Assert.isTrue(note.getCommentHandyWorker().isEmpty());
			} else if (LoginService.getPrincipal().getAuthorities().contains(authority3)) {
				Assert.isTrue(note.getCommentCustomer().isEmpty());
				Assert.isTrue(note.getCommentReferee().isEmpty());
				Assert.isTrue(!note.getCommentHandyWorker().isEmpty());
			}

			result = this.noteRepository.save(note);

			final Report report = this.reportService.findOne(result.getReport().getId());
			final Collection<Note> notes = report.getNotes();
			notes.add(result);
			report.setNotes(notes);
			this.reportService.save(report);

		}

		return result;

	}
	// Other business methods -----------------------

	public Collection<Note> findByReportId(final int reportId) {
		Assert.notNull(reportId);

		final Collection<Note> result = this.noteRepository.findByReportId(reportId);

		Assert.notNull(result);

		return result;
	}
}
