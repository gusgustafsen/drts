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
		DRTSGroup group = null;
		int index = -1;
		
		group = new DRTSGroup(ApplicationProperties.GROUP_ADMIN.getStringValue(), null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return ApplicationProperties.HOME_PAGE_ADMIN.getStringValue();
		}
		
		group = new DRTSGroup(ApplicationProperties.GROUP_DRT.getStringValue(), null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return ApplicationProperties.HOME_PAGE_DRT.getStringValue();
		}
		
		group = new DRTSGroup(ApplicationProperties.GROUP_REPORTER.getStringValue(), null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return ApplicationProperties.HOME_PAGE_REPORTER.getStringValue();
		}
		
		group = new DRTSGroup(ApplicationProperties.GROUP_REQUESTOR.getStringValue(), null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return ApplicationProperties.HOME_PAGE_REQUESTOR.getStringValue();
		}
		
		group = new DRTSGroup(ApplicationProperties.GROUP_SME.getStringValue(), null);
		
		index = user_groups.indexOf(group);
		
		if(index != -1)
		{
			return ApplicationProperties.HOME_PAGE_SME.getStringValue();
		}
		
		return ApplicationProperties.PAGE_ERROR_UNAUTHORIZED.getStringValue();
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
		
		/**
		 * Map<String,String> tokens = new HashMap<String,String>();
tokens.put("cat", "Garfield");
tokens.put("beverage", "coffee");

String template = "%cat% really needs some %beverage%.";

// Create pattern of the format "%(cat|beverage)%"
String patternString = "%(" + StringUtils.join(tokens.keySet(), "|") + ")%";
Pattern pattern = Pattern.compile(patternString);
Matcher matcher = pattern.matcher(template);

StringBuffer sb = new StringBuffer();
while(matcher.find()) {
    matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
}
matcher.appendTail(sb);

System.out.println(sb.toString());
		 */
		
		/*
		String[] items_to_replace = null;
		String[] item_replacements = null;
		
		if(item != null && (replace_items != null && replace_items.size() > 0) && (replace_with != null && replace_with.size() > 0))
		{
			items_to_replace = replace_items.toArray(new String[replace_items.size()]);
			item_replacements = replace_with.toArray(new String[replace_with.size()]);
			
			StringUtils.replaceEach(item, items_to_replace, item_replacements);
		}*/
		
		return item;
	}
}
