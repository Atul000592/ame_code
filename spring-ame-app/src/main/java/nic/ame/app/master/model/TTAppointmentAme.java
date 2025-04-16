package nic.ame.app.master.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_appointment_ame")
@Entity
public class TTAppointmentAme {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String forcePersonalId;
	private String boardId;
	private int forceNo;
	private int unitNo;
	private String irlaNo;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date toDate;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date fromDate;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date createdOn;
	private String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom; 
	private int declarationStatus;
	
	private int declarationInvalidFlag;
	
	private int ameRemarkCode;
	
	private String remark;
	
	@Column(nullable = true)
	private int isAppointmentValid=0;
	
	private String declarationYear;
	
	
	private Boolean rescheduleFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public int getForceNo() {
		return forceNo;
	}

	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}

	public int getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}

	public String getIrlaNo() {
		return irlaNo;
	}

	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public int getDeclarationStatus() {
		return declarationStatus;
	}

	public void setDeclarationStatus(int declarationStatus) {
		this.declarationStatus = declarationStatus;
	}

	public int getDeclarationInvalidFlag() {
		return declarationInvalidFlag;
	}

	public void setDeclarationInvalidFlag(int declarationInvalidFlag) {
		this.declarationInvalidFlag = declarationInvalidFlag;
	}

	public int getAmeRemarkCode() {
		return ameRemarkCode;
	}

	public void setAmeRemarkCode(int ameRemarkCode) {
		this.ameRemarkCode = ameRemarkCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsAppointmentValid() {
		return isAppointmentValid;
	}

	public void setIsAppointmentValid(int isAppointmentValid) {
		this.isAppointmentValid = isAppointmentValid;
	}

	public String getDeclarationYear() {
		return declarationYear;
	}

	public void setDeclarationYear(String declarationYear) {
		this.declarationYear = declarationYear;
	}

	public Boolean getRescheduleFlag() {
		return rescheduleFlag;
	}

	public void setRescheduleFlag(Boolean rescheduleFlag) {
		this.rescheduleFlag = rescheduleFlag;
	}
	

}
