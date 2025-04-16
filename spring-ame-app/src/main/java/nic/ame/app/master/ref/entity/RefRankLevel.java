package nic.ame.app.master.ref.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class RefRankLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column(length = 3)
	private String rankCode;
    private String description;
    private int level;
    private Integer forceNo;
    @Column(length = 2)
    private String status;
    private Date InActiveDate;
    @Column(length = 20)
    private String createdBy;
    private Date createdOn;
    @Column(length = 12)
    private String clientIp; 
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRankCode() {
		return rankCode;
	}
	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Integer getForceNo() {
		return forceNo;
	}
	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInActiveDate() {
		return InActiveDate;
	}
	public void setInActiveDate(Date inActiveDate) {
		InActiveDate = inActiveDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
    
}
