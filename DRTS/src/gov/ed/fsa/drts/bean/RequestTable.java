package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.apache.log4j.Logger;

@ManagedBean(name = "requestTable")
@ViewScoped
public class RequestTable extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

	private static final Logger logger = Logger.getLogger(RequestTable.class);

	private transient ProcessEngine process_engine = null;
	private transient IdentityService identity_service = null;
	
	private int total_rows;
    private int first_row;
    private int rows_per_page;
    private int total_pages;
    private int page_range;
    private Integer[] pages;
    private int current_page;
    private String sort_field;
    private boolean sort_ascending;
	
	private List<DataRequest> all_data_requests = null;
	
	private GroupQuery group_query = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("init History ::: " + userSession.getUser().getId());
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
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
		
		// TODO move to properties
		this.rows_per_page = 3;
        this.page_range = 10;
        this.sort_field = "request_display_id";
        this.sort_ascending = true;
	}
	
	@PreDestroy
	private void destroy(){}
	
	public List<DataRequest> getDataRequests()
	{
		if(this.all_data_requests == null)
		{
			loadDataRequests();
		}
		
		return this.all_data_requests;
	}
	
	private String getCandidateGroups()
	{
		List<Group> user_groups = null;
		List<String> user_group_names = null;
		StringBuilder sb = new StringBuilder();
		
		this.group_query = this.identity_service.createGroupQuery();
		this.group_query.groupMember(userSession.getUser().getId());
		
		user_groups = this.group_query.list();
		
		user_group_names = new ArrayList<String>();
		
		for(Group group : user_groups)
		{
			user_group_names.add(group.getId());
			sb.append("'" + group.getId() + "',");
		}
		
		sb.deleteCharAt(sb.length() -1);
		
		System.out.println("user-groups: " + sb.toString());
		
		return sb.toString();
	}
	
	public String goToTask(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return dataRequest.getCurrentTaskFormKey() + "?faces-redirect=true";
	}
	
	public String viewRequest(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return "/dataRequest/view.htm?faces-redirect=true";
	}
	
	private void loadDataRequests()
    {	
		String candidate_groups = null;
		
		this.all_data_requests = null;
		this.total_rows = 0;
		
		try
		{
			candidate_groups = getCandidateGroups();
			
			if(this.userSession.isAdmin() || this.userSession.isSme())
			{
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByGroupOrAssignee(candidate_groups, this.userSession.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByGroupOrAssigneeCount(candidate_groups, this.userSession.getUser().getId());
			}
			else if(this.userSession.isRequestor())
			{
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByCreator(this.userSession.getUser().getId(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByCreatorCount(this.userSession.getUser().getId());
			}
			else if(this.userSession.isDrt())
			{
				this.all_data_requests = DataLayer.getInstance().getDataRequestsByStatus(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue(), this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
				this.total_rows = DataLayer.getInstance().getDataRequestsByStatusCount(ApplicationProperties.DATA_REQUEST_STATUS_PENDING.getStringValue());
			}
			else
			{
				logger.error("User does not belong to relevant groups. Groups: " + candidate_groups);
				throw new Exception();
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
    	
    	System.out.println("first_row: " + this.first_row);
    	System.out.println("rows_per_page: " + this.rows_per_page);
    	System.out.println("total_rows: " + this.total_rows);
    	
    	loadDataRequests();
    }

    public void sort(ActionEvent event)
    {
    	System.out.println("doing sort");
    	
        String sort_field_attribute = (String) event.getComponent().getAttributes().get("sort_field");
        
        if(this.sort_field.equals(sort_field_attribute))
        {
        	System.out.println("sorting same field, current order: " + this.sort_ascending);
            this.sort_ascending = !this.sort_ascending;
        }
        else
        {
        	System.out.println("sorting new field, current order: " + this.sort_ascending);
        	this.sort_field = sort_field_attribute;
        	this.sort_ascending = true;
        }
        
        System.out.println("sorting this field: " + this.sort_field + ", new order: " + this.sort_ascending);

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
    	System.out.println("field: " + field);
    	System.out.println("sorted by field: " + this.sort_field.equalsIgnoreCase(field));
    	
    	return this.sort_field.equalsIgnoreCase(field);
    }
}