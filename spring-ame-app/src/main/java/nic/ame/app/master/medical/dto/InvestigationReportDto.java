package nic.ame.app.master.medical.dto;

import java.util.List;
import java.util.Map;

public class InvestigationReportDto {
    private	String ameId;
	private String testCode;
    private String testName;
    private String value;
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String string) {
		this.ameId = string;
	}
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "InvestigationReportDto [ameId=" + ameId + ", testCode=" + testCode + ", testName=" + testName
				+ ", value=" + value + "]";
	}
	
}

    
    
    

