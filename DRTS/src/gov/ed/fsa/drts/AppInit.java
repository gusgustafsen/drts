package gov.ed.fsa.drts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

@ManagedBean(eager=true)
@ApplicationScoped
public class AppInit {

	@PostConstruct
    public void init()
	{
//		ProcessEngine process_engine = null;
//		RepositoryService repository_service = null;
//		RuntimeService runtime_service = null;
		
		ProcessEngines.init();
		
//		process_engine = ProcessEngines.getDefaultProcessEngine();
//		
//		runtime_service = process_engine.getRuntimeService();
//		repository_service = process_engine.getRepositoryService();
//		
//		List<ProcessInstance> process_instances = runtime_service.createProcessInstanceQuery().list();
//		
//		for(ProcessInstance process_instance : process_instances)
//		{
//			runtime_service.deleteProcessInstance(process_instance.getId(), "re-start");
//		}
//		
//		List<Deployment> deployments = repository_service.createDeploymentQuery().list();
//		
//		for(Deployment deployment : deployments)
//		{
//			repository_service.deleteDeployment(deployment.getId(), true);
//		}
//		
//		repository_service.createDeployment()
//		  			.addClasspathResource("gov/ed/fsa/drts/process/dataRequest/datarequest.bpmn20.xml")
//		  			.deploy();
    }

    @PreDestroy
    public void destroy()
    {
    	ProcessEngines.destroy();
    }
}
