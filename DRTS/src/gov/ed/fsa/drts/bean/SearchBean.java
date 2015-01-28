package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.table.SearchTablePaginator;
import gov.ed.fsa.drts.table.TablePaginator;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "searchBean")
@ViewScoped
public class SearchBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 2215961558616140309L;

	private String display_id = null;
	private String keyword = null;
	private Date created_date_from = null;
	private Date created_date_to = null;
	private String status = null;
	private String type = null;
	private String assignee = null;
	private String requestor = null;
	
	private boolean search_was_run = false;
	
	private TablePaginator table_paginator = null;
	
	@PostConstruct
	private void init()
	{
		
	}
	
	public void search()
	{
		search_was_run = true;
		
		if(Utils.isStringEmpty(this.display_id) == false)
		{
			this.table_paginator = new SearchTablePaginator(1, this.userSession, this.display_id);
		}
		else
		{
			this.table_paginator = new SearchTablePaginator(2, this.userSession);
		}
	}
	
	public String getDisplayId()
	{
		return display_id;
	}
	
	public void setDisplayId(String display_id)
	{
		this.display_id = display_id;
	}
	
	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}
	
	public Date getCreatedDateFrom()
	{
		return created_date_from;
	}
	
	public void setCreatedDateFrom(Date created_date_from)
	{
		this.created_date_from = created_date_from;
	}
	
	public Date getCreatedDateTo()
	{
		return created_date_to;
	}

	public void setCreatedDateTo(Date created_date_to)
	{
		this.created_date_to = created_date_to;
	}
	
	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getAssignee()
	{
		return assignee;
	}
	
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}
	
	public String getRequestor()
	{
		return requestor;
	}
	
	public void setRequestor(String requestor)
	{
		this.requestor = requestor;
	}
	
	public boolean getSearchWasRun()
	{
		return this.search_was_run;
	}

	public TablePaginator getResultsTable()
	{
		return this.table_paginator;
	}
}
