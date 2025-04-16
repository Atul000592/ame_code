package nic.ame.app.admin.dto;

import java.util.Date;

public class MedicalBoardDetailDto {

	
	
	private String boardId;
	private String forcePersonalId;
	private String roleName;
	private String status;
	private String boardYear;
	private String place; 
	private String irlaNumber;
	private String forceName;
	private String createdBy;
	private Date createdOn;
	private String useFor;
	private String gFlag;
	private String declarationStatus;
	private String appointmentStatus;
	private String name;
	private String createdByRank;
	private String createdForForce;
	
	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
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
	public String getBoardYear() {
		return boardYear;
	}
	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
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
	public String getUseFor() {
		return useFor;
	}
	public void setUseFor(String useFor) {
		this.useFor = useFor;
	}
	public String getgFlag() {
		return gFlag;
	}
	public void setgFlag(String gFlag) {
		this.gFlag = gFlag;
	}
	public String getDeclarationStatus() {
		return declarationStatus;
	}
	public void setDeclarationStatus(String declarationStatus) {
		this.declarationStatus = declarationStatus;
		
	}
	public String getIrlaNumber() {
		return irlaNumber;
	}
	public void setIrlaNumber(String irlaNumber) {
		this.irlaNumber = irlaNumber;
	}
	
	
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedByRank() {
		return createdByRank;
	}
	public void setCreatedByRank(String createdByRank) {
		this.createdByRank = createdByRank;
	}
	public String getCreatedForForce() {
		return createdForForce;
	}
	public void setCreatedForForce(String createdForForce) {
		this.createdForForce = createdForForce;
	}

	
}
