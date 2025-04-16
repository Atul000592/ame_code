package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_medical_examination_Psychological_Assessment_As_Laid_Down")
@Entity
public class PsychologicalAssessmentAsLaidDown {

	@Id
	
	//@Column(name = "ame_id")
	private String AmeId;
	private String psychiatricIllnessHistory;
	private String alcholic_drug_abuse_history;
	private String wrong_decision_public_castigation;
	private String injury_infective_metabolic_encephalopathy_history;
    private String objective_psychometric_scale;
   // private int serialNumber;
    private String category;
    private String duration;
    private String type;
    
    private Date lastModifiedOn;
    private String lastModifiedFrom;
    private String lastModifiedBy;
    
    
    
    //---------------------------------------Getters and Setters--------------------------------------------------------//
	public String getAmeId() {
		return AmeId;
	}
	public void setAmeId(String ameId) {
		AmeId = ameId;
	}
	public String getPsychiatricIllnessHistory() {
		return psychiatricIllnessHistory;
	}
	public void setPsychiatricIllnessHistory(String psychiatricIllnessHistory) {
		this.psychiatricIllnessHistory = psychiatricIllnessHistory;
	}
	public String getAlcholic_drug_abuse_history() {
		return alcholic_drug_abuse_history;
	}
	public void setAlcholic_drug_abuse_history(String alcholic_drug_abuse_history) {
		this.alcholic_drug_abuse_history = alcholic_drug_abuse_history;
	}
	public String getWrong_decision_public_castigation() {
		return wrong_decision_public_castigation;
	}
	public void setWrong_decision_public_castigation(String wrong_decision_public_castigation) {
		this.wrong_decision_public_castigation = wrong_decision_public_castigation;
	}
	public String getInjury_infective_metabolic_encephalopathy_history() {
		return injury_infective_metabolic_encephalopathy_history;
	}
	public void setInjury_infective_metabolic_encephalopathy_history(
			String injury_infective_metabolic_encephalopathy_history) {
		this.injury_infective_metabolic_encephalopathy_history = injury_infective_metabolic_encephalopathy_history;
	}
	public String getObjective_psychometric_scale() {
		return objective_psychometric_scale;
	}
	public void setObjective_psychometric_scale(String objective_psychometric_scale) {
		this.objective_psychometric_scale = objective_psychometric_scale;
	}

	/*
	 * public int getSerialNumber() { return serialNumber; } public void
	 * setSerialNumber(int serialNumber) { this.serialNumber = serialNumber; }
	 */
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	public void setLastModifiedFrom(String lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	
    
    
    
	
	
    
    
    
}
