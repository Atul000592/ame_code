package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table
public class ReportingOfficer {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String createdBy;
	private Date createdOn;
	private String ipAddress;
	private int unit;
	private int forceNo;
	private int status;
   @Column(nullable = false,unique = true)
	private String reportingUniqueId;
   
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public Date getCreatedOn() {
	return createdOn;
}
public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
}
public String getIpAddress() {
	return ipAddress;
}
public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
}
public int getUnit() {
	return unit;
}
public void setUnit(int unit) {
	this.unit = unit;
}
public int getForceNo() {
	return forceNo;
}
public void setForceNo(int forceNo) {
	this.forceNo = forceNo;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getReportingUniqueId() {
	return reportingUniqueId;
}
public void setReportingUniqueId(String reportingUniqueId) {
	this.reportingUniqueId = reportingUniqueId;
}
   
   
	
}
	
	