package nic.ame.app.master.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_tbl")
public class Login {

	
	@Id
	private String userName;

	private String password;

	@Column(name = "first_login_flag")
	private String firstLoginFlag;

	@Column(name = "force_personal_id")
	private String forcePersonalId;

	private String password1;

	private String password2;

	private Date modifiedOn;

	// user status active/inactive/dormant
	@Column(name = "user_status")
	private int userStatus;

	private String statusReason;

	// admin forcepersonalId
	private String inActiveBy;

	private String inActiveByIp;

	private Date inActiveOn;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "userName")
	private List<UserRole> userRoles;
	@Column(name="failed_login_attempts",columnDefinition = "integer default '0'")
	private int failedLoginAttempts=0;
    
	@Column(nullable = true)
	private Date lockReleaseTime;



	//temporary status 
	@Column(name = "locked")
	  private int locked;
	 



	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public String getInActiveBy() {
		return inActiveBy;
	}

	public void setInActiveBy(String inActiveBy) {
		this.inActiveBy = inActiveBy;
	}

	public String getInActiveByIp() {
		return inActiveByIp;
	}

	public void setInActiveByIp(String inActiveByIp) {
		this.inActiveByIp = inActiveByIp;
	}

	public Date getInActiveOn() {
		return inActiveOn;
	}

	public void setInActiveOn(Date inActiveOn) {
		this.inActiveOn = inActiveOn;
	}




	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstLoginFlag() {
		return firstLoginFlag;
	}

	public void setFirstLoginFlag(String firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public Date getLockReleaseTime() {
		return lockReleaseTime;
	}

	public void setLockReleaseTime(Date lockReleaseTime) {
		this.lockReleaseTime = lockReleaseTime;
	}

	
	
	
	

}
