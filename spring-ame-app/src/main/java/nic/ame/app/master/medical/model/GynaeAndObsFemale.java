package nic.ame.app.master.medical.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_medical_examination_gynae_and_obs_female")
@Entity
public class GynaeAndObsFemale {

	@Id
	private String ameId;
	private String menstrualHistory;
	private Date lmp;

	private String obstetricsHistory;
	private Date dateOfLastConfinement;
	private String vaginalDischarge;
	private String uvProlapse;
	private String usgAbdomen;
	private String otherAilment;
	
	private String gFactorCategory;
	private String gFactorType;
	private String gFactorDuration;
	
	//===========================other details========================//
    private Date lastModifiedOn;
    private String lastModifiedBy;
    private String lastModifiedFrom;
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getMenstrualHistory() {
		return menstrualHistory;
	}
	public void setMenstrualHistory(String menstrualHistory) {
		this.menstrualHistory = menstrualHistory;
	}
	
	public Date getLmp() {
		return lmp;
	}
	public void setLmp(Date lmp) {
		this.lmp = lmp;
	}
	public String getObstetricsHistory() {
		return obstetricsHistory;
	}
	public void setObstetricsHistory(String obstetricsHistory) {
		this.obstetricsHistory = obstetricsHistory;
	}
	public Date getDateOfLastConfinement() {
		return dateOfLastConfinement;
	}
	public void setDateOfLastConfinement(Date dateOfLastConfinement) {
		this.dateOfLastConfinement = dateOfLastConfinement;
	}
	public String getVaginalDischarge() {
		return vaginalDischarge;
	}
	public void setVaginalDischarge(String vaginalDischarge) {
		this.vaginalDischarge = vaginalDischarge;
	}
	public String getUvProlapse() {
		return uvProlapse;
	}
	public void setUvProlapse(String uvProlapse) {
		this.uvProlapse = uvProlapse;
	}
	public String getUsgAbdomen() {
		return usgAbdomen;
	}
	public void setUsgAbdomen(String usgAbdomen) {
		this.usgAbdomen = usgAbdomen;
	}
	public String getOtherAilment() {
		return otherAilment;
	}
	public void setOtherAilment(String otherAilment) {
		this.otherAilment = otherAilment;
	}
	public String getgFactorCategory() {
		return gFactorCategory;
	}
	public void setgFactorCategory(String gFactorCategory) {
		this.gFactorCategory = gFactorCategory;
	}
	public String getgFactorType() {
		return gFactorType;
	}
	public void setgFactorType(String gFactorType) {
		this.gFactorType = gFactorType;
	}
	public String getgFactorDuration() {
		return gFactorDuration;
	}
	public void setgFactorDuration(String gFactorDuration) {
		this.gFactorDuration = gFactorDuration;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date date) {
		this.lastModifiedOn = date;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	public void setLastModifiedFrom(String lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	
	

}
