package gov.ed.fsa.drts.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import gov.ed.fsa.drts.bean.PageUtil;
import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.table.ReportTablePaginator;
import gov.ed.fsa.drts.table.TablePaginator;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

@ManagedBean(name = "report1")
@ViewScoped
public class Report1RequestFilter extends PageUtil implements Serializable {

	private static final long serialVersionUID = 6033872196010701108L;

	@ManagedProperty("#{userSession}")
	UserSession userSession;

	private String display_id = null;
	private String keyword = null;
	private boolean urgent = false;
	private String status = null;
	private String type = null;
	private String assignee = null;
	private String requestor = null;
	private String system = null;
	private Date requested_due_date_from = null;
	private Date requested_due_date_to = null;
	private Date resolved_date_from = null;
	private Date resolved_date_to = null;
	private Date updated_date_from = null;
	private Date updated_date_to = null;

	private boolean report_was_run = false;

	private TablePaginator table_paginator = null;

	public void runReport() {
		this.report_was_run = true;

		LinkedHashMap<String, String> search_parameters = new LinkedHashMap<String, String>();

		if (Utils.isStringEmpty(this.status) == false) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue(), this.status);
		}

		if (Utils.isStringEmpty(this.type) == false) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue(), this.type);
		}

		if (Utils.isStringEmpty(this.assignee) == false) {
			search_parameters.put("ASSIGNEE", this.assignee);
		}

		if (Utils.isStringEmpty(this.requestor) == false) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(), this.requestor);
		}

		if (userSession.isAllowedToRunReportsWithMyData()) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue(),
					userSession.getUser().getId());
		}

		if (Utils.isStringEmpty(this.system) == false) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue(), this.system);
		}

		if (this.urgent == true) {
			search_parameters.put(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue(),
					Boolean.valueOf(this.urgent).toString());
		}

		this.table_paginator = new ReportTablePaginator(1, this.userSession, this.display_id, this.keyword,
				search_parameters, this.requested_due_date_from, this.requested_due_date_to, this.resolved_date_from,
				this.resolved_date_to, this.updated_date_from, this.updated_date_to);
	}

	public void clear() {
		this.display_id = null;
		this.keyword = null;
		this.urgent = false;
		this.status = null;
		this.type = null;
		this.assignee = null;
		this.requestor = null;
		this.system = null;
		this.requested_due_date_from = null;
		this.requested_due_date_to = null;
		this.resolved_date_from = null;
		this.resolved_date_to = null;
		this.updated_date_from = null;
		this.updated_date_to = null;

		this.table_paginator = null;

		this.report_was_run = false;
	}

	public Map<String, String> getStatuses() {
		Map<String, String> request_statuses = new LinkedHashMap<String, String>();

		request_statuses.put("", "");

		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_SME.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_ASSIGNED_TO_VALIDATOR.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_CLOSED.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_PENDING_REQUESTOR_APPROVAL.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_ADMIN.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_REJECTED_BY_SME.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_RESOLVED.getStringValue());
		request_statuses.put(ApplicationProperties.DATA_REQUEST_STATUS_VALIDATED.getStringValue(),
				ApplicationProperties.DATA_REQUEST_STATUS_VALIDATED.getStringValue());

		return request_statuses;
	}

	public Map<String, String> getTypes() {
		Map<String, String> request_types = new LinkedHashMap<String, String>();

		request_types.put("", "");

		for (String type : ApplicationProperties.DATA_REQUEST_TYPES.getListValue()) {
			request_types.put(type, type);
		}

		return request_types;
	}

	public Map<String, String> getSystems() {
		Map<String, String> systems = new LinkedHashMap<String, String>();

		systems.put("", "");

		for (String type : ApplicationProperties.DATA_REQUEST_SYSTEMS.getListValue()) {
			systems.put(type, type);
		}

		return systems;
	}

	public boolean getReportWasRun() {
		return this.report_was_run;
	}

	public TablePaginator getResultsTable() {
		return this.table_paginator;
	}

	public String getDisplayId() {
		return display_id;
	}

	public void setDisplayId(String display_id) {
		this.display_id = display_id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isUrgent() {
		return this.urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Date getRequestedDueDateFrom() {
		return requested_due_date_from;
	}

	public void setRequestedDueDateFrom(Date requested_due_date_from) {
		this.requested_due_date_from = requested_due_date_from;
	}

	public Date getRequestedDueDateTo() {
		return requested_due_date_to;
	}

	public void setRequestedDueDateTo(Date requested_due_date_to) {
		this.requested_due_date_to = requested_due_date_to;
	}

	public Date getResolvedDateFrom() {
		return resolved_date_from;
	}

	public void setResolvedDateFrom(Date resolved_date_from) {
		this.resolved_date_from = resolved_date_from;
	}

	public Date getResolvedDateTo() {
		return resolved_date_to;
	}

	public void setResolvedDateTo(Date resolved_date_to) {
		this.resolved_date_to = resolved_date_to;
	}

	public Date getUpdatedDateFrom() {
		return updated_date_from;
	}

	public void setUpdatedDateFrom(Date updated_date_from) {
		this.updated_date_from = updated_date_from;
	}

	public Date getUpdatedDateTo() {
		return updated_date_to;
	}

	public void setUpdatedDateTo(Date updated_date_to) {
		this.updated_date_to = updated_date_to;
	}

	public UserSession getUserSession() {
		return userSession;
	}

	public void setUserSession(UserSession userSession) {
		this.userSession = userSession;
	}
}
