package nic.ame.app.master.medical.dto;

public class MedicalCheckUpMasterDto {
	
	
	private String testCode;
	private String testName;
	private int defaultCheckedFlag;
	
	
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public int getDefaultCheckedFlag() {
		return defaultCheckedFlag;
	}
	public void setDefaultCheckedFlag(int defaultCheckedFlag) {
		this.defaultCheckedFlag = defaultCheckedFlag;
	}
	@Override
	public String toString() {
		return "MedicalCheckUpMasterDto [testCode=" + testCode + ", testName=" + testName + ", defaultCheckedFlag="
				+ defaultCheckedFlag + "]";
	}

    
	
	
	
}
