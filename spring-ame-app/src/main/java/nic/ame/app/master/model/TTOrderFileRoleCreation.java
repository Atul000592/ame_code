package nic.ame.app.master.model;

import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_order_file_role_creation")
@Entity
public class TTOrderFileRoleCreation {

	@Id
	private BigInteger transactionalId;
	
	
	private String orderNumber;
	
	private String fileName;
	private String filePath;
	private String fileType;
	private String fileSize;
	@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
	private Date uploadOn;
	private String uploadBy;
	private String ipAddress;
	private String orderBy;
	private int department;
	private String rank;
	@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
	private Date orderDate;
	private String remark;
	
	
	
	
	private Integer permissionToAssignRole;
	
	public BigInteger getTransactionalId() {
		return transactionalId;
	}
	public void setTransactionalId(BigInteger transactionalId) {
		this.transactionalId = transactionalId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public Date getUploadOn() {
		return uploadOn;
	}
	public void setUploadOn(Date uploadOn) {
		this.uploadOn = uploadOn;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPermissionToAssignRole() {
		return permissionToAssignRole;
	}
	public void setPermissionToAssignRole(Integer permissionToAssignRole) {
		this.permissionToAssignRole = permissionToAssignRole;
	}

	
	
	
	
}
