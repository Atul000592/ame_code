package nic.ame.app.master.dto;


import java.util.Date;
import java.util.List;

import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;

public class AmeFinalDetailDto {
	
	
	private String candidateForcePersonalId;
	private String Name;
	private String candidateIrlaNo;
	private String candidateRank;
	private String AmeYear;
	private String ameId;
	private String boardId;
	private String ameFinalCategoryAwarded;
	private int status;
	private String statusValue;
	private Date createdOn;
	private String ipClientAddress;
	private Date declarationDate;
	private List<MedicalBoardMemberDto> medicalBoardMemberDto;
	private AmeDeclarationIndividualDetails ameDeclarationIndividualDetails;
	private AmeDeclarationIndividualModel ameDeclarationIndividualModel;
	private String amePlace;
	private String date ;
	private MedExamDtoRequest examDtoRequest;
	private AmeMasterStatus ameMasterStatus;
	private ForcePersonnelDto forcePersonnelDto;
	private InvestigationFinalReportDto investigationFinalReportDto;
	private String remark;
	public String getCandidateForcePersonalId() {
		return candidateForcePersonalId;
	}
	public void setCandidateForcePersonalId(String candidateForcePersonalId) {
		this.candidateForcePersonalId = candidateForcePersonalId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCandidateIrlaNo() {
		return candidateIrlaNo;
	}
	public void setCandidateIrlaNo(String candidateIrlaNo) {
		this.candidateIrlaNo = candidateIrlaNo;
	}
	public String getCandidateRank() {
		return candidateRank;
	}
	public void setCandidateRank(String candidateRank) {
		this.candidateRank = candidateRank;
	}
	public String getAmeYear() {
		return AmeYear;
	}
	public void setAmeYear(String ameYear) {
		AmeYear = ameYear;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getAmeFinalCategoryAwarded() {
		return ameFinalCategoryAwarded;
	}
	public void setAmeFinalCategoryAwarded(String ameFinalCategoryAwarded) {
		this.ameFinalCategoryAwarded = ameFinalCategoryAwarded;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getIpClientAddress() {
		return ipClientAddress;
	}
	public void setIpClientAddress(String ipClientAddress) {
		this.ipClientAddress = ipClientAddress;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public List<MedicalBoardMemberDto> getMedicalBoardMemberDto() {
		return medicalBoardMemberDto;
	}
	public void setMedicalBoardMemberDto(List<MedicalBoardMemberDto> medicalBoardMemberDto) {
		this.medicalBoardMemberDto = medicalBoardMemberDto;
	}
	public AmeDeclarationIndividualDetails getAmeDeclarationIndividualDetails() {
		return ameDeclarationIndividualDetails;
	}
	public void setAmeDeclarationIndividualDetails(AmeDeclarationIndividualDetails ameDeclarationIndividualDetails) {
		this.ameDeclarationIndividualDetails = ameDeclarationIndividualDetails;
	}
	public AmeDeclarationIndividualModel getAmeDeclarationIndividualModel() {
		return ameDeclarationIndividualModel;
	}
	public void setAmeDeclarationIndividualModel(AmeDeclarationIndividualModel ameDeclarationIndividualModel) {
		this.ameDeclarationIndividualModel = ameDeclarationIndividualModel;
	}
	public String getAmePlace() {
		return amePlace;
	}
	public void setAmePlace(String amePlace) {
		this.amePlace = amePlace;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public MedExamDtoRequest getExamDtoRequest() {
		return examDtoRequest;
	}
	public void setExamDtoRequest(MedExamDtoRequest examDtoRequest) {
		this.examDtoRequest = examDtoRequest;
	}
	public AmeMasterStatus getAmeMasterStatus() {
		return ameMasterStatus;
	}
	public void setAmeMasterStatus(AmeMasterStatus ameMasterStatus) {
		this.ameMasterStatus = ameMasterStatus;
	}
	public ForcePersonnelDto getForcePersonnelDto() {
		return forcePersonnelDto;
	}
	public void setForcePersonnelDto(ForcePersonnelDto forcePersonnelDto) {
		this.forcePersonnelDto = forcePersonnelDto;
	}
	public InvestigationFinalReportDto getInvestigationFinalReportDto() {
		return investigationFinalReportDto;
	}
	public void setInvestigationFinalReportDto(InvestigationFinalReportDto investigationFinalReportDto) {
		this.investigationFinalReportDto = investigationFinalReportDto;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
	

}
