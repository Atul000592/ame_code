package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "tt_prescribed_check_up")
public class CheckUpList {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ameId;
	private String testName;
	private String testCode;
	@Column(nullable = true)
	private int uploadFlag;

	@Transient
	private int otherFlag;
	
	private Date createdOn;
	private String createdBy;
	private String createdFrom;
	private String modifiedBy;
	private Date modifiedOn;
	private String modifiedFrom; 

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
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
	public int getUploadFlag() {
		return uploadFlag;
	}
	public void setUploadFlag(int uploadFlag) {
		this.uploadFlag = uploadFlag;
	}
	public int getOtherFlag() {
		return otherFlag;
	}
	public void setOtherFlag(int otherFlag) {
		this.otherFlag = otherFlag;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedFrom() {
		return createdFrom;
	}
	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedFrom() {
		return modifiedFrom;
	}
	public void setModifiedFrom(String modifiedFrom) {
		this.modifiedFrom = modifiedFrom;
	}





}
