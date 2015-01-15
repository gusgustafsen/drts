package gov.ed.fsa.drts.bean;

// TODO remove this class

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.DataRequest;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

@ManagedBean(name = "requestTableAll")
@ViewScoped
public class RequestTableAll extends PageUtil implements Serializable {

	private static final long serialVersionUID = -859173943018328800L;

	private static final Logger logger = Logger.getLogger(RequestTableAll.class);
	
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
	
	@PostConstruct
	private void init()
	{
		logger.info("init History ::: " + userSession.getUser().getId());
		
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
	
	public String viewRequest(DataRequest dataRequest)
	{
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("drtsDataRequest", dataRequest);
		
		return "/dataRequest/view.htm?faces-redirect=true";
	}
	
	private void loadDataRequests()
    {
		this.all_data_requests = null;
		this.total_rows = 0;
		
		try
		{
			this.all_data_requests = DataLayer.getInstance().getAllDataRequests(this.first_row, (this.first_row + this.rows_per_page), this.sort_field, this.sort_ascending);
			this.total_rows = DataLayer.getInstance().getAllDataRequestsCount();
			
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