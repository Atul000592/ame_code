package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tt_ame_application_flow")
@Entity
public class AmeApplicationFlow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String forcePersonnelId;
	
	private String ameId;
	
	private int code;
	
	private Date createdOn;
	
	private String createdBy;
	
	private Date year;
	
	private String boardId;
/*	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "code", referencedColumnName = "ameStatusCode")
	 private AmeStatusCode ameStatusCode ;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "boardId", referencedColumnName = "boardId")
	 private MedicalBoard medicalBoard ;
*/
//------------------------------------------------Getters and Setters----------------------------------------------------------------//

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


	public String getAmeId() {
		return ameId;
	}


	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
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


	public Date getYear() {
		return year;
	}


	public void setYear(Date year) {
		this.year = year;
	}


	public String getBoardId() {
		return boardId;
	}


	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

/*
	public AmeStatusCode getAmeStatusCode() {
		return ameStatusCode;
	}


	public void setAmeStatusCode(AmeStatusCode ameStatusCode) {
		this.ameStatusCode = ameStatusCode;
	}


	public MedicalBoard getMedicalBoard() {
		return medicalBoard;
	}


	public void setMedicalBoard(MedicalBoard medicalBoard) {
		this.medicalBoard = medicalBoard;
	}
	
	
*/	
	
	
	
	
	
	
	

}
