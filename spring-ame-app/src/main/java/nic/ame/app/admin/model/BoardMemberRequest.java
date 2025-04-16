package nic.ame.app.admin.model;

public class BoardMemberRequest {
    private String forcePersonalId;
    private String boardId;
  //  private UserRole userRole; // Assuming UserRole is a class you have
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

    // Getters and setters
}
