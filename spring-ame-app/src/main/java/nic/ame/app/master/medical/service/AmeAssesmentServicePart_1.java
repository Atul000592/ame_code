package nic.ame.app.master.medical.service;




import java.util.List;
import java.util.Optional;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.RefEyeFactor;
import nic.ame.app.master.ref.entity.RefHearingType;



public interface AmeAssesmentServicePart_1 {

	
	public String getAmeIdofForcePersonal(String forcepersonalId);
	
	public Optional<PsychologicalAssessmentAsLaidDown> assessmentAsLaidDown(String ameId);
	
	public PhysicalMeasurement savePhysicalMeasurement(String ameId,PhysicalMeasurement physicalMeasurement);
	
	//---------------- Hearing --------------------//
	
	public Optional<Hearing> getHearing(String ameId);
	
	public boolean saveAndUpdateHearing(Hearing eyeFactor);
	
	//--------------- Appendages ---------------------//
	
	public List<RefAppendagesType>  getAppendagesFactorList();
	
	public Optional<Appendages> getAppendages(String ameId);
	
	public boolean saveAndUpdateAppendages(Appendages appendages);
	
	//------------------ Eye ----------------------//
	
	public Optional<EyeFactor> getEyeFactor(String ameId);
	
	public boolean saveAndUpdateEyeFactor(EyeFactor eyeFactor);
	
	public boolean saveAndUpdatePsychologicalAssessment(PsychologicalAssessmentAsLaidDown asLaidDown);
	
	 //--------------Force personal Information---------------//
	
	public ForcePersonnel getforceForcePersonal(String forcePersonalId);
	
    //--------------Force Personal basic information--------------//
	
	public ForcePersonnel getforceForcePersonalBasicInfo(String forcePersonalId);
	
	public PlaceAndDateDto getAmeDeclarationIndividualModel(String ameId);
	
	//--------------Dealing Hand - save and update----------------//
	
	public boolean saveAndUpdatePsychologicalAssessmentDealingHand(PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown);
	
	public boolean saveAndUpdateHearingDealingHand(Hearing eyeFactor);
	
	public boolean saveAndUpdateAppendagesDealingHand(Appendages appendages);
	
	public boolean saveAndUpdateEyeFactorDealingHand(EyeFactor eyeFactor);
	
	
}
