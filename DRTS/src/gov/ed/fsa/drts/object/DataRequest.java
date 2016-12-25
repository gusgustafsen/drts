package gov.ed.fsa.drts.object;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import gov.ed.fsa.drts.util.ApplicationProperties;

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
	private String comments = null;
	private String system = null;
	private String assigned_sme = null;
	private Date date_assigned_to_sme = null;
	private Date date_resolved = null;
	private String current_task_id = null;
	private String current_task_name = null;
	private String current_task_form_key = null;
	private String candidate_group = null;
	private String assignee = null;
	private String assigned_validator = null;
	private Date date_assigned_to_validator = null;
	private Date date_validated = null;
	private Date date_closed = null;
	private Date last_updated_date = null;
	private boolean pii_flag = false;
	private String delay_reason = null;
	private Date original_request_date = null;
	private Date agreed_due_date = null;
	private Date anticipated_due_date = null;
	private Date date_run = null;
	private String report_type = null;
	private String query_report_name = null;
	private String clarifications_assumptions = null;
	private String detailed_steps = null;
	private String validation_description = null;
	private String validation_result = null;
	private String golden_query_library = null;
	private String business_requirements = null;
	private int tier;
	private String sme_group = null;
	private String validation_sme_group = null;
	private String dataRequestCopy = null;
	private String trackingSuffix = null;

	private int display_id = 1;

	private String parent_id = null;

	public DataRequest() {
	}

	public void initialize(String created_by) throws Exception {
		this.drts_requestor = created_by;
		initialize(false);
	}

	public void initialize(boolean copyAttachments) throws Exception {
		if (copyAttachments) {
			dataRequestCopy = this.id;
		}

		this.parent_id = this.id;

		this.id = UUID.randomUUID().toString();
		this.iteration = 1;
		this.created_date_time = new Date();
		this.status = ApplicationProperties.DATA_REQUEST_STATUS_DRAFTED.getStringValue();

		Calendar date = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		this.original_request_date = date.getTime();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedDateTime() {
		return this.created_date_time;
	}

	public void setCreatedDateTime(Date created_date_time) {
		this.created_date_time = created_date_time;
	}

	public String getDrtsRequestor() {
		return this.drts_requestor;
	}

	public void setDrtsRequestor(String drts_requestor) {
		this.drts_requestor = drts_requestor;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrentTaskId() {
		return this.current_task_id;
	}

	public void setCurrentTaskId(String current_task_id) {
		this.current_task_id = current_task_id;
	}

	public String getCurrentTaskName() {
		return this.current_task_name;
	}

	public void setCurrentTaskName(String current_task_name) {
		this.current_task_name = current_task_name;
	}

	public String getCurrentTaskFormKey() {
		return this.current_task_form_key;
	}

	public void setCurrentTaskFormKey(String current_task_form_key) {
		this.current_task_form_key = current_task_form_key;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public Date getDueDate() {
		return due_date;
	}

	public void setDueDate(Date due_date) {
		this.due_date = due_date;
	}

	public boolean isUrgent() {
		return urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public String getRelatedRequests() {
		return related_requests;
	}

	public void setRelatedRequests(String related_requests) {
		this.related_requests = related_requests;
	}

	public String getTopicKeywords() {
		return topic_keywords;
	}

	public void setTopicKeywords(String topic_keywords) {
		this.topic_keywords = topic_keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSpecialConsiderationsIssues() {
		return special_considerations_issues;
	}

	public void setSpecialConsiderationsIssues(String special_considerations_issues) {
		this.special_considerations_issues = special_considerations_issues;
	}

	public String getRequestorName() {
		return requestor_name;
	}

	public void setRequestorName(String requestor_name) {
		this.requestor_name = requestor_name;
	}

	public String getRequestorOrganization() {
		return requestor_organization;
	}

	public void setRequestorOrganization(String requestor_organization) {
		this.requestor_organization = requestor_organization;
	}

	public String getRequestorPhone() {
		return requestor_phone;
	}

	public void setRequestorPhone(String requestor_phone) {
		this.requestor_phone = requestor_phone;
	}

	public String getRequestorEmail() {
		return requestor_email;
	}

	public void setRequestorEmail(String requestor_email) {
		this.requestor_email = requestor_email;
	}

	public String getReceiverName() {
		return receiver_name;
	}

	public void setReceiverName(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getReceiverEmail() {
		return receiver_email;
	}

	public void setReceiverEmail(String receiver_email) {
		this.receiver_email = receiver_email;
	}

	public String getAssignedSme() {
		return assigned_sme;
	}

	public void setAssignedSme(String assigned_sme) {
		this.assigned_sme = assigned_sme;
	}

	public Date getDateAssignedToSme() {
		return date_assigned_to_sme;
	}

	public void setDateAssignedToSme(Date date_assigned_to_sme) {
		this.date_assigned_to_sme = date_assigned_to_sme;
	}

	public Date getDateResolved() {
		return date_resolved;
	}

	public void setDateResolved(Date date_resolved) {
		this.date_resolved = date_resolved;
	}

	public String getDisplayId() {
		String result = "";

		Calendar cal = Calendar.getInstance();
		cal.setTime(this.created_date_time);

		int year = cal.get(Calendar.YEAR);

		result += (year % 100) + "-" + this.display_id + "-" + (trackingSuffix == null ? "D" : trackingSuffix);

		return result;
	}

	public void setDisplayId(int display_id) {
		this.display_id = display_id;
	}

	public String getCandidateGroup() {
		return this.candidate_group;
	}

	public void setCandidateGroup(String candidate_group) {
		this.candidate_group = candidate_group;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getAssignedValidator() {
		return assigned_validator;
	}

	public void setAssignedValidator(String assigned_validator) {
		this.assigned_validator = assigned_validator;
	}

	public Date getDateAssignedToValidator() {
		return date_assigned_to_validator;
	}

	public void setDateAssignedToValidator(Date date_assigned_to_validator) {
		this.date_assigned_to_validator = date_assigned_to_validator;
	}

	public Date getDateValidated() {
		return date_validated;
	}

	public void setDateValidated(Date date_validated) {
		this.date_validated = date_validated;
	}

	public Date getDateClosed() {
		return date_closed;
	}

	public void setDateClosed(Date date_closed) {
		this.date_closed = date_closed;
	}

	public String getParentId() {
		return this.parent_id;
	}

	public void setParentId(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getLastUpdatedDate() {
		return last_updated_date;
	}

	public void setLastUpdatedDate(Date last_updated_date) {
		this.last_updated_date = last_updated_date;
	}

	public boolean isPii() {
		return pii_flag;
	}

	public void setPiiFlag(boolean pii_flag) {
		this.pii_flag = pii_flag;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getDelayReason() {
		return delay_reason;
	}

	public void setDelayReason(String delay_reason) {
		this.delay_reason = delay_reason;
	}

	public Date getOriginalRequestDate() {
		return original_request_date;
	}

	public void setOriginalRequestDate(Date original_request_date) {
		this.original_request_date = original_request_date;
	}

	public Date getAgreedDueDate() {
		return agreed_due_date;
	}

	public void setAgreedDueDate(Date agreed_due_date) {
		this.agreed_due_date = agreed_due_date;
	}

	public Date getAnticipatedDueDate() {
		return anticipated_due_date;
	}

	public void setAnticipatedDueDate(Date anticipated_due_date) {
		this.anticipated_due_date = anticipated_due_date;
	}

	public Date getDateRun() {
		return date_run;
	}

	public void setDateRun(Date date_run) {
		this.date_run = date_run;
	}

	public String getReportType() {
		return report_type;
	}

	public void setReportType(String report_type) {
		this.report_type = report_type;
	}

	public String getQueryReportName() {
		return query_report_name;
	}

	public void setQueryReportName(String query_report_name) {
		this.query_report_name = query_report_name;
	}

	public String getClarificationsAssumptions() {
		return clarifications_assumptions;
	}

	public void setClarificationsAssumptions(String clarifications_assumptions) {
		this.clarifications_assumptions = clarifications_assumptions;
	}

	public String getDetailedSteps() {
		return detailed_steps;
	}

	public void setDetailedSteps(String detailed_steps) {
		this.detailed_steps = detailed_steps;
	}

	public String getValidationDescription() {
		return validation_description;
	}

	public void setValidationDescription(String validation_description) {
		this.validation_description = validation_description;
	}

	public String getValidationResult() {
		return validation_result;
	}

	public void setValidationResult(String validation_result) {
		this.validation_result = validation_result;
	}

	public String getGoldenQueryLibrary() {
		return golden_query_library;
	}

	public void setGoldenQueryLibrary(String golden_query_library) {
		this.golden_query_library = golden_query_library;
	}

	public String getBusinessRequirements() {
		return business_requirements;
	}

	public void setBusinessRequirements(String business_requirements) {
		this.business_requirements = business_requirements;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public String getSmeGroup() {
		return sme_group;
	}

	public void setSmeGroup(String sme_group) {
		this.sme_group = sme_group;
	}

	public String getValidationSmeGroup() {
		return validation_sme_group;
	}

	public void setValidationSmeGroup(String validation_sme_group) {
		this.validation_sme_group = validation_sme_group;
	}

	public String getDataRequestCopy() {
		return dataRequestCopy;
	}

	public void setDataRequestCopy(String dataRequestCopy) {
		this.dataRequestCopy = dataRequestCopy;
	}

	public String getTrackingSuffix() {
		return trackingSuffix;
	}

	public void setTrackingSuffix(String trackingSuffix) {
		this.trackingSuffix = trackingSuffix;
	}
}
