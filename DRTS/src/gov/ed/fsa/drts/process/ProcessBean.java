package gov.ed.fsa.drts.process;

import gov.ed.fsa.drts.bean.PageUtil;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;

@ManagedBean(name = "processBean")
@ViewScoped
public class ProcessBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 7641044800890586270L;

	private static final Logger logger = Logger.getLogger(ProcessBean.class);
	
	private transient ProcessEngine process_engine = null;
	private transient RepositoryService repository_service = null;
	private transient FormService form_service = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("init Process ::: " + userSession.getUser().getId());
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.repository_service = this.process_engine.getRepositoryService();
			
			if(this.repository_service == null)
			{
				// TODO handle error
			}
			
			this.form_service = this.process_engine.getFormService();
			
			if(this.form_service == null)
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
	
	public List<ProcessDefinition> getProcesses()
	{
		List<ProcessDefinition> processes = this.repository_service.createProcessDefinitionQuery().list();
		
		return processes;
	}
	
	public String goTo(ProcessDefinition process)
	{
	    logger.info("going to process: " + process.getKey() + " url: " + this.form_service.getStartFormData(process.getId()).getFormKey());
	    
	    return this.form_service.getStartFormData(process.getId()).getFormKey() + "?faces-redirect=true";
	}
}
