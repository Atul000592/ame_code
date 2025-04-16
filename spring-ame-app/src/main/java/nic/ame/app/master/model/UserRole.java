package nic.ame.app.master.model;

import java.math.BigInteger;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table
@Entity
public class UserRole {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userRoleId;
	private String roleId;
	private String forcePersonalId;
	private long allocatedUnit;
    private String mappedBy;
	private Date mappedOn;
	private int status;
	private String mappedByName;
	private long mappedByUnit;
	private Integer mappedByForceNo;
	private Date modifiedOn;
	private String modifiedBy;
	private String ipAddress;
	private String mappedToForce;
	private BigInteger transactionalId;
	private Integer permissionToAssignRole;
	private Integer refAdminFlag;
	private String statusChangeReason;
	
	
	

	public Integer getPermissionToAssignRole() {
		return permissionToAssignRole;
	}
	public void setPermissionToAssignRole(Integer permissionToAssignRole) {
		this.permissionToAssignRole = permissionToAssignRole;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public long getAllocatedUnit() {
		return allocatedUnit;
	}
	public void setAllocatedUnit(long allocatedUnit) {
		this.allocatedUnit = allocatedUnit;
	}
	public String getMappedBy() {
		return mappedBy;
	}
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}
	public Date getMappedOn() {
		return mappedOn;
	}
	public void setMappedOn(Date mappedOn) {
		this.mappedOn = mappedOn;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMappedByName() {
		return mappedByName;
	}
	public void setMappedByName(String mappedByName) {
		this.mappedByName = mappedByName;
	}
	public long getMappedByUnit() {
		return mappedByUnit;
	}
	public void setMappedByUnit(long mappedByUnit) {
		this.mappedByUnit = mappedByUnit;
	}
	public Integer getMappedByForceNo() {
		return mappedByForceNo;
	}
	public void setMappedByForceNo(Integer mappedByForceNo) {
		this.mappedByForceNo = mappedByForceNo;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getMappedToForce() {
		return mappedToForce;
	}
	public void setMappedToForce(String mappedToForce) {
		this.mappedToForce = mappedToForce;
	}
	public BigInteger getTransactionalId() {
		return transactionalId;
	}
	public void setTransactionalId(BigInteger transactionalId) {
		this.transactionalId = transactionalId;
	}
	public Integer getRefAdminFlag() {
		return refAdminFlag;
	}
	public void setRefAdminFlag(Integer refAdminFlag) {
		this.refAdminFlag = refAdminFlag;
	}
	public String getStatusChangeReason() {
		return statusChangeReason;
	}
	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}
	
	
	
	
	
}
