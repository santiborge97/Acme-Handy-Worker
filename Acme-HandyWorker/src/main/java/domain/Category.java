
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		nameEn;
	private String		nameSp;

	private Category	parent;


	@Column(unique = true)
	@NotBlank
	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(final String nameEn) {
		this.nameEn = nameEn;
	}

	@Column(unique = true)
	@NotBlank
	public String getNameSp() {
		return this.nameSp;
	}

	public void setNameSp(final String nameSp) {
		this.nameSp = nameSp;
	}

	@ManyToOne(optional = true)
	@Valid
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
