package gov.ed.fsa.drts.bean;

public class assignedSmeBean {

	private String Name = "";
	private int SmeCount = 0;
	private int ValidatorCount = 0;
	private int TotalCount = 0;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getSmeCount() {
		return SmeCount;
	}
	public void setSmeCount(int smeCount) {
		SmeCount = smeCount;
	}
	public int getValidatorCount() {
		return ValidatorCount;
	}
	public void setValidatorCount(int validatorCount) {
		ValidatorCount = validatorCount;
	}
	public int getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}
}
