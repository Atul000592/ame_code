package nic.ame.app.admin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class MedicalBoardIndividualMappingDto {
	private String boardId;
	private Integer forceNo;
	private String unitId;
	private String usedFor;
	private String medicalBoardPlace;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date boardAssignedDate;
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
	
	
	public Integer getForceNo() {
		return forceNo;
	}
	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	
	public String getMedicalBoardPlace() {
		return medicalBoardPlace;
	}
	public void setMedicalBoardPlace(String medicalBoardPlace) {
		this.medicalBoardPlace = medicalBoardPlace;
	}
	public Date getBoardAssignedDate() {
		return boardAssignedDate;
	}
	public void setBoardAssignedDate(Date boardAssignedDate) {
		this.boardAssignedDate = boardAssignedDate;
	}
	

}
