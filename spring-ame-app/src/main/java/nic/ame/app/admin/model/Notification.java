package nic.ame.app.admin.model;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
@Entity
@Table(name="tt_notification")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 @Column(columnDefinition = "TEXT")
	private String title; 
	
	 @Column(columnDefinition = "TEXT")
	private String description;
	
	private String orderBy;
	
	private String orderNo;

	private String orderDesignation; 
	
	// auto generated
	private String orderId;
	
	private int ministryDepartment;
	
	
	
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
	
	private int entryByForce_no;
	
	private String entryByIrlaNo;
	
	private String entryIpAddress;
	
	private String modifiedByName;
	
	private String modifiedByDesignation;
	
	private int modifiedByForce_no;
	
	private String modifiedIrlaNo;
	
	private String modifiedIpAddress;
	
	private int statusCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getOrderDesignation() {
		return orderDesignation;
	}

	public void setOrderDesignation(String orderDesignation) {
		this.orderDesignation = orderDesignation;
	}

	public int getMinistryDepartment() {
		return ministryDepartment;
	}

	public void setMinistryDepartment(int ministryDepartment) {
		this.ministryDepartment = ministryDepartment;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public int getEntryByForce_no() {
		return entryByForce_no;
	}

	public void setEntryByForce_no(int entryByForce_no) {
		this.entryByForce_no = entryByForce_no;
	}

	public String getEntryByIrlaNo() {
		return entryByIrlaNo;
	}

	public void setEntryByIrlaNo(String entryByIrlaNo) {
		this.entryByIrlaNo = entryByIrlaNo;
	}

	public String getEntryIpAddress() {
		return entryIpAddress;
	}

	public void setEntryIpAddress(String entryIpAddress) {
		this.entryIpAddress = entryIpAddress;
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

	public int getModifiedByForce_no() {
		return modifiedByForce_no;
	}

	public void setModifiedByForce_no(int modifiedByForce_no) {
		this.modifiedByForce_no = modifiedByForce_no;
	}

	public String getModifiedIrlaNo() {
		return modifiedIrlaNo;
	}

	public void setModifiedIrlaNo(String modifiedIrlaNo) {
		this.modifiedIrlaNo = modifiedIrlaNo;
	}

	public String getModifiedIpAddress() {
		return modifiedIpAddress;
	}

	public void setModifiedIpAddress(String modifiedIpAddress) {
		this.modifiedIpAddress = modifiedIpAddress;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	@Override
	public String toString() {
		return "Notification [title=" + title + ", description=" + description + ", orderBy=" + orderBy
				+ ", orderDesignation=" + orderDesignation + ", ministryDepartment=" + ministryDepartment
				+ ", orderDate=" + orderDate + ", visibleFromDate=" + visibleFromDate + ", visibleToDate="
				+ visibleToDate + "]";
	}

	

	

	


	
	
	
}
