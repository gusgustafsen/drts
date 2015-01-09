package gov.ed.fsa.drts.process.dataRequest;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;

@ManagedBean(name = "dataRequest")
@ViewScoped
public class DataRequestBean extends PageUtil implements Serializable {
	
	private static final long serialVersionUID = -7902190226399221295L;

	private static final Logger logger = Logger.getLogger(DataRequestBean.class);
	
	private transient ProcessEngine process_engine = null;
	private transient RuntimeService runtime_service = null;
	private transient TaskService task_service = null;
	private transient IdentityService identity_service = null;
	
	private DataRequest current_data_request = null;
	
	private String id = UUID.randomUUID().toString();
	private int iteration = 1;
	private int status = Integer.parseInt(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
	private Date created_date_time = new Date();
	private int type;
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
	
	private String created_by = null;
	
	private String assigned_sme = null;
	private Date date_assigned_to_sme = new Date();
	private String administrator_comments = null;
	
	private Date date_resolved = new Date();
	private String resolution = null;
	private String sme_comments = null;
	
	private Map<String, Integer> request_types;
	
	private Map<String, String> email_replace_tokens = new HashMap<String, String>();
	
	Map<String, User> sme_users = null;
	
	
	@PostConstruct
	private void init()
	{
		this.email_replace_tokens = new HashMap<String, String>();
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.runtime_service = this.process_engine.getRuntimeService();
			
			if(this.runtime_service == null)
			{
				// TODO handle error
			}

			this.task_service = this.process_engine.getTaskService();
			
			if(this.task_service == null)
			{
				// TODO handle error
			}

			this.identity_service = this.process_engine.getIdentityService();
			
			if(this.identity_service == null)
			{
				// TODO handle error
			}
		}
		else
		{
			// TODO handle error
		}
		
		this.current_data_request = (DataRequest) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsDataRequest");
		setCurrentDataRequestVariables();
	}

	public String start()
	{
		Map<String, Object> form_variables = new HashMap<String, Object>();
		
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue(), this.id);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue(), this.iteration);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue(), this.due_date);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue(), this.related_requests);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue(), this.topic_keywords);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue(), this.purpose);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue(), this.special_considerations_issues);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.type);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.description);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(), this.urgent);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue(), this.created_date_time);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.userSession.getUser().getId());
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue(), this.requestor_name);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue(), this.requestor_organization);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue(), this.requestor_phone);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue(), this.requestor_email);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue(), this.receiver_name);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue(), this.receiver_email);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), this.status);
		
		updateEmailReplacementValues();
		
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_TO.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_TO.getStringValue());
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_CC.getStringValue());
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_FROM.getStringValue());
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_SUBJECT.getStringValue(), this.email_replace_tokens));
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_CONTENT.getStringValue(), this.email_replace_tokens));
		
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_TO.getStringValue(), this.requestor_email);
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_CC.getStringValue());
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_FROM.getStringValue());
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_SUBJECT.getStringValue(), this.email_replace_tokens));
		form_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_CONTENT.getStringValue(), this.email_replace_tokens));
		
		this.runtime_service.startProcessInstanceByKey(ApplicationProperties.PROCESS_ID_DATA_REQUEST.getStringValue(), form_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	
	public String assignNewRequestToSME()
	{
		User assigned_sme = this.sme_users.get(this.assigned_sme);
		
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_NEW_REQUEST_REJECTED.getStringValue(), false);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue(), assigned_sme.getId());
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue(), this.administrator_comments);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME_FULL_NAME.getStringValue(), assigned_sme.getFirstName() + " " + assigned_sme.getLastName());
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue());
		
		updateEmailReplacementValues();
		
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_TO.getStringValue(), assigned_sme.getEmail());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), this.email_replace_tokens));
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), this.email_replace_tokens));
		
		this.task_service.complete(this.current_data_request.getTaskId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	
	public String rejectNewRequest()
	{
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_NEW_REQUEST_REJECTED.getStringValue(), true);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue(), this.administrator_comments);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue());
		
		this.task_service.complete(this.current_data_request.getTaskId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	
	public String rejectRequest()
	{
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUEST_REJECTED.getStringValue(), true);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_SME_COMMENTS.getStringValue(), this.sme_comments);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue());
		
		this.task_service.complete(this.current_data_request.getTaskId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	
	public String resolveRequest()
	{
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUEST_REJECTED.getStringValue(), false);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_SME_COMMENTS.getStringValue(), this.sme_comments);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue());
		
		updateEmailReplacementValues();
		
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_TO.getStringValue(), getAdminEmails());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_CC.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_FROM.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_SUBJECT.getStringValue(), this.email_replace_tokens));
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_CONTENT.getStringValue(), this.email_replace_tokens));
		
		this.task_service.complete(this.current_data_request.getTaskId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	
	private void updateEmailReplacementValues()
	{
		System.out.println("started updateEmailReplacementValues");
		
		String created_by = this.created_by;
		
		if(created_by == null)
		{
			created_by = this.userSession.getUser().getId();
		}
		
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue(), this.id);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), Integer.toString(this.type));
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.description);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(), Boolean.toString(this.urgent));
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue(), this.created_date_time.toString());
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), created_by);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue(), this.requestor_email);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue(), this.administrator_comments);
	}
	
	public Map<String, Integer> getTypes()
	{
		if(this.request_types == null)
		{
			// TODO move to properties
			request_types = new LinkedHashMap<String, Integer>();
			request_types.put("Type 1", 1);
			request_types.put("Type 2", 2);
			request_types.put("Type 3", 3);
		}
		
		return this.request_types;
	}
	
	private void setSMEUsers()
	{
		List<User> users = this.identity_service.createUserQuery().memberOfGroup("sme").list();
		
		if(users != null && users.size() > 0)
		{
			this.sme_users = new LinkedHashMap<String, User>();
		
			for(User sme : users)
			{
				this.sme_users.put(sme.getId(), sme);
			}
		}
	}
	
	public Map<String, String> getSmes()
	{
		setSMEUsers();
		
		// TODO order alphabetically
		Map<String, String> sme_dropdown = null;
			
		if(this.sme_users != null && this.sme_users.size() > 0)
		{
			sme_dropdown = new HashMap<String, String>();
			
			sme_dropdown.put("", "");
				
			Iterator<Map.Entry<String, User>> it = this.sme_users.entrySet().iterator();
			
			while(it.hasNext())
			{
				Map.Entry<String, User> pairs = (Map.Entry<String, User>) it.next();
				
				sme_dropdown.put(pairs.getValue().getFirstName() + " " + pairs.getValue().getLastName(), pairs.getKey());
			}
		}
			
		return sme_dropdown;
	}
	
	private String getAdminEmails()
	{
		StringBuilder sb = new StringBuilder();
	    String email_list_delimiter = "";
		
		List<User> users = this.identity_service.createUserQuery().memberOfGroup("admin").list();
		
		if(users != null && users.size() > 0)
		{
			for(User user : users)
			{
				sb.append(email_list_delimiter);
		        sb.append(user.getEmail());            
		 
		        // TODO move to properties
		        email_list_delimiter = ",";
		    }
		}
		
		System.out.println("admin emails: " + sb.toString());
		
		return sb.toString();
	}

	private void setCurrentDataRequestVariables()
	{
		if(this.current_data_request != null && this.current_data_request.getProcessInstanceId() != null)
		{
			Map<String, Object> variables = this.runtime_service.getVariables(this.current_data_request.getProcessInstanceId());
			
			if(variables != null)
			{
				Iterator<Map.Entry<String, Object>> it = variables.entrySet().iterator();
				
			    while(it.hasNext())
			    {
			    	Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue()))
			        {
			        	this.id = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()))
			        {
			        	this.type = (Integer) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()))
			        {
			        	this.description = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue()))
			        {
			        	this.urgent = (Boolean) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()))
			        {
			        	this.requestor_email = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue()))
			        {
			        	this.created_by = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue()))
			        {
			        	this.created_date_time = (Date) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue()))
			        {
			        	this.administrator_comments = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_SME_COMMENTS.getStringValue()))
			        {
			        	this.sme_comments = (String) pairs.getValue();
			        }
			    }
			}
			
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", null);
		}
		else
		{
			logger.error("Current request is null");
		}
	}
	
	
	@PreDestroy
	private void destroy(){}
	

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getIteration()
	{
		return iteration;
	}

	public void setIteration(int iteration)
	{
		this.iteration = iteration;
	}
	
	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
	
	public Date getCreatedDateTime()
	{
		return created_date_time;
	}

	public void setCreatedDateTime(Date created_date_time)
	{
		this.created_date_time = created_date_time;
	}
	
	public Date getDueDate()
	{
		return due_date;
	}

	public void setDueDate(Date due_date)
	{
		this.due_date = due_date;
	}
	
	public String getCreatedBy()
	{
		return created_by;
	}

	public void setCreatedBy(String created_by)
	{
		this.created_by = created_by;
	}

	public String getRelatedRequests()
	{
		return related_requests;
	}

	public void setRelatedRequests(String related_requests)
	{
		this.related_requests = related_requests;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTopicKeywords()
	{
		return topic_keywords;
	}

	public void setTopicKeywords(String topic_keywords)
	{
		this.topic_keywords = topic_keywords;
	}
	
	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}
	
	public boolean isUrgent()
	{
		return urgent;
	}

	public void setUrgent(boolean urgent)
	{
		this.urgent = urgent;
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
		return this.date_assigned_to_sme;
	}
	
	public void setDateAssignedToSme(Date date_assigned_to_sme)
	{
		this.date_assigned_to_sme = date_assigned_to_sme;
	}
	
	public String getAdministratorComments()
	{
		return this.administrator_comments;
	}
	
	public void setAdministratorComments(String administrator_comments)
	{
		this.administrator_comments = administrator_comments;
	}
	
	public Date getDateResolved()
	{
		return this.date_resolved;
	}
	
	public void setDateResolved(Date date_resolved)
	{
		this.date_resolved = date_resolved;
	}
	
	public String getResolution()
	{
		return this.resolution;
	}
	
	public void setResolution(String resolution)
	{
		this.resolution = resolution;
	}
	
	public String getSmeComments()
	{
		return this.sme_comments;
	}
	
	public void setSmeComments(String sme_comments)
	{
		this.sme_comments = sme_comments;
	}
}
