package nic.ame.app.admin.dto;

import java.util.List;

public class MedicalBoardAndMemberDetailsDtos {
	
	
	private MedicalBoardDetailDto medicalBoardDetailDto;
	private List<MedicalBoardMemberDto> medicalBoardMemberDtoList;
	private String createdBy;
	
	
	public MedicalBoardDetailDto getMedicalBoardDetailDto() {
		return medicalBoardDetailDto;
	}
	public void setMedicalBoardDetailDto(MedicalBoardDetailDto medicalBoardDetailDto) {
		this.medicalBoardDetailDto = medicalBoardDetailDto;
	}
	public List<MedicalBoardMemberDto> getMedicalBoardMemberDtoList() {
		return medicalBoardMemberDtoList;
	}
	public void setMedicalBoardMemberDtoList(List<MedicalBoardMemberDto> medicalBoardMemberDtoList) {
		this.medicalBoardMemberDtoList = medicalBoardMemberDtoList;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
}
