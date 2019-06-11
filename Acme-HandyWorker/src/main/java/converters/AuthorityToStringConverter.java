
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import security.Authority;

@Component
@Transactional
public class AuthorityToStringConverter implements Converter<Authority, String> {

	@Override
	public String convert(final Authority authority) {
		String result;

		if (authority == null)
			result = null;
		else
			result = authority.getAuthority();

		return result;
	}

	//	@Override
	//	public String convert(final String text) {
	//		String result = null;
	//
	//		try {
	//			if (StringUtils.isEmpty(text))
	//				result = null;
	//			else if (text.equals(Authority.CUSTOMER))
	//				result = "CUSTOMER";
	//			else if (text.equals(Authority.ADMIN))
	//				result = "ADMIN";
	//			else if (text.equals(Authority.HANDYWORKER))
	//				result = "HANDYWORKER";
	//			else if (text.equals(Authority.REFEREE))
	//				result = "REFEREE";
	//			else if (text.equals(Authority.SPONSOR))
	//				result = "SPONSOR";
	//
	//		} catch (final Throwable oops) {
	//			throw new IllegalArgumentException(oops);
	//		}
	//
	//		return result;
	//	}
}
