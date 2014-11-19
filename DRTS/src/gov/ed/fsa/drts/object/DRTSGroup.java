package gov.ed.fsa.drts.object;

import org.activiti.engine.identity.Group;

public class DRTSGroup {

	private String id;
	private Group activiti_group;
	
	public DRTSGroup(){}
	
	public DRTSGroup(Group activiti_group)
	{
		if(activiti_group != null)
		{
			this.id = activiti_group.getId();
			this.activiti_group = activiti_group;
		}
	}
	
	public DRTSGroup(String id, Group activiti_group)
	{
		this.id = id;
		this.activiti_group = activiti_group;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Group getActivitiGroup()
	{
		return activiti_group;
	}

	public void setActivitiGroup(Group activiti_group)
	{
		this.activiti_group = activiti_group;
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
		
		if (obj == null)
		{
			return false;
		}
		
		if(!(obj instanceof DRTSGroup))
		{
			return false;
		}
		
		DRTSGroup other = (DRTSGroup) obj;
		
		if(id == null)
		{
			if(other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		
		return true;
	}
}
