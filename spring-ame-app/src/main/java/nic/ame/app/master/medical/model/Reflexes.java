package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name = "tt_medical_examination_reflexes")
@Entity
public class Reflexes {

	@Id
	private String ameId;
	private String plantar;
	private String abdominal;

	private String gowerSign;
	private String cerebellarSign;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	    
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getPlantar() {
		return plantar;
	}
	public void setPlantar(String plantar) {
		this.plantar = plantar;
	}
	public String getAbdominal() {
		return abdominal;
	}
	public void setAbdominal(String abdominal) {
		this.abdominal = abdominal;
	}
	public String getGowerSign() {
		return gowerSign;
	}
	public void setGowerSign(String gowerSign) {
		this.gowerSign = gowerSign;
	}
	public String getCerebellarSign() {
		return cerebellarSign;
	}
	public void setCerebellarSign(String cerebellarSign) {
		this.cerebellarSign = cerebellarSign;
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
