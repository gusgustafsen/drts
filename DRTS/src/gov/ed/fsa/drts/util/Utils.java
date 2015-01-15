package gov.ed.fsa.drts.util;

import gov.ed.fsa.drts.object.DRTSGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.activiti.engine.identity.Group;
import org.apache.commons.lang3.StringUtils;

public class Utils {

	public static List<DRTSGroup> convertActivitiGroups(List<Group> activiti_groups)
	{
		List<DRTSGroup> result = null;
		
		if(activiti_groups != null && activiti_groups.size() > 0)
		{
			result = new ArrayList<DRTSGroup>();
			
			for(Group activiti_group : activiti_groups)
			{
				result.add(new DRTSGroup(activiti_group));
			}
		}
		
		return result;
	}
	
	public static String getUserHomePage(List<DRTSGroup> user_groups)
	{
		if(isUserInGroup(user_groups, ApplicationProperties.GROUP_ADMIN.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_ADMIN.getStringValue();
		}
		else if(isUserInGroup(user_groups, ApplicationProperties.GROUP_DRT.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_DRT.getStringValue();
		}
		else if(isUserInGroup(user_groups, ApplicationProperties.GROUP_REPORTER.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_REPORTER.getStringValue();
		}
		else if(isUserInGroup(user_groups, ApplicationProperties.GROUP_REQUESTOR.getStringValue()) == true)
		{
			return ApplicationProperties.HOME_PAGE_REQUESTOR.getStringValue();
		}
		else if(isUserInGroup(user_groups, ApplicationProperties.GROUP_SME.getStringValue()) == true)
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
		System.out.println("replace item: " + item);
		
		if(item != null)
		{
			String pattern_string = "@(" + StringUtils.join(tokens.keySet(), "|") + ")@";
			
			System.out.println("pattern_string" + pattern_string);
			
			Pattern pattern = Pattern.compile(pattern_string);
			Matcher matcher = pattern.matcher(item);
			
			StringBuffer sb = new StringBuffer();
			
			while(matcher.find())
			{
				System.out.println("match found" + matcher.group(1));
				
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

	public static boolean isUserInGroup(List<DRTSGroup> user_groups, String group_name)
	{
		DRTSGroup group = null;
		int index = -1;
		
		group = new DRTSGroup(group_name, null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return true;
		}
		
		return false;
	}
}
