package gov.ed.fsa.drts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.activiti.engine.ProcessEngines;
import org.apache.log4j.Logger;

/**
 * Managed bean that initializes and stops the Activiti Engine when 
 * the application starts up and shuts down.
 *
 * @author Timur Asanov | tasanov@ppsco.com
 */
@ManagedBean(eager=true)
@ApplicationScoped
public class AppInit {

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger(AppInit.class);
	
	/**
	 * Bean constructor.
	 */
	@PostConstruct
    public void init()
	{
		ProcessEngines.init();
		
		logger.debug("Initialized Activiti Engine.");
		
//		ProcessEngine process_engine = null;
//		RepositoryService repository_service = null;
//		RuntimeService runtime_service = null;
		
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

	/**
	 * Bean destructor.
	 */
    @PreDestroy
    public void destroy()
    {
    	ProcessEngines.destroy();
    	
    	logger.debug("Stopped Activiti Engine.");
    }
}
