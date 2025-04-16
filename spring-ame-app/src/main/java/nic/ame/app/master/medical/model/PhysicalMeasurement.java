package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "tt_medical_examination_physical_measurement")
@Entity
public class PhysicalMeasurement {
	@Id
	private String ameId;
	private float height;
	private float weight;
	private float chestExpanded;
	private float chestUnexpanded;
	private float abdominalGirth;
	private float trasTroChantericGirth;
	private float bmi;
    private float ratioAT;
    
    private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	
	

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getChestExpanded() {
		return chestExpanded;
	}

	public void setChestExpanded(float chestExpanded) {
		this.chestExpanded = chestExpanded;
	}

	public float getChestUnexpanded() {
		return chestUnexpanded;
	}

	public void setChestUnexpanded(float chestUnexpanded) {
		this.chestUnexpanded = chestUnexpanded;
	}

	public float getAbdominalGirth() {
		return abdominalGirth;
	}

	public void setAbdominalGirth(float abdominalGirth) {
		this.abdominalGirth = abdominalGirth;
	}

	public float getTrasTroChantericGirth() {
		return trasTroChantericGirth;
	}

	public void setTrasTroChantericGirth(float trasTroChantericGirth) {
		this.trasTroChantericGirth = trasTroChantericGirth;
	}

	public float getBmi() {
		return bmi;
	}

	public void setBmi(float bmi) {
		this.bmi = bmi;

	}

	public float getRatioAT() {
		return ratioAT;
	}

	public void setRatioAT(float ratioAT) {
		this.ratioAT = ratioAT;
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
