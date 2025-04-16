package nic.ame.app.master.model;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tt_force_personnel")

public class ForcePersonnel {

	@Id
	@Column(name="force_personal_id")
	private String forcePersonalId;

	private String forceId;

	private String name;

	private Integer forceNo;
	
	private Integer rank;
	
	private Integer designation;
	
	private String unit;
	
	private int jobStatus;
	
	private Date joiningDate;
	
	private Date dob; 
	
	private Date dojPresentRank;

	private Date lastAmeDate;

	private String gender;

	private String cadreMedical;
	
	private String gazettedNonGazettedFlag;
	
	private String maritalStatus;
		
	private BigInteger mobileNumber;
	
	private String emailId;
	
	private String lastAmeShape;
	
    private String attachUnit;
    
    private String previousAmeCategory;
    
    private String amePlace;
    
    private String doctorFlag;
    
    @Transient
    private String designationRankName;
    
    @Transient
    private String ameId;
    
    
    
  //-----------------------------------------------------Getters and Setters------------------------------------------------------------//	
    
	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}

	public String getForceId() {
		return forceId;
	}

	public void setForceId(String forceId) {
		this.forceId = forceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getForceNo() {
		return forceNo;
	}

	public void setforceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getDesignation() {
		return designation;
	}

	public void setDesignation(Integer designation) {
		this.designation = designation;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDojPresentRank() {
		return dojPresentRank;
	}

	public void setDojPresentRank(Date dojPresentRank) {
		this.dojPresentRank = dojPresentRank;
	}

	public Date getLastAmeDate() {
		return lastAmeDate;
	}

	public void setLastAmeDate(Date lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCadreMedical() {
		return cadreMedical;
	}

	public void setCadreMedical(String cadreMedical) {
		this.cadreMedical = cadreMedical;
	}

	public String getGazettedNonGazettedFlag() {
		return gazettedNonGazettedFlag;
	}

	public void setGazettedNonGazettedFlag(String gazettedNonGazettedFlag) {
		this.gazettedNonGazettedFlag = gazettedNonGazettedFlag;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public BigInteger getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(BigInteger mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLastAmeShape() {
		return lastAmeShape;
	}

	public void setLastAmeShape(String lastAmeShape) {
		this.lastAmeShape = lastAmeShape;
	}

	public String getAttachUnit() {
		return attachUnit;
	}

	public void setAttachUnit(String attachUnit) {
		this.attachUnit = attachUnit;
	}

	public String getPreviousAmeCategory() {
		return previousAmeCategory;
	}

	public void setPreviousAmeCategory(String previousAmeCategory) {
		this.previousAmeCategory = previousAmeCategory;
	}

	public String getAmePlace() {
		return amePlace;
	}

	public void setAmePlace(String amePlace) {
		this.amePlace = amePlace;
	}

	public String getDoctorFlag() {
		return doctorFlag;
	}

	public void setDoctorFlag(String doctorFlag) {
		this.doctorFlag = doctorFlag;
	}

	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public String getDesignationRankName() {
		return designationRankName;
	}

	public void setDesignationRankName(String designationRankName) {
		this.designationRankName = designationRankName;
	}

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
    
    
    

	
	
    
    
	
	
		
	

}

