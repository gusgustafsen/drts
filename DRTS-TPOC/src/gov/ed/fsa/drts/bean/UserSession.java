package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.security.SecurityPermissions;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = -1671576504428171087L;

	private static final Logger logger = Logger.getLogger(UserSession.class);
	
	private User user = null;
	
	private Subject subject;
	
	@PostConstruct
	private void init()
	{
		logger.debug("created UserSession bean");
	}
	
	@PreDestroy
	private void destroy(){}
	
	public User getUser()
	{
		return this.user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String getHomePage()
	{
		return Utils.getUserHomePage(this.user.getId());
	}
	
	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}
	
	public boolean isAdmin()
	{
		if(subject == null)
		{
			return false;
		}
		
		return Utils.isUserInGroup(this.user.getId(), ApplicationProperties.GROUP_ADMIN.getStringValue());
	}
	
	public boolean isDrt()
	{
		if(subject == null)
		{
			return false;
		}
		
		return Utils.isUserInGroup(this.user.getId(), ApplicationProperties.GROUP_DRT.getStringValue());
	}

	public boolean isRequestor()
	{
		if(subject == null)
		{
			return false;
		}
		
		return Utils.isUserInGroup(this.user.getId(), ApplicationProperties.GROUP_REQUESTOR.getStringValue());
	}
	
	public boolean isReporter()
	{
		if(subject == null)
		{
			return false;
		}
		
		return Utils.isUserInGroup(this.user.getId(), ApplicationProperties.GROUP_REPORTER.getStringValue());
	}
	
	public boolean isSme()
	{
		if(subject == null)
		{
			return false;
		}
		
		return Utils.isUserInGroup(this.user.getId(), ApplicationProperties.GROUP_SME.getStringValue());
	}
	
	public boolean isAllowedToCreateRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_CREATE.getStringValue());
	}

	public boolean isAllowedToEditAllRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_EDIT_ALL.getStringValue());
	}
		
	public boolean isAllowedToDeleteRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_DELETE.getStringValue());
	}
	
	public boolean isAllowedToViewAllRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REPORTS_VIEW_ALL.getStringValue());
	}
	
	public boolean isAllowedToResolveRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_RESOLVE.getStringValue());
	}
	
	public boolean isAllowedToCloseRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_CLOSE.getStringValue());
	}
	
	public boolean isAllowedToHoldRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REQUESTS_HOLD.getStringValue());
	}
	
	public boolean isAllowedToSearchAllRequests()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.SEARCH_REQUESTS_ALL.getStringValue());
	}
	
	public boolean isAllowedToSearchAllAttachments()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.SEARCH_ATTACHMENTS_ALL.getStringValue());
	}

	public boolean isAllowedToRunReportsWithAllData()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REPORTS_VIEW_ALL.getStringValue());
	}
	
	public boolean isAllowedToExportReports()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.REPORTS_EXPORT.getStringValue());
	}
	
	public boolean isAllowedToCreateAccounts()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.ACCOUNTS_CREATE.getStringValue());
	}
	
	public boolean isAllowedToEditAccounts()
	{
		if(subject == null)
		{
			return false;
		}
		
		return subject.isPermitted(SecurityPermissions.ACCOUNTS_EDIT.getStringValue());
	}
}