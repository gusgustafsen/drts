package gov.ed.fsa.drts.table;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;

public class SearchTablePaginator extends TablePaginator {

	private int search_type = -1;

	private List<DataRequest> all_data_requests = null;

	private String request_display_id = null;
	private String keyword = null;
	private Date created_date_from = null;
	private Date created_date_to = null;
	private String createdBy = null;

	private LinkedHashMap<String, String> search_parameters = null;

	public SearchTablePaginator(UserSession user_session) {
		super(user_session);
	}

	public SearchTablePaginator(int search_type, UserSession user_session) {
		super(user_session);

		this.search_type = search_type;
	}

	public SearchTablePaginator(int search_type, UserSession user_session, String request_display_id) {
		this(search_type, user_session, request_display_id, null);
	}

	public SearchTablePaginator(int search_type, UserSession user_session, String request_display_id,
			String createdBy) {
		super(user_session);

		this.search_type = search_type;

		if (search_type == 1 || search_type == 4) {
			this.request_display_id = request_display_id;
		} else {
			this.keyword = request_display_id;
		}

		this.createdBy = createdBy;
	}

	public SearchTablePaginator(int search_type, UserSession user_session,
			LinkedHashMap<String, String> search_parameters, Date created_date_from, Date created_date_to) {
		super(user_session);

		this.search_type = search_type;

		this.created_date_from = created_date_from;
		this.created_date_to = created_date_to;
		this.search_parameters = search_parameters;
	}

	@Override
	protected void loadDataRequests() {
		try {
			switch (this.search_type) {
			case 1:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByDisplayId(this.request_display_id,
						this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByDisplayIdCount(this.request_display_id);
				break;

			case 2:
				Calendar c = null;
				String date_from = null;
				String date_to = null;

				if (this.created_date_from != null && this.created_date_to != null) {
					c = Calendar.getInstance();

					c.setTime(this.created_date_to);
					c.add(Calendar.DATE, 1);

					date_from = DateFormatUtils.format(this.created_date_from, "MM-dd-yyyy");
					date_to = DateFormatUtils.format(c.getTime(), "MM-dd-yyyy");

					System.out.println("date_from: " + date_from);
					System.out.println("date_to: " + date_to);
				}

				this.all_data_requests = DataLayer.getInstance().getDataRequestsByFields(this.search_parameters,
						date_from, date_to, this.first_row, (this.first_row + this.rows_per_page), this.sort_field,
						this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByFieldsCount(this.search_parameters,
						date_from, date_to);
				break;

			case 3:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByKeyword(this.keyword, this.first_row,
						(this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByKeywordCount(this.keyword);
				break;

			case 4:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByDisplayId(this.request_display_id,
						this.createdBy, this.first_row, (this.first_row + this.rows_per_page), this.sort_field,
						this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByDisplayIdCount(this.request_display_id,
						this.createdBy);
				break;

			case 5:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByKeyword(this.keyword, this.createdBy,
						this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByKeywordCount(this.keyword, this.createdBy);
				break;

			default:
				break;
			}

			super.setAfterLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DataRequest> getDataRequests() {
		if (this.all_data_requests == null) {
			loadDataRequests();
		}

		return this.all_data_requests;
	}

	@Override
	public void page(int first_row) {
		this.first_row = first_row;

		loadDataRequests();
	}
}
