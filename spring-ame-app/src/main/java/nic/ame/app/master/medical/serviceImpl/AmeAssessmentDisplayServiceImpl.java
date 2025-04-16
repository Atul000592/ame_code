package nic.ame.app.master.medical.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nic.ame.app.board.member.repository.AbdomenRepo;
import nic.ame.app.board.member.repository.AmeMasterStatusRepo;
import nic.ame.app.board.member.repository.AppendagesRepo;
import nic.ame.app.board.member.repository.CentralNervousSystemRepo;
import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.board.member.repository.CranialNervesMeningealSignRepo;
import nic.ame.app.board.member.repository.EyeFactorRepo;
import nic.ame.app.board.member.repository.GeneralExaminationRepo;
import nic.ame.app.board.member.repository.GynaeAndObsFemaleRepo;
import nic.ame.app.board.member.repository.HearingRepo;
import nic.ame.app.board.member.repository.InvestigationRepo;
import nic.ame.app.board.member.repository.MedicalCheckUpMasterRepo;
import nic.ame.app.board.member.repository.PsychologicalAssessmentAsLaidDownRepo;
import nic.ame.app.board.member.repository.PhysicalMeasurmentRepo;
import nic.ame.app.board.member.repository.ReflexesRepo;
import nic.ame.app.board.member.repository.RespiratorySystemRepo;
import nic.ame.app.board.member.repository.SensoryRepo;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.medical.model.NGOAMECandidateRequestDetails;
import nic.ame.app.master.medical.model.NGOAMERequest;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.NGOAMECandidateRequestDetailsRepo;
import nic.ame.app.master.service.SequenceService;

@Service
@Transactional
public class AmeAssessmentDisplayServiceImpl implements AmeAssessmentDisplayService{

    @Autowired
	private PhysicalMeasurmentRepo physicalMeasurmentRepo;

	@Autowired
	private GeneralExaminationRepo generalExaminationRepo;
	
	@Autowired
	private CheckUpListRepo checkUpListRepo;
	
	@Autowired
	private CentralNervousSystemRepo centralNervousSystemRepo;
	
	@Autowired
	private ReflexesRepo reflexesRepo;
	
	@Autowired
	private CranialNervesMeningealSignRepo cranialNervesMeningealSignRepo;
	
	@Autowired
	private SensoryRepo sensoryRepo;
	
	@Autowired
	private AbdomenRepo abdomenRepo;
	
	@Autowired 
	private AmeMasterStatusRepo ameMasterStatusRepo;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private EyeFactorRepo eyeFactorRepo;
	
	@Autowired 
	private HearingRepo hearingRepo;
	
	@Autowired
	private InvestigationRepo investigationRepo;
	
	@Autowired
	private AppendagesRepo appendagesRepo;
  
	@Autowired
	private PsychologicalAssessmentAsLaidDownRepo asLaidDownRepo;
	
	@Autowired
	private RespiratorySystemRepo respiratorySystemRepo;
	
	@Autowired
	private MedicalCheckUpMasterRepo checkUpMasterRepo;
	
	@Autowired
	private NGOAMECandidateRequestDetailsRepo candidateRequestDetailsRepo;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private GynaeAndObsFemaleRepo gynaeAndObsFemaleRepo;
	//----------------------retrieve all data AME Declaration--------------------------------------//
	
	@Override
	public MedExamDtoRequest getMedExamDtoRequestData(String ameId) {
		
		MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
		
		Optional<Reflexes> reflexesOptional=reflexesRepo.findById(ameId);
		Optional<SensorySystem> sensorySystemOptional= sensoryRepo.findById(ameId);
		Optional<CranialNervesMeningealSign> cranialNervesMeningealSignRepoOptional=cranialNervesMeningealSignRepo.findById(ameId);
		Optional<Abdomen> abdomenOptional =abdomenRepo.findById(ameId);
		Optional<GeneralExamination> generalOptional =generalExaminationRepo.findById(ameId);
		Optional<CentralNervousSystem>centralNervousSystemOptional=centralNervousSystemRepo.findById(ameId);
		Optional<PhysicalMeasurement> physicalMeasurmentOptional= physicalMeasurmentRepo.findById(ameId);
		Optional<EyeFactor> eyeFactorOptional=eyeFactorRepo.findById(ameId);
		Optional<Hearing> hearingOptional=hearingRepo.findById(ameId);
		Optional<Investigation> investigationOptional=investigationRepo.findByAmeId(ameId);
		Optional<Appendages> appendagesOptional=appendagesRepo.findById(ameId);
		Optional<PsychologicalAssessmentAsLaidDown> phychologicalOptional=asLaidDownRepo.findById(ameId);
		Optional<RespiratorySystem> respiratorySystemOptional=respiratorySystemRepo.findById(ameId);
		Optional<GynaeAndObsFemale> gynaeAndObsFemaleOptional=gynaeAndObsFemaleRepo.findById(ameId);
		
		Reflexes reflexes=new Reflexes();
		SensorySystem sensorySystem=new SensorySystem();
		CranialNervesMeningealSign cranialNervesMeningealSign=new CranialNervesMeningealSign();
		Abdomen abdomen=new Abdomen();
		GeneralExamination generalExamination=new GeneralExamination();
		CentralNervousSystem centralNervousSystem=new CentralNervousSystem();
		PhysicalMeasurement physicalMeasurement=new PhysicalMeasurement();
		EyeFactor eyeFactor=new EyeFactor();
		Appendages appendages=new Appendages();
		Hearing hearing=new Hearing();
		Investigation  investigation=new Investigation();
		PsychologicalAssessmentAsLaidDown asLaidDown=new PsychologicalAssessmentAsLaidDown();
		RespiratorySystem respiratorySystem=new RespiratorySystem();
	     GynaeAndObsFemale gynaeAndObsFemale=new GynaeAndObsFemale();
		
		
		if (!reflexesOptional.isEmpty()) {
			examDtoRequest.setReflexes(reflexesOptional.get());
		} else {
			examDtoRequest.setReflexes(reflexes);

		}
		
		if (!sensorySystemOptional.isEmpty()) {
			examDtoRequest.setSensorysystem(sensorySystemOptional.get());
		} else {
			examDtoRequest.setSensorysystem(sensorySystem);
		}
		
		if (!cranialNervesMeningealSignRepoOptional.isEmpty()) {
			examDtoRequest.setCranialNervesMeningealSign(cranialNervesMeningealSignRepoOptional.get());
		} else {
			examDtoRequest.setCranialNervesMeningealSign(cranialNervesMeningealSign);
		}
		
		
		if (!abdomenOptional.isEmpty()) {
			examDtoRequest.setAbdomen(abdomenOptional.get());
		} else {
			examDtoRequest.setAbdomen(abdomen);
		}
		
		
		if (!generalOptional.isEmpty()) {
			examDtoRequest.setGeneralExamination(generalOptional.get());
		} else {
                   examDtoRequest.setGeneralExamination(generalExamination);
		}
		
		
		if (!centralNervousSystemOptional.isEmpty()) {
			examDtoRequest.setCns(centralNervousSystemOptional.get());
		}else {
			examDtoRequest.setCns(centralNervousSystem);
		}
		
		
		if (!physicalMeasurmentOptional.isEmpty()) {
			examDtoRequest.setPhysicalMeasurement(physicalMeasurmentOptional.get());
		}else {
			examDtoRequest.setPhysicalMeasurement(physicalMeasurement);
		}
		
		if (!eyeFactorOptional.isEmpty()) {
			examDtoRequest.setEyeFactor(eyeFactorOptional.get());
		}else {
			examDtoRequest.setEyeFactor(eyeFactor);
		}
		
		if (!appendagesOptional.isEmpty()) {
			examDtoRequest.setAppendages(appendagesOptional.get());
		}else {
			examDtoRequest.setAppendages(appendages);
		}
		
		if (!hearingOptional.isEmpty()) {
			examDtoRequest.setHearing(hearingOptional.get());
		}else {
			examDtoRequest.setHearing(hearing);
		}
		
		if (!investigationOptional.isEmpty()) {
			examDtoRequest.setInvestigation(investigationOptional.get());
		}else {
			examDtoRequest.setInvestigation(investigation);
		}
		if (!phychologicalOptional.isEmpty()) {
			examDtoRequest.setPhychologicalAssessmentAsLaidDown(phychologicalOptional.get());
		}else {
			examDtoRequest.setPhychologicalAssessmentAsLaidDown(asLaidDown);
		}
		
		if (!respiratorySystemOptional.isEmpty()) {
			examDtoRequest.setRespiratorySystem(respiratorySystemOptional.get());
		}else {
			examDtoRequest.setRespiratorySystem(respiratorySystem);
		}
		if (!gynaeAndObsFemaleOptional.isEmpty()) {
			examDtoRequest.setGynaeAndObsFemale(gynaeAndObsFemaleOptional.get());
		}else {
			examDtoRequest.setGynaeAndObsFemale(gynaeAndObsFemale);
		}
		
		
		
		return examDtoRequest;
	}


//=================================display Application Under Process-list==================================//

	@Override
	public List<AmeDeclarationIndividualModel>displayApplicationUnderProcess() {
	
		return ameDeclarationRepository.findByDataLogic();
      	
}


//=======================================display-application-completed-list==========================================//

	@Override
	public Optional<AmeMasterStatus> displayApplicationCompleted( String status) {
		
		Optional<AmeMasterStatus> optional=ameMasterStatusRepo.findByAmeIdStatusCompleted(status);

        if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
	}
	
	//==========================MedicalCheckUpMaster===============================//

	@Override
	public List<MedicalCheckUpMaster> checkUpMasters() {
        Sort sortByName = Sort.by(Sort.Order.asc("testName"));

	   List<MedicalCheckUpMaster> checkUpMasters=checkUpMasterRepo.findAll(sortByName);
		return checkUpMasters;
	}


	@Override
	
	public boolean saveCheckUpList(CheckUpList checkUpList) {
		
		if( checkUpListRepo.save(checkUpList)!=null) {
		   return true;	
		}
		
		return false;
	}


	@Override
	public String saveNgoAmeList(List<String> list,String controllingForcepersonalId,int forceNo) {
	        
		String TransactionId=sequenceService.createSequenceNgoAme(forceNo);
	
		NGOAMERequest ngoameRequest=new NGOAMERequest();
	   
		for (String string : list) {
			NGOAMECandidateRequestDetails candidateRequestDetails=new NGOAMECandidateRequestDetails();
			candidateRequestDetails.setTransactionId(TransactionId);
			candidateRequestDetails.setCandidateForcepersonalId(string);
			candidateRequestDetails.setNgoAmeRequeststatus(1);
			candidateRequestDetailsRepo.save(candidateRequestDetails);
		}
	
	
		return TransactionId;
	}

}
