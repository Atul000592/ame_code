package nic.ame.app.admin.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ref_role")
public class RefRole {
	
	
	@Id
	@Column(length=2)
	private String roleId;
	private String roleName;
	private String roleCode;
	private Date createdOn;
	private String createdBy;
	private String clientIp;
	@Column(length = 1)
	private String status;
	private Date modifiedOn;
	private String modifiedBy;
	@Column(nullable = true)
	private int hierarchyInRole;
	@Column(nullable = true)
	private int refAdminFlag;
	
	
	
	
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
  
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getHierarchyInRole() {
		return hierarchyInRole;
	}
	public void setHierarchyInRole(int hierarchyInRole) {
		this.hierarchyInRole = hierarchyInRole;
	}
	public int getRefAdminFlag() {
		return refAdminFlag;
	}
	public void setRefAdminFlag(int refAdminFlag) {
		this.refAdminFlag = refAdminFlag;
	}
	 
	

}
