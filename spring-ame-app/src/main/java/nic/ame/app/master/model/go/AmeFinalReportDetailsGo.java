package nic.ame.app.master.model.go;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;

@Table
@Entity
public class AmeFinalReportDetailsGo {
	
	@Id
	private String uniqueAmeId;
	private String ameId;
	@Column(name = "candidate_force_personnel_id")
    private String candidateforcePersonalId;
    private String boardId;
	private Date createdOn;
    private String clientIpAddress;
	private String ameYear;
    private int status;
    private String finalCategoryAwarded;
     
     
     @Column(name = "remark")
     private String remark;
    
    
    @OneToMany(targetEntity = AmeFinalReportBoardMemberDetailsGo.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "fk_unique_ame_Id",referencedColumnName = "uniqueAmeId")
    private List<AmeFinalReportBoardMemberDetailsGo>  ameFinalReportBoardMemberDetailsGo;


	public String getUniqueAmeId() {
		return uniqueAmeId;
	}


	public void setUniqueAmeId(String uniqueAmeId) {
		this.uniqueAmeId = uniqueAmeId;
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


	public List<AmeFinalReportBoardMemberDetailsGo> getAmeFinalReportBoardMemberDetailsGo() {
		return ameFinalReportBoardMemberDetailsGo;
	}


	public void setAmeFinalReportBoardMemberDetailsGo(
			List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetailsGo) {
		this.ameFinalReportBoardMemberDetailsGo = ameFinalReportBoardMemberDetailsGo;
	}
    

	

}
