package nic.ame.app.master.dto;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AmeDeclarationIndividualModelDto {
	

	

	private String ameId;

	
	private String forcePersonalId;
	

	private String forceId;
	
	private String name;
	

	private Date date_of_joining;
	

	private Date declarationDate;
	

	private String rank;


	private String designation;
	
	private Date lastAmeDate;


	private String lastAmePlace;


	private String place;

	
	private String previous_unit;
	
  
	private String curent_new_unit;
	
	private String remark;

  
	private String selfCertifyAboveInfo;
	

	private String flag;


	private String status;
	

	private int declaration_year;
	
	/*
	   @Column(name="client_from") private char clientFrom;
	*/
	
	
    private int ameFinalStatus;
		

	private int finalUploadFlag;


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

}
