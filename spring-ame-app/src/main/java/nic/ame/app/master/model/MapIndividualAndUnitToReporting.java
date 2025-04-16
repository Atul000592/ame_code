package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class MapIndividualAndUnitToReporting {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String candidateForcePersonalId;
	private String candidateIrlaNo;
	private String reportingForcePersonalId;
	@Column(nullable = false)
	private String reportingOfficerUniqueId;
	private String createdBy;
	private Date createdOn;
	private String ipAddress;
	
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCandidateForcePersonalId() {
		return candidateForcePersonalId;
	}
	public void setCandidateForcePersonalId(String candidateForcePersonalId) {
		this.candidateForcePersonalId = candidateForcePersonalId;
	}
	
	public String getReportingForcePersonalId() {
		return reportingForcePersonalId;
	}
	public void setReportingForcePersonalId(String reportingForcePersonalId) {
		this.reportingForcePersonalId = reportingForcePersonalId;
	}
	
	
	public String getCandidateIrlaNo() {
		return candidateIrlaNo;
	}
	public void setCandidateIrlaNo(String candidateIrlaNo) {
		this.candidateIrlaNo = candidateIrlaNo;
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
	public String getReportingOfficerUniqueId() {
		return reportingOfficerUniqueId;
	}
	public void setReportingOfficerUniqueId(String reportingOfficerUniqueId) {
		this.reportingOfficerUniqueId = reportingOfficerUniqueId;
	}
	
	
	
}
