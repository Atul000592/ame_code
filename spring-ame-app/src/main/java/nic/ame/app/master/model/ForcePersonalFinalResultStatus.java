package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class ForcePersonalFinalResultStatus {

	
	
	@Id
	private String forcePersonalId;
	private String irlaNo;
	private int lastAmePlace;
	private int forceNo;
	private String lastAmeShape;
	private Date lastAmeDate;
	private Date createdOn;
	private String createdBy;
	private String IpAddress;
	
	
	
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
	public int getLastAmePlace() {
		return lastAmePlace;
	}
	public void setLastAmePlace(int lastAmePlace) {
		this.lastAmePlace = lastAmePlace;
	}
	public int getForceNo() {
		return forceNo;
	}
	public void setForceNo(int forceNo) {
		this.forceNo = forceNo;
	}
	public String getLastAmeShape() {
		return lastAmeShape;
	}
	public void setLastAmeShape(String lastAmeShape) {
		this.lastAmeShape = lastAmeShape;
	}
	public Date getLastAmeDate() {
		return lastAmeDate;
	}
	public void setLastAmeDate(Date lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}
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
	public String getIpAddress() {
		return IpAddress;
	}
	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	
	
	
}
