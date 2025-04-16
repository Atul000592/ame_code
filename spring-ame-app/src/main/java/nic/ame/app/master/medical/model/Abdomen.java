package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@jakarta.persistence.Table(name = "tt_medical_examination_abdomen")
@Entity
public class Abdomen {
	@Id
	private String ameId;
	private String pilesFissure;
	private String fitsula;
	private String prolapseRectum;
	private String anyMassPalpable;
	private String anyOtherAbnormality;
	private String pNo;
	private String pType;
	private String pDuration;
	
	private Date lastModifiedOn;
	 private String lastModifiedBy;
	 private String lastModifiedFrom;
	 

	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getPilesFissure() {
		return pilesFissure;
	}
	public void setPilesFissure(String pilesFissure) {
		this.pilesFissure = pilesFissure;
	}
	public String getFitsula() {
		return fitsula;
	}
	public void setFitsula(String fitsula) {
		this.fitsula = fitsula;
	}
	
	
	public String getProlapseRectum() {
		return prolapseRectum;
	}
	public void setProlapseRectum(String prolapseRectum) {
		this.prolapseRectum = prolapseRectum;
	}
	public String getAnyMassPalpable() {
		return anyMassPalpable;
	}
	public void setAnyMassPalpable(String anyMassPalpable) {
		this.anyMassPalpable = anyMassPalpable;
	}
	public String getAnyOtherAbnormality() {
		return anyOtherAbnormality;
	}
	public void setAnyOtherAbnormality(String anyOtherAbnormality) {
		this.anyOtherAbnormality = anyOtherAbnormality;
	}
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public String getpDuration() {
		return pDuration;
	}
	public void setpDuration(String pDuration) {
		this.pDuration = pDuration;
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
