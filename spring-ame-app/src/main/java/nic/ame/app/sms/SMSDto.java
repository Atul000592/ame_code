package nic.ame.app.sms;

import java.net.URLEncoder;

public class SMSDto {

	
	String encodedUserName;
	String encodedPassword ;
	String encodedSender  ;
	String encodedDltEntityId ;
	String encodedDltTemplateId;
	String encodedSMSMessage ;
	String mobileNumber;
	public String getEncodedUserName() {
		return encodedUserName;
	}
	public void setEncodedUserName(String encodedUserName) {
		this.encodedUserName = encodedUserName;
	}
	public String getEncodedPassword() {
		return encodedPassword;
	}
	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}
	public String getEncodedSender() {
		return encodedSender;
	}
	public void setEncodedSender(String encodedSender) {
		this.encodedSender = encodedSender;
	}
	public String getEncodedDltEntityId() {
		return encodedDltEntityId;
	}
	public void setEncodedDltEntityId(String encodedDltEntityId) {
		this.encodedDltEntityId = encodedDltEntityId;
	}
	public String getEncodedDltTemplateId() {
		return encodedDltTemplateId;
	}
	public void setEncodedDltTemplateId(String encodedDltTemplateId) {
		this.encodedDltTemplateId = encodedDltTemplateId;
	}
	public String getEncodedSMSMessage() {
		return encodedSMSMessage;
	}
	public void setEncodedSMSMessage(String encodedSMSMessage) {
		this.encodedSMSMessage = encodedSMSMessage;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	

}
