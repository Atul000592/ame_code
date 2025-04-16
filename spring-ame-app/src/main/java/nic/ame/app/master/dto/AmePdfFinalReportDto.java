package nic.ame.app.master.dto;

import java.util.List;
import java.util.Optional;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.test.controller.ForcePersonalDto;

public class AmePdfFinalReportDto {

    private AmeMasterStatus ameMasterStatus;

    private AmeFinalReportDetails ameFinalReportDetails;

    private MedExamDtoRequest examDtoRequest;

    private AmeFinalReportBoardMemberDetails ameFinalReportBoardMemberDetails;

    private ForcePersonnel boardMemberDetails;

    private List<FinalCategoryRemarkTemp> finalCategoryRemarkList;

    private InvestigationFinalReportDto investigationFinalReportDto;
    private ForcePersonnelDto forcePersonalDetails;
    
    private MedicalBoard medicalBoardMemberDetails;
    private String finalCategoryAwarded;
    List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps;

    private String boardMemberUnitName;

    private String boardMemberForceName;

    private ForcePersonnel forcePersonal;

    private String medicalBoardMemberPlace;
    
    private Optional<BloodSugarF> bloodSugarF;
    
    private  Optional<BloodSugarRandom> bloodSugarRandom;
    
    private Optional<BloodSugarHbA1c> bloodSugarHbA1c;
    
    private Optional<BloodSugarPP> bloodSugarPP;
    

    private InvestigationDto investigationDto ;
    
    private Optional<ForcePersonnelDto> forcePersonelDtoOptionalBM;
    private Optional<ForcePersonnelDto> forcePersonelDtoOptionalPO;
    private Optional<ForcePersonnelDto> forcePersonelDtoOptionalAA;
    
    private BoardMemberDetailPdfDto boardMemberDetailPdfDto;
    
   
	private String remark;
    
    private int rCode;

    
    

    
    public int getrCode() {
		return rCode;
	}
	public void setrCode(int rCode) {
		this.rCode = rCode;
	}
	public String getBoardMemberUnitName() {
        return boardMemberUnitName;
    }
    public void setBoardMemberUnitName(String boardMemberUnitName) {
        this.boardMemberUnitName = boardMemberUnitName;
    }
    public String getBoardMemberForceName() {
        return boardMemberForceName;
    }
    public void setBoardMemberForceName(String boardMemberForceName) {
        this.boardMemberForceName = boardMemberForceName;
    }
    
    public ForcePersonnel getForcePersonal() {
		return forcePersonal;
	}
	public void setForcePersonal(ForcePersonnel forcePersonal) {
		this.forcePersonal = forcePersonal;
	}
	public void setBoardMemberDetails(ForcePersonnel boardMemberDetails) {
		this.boardMemberDetails = boardMemberDetails;
	}
	public String getMedicalBoardMemberPlace() {
        return medicalBoardMemberPlace;
    }
    public void setMedicalBoardMemberPlace(String medicalBoardMemberPlace) {
        this.medicalBoardMemberPlace = medicalBoardMemberPlace;
    }
    public List<FinalCategoryRemarkTemp> getFinalCategoryRemarkTemps() {
        return finalCategoryRemarkTemps;
    }
    public void setFinalCategoryRemarkTemps(List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps) {
        this.finalCategoryRemarkTemps = finalCategoryRemarkTemps;
    }
    public String getFinalCategoryAwarded() {
        return finalCategoryAwarded;
    }
    public void setFinalCategoryAwarded(String finalCategoryAwarded) {
        this.finalCategoryAwarded = finalCategoryAwarded;
    }
    public AmeMasterStatus getAmeMasterStatus() {
        return ameMasterStatus;
    }
    public void setAmeMasterStatus(AmeMasterStatus ameMasterStatus) {
        this.ameMasterStatus = ameMasterStatus;
    }
    public AmeFinalReportDetails getAmeFinalReportDetails() {
        return ameFinalReportDetails;
    }
    public void setAmeFinalReportDetails(AmeFinalReportDetails ameFinalReportDetails) {
        this.ameFinalReportDetails = ameFinalReportDetails;
    }
    public MedExamDtoRequest getExamDtoRequest() {
        return examDtoRequest;
    }
    public void setExamDtoRequest(MedExamDtoRequest examDtoRequest) {
        this.examDtoRequest = examDtoRequest;
    }
    public AmeFinalReportBoardMemberDetails getAmeFinalReportBoardMemberDetails() {
        return ameFinalReportBoardMemberDetails;
    }
    public void setAmeFinalReportBoardMemberDetails(AmeFinalReportBoardMemberDetails ameFinalReportBoardMemberDetails) {
        this.ameFinalReportBoardMemberDetails = ameFinalReportBoardMemberDetails;
    }
   
    public ForcePersonnel getBoardMemberDetails() {
		return boardMemberDetails;
	}
	public List<FinalCategoryRemarkTemp> getFinalCategoryRemarkList() {
        return finalCategoryRemarkList;
    }
    public void setFinalCategoryRemarkList(List<FinalCategoryRemarkTemp> finalCategoryRemarkList) {
        this.finalCategoryRemarkList = finalCategoryRemarkList;
    }
    public InvestigationFinalReportDto getInvestigationFinalReportDto() {
        return investigationFinalReportDto;
    }
    public void setInvestigationFinalReportDto(InvestigationFinalReportDto investigationFinalReportDto) {
        this.investigationFinalReportDto = investigationFinalReportDto;
    }
   
    public MedicalBoard getMedicalBoardMemberDetails() {
        return medicalBoardMemberDetails;
    }
    public void setMedicalBoardMemberDetails(MedicalBoard medicalBoardMemberDetails) {
        this.medicalBoardMemberDetails = medicalBoardMemberDetails;
    }
    public InvestigationDto getInvestigationDto() {
        return investigationDto;
    }
    public void setInvestigationDto(InvestigationDto investigationDto) {
        this.investigationDto = investigationDto;
    }
	public ForcePersonnelDto getForcePersonalDetails() {
		return forcePersonalDetails;
	}
	public void setForcePersonalDetails(ForcePersonnelDto forcePersonalDetails) {
		this.forcePersonalDetails = forcePersonalDetails;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Optional<BloodSugarF> getBloodSugarF() {
		return bloodSugarF;
	}
	public void setBloodSugarF(Optional<BloodSugarF> bloodSugarF) {
		this.bloodSugarF = bloodSugarF;
	}
	public Optional<BloodSugarRandom> getBloodSugarRandom() {
		return bloodSugarRandom;
	}
	public void setBloodSugarRandom(Optional<BloodSugarRandom> bloodSugarRandom) {
		this.bloodSugarRandom = bloodSugarRandom;
	}
	public Optional<BloodSugarHbA1c> getBloodSugarHbA1c() {
		return bloodSugarHbA1c;
	}
	public void setBloodSugarHbA1c(Optional<BloodSugarHbA1c> bloodSugarHbA1c) {
		this.bloodSugarHbA1c = bloodSugarHbA1c;
	}
	public Optional<BloodSugarPP> getBloodSugarPP() {
		return bloodSugarPP;
	}
	public void setBloodSugarPP(Optional<BloodSugarPP> bloodSugarPP) {
		this.bloodSugarPP = bloodSugarPP;
	}
	public BoardMemberDetailPdfDto getBoardMemberDetailPdfDto() {
		return boardMemberDetailPdfDto;
	}
	public void setBoardMemberDetailPdfDto(BoardMemberDetailPdfDto boardMemberDetailPdfDto) {
		this.boardMemberDetailPdfDto = boardMemberDetailPdfDto;
	}
	public Optional<ForcePersonnelDto> getForcePersonelDtoOptionalBM() {
		return forcePersonelDtoOptionalBM;
	}
	public void setForcePersonelDtoOptionalBM(Optional<ForcePersonnelDto> forcePersonelDtoOptionalBM) {
		this.forcePersonelDtoOptionalBM = forcePersonelDtoOptionalBM;
	}
	public Optional<ForcePersonnelDto> getForcePersonelDtoOptionalPO() {
		return forcePersonelDtoOptionalPO;
	}
	public void setForcePersonelDtoOptionalPO(Optional<ForcePersonnelDto> forcePersonelDtoOptionalPO) {
		this.forcePersonelDtoOptionalPO = forcePersonelDtoOptionalPO;
	}
	public Optional<ForcePersonnelDto> getForcePersonelDtoOptionalAA() {
		return forcePersonelDtoOptionalAA;
	}
	public void setForcePersonelDtoOptionalAA(Optional<ForcePersonnelDto> forcePersonelDtoOptionalAA) {
		this.forcePersonelDtoOptionalAA = forcePersonelDtoOptionalAA;
	}
	
	

    
}
