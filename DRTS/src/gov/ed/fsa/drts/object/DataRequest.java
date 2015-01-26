package gov.ed.fsa.drts.object;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class DataRequest {

	private String id = null;
	private Date created_date_time = null;
	private String drts_requestor = null;
	private String status = null;
	private String type = null;
	private int iteration = 1;
	private Date due_date = null;
	private boolean urgent = false;
	private String related_requests = null;
	private String topic_keywords = null;
	private String description = null;
	private String purpose = null;
	private String special_considerations_issues = null;
	private String requestor_name = null;
	private String requestor_organization = null;
	private String requestor_phone = null;
	private String requestor_email = null;
	private String receiver_name = null;
	private String receiver_email = null;
	
	private String assigned_sme = null;
	private Date date_assigned_to_sme = null;
	private String administrator_comments = null;
	
	private Date date_resolved = null;
	private String resolution = null;
	private String sme_comments = null;
	
	private String current_task_id = null;
	private String current_task_name = null;
	private String current_task_form_key = null;
	
	private String candidate_group = null;
	private String assignee = null;
	
	private String assigned_validator = null;
	private Date date_assigned_to_validator = null;
	private Date date_validated = null;
	
	private Date date_closed = null;
	
	private int display_id = 1;
	
	public DataRequest(){}
	
	public void initialize(String created_by)
	{
		this.id = UUID.randomUUID().toString();
		this.iteration = 1;
		this.created_date_time = new Date();
		this.status = ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue();
		this.drts_requestor = created_by;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public Date getCreatedDateTime()
	{
		return this.created_date_time;
	}
	
	public void setCreatedDateTime(Date created_date_time)
	{
		this.created_date_time = created_date_time;
	}
	
	public String getDrtsRequestor()
	{
		return this.drts_requestor;
	}
	
	public void setDrtsRequestor(String drts_requestor)
	{
		this.drts_requestor = drts_requestor;
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getCurrentTaskId()
	{
		return this.current_task_id;
	}
	
	public void setCurrentTaskId(String current_task_id)
	{
		this.current_task_id = current_task_id;
	}
	
	public String getCurrentTaskName()
	{
		return this.current_task_name;
	}
	
	public void setCurrentTaskName(String current_task_name)
	{
		this.current_task_name = current_task_name;
	}
	
	public String getCurrentTaskFormKey()
	{
		return this.current_task_form_key;
	}
	
	public void setCurrentTaskFormKey(String current_task_form_key)
	{
		this.current_task_form_key = current_task_form_key;
	}

	public int getIteration()
	{
		return iteration;
	}

	public void setIteration(int iteration)
	{
		this.iteration = iteration;
	}

	public Date getDueDate()
	{
		return due_date;
	}

	public void setDueDate(Date due_date)
	{
		this.due_date = due_date;
	}

	public boolean isUrgent()
	{
		return urgent;
	}

	public void setUrgent(boolean urgent)
	{
		this.urgent = urgent;
	}

	public String getRelatedRequests()
	{
		return related_requests;
	}

	public void setRelatedRequests(String related_requests)
	{
		this.related_requests = related_requests;
	}

	public String getTopicKeywords()
	{
		return topic_keywords;
	}

	public void setTopicKeywords(String topic_keywords)
	{
		this.topic_keywords = topic_keywords;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}

	public String getSpecialConsiderationsIssues()
	{
		return special_considerations_issues;
	}

	public void setSpecialConsiderationsIssues(String special_considerations_issues)
	{
		this.special_considerations_issues = special_considerations_issues;
	}

	public String getRequestorName()
	{
		return requestor_name;
	}

	public void setRequestorName(String requestor_name)
	{
		this.requestor_name = requestor_name;
	}

	public String getRequestorOrganization()
	{
		return requestor_organization;
	}

	public void setRequestorOrganization(String requestor_organization)
	{
		this.requestor_organization = requestor_organization;
	}

	public String getRequestorPhone()
	{
		return requestor_phone;
	}

	public void setRequestorPhone(String requestor_phone)
	{
		this.requestor_phone = requestor_phone;
	}

	public String getRequestorEmail()
	{
		return requestor_email;
	}

	public void setRequestorEmail(String requestor_email)
	{
		this.requestor_email = requestor_email;
	}

	public String getReceiverName()
	{
		return receiver_name;
	}

	public void setReceiverName(String receiver_name)
	{
		this.receiver_name = receiver_name;
	}

	public String getReceiverEmail()
	{
		return receiver_email;
	}

	public void setReceiverEmail(String receiver_email)
	{
		this.receiver_email = receiver_email;
	}

	public String getAssignedSme()
	{
		return assigned_sme;
	}

	public void setAssignedSme(String assigned_sme)
	{
		this.assigned_sme = assigned_sme;
	}

	public Date getDateAssignedToSme()
	{
		return date_assigned_to_sme;
	}

	public void setDateAssignedToSme(Date date_assigned_to_sme)
	{
		this.date_assigned_to_sme = date_assigned_to_sme;
	}

	public String getAdministratorComments()
	{
		return administrator_comments;
	}

	public void setAdministratorComments(String administrator_comments)
	{
		this.administrator_comments = administrator_comments;
	}

	public Date getDateResolved()
	{
		return date_resolved;
	}

	public void setDateResolved(Date date_resolved)
	{
		this.date_resolved = date_resolved;
	}

	public String getResolution()
	{
		return resolution;
	}

	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}

	public String getSmeComments()
	{
		return sme_comments;
	}

	public void setSmeComments(String sme_comments)
	{
		this.sme_comments = sme_comments;
	}

	public String getDisplayId()
	{
		String result = "";
		
		Calendar cal = Calendar.getInstance();
	    cal.setTime(this.created_date_time);
	    
	    int year = cal.get(Calendar.YEAR);
	    
	    result += year + "-" + this.display_id + "-D";
	    
	    return result;
	}
	
	public void setDisplayId(int display_id)
	{
		this.display_id = display_id;
	}

	public String getCandidateGroup()
	{
	    return this.candidate_group;
	}
	
	public void setCandidateGroup(String candidate_group)
	{
		this.candidate_group = candidate_group;
	}
	
	public String getAssignee()
	{
	    return this.assignee;
	}
	
	public void setAssignee(String assignee)
	{
		this.assignee = assignee;
	}
	
	public String getAssignedValidator()
	{
		return assigned_validator;
	}

	public void setAssignedValidator(String assigned_validator)
	{
		this.assigned_validator = assigned_validator;
	}

	public Date getDateAssignedToValidator()
	{
		return date_assigned_to_validator;
	}

	public void setDateAssignedToValidator(Date date_assigned_to_validator)
	{
		this.date_assigned_to_validator = date_assigned_to_validator;
	}
	
	public Date getDateValidated()
	{
		return date_validated;
	}

	public void setDateValidated(Date date_validated)
	{
		this.date_validated = date_validated;
	}
	
	public Date getDateClosed()
	{
		return date_closed;
	}

	public void setDateClosed(Date date_closed)
	{
		this.date_closed = date_closed;
	}
}
