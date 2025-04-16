package nic.ame.app.master.dto;

import java.util.Date;

public class AppointmentListDto {
	
	private String forcePersonalId;
	private String forcePersoanlIdboardMember;
	private String boardId;
	private int appointmentStatusCode;
	private String appointmentStatusDescription;
    private String irlaNo;
    private String forceName;
    private String unitName;
    private Date toDate;
    private Date fromDate;
    private String name;
    private String declarationStatus;
    private String gender;
    private Boolean rescheduleFlag;
    
    
    
	public Boolean getRescheduleFlag() {
		return rescheduleFlag;
	}
	public void setRescheduleFlag(Boolean rescheduleFlag) {
		this.rescheduleFlag = rescheduleFlag;
	}
	public String getDeclarationStatus() {
		return declarationStatus;
	}
	public void setDeclarationStatus(String declarationStatus) {
		this.declarationStatus = declarationStatus;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public String getForcePersoanlIdboardMember() {
		return forcePersoanlIdboardMember;
	}
	public void setForcePersoanlIdboardMember(String forcePersoanlIdboardMember) {
		this.forcePersoanlIdboardMember = forcePersoanlIdboardMember;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public int getAppointmentStatusCode() {
		return appointmentStatusCode;
	}
	public void setAppointmentStatusCode(int appointmentStatusCode) {
		this.appointmentStatusCode = appointmentStatusCode;
	}
	public String getAppointmentStatusDescription() {
		return appointmentStatusDescription;
	}
	public void setAppointmentStatusDescription(String appointmentStatusDescription) {
		this.appointmentStatusDescription = appointmentStatusDescription;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
    
    
    
}
