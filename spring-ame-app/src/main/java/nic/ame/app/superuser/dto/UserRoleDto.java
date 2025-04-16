package nic.ame.app.superuser.dto;

public class UserRoleDto {
	
	private int roleId;
	private String roleName;
	private String roleCode;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	@Override
	public String toString() {
		return "UserRoleDto [roleId=" + roleId + ", roleName=" + roleName + ", roleCode=" + roleCode + "]";
	}
	
	
	
	
	

}
