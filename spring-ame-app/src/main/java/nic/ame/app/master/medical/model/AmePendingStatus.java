package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tt_ame_pendding_status")
public class AmePendingStatus {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "force_personal_id")
	private String forcePersonalId;

	@Column(name = "board_id")
	private String boardId;

	@Column(name = "force_no")
	private int forceNo;

	@Column(name = "unit_no")
	private int unitNo;

	@Column(name = "irla_no")
	private String irlaNo;

	@Column(name = "ame_id")
	private String ameId;
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "candidate_force_personal_id")
	private String candidateForcePersonalId;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "client_ip_address")
	private String clientIpAddress;

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

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCandidateForcePersonalId() {
		return candidateForcePersonalId;
	}

	public void setCandidateForcePersonalId(String candidateForcePersonalId) {
		this.candidateForcePersonalId = candidateForcePersonalId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}
	
     
}
