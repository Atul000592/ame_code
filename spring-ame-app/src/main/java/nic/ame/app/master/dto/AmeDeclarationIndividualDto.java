package nic.ame.app.master.dto;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;

public class AmeDeclarationIndividualDto {
	
	
	private String forcePersonalId;

	private String place;

	private int sNumber;

	private Date declarationDate;

	private String selfCertifyAboveInfo;

	private Date lastAmeDate;

	private String lastAmePlace;

	private String name;

	private String remark;

	private String ameId;

	private Date date_of_joining;

	private String curent_new_unit;

	private String previous_unit;

	private String flag;

	private String rank;

	private String designation;

	private String status;
	
	private String forceId;
	
	private long declaration_year;
	
	private int ameFinalStatus;
		
	private int finalUploadFlag;
	
	private int subRankcode;
	
	private int boardMemberESignStatus;
	
	private int ameFormFill;
	private int esignStatus;
	
	private int uploadStatus;
	
	private int physicalSignStatus;
	
private int physicalReportUploadBy;
	
	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getsNumber() {
		return sNumber;
	}

	public void setsNumber(int sNumber) {
		this.sNumber = sNumber;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getSelfCertifyAboveInfo() {
		return selfCertifyAboveInfo;
	}

	public void setSelfCertifyAboveInfo(String selfCertifyAboveInfo) {
		this.selfCertifyAboveInfo = selfCertifyAboveInfo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	public Date getDate_of_joining() {
		return date_of_joining;
	}

	public void setDate_of_joining(Date date_of_joining) {
		this.date_of_joining = date_of_joining;
	}

	public String getCurent_new_unit() {
		return curent_new_unit;
	}

	public void setCurent_new_unit(String curent_new_unit) {
		this.curent_new_unit = curent_new_unit;
	}

	public String getPrevious_unit() {
		return previous_unit;
	}

	public void setPrevious_unit(String previous_unit) {
		this.previous_unit = previous_unit;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getForceId() {
		return forceId;
	}

	public void setForceId(String forceId) {
		this.forceId = forceId;
	}

	public long getDeclaration_year() {
		return declaration_year;
	}

	public void setDeclaration_year(long declaration_year) {
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

	public int getSubRankcode() {
		return subRankcode;
	}

	public void setSubRankcode(int subRankcode) {
		this.subRankcode = subRankcode;
	}

	
	
	
	public int getBoardMemberESignStatus() {
		return boardMemberESignStatus;
	}

	public void setBoardMemberESignStatus(int boardMemberESignStatus) {
		this.boardMemberESignStatus = boardMemberESignStatus;
	}

	

	public int getAmeFormFill() {
		return ameFormFill;
	}

	public void setAmeFormFill(int ameFormFill) {
		this.ameFormFill = ameFormFill;
	}

	public int getEsignStatus() {
		return esignStatus;
	}

	public void setEsignStatus(int esignStatus) {
		this.esignStatus = esignStatus;
	}

	public int getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public int getPhysicalSignStatus() {
		return physicalSignStatus;
	}

	public void setPhysicalSignStatus(int physicalSignStatus) {
		this.physicalSignStatus = physicalSignStatus;
	}
	
	
	

	public int getPhysicalReportUploadBy() {
		return physicalReportUploadBy;
	}

	public void setPhysicalReportUploadBy(int physicalReportUploadBy) {
		this.physicalReportUploadBy = physicalReportUploadBy;
	}

	@Override
	public String toString() {
		return "AmeDeclarationIndividualDto [forcePersonalId=" + forcePersonalId + ", place=" + place + ", sNumber="
				+ sNumber + ", declarationDate=" + declarationDate + ", selfCertifyAboveInfo=" + selfCertifyAboveInfo
				+ ", lastAmeDate=" + lastAmeDate + ", lastAmePlace=" + lastAmePlace + ", name=" + name + ", remark="
				+ remark + ", ameId=" + ameId + ", date_of_joining=" + date_of_joining + ", curent_new_unit="
				+ curent_new_unit + ", previous_unit=" + previous_unit + ", flag=" + flag + ", rank=" + rank
				+ ", designation=" + designation + ", status=" + status + ", forceId=" + forceId + ", declaration_year="
				+ declaration_year + ", ameFinalStatus=" + ameFinalStatus + ", finalUploadFlag=" + finalUploadFlag
				+ ", subRankcode=" + subRankcode + ", boardMemberESignStatus=" + boardMemberESignStatus
				+ ", ameFormFill=" + ameFormFill + ", esignStatus=" + esignStatus + ", uploadStatus=" + uploadStatus
				+ ", physicalSignStatus=" + physicalSignStatus + "]";
	}

	

	
	
	
	
	
	

}
