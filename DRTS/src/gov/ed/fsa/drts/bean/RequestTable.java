package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.sql.CustomTaskMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.cmd.CustomSqlExecution;
import org.apache.log4j.Logger;

@ManagedBean(name = "requestTable")
@RequestScoped
public class RequestTable extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

	private static final Logger logger = Logger.getLogger(RequestTable.class);

	private transient ProcessEngine process_engine = null;
	private transient TaskService task_service = null;
	private transient IdentityService identity_service = null;
	private transient RuntimeService runtime_service = null;
	private transient ManagementService management_service = null;
	
	private int total_rows;
    private int first_row;
    private int rows_per_page;
    private int total_pages;
    private int page_range;
    private Integer[] pages;
    private int current_page;
    private String sort_field;
    private boolean sort_ascending;
    private String sql_sort;
	
	private List<DataRequest> all_data_requests = null;
	
	private GroupQuery group_query = null;
	
	@PostConstruct
	private void init()
	{
		logger.info("init History ::: " + userSession.getUser().getId());
		
		this.process_engine = ProcessEngines.getDefaultProcessEngine();
		
		if(this.process_engine != null)
		{
			this.task_service = this.process_engine.getTaskService();
			
			if(this.task_service == null)
			{
				// TODO handle error
			}
			
			this.identity_service = this.process_engine.getIdentityService();
			
			if(this.identity_service == null)
			{
				// TODO handle error
			}
			
			this.runtime_service = this.process_engine.getRuntimeService();
			
			if(this.runtime_service == null)
			{
				// TODO handle error
			}
			
			this.management_service = this.process_engine.getManagementService();
			
			if(this.management_service == null)
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
        this.sort_field = "request_type";
        this.sort_ascending = true;
        this.sql_sort = "ASC";
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
	
	private List<DataRequest> pullDataRequests()
	{
		List<DataRequest> data_requests = null;
		DataRequest request = null;
		
		final String candidate_groups = getCandidateGroups();
		final String order_by = this.sort_field;
		final String sort = this.sql_sort;
		final int sql_first_row = this.first_row;
		final int sql_last_row = this.first_row + this.rows_per_page;
		final String assigned_to = this.userSession.getUser().getId();
		
		CustomSqlExecution<CustomTaskMapper, List<Map<String, Object>>> get_tasks = new AbstractCustomSqlExecution<CustomTaskMapper, List<Map<String, Object>>>(CustomTaskMapper.class)
		{
			public List<Map<String, Object>> execute(CustomTaskMapper customMapper)
			{
				return customMapper.selectTasks(candidate_groups, assigned_to, order_by, sort, sql_first_row, sql_last_row);
			}
		};
		
		List<Map<String, Object>> results = this.management_service.executeCustomSql(get_tasks);
		
		if(results != null && results.size() > 0)
		{
			data_requests = new ArrayList<DataRequest>();
			
			for(Map<String, Object> variables : results)
			{
				request = new DataRequest(variables);
				
				data_requests.add(request);
			}
		}
		
		return data_requests;
	}

	private int getDataRequestsCount()
	{
		int result = 0;
		
		final String candidate_groups = getCandidateGroups();
		final String assigned_to = this.userSession.getUser().getId();
		
		CustomSqlExecution<CustomTaskMapper, Integer> get_task_count = new AbstractCustomSqlExecution<CustomTaskMapper, Integer>(CustomTaskMapper.class)
		{
			public Integer execute(CustomTaskMapper customMapper)
			{
				return customMapper.selectTaskCount(candidate_groups, assigned_to, null, null, 0, 0);
			}
		};
		
		result = this.management_service.executeCustomSql(get_task_count);
		
		return result;
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
		
		sb.append("(");
		
		for(Group group : user_groups)
		{
			user_group_names.add(group.getId());
			sb.append("'" + group.getId() + "',");
		}
		
		sb.deleteCharAt(sb.length() -1);
		sb.append(")");
		
		System.out.println("user-groups: " + sb.toString());
		
		return sb.toString();
	}
	
	public String goToTask(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return dataRequest.getTaskFormKey() + "?faces-redirect=true";
	}
	
	private void loadDataRequests()
    {	
		this.all_data_requests = pullDataRequests();
		this.total_rows = getDataRequestsCount();
    	
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
        String sort_field_attribute = (String) event.getComponent().getAttributes().get("sort_field");
        
        if(this.sort_field.equals(sort_field_attribute))
        {
            this.sort_ascending = !this.sort_ascending;
            
            if(this.sort_ascending)
            {
            	this.sql_sort = "ASC";
            }
            else
            {
            	this.sql_sort = "DESC";
            }
        }
        else
        {
        	this.sort_field = sort_field_attribute;
        	this.sort_ascending = true;
        	this.sql_sort = "ASC";
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
    	System.out.println("filed: " + field);
    	System.out.println("sorted by field: " + this.sort_field.equalsIgnoreCase(field));
    	
    	return this.sort_field.equalsIgnoreCase(field);
    }
}