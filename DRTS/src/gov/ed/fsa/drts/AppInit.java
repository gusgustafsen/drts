package gov.ed.fsa.drts;

import java.util.ArrayList;
import java.util.List;

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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

@ManagedBean(eager=true)
@ApplicationScoped
public class AppInit {
	
	private static final Logger logger = Logger.getLogger(AppInit.class);

	@PostConstruct
    public void init()
	{
		logger.info("called init in AppConfig");
		
		ProcessEngine process_engine = null;
		RepositoryService repository_service = null;
		RuntimeService runtime_service = null;
		IdentityService identity_service = null;
		
		ProcessEngines.init();
		
		process_engine = ProcessEngines.getDefaultProcessEngine();
		
		logger.info("AppConfig :: process_engine :: " + process_engine);
		
		runtime_service = process_engine.getRuntimeService();
		repository_service = process_engine.getRepositoryService();
		
		List<ProcessInstance> process_instances = runtime_service.createProcessInstanceQuery().list();
		
		for(ProcessInstance process_instance : process_instances)
		{
			runtime_service.deleteProcessInstance(process_instance.getId(), "re-start");
		}
		
		List<Deployment> deployments = repository_service.createDeploymentQuery().list();
		
		for(Deployment deployment : deployments)
		{
			repository_service.deleteDeployment(deployment.getId(), true);
		}
		
		repository_service.createDeployment()
		  			.addClasspathResource("gov/ed/fsa/drts/process/dataRequest/datarequest.bpmn20.xml")
		  			.deploy();
		
		String admin_user = "admin";
		String user_1 = "tima";
		String user_2 = "sme1";
		String user_3 = "drt1";
		String user_4 = "reporter1";
		
		identity_service = process_engine.getIdentityService();
		
		String[] security_groups = new String[] {"sme", "admin", "drt", "reporter", "requestor"};
		String[] other_groups = new String[] {"group1"};
		
	    for(String group_id : security_groups)
	    {
	    	if(identity_service.createGroupQuery().groupId(group_id).count() == 0)
	    	{
	    		Group newGroup = identity_service.newGroup(group_id);
	    	
	    		newGroup.setName(group_id);
	    		newGroup.setType("security-role");
	    	   
	    		identity_service.saveGroup(newGroup);
	    	}
	    }
	    
	    for(String group_id : other_groups)
	    {
	    	if(identity_service.createGroupQuery().groupId(group_id).count() == 0)
	    	{
	    		Group newGroup = identity_service.newGroup(group_id);
	    	
	    		newGroup.setName(group_id);
	    		newGroup.setType("other-role");
	    	   
	    		identity_service.saveGroup(newGroup);
	    	}
	    }
	    
	    if(identity_service.createUserQuery().userId(user_4).count() == 0)
		{
			String user_4_password = "admin";
			String user_4_email = "tasanov@ppsco.com";
			
			List<String> user_4_groups = new ArrayList<String>();
			
			user_4_groups.add("reporter");
			
			List<String> user_4_info = new ArrayList<String>();
			
			user_4_info.add("birth_date");
			user_4_info.add("20140929");
			
			User user = identity_service.newUser(user_4);
			
			user.setFirstName("Reporter");
			user.setLastName("1");
			user.setPassword(user_4_password);
			user.setEmail(user_4_email);
		
			identity_service.saveUser(user);
			
			for(String group : user_4_groups)
			{
				identity_service.createMembership(user_4, group);
			}
			
			for(int i = 0; i < user_4_info.size(); i += 2)
			{
				identity_service.setUserInfo(user_4, user_4_info.get(i), user_4_info.get(i + 1));
			}
		}
	    
	    if(identity_service.createUserQuery().userId(user_3).count() == 0)
		{
			String user_3_password = "admin";
			String user_3_email = "tasanov@ppsco.com";
			
			List<String> user_3_groups = new ArrayList<String>();
			
			user_3_groups.add("drt");
			
			List<String> user_3_info = new ArrayList<String>();
			
			user_3_info.add("birth_date");
			user_3_info.add("20140929");
			
			User user = identity_service.newUser(user_3);
			
			user.setFirstName("DRT");
			user.setLastName("1");
			user.setPassword(user_3_password);
			user.setEmail(user_3_email);
		
			identity_service.saveUser(user);
			
			for(String group : user_3_groups)
			{
				identity_service.createMembership(user_3, group);
			}
			
			for(int i = 0; i < user_3_info.size(); i += 2)
			{
				identity_service.setUserInfo(user_3, user_3_info.get(i), user_3_info.get(i + 1));
			}
		}
		
		if(identity_service.createUserQuery().userId(admin_user).count() == 0)
		{
			String admin_password = "admin";
			String admin_email = "tasanov@ppsco.com";
			
			List<String> admin_groups = new ArrayList<String>();
			
			admin_groups.add("admin");
			
			List<String> admin_info = new ArrayList<String>();
			
			admin_info.add("birth_date");
			admin_info.add("20140929");
			
			User user = identity_service.newUser(admin_user);
			
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPassword(admin_password);
			user.setEmail(admin_email);
		
			identity_service.saveUser(user);
			
			for(String group : admin_groups)
			{
				identity_service.createMembership(admin_user, group);
			}
			
			for(int i = 0; i < admin_info.size(); i += 2)
			{
				identity_service.setUserInfo(admin_user, admin_info.get(i), admin_info.get(i + 1));
			}
		}
		
		if(identity_service.createUserQuery().userId(user_1).count() == 0)
		{
			String user_1_password = "tima";
			String user_1_email = "tasanov@ppsco.com";
			
			List<String> user_1_groups = new ArrayList<String>();
			
			user_1_groups.add("group1");
			user_1_groups.add("requestor");
			
			List<String> user_1_info = new ArrayList<String>();
			
			user_1_info.add("birth_date");
			user_1_info.add("20140929");
			
			User user = identity_service.newUser(user_1);
			
			user.setFirstName("tima");
			user.setLastName("tima");
			user.setPassword(user_1_password);
			user.setEmail(user_1_email);
		
			identity_service.saveUser(user);
			
			for(String group : user_1_groups)
			{
				identity_service.createMembership(user_1, group);
			}
			
			for(int i = 0; i < user_1_info.size(); i += 2)
			{
				identity_service.setUserInfo(user_1, user_1_info.get(i), user_1_info.get(i + 1));
			}
		}
		
		if(identity_service.createUserQuery().userId(user_2).count() == 0)
		{
			String user_2_password = "tima";
			String user_2_email = "tim.asanov2013+smeemail@gmail.com";
			
			List<String> user_2_groups = new ArrayList<String>();
			
			user_2_groups.add("group1");
			user_2_groups.add("sme");
			
			List<String> user_2_info = new ArrayList<String>();
			
			user_2_info.add("birth_date");
			user_2_info.add("20140929");
			
			User user = identity_service.newUser(user_2);
			
			user.setFirstName("SME");
			user.setLastName("1");
			user.setPassword(user_2_password);
			user.setEmail(user_2_email);
		
			identity_service.saveUser(user);
			
			for(String group : user_2_groups)
			{
				identity_service.createMembership(user_2, group);
			}
			
			for(int i = 0; i < user_2_info.size(); i += 2)
			{
				identity_service.setUserInfo(user_2, user_2_info.get(i), user_2_info.get(i + 1));
			}
		}
    }

    @PreDestroy
    public void destroy()
    {
    	logger.info("called destroy in AppConfig");
    	
    	ProcessEngines.destroy();
    }
}
