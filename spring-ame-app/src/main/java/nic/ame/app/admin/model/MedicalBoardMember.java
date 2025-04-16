package nic.ame.app.admin.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Table
@Entity
@IdClass(MedicalBoardMemberPK.class)
public class MedicalBoardMember {
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	 */
	@Id
	@Column(name="force_personal_id")
	private String forcePersonalId;
	@Column(name = "board_id")
	@Id
	private String boardId;
	private String name; 
	private String designation;
	private String force_no;
	@Id
	private String roleName;
	@Column(length = 10)
	private String unit;
	private int statusCode;
	
	private String createdBy;
	private String modifiedBy;
	private String boardYear;
	private int gazettedFlag;
	
    private String linkMember;
    @Column(nullable = true)
    private int alternateMedicalRoleId;
    private Date changeStatusFromDate;
    private Date changeStatusToDate;
    private String changeRemark;
    @Column(nullable = true)
    private int resereveFlag;
    
    
    
    //================Master table=================//
    private String changeReason;
    @Column(name = "rank")
	private String rank;
	
    private String gender;
    public String getForcePersonalId() {
		return forcePersonalId;
	}
	public String getBoardId() {
		return boardId;
	}
	public String getName() {
		return name;
	}
	public String getDesignation() {
		return designation;
	}
	public String getForce_no() {
		return force_no;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getUnit() {
		return unit;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getBoardYear() {
		return boardYear;
	}
	public int getGazettedFlag() {
		return gazettedFlag;
	}
	public String getLinkMember() {
		return linkMember;
	}
	public int getAlternateMedicalRoleId() {
		return alternateMedicalRoleId;
	}
	public Date getChangeStatusFromDate() {
		return changeStatusFromDate;
	}
	public Date getChangeStatusToDate() {
		return changeStatusToDate;
	}
	public String getChangeRemark() {
		return changeRemark;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public String getRank() {
		return rank;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public void setForce_no(String force_no) {
		this.force_no = force_no;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setBoardYear(String boardYear) {
		this.boardYear = boardYear;
	}
	public void setGazettedFlag(int gazettedFlag) {
		this.gazettedFlag = gazettedFlag;
	}
	public void setLinkMember(String linkMember) {
		this.linkMember = linkMember;
	}
	public void setAlternateMedicalRoleId(int alternateMedicalRoleId) {
		this.alternateMedicalRoleId = alternateMedicalRoleId;
	}
	public void setChangeStatusFromDate(Date changeStatusFromDate) {
		this.changeStatusFromDate = changeStatusFromDate;
	}
	public void setChangeStatusToDate(Date changeStatusToDate) {
		this.changeStatusToDate = changeStatusToDate;
	}
	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public int getResereveFlag() {
		return resereveFlag;
	}
	public void setResereveFlag(int resereveFlag) {
		this.resereveFlag = resereveFlag;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
    
    
    
    
	
	
	


}
