package gov.ed.fsa.drts.reports;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.table.ReportTablePaginator;
import gov.ed.fsa.drts.table.TablePaginator;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "report5")
@ViewScoped
public class Report5Overdue extends PageUtil implements Serializable {

	private static final long serialVersionUID = -6584664910782897254L;

	private TablePaginator table_paginator = null;
	
	@PostConstruct
	public void init()
	{
		this.table_paginator = new ReportTablePaginator(5, this.userSession);
	}
	
	public TablePaginator getResultsTable()
	{
		return this.table_paginator;
	}
}
