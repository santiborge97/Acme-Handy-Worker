
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.RefereeRepository;
import domain.Referee;

@Component
@Transactional
public class StringToRefereeConverter implements Converter<String, Referee> {

	@Autowired
	private RefereeRepository	refereeRepository;


	@Override
	public Referee convert(final String text) {
		Referee result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.refereeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
