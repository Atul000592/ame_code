package nic.ame.app.admin.model;
import java.util.Date;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name="tm_unit")

public class UnitModel {
	@Id
	private String unitId;
	
	private int forceNo;
	
	private String unitName;
	
	private String unitFullName;
	

	private String lastModifiedOn;
	
	private String lastModifiedBy;
	
	private String lastModifiedfrom;
	
	private char isActive;
	
	private Date inActiveDate;
	
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public int getForceNo() {
		return forceNo;
	}
	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitFullName() {
		return unitFullName;
	}
	public void setUnitFullName(String unitFullName) {
		this.unitFullName = unitFullName;
	}
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedfrom() {
		return lastModifiedfrom;
	}
	public void setLastModifiedfrom(String lastModifiedfrom) {
		this.lastModifiedfrom = lastModifiedfrom;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public Date getInActiveDate() {
		return inActiveDate;
	}
	public void setInActiveDate(Date inActiveDate) {
		this.inActiveDate = inActiveDate;
	}
	
	
	
	
	
	
	
	

}
