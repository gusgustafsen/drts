package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "averageAgeReport")
@SessionScoped
public class averageAgeReport implements Serializable {
	
	private BarChartModel barModel;

	private String errorMessage = "";
	private List<AverageAgeBean> rows = new ArrayList<AverageAgeBean>();
	private String sortField = "REPORT_DATE";
	private boolean sortAsc = false;

	public List<AverageAgeBean> getRows() {
		return rows;
	}

	public void setRows(List<AverageAgeBean> rows) {
		this.rows = rows;
	}
	
	@PostConstruct
	public void init() {
		this.errorMessage = "";
		
		rows = new ArrayList<AverageAgeBean>();
		
		// max requests number, need for graph Y axis limit
		int iMaxAge = 0;
		
		// get the list of report beans from the DB
		List<AverageAgeBean> dbBeans = null;
		try {
			for(int iDay=29;iDay>=0;iDay--)
			{
				dbBeans = DataLayer.getInstance().getAverageAgeReport(iDay);
				if(dbBeans!=null)
				{
					AverageAgeBean bn = dbBeans.get(0);
					if(bn!=null)
					{
						this.rows.add(bn);
						iMaxAge = (bn.getTotalAge()>iMaxAge) ? bn.getTotalAge() : iMaxAge;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.errorMessage = "Error connecting to database. Please try again. If problem persists, notify DRTS administrator.";
		}
		
		// create model for bar chart
		this.createBarModel(iMaxAge);
		
	}
	
	// add graph series
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries avgSeries = new ChartSeries();
        avgSeries.setLabel("Average Age of Requests");
        
        for(AverageAgeBean b : this.rows)
        {
        	avgSeries.set(b.getChartReportDate(), b.getAverageAge());
        }
 
        model.addSeries(avgSeries);
         
        return model;
    }
	
    // configure graph model object
    private void createBarModel(int iMaxReqNumber) {
        this.barModel = initBarModel();
         
        this.barModel.setTitle("Average Age Of Requests By Day");
        barModel.setLegendPosition("ne");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Days of Month");
         
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Age, Days");
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
	
    public BarChartModel getBarModel() {
        return barModel;
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
