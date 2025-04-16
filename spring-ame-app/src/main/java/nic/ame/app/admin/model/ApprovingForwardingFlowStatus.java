package nic.ame.app.admin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


public class ApprovingForwardingFlowStatus {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String boardId;
	private String ameId;
	private String status;
	private String requestFrom;
    private String requestTo;
	private String remark;
	private String forcePersonalId;
	
	public int getId() {
		return id;
	}
	public String getBoardId() {
		return boardId;
	}
	public String getAmeId() {
		return ameId;
	}
	public String getStatus() {
		return status;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public String getRequestTo() {
		return requestTo;
	}
	public String getRemark() {
		return remark;
	}
	
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	
	




	public ApprovingForwardingFlowStatus(Builder builder ) {
		super();
		this.boardId = builder.boardId;
		this.ameId = builder.ameId;
		this.status = builder.status;
		this.requestFrom = builder.requestFrom;
		this.requestTo = builder.requestTo;
		this.remark = builder.remark;
		this.forcePersonalId=builder.forcePersonalId;
	}


	public static class Builder {
		
		private String boardId;
		private String ameId;
		private String status;
		private String requestFrom;
		private String requestTo;
		private String remark;
		private String forcePersonalId;
		
		public Builder setBoardId(String boardId) {
			this.boardId = boardId;
			return this;
		}
		public Builder setAmeId(String ameId) {
			this.ameId = ameId;
			return this;
		}
		public Builder setStatus(String status) {
			this.status = status;
			return this;
		}
		public Builder setRequestFrom(String requestFrom) {
			this.requestFrom = requestFrom;
			return this;
		  }
		public Builder setRequestTo(String requestTo) {
			this.requestTo = requestTo;
			return this;
		}
		public Builder setRemark(String remark) {
			this.remark = remark;
			return this;
		}
	
	
		public Builder setForcePersonalId(String forcePersonalId) {
			this.forcePersonalId = forcePersonalId;
			return this;
		}
		public ApprovingForwardingFlowStatus build() {
			
			return new ApprovingForwardingFlowStatus(this);
		}
		
	}
	
	
}
