package nic.ame.app.ama.dto;

public class MedicalRequestDto {
private String ameId;
	
	private String  forcepersonalId;

	public String getForcepersonalId() {
		return forcepersonalId;
	}

	public void setForcepersonalId(String forcepersonalId) {
		this.forcepersonalId = forcepersonalId;
	}

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
}
