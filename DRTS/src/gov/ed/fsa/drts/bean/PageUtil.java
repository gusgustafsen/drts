package gov.ed.fsa.drts.bean;

import javax.faces.bean.ManagedProperty;

public class PageUtil {
	
	@ManagedProperty(value = "#{userSession}")
	protected UserSession userSession;
	
	public UserSession getUserSession()
	{
		return this.userSession;
	}
	
	public void setUserSession(UserSession userSession)
	{
		this.userSession = userSession;
	}
}
