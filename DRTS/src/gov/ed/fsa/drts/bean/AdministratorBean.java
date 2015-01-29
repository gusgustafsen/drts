package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;

/**
 * Managed bean that works with the administration home page.
 *
 * @author Timur Asanov | tasanov@ppsco.com
 */
@ManagedBean(name = "adminBean")
@ViewScoped
public class AdministratorBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 5521046301992198696L;

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger(AdministratorBean.class);
	
	/**
	 * Activiti process engine.
	 */
	private transient ProcessEngine process_engine = null;
	
	/**
	 * Activiti identity service.
	 */
	private transient IdentityService identity_service = null;
	
	/**
	 * Bean constructor.
	 */
	@PostConstruct
	private void init()
	{
		logger.debug("Initialized AdministratorBean");
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		this.identity_service = this.process_engine.getIdentityService();
	}
	
	/**
	 * Method that retrieves all current users.
	 * 
	 * @return Returns all current users.
	 */
	public List<User> getUsers()
	{
		List<User> users = this.identity_service.createUserQuery().list();
		
		logger.debug("Current number of users: "+ users.size());
		
		return users;
	}
	
	/**
	 * Method that takes an administrator to the Edit User page.
	 * 
	 * @param user user to edit
	 * 
	 * @return Takes the administrator to the Edit User page. 
	 */
	public String goToEditUser(User user)
	{
		// passes the user, that was chosen, to the User Bean
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", user);
		
		logger.debug("Passing user: "+ user.getId() + " to the User Bean");
		
		return "/administration/editUser.htm?faces-redirect=true";
	}

	/**
	 * Method that retrieves the group of a user.
	 * 
	 * @param user_id user ID of the user
	 * 
	 * @return Returns the user's group.
	 */
	public String getUserGroup(String user_id)
	{
		Group group = identity_service.createGroupQuery().groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue()).groupMember(user_id).singleResult();
		
		return group.getId();
	}
}
