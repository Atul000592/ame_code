package nic.ame.app.master.dto;

public class RowData {
    private String name;
    private String code;
    private String ameId;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmeId() {
        return ameId;
    }

    public void setAmeId(String ameId) {
        this.ameId = ameId;
    }

	@Override
	public String toString() {
		return "RowData [name=" + name + ", code=" + code + ", ameId=" + ameId + "]";
	}
    
    
    
}
