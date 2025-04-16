package nic.ame.app.master.model.go;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table
@Entity
public class AmeFinalReportBoardMemberDetailsGo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String boardId; 
	private String boardMemberForcePersonalId;
	private String boardMemberIrlaNo;
    private String remark;
    private String roleName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardMemberForcePersonalId() {
		return boardMemberForcePersonalId;
	}
	public void setBoardMemberForcePersonalId(String boardMemberForcePersonalId) {
		this.boardMemberForcePersonalId = boardMemberForcePersonalId;
	}
	public String getBoardMemberIrlaNo() {
		return boardMemberIrlaNo;
	}
	public void setBoardMemberIrlaNo(String boardMemberIrlaNo) {
		this.boardMemberIrlaNo = boardMemberIrlaNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
    
     
    
}
