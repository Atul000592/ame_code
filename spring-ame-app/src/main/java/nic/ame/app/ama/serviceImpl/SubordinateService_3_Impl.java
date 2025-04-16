package nic.ame.app.ama.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.ama.dto.InvestidationReportDto;
import nic.ame.app.ama.service.SubordinateService_3;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.model.ViralMakers;
import nic.ame.app.master.repository.BloodSugarFRepository;
import nic.ame.app.master.repository.BloodSugarHbA1cRepository;
import nic.ame.app.master.repository.BloodSugarPPRepository;
import nic.ame.app.master.repository.BloodSugarRandomRepository;
import nic.ame.app.master.repository.CompleteBloodCountCBCRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.KidneyFunctionTestKFTRepository;
import nic.ame.app.master.repository.LipidRepository;
import nic.ame.app.master.repository.LiverFunctionTestRepository;
import nic.ame.app.master.repository.OthersTestRepository;
import nic.ame.app.master.repository.ThyroidProfileRepository;
import nic.ame.app.master.repository.UrineTestPhysicalMicroscopicRespository;
import nic.ame.app.master.repository.ViralMakersRepository;

@Service
public class SubordinateService_3_Impl implements SubordinateService_3{
	
	Logger logger=LoggerFactory.getLogger(SubordinateService_3_Impl.class);

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private BloodSugarFRepository bloodSugarFRepository;
	
	@Autowired
	private KidneyFunctionTestKFTRepository functionTestKFTRepository;
	
	@Autowired
	private LipidRepository lipidRepository;
	
	@Autowired
	private UrineTestPhysicalMicroscopicRespository urineTestPhysicalMicroscopicRespository;
	
	@Autowired
	private CompleteBloodCountCBCRepository bloodCountCBCRepository;
	
	@Autowired
	private LiverFunctionTestRepository liverFunctionTestRepository;
	
	@Autowired
	private ViralMakersRepository viralMakersRepository;
	
	@Autowired
	private ThyroidProfileRepository thyroidProfileRepository;
	
	@Autowired
	private BloodSugarPPRepository bloodSugarPPRepository;
	
	@Autowired
	private BloodSugarRandomRepository bloodSugarRandomRepository;
	
	@Autowired
	private BloodSugarHbA1cRepository bloodSugarHbA1cRepository;
	
	@Autowired
	private OthersTestRepository othersTestRepository;
	
	@Override
	public List<ForcePersonnelDto> getDealingHandAssignmentList(String unit, int forceNo) 
	{
     	List<Object> objects=forcePersonalRepository.getListOfForcePersonalToAssignDealingHand(unit, forceNo);
     	List<ForcePersonnelDto> forcePersonalDtos = new ArrayList<>();
		if (objects.isEmpty()) {
			return forcePersonalDtos;
		}
		Iterator<Object> iterator = objects.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonnelDto personalDto=new ForcePersonnelDto();
			if (obj[0] != null) {
				personalDto.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				personalDto.setForceId(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				personalDto.setForcePersonalId(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				personalDto.setRank(rankRepo.findById(Integer.parseInt(String.valueOf(obj[3]).trim())).get().getRankFullName());
			}
			if (obj[4] != null) {
				personalDto.setForceName(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				personalDto.setUnitName(String.valueOf(obj[5]).trim());
			}
		
			forcePersonalDtos.add(personalDto);
		
	}return forcePersonalDtos;
	}

	@Override
	public String getInvestidationReport(String ameId, String testCode) {
		
		String filePath = null;
		
		switch (testCode) {
        case "bsf":
             Optional<BloodSugarF> bsfOptional=bloodSugarFRepository.findByAmeId(ameId);
             if(!bsfOptional.isEmpty()) {
            	filePath=bsfOptional.get().getPath();
             }
            break;
        case "kft":
        	 Optional<KidneyFunctionTest> kftOptional=functionTestKFTRepository.findByAmeId(ameId);
        	 if(!kftOptional.isEmpty()) {
        	    filePath=kftOptional.get().getPath();
        	 }
            break;
        case "lipid":
        	Optional<Lipid> lipidOptional=lipidRepository.findByAmeId(ameId);
       	    if(!lipidOptional.isEmpty()) {
       	        filePath=lipidOptional.get().getPath();
       	     }
            break;
        case "utmicro":
        	Optional<UrineTestPhysicalMicroscopic> urineTestPhysicalMicroscopicOptional=urineTestPhysicalMicroscopicRespository.findByAmeId(ameId);
       	    if(!urineTestPhysicalMicroscopicOptional.isEmpty()) {
       	        filePath=urineTestPhysicalMicroscopicOptional.get().getPath();
       	     }
            break;
        case "ecg":
             Optional<Others> ecgOptional =othersTestRepository.findAllByAmeIdAndTestCode(ameId, testCode);
             if(!ecgOptional.isEmpty()) {
        	        filePath=ecgOptional.get().getPath();
        	     }
            break;
        case "cbc":
        	Optional<CompleteBloodCount> bloodCountCBCOptional=bloodCountCBCRepository.findById(ameId);
       	    if(!bloodCountCBCOptional.isEmpty()) {
       	        filePath=bloodCountCBCOptional.get().getPath();
       	     }
            break;
        case "lft":
            //System.out.println("Liver Function Test");
            Optional<LiverFunctionTest> liverFunctionTestOptional=liverFunctionTestRepository.findById(ameId);
       	    if(!liverFunctionTestOptional.isEmpty()) {
       	        filePath=liverFunctionTestOptional.get().getPath();
       	     }
            break;
        case "vrl":
        	 //System.out.println("Viral Load Test");
        	Optional<ViralMakers> viralMakersOptional=viralMakersRepository.findById(ameId);
       	    if(!viralMakersOptional.isEmpty()) {
       	        filePath=viralMakersOptional.get().getPath();
       	     }
       	    break;
        case "tp":
        	//System.out.println("Total Protein Test");
        	Optional<ThyroidProfile> thyroidProfileOptional=thyroidProfileRepository.findById(ameId);
       	    if(!thyroidProfileOptional.isEmpty()) {
       	        filePath=thyroidProfileOptional.get().getPath();
       	     }
            break;
        case "usg":
        	Optional<Others> usgOptional =othersTestRepository.findAllByAmeIdAndTestCode(ameId, testCode);
            if(!usgOptional.isEmpty()) {
       	        filePath=usgOptional.get().getPath();
       	     }
            break;
        case "xray":
           // System.out.println("X-ray Imaging");
            Optional<Others> xRayOptional =othersTestRepository.findAllByAmeIdAndTestCode(ameId, testCode);
            if(!xRayOptional.isEmpty()) {
       	        filePath=xRayOptional.get().getPath();
       	     }
            break;
        case "bspp":
            System.out.println("Blood Sugar Postprandial Test");
            Optional<BloodSugarPP> bloodSugarPPOptional=bloodSugarPPRepository.findByAmeId(ameId);
            if(!bloodSugarPPOptional.isEmpty()) {
       	        filePath=bloodSugarPPOptional.get().getPath();
       	     }
            break;
        case "bsrandom":
           // System.out.println("Blood Sugar Random Test");
            Optional<BloodSugarRandom> bloodSugarRandomOptional=bloodSugarRandomRepository.findByAmeId(ameId);
            if(!bloodSugarRandomOptional.isEmpty()) {
       	        filePath=bloodSugarRandomOptional.get().getPath();
       	     }
            break;
        case "bsHbA1c":
            System.out.println("Blood Sugar HbA1c Test");
            
            Optional<BloodSugarHbA1c> bloodSugarHbA1cOptional=bloodSugarHbA1cRepository.findByAmeId(ameId);
            if(!bloodSugarHbA1cOptional.isEmpty()) {
       	        filePath=bloodSugarHbA1cOptional.get().getPath();
       	     }
            break;
        default:
        	logger.info("no test found by ameId :"+ameId+"and testCode "+testCode);
           
    }
		
		
		
		
		return filePath;
	
	}
	
}
