package nic.ame.app.master.medical.dto;

import java.util.List;

public class InvestigationTestMastersForSubTestDto {

	
	private String testCode;
	private String testName;
	private List<SubTestDto> subTestDtos;
	
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
	public List<SubTestDto> getSubTestDtos() {
		return subTestDtos;
	}
	public void setSubTestDtos(List<SubTestDto> subTestDtos) {
		this.subTestDtos = subTestDtos;
	}
	@Override
	public String toString() {
		return "InvestigationTestMastersForSubTestDto [testCode=" + testCode + ", testName=" + testName
				+ ", subTestDtos=" + subTestDtos + "]";
	}
	
	
	
}
