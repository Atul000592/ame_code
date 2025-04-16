package nic.ame.app.user.dto;

import java.util.Date;

public class UserDeclarationDto {
	
	
	private String boardId;
	private String year;
	private String usedFor;
	private String appointmentStatus;
	private int appointmentStatusCode;
	private String declarationStatus;
	private String forcePersonalId;
	private Date fromDate;
	private Date toDate;
	private String ameId;
	private int declarationEsignFlag;
	private int ameFinalEsignFlag;
	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
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
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public int getDeclarationEsignFlag() {
		return declarationEsignFlag;
	}
	public void setDeclarationEsignFlag(int declarationEsignFlag) {
		this.declarationEsignFlag = declarationEsignFlag;
	}
	public int getAmeFinalEsignFlag() {
		return ameFinalEsignFlag;
	}
	public void setAmeFinalEsignFlag(int ameFinalEsignFlag) {
		this.ameFinalEsignFlag = ameFinalEsignFlag;
	}
	public int getAppointmentStatusCode() {
		return appointmentStatusCode;
	}
	public void setAppointmentStatusCode(int appointmentStatusCode) {
		this.appointmentStatusCode = appointmentStatusCode;
	}
	

}
