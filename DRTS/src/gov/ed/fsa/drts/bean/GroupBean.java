package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

@ManagedBean(name = "groupBean")
@ViewScoped
public class GroupBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 5649021948609707856L;

	private transient ProcessEngine process_engine = null;
	private transient IdentityService identity_service = null;

	private String name = null;
	
	private Group current_group = null;
	
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
		
		this.current_group = (Group) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsGroup");
		
		if(this.current_group != null)
		{
			loadData();
		}
	}
	
	private void loadData()
	{
		this.name = this.current_group.getName();
	}
	
	public String save(boolean new_group)
	{
		Group updated_group = null;
		
		if(new_group == true)
		{
			updated_group = this.identity_service.newGroup(this.name);
		}
		else
		{
			updated_group = this.current_group;
		}

		updated_group.setName(this.name);
		updated_group.setType(ApplicationProperties.GROUP_TYPE_GENERAL.getStringValue());
		
		this.identity_service.saveGroup(updated_group);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsGroup", null);
		
		return "/administration/groupManagement.htm?faces-redirect=true";
	}
	
	public String cancel()
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", null);
		
		return "/administration/groupManagement.htm?faces-redirect=true";
	}
	
	@PreDestroy
	private void destroy()
	{
		
	}

	public String getName()
	{
		return (this.current_group == null ? this.name : this.current_group.getName());
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
