package nic.ame.app.admin.dto;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UserRoleMapDto {

	
	
	private String forcePersonalId;
	private String irlaNo;
	private String name;
	private String forceName;
	private int roleName ;
	private String unitName;
	private int userRoleId;
	private String roleId;
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
	private MultipartFile fileName;
	private String privilege;
	private int refAdminFlag;
	
	public int getRefAdminFlag() {
		return refAdminFlag;
	}
	public void setRefAdminFlag(int refAdminFlag) {
		this.refAdminFlag = refAdminFlag;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public String getIrlaNo() {
		return irlaNo;
	}
	public void setIrlaNo(String irlaNo) {
		this.irlaNo = irlaNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}
	public int getRoleName() {
		return roleName;
	}
	public void setRoleName(int roleName) {
		this.roleName = roleName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public MultipartFile getFileName() {
		return fileName;
	}
	public void setFileName(MultipartFile fileName) {
		this.fileName = fileName;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
	
	

	
}
