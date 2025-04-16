package nic.ame.app.master.model;

import java.math.BigInteger;
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
import nic.ame.app.admin.model.RefMedicalExamType;

@Table(name = "tt_alert_and_notification")
@Entity
public class AlertAndNotification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	-----Receiver's Details-----  //
	private String receiverForcePersonnelId;
	private String receiverEmail;
	private BigInteger receiverMobileNumber;
	private Integer isSuccessEmail;
	private Date isSuccessEmailOn;
	@Column(name="is_success_sms")
    private Integer isSuccessSMS;
	@Column(name="is_success_sms_on")
	private Date isSuccessSMSOn;
	
//	-----Sender's Details-----  //
	private String sendByForcePersonnelId;
	private String sendFrom;
	private Date sendOn;
	private String sendBy;
	
	
    private String lastModifiedBy;
	private Date lastModifiedOn;
	private String lastModifiedFrom;
	
	

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "exam_type")
	private RefMedicalExamType examType;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name ="header" )
	private ApplicationStateDescription applicationStateDescription;
	
	private String message;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReceiverForcePersonnelId() {
		return receiverForcePersonnelId;
	}
	public void setReceiverForcePersonnelId(String receiverForcePersonnelId) {
		this.receiverForcePersonnelId = receiverForcePersonnelId;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public BigInteger getReceiverMobileNumber() {
		return receiverMobileNumber;
	}
	public void setReceiverMobileNumber(BigInteger receiverMobileNumber) {
		this.receiverMobileNumber = receiverMobileNumber;
	}
	public Integer getIsSuccessEmail() {
		return isSuccessEmail;
	}
	public void setIsSuccessEmail(Integer isSuccessEmail) {
		this.isSuccessEmail = isSuccessEmail;
	}
	public Date getIsSuccessEmailOn() {
		return isSuccessEmailOn;
	}
	public void setIsSuccessEmailOn(Date isSuccessEmailOn) {
		this.isSuccessEmailOn = isSuccessEmailOn;
	}
	public Integer getIsSuccessSMS() {
		return isSuccessSMS;
	}
	public void setIsSuccessSMS(Integer isSuccessSMS) {
		this.isSuccessSMS = isSuccessSMS;
	}
	public Date getIsSuccessSMSOn() {
		return isSuccessSMSOn;
	}
	public void setIsSuccessSMSOn(Date isSuccessSMSOn) {
		this.isSuccessSMSOn = isSuccessSMSOn;
	}
	public String getSendByForcePersonnelId() {
		return sendByForcePersonnelId;
	}
	public void setSendByForcePersonnelId(String sendByForcePersonnelId) {
		this.sendByForcePersonnelId = sendByForcePersonnelId;
	}
	public String getSendFrom() {
		return sendFrom;
	}
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	public Date getSendOn() {
		return sendOn;
	}
	public void setSendOn(Date sendOn) {
		this.sendOn = sendOn;
	}
	public String getSendBy() {
		return sendBy;
	}
	public void setSendBy(String sendBy) {
		this.sendBy = sendBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	public void setLastModifiedFrom(String lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public RefMedicalExamType getExamType() {
		return examType;
	}
	public void setExamType(RefMedicalExamType examType) {
		this.examType = examType;
	}
	public ApplicationStateDescription getApplicationStateDescription() {
		return applicationStateDescription;
	}
	public void setApplicationStateDescription(ApplicationStateDescription applicationStateDescription) {
		this.applicationStateDescription = applicationStateDescription;
	}

	
	
	
	

}
