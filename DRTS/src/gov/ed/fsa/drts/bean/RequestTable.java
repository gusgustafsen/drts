package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "requestTable")
@ViewScoped
public class RequestTable extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

//	private static final Logger logger = Logger.getLogger(RequestTable.class);
	
	private TablePaginator table_paginator = null;
	
	public TablePaginator getTable1()
	{
		if(this.table_paginator == null)
		{
			this.table_paginator = new TablePaginator(1, this.userSession);
		}
		
		return this.table_paginator;
	}
	
	public TablePaginator getTable2()
	{
		if(this.table_paginator == null)
		{
			this.table_paginator = new TablePaginator(2, this.userSession);
		}
		
		return this.table_paginator;
	}
	
	public TablePaginator getTable3()
	{
		if(this.table_paginator == null)
		{
			this.table_paginator = new TablePaginator(3, this.userSession);
		}
		
		return this.table_paginator;
	}
	
	public TablePaginator getTable4()
	{
		if(this.table_paginator == null)
		{
			this.table_paginator = new TablePaginator(4, this.userSession);
		}
		
		return this.table_paginator;
	}
	
	public String goToTask(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return dataRequest.getCurrentTaskFormKey() + "?faces-redirect=true";
	}
	
	public String viewRequest(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return "/dataRequest/view.htm?faces-redirect=true";
	}
	
	public String goToOpenNew(DataRequest dataRequest)
	{
		int next_iteration = 2;
		
		try
		{
			next_iteration = DataLayer.getInstance().getNextIteration(dataRequest.getId());
			
			DataRequest new_request = new DataRequest();
			new_request.initialize(this.userSession.getUser().getId());
			
			new_request.setIteration(next_iteration);
			new_request.setDescription(dataRequest.getDescription());
			new_request.setParentId(dataRequest.getId());
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", new_request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "/dataRequest/view.htm?faces-redirect=true";
	}
}