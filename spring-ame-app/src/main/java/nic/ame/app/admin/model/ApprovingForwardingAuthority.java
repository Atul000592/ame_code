package nic.ame.app.admin.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



public class ApprovingForwardingAuthority {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String boardId;
	private String forcePersonalId;
	private int roleId;
	private int rankIdTo;
	private int rankIdFrom;
	private int status;
	private String createdBy;
	private Date createdOn;
	private String modifiedBy;
	private Date modifiedOn;
	private String IpAddressCreatedBy;
	private String IpAddressModifiedBy;
	private String remark;
	private int unit;
	
	
	
	
	public int getId() {
		return id;
	}

	public String getBoardId() {
		return boardId;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public int getRoleId() {
		return roleId;
	}

	public int getRankIdTo() {
		return rankIdTo;
	}

	public int getRankIdFrom() {
		return rankIdFrom;
	}

	public int getStatus() {
		return status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public String getIpAddressCreatedBy() {
		return IpAddressCreatedBy;
	}

	public String getIpAddressModifiedBy() {
		return IpAddressModifiedBy;
	}

	public String getRemark() {
		return remark;
	}
	


	
	
	
	
public ApprovingForwardingAuthority(Builder builder) {
		super();
		
		this.boardId = builder.boardId;
		this.forcePersonalId = builder.forcePersonalId;
		this.roleId =builder. roleId;
		this.rankIdTo = builder.rankIdTo;
		this.rankIdFrom = builder.rankIdFrom;
		this.status = builder.status;
		this.createdBy = builder.createdBy;
		this.createdOn = builder.createdOn;
		this.modifiedBy = builder.modifiedBy;
		this.modifiedOn = builder.modifiedOn;
		this.IpAddressCreatedBy = builder.ipAddressCreatedBy;
		this.IpAddressModifiedBy = builder.ipAddressModifiedBy;
		this.remark = builder.remark;
		this.unit=builder.unit;
	}




public static class Builder{
		
		
		public String ipAddressModifiedBy;
		public String ipAddressCreatedBy;
		private String boardId;
		private String forcePersonalId;
		private int roleId;
		private int rankIdTo;
		private int rankIdFrom;
		private int status;
		private String createdBy;
		private Date createdOn;
		private String modifiedBy;
		private Date modifiedOn;
		private String IpAddressCreatedBy;
		private String IpAddressModifiedBy;
		private String remark;
		private int unit;
		
		
		
		public Builder setBoardId(String boardId) {
			this.boardId = boardId;
			return this;
		}
		public Builder setForcePersonalId(String forcePersonalId) {
			this.forcePersonalId = forcePersonalId;
			return this;
		}
		public Builder setRoleId(int roleId) {
			this.roleId = roleId;
			return this;
		}
		public Builder setRankIdTo(int rankIdTo) {
			this.rankIdTo = rankIdTo;
			return this;
		}
		public Builder setRankIdFrom(int rankIdFrom) {
			this.rankIdFrom = rankIdFrom;
			return this;
		}
		public Builder setStatus(int status) {
			this.status = status;
			return this;
		}
		public Builder setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
			return this;
		}
		public Builder setCreatedOn(Date createdOn) {
			this.createdOn = createdOn;
			return this;
		}
		public Builder setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
			return this;
		}
		public Builder setModifiedOn(Date modifiedOn) {
			this.modifiedOn = modifiedOn;
			return this;
		}
		public Builder setIpAddressCreatedBy(String ipAddressCreatedBy) {
			IpAddressCreatedBy = ipAddressCreatedBy;
			return this;
		}
		public Builder setIpAddressModifiedBy(String ipAddressModifiedBy) {
			IpAddressModifiedBy = ipAddressModifiedBy;
			return this;
		}
		public Builder setRemark(String remark) {
			this.remark = remark;
			return this;
		}
		public Builder setUnit(int unit) {
			this.unit = unit;
			return this;
		}
		
		public ApprovingForwardingAuthority build() {
			return new ApprovingForwardingAuthority(this);
		}
		
		
	}
	
	

}
