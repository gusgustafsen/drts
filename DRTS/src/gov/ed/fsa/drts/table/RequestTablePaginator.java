package gov.ed.fsa.drts.table;

import java.util.List;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;

public class RequestTablePaginator extends TablePaginator {

	private int table_type = -1;

	private List<DataRequest> all_data_requests = null;

	public RequestTablePaginator(UserSession user_session) {
		super(user_session);
	}

	public RequestTablePaginator(int table_type, UserSession user_session) {
		super(user_session);

		this.table_type = table_type;
	}

	@Override
	protected void loadDataRequests() {
		String candidate_groups = null;

		this.all_data_requests = null;
		this.total_rows = 0;

		try {
			candidate_groups = super.getCandidateGroups();

			switch (this.table_type) {
			case 1:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByGroupOrAssignee(candidate_groups,
						this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page),
						this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByGroupOrAssigneeCount(candidate_groups,
						this.user_session.getUser().getId());
				break;

			case 2:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByCreator(
						this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page),
						this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance()
						.getDataRequestsByCreatorCount(this.user_session.getUser().getId());
				break;

			case 3:
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByStatus(
						ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue(), this.first_row,
						(this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByStatusCount(
						ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
				break;

			case 4:
				if (this.user_session.isAdmin() == true || this.user_session.isDrt()) {
					this.all_data_requests = DataLayer.getInstance().getAllDataRequests(this.first_row,
							(this.first_row + this.rows_per_page), this.sort_field, false);
					this.total_rows = DataLayer.getInstance().getAllDataRequestsCount();
				} else if (this.user_session.isRequestor() == true) {
					this.all_data_requests = DataLayer.getInstance().getDataRequestsByCreator(
							this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page),
							this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance()
							.getDataRequestsByCreatorCount(this.user_session.getUser().getId());
				} else if (this.user_session.isSme() == true) {
					this.all_data_requests = DataLayer.getInstance().getDataRequestsByAssociation(
							this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page),
							this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance()
							.getDataRequestsByAssociationCount(this.user_session.getUser().getId());
				}
				break;

			case 5:

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