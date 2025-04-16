package nic.ame.app.master.medical.service;

import java.util.List;
import java.util.Map;

import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.InvestigationReportDto;
import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.InvestigationReport;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.model.ViralMakers;
import nic.ame.app.master.ref.entity.RefInvestigationTestMaster;
import nic.ame.app.master.repository.KidneyFunctionTestKFTRepository;
import nic.ame.app.master.medical.model.OtherTest;

public interface InvestigationMasterService {
	
	
	List<RefInvestigationTestMaster> refInvestigationTestMastersListByTestCode(String testCode);

	List<CheckUpList> checkUpListsByAmeId(String AmeId,int status);
	
	boolean saveFileOfInvestigationReportLiver(FileUploadDto fileUploadDto,String ameId);

	boolean saveInvestigationReport(List<InvestigationReport> investigationReports, FileUploadDto fileUploadDto,String ameId,String testCode);

	List<Map<String, String>> convertJsonToList(String jsonData);
	List<CheckUpList>  findCheckUpListByAmeIdUploadFlagTrue(String ameId);
	Map<String, Map<String,String>>  findInvestigationReportByAmeId(String ameId);
	
	BloodSugar saveBloodSugar(BloodSugar bloodSugar);
	
	LiverFunctionTest saveLiverFunctionTest(LiverFunctionTest liverFunctionTest);
	
	CompleteBloodCount saveCompleteBloodCountCBC(CompleteBloodCount completeBloodCountCBC);
	
	Lipid saveLipid(Lipid lipid);
	
	KidneyFunctionTest saveKidneyFunctionTestKFT(KidneyFunctionTest kidneyFunctionTestKFT );
	
	UrineTestPhysicalMicroscopic saveUrineTestPhysicalMicroscopic(UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic);

	InvestigationFinalReportDto findAllInvestigationReportByAmeId(String ameId);

	ViralMakers saveViral(ViralMakers viralMakers);
	
	ThyroidProfile saveThyroidProfile(ThyroidProfile thyroidProfile);

	Others savOthers(Others others);
	
	OtherTest saveOtherTest(OtherTest otherTest);

	CompleteBloodCount get(String ameId);

	InvestigationDto getReportView(String ameId);
	 
	 
	void saveCheckUpListForCandidate(List<CheckUpList> checkUpLists,String forcePersonnelId,String ipAddress);
	
}
