package gov.ed.fsa.drts.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// used for Open-Closed Requests Report
public class OpenClosedReqBean {
	
	private Date reportDate = null;
	private int openedRequests = 0;
	private int closedRequests = 0;
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public int getOpenedRequests() {
		return openedRequests;
	}
	public void setOpenedRequests(int openedRequests) {
		this.openedRequests = openedRequests;
	}
	public int getClosedRequests() {
		return closedRequests;
	}
	public void setClosedRequests(int closedRequests) {
		this.closedRequests = closedRequests;
	}
	
	// used for table output
	public String getFormattedReportDate()
	{
		if(this.reportDate == null) return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		return sdf.format(this.reportDate);
	}
	
	// used for chart output
	public String getChartReportDate()
	{
		if(this.reportDate == null) return "";
		
		// extract day from date
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.reportDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		// default format
		String strFormat = "dd";
		
		// if first day of month - add month name
		if(day==1)
		{
			strFormat = "MMM";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		return sdf.format(this.reportDate);
	}

}
