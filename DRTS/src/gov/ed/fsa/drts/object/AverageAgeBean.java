package gov.ed.fsa.drts.object;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AverageAgeBean {
	private Date reportDate = null;
	private int requestNumber = 0;
	private int totalAge = 0;
	private int averageAge = 0;
	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public int getTotalAge() {
		return totalAge;
	}
	public void setTotalAge(int totalAge) {
		this.totalAge = totalAge;
	}
	public int getAverageAge() {
		return averageAge;
	}
	public void setAverageAge(int averageAge) {
		this.averageAge = averageAge;
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
