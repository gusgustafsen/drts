package gov.ed.fsa.drts.object;

import gov.ed.fsa.drts.util.Utils;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.User;

public class DRTSUser {

	private User activiti_user;
	private List<DRTSGroup> groups;
	private String home_page;
	
	public DRTSUser(){}
	
	public DRTSUser(User activiti_user, List<DRTSGroup> groups, String home_page)
	{
		this.activiti_user = activiti_user;
		this.groups = groups;
		this.home_page = home_page;
	}
	
	public String getId()
	{
		return this.activiti_user == null ? null : this.activiti_user.getId();
	}

	public User getActivitiUser()
	{
		return activiti_user;
	}

	public void setActivitiUser(User activiti_user)
	{
		this.activiti_user = activiti_user;
	}

	public List<DRTSGroup> getGroups()
	{
		return groups;
	}

	public void setGroups(List<DRTSGroup> groups)
	{
		this.groups = groups;
	}
	
	public void addGroup(DRTSGroup group)
	{
		if(this.groups == null)
		{
			this.groups = new ArrayList<DRTSGroup>();
		}
		
		this.groups.add(group);
	}
	
	public void addGroups(List<DRTSGroup> groups)
	{
		if(this.groups == null)
		{
			this.groups = new ArrayList<DRTSGroup>();
		}
		
		for(DRTSGroup group : groups)
		{
			this.groups.add(group);
		}
	}
	
	public String getHomePage()
	{
		return this.home_page;
	}
	
	public void setHomePage()
	{
		this.home_page = Utils.getUserHomePage(this.groups);
	}
	
	public boolean isAdmin()
	{
		int index = -1;
		DRTSGroup admin_group = new DRTSGroup("admin", null);
		
		index = this.groups.indexOf(admin_group);
		
		return index != -1 ? true : false;
	}
}
