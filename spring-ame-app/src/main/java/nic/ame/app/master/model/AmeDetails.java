package nic.ame.app.master.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tt_ame_details")
@Entity
public class AmeDetails {
	
	@Id
	@Column(name="ame_id")
	private String ameId;
	
	@Column(name="force_personnel_id")
	private String forcePersonalId;

	@Column(name="force_id")
	private String forceId;

	@Column(name="name")
	private String name;
	
	@Column(name="force_no")
	private Integer forceNo;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="attached_unit")
	private String attachedUnit;
	
	@Column(name="rank")
	private Integer rank;
	
	@Column(name="designation")
	private Integer designation;
	
	@Column(name="grade")
	private String grade;
	
	@Column(name="joining_date")
	private Date joiningDate;
	
	@Column(name="dob")
	private Date dob; 
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="gazetted_non_gazetted_flag")
	private String gazettedNonGazettedFlag;
	
	@Column(name="last_ame_date")
	private Date lastAmeDate;
	
	@Column(name="ame_place")
	private String amePlace;
	
	@Column(name="total_service")
	private String totalService;
	
	@Column(name="last_modified_on")
	private Date lastModifiedOn;
	
	@Column(name="last_modified_by")
	private String lastModifiedBy;
	
	@Column(name="last_modified_from")
	private String lastModifiedFrom;
	
	
	
	//---------------------------------------------Getters and Setters------------------------------------------------------//

	

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	
	

	

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

	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

	public String getAttachedUnit() {
		return attachedUnit;
	}

	public void setAttachedUnit(String attachedUnit) {
		this.attachedUnit = attachedUnit;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
