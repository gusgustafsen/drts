package gov.ed.fsa.drts.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;

public class Utils {

	public static String getUserHomePage(String user_id)
	{
		if(isUserInGroup(user_id, ApplicationProperties.GROUP_ADMIN.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_ADMIN.getStringValue();
		}
		else if(isUserInGroup(user_id, ApplicationProperties.GROUP_DRT.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_DRT.getStringValue();
		}
		else if(isUserInGroup(user_id, ApplicationProperties.GROUP_REPORTER.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_REPORTER.getStringValue();
		}
		else if(isUserInGroup(user_id, ApplicationProperties.GROUP_REQUESTOR.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_REQUESTOR.getStringValue();
		}
		else if(isUserInGroup(user_id, ApplicationProperties.GROUP_SME.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_SME.getStringValue();
		}
		else
		{
			return ApplicationProperties.PAGE_ERROR_UNAUTHORIZED.getStringValue();
		}
	}

	public static boolean isStringEmpty(String item)
	{
		boolean result = true;
		
		if(item != null)
		{
			if(item.length() > 0)
			{
				result = false;
			}
		}
		
		return result;
	}

	public static String replaceAll(String item, Map<String, String> tokens)
	{
		if(item != null)
		{
			String pattern_string = "@(" + StringUtils.join(tokens.keySet(), "|") + ")@";
			
			Pattern pattern = Pattern.compile(pattern_string);
			Matcher matcher = pattern.matcher(item);
			
			StringBuffer sb = new StringBuffer();
			
			while(matcher.find())
			{
				if(tokens.get(matcher.group(1)) != null)
				{
					matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
				}
			}
			
			matcher.appendTail(sb);
			
			return sb.toString();
		}
		
		return item;
	}

	public static boolean isUserInGroup(String user_id, String group_name)
	{
		ProcessEngine process_engine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identity_service = process_engine.getIdentityService();
		User user = null;
		
		user = identity_service.createUserQuery().userId(user_id).memberOfGroup(group_name).singleResult();
		
		if(user != null)
		{
			return true;
		}
		
		return false;
	}

	public static boolean areStringsEqual(String string1, String string2)
	{
		if(isStringEmpty(string1) == true && isStringEmpty(string2) == true)
		{
			return true;
		}
		else if((isStringEmpty(string1) == true && isStringEmpty(string2) == false) ||
				(isStringEmpty(string1) == false && isStringEmpty(string2) == true))
		{
			return false;
		}
		else
		{
			return string1.equalsIgnoreCase(string2);
		}
	}
}
