package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;

// TODO implement inheritance for different tables
public class TablePaginator {

	private int table_type = -1;
	
	private UserSession user_session = null;
	
	private List<DataRequest> all_data_requests = null;
	
	private int total_rows;
    private int first_row;
    private int rows_per_page;
    private int total_pages;
    private int page_range;
    private Integer[] pages;
    private int current_page;
    private String sort_field;
    private boolean sort_ascending;
    
    public TablePaginator(int table_type, UserSession user_session)
    {
    	this.table_type = table_type;
    	
    	this.user_session = user_session;
    	
    	this.rows_per_page = 3;
    	this.page_range = 10;
    	this.sort_field = "request_display_id";
    	this.sort_ascending = true;
    }
    
    private void loadDataRequests()
    {
    	String candidate_groups = null;
		
		this.all_data_requests = null;
		this.total_rows = 0;
		
		try
		{
			candidate_groups = getCandidateGroups();
			
	    	switch(this.table_type)
	    	{
		    	case 1:
		    		this.all_data_requests = DataLayer.getInstance().getDataRequestsByGroupOrAssignee(candidate_groups, this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance().getDataRequestsByGroupOrAssigneeCount(candidate_groups, this.user_session.getUser().getId());
		    		break;
		    		
		    	case 2:
					this.all_data_requests = DataLayer.getInstance().getDataRequestsByCreator(this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance().getDataRequestsByCreatorCount(this.user_session.getUser().getId());
		    		break;
		    	
		    	case 3:
					this.all_data_requests = DataLayer.getInstance().getDataRequestsByStatus(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
					this.total_rows = DataLayer.getInstance().getDataRequestsByStatusCount(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
		    		break;
		    		
		    	case 4:
		    		if(this.user_session.isAdmin() == true || this.user_session.isDrt())
		    		{
			    		this.all_data_requests = DataLayer.getInstance().getAllDataRequests(this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
						this.total_rows = DataLayer.getInstance().getAllDataRequestsCount();
		    		}
		    		else if(this.user_session.isRequestor() == true)
		    		{
		    			this.all_data_requests = DataLayer.getInstance().getDataRequestsByCreator(this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
						this.total_rows = DataLayer.getInstance().getDataRequestsByCreatorCount(this.user_session.getUser().getId());
		    		}
		    		else if(this.user_session.isSme() == true)
		    		{
		    			this.all_data_requests = DataLayer.getInstance().getDataRequestsByAssociation(this.user_session.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
						this.total_rows = DataLayer.getInstance().getDataRequestsByAssociationCount(this.user_session.getUser().getId());
		    		}
		    		break;
		    		
		    	default:
		    		break;
	    	}
	    	
	    	this.current_page = (this.total_rows / this.rows_per_page) - ((this.total_rows - this.first_row) / this.rows_per_page) + 1;
	        this.total_pages = (this.total_rows / this.rows_per_page) + ((this.total_rows % this.rows_per_page != 0) ? 1 : 0);
	        
	        int pages_length = Math.min(this.page_range, this.total_pages);
	        
	        pages = new Integer[pages_length];
	
	        int first_page = Math.min(Math.max(0, this.current_page - (this.page_range / 2)), this.total_pages - pages_length);
	
	        for (int i = 0; i < pages_length; i++)
	        {
	            pages[i] = ++first_page;
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    private String getCandidateGroups()
	{
    	IdentityService identity_service = ProcessEngines.getDefaultProcessEngine().getIdentityService();
    	
		List<Group> user_groups = null;
		List<String> user_group_names = null;
		
		StringBuilder sb = new StringBuilder();
		
		GroupQuery group_query = identity_service.createGroupQuery();
		group_query.groupMember(this.user_session.getUser().getId());
		
		user_groups = group_query.list();
		
		user_group_names = new ArrayList<String>();
		
		for(Group group : user_groups)
		{
			user_group_names.add(group.getId());
			sb.append("'" + group.getId() + "',");
		}
		
		sb.deleteCharAt(sb.length() -1);
		
		return sb.toString();
	}
    
    public List<DataRequest> getDataRequests()
	{
		if(this.all_data_requests == null)
		{
			loadDataRequests();
		}
		
		return this.all_data_requests;
	}
    
	public void pageFirst()
	{
        page(0);
    }

    public void pageNext()
    {
        page(this.first_row + this.rows_per_page);
    }

    public void pagePrevious()
    {
        page(this.first_row - this.rows_per_page);
    }

    public void pageLast()
    {
        page(this.total_rows - ((this.total_rows % this.rows_per_page != 0) ? this.total_rows % this.rows_per_page : this.rows_per_page));
    }

    public void page(ActionEvent event)
    {
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * this.rows_per_page);
    }

    private void page(int first_row)
    {
    	this.first_row = first_row;
    	
    	loadDataRequests();
    }

    public void sort(ActionEvent event)
    {
    	String sort_field_attribute = (String) event.getComponent().getAttributes().get("sort_field");
    	
        if(this.sort_field.equals(sort_field_attribute))
        {
            this.sort_ascending = !this.sort_ascending;
        }
        else
        {
        	this.sort_field = sort_field_attribute;
        	this.sort_ascending = true;
        }
       
        pageFirst();
    }
    
    public int getTotalRows()
    {
        return this.total_rows;
    }

    public int getFirstRow()
    {
        return this.first_row;
    }

    public int getRowsPerPage()
    {
        return this.rows_per_page;
    }

    public Integer[] getPages()
    {
        return this.pages;
    }

    public int getCurrentPage()
    {
        return this.current_page;
    }

    public int getTotalPages()
    {
        return this.total_pages;
    }
    
    public boolean getSortAscending()
    {
    	return this.sort_ascending;
    }

    public boolean isSortedBy(String field)
    {
    	return this.sort_field.equalsIgnoreCase(field);
    }
}
