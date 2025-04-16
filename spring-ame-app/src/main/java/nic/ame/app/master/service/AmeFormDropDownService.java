package nic.ame.app.master.service;



import java.util.List;

import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.model.ColumnIdentifier;



public interface AmeFormDropDownService {

	//===============hearing Drop Down================//
	public ColumnIdentifier getHearingDropDownOption();
	
	//============general Examination Drop Down==========//
	public ColumnIdentifier getGeneralExamination();
	
	//===============RespiratoryDropDown===================//
	public ColumnIdentifier getRespiratoryDropDown();
	
    //================CNS Drop Down==========================//
	public ColumnIdentifier getCNSDropDown();
	
	//================Abdomen Drop Down=====================//
	public ColumnIdentifier getAbdomenDropDown();
	
	//==================Psychology Drop Down====================//
	public ColumnIdentifier getPsychologyDropDown();
	
	//==================EyeVision Drop Down=======================//
	public ColumnIdentifier getEyeVisionDropDown();
	
	public List<DropDownDto> getListOfDownCategory(AmeMasterStatus ameMasterStatus,String gender);
	
	
	
}
