
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;
	private String		status;
	private Money		offeredPrice;
	private String		comment;
	private String		commentReject;
	private CreditCard	creditCard;

	//relationship
	private FixUpTask	fixUpTask;


	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "\\APENDING\\z|\\AREJECTED\\z|\\AACCEPTED\\z")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	@NotNull
	public Money getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public String getCommentReject() {
		return this.commentReject;
	}

	public void setCommentReject(final String comment) {
		this.commentReject = comment;
	}

	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public FixUpTask getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final FixUpTask fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

}
