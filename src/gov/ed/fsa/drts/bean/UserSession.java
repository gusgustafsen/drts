package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.object.DRTSUser;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;

@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = -1671576504428171087L;

	private static final Logger logger = Logger.getLogger(UserSession.class);
	
	private transient IdentityService identity_service = null;
	
	private DRTSUser user = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("created UserSession bean");
		
		Map<String, String> request_header_map = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
		
		String incoming_user = null;
		
		incoming_user = request_header_map.get("iv-user");
		
		if(incoming_user != null)
		{
			identity_service = ProcessEngines.getDefaultProcessEngine().getIdentityService();
			
			User activiti_user = identity_service.createUserQuery().userId(incoming_user).singleResult();
			
			if(activiti_user != null)
			{
				List<Group> security_groups = identity_service.createGroupQuery().groupType("security-role").groupMember(activiti_user.getId()).list();
				
				for(Group group : security_groups)
				{
					logger.info("user belongs to: " + group.getName() + " security group of type " + group.getType());
				}
				
				if(security_groups != null && security_groups.size() > 0)
				{
					List<Group> all_groups = identity_service.createGroupQuery().groupMember(activiti_user.getId()).list();
					
					logger.info("user belongs to total of " + all_groups.size() + " groups.");
					
					this.user = new DRTSUser();
					this.user.setActivitiUser(activiti_user);
					this.user.addGroups(Utils.convertActivitiGroups(all_groups));
					this.user.setHomePage();
					
					logger.info("is user admin?: " + this.user.isAdmin());
				}
				else
				{
					// TODO handle major error user has no security groups
				}
			}
			else
			{
				// TODO handle major error unknown user
			}
		}
	}
	
	@PreDestroy
	private void destroy(){}
	
	public DRTSUser getUser()
	{
		return this.user;
	}
}
