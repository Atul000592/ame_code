package nic.ame.app.master.model;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.RefRoleMedical;

@Entity
@Table(name = "tt_examination_application_flow_history")
public class ExaminationApplicationFlowHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ame_id")
	private String ameId;

	@Column(name = "tm_exam_code")
	private String tmExamCode;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private LocalDateTime createOn;

	@Column(name = "remark")
	private String remark;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "board_id")
	private Long boardId;

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_receiver_role_id")
	private RefRoleMedical receiverRoleCode;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sender_role_id")
	private RefRoleMedical senderRoleCode;

	@Column(name = "is_esign_status")
	private Boolean isEsignStatus;

	@Column(name = "r_code")
	private String rCode;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "status_code")
	private AmeApprovalStatus ameApprovalStatus;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;

	private boolean isApproved;
	
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

	public String getTmExamCode() {
		return tmExamCode;
	}

	public void setTmExamCode(String tmExamCode) {
		this.tmExamCode = tmExamCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreateOn() {
		return createOn;
	}

	public void setCreateOn(LocalDateTime createOn) {
		this.createOn = createOn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	

	

	public RefRoleMedical getReceiverRoleCode() {
		return receiverRoleCode;
	}

	public void setReceiverRoleCode(RefRoleMedical receiverRoleCode) {
		this.receiverRoleCode = receiverRoleCode;
	}

	public RefRoleMedical getSenderRoleCode() {
		return senderRoleCode;
	}

	public void setSenderRoleCode(RefRoleMedical senderRoleCode) {
		this.senderRoleCode = senderRoleCode;
	}

	public Boolean getIsEsignStatus() {
		return isEsignStatus;
	}

	public void setIsEsignStatus(Boolean isEsignStatus) {
		this.isEsignStatus = isEsignStatus;
	}

	public String getrCode() {
		return rCode;
	}

	public void setrCode(String rCode) {
		this.rCode = rCode;
	}

	public AmeApprovalStatus getAmeApprovalStatus() {
		return ameApprovalStatus;
	}

	public void setAmeApprovalStatus(AmeApprovalStatus ameApprovalStatus) {
		this.ameApprovalStatus = ameApprovalStatus;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	
	
}
