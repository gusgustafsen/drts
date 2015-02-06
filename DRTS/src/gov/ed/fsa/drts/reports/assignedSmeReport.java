package gov.ed.fsa.drts.reports;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Report3AssignedSMEBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "assignedSmeReport")
@SessionScoped
public class assignedSmeReport implements Serializable {
	
	private PieChartModel pieModel;
	
	private String errorMessage = "";
	
	private List<Report3AssignedSMEBean> rows = new ArrayList<Report3AssignedSMEBean>();
	private String sortField = "TOTAL";
	private boolean sortAsc = false;

	public List<Report3AssignedSMEBean> getRows() {
		return rows;
	}

	public void setRows(List<Report3AssignedSMEBean> rows) {
		this.rows = rows;
	}
	
	@PostConstruct
	public void init() {
		
		this.errorMessage = "";
		
//		try {
//			this.rows = DataLayer.getInstance().getAssignedSmeReport(this.sortField, this.sortAsc);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			this.errorMessage = "Error connecting to database. Please try again. If problem persists, notify DRTS administrator.";
//		}
		
		if(this.rows==null)
		{
			//rows = new ArrayList<assignedSmeBean>();
			this.errorMessage = "No users found.";
		}
		
		// create model for pie chart
		createPieModel();
		
	}
	
    // add pie chart
    private void createPieModel() {
        pieModel = new PieChartModel();
         
        pieModel.setTitle("Total Requests Assigned to SMEs");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(350);
        pieModel.setDataFormat("percent");
        
        for(Report3AssignedSMEBean b : this.rows)
        {
        	pieModel.set(b.getName(), b.getTotalCount());
        }
    }
	
    public PieChartModel getPieModel() {
        return pieModel;
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
