package nic.ame.app.admin.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class NotificationDto {
	private String title;
	
	private String description;
	
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String orderBy;
	
	private String orderNo;
	
	private String orderDesignation;
	
	private String ministryORDepartment;
	
	private String orderId;// auto generated
	
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date orderDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date visibleFromDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date visibleToDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd" ,shape = Shape.STRING)
	private Date entryDate;
	
	private String entryByName;
	
	private String entryByDesignation;
	
	private String entryByForce_no;
	
	private String entryByirlaNo;
	
	private String modifiedByName;
	
	private String modifiedByDesignation;
	
	private String modifiedByForce_no;
	
	private String modifiedirlaNo;
	
	private int statusCode;
	
	private String path;

	private String forceName;
	
	public String getForceName() {
		return forceName;
	}

	public void setForceName(String forceName) {
		this.forceName = forceName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDesignation() {
		return orderDesignation;
	}

	public void setOrderDesignation(String orderDesignation) {
		this.orderDesignation = orderDesignation;
	}

	public String getMinistryORDepartment() {
		return ministryORDepartment;
	}

	public void setMinistryORDepartment(String ministryORDepartment) {
		this.ministryORDepartment = ministryORDepartment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getVisibleFromDate() {
		return visibleFromDate;
	}

	public void setVisibleFromDate(Date visibleFromDate) {
		this.visibleFromDate = visibleFromDate;
	}

	public Date getVisibleToDate() {
		return visibleToDate;
	}

	public void setVisibleToDate(Date visibleToDate) {
		this.visibleToDate = visibleToDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryByName() {
		return entryByName;
	}

	public void setEntryByName(String entryByName) {
		this.entryByName = entryByName;
	}

	public String getEntryByDesignation() {
		return entryByDesignation;
	}

	public void setEntryByDesignation(String entryByDesignation) {
		this.entryByDesignation = entryByDesignation;
	}

	public String getEntryByForce_no() {
		return entryByForce_no;
	}

	public void setEntryByForce_no(String entryByForce_no) {
		this.entryByForce_no = entryByForce_no;
	}

	public String getEntryByirlaNo() {
		return entryByirlaNo;
	}

	public void setEntryByirlaNo(String entryByirlaNo) {
		this.entryByirlaNo = entryByirlaNo;
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

	public String getModifiedirlaNo() {
		return modifiedirlaNo;
	}

	public void setModifiedirlaNo(String modifiedirlaNo) {
		this.modifiedirlaNo = modifiedirlaNo;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

}
