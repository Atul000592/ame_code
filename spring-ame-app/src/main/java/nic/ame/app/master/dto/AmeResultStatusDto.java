package nic.ame.app.master.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AmeResultStatusDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String forcePersonnelId;
	private String name;
	private String irlaNo;
	private String forceName;
	private String unitName;
	private String shape;
	private String declarationFilePath;
	private String amefinalReportFilePath;
	private Date createdOn;
	private String boardId;
	
	private String boardYear;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getForcePersonnelId() {
		return forcePersonnelId;
	}
	public void setForcePersonnelId(String forcePersonnelId) {
		this.forcePersonnelId = forcePersonnelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getDeclarationFilePath() {
		return declarationFilePath;
	}
	public void setDeclarationFilePath(String declarationFilePath) {
		this.declarationFilePath = declarationFilePath;
	}
	public String getAmefinalReportFilePath() {
		return amefinalReportFilePath;
	}
	public void setAmefinalReportFilePath(String amefinalReportFilePath) {
		this.amefinalReportFilePath = amefinalReportFilePath;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardYear() {
		return boardYear;
	}
	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}
	
	

}
