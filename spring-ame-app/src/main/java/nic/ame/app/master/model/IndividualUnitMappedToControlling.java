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
public class IndividualUnitMappedToControlling {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String candidateForcePersonalId;
	private String candidateForceNo;
	private String candidateIrlaNo;
	private String controllingForcePersonalId;
	
	@Column(nullable = false)
	private String controllingUniqueId;
	
	private String createdBy;
	private Date createdOn;
	private String ipAddress;
	
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
	public String getCandidateForceNo() {
		return candidateForceNo;
	}
	public void setCandidateForceNo(String candidateForceNo) {
		this.candidateForceNo = candidateForceNo;
	}
	public String getCandidateIrlaNo() {
		return candidateIrlaNo;
	}
	public void setCandidateIrlaNo(String candidateIrlaNo) {
		this.candidateIrlaNo = candidateIrlaNo;
	}
	public String getControllingForcePersonalId() {
		return controllingForcePersonalId;
	}
	public void setControllingForcePersonalId(String controllingForcePersonalId) {
		this.controllingForcePersonalId = controllingForcePersonalId;
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
	
}
