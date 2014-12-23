package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.object.DRTSUser;
import gov.ed.fsa.drts.security.SecurityPermissions;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

@ManagedBean(name = "userSession")
@SessionScoped
public class UserSession implements Serializable {

	private static final long serialVersionUID = -1671576504428171087L;

	private static final Logger logger = Logger.getLogger(UserSession.class);
	
	private DRTSUser user = null;
	
	private Subject subject;
	
	@PostConstruct
	private void init()
	{
		logger.debug("created UserSession bean");
	}
	
	@PreDestroy
	private void destroy(){}
	
	public DRTSUser getUser()
	{
		return this.user;
	}
	
	public void setUser(DRTSUser user)
	{
		this.user = user;
	}
	
	public void setSubject(Subject subject)
	{
		this.subject = subject;
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
