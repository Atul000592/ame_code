package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.admin.model.Rank;

@Table(name = "tm_personnel_others")
@Entity
public class PersonnelOthers {
	
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private int id;
	   private String name;
	
	   @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	   @JoinColumn(name = "designation_id")
	   private Rank rankMaster;
	 
	 
	   private long forceId;
	   
	   private Date createdOn;
	   
	   private String createdBy;
	   
	   private String IpAddress;
	   
	   private String modifiedOn;
	   
	   private int status;
	   
	   
	   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Rank getRankMaster() {
		return rankMaster;
	}
	public void setRankMaster(Rank rankMaster) {
		this.rankMaster = rankMaster;
	}
	public long getForceId() {
		return forceId;
	}
	public void setForceId(long forceId) {
		this.forceId = forceId;
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
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	 
	 
	

}
