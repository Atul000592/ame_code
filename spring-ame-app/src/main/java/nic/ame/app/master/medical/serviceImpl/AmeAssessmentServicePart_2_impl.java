
package nic.ame.app.master.medical.serviceImpl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nic.ame.app.board.member.repository.AbdomenRepo;
import nic.ame.app.board.member.repository.AmeAppointmentDetailsRepo;
import nic.ame.app.board.member.repository.AmeMasterStatusRepo;
import nic.ame.app.board.member.repository.CentralNervousSystemRepo;
import nic.ame.app.board.member.repository.CranialNervesMeningealSignRepo;
import nic.ame.app.board.member.repository.GeneralExaminationRepo;
import nic.ame.app.board.member.repository.GynaeAndObsFemaleRepo;
import nic.ame.app.board.member.repository.InvestigationRepo;
import nic.ame.app.board.member.repository.PhysicalMeasurmentRepo;
import nic.ame.app.board.member.repository.ReflexesRepo;
import nic.ame.app.board.member.repository.RespiratorySystemRepo;
import nic.ame.app.board.member.repository.SensoryRepo;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.AmePendingStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.RefPsychologicalType;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AmePendingStatusRepository;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.constant.CommonConstant;

@Service
@Transactional
public class AmeAssessmentServicePart_2_impl implements AmeAssessmentServicePart_2 {

	@Autowired
	private PhysicalMeasurmentRepo physicalMeasurmentRepo;

	@Autowired
	private GeneralExaminationRepo generalExaminationRepo;
	
	@Autowired
	private AmeMasterStatusRepo ameMasterStatusRepo;
	
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
	private InvestigationRepo investigationRepo;
	
	@Autowired
   private AmeDeclarationRepository individualDetailsRepo;
	
	@Autowired
	private AmeAppointmentDetailsRepo ameAppointmentDetailsRepo;
	
	@Autowired
	private GynaeAndObsFemaleRepo gynaeAndObsFemaleRepo;
	
	@Autowired
	private RespiratorySystemRepo respiratorySystemRepo;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	

	
	@Autowired
	private AmePendingStatusRepository amePendingStatusRepository;
	
	
	

	@Override
	public Optional<PhysicalMeasurement> getPhysicalMeasurmentByAmeId(String ameId) {

		Optional<PhysicalMeasurement> optional = physicalMeasurmentRepo.findById(ameId);
		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
	}

	@Override
	public boolean savePhysicalMeasurement(PhysicalMeasurement physicalMeasurement,String ameId ) {
		  
		   String boardId=null;
		   String candidateForcePersonalId=null;
		   int year = Calendar.getInstance().get(Calendar.YEAR);
		if (physicalMeasurmentRepo.save(physicalMeasurement) != null) {
			 Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(physicalMeasurement.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		  
		     Optional<AmeApplicationFlowStatus> optional =ameApplicationFlowStatusRepo.findByAmeId(ameId);
		 
		   if(!optional.isEmpty()) {
			   boardId= optional.get().getBoardId();
			   candidateForcePersonalId=optional.get().getForcepersonalId();
		   }
		   
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setPhysicalMeasurement(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setBoardId(boardId);
		    	 ameMasterStatus.setYear(String.valueOf(year));
		    	 ameMasterStatus.setForcePersonalId(candidateForcePersonalId);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		   }else {
		    	 ameMasterStatus.setAmeId(physicalMeasurement.getAmeId());
		    	 ameMasterStatus.setPhysicalMeasurement(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setYear(String.valueOf(year));
		    	 ameMasterStatus.setBoardId(boardId);
		    	 ameMasterStatus.setForcePersonalId(candidateForcePersonalId);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
 			return true;
		}
		return false;
	}

	@Override
	public boolean updatePhysicalMeasurement(String ameId, PhysicalMeasurement physicalMeasurement) {
		 
		
		String boardId=null;
		String candidateForcePersonalId=null;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		physicalMeasurement.setAmeId(ameId);
		if (physicalMeasurmentRepo.save(physicalMeasurement) != null) {
			 Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(physicalMeasurement.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		  
		     Optional<AmeApplicationFlowStatus> optional =ameApplicationFlowStatusRepo.findByAmeId(ameId);
		 
		   if(!optional.isEmpty()) {
			   boardId= optional.get().getBoardId();
			   candidateForcePersonalId=optional.get().getForcepersonalId();
		   }
		   
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setPhysicalMeasurement(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setBoardId(boardId);
		    	 ameMasterStatus.setYear(String.valueOf(year));
		    	 ameMasterStatus.setForcePersonalId(candidateForcePersonalId);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		   }else {
		    	 ameMasterStatus.setAmeId(physicalMeasurement.getAmeId());
		    	 ameMasterStatus.setPhysicalMeasurement(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setYear(String.valueOf(year));
		    	 ameMasterStatus.setBoardId(boardId);
		    	 ameMasterStatus.setForcePersonalId(candidateForcePersonalId);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
			return true;
		}
		return false;
	}

	// -------------------Get-General-Examination-By-AmeId------------------------------//
	@Override
	public Optional<GeneralExamination> getGeneralExaminationByAmeId(String ameId) {

		Optional<GeneralExamination> optional = generalExaminationRepo.findById(ameId);

		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
	}

	// --------------------Save General
	// Examination-----------------------------------//
	@Override
	public boolean saveAndUpdateGeneralExamination(GeneralExamination generalExamination) {
		
		   if (generalExaminationRepo.save(generalExamination) != null ) {
			  Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(generalExamination.getAmeId());
			     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
			     
			     if(!getAmeStatusOptional.isEmpty()) {
			    	 ameMasterStatus=getAmeStatusOptional.get();
			    	 ameMasterStatus.setGeneralExamination(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatusRepo.save(ameMasterStatus);
				     	
			     }else {
			    	 ameMasterStatus.setGeneralExamination(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatusRepo.save(ameMasterStatus);
			     }
			     return true;
		}
		return false;
	}
	
	
	//------------------ Get Ame Master Status-----------------------//
	
	@Override
	public Optional<AmeMasterStatus> getAmeMasterStatusByAmeId(String ameId) {
		
	Optional<AmeMasterStatus>ameMasterStatusList =ameMasterStatusRepo.findByAmeId(ameId);
		
	if (ameMasterStatusList.isPresent()) {
		return Optional.ofNullable(ameMasterStatusList.get());
	}


		return Optional.empty();
	}
	
	
//-------------------Central Nervous System---------------------------------//

	@Override
	public Optional<CentralNervousSystem> centralNervousSystemByAmeId(String ameId) {
	
		Optional<CentralNervousSystem>centralNervousSystemList =centralNervousSystemRepo.findById(ameId);
		
		if (centralNervousSystemList.isPresent()) {
			return Optional.ofNullable(centralNervousSystemList.get());
		}


		return Optional.empty();
	}


//----------------------------------Reflexes---------------------------------------------//

	@Override
	public Optional<Reflexes> reflexesByAmeId(String ameId) {

		Optional<Reflexes> reflexesList = reflexesRepo.findById(ameId);

		if (reflexesList.isPresent()) {
			return Optional.ofNullable(reflexesList.get());
		}
		return Optional.empty();
	}

//-----------------------------------Cranial Nerves Meningeal Sign-----------------------------------//
	
	
	@Override
	public Optional<CranialNervesMeningealSign> cranialNervesMeningealSignByAmeId(String ameId) {
		
		Optional<CranialNervesMeningealSign> optional=cranialNervesMeningealSignRepo.findById(ameId);
		
		if(optional.isPresent()) {
			return Optional.ofNullable(optional.get());

		}
		return Optional.empty();
	}

	//-------------------------------Sensory System------------------------------------//

	@Override
	public Optional<SensorySystem> sensorySystemByAmeId(String ameId) {
     Optional<SensorySystem> sensorySystemOptional = sensoryRepo.findById(ameId);
		
		if(sensorySystemOptional.isPresent()) {
			return Optional.ofNullable(sensorySystemOptional.get());

		}
		return Optional.empty();
	}


	@Override
	public boolean saveAndUpdateCentralNervousSystemAndCranialNervesMeningealSign (CentralNervousSystem centralNervousSystem
	    	   ,CranialNervesMeningealSign cranialNervesMeningealSign) {
		
			if (centralNervousSystemRepo.save(centralNervousSystem)!= null &&cranialNervesMeningealSignRepo.save(cranialNervesMeningealSign)!=null) {

			 Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(centralNervousSystem.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setCns(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCranialnervesmeningealsign(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
				     
		     }else {
		    	 ameMasterStatus.setAmeId(centralNervousSystem.getAmeId());
		    	 ameMasterStatus.setCns(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCranialnervesmeningealsign(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
		     
			
			return true;
		}
		return false;
		
	}
	
	

	@Override
	public boolean saveAndUpdateReflexesAndSensory(Reflexes reflexes, SensorySystem sensorySystem) {
		
		
		if(reflexesRepo.save(reflexes)!=null&&sensoryRepo.save(sensorySystem)!=null) {
			
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(reflexes.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setReflexes(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setSensorySystem(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
			     
		     }else {
		    	
		    	 ameMasterStatus.setReflexes(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setSensorySystem(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
			
			
			return true;
		}
		return false;
	}

	

	
	
	
	
	
	
	
	
	
	@Override
	public Optional<Abdomen> abdomenByAmeId(String ameId) {
		Optional<Abdomen> abdomenOptional= abdomenRepo.findById(ameId);

		if(abdomenOptional.isPresent()) {
			return Optional.ofNullable(abdomenOptional.get());

		}
		return Optional.empty();
		
	}


	// -------------------Get-Investigation-Examination-By-AmeId------------------------------//
		@Override
		public Optional<Investigation> getInvestigationByAmeId(String ameId) {

			Optional<Investigation> optional = investigationRepo.findByAmeId(ameId);

			if (optional.isPresent()) {
				return Optional.ofNullable(optional.get());
			}

			return Optional.empty();
		}

		// --------------------Save investigation
		// Examination-----------------------------------//
		@Override
		public boolean saveInvestigation(Investigation investigation) {
			
			if (investigationRepo.save(investigation) != null) {
				

	        	 Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(investigation.getAmeId());
	    	     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
	    	     
	    	     if(!getAmeStatusOptional.isEmpty()) {
	    	    	 ameMasterStatus=getAmeStatusOptional.get();
	    	    	 ameMasterStatus.setInvestigation(CommonConstant.INSERT_STATUS_YES);
	    	    	 ameMasterStatusRepo.save(ameMasterStatus);
	    	    	
	    	     }else {
	    	    	 ameMasterStatus.setAmeId(investigation.getAmeId());
	    	    	 ameMasterStatus.setInvestigation(CommonConstant.INSERT_STATUS_YES);
	    	    	 ameMasterStatusRepo.save(ameMasterStatus);
	    	     }
	        	 
				return true;
			}
			return false;
		}
		// ---------------------------------Update-  investigation -------------------------------//

		@Override
		@Transactional
		public boolean updateInvestigation(String ameId, Investigation investigation) {
			

			Optional<Investigation> optional = investigationRepo.findByAmeId(ameId);
			Investigation investigationdata = new Investigation();

			investigationdata = optional.get();
			investigationdata.setHb(investigation.getHb());
			/* investigationdata.setRoutine_ex(investigation.getRoutine_ex()); */
			investigationdata.setMicroscope_ex(investigation.getMicroscope_ex());
			investigationdata.setUrine(investigation.getUrine());
			investigationdata.setBlood_sugar(investigation.getBlood_sugar());
			investigationdata.setBlood_sugar_pp(investigation.getBlood_sugar_pp());
			investigationdata.setGlycosylated_hb(investigation.getGlycosylated_hb());
			
			investigationdata.setS_cholesterol(investigation.getS_cholesterol());
			investigationdata.setHdl(investigation.getHdl());
			investigationdata.setRatio(investigation.getRatio());
			investigationdata.setTriglyceride(investigation.getTriglyceride());
			investigationdata.setLdl(investigation.getLdl());
			investigationdata.setvLDL(investigation.getvLDL());
			investigationdata.setBlood_urea(investigation.getBlood_urea());
			investigationdata.setS_creatinine(investigation.getS_creatinine());
			investigationdata.setUric_acid(investigation.getUric_acid());
			investigationdata.setxRay_chest(investigation.getxRay_chest());
			investigationdata.seteCG(investigation.geteCG());
			investigationdata.settMT(investigation.gettMT());
			investigationdata.setEcho_cardiography(investigation.getEcho_cardiography());
			
			

			if (investigationRepo.save(investigationdata) != null) {
				return true;
			}
			
			
			
			return false;
		}

		
		
		@Override
		public boolean updateAmeStatus(String ameId,String ameDataCheckFlagRemark ) {
			
		Optional<AmeDeclarationIndividualModel> optional=individualDetailsRepo.findByAmeId(ameId);
		
	    AmeDeclarationIndividualModel ameDeclarationIndividualModel=new AmeDeclarationIndividualModel();
	    ameDeclarationIndividualModel=optional.get();
	    ameDeclarationIndividualModel.setStatus("A");
		
	   Optional<AmeApplicationFlowStatus> optional2=ameApplicationFlowStatusRepo.findByAmeId(ameId);
	    
	   if(!optional2.isEmpty()) {
		   
		   AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
		   ameApplicationFlowStatus=optional2.get();
		   ameApplicationFlowStatus.setAmeDataCheckFlag(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DATA_CHECK_COMPLETE)));
		   ameApplicationFlowStatus.setAmeDataCheckFlagRemark(ameDataCheckFlagRemark);
		   
		   if(individualDetailsRepo.save(ameDeclarationIndividualModel)!=null&&ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus)!=null) {
				return true;
			}
		   else {
			   return false;
		   }
		   
	   }
	return false;
	
		}

		
		
	//===========================================GynaeAndObsFemale======================================//
		
		@Override
		public Optional<GynaeAndObsFemale> gynaeByAmeId(String ameId) {
			
			Optional<GynaeAndObsFemale> optional = gynaeAndObsFemaleRepo.findById(ameId);

			if (optional.isPresent()) {
				return Optional.ofNullable(optional.get());
			}

			return Optional.empty();
		}

		@Override
		public boolean saveAndUpdateGynaeAndObsFemale(GynaeAndObsFemale gynaeAndObsFemale) {
			String finalGynaecologyShape;
			if(gynaeAndObsFemale.getgFactorCategory().equals("G-1")) {
				
				gynaeAndObsFemale.setgFactorType(null);
				gynaeAndObsFemale.setgFactorDuration(null);
				finalGynaecologyShape=gynaeAndObsFemale.getgFactorCategory();
			}
			
			else {
				finalGynaecologyShape=gynaeAndObsFemale.getgFactorCategory()+"("+gynaeAndObsFemale.getgFactorDuration()+")";
			}
			
	
		if(	gynaeAndObsFemaleRepo.save(gynaeAndObsFemale)!=null){
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(gynaeAndObsFemale.getAmeId());
   	     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
   	     
   	     if(!getAmeStatusOptional.isEmpty()) {
   	    	 ameMasterStatus=getAmeStatusOptional.get();
   	    	ameMasterStatus.setGynaecologyObs(CommonConstant.INSERT_STATUS_YES);
	    	ameMasterStatus.setGynaecologyShape(finalGynaecologyShape);
   	    	 ameMasterStatusRepo.save(ameMasterStatus);
   	    	
   	     }else {
   	    	 ameMasterStatus.setAmeId(gynaeAndObsFemale.getAmeId());
   	    	 ameMasterStatus.setGynaecologyObs(CommonConstant.INSERT_STATUS_YES);
   	    	ameMasterStatus.setGynaecologyShape(finalGynaecologyShape);
   	    	 ameMasterStatusRepo.save(ameMasterStatus);
   	     }
			return true;
		}
			
			return false;
		}

		
		

		
		
		
		
		
		
		
		
		
		//===========================Respiratory System and Abdomen=======================================//
		
		@Override
		public Optional<RespiratorySystem> respiratorySystemByAmeId(String ameId) {
			
			Optional<RespiratorySystem> optional = respiratorySystemRepo.findById(ameId);

			if (optional.isPresent()) {
				return Optional.ofNullable(optional.get());
			}

			return Optional.empty();
		}

	public boolean saveAndUpdateRespiratoryAndAbdomen(RespiratorySystem respiratorySystem,Abdomen abdomen) {
		
    String finalAbdomenShape;
		if(abdomen.getpNo().equals("P-1")) {
			
			abdomen.setpDuration(null);
			abdomen.setpType(null);
			finalAbdomenShape=abdomen.getpNo();
		}
		
		else {
			finalAbdomenShape=abdomen.getpNo()+"("+abdomen.getpDuration()+")";
		}
		if(respiratorySystemRepo.save(respiratorySystem)!=null&& abdomenRepo.save(abdomen)!=null) {
		
		Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(respiratorySystem.getAmeId());
	     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
	     
	     if(!getAmeStatusOptional.isEmpty()) {
	    	 ameMasterStatus=getAmeStatusOptional.get();
	    	 ameMasterStatus.setRespiratorySystem(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setAbdomen(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setPhysicalShape(finalAbdomenShape);
	    	 ameMasterStatusRepo.save(ameMasterStatus);
	     }else {
	    	
	    	 ameMasterStatus.setRespiratorySystem(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setAbdomen(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setPhysicalShape(finalAbdomenShape);
	    	 ameMasterStatusRepo.save(ameMasterStatus);
	     }
	     return true;
	     
		}
		return false;
	}
	
	
	 
	 
		
	

		@Override
		public boolean getDataExistStatus() {
			
			long count=ameMasterStatusRepo.count();
			if(count>0) {
				return true;
			}
			
			return false;
		}
		
		
		//============get Appointment by AmeId=====================//
		@Override
		public Optional<AmeAppointmentDetails> ameAppointmentDetails(String ameId) {
			Optional<AmeAppointmentDetails> ameAppointmentDetailsOptional= ameAppointmentDetailsRepo.findByAmeId(ameId);
			AmeAppointmentDetails ameAppointmentDetails = null;
			if(ameAppointmentDetailsOptional.isEmpty()) {
			 return Optional.empty();
			}
			 return Optional.ofNullable(ameAppointmentDetailsOptional.get());
		
		}

		

		public boolean updateAMEStatusCNSRSCI(boolean result,String ameId) {
			
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(ameId);
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setReflexes(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setSensorySystem(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCranialnervesmeningealsign(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCns(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }else {
		    	 ameMasterStatus.setAmeId(ameId);
		    	 ameMasterStatus.setReflexes(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setSensorySystem(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCranialnervesmeningealsign(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setCns(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
			
			return false;
			
		}

	
		@Override
		public boolean saveAndUpdateGynaeAndObsFemaleDealingHand(GynaeAndObsFemale gynaeAndObsFemale) {
			if(	gynaeAndObsFemaleRepo.save(gynaeAndObsFemale)!=null){
				Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(gynaeAndObsFemale.getAmeId());
	   	      AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
	   	     
	   	     if(!getAmeStatusOptional.isEmpty()) {
	   	    	 ameMasterStatus=getAmeStatusOptional.get();
	   	    	ameMasterStatus.setGynaecologyObs(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
	   	    	
	   	     }else {
	   	    	 ameMasterStatus.setAmeId(gynaeAndObsFemale.getAmeId());
	   	    	 ameMasterStatus.setGynaecologyObs(CommonConstant.INSERT_STATUS_YES);
	   	    	 ameMasterStatusRepo.save(ameMasterStatus);
	   	     }
				return true;
			}
				
				return false;
		}

		@Override
		public boolean saveAndUpdateRespiratoryAndAbdomenDealingHand(RespiratorySystem respiratorySystem,
				Abdomen abdomen) {
			if(respiratorySystemRepo.save(respiratorySystem)!=null&& abdomenRepo.save(abdomen)!=null) {
				
				Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(respiratorySystem.getAmeId());
			     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
			     
			     if(!getAmeStatusOptional.isEmpty()) {
			    	 ameMasterStatus=getAmeStatusOptional.get();
			    	 ameMasterStatus.setRespiratorySystem(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatus.setAbdomen(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatusRepo.save(ameMasterStatus);
			     }else {
			    	
			    	 ameMasterStatus.setRespiratorySystem(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatus.setAbdomen(CommonConstant.INSERT_STATUS_YES);
			    	 ameMasterStatusRepo.save(ameMasterStatus);
			     }
			     return true;
			     
				}
				return false;
			
		}

		
		//====== 19-03-2025
		@Override
		public Optional<AmePendingStatus> getAmePendingStatusRemarkByAmeId(String ameId) {
			Optional<AmePendingStatus> optional = amePendingStatusRepository.findByAmeId(ameId);
			if (optional.isPresent()) {
				return Optional.ofNullable(optional.get());
			}

			return Optional.empty();
		}

		
	
}



