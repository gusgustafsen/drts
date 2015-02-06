package gov.ed.fsa.drts.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Report4AverageAgeBean {
	
	private Date reportDate = null;
	private int requestNumber = 0;
	private int totalAge = 0;
	private int averageAge = 0;
	
	public Date getReportDate()
	{
		return reportDate;
	}
	
	public void setReportDate(Date reportDate)
	{
		this.reportDate = reportDate;
	}
	
	public int getRequestNumber()
	{
		return requestNumber;
	}
	
	public void setRequestNumber(int requestNumber)
	{
		this.requestNumber = requestNumber;
	}
	
	public int getTotalAge()
	{
		return totalAge;
	}
	
	public void setTotalAge(int totalAge)
	{
		this.totalAge = totalAge;
	}
	
	public int getAverageAge()
	{
		return averageAge;
	}
	
	public void setAverageAge(int averageAge)
	{
		this.averageAge = averageAge;
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
