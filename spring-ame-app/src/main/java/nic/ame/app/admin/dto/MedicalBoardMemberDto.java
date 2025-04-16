package nic.ame.app.admin.dto;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;

public class MedicalBoardMemberDto {
	
	public String getForceCodeName() {
		return forceCodeName;
	}
	public void setForceCodeName(String forceCodeName) {
		this.forceCodeName = forceCodeName;
	}
	private String forcePersonalId;
	
	private String name; 
	private String designation;
	private String forceNo;
	private String roleName;
	@Column(name = "rank")
	private String rank;
	private String usedFor;
	private String boardAtForceNo;
	private String place;
	private String committeeId;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date fromDate;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date toDate;
	
	private String forceName;
	private Date createdOn;
	private String boardId;
	private String irlaNumber;
	private String unitName;
	private int appointmentCompletedCount;
	private int appointmentPendingCount;
	private String boardYear;
	private int gFlag;
	private String appointMentStatus;
	private String gazettedFlagValue;
	private int status;
	private String roleStatus;
	private int reserveFlag;
	private String createdBy;
	private String remark;
	
	private int alternateMedicalId;
	
	private String forceCodeName;
	private String forceId;
	private String gender;
	private String statusValue;
	private String subRole;
	
	
	public String getForceId() {
		return forceId;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getForceNo() {
		return forceNo;
	}
	public void setForceNo(String forceNo) {
		this.forceNo = forceNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	
	
	
	public String getBoardAtForceNo() {
		return boardAtForceNo;
	}
	public void setBoardAtForceNo(String boardAtForceNo) {
		this.boardAtForceNo = boardAtForceNo;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCommitteeId() {
		return committeeId;
	}
	public void setCommitteeId(String committeeId) {
		this.committeeId = committeeId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}

	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getIrlaNumber() {
		return irlaNumber;
	}
	public void setIrlaNumber(String irlaNumber) {
		this.irlaNumber = irlaNumber;
	}
	public String getUnitName() {
		return unitName;
		
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public int getAppointmentCompletedCount() {
		return appointmentCompletedCount;
	}
	public void setAppointmentCompletedCount(int appointmentCompletedCount) {
		this.appointmentCompletedCount = appointmentCompletedCount;
	}
	public int getAppointmentPendingCount() {
		return appointmentPendingCount;
	}
	public void setAppointmentPendingCount(int appointmentPendingCount) {
		this.appointmentPendingCount = appointmentPendingCount;
	}
	public String getBoardYear() {
		return boardYear;
	}
	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}
	public int getgFlag() {
		return gFlag;
	}
	public void setgFlag(int gFlag) {
		this.gFlag = gFlag;
	}
	public String getAppointMentStatus() {
		return appointMentStatus;
	}
	public void setAppointMentStatus(String appointMentStatus) {
		this.appointMentStatus = appointMentStatus;
	}
	public void setGazettedFlagValue(String string) {
		// TODO Auto-generated method stub
		
	}
	public String getGazettedFlagValue() {
		return gazettedFlagValue;
	}
	public int getStatus() {
		return status;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	public int getReserveFlag() {
		return reserveFlag;
	}
	public void setReserveFlag(int reserveFlag) {
		this.reserveFlag = reserveFlag;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getAlternateMedicalId() {
		return alternateMedicalId;
	}
	public void setAlternateMedicalId(int alternateMedicalId) {
		this.alternateMedicalId = alternateMedicalId;
	}
	
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	public String getSubRole() {
		return subRole;
	}
	public void setSubRole(String subRole) {
		this.subRole = subRole;
	}
	@Override
	public String toString() {
		return "MedicalBoardMemberDto [forcePersonalId=" + forcePersonalId + ", name=" + name + ", designation="
				+ designation + ", forceNo=" + forceNo + ", roleName=" + roleName + ", rank=" + rank + ", usedFor="
				+ usedFor + ", boardAtForceNo=" + boardAtForceNo + ", place=" + place + ", committeeId=" + committeeId
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", forceName=" + forceName + ", createdOn="
				+ createdOn + ", boardId=" + boardId + ", irlaNumber=" + irlaNumber + ", unitName=" + unitName
				+ ", appointmentCompletedCount=" + appointmentCompletedCount + ", appointmentPendingCount="
				+ appointmentPendingCount + ", boardYear=" + boardYear + ", gFlag=" + gFlag + ", appointMentStatus="
				+ appointMentStatus + ", gazettedFlagValue=" + gazettedFlagValue + ", status=" + status
				+ ", roleStatus=" + roleStatus + ", reserveFlag=" + reserveFlag + ", createdBy=" + createdBy
				+ ", remark=" + remark + ", alternateMedicalId=" + alternateMedicalId + ", forceCodeName="
				+ forceCodeName + ", forceId=" + forceId + ", gender=" + gender + "]";
	}
	
	

}
