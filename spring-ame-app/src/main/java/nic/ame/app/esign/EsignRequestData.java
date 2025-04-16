package nic.ame.app.esign;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "tt_esign_request_data")
public class EsignRequestData {

	@Id
	private String txn;
	@Lob
	private String  xml;
	private String  esignUrl;
	private String responseUrl;
	private String ameId;
	private String signeeForcePersonnelId;
	private String filePath;
	private String candidateIrlaNuber;
	private String signDocumentType;
	private String createdFrom;
	private Date createdON;
	private String sessionId;
	
	
	
	public String getTxn() {
		return txn;
	}
	public void setTxn(String txn) {
		this.txn = txn;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getEsignUrl() {
		return esignUrl;
	}
	public void setEsignUrl(String esignUrl) {
		this.esignUrl = esignUrl;
	}
	public String getResponseUrl() {
		return responseUrl;
	}
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getSigneeForcePersonnelId() {
		return signeeForcePersonnelId;
	}
	public void setSigneeForcePersonnelId(String signeeForcePersonnelId) {
		this.signeeForcePersonnelId = signeeForcePersonnelId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCandidateIrlaNuber() {
		return candidateIrlaNuber;
	}
	public void setCandidateIrlaNuber(String candidateIrlaNuber) {
		this.candidateIrlaNuber = candidateIrlaNuber;
	}
	public String getCreatedFrom() {
		return createdFrom;
	}
	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}
	public Date getCreatedON() {
		return createdON;
	}
	public void setCreatedON(Date createdON) {
		this.createdON = createdON;
	}
	public String getSignDocumentType() {
		return signDocumentType;
	}
	public void setSignDocumentType(String signDocumentType) {
		this.signDocumentType = signDocumentType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
	
	
	
	
	
}
