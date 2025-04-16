package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name = "tt_medical_examination_general_examination")
@Entity
public class GeneralExamination {
	@Id
	private String ameId;
	private String bodyBuild;
	private Integer bpSystolic;
	private Integer bpDiastolic;
	private String tounge;
	private String pulseRate;
	private String temperature;
	private String anaemia;
	private String cyanosis;
	private String respiration;
	private String icterus;
    private String oedema;
    private String clubbing;
    private String koilonychia;

    private String lymphGlandsPalpable;

    private String tonsils;
    private String jvp;
    private String teethDenture;
    private String thyroid;
    private String throat;
    private String spleen;
    private String liver;
//    private String cvs;

    private String s1;
    private String s2;
    private String murmur;
    private Float distanceCoveredInMin;
    private String breastExamination;
    
    private Date lastModifiedOn;
    private String lastModifiedBy;
    private String lastModifiedFrom;
    
    
    
    
    
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getBodyBuild() {
		return bodyBuild;
	}
	public void setBodyBuild(String bodyBuild) {
		this.bodyBuild = bodyBuild;
	}
	public Integer getBpSystolic() {
		return bpSystolic;
	}
	public void setBpSystolic(Integer bpSystolic) {
		this.bpSystolic = bpSystolic;
	}
	public Integer getBpDiastolic() {
		return bpDiastolic;
	}
	public void setBpDiastolic(Integer bpDiastolic) {
		this.bpDiastolic = bpDiastolic;
	}
	public String getTounge() {
		return tounge;
	}
	public void setTounge(String tounge) {
		this.tounge = tounge;
	}
	public String getPulseRate() {
		return pulseRate;
	}
	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getAnaemia() {
		return anaemia;
	}
	public void setAnaemia(String anaemia) {
		this.anaemia = anaemia;
	}
	public String getCyanosis() {
		return cyanosis;
	}
	public void setCyanosis(String cyanosis) {
		this.cyanosis = cyanosis;
	}
	public String getRespiration() {
		return respiration;
	}
	public void setRespiration(String respiration) {
		this.respiration = respiration;
	}
	public String getIcterus() {
		return icterus;
	}
	public void setIcterus(String icterus) {
		this.icterus = icterus;
	}
	public String getOedema() {
		return oedema;
	}
	public void setOedema(String oedema) {
		this.oedema = oedema;
	}
	public String getClubbing() {
		return clubbing;
	}
	public void setClubbing(String clubbing) {
		this.clubbing = clubbing;
	}
	public String getKoilonychia() {
		return koilonychia;
	}
	public void setKoilonychia(String koilonychia) {
		this.koilonychia = koilonychia;
	}
	public String getLymphGlandsPalpable() {
		return lymphGlandsPalpable;
	}
	public void setLymphGlandsPalpable(String lymphGlandsPalpable) {
		this.lymphGlandsPalpable = lymphGlandsPalpable;
	}
	public String getTonsils() {
		return tonsils;
	}
	public void setTonsils(String tonsils) {
		this.tonsils = tonsils;
	}
	public String getJvp() {
		return jvp;
	}
	public void setJvp(String jvp) {
		this.jvp = jvp;
	}
	public String getTeethDenture() {
		return teethDenture;
	}
	public void setTeethDenture(String teethDenture) {
		this.teethDenture = teethDenture;
	}
	public String getThyroid() {
		return thyroid;
	}
	public void setThyroid(String thyroid) {
		this.thyroid = thyroid;
	}
	public String getThroat() {
		return throat;
	}
	public void setThroat(String throat) {
		this.throat = throat;
	}
	public String getSpleen() {
		return spleen;
	}
	public void setSpleen(String spleen) {
		this.spleen = spleen;
	}
	public String getLiver() {
		return liver;
	}
	public void setLiver(String liver) {
		this.liver = liver;
	}
	
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getMurmur() {
		return murmur;
	}
	public void setMurmur(String murmur) {
		this.murmur = murmur;
	}
	public Float getDistanceCoveredInMin() {
		return distanceCoveredInMin;
	}
	public void setDistanceCoveredInMin(Float distanceCoveredInMin) {
		this.distanceCoveredInMin = distanceCoveredInMin;
	}
	public String getBreastExamination() {
		return breastExamination;
	}
	public void setBreastExamination(String breastExamination) {
		this.breastExamination = breastExamination;
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
