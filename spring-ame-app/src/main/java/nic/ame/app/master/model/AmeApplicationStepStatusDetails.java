package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.admin.model.RefMedicalExamType;


@Table (name="tt_ame_application_step_status_details")
@Entity
public class AmeApplicationStepStatusDetails {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;	
	
@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
@JoinColumn(name = "candidate_forcepersonnel_id")
private ForcePersonnel forcePersonnel;

private int year;

private Date createdOn;

private String createdBy;

private String createdFrom;

@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
@JoinColumn(name = "ame_status_code")
private AmeStatusCode ameStatusCode;

@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
@JoinColumn(name = "application_state_description")
private ApplicationStateDescription applicationStateDescription;

@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
@JoinColumn(name = "exam_type")
private RefMedicalExamType refMedicalExamType;

private String remark;

public ForcePersonnel getForcePersonnel() {
	return forcePersonnel;
}

public void setForcePersonnel(ForcePersonnel forcePersonnel) {
	this.forcePersonnel = forcePersonnel;
}

public int getYear() {
	return year;
}

public void setYear(int year) {
	this.year = year;
}

public Date getCreatedOn() {
	return createdOn;
}

public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
}

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public String getCreatedFrom() {
	return createdFrom;
}

public void setCreatedFrom(String createdFrom) {
	this.createdFrom = createdFrom;
}

public AmeStatusCode getAmeStatusCode() {
	return ameStatusCode;
}

public void setAmeStatusCode(AmeStatusCode ameStatusCode) {
	this.ameStatusCode = ameStatusCode;
}

public ApplicationStateDescription getApplicationStateDescription() {
	return applicationStateDescription;
}

public void setApplicationStateDescription(ApplicationStateDescription applicationStateDescription) {
	this.applicationStateDescription = applicationStateDescription;
}

public RefMedicalExamType getRefMedicalExamType() {
	return refMedicalExamType;
}

public void setRefMedicalExamType(RefMedicalExamType refMedicalExamType) {
	this.refMedicalExamType = refMedicalExamType;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

@Override
public String toString() {
	return "AmeApplicationStepStatusDetails [forcePersonnel=" + forcePersonnel + ", year=" + year + ", createdOn="
			+ createdOn + ", createdBy=" + createdBy + ", createdFrom=" + createdFrom + ", ameStatusCode="
			+ ameStatusCode + ", applicationStateDescription=" + applicationStateDescription + ", refMedicalExamType="
			+ refMedicalExamType + ", remark=" + remark + "]";
}




}
