package nic.ame.app.master.medical.dto;

public class SubTestDto {

	
	private String testName;
	private  String testCode;
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	@Override
	public String toString() {
		return "SubTestDto [testName=" + testName + ", testCode=" + testCode + "]";
	}
	
	

}
