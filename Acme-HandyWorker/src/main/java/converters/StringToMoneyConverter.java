
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Money;

@Component
@Transactional
public class StringToMoneyConverter implements Converter<String, Money> {

	@Override
	public Money convert(final String text) {
		Money result;
		final String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("//|");
				result = new Money();
				result.setAmount(Double.valueOf(parts[0]));
				result.setCurrency(parts[1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}
