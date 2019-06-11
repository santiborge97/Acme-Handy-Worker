
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Collection<String>	spamWord;
	private Collection<String>	creditCardMakes;
	private double				vatTax;
	private String				countryCode;
	private int					finderTime;
	private int					finderResult;
	private String				banner;
	private String				welcomeMessage;
	private String				welcomeMessageEs;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getSpamWord() {
		return this.spamWord;
	}
	public void setSpamWord(final Collection<String> spamWord) {
		this.spamWord = spamWord;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}
	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@Min(0)
	@Max(100)
	public double getVatTax() {
		return this.vatTax;
	}
	public void setVatTax(final Double vatTax) {
		this.vatTax = vatTax;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Min(1)
	@Max(24)
	public int getFinderTime() {
		return this.finderTime;
	}
	public void setFinderTime(final Integer finderTime) {
		this.finderTime = finderTime;
	}

	@Min(10)
	@Max(100)
	public int getFinderResult() {
		return this.finderResult;
	}
	public void setFinderResult(final Integer finderResult) {
		this.finderResult = finderResult;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}
}
