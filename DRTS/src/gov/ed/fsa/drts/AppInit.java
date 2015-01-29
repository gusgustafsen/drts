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
