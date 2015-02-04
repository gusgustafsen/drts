package gov.ed.fsa.drts.search;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.table.SearchTablePaginator;
import gov.ed.fsa.drts.table.TablePaginator;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
	
	private int current_tab = 0;
	
	@PostConstruct
	private void init()
	{
		
	}
	
	public void search()
	{
		search_was_run = true;
		
		LinkedHashMap<String, String> search_parameters = null;
		
		if(Utils.isStringEmpty(this.display_id) == false)
		{
			this.table_paginator = new SearchTablePaginator(1, this.userSession, this.display_id);
			this.current_tab = 0;
		}
		else if(Utils.isStringEmpty(this.keyword) == false)
		{
			this.table_paginator = new SearchTablePaginator(3, this.userSession, this.keyword);
			this.current_tab = 2;
		}
		else
		{
			search_parameters = new LinkedHashMap<String, String>();
			
			if(Utils.isStringEmpty(this.status) == false)
			{
				search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), this.status);
			}
			
			if(Utils.isStringEmpty(this.type) == false)
			{
				search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.type);
			}
			
			if(Utils.isStringEmpty(this.assignee) == false)
			{
				search_parameters.put("ASSIGNEE", this.assignee);
			}
			
			if(Utils.isStringEmpty(this.requestor) == false)
			{
				search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.requestor);
			}
			
			this.table_paginator = new SearchTablePaginator(2, this.userSession, search_parameters, this.created_date_from, this.created_date_to);
			this.current_tab = 1;
		}
	}

	public void clear()
	{
		this.display_id = null;
		this.keyword = null;
		this.created_date_from = null;
		this.created_date_to = null;
		this.status = null;
		this.type = null;
		this.assignee = null;
		this.requestor = null;
		
		this.table_paginator = null;
		
		this.search_was_run = false;
	}
	
	public String viewRequest(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return "/dataRequest/view.htm?faces-redirect=true";
	}
	
	public Map<String, String> getStatuses()
	{
		Map<String, String> request_statuses = new LinkedHashMap<String, String>();
		
		request_statuses.put("", "");
		
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_VALIDATED.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_VALIDATED.getStringValue());
			
		return request_statuses;
	}
	
	public Map<String, String> getTypes()
	{
		Map<String, String> request_types = new LinkedHashMap<String, String>();
		
		request_types.put("", "");
		
		for(String type : ApplicationProperties.DATA_REQUEST_TYPES.getListValue())
		{
			request_types.put(type, type);
		}
			
		return request_types;
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

	public int getCurrentTab()
	{
		return this.current_tab;
	}
}
