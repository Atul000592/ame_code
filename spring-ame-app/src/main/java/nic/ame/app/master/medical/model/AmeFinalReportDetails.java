package nic.ame.app.master.medical.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.admin.model.MedicalBoard;

@Entity
@Table
public class AmeFinalReportDetails {

	
	@Id
	private String ameId;
	
	private String candidateforcePersonalId;
	
	private String irlaNo;
	

	

    private String boardId;
	
	 private Date createdOn;
	
     private String clientIpAddress;
	
     private String ameYear;
    
     private int status;
     
     
     private String finalCategoryAwarded;
     
     
     @Column(name = "remark")
     private String remark;
    
    
    @OneToMany(targetEntity = AmeFinalReportBoardMemberDetails.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "fk_ame_Id",referencedColumnName = "ameId")
    private List<AmeFinalReportBoardMemberDetails>  ameFinalReportBoardMemberDetails;
    
  
	public List<AmeFinalReportBoardMemberDetails> getAmeFinalReportBoardMemberDetails() {
		return ameFinalReportBoardMemberDetails;
	}


	public void setAmeFinalReportBoardMemberDetails(
			List<AmeFinalReportBoardMemberDetails> ameFinalReportBoardMemberDetails) {
		this.ameFinalReportBoardMemberDetails = ameFinalReportBoardMemberDetails;
	}


	public String getAmeId() {
		return ameId;
	}


	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}


	public String getCandidateforcePersonalId() {
		return candidateforcePersonalId;
	}


	public void setCandidateforcePersonalId(String candidateforcePersonalId) {
		this.candidateforcePersonalId = candidateforcePersonalId;
	}


	public String getIrlaNo() {
		return irlaNo;
	}


	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}


	


	public String getBoardId() {
		return boardId;
	}


	public void setBoardId(String boardId) {
		this.boardId = boardId;
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
 

	public String getAmeYear() {
		return ameYear;
	}


	public void setAmeYear(String ameYear) {
		this.ameYear = ameYear;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	



	public String getFinalCategoryAwarded() {
		return finalCategoryAwarded;
	}


	public void setFinalCategoryAwarded(String finalCategoryAwarded) {
		this.finalCategoryAwarded = finalCategoryAwarded;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	
	
    
    
}
