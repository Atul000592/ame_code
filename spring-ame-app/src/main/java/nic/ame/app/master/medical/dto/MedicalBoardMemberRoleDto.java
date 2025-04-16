package nic.ame.app.master.medical.dto;

import java.util.Date;

public class MedicalBoardMemberRoleDto {

	
	private String forcePersonalId;
	private String boardId;
	private String roleName;
	private String status;
	private String uri;
	private int rCode;
	private String ForceName;
	private String unitName;
	private String boardYear;
	private String UsedFor;
	private Date createdOn;
	private int mappedMamberCount;
	private int ameCompleteCount;
	private int appointmentCount;
	private int reviewCandidateCount;
	private int reviewCandidateAppointmentPending;
	
	
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getrCode() {
		return rCode;
	}
	public void setrCode(int rCode) {
		this.rCode = rCode;
	}
	public String getForceName() {
		return ForceName;
	}
	public void setForceName(String forceName) {
		ForceName = forceName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBoardYear() {
		return boardYear;
	}
	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}
	public String getUsedFor() {
		return UsedFor;
	}
	public void setUsedFor(String usedFor) {
		UsedFor = usedFor;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getMappedMamberCount() {
		return mappedMamberCount;
	}
	public void setMappedMamberCount(int mappedMamberCount) {
		this.mappedMamberCount = mappedMamberCount;
	}
	public int getAmeCompleteCount() {
		return ameCompleteCount;
	}
	public void setAmeCompleteCount(int ameCompleteCount) {
		this.ameCompleteCount = ameCompleteCount;
	}
	public int getAppointmentCount() {
		return appointmentCount;
	}
	public void setAppointmentCount(int appointmentCount) {
		this.appointmentCount = appointmentCount;
	}
	public int getReviewCandidateCount() {
		return reviewCandidateCount;
	}
	public void setReviewCandidateCount(int reviewCandidateCount) {
		this.reviewCandidateCount = reviewCandidateCount;
	}
	public int getReviewCandidateAppointmentPending() {
		return reviewCandidateAppointmentPending;
	}
	public void setReviewCandidateAppointmentPending(int reviewCandidateAppointmentPending) {
		this.reviewCandidateAppointmentPending = reviewCandidateAppointmentPending;
	}
	
	
	
}
