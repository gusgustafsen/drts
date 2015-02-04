package gov.ed.fsa.drts.table;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;

public class ReportTablePaginator extends TablePaginator {

	private int report_type = -1;
	
	private String request_display_id = null;
	private String keyword = null;
	private Date requested_due_date_from = null;
	private Date requested_due_date_to = null;
	private Date resolved_date_from = null;
	private Date resolved_date_to = null;
	private Date updated_date_from = null;
	private Date updated_date_to = null;
	
	private LinkedHashMap<String, String> search_parameters = null;
	
	private List<DataRequest> all_data_requests = null;
	
	public ReportTablePaginator(UserSession user_session)
	{
		super(user_session);
	}
	
	public ReportTablePaginator(int report_type, UserSession user_session)
	{
		super(user_session);
		
		this.report_type = report_type;
	}
	
	// TODO redo/optimize
	public ReportTablePaginator(int report_type, UserSession user_session, String request_display_id, String keyword, LinkedHashMap<String, String> search_parameters, 
								Date requested_due_date_from, Date requested_due_date_to, Date resolved_date_from,
								Date resolved_date_to, Date updated_date_from, Date updated_date_to)
	{
		super(user_session);
		
		this.report_type = report_type;
		
		this.request_display_id = request_display_id;
		this.keyword = keyword;
		this.search_parameters = search_parameters;
		this.requested_due_date_from = requested_due_date_from;
		this.requested_due_date_to = requested_due_date_to;
		this.resolved_date_from = resolved_date_from;
		this.resolved_date_to = resolved_date_to;
		this.updated_date_from = updated_date_from;
		this.updated_date_to = updated_date_to;
	}

	@Override
	protected void loadDataRequests()
	{
		try
		{
			switch(this.report_type)
			{
				case 1:
				
					String date_from_1 = null, date_to_1 = null;
					String date_from_2 = null, date_to_2 = null;
					String date_from_3 = null, date_to_3 = null;
					
					if(this.requested_due_date_from != null && this.requested_due_date_to != null)
					{
						date_from_1 = DateFormatUtils.format(this.requested_due_date_from, "MM-dd-yyyy");
						date_to_1 = DateFormatUtils.format(this.requested_due_date_to, "MM-dd-yyyy");
					}
					
					if(this.resolved_date_from != null && this.resolved_date_to != null)
					{
						date_from_2 = DateFormatUtils.format(this.resolved_date_from, "MM-dd-yyyy");
						date_to_2 = DateFormatUtils.format(this.resolved_date_to, "MM-dd-yyyy");
					}
					
					if(this.updated_date_from != null && this.updated_date_to != null)
					{
						date_from_3 = DateFormatUtils.format(this.updated_date_from, "MM-dd-yyyy");
						date_to_3 = DateFormatUtils.format(this.updated_date_to, "MM-dd-yyyy");
					}
					
					this.all_data_requests = DataLayer.getInstance().report1(this.request_display_id, this.keyword, this.search_parameters, date_from_1, date_to_1, date_from_2, date_to_2, date_from_3, date_to_3, 
																				this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance().report1count(this.request_display_id, this.keyword, this.search_parameters, date_from_1, date_to_1, date_from_2, date_to_2, date_from_3, date_to_3);
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
