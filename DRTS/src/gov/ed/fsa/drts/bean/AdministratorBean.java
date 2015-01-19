package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.io.Serializable;
import java.util.List;

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

@ManagedBean(name = "adminBean")
@ViewScoped
public class AdministratorBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 5521046301992198696L;

	private transient ProcessEngine process_engine = null;
	private transient IdentityService identity_service = null;
	
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
	}
	
	@PreDestroy
	private void destroy()
	{
		
	}
	
	public List<User> getUsers()
	{
		List<User> users = this.identity_service.createUserQuery().list();
		
		return users;
	}
	
	public String goToEditUser(User user)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", user);
		
		return "/administration/editUser.htm?faces-redirect=true";
	}

	public String getUserGroups(String user_id)
	{
		StringBuilder sb = new StringBuilder();
		
		List<Group> groups_list = identity_service.createGroupQuery().groupMember(user_id).list();
		
		if(groups_list != null)
		{
			for(Group group : groups_list)
			{
				sb.append(group.getId() + ", ");
			}
			
			sb.deleteCharAt(sb.length() - 2);
		}
		
		return sb.toString();
	}

	public String goToEditGroup(Group group)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsGroup", group);
		
		return "/administration/editGroup.htm?faces-redirect=true";
	}
	
	public List<Group> getGroups()
	{
		List<Group> general_groups = this.identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_GENERAL.getStringValue()).list();
		
		return general_groups;
	}
}
