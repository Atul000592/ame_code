package nic.ame.app.admin.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
@Entity
@Table
public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name; 
	private String department;
	private String designation; 
	private String irlaNo;
	private int iSD;
	private String phNO;
	private int stD;
	private String mobileNo;
	private String email;
	private String contactType;
	@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
	private Date createdOn;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date ModifiedOn;
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date ModifiedBy;	
	private Date inactiveDate;
	private int statusCode;



	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public int getiSD() {
		return iSD;
	}
	public void setiSD(int iSD) {
		this.iSD = iSD;
	}
	public String getPhNO() {
		return phNO;
	}
	public void setPhNO(String phNO) {
		this.phNO = phNO;
	}
	public int getStD() {
		return stD;
	}
	public void setStD(int stD) {
		this.stD = stD;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Date createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedOn() {
		return ModifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		ModifiedOn = modifiedOn;
	}
	public Date getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(Date modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getInactiveDate() {
		return inactiveDate;
	}
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}
