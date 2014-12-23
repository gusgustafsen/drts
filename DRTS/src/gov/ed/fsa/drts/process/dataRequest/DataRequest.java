package gov.ed.fsa.drts.process.dataRequest;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.object.DRTSTask;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class DataRequest extends PageUtil implements Serializable {
	
	private static final long serialVersionUID = -7902190226399221295L;

	private static final Logger logger = Logger.getLogger(DataRequest.class);
	
	private transient ProcessEngine process_engine = null;
	private transient RuntimeService runtime_service = null;
	private transient TaskService task_service = null;
	private transient IdentityService identity_service = null;
	
	private DRTSTask current_task = null;
	
	private String request_id = null;
	private int request_type;
	private Date request_created_date = new Date();
	private String request_description = null;
	private boolean request_urgent = false;
	private String requestor_email = null;
	private String request_created_by = null;
	private String assigned_sme = null;
	
	private Map<String, Integer> request_types;
	
	private Map<String, String> email_replace_tokens = new HashMap<String, String>();
	
	@PostConstruct
	private void init()
	{
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
		
		this.email_replace_tokens = new HashMap<String, String>();
		
		this.current_task = (DRTSTask) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsTask");
		
		if(this.current_task != null)
		{
			Map<String, Object> variables = this.runtime_service.getVariables(this.current_task.getActivitiTask().getProcessInstanceId());
			
			if(variables != null)
			{
				Iterator<Map.Entry<String, Object>> it = variables.entrySet().iterator();
				
			    while(it.hasNext())
			    {
			    	Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
			        logger.info("\t" + pairs.getKey() + " = " + pairs.getValue());
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue()))
			        {
			        	this.request_id = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()))
			        {
			        	this.request_type = (Integer) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE.getStringValue()))
			        {
			        	this.request_created_date = (Date) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()))
			        {
			        	this.request_description = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue()))
			        {
			        	this.request_urgent = (Boolean) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()))
			        {
			        	this.requestor_email = (String) pairs.getValue();
			        }
			        
			        if(pairs.getKey().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue()))
			        {
			        	this.request_created_by = (String) pairs.getValue();
			        }
			    }
			}
		}
	}

	public void start()
	{
		Map<String, Object> form_variables = new HashMap<String, Object>();
		
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue(), this.request_id);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.request_type);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE.getStringValue(), this.request_created_date);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.request_description);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(), this.request_urgent);
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.userSession.getUser().getId());
		form_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue(), this.requestor_email);
		
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
	}
	
	public String assignNewRequestToSME()
	{
		String sme_email = "tim.asanov2013+sme@gmail.com";
		
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_NEW_REQUEST_REJECTED.getStringValue(), false);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue(), this.assigned_sme);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME_EMAIL.getStringValue(), sme_email);
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME_FULL_NAME.getStringValue(), "SME 1");
		
		updateEmailReplacementValues();
		
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_TO.getStringValue(), sme_email);
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue());
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), this.email_replace_tokens));
		task_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), this.email_replace_tokens));
		
		this.task_service.complete(this.current_task.getId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	public String rejectNewRequest()
	{
		Map<String, Object> task_variables = new HashMap<String, Object>();
		task_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_NEW_REQUEST_REJECTED.getStringValue(), true);
		
		this.task_service.complete(this.current_task.getId(), task_variables);
		
		return userSession.getUser().getHomePage() + "?faces-redirect=true";
	}
	
	private void updateEmailReplacementValues()
	{
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue(), this.request_id);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), Integer.toString(this.request_type));
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE.getStringValue(), this.request_created_date.toString());
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.request_description);
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(), Boolean.toString(this.request_urgent));
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.userSession.getUser().getId());
		this.email_replace_tokens.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue(), this.requestor_email);
	}
	
	@PreDestroy
	private void destroy(){}

	public String getRequestId()
	{
		return request_id;
	}

	public void setRequestId(String request_id)
	{
		this.request_id = request_id;
	}

	public int getRequestType()
	{
		return request_type;
	}

	public void setRequestType(int request_type)
	{
		this.request_type = request_type;
	}

	public Date getRequestCreatedDate()
	{
		return request_created_date;
	}

	public void setRequestCreatedDate(Date request_created_date)
	{
		this.request_created_date = request_created_date;
	}
	
	public String getRequestCreatedBy()
	{
		return request_created_by;
	}

	public void setRequestCreatedBy(String request_created_by)
	{
		this.request_created_by = request_created_by;
	}

	public String getRequestDescription()
	{
		return request_description;
	}

	public void setRequestDescription(String request_description)
	{
		this.request_description = request_description;
	}

	public boolean isRequestUrgent()
	{
		return request_urgent;
	}

	public void setRequestUrgent(boolean request_urgent)
	{
		this.request_urgent = request_urgent;
	}

	public String getRequestorEmail()
	{
		return requestor_email;
	}

	public void setRequestorEmail(String requestor_email)
	{
		this.requestor_email = requestor_email;
	}
	
	public String getAssignedSme()
	{
		return assigned_sme;
	}

	public void setAssignedSme(String assigned_sme)
	{
		this.assigned_sme = assigned_sme;
	}

	public Map<String, Integer> getRequestTypes()
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
	
	public Map<String, String> getSmes()
	{
		// TODO move to utils or somewhere else
		
		List<User> sme_users = this.identity_service.createUserQuery().memberOfGroup("sme").list();
		
		Map<String, String> smes = new LinkedHashMap<String, String>();
		
		smes.put("", "");
		
		for(User sme : sme_users)
		{
			smes.put(sme.getFirstName() + " " + sme.getLastName(), sme.getId());
		}
		
		return smes;
	}
}
