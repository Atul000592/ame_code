package nic.ame.app.dealinghand.dto;

public class RequestDealingHandDto {

	
	private String roleName;
	private Integer forceNo;
	private String unit;
	private String forcePersonalId;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getForceNo() {
		return forceNo;
	}
	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	
	
}
