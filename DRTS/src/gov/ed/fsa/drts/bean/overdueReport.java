package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "overdueReport")
@SessionScoped
public class overdueReport implements Serializable {
	
	private List<overdueReportBean> rows = new ArrayList<overdueReportBean>();
	private String sortField = "REQUESTED_DUE_DATE";
	private boolean sortAsc = true;
	private String errorMessage = "";

	public List<overdueReportBean> getRows() {
		return rows;
	}

	public void setRows(List<overdueReportBean> rows) {
		this.rows = rows;
	}
	
	@PostConstruct
	public void init() {
		this.errorMessage = "";
		
		// get the list of report beans from the DB
		try {
				this.rows = DataLayer.getInstance().getOverdueReport(sortField, sortAsc);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.errorMessage = "Error connecting to database. Please try again. If problem persists, notify DRTS administrator.";
		}
		
		if(this.rows == null) {
			this.errorMessage = "No overdue requests found.";
			this.rows = new ArrayList<overdueReportBean>();
		}
		
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isSortAsc() {
		return sortAsc;
	}

	public void setSortAsc(boolean sortAsc) {
		this.sortAsc = sortAsc;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	// <!--  <h:commandButton id="rptExportCsvCustom" class="pull-right" value="Export Table to CSV" rendered="#{not empty openClosedRequestsByDay.rows}" action="#{openClosedRequestsByDay.exportCsv}" />  -->
	public void exportCsv()
	{
	}
}
