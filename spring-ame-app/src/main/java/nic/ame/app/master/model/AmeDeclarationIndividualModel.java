package nic.ame.app.master.model;

import java.math.BigInteger;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.descriptor.java.YearJavaType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tt_ame_declaration_individual")
public class AmeDeclarationIndividualModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	@Column(nullable = false) 
	private String ameId;

	@Column(nullable = false)
	private String forcePersonalId;
	
	@Column(name = "force_id")
	private String forceId;
	
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Column(name = "date_of_joining")
	private Date date_of_joining;
	
	@JsonFormat(pattern = "dd-MM-yyyy", shape = Shape.STRING)
	@Column(name = "declaration_date")
	private Date declarationDate;
	
	@Column(name = "rank")
	private Integer rank;

	@Column(name = "designation")
	private Integer designation;
	
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@Column(name = "last_ame_date")
	private Date lastAmeDate;

	@Column(name = "last_ame_place")
	private String lastAmePlace;

	@Column(name = "ame_place")
	private String place;

	@Column(name = "previous_unit")
	private String previous_unit;
	
    @Column(name = "current_new_unit")
	private String curent_new_unit;
	
	private String remark;

    @Column(name = "self_certify_above_info")
	private String selfCertifyAboveInfo;
	
	@Column(name = "declaration_flag")
	private String flag;

	@Column(name = "approved_by_doctor_status")
	private String status;
	
	@Column(name="declaration_year")
	private int declaration_year;
	
	/*
	   @Column(name="client_from") private char clientFrom;
	*/
	
	@ColumnDefault("0")
    private int ameFinalStatus;
		
	@ColumnDefault("0")
	private int finalUploadFlag;
	//-------------------Mapping------------------------------------//
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "serial_no", referencedColumnName = "id")
	AmeDeclarationIndividualDetails ameDeclarationIndividualDetails;
	
    private String createdFrom;
    private Date createdOn;
    
    @Column(nullable = true)
    private boolean declarationStatusValid;

    @PrePersist
    protected void onCreate() {
        if (declarationStatusValid == false) { // Only set if it's not already true
            declarationStatusValid = true;
        }
    }

	
//------------------------------------------------Getters and Setters------------------------------------------------------//	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getDate_of_joining() {
		return date_of_joining;
	}

	public void setDate_of_joining(Date date_of_joining) {
		this.date_of_joining = date_of_joining;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
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

	public Date getLastAmeDate() {
		return lastAmeDate;
	}

	public void setLastAmeDate(Date lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}

	public String getLastAmePlace() {
		return lastAmePlace;
	}

	public void setLastAmePlace(String lastAmePlace) {
		this.lastAmePlace = lastAmePlace;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPrevious_unit() {
		return previous_unit;
	}

	public void setPrevious_unit(String previous_unit) {
		this.previous_unit = previous_unit;
	}

	public String getCurent_new_unit() {
		return curent_new_unit;
	}

	public void setCurent_new_unit(String curent_new_unit) {
		this.curent_new_unit = curent_new_unit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSelfCertifyAboveInfo() {
		return selfCertifyAboveInfo;
	}

	public void setSelfCertifyAboveInfo(String selfCertifyAboveInfo) {
		this.selfCertifyAboveInfo = selfCertifyAboveInfo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDeclaration_year() {
		return declaration_year;
	}

	public void setDeclaration_year(int declaration_year) {
		this.declaration_year = declaration_year;
	}

	public int getAmeFinalStatus() {
		return ameFinalStatus;
	}

	public void setAmeFinalStatus(int ameFinalStatus) {
		this.ameFinalStatus = ameFinalStatus;
	}

	public int getFinalUploadFlag() {
		return finalUploadFlag;
	}

	public void setFinalUploadFlag(int finalUploadFlag) {
		this.finalUploadFlag = finalUploadFlag;
	}

	public AmeDeclarationIndividualDetails getAmeDeclarationIndividualDetails() {
		return ameDeclarationIndividualDetails;
	}

	public void setAmeDeclarationIndividualDetails(AmeDeclarationIndividualDetails ameDeclarationIndividualDetails) {
		this.ameDeclarationIndividualDetails = ameDeclarationIndividualDetails;
	}

	public String getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}



	public boolean isDeclarationStatusValid() {
		return declarationStatusValid;
	}



	public void setDeclarationStatusValid(boolean declarationStatusValid) {
		this.declarationStatusValid = declarationStatusValid;
	}

	
	
	
	
	

	
	
}
