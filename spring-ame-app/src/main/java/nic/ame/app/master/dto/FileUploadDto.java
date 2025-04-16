package nic.ame.app.master.dto;

import java.util.Date;

public class FileUploadDto {

	private String name_IrlaNo;
	private int department;
	private String designation;
	private String orderNo;
	private Date orderDate;
	private String permissionToAssignRole;
	private String fileName;
	private String fileType;
	private String fileSize;
	private String filePath;
	private String remark;
	private byte[] fileContent;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] bs) {
		this.fileContent = bs;
	}
	public String getName_IrlaNo() {
		return name_IrlaNo;
	}
	public void setName_IrlaNo(String name_IrlaNo) {
		this.name_IrlaNo = name_IrlaNo;
	}
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPermissionToAssignRole() {
		return permissionToAssignRole;
	}
	public void setPermissionToAssignRole(String permissionToAssignRole) {
		this.permissionToAssignRole = permissionToAssignRole;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
}
