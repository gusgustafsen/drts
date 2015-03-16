package gov.ed.fsa.drts.object;

import java.util.UUID;

public class AuditField {

	private String id = null;
	private String request_id = null;
	private String column_name = null;
	private String old_value = null;
	private String new_value = null;
	private String modified_by = null;
	
	public AuditField(){}
	
	public AuditField(String request_id, String column_name, String old_value, String new_value, String modified_by)
	{
		this.id = UUID.randomUUID().toString();
		this.request_id = request_id;
		this.column_name = column_name;
		this.old_value = old_value;
		this.new_value = new_value;
		this.modified_by = modified_by;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getRequestId()
	{
		return this.request_id;
	}

	public String getColumnName()
	{
		return column_name;
	}

	public String getOldValue()
	{
		return old_value;
	}

	public String getNewValue()
	{
		return new_value;
	}

	public String getModifiedBy()
	{
		return modified_by;
	}
}
