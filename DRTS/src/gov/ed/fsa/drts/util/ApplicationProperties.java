package gov.ed.fsa.drts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public enum ApplicationProperties {

	SHIRO_PERMISSIONS_BY_GROUP(true, true),
	
	PROCESS_ID_DATA_REQUEST(true, false),
	
	DATE_FORMATS(true, true),
	
	GROUP_ADMIN(true, false),
	
	GROUP_SME(true, false),
	
	GROUP_DRT(true, false),
	
	GROUP_REPORTER(true, false),
	
	GROUP_REQUESTOR(true, false),
	
	GROUP_TYPE_SECURITY(true, false),
	
	GROUP_TYPE_GENERAL(true, false),
	
	HOME_PAGE_ADMIN(true, false),
	
	HOME_PAGE_SME(true, false),
	
	HOME_PAGE_DRT(true, false),
	
	HOME_PAGE_REPORTER(true, false),
	
	HOME_PAGE_REQUESTOR(true, false),
	
	PAGE_ERROR_APPLICATION(true, false),
	
	PAGE_ERROR_UNAUTHORIZED(true, false),
	
	USER_HEADER(true, false),
	
	USER_SESSION_HEADER(true, false),
	
	CONTEXT_ROOT(true, false),
	
	EMAIL_LABEL_NOTIFY_ADMIN_DRT_TO(true, false),
	
	EMAIL_LABEL_NOTIFY_ADMIN_DRT_CC(false, false),
	
	EMAIL_LABEL_NOTIFY_ADMIN_DRT_FROM(true, false),
	
	EMAIL_LABEL_NOTIFY_ADMIN_DRT_SUBJECT(true, false),
	
	EMAIL_LABEL_NOTIFY_ADMIN_DRT_CONTENT(true, false),
	
	EMAIL_NOTIFY_ADMIN_DRT_TO(true, false),
	
	EMAIL_NOTIFY_ADMIN_DRT_CC(false, false),
	
	EMAIL_NOTIFY_ADMIN_DRT_FROM(true, false),
	
	EMAIL_NOTIFY_ADMIN_DRT_SUBJECT(true, false),
	
	EMAIL_NOTIFY_ADMIN_DRT_CONTENT(true, false),
	
	EMAIL_LABEL_NOTIFY_REQUESTOR_TO(true, false),
	
	EMAIL_LABEL_NOTIFY_REQUESTOR_CC(false, false),
	
	EMAIL_LABEL_NOTIFY_REQUESTOR_FROM(true, false),
	
	EMAIL_LABEL_NOTIFY_REQUESTOR_SUBJECT(true, false),
	
	EMAIL_LABEL_NOTIFY_REQUESTOR_CONTENT(true, false),
	
	EMAIL_NOTIFY_REQUESTOR_CC(false, false),
	
	EMAIL_NOTIFY_REQUESTOR_FROM(true, false),
	
	EMAIL_NOTIFY_REQUESTOR_SUBJECT(true, false),
	
	EMAIL_NOTIFY_REQUESTOR_CONTENT(true, false),
	
	EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_TO(true, false),
	
	EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CC(false, false),
	
	EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_FROM(true, false),
	
	EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_SUBJECT(true, false),
	
	EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CONTENT(true, false),
	
	EMAIL_NOTIFY_SME_NEW_REQUEST_CC(false, false),
	
	EMAIL_NOTIFY_SME_NEW_REQUEST_FROM(true, false),
	
	EMAIL_NOTIFY_SME_NEW_REQUEST_SUBJECT(true, false),
	
	EMAIL_NOTIFY_SME_NEW_REQUEST_CONTENT(true, false),
	
	DATA_REQUEST_FIELD_ID(true, false),
	
	DATA_REQUEST_FIELD_TYPE(true, false),
	
	DATA_REQUEST_FIELD_CREATED_DATE(true, false),
	
	DATA_REQUEST_FIELD_CREATED_BY(true, false),
	
	DATA_REQUEST_FIELD_DESCRIPTION(true, false),
	
	DATA_REQUEST_FIELD_URGENT(true, false),
	
	DATA_REQUEST_FIELD_REQUESTOR_EMAIL(true, false),
	
	DATA_REQUEST_FIELD_NEW_REQUEST_REJECTED(true, false),
	
	DATA_REQUEST_FIELD_ASSIGNED_SME(true, false),
	
	DATA_REQUEST_FIELD_ASSIGNED_SME_EMAIL(true, false),
	
	DATA_REQUEST_FIELD_ASSIGNED_SME_FULL_NAME(true, false);
	
	private static final String LIST_DELIMITER = "\\|";
	
	private String string_value;
	private List<String> list_value;
	
	private Properties properties = null;

	private static final String SYSTEM_PROPERTIES_PREFIX = "gov.ed.fsa.drts.";

	public static final String PROPERTIES_FILE_NAME = "drts";
	
	private ApplicationProperties()
	{
		this(true, false);
	}
	
	private ApplicationProperties(final boolean required)
	{
		this(required, false);
	}
	
	private ApplicationProperties(final boolean required, final boolean list_format)
	{
		if(properties == null)
		{
			properties = new Properties();

			final ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);

			final Iterator<String> bundle_iterator = bundle.keySet().iterator();

			while(bundle_iterator.hasNext())
			{
				final String key = bundle_iterator.next();
				final String value = bundle.getString(key);
				properties.setProperty(key.toLowerCase().replace('.', '_'), value);
			}

			final Properties system_properties = System.getProperties();

			final Iterator<Entry<Object, Object>> system_properties_iterator = system_properties.entrySet().iterator();

			while(system_properties_iterator.hasNext())
			{
				final Entry<Object, Object> entry = system_properties_iterator.next();

				final String key = (String) entry.getKey();
				final String value = (String) entry.getValue();

				if(key.startsWith(SYSTEM_PROPERTIES_PREFIX) && !key.equals(SYSTEM_PROPERTIES_PREFIX))
				{
					final String key_to_use = key.substring(SYSTEM_PROPERTIES_PREFIX.length()).toLowerCase().replace('.', '_');
					properties.setProperty(key_to_use, value);
				}
			}
		}
	
		string_value = properties.getProperty(toString().toLowerCase());

		if(string_value != null && string_value.isEmpty())
		{
			string_value = null;
		}

		if(string_value == null)
		{
			list_value = Collections.emptyList();
			
			if(required)
			{
				throw new MissingResourceException("Property '" + toString() + "' not found in resource bundle '" + ApplicationProperties.PROPERTIES_FILE_NAME + "', required", ApplicationProperties.PROPERTIES_FILE_NAME, toString());
			}
		}
		else
		{
			string_value = string_value.trim();

			list_value = new ArrayList<String>();
			
			if(list_format)
			{
				list_value.addAll(Arrays.asList(string_value.split(LIST_DELIMITER)));
			}
			else
			{
				list_value.add(string_value);
			}
		}
	}
	
	public String getStringValue()
	{
		return string_value;
	}

	public boolean getBooleanValue()
	{
		return Boolean.parseBoolean(string_value);
	}

	public List<String> getListValue()
	{
		return list_value;
	}
}
