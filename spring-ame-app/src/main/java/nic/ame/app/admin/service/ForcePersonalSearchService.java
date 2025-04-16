package nic.ame.app.admin.service;

import java.util.List;

import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.model.ForcePersonnel;

public interface ForcePersonalSearchService {

	public List<ForcePersonnel> getAllForcepersonal();

	public List<ForcePersonnel> geAllForcePersonalByForceAndUnit(int force_no, String unitId);
	public List<ForcePersonnel> geAllForcePersonalByForceAndAttachUnit(int force_no, String unitId);

	/*
	 * TODO: Get Shop By keyword
	 */
	public List<ForcePersonnel> findByKeyword(String keyword);

	public List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonal(int force_no, String unitId,int gFlag,String year);
	public List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonalAssignBoard(int force_no, String unitId);
	
	public List<ForcePersonnel> getMappingListToboardForcePersonalAttachUnit(int force_no, String attachUnit,int gFlag,String boardYear);
	
	public List<ForcePersonnel> getMappingListToboardForcePersonalForNonMedic(int force_no, String unitId);
	
	public List<ForcePersonalResponeseAjax> ListOfMedicalCadre(String unit,int forceNo);
	public List<ForcePersonalResponeseAjax> ListOfMedicalCadreAttachUnit(String attachUnit,int forceNo);
	
	
	public List<ForcePersonnel> getForcePersonelForMappingToReportingOfficer(int force_no, String unitId);

	public List<ForcePersonnel> getForcePersonelForMappingToReportingOfficerByAtachUnit(int force_no, String unitId);
	
	public List<ForcePersonnelDto> getForcePersonnelListByForceNoAndDesignation(Integer forceNo,Integer designation);
	
	List<PersonnelOthersDto> getPersonalOthersByForceNoAndDesignation(Integer forceNo,Integer designation);

	 List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonalAssignBoardforAttachUnit(int int1,
			String unitNo);
	 
	 public int isMappedCountByIrlaNoAndBoardYear(String irlaNo,String boardYear,int isMappedValid);
	

}
