package nic.ame.app.user.dto;

import java.util.Date;

public class UserStatusDto {

	
	private String irlaNumber;
	private String candidateName;
	private String forcePersonalName;
	private String status;
	private String remark;
	private Date appointmentDate;
	private String forceName;
	private String forcePersonalDesignation;
	private String ameId;
	

	
	public String getIrlaNumber() {
		return irlaNumber;
	}
	public void setIrlaNumber(String irlaNumber) {
		this.irlaNumber = irlaNumber;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getForcePersonalName() {
		return forcePersonalName;
	}
	public void setForcePersonalName(String forcePersonalName) {
		this.forcePersonalName = forcePersonalName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}
	public String getForcePersonalDesignation() {
		return forcePersonalDesignation;
	}
	public void setForcePersonalDesignation(String forcePersonalDesignation) {
		this.forcePersonalDesignation = forcePersonalDesignation;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	
	
}
