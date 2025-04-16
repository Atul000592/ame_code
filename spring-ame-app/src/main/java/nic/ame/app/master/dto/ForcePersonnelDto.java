package nic.ame.app.master.dto;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Id;

public class ForcePersonnelDto {

	@Id
	private String forcePersonalId;

	//  forceId = IRLA number
	private String forceId;

	private String forceName;
	
	private String forceCodeName;
	
	private String name;
	
	private Integer forceNo;
	
	private String rank;
	
	private String rankSubCode;

	private String designation;

	private String unit;
	
	private String unitName;
	
	private String attachUnit;
	
	private String attachUnitName;

	private String jobStatus;

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

	private String previousAmeCategory;

	private String amePlace;

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

	public String getForceName() {
		return forceName;
	}

	public void setForceName(String forceName) {
		this.forceName = forceName;
	}

	public String getForceCodeName() {
		return forceCodeName;
	}

	public void setForceCodeName(String forceCodeName) {
		this.forceCodeName = forceCodeName;
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

	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getRankSubCode() {
		return rankSubCode;
	}

	public void setRankSubCode(String rankSubCode) {
		this.rankSubCode = rankSubCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAttachUnit() {
		return attachUnit;
	}

	public void setAttachUnit(String attachUnit) {
		this.attachUnit = attachUnit;
	}

	public String getAttachUnitName() {
		return attachUnitName;
	}

	public void setAttachUnitName(String attachUnitName) {
		this.attachUnitName = attachUnitName;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
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

	@Override
	public String toString() {
		return "ForcePersonnelDto [forcePersonalId=" + forcePersonalId + ", forceId=" + forceId + ", forceName="
				+ forceName + ", forceCodeName=" + forceCodeName + ", name=" + name + ", force_no=" + forceNo
				+ ", rank=" + rank + ", rankSubCode=" + rankSubCode + ", designation=" + designation + ", unit=" + unit
				+ ", unitName=" + unitName + ", attachUnit=" + attachUnit + ", attachUnitName=" + attachUnitName
				+ ", jobStatus=" + jobStatus + ", joiningDate=" + joiningDate + ", dob=" + dob + ", dojPresentRank="
				+ dojPresentRank + ", lastAmeDate=" + lastAmeDate + ", gender=" + gender + ", cadreMedical="
				+ cadreMedical + ", gazettedNonGazettedFlag=" + gazettedNonGazettedFlag + ", maritalStatus="
				+ maritalStatus + ", mobileNumber=" + mobileNumber + ", emailId=" + emailId + ", lastAmeShape="
				+ lastAmeShape + ", previousAmeCategory=" + previousAmeCategory + ", amePlace=" + amePlace + "]";
	}

	
	

	
	

}
