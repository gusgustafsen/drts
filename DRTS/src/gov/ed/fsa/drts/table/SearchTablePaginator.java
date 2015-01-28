package gov.ed.fsa.drts.table;

import java.util.List;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;

public class SearchTablePaginator extends TablePaginator {

	private int search_type = -1;
	
	private List<DataRequest> all_data_requests = null;
	
	private String request_display_id = null;
	
	public SearchTablePaginator(UserSession user_session)
	{
		super(user_session);
	}
	
	public SearchTablePaginator(int search_type, UserSession user_session)
	{
		super(user_session);
		
		this.search_type = search_type;
	}
	
	public SearchTablePaginator(int search_type, UserSession user_session, String request_display_id)
	{
		super(user_session);
		
		this.search_type = search_type;
		
		this.request_display_id = request_display_id;
	}

	@Override
	protected void loadDataRequests()
	{
		try
		{
			switch(this.search_type)
			{
				case 1:
					this.all_data_requests = DataLayer.getInstance().getDataRequestsByDisplayId(this.request_display_id, this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance().getDataRequestsByDisplayIdCount(this.request_display_id);
					break;
					
				case 2:
					System.out.println("searching by fields.");
					break;
					
				default:
					break;
			}
			
			super.setAfterLoad();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<DataRequest> getDataRequests()
	{
		if(this.all_data_requests == null)
		{
			loadDataRequests();
		}
			
		return this.all_data_requests;
	}

	@Override
	public void page(int first_row)
	{
		this.first_row = first_row;
    	
    	loadDataRequests();
	}
}
