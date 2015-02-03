package gov.ed.fsa.drts.reports;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.table.TablePaginator;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "report1")
@ViewScoped
public class Report1RequestFilter extends PageUtil implements Serializable {

	private static final long serialVersionUID = 6033872196010701108L;

	private String display_id = null;
	private String keyword = null;
	private String status = null;
	private String type = null;
	private String assignee = null;
	private String requestor = null;
	
	/*
	 * Priority
	 * Requested Due Date
	 * Resolved Date
	 * Modified Date
	 * System
	 */
	
	private boolean report_was_run = false;
	
	private TablePaginator table_paginator = null;
	
	public void runReport()
	{
		this.report_was_run = true;
		
		
	}
	
	public void clear()
	{
		this.table_paginator = null;
		
		this.report_was_run = false;
	}
	
	public boolean getReportWasRun()
	{
		return this.report_was_run;
	}

	public TablePaginator getResultsTable()
	{
		return this.table_paginator;
	}
}
