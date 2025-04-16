package nic.ame.app.admin.model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import nic.ame.app.master.model.Force;

@Table(name = "tm_rank")
@Entity
public class Rank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="force_no")
	private Integer forceNo;
    
	@Column(length = 30) 
    private String rankCode;
    
	@Column(length = 30)
    private String subRankCode;
    
	@Column(length = 10)
    private String gazettedNonGazettedFlag; 
    
	@Column(length = 100,name = "rank_full_name")
    private String rankFullName;

	
	private String lastModifiedOn;
	
	private String lastModifiedBy;
	
	private String lastModifiedfrom;
	
	private char isActive;
	
	private Date inActiveDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getForceNo() {
		return forceNo;
	}

	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getSubRankCode() {
		return subRankCode;
	}

	public void setSubRankCode(String subRankCode) {
		this.subRankCode = subRankCode;
	}

	public String getGazettedNonGazettedFlag() {
		return gazettedNonGazettedFlag;
	}

	public void setGazettedNonGazettedFlag(String gazettedNonGazettedFlag) {
		this.gazettedNonGazettedFlag = gazettedNonGazettedFlag;
	}

	public String getRankFullName() {
		return rankFullName;
	}

	public void setRankFullName(String rankFullName) {
		this.rankFullName = rankFullName;
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
