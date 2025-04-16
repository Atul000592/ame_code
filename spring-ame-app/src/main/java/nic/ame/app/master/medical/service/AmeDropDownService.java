package nic.ame.app.master.medical.service;

import java.util.List;


import nic.ame.app.master.medical.model.EyeVisonScaleMaster;
import nic.ame.app.master.medical.model.NearVisionScaleMaster;
import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.RefAppetite;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefGFactor;
import nic.ame.app.master.ref.entity.RefPsychologicalDuration;
import nic.ame.app.master.ref.entity.RefPsychologicalStatus;
import nic.ame.app.master.ref.entity.RefPsychologicalType;
import nic.ame.app.master.ref.entity.RefSleep;

public interface AmeDropDownService {

	
	
	
	
	
	   //====================RefAppendages========================//
	
	    public List<RefAppendagesType> getRefAppendagesUpperTypes();
	    public List<RefAppendagesType> getRefAppendagesLowertype();
	    public List<RefAppendagesType> getRefAppendagesCategory(String type);
	  
	    
	    
	    
	    
	    //===================EyeStatus============================//
	    

	    public List<NearVisionScaleMaster> getNearVisionScaleMasters();
	    public List<EyeVisonScaleMaster> getEyeVisonScaleMasters();
	    
	    
         //============================RefHearing=========================//
	    
	  
	    
	    
	    
	    //=======================RefPsychologicalType===========================//
	
	    
	    
	    
	    
	    
	    
	    //=================================RefPhysicalStatus===========================//
	    
	    //==============================gyno====================================//
	  
	  
	    public List<RefGFactor> getGFactors();
	    
	    //===============================CategoryStatus=====================================//
	    public List<CategoryTypeMaster> categoryStatusTypes();
	    public RefDropDownRange getDropDownRanges(String name);
	    
	    
	    //====================sleep==============================//
	    
	    public List<RefSleep> refSleepsList();
	
	    
	    //=================appetite========================//
	    public List<RefAppetite> refAppetitesList();
	    
	    
	
	    
}
