package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_kidney_function_test")
public class KidneyFunctionTest {
	@Id
	private String ameId;
	private String bloodUrea; 	
	private String scrumCreatinine;	
	private String uricAcid;
	
	
	private String testCode;
	private String path;
	private String fileName;
	private String fileType;
	private int fileSize;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getBloodUrea() {
		return bloodUrea;
	}
	public void setBloodUrea(String bloodUrea) {
		this.bloodUrea = bloodUrea;
	}
	public String getScrumCreatinine() {
		return scrumCreatinine;
	}
	public void setScrumCreatinine(String scrumCreatinine) {
		this.scrumCreatinine = scrumCreatinine;
	}
	public String getUricAcid() {
		return uricAcid;
	}
	public void setUricAcid(String uricAcid) {
		this.uricAcid = uricAcid;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	public void setLastModifiedFrom(String lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	
	
	
	//======================================new Test Added========================================//
	
	
	

}
