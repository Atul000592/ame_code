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

@Table(name="tm_application_state_description")
@Entity
public class ApplicationStateDescription {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String Description;
	private int status;
	private Date createdOn;
	private String createdBy;
	private String createdFrom;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "exam_type")
	private RefMedicalExamType medicalExamType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public RefMedicalExamType getMedicalExamType() {
		return medicalExamType;
	}

	public void setMedicalExamType(RefMedicalExamType medicalExamType) {
		this.medicalExamType = medicalExamType;
	}

	@Override
	public String toString() {
		return "ApplicationStateDescription [id=" + id + ", Description=" + Description + ", status=" + status
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", createdFrom=" + createdFrom
				+ ", medicalExamType=" + medicalExamType + "]";
	}
	
	

}
