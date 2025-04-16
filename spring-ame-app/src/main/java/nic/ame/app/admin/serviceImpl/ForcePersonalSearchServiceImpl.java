package nic.ame.app.admin.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.repository.ForcePersonalAdminRepository;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.ForcePersonalSearchService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.PersonnelOthers;
import nic.ame.app.master.ref.entity.ForcePersonalOthersRepo;
import nic.ame.app.test.controller.ForcePersonalDto;


@Service
@Transactional
public class ForcePersonalSearchServiceImpl implements ForcePersonalSearchService {
	
	@Autowired
	private ForcePersonalAdminRepository forcePersonalRepository;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	ForcePersonalAdminRepository forcePersonalAdminRepository;
	
	@Autowired
	private ForcePersonalOthersRepo forcePersonalOthersRepo;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	public List<ForcePersonnel> getAllForcepersonal() {
		// TODO Auto-generated method stub
		List <ForcePersonnel> individualList=forcePersonalRepository.getByForceByCadre();
		return individualList;
	}

	@Override
	public List<ForcePersonnel> findByKeyword(String keyword) {
		System.out.println("================================="+keyword+"================================================================");
		// TODO Auto-generated method stub
			List <ForcePersonnel> individualList=forcePersonalRepository.findByKeyword(keyword);
			if (!individualList.isEmpty()) {
				System.out.println("===============================present ===="+keyword+"================================================================");
				
				return individualList;
			}
			else {
				 individualList=forcePersonalRepository.findAll();
				return individualList;
				
			}
			
		
	}
	
	// Return Force Personal List for Board mapping with individual
	
	public List<ForcePersonnel> geAllForcePersonalByForceAndUnit(int force_no,String unitId){
		
		List <ForcePersonnel> individualList = forcePersonalRepository.getByForceandUnitId(force_no, unitId);
		return individualList;
		
	}

	@Override
	public List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonal(int force_no, String unitId,int gFlag,String boardYear) {
		String gFlagValue;
		if(gFlag==1) 
			gFlagValue="G";
		else
			gFlagValue="N";
				
			
		
		List<Object> forcePersonalsList=forcePersonalRepository.getForcePersonalListByForceNoAndUnit(force_no, unitId.trim(),gFlagValue,boardYear);
		
		List<ForcePersonalResponeseAjax> forcePersonnelDtosList=new ArrayList<>();
		
		if (forcePersonalsList.isEmpty()) {
			return forcePersonnelDtosList;
		}
		Iterator<Object> iterator = forcePersonalsList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonalResponeseAjax ajax=new ForcePersonalResponeseAjax();

			if (obj[0] != null) {
				ajax.setForcePersonalId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				ajax.setName(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				ajax.setForceName(refForceService.getForceNameByForceId( Integer.parseInt(String.valueOf(obj[3]).trim())));
				ajax.setForceNumber(Integer.parseInt(String.valueOf(obj[3]).trim()));
			}
			if(obj[4]!=null) {
				ajax.setUnitName(
				refForceService.getUnitNameByUnitId(Integer.parseInt(String.valueOf(obj[3]).trim()),String.valueOf(obj[4])));
			}
			if(obj[5]!=null) {
				ajax.setDesignation(rankRepo.findById(Integer.parseInt(String.valueOf(obj[5]))).get().getRankFullName());
			}
			if(obj[6]!=null) {
				if(String.valueOf(obj[6]).equalsIgnoreCase("M"))
					ajax.setGender("Male");
				else
					ajax.setGender("Female");

					
			}
			forcePersonnelDtosList.add(ajax);
		
		}
		
		
		return forcePersonnelDtosList;
	}

	@Override
	public List<ForcePersonalResponeseAjax> ListOfMedicalCadre(String unit, int forceNo) {
		
		List<Object> list=forcePersonalRepository.getListOfMedicalCadreForcePersonalByForceNoAndUnit(unit, forceNo);
		
		
		

		List<ForcePersonalResponeseAjax> forcePersonalList = new ArrayList<>();

		if (list.isEmpty()) {
			return forcePersonalList;
		}
		Iterator<Object> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonalResponeseAjax ajax=new ForcePersonalResponeseAjax();

			if (obj[0] != null) {
				ajax.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				ajax.setForceName(refForceService.getForceNameByForceId( Integer.parseInt(String.valueOf(obj[1]).trim())));
				ajax.setForceNumber(Integer.parseInt(String.valueOf(obj[1]).trim()));
			}
			if (obj[1] != null && obj[2] != null) {
				ajax.setUnitName(refForceService.getUnitNameByUnitId( Integer.parseInt(String.valueOf(obj[1]).trim()), String.valueOf(obj[2]).trim()));
				ajax.setUnitNumber( Integer.parseInt(String.valueOf(obj[2]).trim()));
			}
			if (obj[3] != null) {
				ajax.setDesignation(String.valueOf(obj[3]).trim());
			}
			
			if (obj[4] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				ajax.setForcePersonalId(String.valueOf(obj[5]).trim());
			}
			if(obj[6]!=null) {
				if(String.valueOf(obj[6]).equalsIgnoreCase("M"))
					ajax.setGender("Male");
				else
					ajax.setGender("Female");
 
					

			}
			forcePersonalList.add(ajax);
		}
		return forcePersonalList;
	}

	
	
	//===================================================================================//
	@Override
	public List<ForcePersonalResponeseAjax> ListOfMedicalCadreAttachUnit(String unit, int forceNo) {
		
		List<Object> list=forcePersonalRepository.getListOfMedicalCadreForcePersonalByForceNoAndAttachUnit(unit, forceNo);
		
		
		

		List<ForcePersonalResponeseAjax> forcePersonalList = new ArrayList<>();

		if (list.isEmpty()) {
			return forcePersonalList;
		}
		Iterator<Object> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonalResponeseAjax ajax=new ForcePersonalResponeseAjax();

			if (obj[0] != null) {
				ajax.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				ajax.setForceName(refForceService.getForceNameByForceId( Integer.parseInt(String.valueOf(obj[1]).trim())));
				ajax.setForceNumber(Integer.parseInt(String.valueOf(obj[1]).trim()));
			}
			if (obj[1] != null && obj[2] != null) {
				ajax.setUnitName(refForceService.getUnitNameByUnitId( Integer.parseInt(String.valueOf(obj[1]).trim()), String.valueOf(obj[2]).trim()));
				ajax.setUnitNumber( Integer.parseInt(String.valueOf(obj[2]).trim()));
			}
			if (obj[3] != null) {
				ajax.setDesignation(rankRepo.findById(Integer.parseInt(String.valueOf(obj[3]).trim())).get().getRankFullName());
			}
			
			if (obj[4] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				ajax.setForcePersonalId(String.valueOf(obj[5]).trim());
			}
			if (obj[6] != null) {
				ajax.setAttachUnit(unitRepository.findByUnitId(String.valueOf(obj[6]).trim()).get().getUnitFullName());
			}
			
			if (obj[7] != null) {
				if(String.valueOf(obj[7]).trim().equalsIgnoreCase("M"))
					ajax.setGender("Male");
				else
					ajax.setGender("Female");
				
			}
			
			
			
			forcePersonalList.add(ajax);
		}
		return forcePersonalList;
	}
	
	
	
	
	
	
	@Override
	public List<ForcePersonnel> getMappingListToboardForcePersonalForNonMedic(int force_no, String unitId) 
	{
      List<ForcePersonnel> forcePersonalsList=forcePersonalRepository.getForcePersonalListByForceNoAndUnitForNonMedic(force_no, unitId.trim());
		
      return forcePersonalsList;
	}

	@Override
	public List<ForcePersonnel> geAllForcePersonalByForceAndAttachUnit(int force_no, String AttachUnit) {
		 List<ForcePersonnel> attachUnitForcePersonal= forcePersonalRepository.findByForceNoAndAttachUnit(force_no,AttachUnit);
		return attachUnitForcePersonal;
	}

	@Override
	public List<ForcePersonnel> getMappingListToboardForcePersonalAttachUnit(int force_no, String attachUnit,int gFlag,String boardYear) {
		
		String gFlagValue;
		if(gFlag==1) 
			gFlagValue="G";
		else
			gFlagValue="N";
		List<ForcePersonnel> forcePersonalsList=
				forcePersonalRepository.getForcePersonalListByForceNoAndAttachUnit(force_no, attachUnit.trim(),gFlagValue,boardYear);
		
		return forcePersonalsList;
	}

	@Override
	public List<ForcePersonnel> getForcePersonelForMappingToReportingOfficer(int force_no, String unitId) {

		List <ForcePersonnel> individualList = forcePersonalRepository.getforcePersonnelListForReportingOfficerMapping(force_no, unitId);
		return individualList;
	}


	@Override
	public List<ForcePersonnel> getForcePersonelForMappingToReportingOfficerByAtachUnit(int force_no, String unitId) {

		List <ForcePersonnel> individualList = forcePersonalRepository.getforcePersonnelListForReportingOfficerMappingAttachUnit(force_no, unitId);
		return individualList;
	}

	@Override
	public List<ForcePersonnelDto> getForcePersonnelListByForceNoAndDesignation(Integer forceNo,Integer designation) {
		
		List<Object> forcePersonnelObjectList = forcePersonalAdminRepository.getForcePersonnelListByForceNoAndDesignation(forceNo,designation);
		List<ForcePersonnelDto> forcePersonnelDtoList= new ArrayList<>();
		
		Iterator<Object> iterator = forcePersonnelObjectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			ForcePersonnelDto forcePersonnel= new ForcePersonnelDto();
		   if (obj[0] != null) {
			   forcePersonnel.setForceId(String.valueOf(obj[0]).trim());
			}
		   if (obj[1] != null) {
			   forcePersonnel.setName(String.valueOf(obj[1]).trim());
			}
		   if (obj[2] != null) {
			   forcePersonnel.setDesignation(String.valueOf(obj[2]).trim());
			}
		   if (obj[3] != null) {
			   forcePersonnel.setUnit(String.valueOf(obj[3]).trim());
			   forcePersonnel.setUnitName (refForceService.getUnitNameByUnitId(forceNo,forcePersonnel.getUnit()));
			}
		   forcePersonnelDtoList.add(forcePersonnel);
		
		}
		
		return forcePersonnelDtoList;
	}

	@Override
	public List<PersonnelOthersDto> getPersonalOthersByForceNoAndDesignation(Integer forceNo, Integer designation) {
		List<Object> personnelOthersObjectList = forcePersonalOthersRepo
				.getPersonalOthersByForceNoAndDesignation(forceNo, designation);
		List<PersonnelOthersDto> personnelOthersDtoList = new ArrayList<>();

		Iterator<Object> iterator = personnelOthersObjectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			PersonnelOthersDto personnelOthers = new PersonnelOthersDto();
			if (obj[0] != null) {
				personnelOthers.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				personnelOthers.setDesignation(String.valueOf(obj[1]).trim());
			}
			personnelOthersDtoList.add(personnelOthers);
		}
		return personnelOthersDtoList;

	}

	@Override
	public List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonalAssignBoard(int force_no, String unitId) {

		
		List<Object> list = forcePersonalRepository.getListOfboardForcePersonalAssignBoard(force_no, unitId);
		List<ForcePersonalResponeseAjax> forcePersonalList = new ArrayList<>();

		
		if (list.isEmpty()) {
			return forcePersonalList;
		}

	
		for (Object result : list) {
			Object[] obj = (Object[]) result;
			ForcePersonalResponeseAjax ajax = new ForcePersonalResponeseAjax();

		
			if (obj[0] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[0]).trim()); 
			}
			if (obj[1] != null) {
				ajax.setName(String.valueOf(obj[1]).trim()); 
			}
			if (obj[2] != null) {
				ajax.setDesignation(String.valueOf(obj[2]).trim()); 
			}
	
				
			if (obj[3] != null) {
				String gender = String.valueOf(obj[3]).trim();
				ajax.setGender(gender.equalsIgnoreCase("M") ? "Male" : "Female"); // gender
			}
			if (obj[4] != null) {
				String forcePersonalId = String.valueOf(obj[4]).trim();
				ajax.setForcePersonalId(forcePersonalId); 
			}
			ajax.setUnitName(refForceService.getUnitNameByUnitId(force_no,
					unitId)); 

			
			forcePersonalList.add(ajax);
		}

		return forcePersonalList;
	}

	@Override
	public List<ForcePersonalResponeseAjax> getMappingListToboardForcePersonalAssignBoardforAttachUnit(int forceNo,
			String unitNo) {

		List<Object> list = forcePersonalRepository.getListOfboardForcePersonalAssignBoardAttachUnit(forceNo, unitNo);
		List<ForcePersonalResponeseAjax> forcePersonalList = new ArrayList<>();

		
		if (list.isEmpty()) {
			return forcePersonalList;
		}

	
		for (Object result : list) {
			Object[] obj = (Object[]) result;
			ForcePersonalResponeseAjax ajax = new ForcePersonalResponeseAjax();

		
			if (obj[0] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[0]).trim()); 
			}
			if (obj[1] != null) {
				ajax.setName(String.valueOf(obj[1]).trim()); 
			}
			if (obj[2] != null) {
				ajax.setDesignation(String.valueOf(obj[2]).trim()); 
			}
	
				
			if (obj[3] != null) {
				String gender = String.valueOf(obj[3]).trim();
				ajax.setGender(gender.equalsIgnoreCase("M") ? "Male" : "Female"); // gender
			}
			if (obj[4] != null) {
				String forcePersonalId = String.valueOf(obj[4]).trim();
				ajax.setForcePersonalId(forcePersonalId); 
			}
			ajax.setUnitName(refForceService.getUnitNameByUnitId(forceNo,
					unitNo)); 

			
			forcePersonalList.add(ajax);
		}

		return forcePersonalList;
	
	}

	@Override
	public int isMappedCountByIrlaNoAndBoardYear(String irlaNo, String boardYear, int isMappedValid) {
		return forcePersonalRepository.getIsMappedCountByIrlaNoAndBoardYearForForcePersonnel(irlaNo,boardYear,isMappedValid);
	}

}
