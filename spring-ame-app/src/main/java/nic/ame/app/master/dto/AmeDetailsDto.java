package nic.ame.app.master.dto;

import java.util.Date;

public class AmeDetailsDto {


	private String forcePersonalId;

	private String forceId;

	private String name;

	private String forceName;
	
	private String forceCodeName;
	
	private String unitName;
	
	private String rank;
	
	private String designation;
	
	private Date joiningDate;
	
	private Date dob;
	
	private Integer age;
	
	private String gender;
	
	private String gazettedNonGazettedFlag;
	
	private Date lastAmeDate;
	
	private String amePlace;
	
	private String totalService;
	
	//---------------------------------------------Getters and Setters------------------------------------------------------//


	

	public String getForceId() {
		return forceId;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGazettedNonGazettedFlag() {
		return gazettedNonGazettedFlag;
	}

	public void setGazettedNonGazettedFlag(String gazettedNonGazettedFlag) {
		this.gazettedNonGazettedFlag = gazettedNonGazettedFlag;
	}

	public Date getLastAmeDate() {
		return lastAmeDate;
	}

	public void setLastAmeDate(Date lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}

	public String getAmePlace() {
		return amePlace;
	}

	public void setAmePlace(String amePlace) {
		this.amePlace = amePlace;
	}

	public String getTotalService() {
		return totalService;
	}

	public void setTotalService(String totalService) {
		this.totalService = totalService;
	}
	

	
}
