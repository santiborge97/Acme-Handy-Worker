
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository

	@Autowired
	private ReportRepository		reportRepository;

	// Suporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	public Boolean ReportSecurity(final int reportId) {
		Boolean res = false;
		HandyWorker owner2 = null;

		final Report report = this.reportRepository.findOne(reportId);

		final Customer owner = this.customerService.findByComplaintId(report.getComplaint().getId());

		final Actor actor = this.actorService.findByPrincipal();

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);

		if (actor.getUserAccount().getAuthorities().contains(authority)) {

			final Collection<Application> application = report.getComplaint().getFixUpTask().getApplications();

			for (final Application application2 : application)
				if (application2.getStatus().equals("ACCEPTED") && this.handyWorkerService.findByPrincipal().getApplications().contains(application2)) {
					owner2 = this.handyWorkerService.findHandyWorkerByApplicationId(application2.getId());
					break;
				}
		}

		final Referee owner3 = report.getReferee();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner) || login.equals(owner2) || login.equals(owner3))
			res = true;

		return res;
	}
	// Simple CRUD methods

	public Report create(@RequestParam final int complaintId) {

		final Referee actor = (Referee) this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		final Report result = new Report();

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(currentMoment);

		final Collection<Note> notes = new HashSet<>();
		result.setNotes(notes);
		final Collection<String> attachments = new HashSet<>();
		result.setAttachments(attachments);

		final Complaint complaint = this.complaintService.findOne(complaintId);
		result.setComplaint(complaint);

		result.setReferee(actor);

		return result;

	}

	public Collection<Report> findAll() {

		final Collection<Report> reports = this.reportRepository.findAll();

		Assert.notNull(reports);

		return reports;
	}

	public Report findOne(final int reportsID) {

		final Report report = this.reportRepository.findOne(reportsID);

		Assert.notNull(report);

		return report;

	}

	public Report save(final Report report) {

		Report result = null;
		Assert.notNull(report);

		final Date currentMoment = new Date(System.currentTimeMillis() - 1000);
		report.setMoment(currentMoment);

		if (report.getId() != 0) {

			final Report reportOld = this.reportRepository.findOne(report.getId());

			if (reportOld.getFinalMode()) {
				Assert.isTrue(report.getDescription().equals(reportOld.getDescription()));
				Assert.isTrue(report.getAttachments().equals(reportOld.getAttachments()));
				Assert.isTrue(report.getReferee().equals(reportOld.getReferee()));
				Assert.isTrue(report.getComplaint().equals(reportOld.getComplaint()));

				result = this.reportRepository.save(report);
				final Complaint complaint = this.complaintService.findOne(result.getComplaint().getId());
				complaint.setReport(result);

				this.complaintService.save(complaint);

			} else {

				this.checkAttachments(report.getAttachments());
				result = this.reportRepository.save(report);
				final Complaint complaint = this.complaintService.findOne(result.getComplaint().getId());
				complaint.setReport(result);

				this.complaintService.save(complaint);

			}

		} else {
			this.configurationService.spamContent(report.getDescription());

			this.checkAttachments(report.getAttachments());
			result = this.reportRepository.save(report);

			final Complaint complaint = this.complaintService.findOne(result.getComplaint().getId());
			complaint.setReport(result);

			this.complaintService.save(complaint);
		}

		return result;

	}

	public void delete(final Report report) {

		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(!report.getFinalMode());

		final Complaint complaint = this.complaintService.findOne(report.getComplaint().getId());
		complaint.setReport(null);

		this.complaintService.save(complaint);

		this.reportRepository.delete(report);
	}

	// Other business methods

	public Collection<Double> statsOfNotesPerReport() {

		final Collection<Double> result = this.reportRepository.statsOfNotesPerReport();
		Assert.notNull(result);
		return result;

	}

	public Report findOneByComplaintId(final int complaintId) {

		final Report report = this.reportRepository.findOneByComplaintId(complaintId);

		Assert.notNull(report);
		Assert.isTrue(report.getFinalMode());

		return report;

	}

	public Collection<Report> findAllByReferee(final int refereeId) {

		final Collection<Report> reports = this.reportRepository.findAllByReferee(refereeId);

		Assert.notNull(reports);

		return reports;
	}

	public Integer findComplaintByReportId(final int reportId) {

		Assert.notNull(reportId);

		final Integer result = this.findComplaintByReportId(reportId);

		return result;

	}

	public void checkAttachments(final Collection<String> attachments) {

		for (final String url : attachments) {
			final boolean checkUrl = url.matches("^http(s*)://(?:[a-zA-Z0-9-]+[\\.\\:])+[a-zA-Z0-9/]+$");
			Assert.isTrue(checkUrl);

		}
	}

}
