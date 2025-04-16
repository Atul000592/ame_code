package nic.ame.app.admin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ContactUsDto {
	private String contactUsId;
	private String name; 
	private String designation;
	private String force_no;
	private String irlaNo;
	private String typeOfContact;
	
	@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
	private Date createdOn;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date ModifiedDate;
		
	private String createdByName;
	private String createdByDesignation;
	private String createdByForce_no;
	private String createdByirlaNo;
	private String modifiedByName;
	private String modifiedByDesignation;
	private String modifiedByForce_no;
	private String modifiedByirlaNo;
	private int statusCode;
	public String getContactUsId() {
		return contactUsId;
	}
	public void setContactUsId(String contactUsId) {
		this.contactUsId = contactUsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getForce_no() {
		return force_no;
	}
	public void setForce_no(String force_no) {
		this.force_no = force_no;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public String getTypeOfContact() {
		return typeOfContact;
	}
	public void setTypeOfContact(String typeOfContact) {
		this.typeOfContact = typeOfContact;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public String getCreatedByDesignation() {
		return createdByDesignation;
	}
	public void setCreatedByDesignation(String createdByDesignation) {
		this.createdByDesignation = createdByDesignation;
	}
	public String getCreatedByForce_no() {
		return createdByForce_no;
	}
	public void setCreatedByForce_no(String createdByForce_no) {
		this.createdByForce_no = createdByForce_no;
	}
	public String getCreatedByirlaNo() {
		return createdByirlaNo;
	}
	public void setCreatedByirlaNo(String createdByirlaNo) {
		this.createdByirlaNo = createdByirlaNo;
	}
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	public String getModifiedByDesignation() {
		return modifiedByDesignation;
	}
	public void setModifiedByDesignation(String modifiedByDesignation) {
		this.modifiedByDesignation = modifiedByDesignation;
	}
	public String getModifiedByForce_no() {
		return modifiedByForce_no;
	}
	public void setModifiedByForce_no(String modifiedByForce_no) {
		this.modifiedByForce_no = modifiedByForce_no;
	}
	public String getModifiedByirlaNo() {
		return modifiedByirlaNo;
	}
	public void setModifiedByirlaNo(String modifiedByirlaNo) {
		this.modifiedByirlaNo = modifiedByirlaNo;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	
	
}
