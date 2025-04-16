package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.master.model.AmeStatusCode;

@Entity
@Table
public class AmeReviewCandidatesList {

	@Id
	private String ameId;
	private String irlaNo;
	private String candidateForcePersonalId;
	private Date reviewCreatedOn;
	private Date reviewEndDate;
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "status_code")
	private AmeStatusCode ameStatusCode;
	
	private String createdBy;
	
	private String ipAddress;
	
	private String year;
	
	private String boardId;
	private String remark;
	
	private boolean rescheduleFlag;

	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public String getCandidateForcePersonalId() {
		return candidateForcePersonalId;
	}
	public void setCandidateForcePersonalId(String candidateForcePersonalId) {
		this.candidateForcePersonalId = candidateForcePersonalId;
	}
	public Date getReviewCreatedOn() {
		return reviewCreatedOn;
	}
	public void setReviewCreatedOn(Date reviewCreatedOn) {
		this.reviewCreatedOn = reviewCreatedOn;
	}
	public Date getReviewEndDate() {
		return reviewEndDate;
	}
	public void setReviewEndDate(Date reviewEndDate) {
		this.reviewEndDate = reviewEndDate;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public AmeStatusCode getAmeStatusCode() {
		return ameStatusCode;
	}
	public void setAmeStatusCode(AmeStatusCode ameStatusCode) {
		this.ameStatusCode = ameStatusCode;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	public boolean isRescheduleFlag() {
		return rescheduleFlag;
	}
	public void setRescheduleFlag(boolean rescheduleFlag) {
		this.rescheduleFlag = rescheduleFlag;
	}
	@Override
	public String toString() {
		return "AmeReviewCandidatesList [ameId=" + ameId + ", irlaNo=" + irlaNo + ", candidateForcePersonalId="
				+ candidateForcePersonalId + ", reviewCreatedOn=" + reviewCreatedOn + ", reviewEndDate=" + reviewEndDate
				+ ", ameStatusCode=" + ameStatusCode + ", createdBy=" + createdBy + ", ipAddress=" + ipAddress
				+ ", year=" + year + ", boardId=" + boardId + ", remark=" + remark + "]";
	}
	
	
	
	
	
}
