package nic.ame.app.superuser.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tt_user_manual")
@Entity
public class UserManualEntity {
		
	@Id
		private int roleId;
		private String roleName;
		private String roleCode;
		private String path;
		private String fileName;
		private String fileType;
		private int fileSize;
		
		private Date lastModifiedOn;
		private String lastModifiedBy;
		private String lastModifiedFrom;
		
		
		
		public int getRoleId() {
			return roleId;
		}
		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
		public String getRoleCode() {
			return roleCode;
		}
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
		
		
		
		
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		public int getFileSize() {
			return fileSize;
		}
		public void setFileSize(int fileSize) {
			this.fileSize = fileSize;
		}
		public Date getLastModifiedOn() {
			return lastModifiedOn;
		}
		public void setLastModifiedOn(Date lastModifiedOn) {
			this.lastModifiedOn = lastModifiedOn;
		}
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
		public String getLastModifiedFrom() {
			return lastModifiedFrom;
		}
		public void setLastModifiedFrom(String lastModifiedFrom) {
			this.lastModifiedFrom = lastModifiedFrom;
		}
		@Override
		public String toString() {
			return "UserRoleDto [roleId=" + roleId + ", roleName=" + roleName + ", roleCode=" + roleCode + "]";
		}

	}
