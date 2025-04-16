package nic.ame.app.master.dto;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class IndividuaUnitMappedToControllingAmeAppointment {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String candidateForcePersonalId;
	private String candidateForceNo;
	private String candidateIrlaNo;
	private String controllingForcePersonalId;
	private String candidateName;
	private String forceName;
	private String unitName;
	private String rank;
	private String appointmentStatus;
	private String ameStatus;
	private Date declarationYear;
	
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(nullable = false)
	private String controllingUniqueId;
	
	private String createdBy;
	private Date createdOn;
	private String ipAddress;
	
	private String forcePersonalId;
	private String boardId;
	private int forceNo;
	private int unitNo;
	private String irlaNo;
	private Date toDate;
	private Date fromDate;
	//private String createdOn;
	//private String createdBy;
	private String modifiedOn;
	private String modifiedBy;
	//private String ipAddress;
	private String declarationStatus;
	private String declarationInvalidFlag;
	private String remark;
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
	public String getControllingUniqueId() {
		return controllingUniqueId;
	}
	public void setControllingUniqueId(String controllingUniqueId) {
		this.controllingUniqueId = controllingUniqueId;
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
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public int getForceNo() {
		return forceNo;
	}
	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}
	public int getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getDeclarationStatus() {
		return declarationStatus;
	}
	public void setDeclarationStatus(String declarationStatus) {
		this.declarationStatus = declarationStatus;
	}
	public String getDeclarationInvalidFlag() {
		return declarationInvalidFlag;
	}
	public void setDeclarationInvalidFlag(String declarationInvalidFlag) {
		this.declarationInvalidFlag = declarationInvalidFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	
	
	
	public String getAmeStatus() {
		return ameStatus;
	}
	public void setAmeStatus(String ameStatus) {
		this.ameStatus = ameStatus;
	}
	
	
	
	
	public Date getDeclarationYear() {
		return declarationYear;
	}
	public void setDeclarationYear(Date declarationYear) {
		this.declarationYear = declarationYear;
	}
	@Override
	public String toString() {
		return "IndividuaUnitMappedToControllingAmeAppointment [id=" + id + ", candidateForcePersonalId="
				+ candidateForcePersonalId + ", candidateForceNo=" + candidateForceNo + ", candidateIrlaNo="
				+ candidateIrlaNo + ", controllingForcePersonalId=" + controllingForcePersonalId
				+ ", controllingUniqueId=" + controllingUniqueId + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", ipAddress=" + ipAddress + ", forcePersonalId=" + forcePersonalId + ", boardId="
				+ boardId + ", forceNo=" + forceNo + ", unitNo=" + unitNo + ", irlaNo=" + irlaNo + ", toDate=" + toDate
				+ ", fromDate=" + fromDate + ", modifiedOn=" + modifiedOn + ", modifiedBy=" + modifiedBy
				+ ", declarationStatus=" + declarationStatus + ", declarationInvalidFlag=" + declarationInvalidFlag
				+ ", remark=" + remark + "]";
	}
	
	
	
	
	

}
