package gov.ed.fsa.drts.table;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.object.DataRequest;

public abstract class TablePaginator {

	protected UserSession user_session = null;

	protected int total_rows;
	protected int first_row;
	protected int rows_per_page;
	protected int total_pages;
	protected int page_range;
	protected Integer[] pages;
	protected int current_page;
	protected String sort_field;
	protected boolean sort_ascending;

	protected TablePaginator(UserSession user_session) {
		this.user_session = user_session;

		this.rows_per_page = Integer.MAX_VALUE - 1000;
		this.page_range = 10;
		this.sort_field = "request_display_id";
		this.sort_ascending = true;
	}

	protected abstract void loadDataRequests();

	protected void setAfterLoad() {
		this.current_page = (this.total_rows / this.rows_per_page)
				- ((this.total_rows - this.first_row) / this.rows_per_page) + 1;
		this.total_pages = (this.total_rows / this.rows_per_page)
				+ ((this.total_rows % this.rows_per_page != 0) ? 1 : 0);

		int pages_length = Math.min(this.page_range, this.total_pages);

		pages = new Integer[pages_length];

		int first_page = Math.min(Math.max(0, this.current_page - (this.page_range / 2)),
				this.total_pages - pages_length);

		for (int i = 0; i < pages_length; i++) {
			pages[i] = ++first_page;
		}
	}

	protected String getCandidateGroups() {
		IdentityService identity_service = ProcessEngines.getDefaultProcessEngine().getIdentityService();

		List<Group> user_groups = null;
		List<String> user_group_names = null;

		StringBuilder sb = new StringBuilder();

		GroupQuery group_query = identity_service.createGroupQuery();
		group_query.groupMember(this.user_session.getUser().getId());

		user_groups = group_query.list();

		user_group_names = new ArrayList<String>();

		for (Group group : user_groups) {
			user_group_names.add(group.getId());
			sb.append("'" + group.getId() + "',");
		}

		sb.deleteCharAt(sb.length() - 1);

		return sb.toString();
	}

	protected abstract List<DataRequest> getDataRequests();

	public void pageFirst() {
		page(0);
	}

	public void pageNext() {
		page(this.first_row + this.rows_per_page);
	}

	public void pagePrevious() {
		page(this.first_row - this.rows_per_page);
	}

	public void pageLast() {
		page(this.total_rows - ((this.total_rows % this.rows_per_page != 0) ? this.total_rows % this.rows_per_page
				: this.rows_per_page));
	}

	public void page(ActionEvent event) {
		page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * this.rows_per_page);
	}

	protected abstract void page(int first_row);

	public void sort(ActionEvent event) {
		String sort_field_attribute = (String) event.getComponent().getAttributes().get("sort_field");

		if (this.sort_field.equals(sort_field_attribute)) {
			this.sort_ascending = !this.sort_ascending;
		} else {
			this.sort_field = sort_field_attribute;
			this.sort_ascending = true;
		}

		pageFirst();
	}

	public int getTotalRows() {
		return this.total_rows;
	}

	public int getFirstRow() {
		return this.first_row;
	}

	public int getRowsPerPage() {
		return this.rows_per_page;
	}

	public Integer[] getPages() {
		return this.pages;
	}

	public int getCurrentPage() {
		return this.current_page;
	}

	public int getTotalPages() {
		return this.total_pages;
	}

	public boolean getSortAscending() {
		return this.sort_ascending;
	}

	public boolean isSortedBy(String field) {
		return this.sort_field.equalsIgnoreCase(field);
	}
}
