package gov.ed.fsa.drts.util;

import gov.ed.fsa.drts.object.DRTSGroup;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;

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
		String result = "/index";
		DRTSGroup admin_group = new DRTSGroup("admin", null);
		int index = -1;
		
		index = user_groups.indexOf(admin_group);
		
		if(index != -1)
		{
			result = "/admin";
		}
		
		return result;
	}
}
