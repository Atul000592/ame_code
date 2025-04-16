package nic.ame.app.master.medical.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table
@Entity
public class AmeFinalReportFileDir implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7421973102749221410L;
	
	@Id
	private String ameId;
	private String fileName;
	private String filePath;
	private long fileSize;
	private String fileType;
	private Date createdOn;
	private String createdBy;
	private String ipAddress;
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "AmeFinalReportFileDir [ameId=" + ameId + ", fileName=" + fileName + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", fileType=" + fileType + ", createdOn=" + createdOn + ", createdBy="
				+ createdBy + ", ipAddress=" + ipAddress + "]";
	}
	
	

	
	
}
