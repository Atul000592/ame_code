package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_ngo_ame_request")
@Entity
public class NGOAMERequest {
	@Id
	private String transactionId;
	private Date validFromDate;
	private Date validToDate;
	private String forwardedBy;
	private String forwardedRemarks;
	private Date forwardedOn;
	private String forwardedTo;
	private String acceptorRemarks;
	private Date acceptedOn;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getValidFromDate() {
		return validFromDate;
	}
	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}
	public Date getValidToDate() {
		return validToDate;
	}
	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
	}
	public String getForwardedBy() {
		return forwardedBy;
	}
	public void setForwardedBy(String forwardedBy) {
		this.forwardedBy = forwardedBy;
	}
	public String getForwardedRemarks() {
		return forwardedRemarks;
	}
	public void setForwardedRemarks(String forwardedRemarks) {
		this.forwardedRemarks = forwardedRemarks;
	}
	public Date getForwardedOn() {
		return forwardedOn;
	}
	public void setForwardedOn(Date forwardedOn) {
		this.forwardedOn = forwardedOn;
	}
	public String getForwardedTo() {
		return forwardedTo;
	}
	public void setForwardedTo(String forwardedTo) {
		this.forwardedTo = forwardedTo;
	}
	public String getAcceptorRemarks() {
		return acceptorRemarks;
	}
	public void setAcceptorRemarks(String acceptorRemarks) {
		this.acceptorRemarks = acceptorRemarks;
	}
	public Date getAcceptedOn() {
		return acceptedOn;
	}
	public void setAcceptedOn(Date acceptedOn) {
		this.acceptedOn = acceptedOn;
	}
	
	
	
}
