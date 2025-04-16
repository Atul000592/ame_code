package nic.ame.app.master.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class AmeApplicationFlowStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ameId;
	private String forcepersonalId;
	private String boardId;
	private String forceId;
	private int appointmentFlag;
	
	@Column(columnDefinition = "int default 0")
	private int ameFormUploadFlag;
	private int checkUpListFlag;
	private String unit;
	private Integer forceNo;
	private Integer ameDataCheckFlag;
	private String ameDataCheckFlagRemark;
	
	@Column(length = 2)
	private int declarationStatus=0;
	@Column(columnDefinition = "int default 0")
	private int ameFinalStatus=0;
	@Column(columnDefinition = "int default 0")
	private int ameFinalUplaod=0;
	@Column(columnDefinition = "int default 0")
	private int ameYear=0;
	@Column(columnDefinition = "int default 0")
	private int esignByCandidate=0;
	@Column(columnDefinition = "int default 0")
	private int ameFinalEsignByBoardMember;
	@Column(columnDefinition = "boolean default true")
	private Boolean rowIsValid;
	
	@Column
	private int physicalReportUploadBy;
	
	
	
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
	public String getForcepersonalId() {
		return forcepersonalId;
	}
	public void setForcepersonalId(String forcepersonalId) {
		this.forcepersonalId = forcepersonalId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getForceId() {
		return forceId;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public int getAppointmentFlag() {
		return appointmentFlag;
	}
	public void setAppointmentFlag(int appointmentFlag) {
		this.appointmentFlag = appointmentFlag;
	}
	public int getAmeFormUploadFlag() {
		return ameFormUploadFlag;
	}
	public void setAmeFormUploadFlag(int ameFormUploadFlag) {
		this.ameFormUploadFlag = ameFormUploadFlag;
	}
	public int getCheckUpListFlag() {
		return checkUpListFlag;
	}
	public void setCheckUpListFlag(int checkUpListFlag) {
		this.checkUpListFlag = checkUpListFlag;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getForceNo() {
		return forceNo;
	}
	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}
	public int getDeclarationStatus() {
		return declarationStatus;
	}
	public void setDeclarationStatus(int declarationStatus) {
		this.declarationStatus = declarationStatus;
	}
	public Integer getAmeDataCheckFlag() {
		return ameDataCheckFlag;
	}
	public void setAmeDataCheckFlag(Integer ameDataCheckFlag) {
		this.ameDataCheckFlag = ameDataCheckFlag;
	}
	public String getAmeDataCheckFlagRemark() {
		return ameDataCheckFlagRemark;
	}
	public void setAmeDataCheckFlagRemark(String ameDataCheckFlagRemark) {
		this.ameDataCheckFlagRemark = ameDataCheckFlagRemark;
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
	public int getAmeYear() {
		return ameYear;
	}
	public void setAmeYear(int ameYear) {
		this.ameYear = ameYear;
	}
	
	
	
	public int getEsignByCandidate() {
		return esignByCandidate;
	}
	public void setEsignByCandidate(int esignByCandidate) {
		this.esignByCandidate = esignByCandidate;
	}
	
	public int getAmeFinalEsignByBoardMember() {
		return ameFinalEsignByBoardMember;
	}
	public void setAmeFinalEsignByBoardMember(int ameFinalEsignByBoardMember) {
		this.ameFinalEsignByBoardMember = ameFinalEsignByBoardMember;
	}
	public Boolean getRowIsValid() {
		return rowIsValid;
	}
	public void setRowIsValid(Boolean rowIsValid) {
		this.rowIsValid = rowIsValid;
	}
	
	
	
	public int getPhysicalReportUploadBy() {
		return physicalReportUploadBy;
	}
	public void setPhysicalReportUploadBy(int physicalReportUploadBy) {
		this.physicalReportUploadBy = physicalReportUploadBy;
	}
	@Override
	public String toString() {
		return "AmeApplicationFlowStatus [id=" + id + ", ameId=" + ameId + ", forcepersonalId=" + forcepersonalId
				+ ", boardId=" + boardId + ", forceId=" + forceId + ", appointmentFlag=" + appointmentFlag
				+ ", ameFormUploadFlag=" + ameFormUploadFlag + ", checkUpListFlag=" + checkUpListFlag + ", unit=" + unit
				+ ", forceNo=" + forceNo + ", ameDataCheckFlag=" + ameDataCheckFlag + ", ameDataCheckFlagRemark="
				+ ameDataCheckFlagRemark + ", declarationStatus=" + declarationStatus + ", ameFinalStatus="
				+ ameFinalStatus + ", ameFinalUplaod=" + ameFinalUplaod + ", ameYear=" + ameYear + "]";
	}
	
}
