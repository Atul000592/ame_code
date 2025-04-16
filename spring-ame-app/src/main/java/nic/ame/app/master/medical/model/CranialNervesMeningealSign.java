package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name = "tt_medical_examination_cranial_nerves_meningeal_sign")
@Entity
public class CranialNervesMeningealSign{

	@Id
	private String ameId;
	private String cranialNerves;
	private String meningealSignIfAny;
	//private String motorSystem;
	private String nutritionOfMuscles;
	private String wasting;
	private String tone;
	private String coordination;
	private String abnormalMovementFasiculation;
	private String power;
	private String dtr;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	    
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getCranialNerves() {
		return cranialNerves;
	}
	public void setCranialNerves(String cranialNerves) {
		this.cranialNerves = cranialNerves;
	}
	public String getMeningealSignIfAny() {
		return meningealSignIfAny;
	}
	public void setMeningealSignIfAny(String meningealSignIfAny) {
		this.meningealSignIfAny = meningealSignIfAny;
	}
	public String getNutritionOfMuscles() {
		return nutritionOfMuscles;
	}
	public void setNutritionOfMuscles(String nutritionOfMuscles) {
		this.nutritionOfMuscles = nutritionOfMuscles;
	}
	public String getWasting() {
		return wasting;
	}
	public void setWasting(String wasting) {
		this.wasting = wasting;
	}
	public String getTone() {
		return tone;
	}
	public void setTone(String tone) {
		this.tone = tone;
	}
	public String getCoordination() {
		return coordination;
	}
	public void setCoordination(String coordination) {
		this.coordination = coordination;
	}
	public String getAbnormalMovementFasiculation() {
		return abnormalMovementFasiculation;
	}
	public void setAbnormalMovementFasiculation(String abnormalMovementFasiculation) {
		this.abnormalMovementFasiculation = abnormalMovementFasiculation;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getDtr() {
		return dtr;
	}
	public void setDtr(String dtr) {
		this.dtr = dtr;
	}
	
	
	
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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
