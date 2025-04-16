package nic.ame.app.admin.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MedicalBoardMemberPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String forcePersonalId;
	private String boardId;
	private String roleName;
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
}
