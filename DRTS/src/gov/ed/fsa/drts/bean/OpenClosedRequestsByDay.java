package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean(name = "openClosedRequestsByDay")
@SessionScoped
public class OpenClosedRequestsByDay implements Serializable {
	
	private LineChartModel lineModel;

	
	private List<OpenClosedReqBean> rows = new ArrayList<OpenClosedReqBean>();
	private String sortField = "DATE_DAY";
	private boolean sortAsc = false;
	private String errorMessage = "";

	public List<OpenClosedReqBean> getRows() {
		return rows;
	}

	public void setRows(List<OpenClosedReqBean> rows) {
		this.rows = rows;
	}
	
	@PostConstruct
	public void init() {
		this.errorMessage = "";
		
		rows = new ArrayList<OpenClosedReqBean>();
		
		// get 30 days ago at midnight
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);	
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today30 = cal.getTime();
		System.out.println(today30.toString());
		
		// get the list of report beans from the DB
		List<OpenClosedReqBean> dbBeans = null;
		try {
			dbBeans = DataLayer.getInstance().getOpenClosedReqReport(this.sortField, this.sortAsc);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.errorMessage = "Error connecting to database. Please try again. If problem persists, notify DRTS administrator.";
		}
		
		// max requests number, need for graph Y axis limit
		int iMaxReqNumber = 0;
		
		// iterate through each of the last 30 days
		for(int i=0; i<30; i++){
			cal.add(Calendar.DAY_OF_MONTH, 1);
			Date curDay = cal.getTime();
			
			// flag that date was found in DB recordset
			boolean bFound = false;
			
			// iterate through DB recordset
			for(OpenClosedReqBean b : dbBeans)
			{
				// if the day is found in DB
				if((dbBeans!=null)&&(curDay.compareTo(b.getReportDate())==0))
				{
					rows.add(b);
					bFound = true;
					
					// check max requests number
					iMaxReqNumber = (b.getOpenedRequests()>iMaxReqNumber) ? b.getOpenedRequests() : iMaxReqNumber;
					iMaxReqNumber = (b.getClosedRequests()>iMaxReqNumber) ? b.getClosedRequests() : iMaxReqNumber;
					
					break;
				}
			}
			
			// if date was not found in recordset, add empty date
			if(!bFound) 
			{
				OpenClosedReqBean newBean = new OpenClosedReqBean();
				newBean.setReportDate(curDay);
				rows.add(newBean);
			}
		}
		
		// create model for bar chart
		this.createLineModel(iMaxReqNumber);
		
	}
	
	// add graph series
    private LineChartModel initCategoryModel() {
    	LineChartModel model = new LineChartModel();
 
        ChartSeries openSeries = new ChartSeries();
        openSeries.setLabel("Opened Requests");

        ChartSeries closedSeries = new ChartSeries();
        closedSeries.setLabel("Closed Requests");
        
        for(OpenClosedReqBean b : this.rows)
        {
        	openSeries.set(b.getChartReportDate(), b.getOpenedRequests());
        	closedSeries.set(b.getChartReportDate(), b.getClosedRequests());
        }
 
        model.addSeries(openSeries);
        model.addSeries(closedSeries);
         
        return model;
    }
	
    // configure graph model object
    private void createLineModel(int iMaxReqNumber) {
        this.lineModel = initCategoryModel();
        this.lineModel.setTitle("Open-Closed Requests By Day");
        this.lineModel.setLegendPosition("ne");
        this.lineModel.setShowPointLabels(true);
        this.lineModel.getAxes().put(AxisType.X, new CategoryAxis("Days of Month"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number of Requests");
        yAxis.setMin(0);
    	yAxis.setTickFormat("%#d");
        
    		// calculate maximum interval for Y axis (we need it because graph tends to add decimal tick values)
        	if(iMaxReqNumber<5)
        	{
        		yAxis.setTickInterval("1");
        	}
        	else
        	{
        		// if max Y value is more than 50 then no need to play with intervals, leave the default behavior
        		for(int i=10;i<=50;i+=10)
        		{
        			if(iMaxReqNumber<i)
        			{
        				yAxis.setMax(i);
        				int iInt = i/10;
        				yAxis.setTickInterval(Integer.toString(iInt));
        				break;
        			}
        		}
        	}

    }
	
    public LineChartModel getLineModel() {
        return lineModel;
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
	
	// <!--  <h:commandButton id="rptExportCsvCustom" class="pull-right" value="Export Table to CSV" rendered="#{not empty openClosedRequestsByDay.rows}" action="#{openClosedRequestsByDay.exportCsv}" />  -->
	public void exportCsv()
	{
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
