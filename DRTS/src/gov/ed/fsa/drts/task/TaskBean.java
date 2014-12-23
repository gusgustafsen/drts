package gov.ed.fsa.drts.task;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.object.DRTSTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

@ManagedBean(name = "taskBean")
@ViewScoped
public class TaskBean extends PageUtil implements Serializable  {

	private static final long serialVersionUID = 384464876046745038L;

	private static final Logger logger = Logger.getLogger(TaskBean.class);
	
	private transient ProcessEngine process_engine = null;
	private transient IdentityService identity_service = null;
	private transient TaskService task_service = null;
	private transient RuntimeService runtime_service = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("init Task ::: " + userSession.getUser().getId());
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.task_service = this.process_engine.getTaskService();
			
			if(this.task_service == null)
			{
				// TODO handle error
			}
			
			this.identity_service = this.process_engine.getIdentityService();
			
			if(this.identity_service == null)
			{
				// TODO handle error
			}
			
			this.runtime_service = this.process_engine.getRuntimeService();
			
			if(this.runtime_service == null)
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
	private void destroy(){}
	
	public List<DRTSTask> getTasks()
	{
		DRTSTask drts_task = null;
		
		List<DRTSTask> all_tasks = new ArrayList<DRTSTask>();
		
		List<Task> user_tasks = this.task_service.createTaskQuery().taskCandidateOrAssigned(userSession.getUser().getId()).list();
		
		List<Group> user_groups = this.identity_service.createGroupQuery().groupMember(userSession.getUser().getId()).list();
		
		List<String> user_group_names = null;
		
		if(user_groups != null)
		{
			user_group_names = new ArrayList<String>();
			
			for(Group group : user_groups)
			{
				user_group_names.add(group.getId());
			}
		}
		
		if(user_tasks != null)
		{
			for(Task task : user_tasks)
			{
				drts_task = new DRTSTask(task.getId(), task);
				
				if(all_tasks.contains(drts_task) == false)
				{
					all_tasks.add(drts_task);
				}
			}
		}
		
		if(user_group_names != null)
		{
			List<Task> group_tasks = this.task_service.createTaskQuery().taskCandidateGroupIn(user_group_names).list();
			
			if(group_tasks != null)
			{
				for(Task task : group_tasks)
				{
					drts_task = new DRTSTask(task.getId(), task);
					
					if(all_tasks.contains(drts_task) == false)
					{
						all_tasks.add(drts_task);
					}
				}
			}
		}
		
		return all_tasks;
	}

	public String handleTask(DRTSTask task)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsTask", task);
		
		return task.getActivitiTask().getFormKey() + "?faces-redirect=true";
	}
}
