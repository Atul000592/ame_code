package nic.ame.app.master.dto;

import java.util.Date;

public class AmeAppointmentDto {
	
	
	private String forcePersonalId;
	private String boardId;
	private int forceNo;
	private int unitNo;
	private String irlaNo;
	private Date toDate;
	private Date fromDate;
	private String createdOn;
	private String createdBy;
	private String modifiedOn;
	private String modifiedBy;
	private String ipAddress;
	private String declarationStatus;
	private String declarationInvalidFlag;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	
	
	
}
