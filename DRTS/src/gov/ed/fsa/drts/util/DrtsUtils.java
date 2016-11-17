package gov.ed.fsa.drts.util;

import java.util.Collection;
import java.util.Map;

import oracle.net.aso.e;

public class DrtsUtils {

	/**
	 * @param name
	 * @return true is string is empty or not
	 */
	public static boolean isEmpty(final String name) {
		return name == null || name.isEmpty();
	}

	/**
	 * @param name
	 * @return true is collection is empty or not
	 */
	public static boolean isEmpty(final Collection<e> name) {
		return name == null || name.isEmpty();
	}

	/**
	 * @param name
	 * @return true is collection is empty or not
	 */
	public static boolean isEmpty(final Map<?, ?> name) {
		return name == null || name.isEmpty();
	}

	public static boolean isEqual(final String str1, final String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		}

		return isEmpty(str1) ? str2.equals(str1) : str1.equals(str2);
	}

	public static boolean isEqualIgnoreCase(final String str1, final String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		}

		return isEmpty(str1) ? str2.equals(str1) : str1.toLowerCase().equals(str2 == null ? null : str2.toLowerCase());
	}

	public static boolean isEqual(final Object o1, final Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}

		return o1 == null ? o2.equals(o1) : o1.equals(o2);
	}

}
