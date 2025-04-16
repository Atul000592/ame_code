package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_complete_blood_count")
public class CompleteBloodCount {
	@Id
	private String ameId;
	private String haemoglobin;
	private String totalLeukocyteCount;
	private String differentialLeukocyteCount; 	
	private String neutrophilsOrPolymorphs; 	
	private String monocytes;
	private String lymphocytes;
	private String basophils;
	private String eosinophils;
	private String erythrocyteSedimentationRate;
	private String plateletCount;
	
	
	//================================================================//
	
	private String testCode;
	private String path;
	private String fileName;
	private String fileType;
	private int fileSize;
	
	//======================new test added=======================================//
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	

    
    
	
	
	public String getHaemoglobin() {
		return haemoglobin;
	}
	public void setHaemoglobin(String haemoglobin) {
		this.haemoglobin = haemoglobin;
	}
	public String getTotalLeukocyteCount() {
		return totalLeukocyteCount;
	}
	public void setTotalLeukocyteCount(String totalLeukocyteCount) {
		this.totalLeukocyteCount = totalLeukocyteCount;
	}
	public String getDifferentialLeukocyteCount() {
		return differentialLeukocyteCount;
	}
	public void setDifferentialLeukocyteCount(String differentialLeukocyteCount) {
		this.differentialLeukocyteCount = differentialLeukocyteCount;
	}
	public String getNeutrophilsOrPolymorphs() {
		return neutrophilsOrPolymorphs;
	}
	public void setNeutrophilsOrPolymorphs(String neutrophilsOrPolymorphs) {
		this.neutrophilsOrPolymorphs = neutrophilsOrPolymorphs;
	}
	public String getMonocytes() {
		return monocytes;
	}
	public void setMonocytes(String monocytes) {
		this.monocytes = monocytes;
	}
	
	public String getLymphocytes() {
		return lymphocytes;
	}
	public void setLymphocytes(String lymphocytes) {
		this.lymphocytes = lymphocytes;
	}
	public String getBasophils() {
		return basophils;
	}
	public void setBasophils(String basophils) {
		this.basophils = basophils;
	}
	public String getEosinophils() {
		return eosinophils;
	}
	public void setEosinophils(String eosinophils) {
		this.eosinophils = eosinophils;
	}
	public String getErythrocyteSedimentationRate() {
		return erythrocyteSedimentationRate;
	}
	public void setErythrocyteSedimentationRate(String erythrocyteSedimentationRate) {
		this.erythrocyteSedimentationRate = erythrocyteSedimentationRate;
	}
	
	
	
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testcode) {
		this.testCode = testcode;
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

	public String getPlateletCount() {
		return plateletCount;
	}
	public void setPlateletCount(String plateletCount) {
		this.plateletCount = plateletCount;
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
