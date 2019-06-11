
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String							ticker;

	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EducationRecord>		educationRecords;
	private PersonalRecord					personalRecord;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;

	private HandyWorker						handyWorker;


	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	public void addProfessionalRecord(final ProfessionalRecord professionalRecord) {
		this.professionalRecords.add(professionalRecord);
	}

	public void removeProfessionalRecord(final ProfessionalRecord professionalRecord) {
		this.professionalRecords.remove(professionalRecord);
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	public void addEducationRecord(final EducationRecord educationRecord) {
		this.educationRecords.add(educationRecord);
	}

	public void removeEducationRecord(final EducationRecord educationRecord) {
		this.educationRecords.remove(educationRecord);
	}

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	public void addEndorserRecord(final EndorserRecord endorserRecord) {
		this.endorserRecords.add(endorserRecord);
	}

	public void removeEndorserRecord(final EndorserRecord endorserRecord) {
		this.endorserRecords.remove(endorserRecord);
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}

	public void addMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.add(miscellaneousRecord);
	}

	public void removeMiscellaneousRecord(final MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.remove(miscellaneousRecord);
	}

	@Valid
	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
