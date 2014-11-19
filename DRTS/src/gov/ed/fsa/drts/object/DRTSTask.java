package gov.ed.fsa.drts.object;

import org.activiti.engine.task.Task;

public class DRTSTask {

	private String id;
	private Task activiti_task;
	
	public DRTSTask(){}
	
	public DRTSTask(String id, Task task)
	{
		this.id = id;
		this.activiti_task = task;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Task getActivitiTask()
	{
		return activiti_task;
	}

	public void setActivitiTask(Task activiti_task)
	{
		this.activiti_task = activiti_task;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		
		if(obj == null)
		{
			return false;
		}
		
		if(!(obj instanceof DRTSTask))
		{
			return false;
		}
		
		DRTSTask other = (DRTSTask) obj;
		
		if(id == null)
		{
			if(other.id != null)
			{
				return false;
			}
		}
		else if(!id.equalsIgnoreCase(other.id))
		{
			return false;
		}
		
		return true;
	}
}
