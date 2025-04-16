package nic.ame.app.admin.model;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table
public class MedicalBoardIndividualMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="board_id")
	private String boardId;
	@Column(name="force_personal_id")
	private String forcePersonalId;
	private Integer forceNo;
	private String unitNo;
	private String usedFor;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date boardAssignedDate;
	private String remarks;
	private String year;
    private int appointmentStatus;
	private String boardChangeId;
	private String ipAddress;
	@Column(nullable = false)
	private int isMappingValid=0;
	
	private String unmappingReason;
	private Date unmappedOn;
	private String unmappedBy;
	
	private String unmappedFrom;
	
	
	
	
	
	public String getBoardChangeId() {
		return boardChangeId;
	}
	public void setBoardChangeId(String boardChangeId) {
		this.boardChangeId = boardChangeId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	
	public Integer getForceNo() {
		return forceNo;
	}
	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	public Date getBoardAssignedDate() {
		return boardAssignedDate;
	}
	public void setBoardAssignedDate(Date boardAssignedDate) {
		this.boardAssignedDate = boardAssignedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(int appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getIsMappingValid() {
		return isMappingValid;
	}
	public void setIsMappingValid(int isMappingValid) {
		this.isMappingValid = isMappingValid;
	}
	public String getUnmappingReason() {
		return unmappingReason;
	}
	public void setUnmappingReason(String unmappingReason) {
		this.unmappingReason = unmappingReason;
	}
	public Date getUnmappedOn() {
		return unmappedOn;
	}
	public void setUnmappedOn(Date unmappedOn) {
		this.unmappedOn = unmappedOn;
	}
	public String getUnmappedBy() {
		return unmappedBy;
	}
	public void setUnmappedBy(String unmappedBy) {
		this.unmappedBy = unmappedBy;
	}
	public String getUnmappedFrom() {
		return unmappedFrom;
	}
	public void setUnmappedFrom(String unmappedFrom) {
		this.unmappedFrom = unmappedFrom;
	}
	
	

}
