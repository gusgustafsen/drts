package gov.ed.fsa.drts.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

/** Handles application messages */

public enum Messages {
	TRY_AGAIN,

	EMAIL_SENT,

	EMAIL_SEND_ERROR,

	EMAIL_ADDRESS_MISSING,

	TIMEOUT;

	/** Log4j logger */
	private static final Logger logger = Logger.getLogger(Messages.class);

	/** Properties file name */
	private static final String PROPERTIES_FILE_NAME = "messages";

	/** Resource bundle containing properties */
	private ResourceBundle msgBundle = null;

	/** Language locale */
	private Locale locale = null;

	/** Delimiter for list property values */
	private static final String LIST_DELIMITER = "\\|";

	/** Delimiter for mapping */
	private static final String MAP_DELIMITER = "=";

	public String getStringValue() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Locale newLocale = context == null ? locale == null ? Locale.US : locale
				: context.getViewRoot().getLocale();

		if (msgBundle == null || !locale.equals(newLocale)) {
			locale = newLocale;
			msgBundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, locale);
		}

		String stringValue;

		try {
			stringValue = msgBundle.getString(name().toLowerCase().replace('_', '.'));
		} catch (final MissingResourceException mre) {
			stringValue = "";
			logger.error("Message properties does not contain the following resource: " + name());
		}

		return stringValue;
	}

	public List<String> getListValue() {
		final String tempString = getStringValue();
		final List<String> listValue = new ArrayList<String>();
		listValue.addAll(Arrays.asList(tempString.split(LIST_DELIMITER)));

		return listValue;
	}

	public LinkedHashMap<String, String> getLinkedMapValue() {
		final LinkedHashMap<String, String> mapValue = new LinkedHashMap<String, String>();
		final List<String> tempList = getListValue();
		final Iterator<String> it = tempList.iterator();
		String tempString;

		while (it.hasNext()) {
			tempString = it.next();
			final String[] mapping = tempString.split(MAP_DELIMITER);
			mapValue.put(mapping[0], mapping[1]);
		}

		return mapValue;
	}
}
