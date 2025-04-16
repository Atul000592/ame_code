package nic.ame.app.master.medical.service;


import java.util.List;
import java.util.Optional;

import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.AmePendingStatus;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;


public interface AmeAssessmentServicePart_2 {

	
	//----------------------------Physical Measurement------------------------------//
	
	public Optional<PhysicalMeasurement> getPhysicalMeasurmentByAmeId(String ameId);
	
	public boolean savePhysicalMeasurement(PhysicalMeasurement physicalMeasurement,String ameId);
    
	public boolean updatePhysicalMeasurement(String ameId,PhysicalMeasurement physicalMeasurement);
    
    
    //---------------------------General Examination---------------------------------//
  
	public Optional<GeneralExamination> getGeneralExaminationByAmeId(String ameId);
    
	public boolean saveAndUpdateGeneralExamination(GeneralExamination generalExamination);
    
    //------------------------------Central Nervous System--------------------------------//
  
	public Optional<CentralNervousSystem> centralNervousSystemByAmeId(String ameId);
    
	public Optional<CranialNervesMeningealSign> cranialNervesMeningealSignByAmeId(String ameId);
    
	public boolean saveAndUpdateCentralNervousSystemAndCranialNervesMeningealSign(
	    		CentralNervousSystem centralNervousSystem
	    	   ,CranialNervesMeningealSign cranialNervesMeningealSign);
	 
     //-------------------------------Reflexes and Sensory System---------------------------------------------//
   
	public Optional<Reflexes> reflexesByAmeId(String ameId);
    
	public Optional<SensorySystem> sensorySystemByAmeId(String ameId);
	   
    public boolean saveAndUpdateReflexesAndSensory(Reflexes reflexes,SensorySystem sensorySystem);
	   
	
	//--------------------------------Cranial Nerves Meningeal Sign------------------------//
    
    
      
    
    //------------------------------Ame Master Status-------------------------------//
  
    public Optional<AmeMasterStatus> getAmeMasterStatusByAmeId(String ameId);
    
    
     
      
    public boolean updateAMEStatusCNSRSCI(boolean result,String ameId);
    	
    	
    
    
   
    
    //---------------------------
   
    public Optional<Investigation> getInvestigationByAmeId(String ameId);
	
    public boolean saveInvestigation(Investigation investigation);
    
	public boolean updateInvestigation(String ameId,Investigation investigation);

    //===update ame status to 'A'-=============================//
    
    public boolean updateAmeStatus(String ameId,String ameDataCheckFlagRemark);
    
    public Optional<AmeAppointmentDetails> ameAppointmentDetails(String ameId);
    
    //===========================data exist in AmeStatus Table=====================//
    
    public boolean getDataExistStatus();
    
   
   
//----------------------------------------------Abdomen---------------------------------------------------------//
    
    public Optional<Abdomen> abdomenByAmeId(String ameId);
    
    public Optional<RespiratorySystem> respiratorySystemByAmeId(String ameId);
    
    public boolean saveAndUpdateRespiratoryAndAbdomen(RespiratorySystem respiratorySystem,Abdomen abdomen);
   
    
    
    //=============================Gynae-Female=========================================//
    
    public Optional<GynaeAndObsFemale> gynaeByAmeId(String ameId);
    
    public boolean saveAndUpdateGynaeAndObsFemale(GynaeAndObsFemale gynaeAndObsFemale);
    
    public boolean saveAndUpdateGynaeAndObsFemaleDealingHand(GynaeAndObsFemale gynaeAndObsFemale);
    
    
    //------------------------save and update respiratory and abdomen--------------------//
    
    
   public boolean saveAndUpdateRespiratoryAndAbdomenDealingHand(RespiratorySystem respiratorySystem,Abdomen abdomen);
    

   //========= AME Pendng Status Remark================
   public Optional<AmePendingStatus> getAmePendingStatusRemarkByAmeId(String ameId);
    
  }
