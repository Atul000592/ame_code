package nic.ame.app.admin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class RefRoleMedical {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String roleName;
	private int allowedId;
	private String fullName;
	@Column(nullable = true)
	private int reserverForId;
	private String description;
	@Column(nullable = true)
	private int reserveFlag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getAllowedId() {
		return allowedId;
	}
	public void setAllowedId(int allowedId) {
		this.allowedId = allowedId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getReserverForId() {
		return reserverForId;
	}
	public String getDescription() {
		return description;
	}
	public void setReserverForId(int reserverForId) {
		this.reserverForId = reserverForId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReserveFlag() {
		return reserveFlag;
	}
	public void setReserveFlag(int reserveFlag) {
		this.reserveFlag = reserveFlag;
	}
	@Override
	public String toString() {
		return "RefRoleMedical [id=" + id + ", roleName=" + roleName + ", allowedId=" + allowedId + ", fullName="
				+ fullName + ", reserverForId=" + reserverForId + ", description=" + description + ", reserveFlag="
				+ reserveFlag + "]";
	}

	

}
