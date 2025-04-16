package nic.ame.app.master.medical.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class AmeAppointmentDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String ameId;




	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date appointmentDate;


	@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
    private Date deadLineDate;



	private String statusFlag;

	private String remark;

	@Column(name  ="force_personal_id")
	private String candidateForcePersonalId;

	private String amaForcePersonalid;

	private String appointmentModified;

    public String getCandidateForcePersonalId() {
		return candidateForcePersonalId;
	}

	public void setCandidateForcePersonalId(String candidateForcePersonalId) {
		this.candidateForcePersonalId = candidateForcePersonalId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}





	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAmaForcePersonalid() {
		return amaForcePersonalid;
	}

	public void setAmaForcePersonalid(String amaForcePersonalid) {
		this.amaForcePersonalid = amaForcePersonalid;
	}

	public String getAppointmentModified() {
		return appointmentModified;
	}

	public void setAppointmentModified(String appointmentModified) {
		this.appointmentModified = appointmentModified;
	}

	public Date getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(Date deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}


}
