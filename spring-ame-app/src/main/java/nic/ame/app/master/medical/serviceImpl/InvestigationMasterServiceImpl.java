package nic.ame.app.master.medical.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.master.controller.InvestigationMasterController;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.InvestigationReportDto;
import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.InvestigationReport;
import nic.ame.app.master.medical.model.InvestigationReportFileUpload;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.model.ViralMakers;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.ref.entity.RefInvestigationTestMaster;

import nic.ame.app.master.ref.entity.repo.RefInvestigationMasterRepo;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.BloodSugarFRepository;
import nic.ame.app.master.repository.BloodSugarHbA1cRepository;
import nic.ame.app.master.repository.BloodSugarPPRepository;
import nic.ame.app.master.repository.BloodSugarRandomRepository;
import nic.ame.app.master.repository.BloodSugarRepository;
import nic.ame.app.master.repository.CompleteBloodCountCBCRepository;
import nic.ame.app.master.repository.InvestigationReportFileUploadRepo;
import nic.ame.app.master.repository.InvestigationReportRepo;
import nic.ame.app.master.repository.KidneyFunctionTestKFTRepository;
import nic.ame.app.master.repository.LipidRepository;
import nic.ame.app.master.repository.LiverFunctionTestRepository;
import nic.ame.app.master.repository.OtherTestRepository;
import nic.ame.app.master.repository.OthersTestRepository;
import nic.ame.app.master.repository.ThyroidProfileRepository;
import nic.ame.app.master.repository.UrineTestPhysicalMicroscopicRespository;
import nic.ame.app.master.repository.ViralMakersRepository;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.UserDateUtil;
import nic.ame.app.master.medical.model.OtherTest;


@Service
public class InvestigationMasterServiceImpl implements InvestigationMasterService {
    
    
	private Logger logger =  LoggerFactory.getLogger(InvestigationMasterServiceImpl.class);
	
	@Autowired
	private CheckUpListRepo checkUpListRepo;
	
	@Autowired
	private RefInvestigationMasterRepo  refInvestigationMasterRepo;

	@Autowired
	private ThyroidProfileRepository thyroidProfileRepository;

	@Autowired
	private OthersTestRepository othersTestRepository;

	@Autowired
	private ViralMakersRepository viralMakersRepository;
	
	
	@Autowired
	private InvestigationReportRepo investigationReportRepo;
	
	
	@Autowired
	private InvestigationReportFileUploadRepo investigationReportFileUploadRepo;

	@Autowired
	private BloodSugarRepository bloodSugarRepository; 
	
	@Autowired
	private LiverFunctionTestRepository liverFunctionTestRepository;
	
	@Autowired
	private CompleteBloodCountCBCRepository completeBloodCountCBCRepository;
	
	@Autowired
	private LipidRepository lipidRepository;
	
	@Autowired
	private KidneyFunctionTestKFTRepository kidneyFunctionTestKFTRepository;
	
	@Autowired
	private UrineTestPhysicalMicroscopicRespository urineTestPhysicalMicroscopicRepository;
	
	@Autowired
	private OtherTestRepository otherTestRepository;
	
	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo; 
	@Autowired
	private BloodSugarPPRepository bloodSugarPPRepository;
	@Autowired
	private BloodSugarFRepository bloodSugarfRepository;
	
	@Autowired
	private BloodSugarRandomRepository bloodSugarRandomRepository;
	@Autowired
	private BloodSugarHbA1cRepository bloodSugarHbA1cRepository;
	
	@Autowired 
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	

	
	
	@Override
	public List<RefInvestigationTestMaster> refInvestigationTestMastersListByTestCode(String testCode) {
		return refInvestigationMasterRepo.findByTestCode(testCode);
	}
	
	

	@Override
	public List<CheckUpList> checkUpListsByAmeId(String AmeId,int status) {
		List<CheckUpList>checkUpLists=new  ArrayList<>();
		checkUpLists=checkUpListRepo.findCheckUpListByAmeId(AmeId,status);
		
		if(checkUpLists.size()>0) 
			return checkUpLists;
		else
			return null;
	}

	
	
	
	@Override
	public  List<Map<String, String>> convertJsonToList(String jsonData) {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				// Convert JSON string to List<Map<String, String>>
				List<Map<String, String>> list = objectMapper.readValue(jsonData,
						new TypeReference<List<Map<String, String>>>() {
						});
				return list;
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
		}

	
	
	
	
	
	@Override
	@Transactional
	public boolean saveInvestigationReport(List<InvestigationReport> investigationReports,
			FileUploadDto fileUploadDto,String ameId,String testCode) {
		
               checkUpListRepo.updateFileUploadStatus(ameId,testCode);
		       logger.info("checkUp Flag invited......");
		       int count;
		     for (InvestigationReport investigationReport : investigationReports) {
		    	 investigationReportRepo.save(investigationReport);
		    	 logger.info("update ...........!");
			  }
		
		     
		
		
		int year=UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD+File.separator+year;
		
		String path=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year)); 
		path=path+File.separator+ameId;
		String pathForAmeId=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,ameId); 
		
		String savePath=pathForAmeId+File.separator+ameId+"-"+fileUploadDto.getFileName();
		 try {
			FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
			InvestigationReportFileUpload fileUpload=new InvestigationReportFileUpload();
			fileUpload.setAmeId(ameId);
			fileUpload.setFilePath(savePath);
			fileUpload.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
			fileUpload.setFileType(fileUploadDto.getFileType());
			fileUpload.setFileName(fileUploadDto.getFileName());
			fileUpload.setTestCode(testCode);
			
			investigationReportFileUploadRepo.save(fileUpload); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CheckUpList> findCheckUpListByAmeIdUploadFlagTrue(String ameId) {
		return checkUpListRepo.findCheckUpListByAmeIdUploadFlagTrue(ameId);
		
	}

	@Override
	public Map<String, Map<String,String>> findInvestigationReportByAmeId(String ameId) {
	List<InvestigationReportDto> investigationReportDtos=new ArrayList<>();
	
	List<Object> investigationReportDtoObjectList = investigationReportRepo.getInvestigationReportByAmeId(ameId);
	
	Iterator itr = investigationReportDtoObjectList.iterator();
	while(itr.hasNext())
	{    Object[] obj = (Object[]) itr.next();
		InvestigationReportDto investigationReportDto= new InvestigationReportDto();
		if(obj[0]!=null) {
      investigationReportDto.setAmeId(String.valueOf(obj[0]).trim());
		}
		if(obj[1]!=null) {
		      investigationReportDto.setTestCode(String.valueOf(obj[1]).trim());
				}
		if(obj[2]!=null) {
		      investigationReportDto.setTestName(String.valueOf(obj[2]).trim());
				}
		if(obj[3]!=null) {
		      investigationReportDto.setValue(String.valueOf(obj[3]).trim());
				}
	
		investigationReportDtos.add(investigationReportDto);
	}
	
	investigationReportDtos=investigationReportDtos.stream()
			.filter(s->!s.getTestCode().equals("cbc"))
			.collect(Collectors.toList());
	
	Map<String, Map<String,String>> groupByTestCode = new HashMap<>();
    for (InvestigationReportDto investigationReportDto : investigationReportDtos) {
        
        Map<String, String> testNameToTestValue = groupByTestCode.computeIfAbsent(investigationReportDto.getTestCode(), k -> new HashMap<>());

       
       
        testNameToTestValue.put(investigationReportDto.getTestName(),investigationReportDto.getValue());
    }

    
   
    groupByTestCode.forEach((testCode, testNameToTestValue) -> {
        System.out.println("Test Code: " + testCode);
        testNameToTestValue.forEach((testName, testValue) -> {
            System.out.println("Test Name: " + testName);
            System.out.println("Test Vakue: " + testValue);
        });
    });
	
//	investigationReportDtos.stream().collect(Collectors.groupingByConcurrent(InvestigationReportDto::getTestCode));
//	
	
 	
	
	return groupByTestCode;
}

	@Override
	public BloodSugar saveBloodSugar(BloodSugar bloodSugar) {
		
	 return bloodSugarRepository.save(bloodSugar);
			
}



	@Override
	public LiverFunctionTest saveLiverFunctionTest(LiverFunctionTest liverFunctionTest) {
		return liverFunctionTestRepository.save(liverFunctionTest);
	
	}



	@Override
	public boolean saveFileOfInvestigationReportLiver(FileUploadDto fileUploadDto,String ameId) {

		int year=UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD+File.separator+year;
		
		String path=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year)); 
		path=path+File.separator+ameId;
		String pathForAmeId=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,ameId); 
		
		String savePath=pathForAmeId+File.separator+ameId+"-"+fileUploadDto.getFileName();
		 
			try {
				FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LiverFunctionTest liverFunctionTest =new LiverFunctionTest();
			liverFunctionTest.setAmeId(ameId);
			liverFunctionTest.setFileName(fileUploadDto.getFileName());
			liverFunctionTest.setFileType(fileUploadDto.getFileType());
			liverFunctionTest.setPath(savePath);
			liverFunctionTest.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
			if(liverFunctionTestRepository.save(liverFunctionTest)!=null) {
				return true;
			}
		
	return false;

		 }



	@Override
	public CompleteBloodCount saveCompleteBloodCountCBC(CompleteBloodCount completeBloodCountCBC) {
		
		return completeBloodCountCBCRepository.save(completeBloodCountCBC);
	}



	@Override
	public Lipid saveLipid(Lipid lipid) {
		
		return lipidRepository.save(lipid);
	}



	@Override
	public KidneyFunctionTest saveKidneyFunctionTestKFT(KidneyFunctionTest kidneyFunctionTestKFT) {
		
		return kidneyFunctionTestKFTRepository.save(kidneyFunctionTestKFT);
	}



	@Override
	public UrineTestPhysicalMicroscopic saveUrineTestPhysicalMicroscopic(
			UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic) {
		
		return urineTestPhysicalMicroscopicRepository.save(urineTestPhysicalMicroscopic);
	}


//===========================investigation display report=====================================//
	
	@Override
	public InvestigationFinalReportDto findAllInvestigationReportByAmeId(String ameId) {
		
		InvestigationFinalReportDto investigationFinalReportDto = new InvestigationFinalReportDto();
		Optional<LiverFunctionTest> lftOptional = liverFunctionTestRepository.getByAmeId(ameId);
		Optional<KidneyFunctionTest> kftOptional = kidneyFunctionTestKFTRepository.findByAmeId(ameId);
		Optional<Lipid> lipidOptional = lipidRepository.findByAmeId(ameId);
		Optional<BloodSugar> bsOptional = bloodSugarRepository.findByAmeId(ameId);
		Optional<UrineTestPhysicalMicroscopic> utpmOptional = urineTestPhysicalMicroscopicRepository.findByAmeId(ameId);
		Optional<CompleteBloodCount> cbcOptional = completeBloodCountCBCRepository.findByAmeId(ameId);

		LiverFunctionTest liverFunctionTest = new LiverFunctionTest();
		KidneyFunctionTest kft = new KidneyFunctionTest();
		Lipid lipid = new Lipid();
		BloodSugar bloodSugar = new BloodSugar();
		UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic = new UrineTestPhysicalMicroscopic();
		CompleteBloodCount bloodCountCBC = new CompleteBloodCount();

		if (!lftOptional.isEmpty()) {
			liverFunctionTest = lftOptional.get();
			investigationFinalReportDto.setLft(liverFunctionTest);
		} else {
			investigationFinalReportDto.setLft(liverFunctionTest);
		}

		if (!kftOptional.isEmpty()) {
			kft = kftOptional.get();
			investigationFinalReportDto.setKft(kft);
		} else {
			investigationFinalReportDto.setKft(kft);
		}
		if (!lipidOptional.isEmpty()) {
			lipid = lipidOptional.get();
			investigationFinalReportDto.setLipid(lipid);
		} else {
			investigationFinalReportDto.setLipid(lipid);

		}
		if (!bsOptional.isEmpty()) {
			bloodSugar = bsOptional.get();
			investigationFinalReportDto.setBs(bloodSugar);

		} else {
			investigationFinalReportDto.setBs(bloodSugar);
		}
		if (!utpmOptional.isEmpty()) {
			urineTestPhysicalMicroscopic = utpmOptional.get();
			investigationFinalReportDto.setUtpm(urineTestPhysicalMicroscopic);
		} else {
			investigationFinalReportDto.setUtpm(urineTestPhysicalMicroscopic);

		}

		if (!cbcOptional.isEmpty()) {
			bloodCountCBC = cbcOptional.get();
			investigationFinalReportDto.setCbc(bloodCountCBC);
		} else {
			investigationFinalReportDto.setCbc(bloodCountCBC);

		}
		return investigationFinalReportDto;
	}



	@Override
	public ViralMakers saveViral(ViralMakers viralMakers) {
		return this.viralMakersRepository.save(viralMakers);
	}



	@Override
	public ThyroidProfile saveThyroidProfile(ThyroidProfile thyroidProfile) {
		return this.thyroidProfileRepository.save(thyroidProfile);
	}



	@Override
	public Others savOthers(Others others) {
		return this.othersTestRepository.save(others);
	}



	@Override
	public OtherTest saveOtherTest(OtherTest otherTest) {
		
		return otherTestRepository.save(otherTest);
	}



	@Override
	public CompleteBloodCount get(String ameId) {
		
		Optional<CompleteBloodCount> optionalCBC=this.completeBloodCountCBCRepository.findById(ameId);
		if(!optionalCBC.isPresent()){
			try {
				throw new Exception("Data Error : CompleteBloodCountCBC Not Found "+optionalCBC);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		CompleteBloodCount completeBloodCountCBC=optionalCBC.get();
		return completeBloodCountCBC;
	}



	@Override
	public InvestigationDto getReportView(String ameId) {
		
		InvestigationDto investigationDto = new InvestigationDto();
		Optional<ThyroidProfile> thyOptional= this.thyroidProfileRepository.findByAmeId(ameId);
		Optional<Lipid> lipidOptional=this.lipidRepository.findByAmeId(ameId);
		Optional<CompleteBloodCount> cbcOptional=this.completeBloodCountCBCRepository.findByAmeId(ameId);
		Optional<ViralMakers> viralOptional=this.viralMakersRepository.findByAmeId(ameId);
		Optional<LiverFunctionTest> liverOptional=this.liverFunctionTestRepository.findByAmeId(ameId);
		Optional<KidneyFunctionTest> kidneyOptional=this.kidneyFunctionTestKFTRepository.findByAmeId(ameId);
		List<Others> otherOptional=this.othersTestRepository.findByAmeId(ameId);
		Optional<UrineTestPhysicalMicroscopic> urineOptional=this.urineTestPhysicalMicroscopicRepository.findByAmeId(ameId);
		Optional<BloodSugar> blooOptional=this.bloodSugarRepository.findByAmeId(ameId);
        Optional<BloodSugarPP> bloodSugarPP = bloodSugarPPRepository.findById(ameId);
        Optional<BloodSugarF> bloodSugarF = bloodSugarfRepository.findById(ameId);
        Optional<BloodSugarRandom> bloodSugarRandom = bloodSugarRandomRepository.findById(ameId);
        Optional<BloodSugarHbA1c> bloodSugarHbA1c = bloodSugarHbA1cRepository.findById(ameId);

		ThyroidProfile thyroidProfile= new ThyroidProfile();
		Lipid lipid= new Lipid();
		BloodSugar bloodSugar = new BloodSugar();
		CompleteBloodCount completeBloodCountCBC= new CompleteBloodCount();
		ViralMakers viralMakers = new ViralMakers();
		LiverFunctionTest liverFunctionTest = new LiverFunctionTest();
		KidneyFunctionTest kidneyFunctionTestKFT= new KidneyFunctionTest();
		UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic= new UrineTestPhysicalMicroscopic();
		List<Others> others = new ArrayList<>(); 

		if(viralOptional.isPresent()){
			investigationDto.setViralMakers(viralOptional.get());
		}else{
			investigationDto.setViralMakers(viralMakers);
		}

		if(!otherOptional.isEmpty()){
			investigationDto.setOthers(otherOptional);
		}else{
			investigationDto.setOthers(others);
		}


		if(thyOptional.isPresent()){
			investigationDto.setThyroidProfile(thyOptional.get());
		}else{
			investigationDto.setThyroidProfile(thyroidProfile);
		}


		if(lipidOptional.isPresent()){
			investigationDto.setLipid(lipidOptional.get());
		}else{
			investigationDto.setLipid(lipid);
		}


		if(cbcOptional.isPresent()){
			investigationDto.setCompleteBloodCountCBC(cbcOptional.get());
		}else{
			investigationDto.setCompleteBloodCountCBC(completeBloodCountCBC);
		}


		if(liverOptional.isPresent()){
			investigationDto.setLiverFunctionTest(liverOptional.get());
		}else{
			investigationDto.setLiverFunctionTest(liverFunctionTest);
		}

		if(kidneyOptional.isPresent()){
			investigationDto.setKft(kidneyOptional.get());
		}else{
			investigationDto.setKft(kidneyFunctionTestKFT);
		}

		if(urineOptional.isPresent()){
			investigationDto.setUrineTestPhysicalMicroscopic(urineOptional.get());
		}else{
			investigationDto.setUrineTestPhysicalMicroscopic(urineTestPhysicalMicroscopic);
		}



		if(blooOptional.isPresent()){
			investigationDto.setBloodSugar(blooOptional.get());
		}else{
			investigationDto.setBloodSugar(bloodSugar);
		}
		if(viralOptional.isPresent()){
			investigationDto.setViralMakers(viralOptional.get());
		}else{
			investigationDto.setViralMakers(viralMakers);
		}
		
		if(bloodSugarPP.isPresent()){
			investigationDto.setBloodSugarPPValue(bloodSugarPP.get());
		}
		else {
			investigationDto.setBloodSugarPPValue(new BloodSugarPP());
		}
		if(bloodSugarF.isPresent()){
			investigationDto.setBloodSugarFValue(bloodSugarF.get());
		}
		else {
			investigationDto.setBloodSugarFValue(new BloodSugarF());
		}
		if(bloodSugarRandom.isPresent()){
			investigationDto.setBloodSugarRandomValue(bloodSugarRandom.get());
		}
		else {
			investigationDto.setBloodSugarRandomValue(new BloodSugarRandom());
		}
		if(bloodSugarHbA1c.isPresent()){
			investigationDto.setBloodSugarHbA1cValue(bloodSugarHbA1c.get());
		}
		else {
			investigationDto.setBloodSugarHbA1cValue(new BloodSugarHbA1c());
		}

		return investigationDto;
	}



	@Override
	@Transactional
	public void saveCheckUpListForCandidate(List<CheckUpList> checkUpLists,String forcePersonnelId,String ipaddress) {
		
		String ameId = null;
		
		for (CheckUpList checkUpList : checkUpLists) {
			checkUpList.setCreatedOn(Calendar.getInstance().getTime());
			checkUpList.setCreatedFrom(ipaddress);
			checkUpList.setCreatedBy(forcePersonnelId);
			CheckUpList checkUpListSaveData=checkUpListRepo.save(checkUpList);
			ameId=checkUpList.getAmeId();
			if(checkUpListSaveData!=null)
				logger.info("Data Saved.........checkuplist");
			else
				logger.info("Not Saved ERROR.........checkuplist");

				
		}
		
		Optional<AmeApplicationFlowStatus> ameApplicationFlowStatusOptional= ameApplicationFlowStatusRepo.findByAmeId(ameId);
		if(!ameApplicationFlowStatusOptional.isEmpty()) {
			ameApplicationFlowStatusOptional.get().setCheckUpListFlag(1);
			ameApplicationFlowStatusRepo.save(ameApplicationFlowStatusOptional.get());
			logger.info("AmeApplicationFlowStatus....updated........!");

		}
		
		 
		
	}
	
}
