package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_lipid")
public class Lipid {
	@Id
	private String ameId;
	private String serumCcholesterol; 	
	private String lDLCholesterol; 	
	private String hDLCholesterol; 	
	private String vLDLcholesterol; 	
	private String triglycerides; 	
	private String lDLhDLRatio;
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
	public String getSerumCcholesterol() {
		return serumCcholesterol;
	}
	public void setSerumCcholesterol(String serumCcholesterol) {
		this.serumCcholesterol = serumCcholesterol;
	}
	public String getlDLCholesterol() {
		return lDLCholesterol;
	}
	public void setlDLCholesterol(String lDLCholesterol) {
		this.lDLCholesterol = lDLCholesterol;
	}
	public String gethDLCholesterol() {
		return hDLCholesterol;
	}
	public void sethDLCholesterol(String hDLCholesterol) {
		this.hDLCholesterol = hDLCholesterol;
	}
	public String getvLDLcholesterol() {
		return vLDLcholesterol;
	}
	public void setvLDLcholesterol(String vLDLcholesterol) {
		this.vLDLcholesterol = vLDLcholesterol;
	}
	public String getTriglycerides() {
		return triglycerides;
	}
	public void setTriglycerides(String triglycerides) {
		this.triglycerides = triglycerides;
	}
	public String getlDLhDLRatio() {
		return lDLhDLRatio;
	}
	public void setlDLhDLRatio(String lDLhDLRatio) {
		this.lDLhDLRatio = lDLhDLRatio;
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
	
	
	
	
	
}
