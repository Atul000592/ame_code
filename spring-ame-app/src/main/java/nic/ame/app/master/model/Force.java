package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tm_force")
public class Force {
	
	@Id
    private int forceNo;
	
	private String forceName;
	
	private String forceCodeName;
	
	private String lastModifiedOn;
	
	private String lastModifiedBy;
	
	private String lastModifiedfrom;
	
	private char isActive;
	
	private Date inActiveDate;
	
//-----------------------------------------------------Getters and Setters------------------------------------------------------------//	
	
	public int getForceNo() {
		return forceNo;
	}
	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}
	public String getForceName() {
		return forceName;
	}
	public void setForceName(String forceName) {
		this.forceName = forceName;
	}
	public String getForceCodeName() {
		return forceCodeName;
	}
	public void setForceCodeName(String forceCodeName) {
		this.forceCodeName = forceCodeName;
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
