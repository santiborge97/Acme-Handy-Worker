
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Money;

@Component
@Transactional
public class MoneyToStringConverter implements Converter<Money, String> {

	@Override
	public String convert(final Money money) {
		String result;
		StringBuilder builder;

		if (money == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(Double.toString(money.getAmount()));
				builder.append("|");
				builder.append(money.getCurrency());
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}
