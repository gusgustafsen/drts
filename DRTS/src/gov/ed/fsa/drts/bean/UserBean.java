package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 612941570092949641L;
	
	private transient ProcessEngine process_engine = null;
	private transient IdentityService identity_service = null;

	private String id = null;
	private String first_name = null;
	private String last_name = null;
	private String email = null;
	
	private User current_user = null;
	
	private List<String> user_current_main_groups = new ArrayList<String>();
	private List<String> user_current_general_groups = new ArrayList<String>();
	
	private String[] selected_main_groups;
	private String[] selected_general_groups;
	
	@PostConstruct
	private void init()
	{
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.identity_service = this.process_engine.getIdentityService();
			
			if(this.identity_service == null)
			{
				// TODO handle error
			}
		}
		else
		{
			// TODO handle error
		}
		
		this.current_user = (User) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsUser");
		
		if(this.current_user != null)
		{
			loadData();
		}
	}

	private void loadData()
	{
		this.first_name = this.current_user.getFirstName();
		this.last_name = this.current_user.getLastName();
		this.email = this.current_user.getEmail();
		
		loadGroups();
	}
	
	private void loadGroups()
	{
		List<Group> user_current_main_groups_list = identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue()).groupMember(this.current_user.getId()).list();
		
		for(Group group : user_current_main_groups_list)
		{
			user_current_main_groups.add(group.getName());
		}
		
		this.selected_main_groups = user_current_main_groups.toArray(new String[user_current_main_groups.size()]);
		
		List<Group> user_current_general_groups_list = identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_GENERAL.getStringValue()).groupMember(this.current_user.getId()).list();
		
		if(user_current_general_groups_list != null)
		{
			for(Group group : user_current_general_groups_list)
			{
				user_current_general_groups.add(group.getName());
			}
			
			this.selected_general_groups = user_current_general_groups.toArray(new String[user_current_general_groups.size()]);
		}
		else
		{
			this.selected_general_groups = new String[]{};
		}
	}
	
	private Collection<String> finalGroups()
	{
		Collection<String> final_groups = null;
		
		if((Arrays.equals(this.user_current_main_groups.toArray(), this.selected_main_groups) == false)
				|| (Arrays.equals(this.user_current_general_groups.toArray(), this.selected_general_groups) == false))
		{
			final_groups = new TreeSet<String>();
			
			final_groups.addAll(new ArrayList<String>(Arrays.asList(this.selected_main_groups)));
			final_groups.addAll(new ArrayList<String>(Arrays.asList(this.selected_general_groups)));
		}
		
		return final_groups;
	}
	
	public String save(boolean new_user)
	{
		Collection<String> final_groups = null;
		List<String> all_current_groups = null;
		User updated_user = null;
		
		if(new_user == true)
		{
			updated_user = this.identity_service.newUser(this.id);
		}
		else
		{
			updated_user = this.current_user;
		}
		
		updated_user.setFirstName(this.first_name);
		updated_user.setLastName(this.last_name);
		updated_user.setEmail(this.email);
		
		this.identity_service.saveUser(updated_user);
		
		final_groups = finalGroups();
		
		if(final_groups != null)
		{
			all_current_groups = new ArrayList<String>();
			
			all_current_groups.addAll(this.user_current_main_groups);
			all_current_groups.addAll(this.user_current_general_groups);
			
			for(String old_group : all_current_groups)
			{
				this.identity_service.deleteMembership(updated_user.getId(), old_group);
			}
			
			for(String new_group : final_groups)
			{
				this.identity_service.createMembership(updated_user.getId(), new_group);
			}
		}
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", null);
		
		return "/administration/userManagement.htm?faces-redirect=true";
	}
	
	public String cancel()
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", null);
		
		return "/administration/userManagement.htm?faces-redirect=true";
	}
	
	public List<String> getMainGroups()
	{
		List<String> result = null;
		List<Group> main_groups = identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue()).list();
		
		if(main_groups != null)
		{
			result = new ArrayList<String>();
			
			for(Group group : main_groups)
			{
				result.add(group.getName());
			}
		}
		
		return result;
	}
	
	public List<String> getGeneralGroups()
	{
		List<String> result = null;
		List<Group> general_groups = identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_GENERAL.getStringValue()).list();
		
		if(general_groups != null)
		{
			result = new ArrayList<String>();
			
			for(Group group : general_groups)
			{
				result.add(group.getName());
			}
		}
		
		return result;
	}
	
	@PreDestroy
	private void destroy()
	{
		
	}

	public String getId()
	{
		return (this.current_user == null ? this.id : this.current_user.getId());
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return this.first_name;
	}

	public void setFirstName(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLastName()
	{
		return this.last_name;
	}

	public void setLastName(String last_name)
	{
		this.last_name = last_name;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String[] getSelectedMainGroups()
	{
		return selected_main_groups;
	}
	
	public void setSelectedMainGroups(String[] selected_main_groups)
	{
		this.selected_main_groups = selected_main_groups;
	}
	
	public String[] getSelectedGeneralGroups()
	{
		return selected_general_groups;
	}
	
	public void setSelectedGeneralGroups(String[] selected_general_groups)
	{
		this.selected_general_groups = selected_general_groups;
	}
}
