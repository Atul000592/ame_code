package nic.ame.app.master.medical.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import nic.ame.app.board.member.repository.AmeMasterStatusRepo;
import nic.ame.app.board.member.repository.AppendagesRepo;
import nic.ame.app.board.member.repository.EyeFactorRepo;
import nic.ame.app.board.member.repository.HearingRepo;
import nic.ame.app.board.member.repository.PsychologicalAssessmentAsLaidDownRepo;
import nic.ame.app.board.member.repository.PhysicalMeasurmentRepo;
import nic.ame.app.board.member.repository.RefAppendagesTypeRepo;
import nic.ame.app.board.member.repository.RefEyeFactorRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.dto.PlaceAndDateDtoRepo;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;

import nic.ame.app.master.model.ForcePersonnel;

import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.constant.CommonConstant;

@Service
@Transactional
public class AmeAssessmentServicePart_1_impl implements AmeAssesmentServicePart_1 {

	@Autowired
	private PsychologicalAssessmentAsLaidDownRepo assessmentAsLaidDownRepo;

	@Autowired
	private PhysicalMeasurmentRepo physicalMeasurmentRepo;
	
	@Autowired
	private HearingRepo hearingRepo;
	@Autowired
	private RefAppendagesTypeRepo refAppendagesTypeRepo;
	@Autowired
	private AppendagesRepo appendagesRepo;
	
	
	@Autowired
	private RefEyeFactorRepo refEyeFactorRepo;
	@Autowired
	private EyeFactorRepo eyeFactorRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeMasterStatusRepo ameMasterStatusRepo;
	
	@Autowired
	private PlaceAndDateDtoRepo ameDeclarationIndividualModelRepoUser;

	

	

	@Override
	public Optional<PsychologicalAssessmentAsLaidDown> assessmentAsLaidDown(String ameId) {
		PsychologicalAssessmentAsLaidDown assessmentAsLaidDown = new PsychologicalAssessmentAsLaidDown();

		Optional<PsychologicalAssessmentAsLaidDown> optional = assessmentAsLaidDownRepo.findById(ameId);
		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.ofNullable(assessmentAsLaidDown);
	}

	@Override
	public PhysicalMeasurement savePhysicalMeasurement(String ameId, PhysicalMeasurement physicalMeasurement) {

		Optional<PhysicalMeasurement> optional = physicalMeasurmentRepo.findById(ameId);
		if (!optional.isPresent()) {
			physicalMeasurmentRepo.save(physicalMeasurement);
			return physicalMeasurement;
		}
		return null;
	}



	@Override
	public String getAmeIdofForcePersonal(String forcepersonalId) {

		return null;
	}

	@Override
	public boolean saveAndUpdatePsychologicalAssessment(PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown) {
		String finalShape;
		if(phychologicalAssessmentAsLaidDown.getCategory().contentEquals("S-1")) {
			phychologicalAssessmentAsLaidDown.setType(null);
			phychologicalAssessmentAsLaidDown.setDuration(null);
			finalShape=	phychologicalAssessmentAsLaidDown.getCategory();
		}
		
		else{
			finalShape=	phychologicalAssessmentAsLaidDown.getCategory()+"/("+phychologicalAssessmentAsLaidDown.getDuration()+")";
			
		}
		
		
		if(assessmentAsLaidDownRepo.save(phychologicalAssessmentAsLaidDown)!=null) {
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(phychologicalAssessmentAsLaidDown.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setPhychologicalAssessmentAsLaidDown(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setPsycological_shape(finalShape);
                 ameMasterStatusRepo.save(ameMasterStatus);
		     }
		     
		     else {
		    	 ameMasterStatus.setAmeId(phychologicalAssessmentAsLaidDown.getAmeId());
		    	 ameMasterStatus.setPhychologicalAssessmentAsLaidDown(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setPsycological_shape(finalShape);
		     	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
		}

		return false;
	}
// ----------------------------save and Update Psychological data Dealing Hand
	@Override
	public boolean saveAndUpdatePsychologicalAssessmentDealingHand(PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown) {
		
		
		
		if(assessmentAsLaidDownRepo.save(phychologicalAssessmentAsLaidDown)!=null) {
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(phychologicalAssessmentAsLaidDown.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setPhychologicalAssessmentAsLaidDown(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
		     
		     else {
		    	 ameMasterStatus.setAmeId(phychologicalAssessmentAsLaidDown.getAmeId());
		    	 ameMasterStatus.setPhychologicalAssessmentAsLaidDown(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		     }
		}

		return false;
	}
	
		
	// ======================Hearing all Code goes Below==========================================//
	


	
	@Override
	public Optional<Hearing> getHearing(String ameId) {
		Optional<Hearing> optional = hearingRepo.findById(ameId);
		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean saveAndUpdateHearing(Hearing hearing) {
		String finalHearingShape;
		
		if(hearing.gethNo().equalsIgnoreCase("H-1")){
			hearing.sethType(null);
			hearing.sethDuration(null);
			finalHearingShape=hearing.gethNo();
		}
		
		else {
			finalHearingShape=hearing.gethNo()+"("+hearing.gethDuration()+")";
			}
		
		hearingRepo.save(hearing);
		if(hearingRepo.save(hearing)!=null) {
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(hearing.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setHearing(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setHearingShape(finalHearingShape);
		    	 
		    	 
     	    	 ameMasterStatusRepo.save(ameMasterStatus);
     	    	 return true;
		     }else {
		    	 
		    	 ameMasterStatus.setAmeId(hearing.getAmeId());
		    	 ameMasterStatus.setHearing(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatus.setHearingShape(finalHearingShape);
		    	 
     	    	 ameMasterStatusRepo.save(ameMasterStatus);
     	    	 return true;
		     }
			
		};
		return false;
	}

// save and Update hearing Dealing Hand
	@Override
	public boolean saveAndUpdateHearingDealingHand(Hearing hearing) {
		
		hearingRepo.save(hearing);
		if(hearingRepo.save(hearing)!=null) {
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(hearing.getAmeId());
		    AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setHearing(CommonConstant.INSERT_STATUS_YES);
		         ameMasterStatusRepo.save(ameMasterStatus);
     	    	 return true;
		     }else {
		    	 
		    	 ameMasterStatus.setAmeId(hearing.getAmeId());
		    	 ameMasterStatus.setHearing(CommonConstant.INSERT_STATUS_YES);
		    	 ameMasterStatusRepo.save(ameMasterStatus);
     	    	 return true;
		     }
			
		}
		return false;
	}

// Appendages all Code goes Below

	@Override
	public List<RefAppendagesType> getAppendagesFactorList() {
		// TODO Auto-generated method stub
		List<RefAppendagesType> refAppendagesTypeList = refAppendagesTypeRepo.findAll();
		return refAppendagesTypeList;
	}
	
	@Override
	public Optional<Appendages> getAppendages(String ameId) {
		Optional<Appendages> optional = appendagesRepo.findById(ameId);
		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
		
		
	}
	
//=======================================New saveAppendages Method By Rajat==========================================================//
	@Override
	public boolean saveAndUpdateAppendages(Appendages appendages) {
		String finalUpperLimbShape;
		String finalLowerLimbShape;
		String finalSpineShape;
		if(appendages.getUpperLimbShape().equalsIgnoreCase("A-1(U)")){
			appendages.setUpperLimbType(null);
			appendages.setUpperLimbDuration(null);
			finalUpperLimbShape=appendages.getUpperLimbShape();
		} 
		else {
			finalUpperLimbShape=appendages.getUpperLimbShape()+"("+appendages.getUpperLimbDuration()+")";
			
		}
		if(appendages.getLowerLimbShape().equalsIgnoreCase("A-1(L)")){
			appendages.setLowerLimbType(null);
			appendages.setLowerLimbDuration(null);
			finalLowerLimbShape=appendages.getLowerLimbShape();
		}
		
		else {
			finalLowerLimbShape=appendages.getLowerLimbShape()+"("+appendages.getLowerLimbDuration()+")";
			
		}
		if(appendages.getSpineShape().equalsIgnoreCase("A-1(S)")){
			appendages.setSpineType(null);
			appendages.setSpineDuration(null);
			finalSpineShape=appendages.getSpineShape();
		}
		else {
			finalSpineShape=appendages.getSpineShape()+"("+appendages.getSpineDuration()+")";
				
		}
		
		appendagesRepo.save(appendages);
		if (appendagesRepo.save(appendages) != null) {
			Optional<AmeMasterStatus> getAmeStatusOptional = ameMasterStatusRepo.findByAmeId(appendages.getAmeId());
			AmeMasterStatus ameMasterStatus = new AmeMasterStatus();

			if (!getAmeStatusOptional.isEmpty()) {
				ameMasterStatus = getAmeStatusOptional.get();
                ameMasterStatus.setAppendages(CommonConstant.INSERT_STATUS_YES);
                ameMasterStatus.setLowerLimbShape(finalLowerLimbShape);
                ameMasterStatus.setUpperLimbShape(finalUpperLimbShape);
                ameMasterStatus.setSpineShape(finalSpineShape);
				ameMasterStatusRepo.save(ameMasterStatus);

			} else {

				ameMasterStatus.setAmeId(appendages.getAmeId());
				ameMasterStatus.setAppendages(CommonConstant.INSERT_STATUS_YES);
				 ameMasterStatus.setLowerLimbShape(finalLowerLimbShape);
	                ameMasterStatus.setUpperLimbShape(finalUpperLimbShape);
	                ameMasterStatus.setSpineShape(finalSpineShape);
				ameMasterStatusRepo.save(ameMasterStatus);
			}
		}
		return false;
	}
//=================================================================================================================================//

	
//========================================= Update Appendages data Dealing Hand====================================================//
	@Override
	public boolean saveAndUpdateAppendagesDealingHand(Appendages appendages) {
		
		if ((appendagesRepo.save(appendages)) != null) {
			Optional<AmeMasterStatus> getAmeStatusOptional = ameMasterStatusRepo.findByAmeId(appendages.getAmeId());
			AmeMasterStatus ameMasterStatus = new AmeMasterStatus();
			if (!getAmeStatusOptional.isEmpty()) {
				ameMasterStatus = getAmeStatusOptional.get();
				ameMasterStatus.setAppendages(CommonConstant.INSERT_STATUS_YES);
				ameMasterStatusRepo.save(ameMasterStatus);
			} else {
				ameMasterStatus.setAmeId(appendages.getAmeId());
				ameMasterStatus.setAppendages(CommonConstant.INSERT_STATUS_YES);
				ameMasterStatusRepo.save(ameMasterStatus);
			}
        return true;
	   }	
		
			
		
		return false;
	}
//==================================================================================================================================//	


//===========================================================Eye===================================================================//
	


	@Override
	public Optional<EyeFactor> getEyeFactor(String ameId) {
		Optional<EyeFactor> optional = eyeFactorRepo.findById(ameId);
		if (optional.isPresent()) {
			return Optional.ofNullable(optional.get());
		}

		return Optional.empty();
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean saveAndUpdateEyeFactor(EyeFactor eyeFactor) {
    String finalRightEyeShape;
   
    if(eyeFactor.getEyeCategory().equalsIgnoreCase("E-1")) {
    	eyeFactor.setEyeType(null);
    	eyeFactor.setEyeDuration(null);
    	finalRightEyeShape=eyeFactor.getEyeCategory();
    }
    
    else{
    	
    	finalRightEyeShape=eyeFactor.getEyeCategory()+"("+eyeFactor.getEyeDuration()+")";
    }

   
	if(eyeFactorRepo.save(eyeFactor)!=null) {
		Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(eyeFactor.getAmeId());
	     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
	     
	     if(!getAmeStatusOptional.isEmpty()) {
	    	 ameMasterStatus=getAmeStatusOptional.get();
	    	 ameMasterStatus.setEyefactor(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setEyeShape(finalRightEyeShape);
//	    	 ameMasterStatus.setLeftEyeShape(finalLeftEyeShape);
	    	 
	    	 
	    	 ameMasterStatusRepo.save(ameMasterStatus);
	     }else {
	    	 ameMasterStatus.setAmeId(eyeFactor.getAmeId());
	    	 ameMasterStatus.setEyefactor(CommonConstant.INSERT_STATUS_YES);
	    	 ameMasterStatus.setEyeShape(finalRightEyeShape);
//	    	 ameMasterStatus.setLeftEyeShape(finalLeftEyeShape);
	    	 ameMasterStatusRepo.save(ameMasterStatus);
	    	 
	     }
	     return true;
	}
		
	
		
		return false;
	}
	
	//--------------------------Update Eye Factor data Dealing Hand-------------------------//
	@Override
	public boolean saveAndUpdateEyeFactorDealingHand(EyeFactor eyeFactor) {
		
      
		if(eyeFactorRepo.save(eyeFactor)!=null) {
			Optional<AmeMasterStatus> getAmeStatusOptional=ameMasterStatusRepo.findByAmeId(eyeFactor.getAmeId());
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
		     if(!getAmeStatusOptional.isEmpty()) {
		    	 ameMasterStatus=getAmeStatusOptional.get();
		    	 ameMasterStatus.setEyefactor(CommonConstant.INSERT_STATUS_YES);
		      	 ameMasterStatusRepo.save(ameMasterStatus);
		     }else {
		    	 ameMasterStatus.setAmeId(eyeFactor.getAmeId());
		    	 ameMasterStatus.setEyefactor(CommonConstant.INSERT_STATUS_YES);
		    	
		    	 ameMasterStatusRepo.save(ameMasterStatus);
		    	 
		     }
		     return true;
		   }
		return false;
	}
	
	

	@Override
	public ForcePersonnel getforceForcePersonal(String forcePersonalId) {
		Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(forcePersonalId);
		ForcePersonnel forcePersonnel=new ForcePersonnel();
		if(optional.isEmpty()) {
			return forcePersonnel;
		}
		forcePersonnel=optional.get();
		return forcePersonnel;
	}

	@Override
	public ForcePersonnel getforceForcePersonalBasicInfo(String forcePersonalId) {
		// TODO Auto-generated method stub
		return null;
	}

	
      public PlaceAndDateDto getAmeDeclarationIndividualModel(String ameId) {
    	   PlaceAndDateDto placeAndDateDto=new PlaceAndDateDto();
    	  Optional<PlaceAndDateDto> optional=ameDeclarationIndividualModelRepoUser.getPlaceAndDateDto(ameId);
    	  if(!optional.isEmpty()) {
    		  placeAndDateDto=optional.get();
    		 
    		  return placeAndDateDto;
    	  }
    	  return placeAndDateDto;
      }

	

	

}
