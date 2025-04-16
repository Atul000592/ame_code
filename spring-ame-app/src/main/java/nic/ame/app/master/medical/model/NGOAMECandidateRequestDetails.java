package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tt_ngo_ame_candidate_request_details")
@Entity
public class NGOAMECandidateRequestDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String transactionId;
	private String candidateForcepersonalId;
	private int NgoAmeRequeststatus;
	private String modifiedBy;
	private String modifiedRemarks;
	
	
	
	public int getId() {
		return id;
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getCandidateForcepersonalId() {
		return candidateForcepersonalId;
	}
	public void setCandidateForcepersonalId(String candidateForcepersonalId) {
		this.candidateForcepersonalId = candidateForcepersonalId;
	}
	public int getNgoAmeRequeststatus() {
		return NgoAmeRequeststatus;
	}
	public void setNgoAmeRequeststatus(int ngoAmeRequeststatus) {
		NgoAmeRequeststatus = ngoAmeRequeststatus;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedRemarks() {
		return modifiedRemarks;
	}
	public void setModifiedRemarks(String modifiedRemarks) {
		this.modifiedRemarks = modifiedRemarks;
	}

	
	
	
}
