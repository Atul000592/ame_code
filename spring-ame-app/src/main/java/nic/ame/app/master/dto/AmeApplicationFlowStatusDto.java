package nic.ame.app.master.dto;

import java.util.Date;

import jakarta.persistence.Column;

public class AmeApplicationFlowStatusDto {

	
	private int id;
	@Column(name = "ame_id")
	private String ameId;
	

	
	@Column(name = "force_id")
	private String forceId;
	
	@Column(name = "appointment_flag")
	private String appointmentFlag;
	
	@Column(name = "ame_form_upload_flag")
	private String ameFormUploadFlag;
	
	@Column(name = "check_up_list_flag")
	private String checkUpListFlag;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "rank")
	private String rank;
	
	@Column(name = "designation")
	private String designation;
	
   
	private Date declarationDate;
	
	private int  forceNo;
	
	private String unit;
	
	private String boardId;
	
	private String irlaNo;
	
	private String forcePersonalId;
	
	private String year;
	

	
	private int ameFinalStatus;

	private int ameFinalUplaod;



	private int esignByCandidate;

	private int ameFinalEsignByBoardMemmber;

	private String boardAtPlace;
	
	
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

	public String getForceId() {
		return forceId;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public String getAppointmentFlag() {
		return appointmentFlag;
	}
	public void setAppointmentFlag(String appointmentFlag) {
		this.appointmentFlag = appointmentFlag;
	}
	public String getAmeFormUploadFlag() {
		return ameFormUploadFlag;
	}
	public void setAmeFormUploadFlag(String ameFormUploadFlag) {
		this.ameFormUploadFlag = ameFormUploadFlag;
	}
	public String getCheckUpListFlag() {
		return checkUpListFlag;
	}
	public void setCheckUpListFlag(String checkUpListFlag) {
		this.checkUpListFlag = checkUpListFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public int getForceNo() {
		return forceNo;
	}
	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	
	
	
	public int getAmeFinalStatus() {
		return ameFinalStatus;
	}
	public void setAmeFinalStatus(int ameFinalStatus) {
		this.ameFinalStatus = ameFinalStatus;
	}
	public int getAmeFinalUplaod() {
		return ameFinalUplaod;
	}
	public void setAmeFinalUplaod(int ameFinalUplaod) {
		this.ameFinalUplaod = ameFinalUplaod;
	}
	public int getEsignByCandidate() {
		return esignByCandidate;
	}
	public void setEsignByCandidate(int esignByCandidate) {
		this.esignByCandidate = esignByCandidate;
	}
	public int getAmeFinalEsignByBoardMemmber() {
		return ameFinalEsignByBoardMemmber;
	}
	public void setAmeFinalEsignByBoardMemmber(int ameFinalEsignByBoardMemmber) {
		this.ameFinalEsignByBoardMemmber = ameFinalEsignByBoardMemmber;
	}
	@Override
	public String toString() {
		return "AmeApplicationFlowStatusDto [id=" + id + ", ameId=" + ameId + ", forceId=" + forceId
				+ ", appointmentFlag=" + appointmentFlag + ", ameFormUploadFlag=" + ameFormUploadFlag
				+ ", checkUpListFlag=" + checkUpListFlag + ", name=" + name + ", rank=" + rank + ", designation="
				+ designation + ", declarationDate=" + declarationDate + ", forceNo=" + forceNo + ", unit=" + unit
				+ ", boardId=" + boardId + ", irlaNo=" + irlaNo + ", forcePersonalId=" + forcePersonalId + "]";
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBoardAtPlace() {
		return boardAtPlace;
	}
	public void setBoardAtPlace(String boardAtPlace) {
		this.boardAtPlace = boardAtPlace;
	}


	
	
}
