package gov.ed.fsa.drts.object;

import gov.ed.fsa.drts.util.ApplicationProperties;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataRequest {

	private Map<String, Object> variables = null;
	
	public DataRequest(){}
	
	public DataRequest(Map<String, Object> variables)
	{
		this.variables = variables;
	}

	public void setVariables(Map<String, Object> variables)
	{
		this.variables = variables;
	}

	public Date getCreatedDateTime()
	{
		return getDateVariable(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue());
	}
	
	public String getDrtsRequestor()
	{
		return getStringVariable(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue());
	}
	
	public String getStatus()
	{
		// TODO change to string
		
		return getStringVariable(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue());
	}
	
	public String getTaskId()
	{
		return getStringVariable("TASK_ID");
	}
	
	public String getTaskName()
	{
		return getStringVariable("TASK_NAME");
	}
	
	public String getTaskFormKey()
	{
		return getStringVariable("TASK_FORM_KEY");
	}
	
	public String getProcessInstanceId()
	{
		return getStringVariable("TASK_PROCESS_INSTANCE_ID");
	}
	
	public String getType()
	{
		int int_type = getIntegerVariable(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue());
		
		// TODO fix
		
		Map<Integer, String> request_types = new LinkedHashMap<Integer, String>();
		request_types.put(1, "Type 1");
		request_types.put(2, "Type 2");
		request_types.put(3, "Type 3");
		
		return request_types.get(int_type);
	}
	
	private String getStringVariable(String key)
	{
		if(this.variables != null)
		{
			return (String) this.variables.get(key);
		}
		
		return null;
	}
	
	private int getIntegerVariable(String key)
	{
		if(this.variables != null)
		{
			return Integer.parseInt(getStringVariable(key));
		}
		
		return 0;
	}
	
	private Date getDateVariable(String key)
	{
		if(this.variables != null)
		{
			return new Date(((BigDecimal) this.variables.get(key)).longValue());
		}
		
		return null;
	}
}
