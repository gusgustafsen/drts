package gov.ed.fsa.drts.bean;

import java.io.Serializable;
import java.util.ArrayList;
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

import gov.ed.fsa.drts.util.ApplicationProperties;

/**
 * Managed bean that works with a user's data.
 *
 * @author Timur Asanov | tasanov@ppsco.com
 */
@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 612941570092949641L;

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger(UserBean.class);

	/**
	 * Activiti process engine.
	 */
	private transient ProcessEngine process_engine = null;

	/**
	 * Activiti identity service.
	 */
	private transient IdentityService identity_service = null;

	/**
	 * User's ID, from the JSF form.
	 */
	private String id = null;

	/**
	 * User's first name, from the JSF form.
	 */
	private String first_name = null;

	/**
	 * User's last name, from the JSF form.
	 */
	private String last_name = null;

	/**
	 * User's email, from the JSF form.
	 */
	private String email = null;

	/**
	 * User that the bean is working with.
	 */
	private User current_user = null;

	/**
	 * User's current main group.
	 */
	private String user_current_group;

	/**
	 * User's currently selected group, from the JSF form.
	 */
	private String selected_group;

	private boolean isFaces = true;

	public UserBean() {
		// default constructor
	}

	public UserBean(String id, String first_name, String last_name, String email, String selected_group) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.selected_group = selected_group;
		this.isFaces = false;

		init();
	}

	/**
	 * Bean constructor.
	 */
	@PostConstruct
	private void init() {
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		this.identity_service = this.process_engine.getIdentityService();

		if (isFaces) {
			this.current_user = (User) FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.get("drtsUser");

			if (this.current_user != null) {
				logger.debug("Received: " + this.current_user.getId() + " from the AdministartorBean.");

				loadData();
			} else {
				logger.error("Did not receive a user object from the AdministartorBean.");
			}
		}
	}

	/**
	 * Method that loads the form variables from the current user's data.
	 */
	private void loadData() {
		this.first_name = this.current_user.getFirstName();
		this.last_name = this.current_user.getLastName();
		this.email = this.current_user.getEmail();

		loadGroup();
	}

	/**
	 * Method that retrieves the current user's group.
	 */
	private void loadGroup() {
		Group group = identity_service.createGroupQuery()
				.groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue())
				.groupMember(this.current_user.getId()).singleResult();

		String groupName = group == null ? ApplicationProperties.GROUP_DEFAULT.getStringValue() : group.getName();

		logger.debug("User's current group: " + groupName);

		this.user_current_group = groupName;
		this.selected_group = groupName;
	}

	/**
	 * Method that saves the current user.
	 * 
	 * @param new_user
	 *            true if a new user is being created, false otherwise
	 * 
	 * @return Takes the administrator back to the User Management page.
	 */
	public String save(boolean new_user) {
		User updated_user = null;

		if (new_user == true) {
			logger.info("Creating a new user: " + this.id);

			updated_user = this.identity_service.newUser(this.id);
		} else {
			logger.info("Updating an existing user: " + this.id);

			updated_user = this.current_user;
		}

		updated_user.setFirstName(this.first_name);
		updated_user.setLastName(this.last_name);
		updated_user.setEmail(this.email);

		this.identity_service.saveUser(updated_user);

		if (new_user == true) {
			logger.info("Adding a new user to group: " + this.selected_group);

			this.identity_service.createMembership(updated_user.getId(), this.selected_group);
		} else {
			if (this.user_current_group.equalsIgnoreCase(this.selected_group) == false) {
				logger.info("Updating an existing user's group from: " + this.user_current_group + " to: "
						+ this.selected_group);

				this.identity_service.deleteMembership(updated_user.getId(), this.user_current_group);
				this.identity_service.createMembership(updated_user.getId(), this.selected_group);
			}
		}

		if (isFaces) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", null);
		}

		return "/administration/userManagement.htm?faces-redirect=true";
	}

	/**
	 * Method that cancels creation of new user or update of an existing user.
	 * 
	 * @return Takes the administrator back to the User Management page.
	 */
	public String cancel() {
		// TODO check log for warnings
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsUser", null);

		return "/administration/userManagement.htm?faces-redirect=true";
	}

	/**
	 * Method that retrieves all groups that exist in the system.
	 * 
	 * @return Returns a list of all groups that exist in the system.
	 */
	public List<String> getGroups() {
		List<String> result = null;
		List<Group> groups = identity_service.createGroupQuery()
				.groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue()).list();

		if (groups != null) {
			logger.debug("Current number of groups: " + groups.size());

			result = new ArrayList<String>();

			for (Group group : groups) {
				result.add(group.getName());
			}
		} else {
			logger.error("There are no existing groups in the system.");
		}

		return result;
	}

	/*
	 * GETTERS AND SETTERS
	 * 
	 */

	public String getId() {
		return (this.current_user == null ? this.id : this.current_user.getId());
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return this.last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSelectedGroup() {
		return selected_group;
	}

	public void setSelectedGroup(String selected_main_group) {
		this.selected_group = selected_main_group;
	}
}
