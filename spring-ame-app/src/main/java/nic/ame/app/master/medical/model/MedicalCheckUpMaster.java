package nic.ame.app.master.medical.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Table(name = "tm_medical_check_up_master")
@Entity
public class MedicalCheckUpMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String testCode;
	private String testName;
	private String description;
	private int defaultCheckedFlag;
	private int status;
	
	@Transient
	private String ameId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	
	
	
	
	public int getDefaultCheckedFlag() {
		return defaultCheckedFlag;
	}
	public void setDefaultCheckedFlag(int defaultCheckedFlag) {
		this.defaultCheckedFlag = defaultCheckedFlag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MedicalCheckUpMaster [id=" + id + ", testCode=" + testCode + ", testName=" + testName + ", description="
				+ description + ", ameId=" + ameId + "]";
	}
	
	
	
}
