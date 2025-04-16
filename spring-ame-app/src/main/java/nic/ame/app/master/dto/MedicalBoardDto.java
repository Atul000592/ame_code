package nic.ame.app.master.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Id;

public class MedicalBoardDto {

	

	private String boardId;
	private String usedFor;
	private String boardAtForceNo;
	private String place;
	

	private Date createdOn;

	private Date fromDate;
	
	private Date toDate;
	
	private String createdBy;
	private String modifiedBy;
	
	private String boardYear;
	

	private int gazettedFlag;
	
	private String ipAddress;

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getUsedFor() {
		return usedFor;
	}

	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}

	public String getBoardAtForceNo() {
		return boardAtForceNo;
	}

	public void setBoardAtForceNo(String boardAtForceNo) {
		this.boardAtForceNo = boardAtForceNo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getBoardYear() {
		return boardYear;
	}

	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}

	public int getGazettedFlag() {
		return gazettedFlag;
	}

	public void setGazettedFlag(int gazettedFlag) {
		this.gazettedFlag = gazettedFlag;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
