package gov.ed.fsa.drts.history;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.process.bookOrder.BookOrder;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.log4j.Logger;

@ManagedBean(name = "historyBean")
@ViewScoped
public class History extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

	private static final Logger logger = Logger.getLogger(BookOrder.class);
	
	private transient ProcessEngine process_engine = null;
	private transient HistoryService history_service = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("init History ::: " + userSession.getUser().getId());
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.history_service = this.process_engine.getHistoryService();
			
			if(this.history_service == null)
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
	
	public List<HistoricTaskInstance> getCompletedTasks()
	{
		List<HistoricTaskInstance> completed_tasks = this.history_service.createHistoricTaskInstanceQuery().finished().orderByHistoricTaskInstanceEndTime().desc().list();
		
		return completed_tasks;
	}
	
	public List<HistoricProcessInstance> getCompletedProcesses()
	{
		List<HistoricProcessInstance> completed_processes = this.history_service.createHistoricProcessInstanceQuery().finished().orderByProcessInstanceEndTime().desc().list();
		
		return completed_processes;
	}
}
