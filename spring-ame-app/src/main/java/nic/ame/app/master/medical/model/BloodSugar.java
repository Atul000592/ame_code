package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_blood_sugar")
public class BloodSugar {
	
	@Id
	private String ameId;
	private String bloodSugarPP;
	private String bloodSugarF;
	private String bloodSugarRandom;
	private String HbA1c;
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
	public String getBloodSugarPP() {
		return bloodSugarPP;
	}
	public void setBloodSugarPP(String bloodSugarPP) {
		this.bloodSugarPP = bloodSugarPP;
	}
	public String getBloodSugarF() {
		return bloodSugarF;
	}
	public void setBloodSugarF(String bloodSugarF) {
		this.bloodSugarF = bloodSugarF;
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
	public String getHbA1c() {
		return HbA1c;
	}
	public void setHbA1c(String hbA1c) {
		HbA1c = hbA1c;
	}
	public String getBloodSugarRandom() {
		return bloodSugarRandom;
	}
	public void setBloodSugarRandom(String bloodSugarRandom) {
		this.bloodSugarRandom = bloodSugarRandom;
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
