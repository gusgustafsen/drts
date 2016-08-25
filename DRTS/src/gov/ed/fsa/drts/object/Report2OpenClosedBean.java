package gov.ed.fsa.drts.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Report2OpenClosedBean {
	
	private Date reportDate = null;
	private int openedRequests = 0;
	private int closedRequests = 0;
	
	public Date getReportDate()
	{
		return reportDate;
	}
	
	public void setReportDate(Date reportDate)
	{
		this.reportDate = reportDate;
	}
	
	public int getOpenedRequests()
	{
		return openedRequests;
	}
	
	public void setOpenedRequests(int openedRequests)
	{
		this.openedRequests = openedRequests;
	}
	
	public int getClosedRequests()
	{
		return closedRequests;
	}
	
	public void setClosedRequests(int closedRequests)
	{
		this.closedRequests = closedRequests;
	}
	
	public String getChartReportDate()
	{
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(this.reportDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String strFormat = "dd";
		
		if(day == 1)
		{
			strFormat = "MMM";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		
		return sdf.format(this.reportDate);
	}
}