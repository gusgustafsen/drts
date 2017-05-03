package gov.ed.fsa.drts;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;

import gov.ed.fsa.drts.dataaccess.OracleFactory;

/**
 * Managed bean that initializes and stops the Activiti Engine when the application starts up and shuts down.
 *
 * @author Timur Asanov | tasanov@ppsco.com
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class AppInit {

	/** Log4j logger. */
	private static final Logger logger = Logger.getLogger(AppInit.class);

	/** Bean constructor. */
	@PostConstruct
	public void init() {
		ProcessEngine process_engine = null;
		RepositoryService repository_service = null;
		RuntimeService runtime_service = null;
		IdentityService identity_service = null;

		process_engine = OracleFactory.getProcessEngine();
		runtime_service = process_engine.getRuntimeService();
		repository_service = process_engine.getRepositoryService();
		identity_service = process_engine.getIdentityService();

		// TODO groups and usernames should not be hard coded
		String[] security_groups = new String[] { "sme", "admin", "drt", "reporter", "requestor" };
		String[] user_ids = new String[] { "sme1", "admin1", "drt1", "reporter1", "requestor1" };
		HashMap<String, String> user_groups = new HashMap<String, String>();
		user_groups.put("sme1", "sme");
		user_groups.put("admin1", "admin");
		user_groups.put("drt1", "drt");
		user_groups.put("reporter1", "reporter");
		user_groups.put("requestor1", "requestor");

		/*
		 * This code removes users, and their group memberships.
		 */
		// for(String user_id : user_ids)
		// {
		// if(identity_service.createUserQuery().userId(user_id).count() != 0)
		// {
		// // remove user's group memberships
		// for(String group_id : security_groups)
		// {
		// logger.debug("removing user[" + user_id + "] from group["+ group_id + "]");
		// identity_service.deleteMembership(user_id, group_id);
		// }
		//
		// // remove user
		// logger.debug("removing user[" + user_id + "]");
		// identity_service.deleteUser(user_id);
		// }
		// }

		/*
		 * This code creates user(s) if they do not exist.
		 */
		for (String user_id : user_ids) {
			if (identity_service.createUserQuery().userId(user_id).count() == 0) {
				User user = identity_service.newUser(user_id);

				user.setFirstName(user_id + "first");
				user.setLastName(user_id + "last");
				// TODO password is not required, handled by AIMS
				user.setPassword(user_id);
				// TODO change to group email or someone else's email
				user.setEmail("tasanov+" + user_id + "@ppsco.com");

				logger.debug("adding user[" + user_id + "]");
				identity_service.saveUser(user);
			}
		}

		/*
		 * This code removes all groups. This can only be run if the group(s) do not have
		 * users in them, otherwise it will throw a foreign key constraint error.
		 */
		// for(String group_id : security_groups)
		// {
		// if(identity_service.createGroupQuery().groupId(group_id).count() != 0)
		// {
		// logger.debug("removing group[" + group_id + "]");
		// identity_service.deleteGroup(group_id);
		// }
		// }

		/*
		 * This code creates group(s) if they do not exist.
		 */
		for (String group_id : security_groups) {
			if (identity_service.createGroupQuery().groupId(group_id).count() == 0) {
				Group new_group = identity_service.newGroup(group_id);

				new_group.setName(group_id);
				new_group.setType("security-role");

				logger.debug("adding group[" + group_id + "]");
				identity_service.saveGroup(new_group);
			}
		}

		/*
		 * This code assigns group(s) to users.
		 */
		// for(Map.Entry<String, String> user_group : user_groups.entrySet())
		// {
		// // TODO check if user is already a member of that group
		//
		// logger.debug("adding user[" + user_group.getKey() + "] to group["+ user_group.getValue() + "]");
		// identity_service.createMembership(user_group.getKey(), user_group.getValue());
		// }

		/*
		 * This code removes all running process instances.
		 * TODO it removes instances from Activiti but not from the DRTS application
		 */
		// List<ProcessInstance> process_instances = runtime_service.createProcessInstanceQuery().list();
		//
		// for(ProcessInstance process_instance : process_instances)
		// {
		// logger.debug("deleting process instance[" + process_instance.getId() + "]");
		// runtime_service.deleteProcessInstance(process_instance.getId(), "re-start");
		// }

		/*
		 * This code removes all deployments
		 */
		// List<Deployment> deployments = repository_service.createDeploymentQuery().list();
		//
		// for(Deployment deployment : deployments)
		// {
		// logger.debug("deleting deployment[" + deployment.getId() + "]");
		// repository_service.deleteDeployment(deployment.getId(), true);
		// }

		/*
		 * This codes deploys the DRTS workflow
		 * TODO should only run if the workflow changed
		 * TODO what happens if workflow changes while there are active processes running
		 */
		logger.debug("deploying DRTS workflow");
		repository_service.createDeployment()
				.addClasspathResource("gov/ed/fsa/drts/process/dataRequest/datarequest.bpmn20.xml").deploy();
	}

	/** Bean destructor. */
	@PreDestroy
	public void destroy() {
		ProcessEngines.destroy();

		logger.debug("Stopped Activiti Engine.");
	}
}
