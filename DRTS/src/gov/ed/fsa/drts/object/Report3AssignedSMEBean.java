package gov.ed.fsa.drts.object;

public class Report3AssignedSMEBean {

	private String Name = "";
	private int SmeCount = 0;
	private int ValidatorCount = 0;
	
	public String getName()
	{
		return Name;
	}
	
	public void setName(String name)
	{
		Name = name;
	}
	
	public int getSmeCount()
	{
		return SmeCount;
	}
	
	public void setSmeCount(int smeCount)
	{
		SmeCount = smeCount;
	}
	
	public int getValidatorCount()
	{
		return ValidatorCount;
	}
	
	public void setValidatorCount(int validatorCount)
	{
		ValidatorCount = validatorCount;
	}
	
	public int getTotalCount()
	{
		return this.SmeCount + this.ValidatorCount;
	}
}
