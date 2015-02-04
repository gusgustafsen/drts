package gov.ed.fsa.drts.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class overdueReportBean {
	
	private String trackingNumber = "";
	private String description = "";
	private String requestorName = "";
	private String status = "";
	private String urgencyFlag = "";
	private Date requestedDueDate = null;
	private String sme = "";
	private String requestType = "";
	private String systemName = "";

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescriptionTruncated() {
		if((description != null)&&(description.length()>15))
		return (description.substring(0, 15) + "...");
		else return description;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrgencyFlag() {
		return urgencyFlag;
	}

	public void setUrgencyFlag(String urgencyFlag) {
		this.urgencyFlag = urgencyFlag;
	}

	public Date getRequestedDueDate() {
		return requestedDueDate;
	}

	public void setRequestedDueDate(Date requestedDueDate) {
		this.requestedDueDate = requestedDueDate;
	}

	public String getSme() {
		return sme;
	}

	public void setSme(String sme) {
		this.sme = sme;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	// used for table output
	public String getFormattedDueDate()
	{
		if(this.requestedDueDate == null) return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		return sdf.format(this.requestedDueDate);
	}
	
	// used for chart output
	public String getChartDueDate()
	{
		if(this.requestedDueDate == null) return "";
		
		// extract day from date
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.requestedDueDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		// default format
		String strFormat = "dd";
		
		// if first day of month - add month name
		if(day==1)
		{
			strFormat = "MMM";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		return sdf.format(this.requestedDueDate);
	}

}
