package gov.ed.fsa.drts.process.dataRequest;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Attachment;
import gov.ed.fsa.drts.object.AuditField;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

/**
 * Managed bean that controls a data request workflow.
 *
 * @author Timur Asanov | tasanov@ppsco.com
 */
@ManagedBean(name = "dataRequest")
@ViewScoped
public class DataRequestBean extends PageUtil implements Serializable {
	
	private static final long serialVersionUID = -7902190226399221295L;

	/**
	 * Log4j logger.
	 */
	private static final Logger logger = Logger.getLogger(DataRequestBean.class);
	
	/**
	 * Activiti process engine.
	 */
	private transient ProcessEngine process_engine = null;
	
	/**
	 * Activiti runtime service.
	 */
	private transient RuntimeService runtime_service = null;
	
	/**
	 * Activiti task service.
	 */
	private transient TaskService task_service = null;
	
	/**
	 * Activiti identity service.
	 */
	private transient IdentityService identity_service = null;
	
	/**
	 * Data request that the bean is working with.
	 */
	private DataRequest current_data_request = null;
	
	// TODO incorporate upload into JSF and into the same form as request
	// TODO files are uploaded when chose, so if user doesn't save request need to delete the uploaded file
	/**
	 * List of attachments associated with the current request.
	 */
	private List<Attachment> request_attachments = null;

	/**
	 * Assigned SME user, from the JSF form.
	 */
	private String assigned_sme = null;
	
	/**
	 * Assigned Validator user, from the JSF form.
	 */
	private String assigned_validator = null;
	
	/**
	 * New comments field, from the JSF form.
	 */
	private String new_comments = null;
	
	private String original_description = null;
	private String original_sme = null;
	private String original_status = null;
	private String original_type = null;
	private String original_system = null;
	
	/**
	 * A map of tokens that have to be replaced with request values 
	 * in the emails that are sent.
	 */
	private Map<String, String> email_replace_tokens = new HashMap<String, String>();
	
	// TODO split request and workflow variables, then sent the data request to data layer instead of map
	/**
	 * Request and workflow variables map.
	 */
	private Map<String, Object> request_variables = new HashMap<String, Object>();
	
	/**
	 * A map of SME users, with the key = ID and value = First Name Last Name
	 */
	private Map<String, String> sme_users_names = null;

	/**
	 * A map of SME users, with the key = ID and value = Email
	 */
	private Map<String, String> sme_users_emails = null;
	
	/**
	 * A map of users that can create requests, with the key = ID and value = Email
	 */
	private Map<String, String> creator_users_emails = null;
	
	/**
	 * Bean constructor.
	 */
	@PostConstruct
	private void init()
	{	
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		this.runtime_service = this.process_engine.getRuntimeService();
		this.task_service = this.process_engine.getTaskService();
		this.identity_service = this.process_engine.getIdentityService();

		this.current_data_request = (DataRequest) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsDataRequest");
		
		if(this.current_data_request == null)
		{
			this.current_data_request = new DataRequest();
			this.current_data_request.initialize(this.userSession.getUser().getId());
		}
		else
		{
			if(this.current_data_request.getParentId() == null)
			{
				try
				{
					this.request_attachments = DataLayer.getInstance().getAttachmentByRequestID(this.current_data_request.getId());
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		this.original_description = this.current_data_request.getDescription();
		this.original_sme = this.current_data_request.getAssignedSme();
		this.original_status = this.current_data_request.getStatus();
		this.original_type = this.current_data_request.getType();
		this.original_system = this.current_data_request.getSystem();
		
		setSMEUsers();
		setCreators();
	}
	
	/**
	 * This method handles all actions that are performed by users on a 
	 * data request.
	 * 
	 * @param action_type_long action type passed by a JSF action
	 * @return Returns the user to their home page.
	 */
	public String updateRequest(Long action_type_long)
	{
		String status = this.current_data_request.getStatus();
		String candidate_group = this.current_data_request.getCandidateGroup();
		String assignee = this.current_data_request.getAssignee();
		String assigned_sme = this.current_data_request.getAssignedSme();
		String assigned_validator = this.current_data_request.getAssignedValidator();
		boolean complete_task = false;
		boolean start_new_request = false;
		
		int action_type = action_type_long.intValue();
		
		logger.info("Performing request action #" + action_type);
		
		// TODO change to ENUM if possible
		switch(action_type)
		{	
			// user created a new request as drafted
			case 1:
				status = ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_DRAFTED.getStringValue(), 1);
				this.current_data_request.setCreatedDateTime(new Date());
				candidate_group = null;
				assignee = this.userSession.getUser().getId();
				start_new_request = true;
				break;
			
			// user created a new request and submitted
			case 2:
				status = ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_DRAFTED.getStringValue(), 2);
				this.current_data_request.setCreatedDateTime(new Date());
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				start_new_request = true;
				break;
		
			// user submitted a drafted request
			case 3:
				status = ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_DRAFTED_SUBMITTED.getStringValue(), 1);
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
				
			case 19:
				status = ApplicationProperties.DATA_REQUEST_STATUS_DISCARDED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_DRAFTED_SUBMITTED.getStringValue(), 2);
				candidate_group = null;
				assignee = null;
				complete_task = true;
				break;
				
			// administrator updated a new request
			case 4:
				// if the assigned SME was picked, then the request will be assigned to that SME
				if(Utils.isStringEmpty(this.assigned_sme) == false)
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_ADMIN.getStringValue(), 1);
					this.current_data_request.setDateAssignedToSme(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = this.assigned_sme;
					assigned_sme = this.assigned_sme;
					complete_task = true;
				}
				if(Utils.isStringEmpty(this.assigned_validator) == false)
				{
					assigned_validator = this.assigned_validator;
				}
				break;
				
			// administrator rejected a request
			case 5:
				status = ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_ADMIN.getStringValue(), 2);
				this.current_data_request.setDateClosed(new Date());
				complete_task = true;
				candidate_group = null;
				assignee = null;
				break;
			
			// administrator or SME updated a request
			case 6:
				// if the assigned SME was changed, then the request will be reassigned to a new SME
				if((Utils.isStringEmpty(this.assigned_sme) == false) &&
						(this.assigned_sme.equalsIgnoreCase(this.current_data_request.getAssignedSme()) == false))
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_SME.getStringValue(), 3);
					this.current_data_request.setDateAssignedToSme(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = this.assigned_sme;
					assigned_sme = this.assigned_sme;
					complete_task = true;
				}
				break;
			
			// SME resolved a request
			case 7:
				// if a validator has not been picked by administrator before
				if(Utils.isStringEmpty(this.current_data_request.getAssignedValidator()) == true)
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_SME.getStringValue(), 1);
					this.current_data_request.setDateResolved(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = null;
					complete_task = true;
				}
				// if a validator has already been picked by administrator
				else
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_SME.getStringValue(), 4);
					this.current_data_request.setDateAssignedToValidator(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = this.current_data_request.getAssignedValidator();
					assigned_validator = this.current_data_request.getAssignedValidator();
					complete_task = true;
				}
				break;
			
			// SME rejected a request
			case 8:
				status = ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_REJECTED_BY_SME.getStringValue(), 2);
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
			
			// administrator updated a resolved request
			case 9:
				// if the assigned validator was picked, then the request will be assigned to that validator
				if(Utils.isStringEmpty(this.assigned_validator) == false)
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_ASSIGNED_TO_VALIDATOR.getStringValue(), 1);
					this.current_data_request.setDateAssignedToValidator(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = this.assigned_validator;
					assigned_validator = this.assigned_validator;
					complete_task = true;
				}
				break;
			
			// administrator changed a resolved request to the "Pending Requestor Approval" status
			case 10:
				status = ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_ASSIGNED_TO_VALIDATOR.getStringValue(), 2);
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
			
			// administrator closed a resolved request
			case 11:
				status = ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_ASSIGNED_TO_VALIDATOR.getStringValue(), 3);
				this.current_data_request.setDateClosed(new Date());
				candidate_group = null;
				assignee = null;
				complete_task = true;
				break;
			
			// administrator or validator updated a resolved request
			case 12:
				// if the assigned validator was changed, then the request will be reassigned to a new validator
				if((Utils.isStringEmpty(this.assigned_validator) == false) &&
						(this.assigned_validator.equalsIgnoreCase(this.current_data_request.getAssignedValidator()) == false))
				{
					status = ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue();
					this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_VALIDATED.getStringValue(), 2);
					this.current_data_request.setDateAssignedToValidator(new Date());
					candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
					assignee = this.assigned_validator;
					assigned_validator = this.assigned_validator;
					complete_task = true;
				}
				break;
				
			// validator validated a request
			case 13:
				status = ApplicationProperties.DATA_REQUEST_STATUS_VALIDATED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_VALIDATED.getStringValue(), 1);
				this.current_data_request.setDateValidated(new Date());
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
				
			// administrator changed a validated request to the "Pending Requestor Approval" status
			case 14:
				status = ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_VALIDATED_REQUEST_CLOSED.getStringValue(), 2);
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
				
			// administrator closed a validated request
			case 15:
				status = ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_VALIDATED_REQUEST_CLOSED.getStringValue(), 1);
				this.current_data_request.setDateClosed(new Date());
				candidate_group = null;
				assignee = null;
				complete_task = true;
				break;
				
			// administrator closed a request that was in "Pending Requestor Approval" status
			case 16:
				status = ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue();
				this.current_data_request.setDateClosed(new Date());
				candidate_group = null;
				assignee = null;
				complete_task = true;
				break;
			
			// administrator put request on hold
			case 17:
				status = ApplicationProperties.DATA_REQUEST_STATUS_ON_HOLD.getStringValue();
				break;
			
			// validator rejected a request
			case 18:
				status = ApplicationProperties.DATA_REQUEST_STATUS_VALIDATION_REJECTED.getStringValue();
				this.request_variables.put(ApplicationProperties.DATA_REQUEST_WORKFLOW_REQUEST_VALIDATED.getStringValue(), 3);
				candidate_group = ApplicationProperties.GROUP_ADMIN.getStringValue();
				assignee = null;
				complete_task = true;
				break;
			
			// a request was updated, but a workflow action has not been made
			default:
				break;
		}
		
		try
		{
			// update data request, workflow and email variables
			updateVariables(status, assigned_sme, assigned_validator);
			
			List<AuditField> updated_fields = new ArrayList<AuditField>();
			
			if(Utils.areStringsEqual(this.original_description, this.current_data_request.getDescription()) == false)
			{
				System.out.println("description was changed.");
				updated_fields.add(new AuditField(this.current_data_request.getId(), ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.original_description, this.current_data_request.getDescription(), this.userSession.getUser().getId()));
			}
			
			System.out.println("original sme: " + this.original_sme);
			System.out.println("current sme: " + this.current_data_request.getAssignedSme());
			if(Utils.areStringsEqual(this.original_sme, this.assigned_sme) == false)
			{
				System.out.println("assigned sme was changed.");
				updated_fields.add(new AuditField(this.current_data_request.getId(), ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue(), this.original_sme, this.assigned_sme, this.userSession.getUser().getId()));
			}
			
			if(Utils.areStringsEqual(this.original_status, status) == false)
			{
				System.out.println("status was changed.");
				updated_fields.add(new AuditField(this.current_data_request.getId(), ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), this.original_status, status, this.userSession.getUser().getId()));
			}
			
			if(Utils.areStringsEqual(this.original_system, this.current_data_request.getSystem()) == false)
			{
				System.out.println("system was changed.");
				updated_fields.add(new AuditField(this.current_data_request.getId(), ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue(), this.original_system, this.current_data_request.getSystem(), this.userSession.getUser().getId()));
			}
			
			if(Utils.areStringsEqual(this.original_type, this.current_data_request.getType()) == false)
			{
				System.out.println("type was changed.");
				updated_fields.add(new AuditField(this.current_data_request.getId(), ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.original_type, this.current_data_request.getType(), this.userSession.getUser().getId()));
			}
			
			// user action resulted in a completion of a workflow task
			if(complete_task == true)
			{
				logger.info("Completing task, and updating the request");
				
				this.task_service.complete(this.current_data_request.getCurrentTaskId(), this.request_variables);
				
				DataLayer.getInstance().updateDataRequest(this.current_data_request.getId(), this.request_variables, candidate_group, assignee);
				
				if(updated_fields.size() > 0)
				{
					DataLayer.getInstance().insertAuditFields(updated_fields);
				}
			}
			// user action resulted in a new data request
			else if(start_new_request == true)
			{
				logger.info("Starting a new process instance, and inserting the request");
				
				if(this.current_data_request.getParentId() != null)
				{
					DataLayer.getInstance().insertIterationAssociation(this.current_data_request.getParentId(), this.current_data_request.getIteration(), this.current_data_request.getId());
				}
				
				ProcessInstance started_process_instance = this.runtime_service.startProcessInstanceByKey(ApplicationProperties.PROCESS_ID_DATA_REQUEST.getStringValue(), this.request_variables);
				
				DataLayer.getInstance().insertDataRequest(started_process_instance.getId(), this.request_variables, candidate_group, assignee);
			}
			// user action resulted only in an updated to a request
			else
			{
				logger.info("Updating the request");
				
				DataLayer.getInstance().updateDataRequest(this.current_data_request.getId(), this.request_variables, candidate_group, assignee);
				
				if(updated_fields.size() > 0)
				{
					DataLayer.getInstance().insertAuditFields(updated_fields);
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e);
			e.printStackTrace();
		}
		
		return userSession.getHomePage() + "?faces-redirect=true";
	}

	/**
	 * This method updates all of the data request and email values.
	 * 
	 * @param status current status of the request
	 */
	private void updateVariables(String status, String assigned_sme, String assigned_validator)
	{
		String assigned_sme_email = null;
		String assigned_validator_email = null;
		
		assigned_sme_email = this.sme_users_emails.get(assigned_sme);
		assigned_validator_email = this.sme_users_emails.get(assigned_validator);
		
		// data request variables
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue(), this.current_data_request.getId());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue(), this.getIteration());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue(), this.getDueDate());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue(), this.getRelatedRequests());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue(), this.getTopicKeywords());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue(), this.getPurpose());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue(), this.getSpecialConsiderationsIssues());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.getType());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue(), this.getDescription());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(), this.isUrgent());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue(), this.getCreatedDateTime());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.getCreatedBy());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue(), this.getRequestorName());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue(), this.getRequestorOrganization());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue(), this.getRequestorPhone());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue(), this.getRequestorEmail());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue(), this.getReceiverName());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue(), this.getReceiverEmail());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), status);
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue(), assigned_sme);
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_SME.getStringValue(), this.getDateAssignedToSme());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue(), this.getDateResolved());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue(), assigned_validator);
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue(), this.getDateAssignedToValidator());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue(), this.getDateValidated());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue(), this.getDateClosed());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_COMMENTS.getStringValue(), this.getAllComments());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue(), this.isPii());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue(), this.getSystem());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ORIGINAL_REQUEST_DATE.getStringValue(), this.getOriginalRequestDate());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_CLARIFICATIONS_ASSUMPTIONS.getStringValue(), this.getClarificationsAssumptions());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_GOLDEN_QUERY_LIBRARY.getStringValue(), this.getGoldenQueryLibrary());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_BUSINESS_REQUIREMENTS.getStringValue(), this.getBusinessRequirements());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_TIER.getStringValue(), this.getTier());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_VALIDATION_DESCRIPTION.getStringValue(), this.getValidationDescription());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_VALIDATION_RESULT.getStringValue(), this.getValidationResult());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DELAY_REASON.getStringValue(), this.getDelayReason());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_AGREED_DUE_DATE.getStringValue(), this.getAgreedDueDate());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_ANTICIPATED_DUE_DATE.getStringValue(), this.getAnticipatedDueDate());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RUN.getStringValue(), this.getDateRun());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_REPORT_TYPE.getStringValue(), this.getReportType());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_QUERY_REPORT_NAME.getStringValue(), this.getQueryReportName());
		this.request_variables.put(ApplicationProperties.DATA_REQUEST_FIELD_DETAILED_STEPS.getStringValue(), this.getDetailedSteps());
		
		// email content replacement
		this.email_replace_tokens.put("REQUEST_DISPLAY_ID", this.current_data_request.getDisplayId());
		
		// email to notify the administrators and the DRTs about a new request
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_TO.getStringValue(), getAdminDRTEmails());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify a requestor about a new request
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_TO.getStringValue(), this.getRequestorEmail());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUESTOR_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUESTOR_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify a SME that a request has been assigned to him/her
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_TO.getStringValue(), assigned_sme_email);
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_SME_NEW_REQUEST_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify the administrators that a SME resolved a request
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_TO.getStringValue(), getAdminEmails());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_RESOLVED_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_RESOLVED_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify a validator that a request has been assigned to him/her
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_VALIDATOR_TO.getStringValue(), assigned_validator_email);
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_VALIDATOR_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_VALIDATOR_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_VALIDATOR_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_VALIDATOR_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_VALIDATOR_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_VALIDATOR_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_VALIDATOR_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_VALIDATOR_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify the administrators that a SME validated a request
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_VALIDATED_TO.getStringValue(), getAdminEmails());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_VALIDATED_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_VALIDATED_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_VALIDATED_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_VALIDATED_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_VALIDATED_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_VALIDATED_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_REQUEST_VALIDATED_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_REQUEST_VALIDATED_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify a requestor that a request is pending their approval
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_PENDING_APPROVAL_TO.getStringValue(), this.creator_users_emails.get(this.getCreatedBy()));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_PENDING_APPROVAL_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUEST_PENDING_APPROVAL_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_PENDING_APPROVAL_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUEST_PENDING_APPROVAL_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_PENDING_APPROVAL_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUEST_PENDING_APPROVAL_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_PENDING_APPROVAL_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUEST_PENDING_APPROVAL_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify a requestor that a request has been closed
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_CLOSED_TO.getStringValue(), this.creator_users_emails.get(this.getCreatedBy()));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_CLOSED_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUEST_CLOSED_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_CLOSED_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_REQUEST_CLOSED_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_CLOSED_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUEST_CLOSED_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_REQUEST_CLOSED_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_REQUEST_CLOSED_CONTENT.getStringValue(), this.email_replace_tokens));
		
		// email to notify the administrators that a SME rejected validation of a request
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_VALIDATION_REJECTED_TO.getStringValue(), getAdminEmails());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_VALIDATION_REJECTED_CC.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_VALIDATION_REJECTED_CC.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_VALIDATION_REJECTED_FROM.getStringValue(), ApplicationProperties.EMAIL_NOTIFY_ADMIN_VALIDATION_REJECTED_FROM.getStringValue());
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_VALIDATION_REJECTED_SUBJECT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_VALIDATION_REJECTED_SUBJECT.getStringValue(), this.email_replace_tokens));
		this.request_variables.put(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_VALIDATION_REJECTED_CONTENT.getStringValue(), Utils.replaceAll(ApplicationProperties.EMAIL_NOTIFY_ADMIN_VALIDATION_REJECTED_CONTENT.getStringValue(), this.email_replace_tokens));
	}
	
	/**
	 * This method creates a map of request types, for 
	 * use in a dropdown.
	 * 
	 * @return Returns a map of request types.
	 */
	public Map<String, String> getTypes()
	{
		Map<String, String> request_types = new LinkedHashMap<String, String>();
		
		request_types.put("", "");
		
		for(String type : ApplicationProperties.DATA_REQUEST_TYPES.getListValue())
		{
			request_types.put(type, type);
		}
			
		return request_types;
	}
	
	/**
	 * This method creates a map of systems, for 
	 * use in a dropdown.
	 * 
	 * @return Returns a map of systems.
	 */
	public Map<String, String> getSystems()
	{
		Map<String, String> systems = new LinkedHashMap<String, String>();
		
		systems.put("", "");
		
		for(String system : ApplicationProperties.DATA_REQUEST_SYSTEMS.getListValue())
		{
			systems.put(system, system);
		}
			
		return systems;
	}
	
	public Map<String, String> getDelayReasons()
	{
		Map<String, String> delay_reasons = new LinkedHashMap<String, String>();
		
		delay_reasons.put("", "");
		
		for(String delay_reason : ApplicationProperties.DATA_REQUEST_DELAY_REASONS.getListValue())
		{
			delay_reasons.put(delay_reason, delay_reason);
		}
			
		return delay_reasons;
	}
	
	public Map<String, String> getReportTypes()
	{
		Map<String, String> report_types = new LinkedHashMap<String, String>();
		
		report_types.put("", "");
		
		for(String report_type : ApplicationProperties.DATA_REQUEST_REPORT_TYPES.getListValue())
		{
			report_types.put(report_type, report_type);
		}
			
		return report_types;
	}
	
	public Map<String, String> getSmeGroups()
	{
		Map<String, String> sme_groups = new LinkedHashMap<String, String>();
		
		sme_groups.put("", "");
		
		for(String sme_group : ApplicationProperties.DATA_REQUEST_SME_GROUPS.getListValue())
		{
			sme_groups.put(sme_group, sme_group);
		}
			
		return sme_groups;
	}
	
	public Map<String, Integer> getTiers()
	{
		Map<String, Integer> tiers = new LinkedHashMap<String, Integer>();
		
		tiers.put("", 0);
		
		for(String tier : ApplicationProperties.DATA_REQUEST_TIERS.getListValue())
		{
			tiers.put(tier, Integer.parseInt(tier));
		}
			
		return tiers;
	}
	
	/**
	 * This method creates a map of users, that are able to 
	 * create requests, and their emails.
	 */
	private void setCreators()
	{
		List<User> creators = null;
		List<User> admin_users = this.identity_service.createUserQuery().memberOfGroup(ApplicationProperties.GROUP_ADMIN.getStringValue()).list();
		List<User> requestor_users = this.identity_service.createUserQuery().memberOfGroup(ApplicationProperties.GROUP_REQUESTOR.getStringValue()).list();
		
		if((admin_users != null && admin_users.size() > 0) ||
				(requestor_users != null && requestor_users.size() > 0))
		{
			creators = new ArrayList<User>();
			
			creators.addAll(admin_users);
			creators.addAll(requestor_users);
			
			this.creator_users_emails = new HashMap<String, String>();
			
			for(User user : creators)
			{
				this.creator_users_emails.put(user.getId(), user.getEmail());
			}
		}
	}
	
	/**
	 * This method creates two maps of SME users, one with their full 
	 * names and one with their emails.
	 */
	private void setSMEUsers()
	{
		List<User> users = this.identity_service.createUserQuery().memberOfGroup(ApplicationProperties.GROUP_SME.getStringValue()).list();
		
		if(users != null && users.size() > 0)
		{
			this.sme_users_names = new HashMap<String, String>();
			this.sme_users_emails = new HashMap<String, String>();
		
			for(User sme : users)
			{
				this.sme_users_names.put(sme.getId(), sme.getFirstName() + " " + sme.getLastName());
				this.sme_users_emails.put(sme.getId(), sme.getEmail());
			}
		}
	}
	
	/**
	 * This method reverses the keys and values of the SME names map, for 
	 * use in a dropdown.
	 * 
	 * @return Returns a map of SME users and their full names.
	 */
	public Map<String, String> getSmes()
	{
		Map<String, String> sme_dropdown = null;
			
		if(this.sme_users_names != null && this.sme_users_names.size() > 0)
		{
			sme_dropdown = new HashMap<String, String>();
			
			sme_dropdown.put("", "");
				
			Iterator<Map.Entry<String, String>> it = this.sme_users_names.entrySet().iterator();
			
			while(it.hasNext())
			{
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
				
				sme_dropdown.put(pairs.getValue(), pairs.getKey());
			}
		}
			
		return sme_dropdown;
	}
	
	/**
	 * This method creates a string with a comma separated list 
	 * of administrator emails.
	 * 
	 * @return Returns a string with a comma separated list of administrator 
	 * emails.
	 */
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
		
		        email_list_delimiter = ",";
		    }
		}
		
		return sb.toString();
	}
	
	/**
	 * This method creates a string with a comma separated list 
	 * of administrator and DRT emails.
	 * 
	 * @return Returns a string with a comma separated list of administrator 
	 * and DRT emails.
	 */
	private String getAdminDRTEmails()
	{
		StringBuilder sb = new StringBuilder();
	    String email_list_delimiter = "";
	    String admin_emails = getAdminEmails();
		
		List<User> users = this.identity_service.createUserQuery().memberOfGroup("drt").list();
		
		if(users != null && users.size() > 0)
		{
			for(User user : users)
			{
				sb.append(email_list_delimiter);
		        sb.append(user.getEmail());            
		
		        email_list_delimiter = ",";
		    }
			
			if(Utils.isStringEmpty(admin_emails) == false)
			{
				sb.append("," + admin_emails);
			}
		}
		
		return sb.toString();
	}

	/**
	 * This method decides if the current request is in drafted state and was 
	 * created by the currently logged in user.
	 * 
	 * @return Returns true if the current request is in drafted state and was 
	 * created by the currently logged in user, false otherwise.
	 */
	public boolean getStatusDrafted()
	{
		if((this.userSession.getUser().getId().equalsIgnoreCase(this.current_data_request.getDrtsRequestor()) == true) && 
				(this.current_data_request.getStatus().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue()) == true))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method decides if the current request is being opened as a new 
	 * iteration of another request.
	 * 
	 * @return Returns true if the current request is being opened as a new 
	 * iteration of another request, false otherwise.
	 */
	public boolean getRequestIsNewIteration()
	{
		return (this.current_data_request.getParentId() != null);
	}
	
	/**
	 * This method decides if an external email can be sent.
	 * 
	 * @return Returns true if an external email can be sent, false otherwise.
	 */
	public boolean getCanSendEmail()
	{
		if((this.current_data_request.getStatus().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue()) == true) || 
				(this.current_data_request.getStatus().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue()) == true))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method decides if the current request is in the "On Hold" state.
	 * 
	 * @return Returns true if the current request is in the "On Hold" state, 
	 * false otherwise.
	 */
	public boolean getStatusOnHold()
	{
		return (this.current_data_request.getStatus().equalsIgnoreCase(ApplicationProperties.DATA_REQUEST_STATUS_ON_HOLD.getStringValue()) == true);
	}
	
	/*
	 * GETTERS AND SETTERS
	 * 
	 */
	
	public String getId()
	{
		return this.current_data_request.getId();
	}
	
	public String getDisplayId()
	{
		return this.current_data_request.getDisplayId();
	}
	
	public int getIteration()
	{
		return this.current_data_request.getIteration();
	}
	
	public String getStatus()
	{
		return this.current_data_request.getStatus();
	}
	
	public String getType()
	{
		return this.current_data_request.getType();
	}

	public void setType(String type)
	{
		this.current_data_request.setType(type);
	}
	
	public Date getCreatedDateTime()
	{
		return this.current_data_request.getCreatedDateTime();
	}
	
	public Date getDueDate()
	{
		return this.current_data_request.getDueDate();
	}

	public void setDueDate(Date due_date)
	{
		this.current_data_request.setDueDate(due_date);
	}
	
	public String getCreatedBy()
	{
		return this.current_data_request.getDrtsRequestor();
	}

	public String getRelatedRequests()
	{
		return this.current_data_request.getRelatedRequests();
	}

	public void setRelatedRequests(String related_requests)
	{
		this.current_data_request.setRelatedRequests(related_requests);
	}
	
	public String getDescription()
	{
		return this.current_data_request.getDescription();
	}

	public void setDescription(String description)
	{
		this.current_data_request.setDescription(description);
	}

	public String getTopicKeywords()
	{
		return this.current_data_request.getTopicKeywords();
	}

	public void setTopicKeywords(String topic_keywords)
	{
		this.current_data_request.setTopicKeywords(topic_keywords);
	}
	
	public String getPurpose()
	{
		return this.current_data_request.getPurpose();
	}

	public void setPurpose(String purpose)
	{
		this.current_data_request.setPurpose(purpose);
	}
	
	public boolean isUrgent()
	{
		return this.current_data_request.isUrgent();
	}

	public void setUrgent(boolean urgent)
	{
		this.current_data_request.setUrgent(urgent);
	}

	public String getSpecialConsiderationsIssues()
	{
		return this.current_data_request.getSpecialConsiderationsIssues();
	}

	public void setSpecialConsiderationsIssues(String special_considerations_issues)
	{
		this.current_data_request.setSpecialConsiderationsIssues(special_considerations_issues);
	}
	
	public String getRequestorName()
	{
		return this.current_data_request.getRequestorName();
	}

	public void setRequestorName(String requestor_name)
	{
		this.current_data_request.setRequestorName(requestor_name);
	}
	
	public String getRequestorOrganization()
	{
		return this.current_data_request.getRequestorOrganization();
	}

	public void setRequestorOrganization(String requestor_organization)
	{
		this.current_data_request.setRequestorOrganization(requestor_organization);
	}
	
	public String getRequestorPhone()
	{
		return this.current_data_request.getRequestorPhone();
	}

	public void setRequestorPhone(String requestor_phone)
	{
		this.current_data_request.setRequestorPhone(requestor_phone);
	}
	
	public String getRequestorEmail()
	{
		return this.current_data_request.getRequestorEmail();
	}

	public void setRequestorEmail(String requestor_email)
	{
		this.current_data_request.setRequestorEmail(requestor_email);
	}
	
	public String getReceiverName()
	{
		return this.current_data_request.getReceiverName();
	}

	public void setReceiverName(String receiver_name)
	{
		this.current_data_request.setReceiverName(receiver_name);
	}
	
	public String getReceiverEmail()
	{
		return this.current_data_request.getReceiverEmail();
	}

	public void setReceiverEmail(String receiver_email)
	{
		this.current_data_request.setReceiverEmail(receiver_email);
	}
	
	public String getAssignedSme()
	{
		return this.current_data_request.getAssignedSme();
	}

	public void setAssignedSme(String assigned_sme)
	{
		this.assigned_sme = assigned_sme;
	}

	public Date getDateAssignedToSme()
	{
		return this.current_data_request.getDateAssignedToSme();
	}
	
	public Date getDateResolved()
	{
		return this.current_data_request.getDateResolved();
	}
	
	public String getAssignedValidator()
	{
		return this.current_data_request.getAssignedValidator();
	}

	public void setAssignedValidator(String assigned_validator)
	{
		this.assigned_validator = assigned_validator;
	}

	public Date getDateAssignedToValidator()
	{
		return this.current_data_request.getDateAssignedToValidator();
	}
	
	public Date getDateValidated()
	{
		return this.current_data_request.getDateValidated();
	}
	
	public Date getDateClosed()
	{
		return this.current_data_request.getDateClosed();
	}

	public List<Attachment> getAttachments()
	{
		return this.request_attachments;
	}

	private String getAllComments()
	{
		String current_comments = this.current_data_request.getComments();
		String new_comments = "";
		
		if(current_comments == null)
		{
			if(Utils.isStringEmpty(this.new_comments) == false)
			{
				new_comments = "<p>" + this.new_comments + "</p>";
			}
		}
		else
		{
			if(Utils.isStringEmpty(this.new_comments) == false)
			{
				new_comments = "<p>" + this.new_comments + "</p>";
				new_comments += current_comments;
			}
		}
		
		return new_comments;
	}
	
	public String getComments()
	{
		return this.current_data_request.getComments();
	}
	
	public String getNewComments()
	{
		return this.new_comments;
	}
	
	public void setNewComments(String new_comments)
	{
		this.new_comments = new_comments;
	}

	public boolean isPii()
	{
		return this.current_data_request.isPii();
	}

	public void setPii(boolean pii)
	{
		this.current_data_request.setPiiFlag(pii);
	}

	public String getSystem()
	{
		return this.current_data_request.getSystem();
	}

	public void setSystem(String system)
	{
		this.current_data_request.setSystem(system);;
	}

	public String getDelayReason()
	{
		return this.current_data_request.getDelayReason();
	}

	public void setDelayReason(String delay_reason)
	{
		this.current_data_request.setDelayReason(delay_reason);
	}

	public Date getOriginalRequestDate()
	{
		return this.current_data_request.getOriginalRequestDate();
	}

	public void setOriginalRequestDate(Date original_request_date)
	{
		this.current_data_request.setOriginalRequestDate(original_request_date);
	}

	public Date getAgreedDueDate()
	{
		return this.current_data_request.getAgreedDueDate();
	}

	public void setAgreedDueDate(Date agreed_due_date)
	{
		this.current_data_request.setAgreedDueDate(agreed_due_date);
	}

	public Date getAnticipatedDueDate()
	{
		return this.current_data_request.getAnticipatedDueDate();
	}

	public void setAnticipatedDueDate(Date anticipated_due_date)
	{
		this.current_data_request.setAnticipatedDueDate(anticipated_due_date);
	}

	public Date getDateRun()
	{
		return this.current_data_request.getDateRun();
	}

	public void setDateRun(Date date_run)
	{
		this.current_data_request.setDateRun(date_run);
	}

	public String getReportType()
	{
		return this.current_data_request.getReportType();
	}

	public void setReportType(String report_type)
	{
		this.current_data_request.setReportType(report_type);
	}

	public String getQueryReportName()
	{
		return this.current_data_request.getQueryReportName();
	}

	public void setQueryReportName(String query_report_name)
	{
		this.current_data_request.setQueryReportName(query_report_name);
	}

	public String getClarificationsAssumptions()
	{
		return this.current_data_request.getClarificationsAssumptions();
	}

	public void setClarificationsAssumptions(String clarifications_assumptions)
	{
		this.current_data_request.setClarificationsAssumptions(clarifications_assumptions);
	}

	public String getDetailedSteps()
	{
		return this.current_data_request.getDetailedSteps();
	}

	public void setDetailedSteps(String detailed_steps)
	{
		this.current_data_request.setDetailedSteps(detailed_steps);
	}

	public String getValidationDescription()
	{
		return this.current_data_request.getValidationDescription();
	}

	public void setValidationDescription(String validation_description)
	{
		this.current_data_request.setValidationDescription(validation_description);
	}

	public String getValidationResult()
	{
		return this.current_data_request.getValidationResult();
	}

	public void setValidationResult(String validation_result)
	{
		this.current_data_request.setValidationResult(validation_result);
	}

	public String getGoldenQueryLibrary()
	{
		return this.current_data_request.getGoldenQueryLibrary();
	}

	public void setGoldenQueryLibrary(String golden_query_library)
	{
		this.current_data_request.setGoldenQueryLibrary(golden_query_library);
	}

	public String getBusinessRequirements()
	{
		return this.current_data_request.getBusinessRequirements();
	}

	public void setBusinessRequirements(String business_requirements)
	{
		this.current_data_request.setBusinessRequirements(business_requirements);
	}

	public int getTier()
	{
		return this.current_data_request.getTier();
	}

	public void setTier(int tier)
	{
		this.current_data_request.setTier(tier);
	}

	public String getSmeGroup()
	{
		return this.current_data_request.getSmeGroup();
	}

	public void setSmeGroup(String sme_group)
	{
		this.current_data_request.setSmeGroup(sme_group);
	}

	public String getValidationSmeGroup()
	{
		return this.current_data_request.getValidationSmeGroup();
	}

	public void setValidationSmeGroup(String validation_sme_group)
	{
		this.current_data_request.setValidationSmeGroup(validation_sme_group);
	}
}
