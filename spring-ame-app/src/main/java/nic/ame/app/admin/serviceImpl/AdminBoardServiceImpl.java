package nic.ame.app.admin.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.dto.MedicalMemberMappedDto;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.AdminBoardService;
import nic.ame.app.admin.service.ForceUnitService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.constant.CommonConstant;

@Component
public class AdminBoardServiceImpl implements AdminBoardService {

	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;

	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	public int getBoardCountCreated(String loginForcePersonalId) {
		int count = medicalBoardRepo.getBoardCountByForcePersonalId(loginForcePersonalId);
		return count;
	}

	@Override
	public int getTotalMemberMapToAllBoardFormByAdmin(String loginForcePersonalId) {
		int mappedCount = medicalBoardIndividualMappingRepo.getTotalPersonalMappedToBoardCount(loginForcePersonalId);
		return mappedCount;
	}

	@Override
	public List<MedicalBoardMemberDto> getListOfAllMappedPersonalByCreatedByBoardPersonal(String loginForcePersonalId) {

		List<Object> list = medicalBoardIndividualMappingRepo.mappedMemberBoardMemberDtos(loginForcePersonalId);

		List<MedicalBoardMemberDto> medicalMemberDtos = new ArrayList<>();

		if (list.isEmpty()) {
			return medicalMemberDtos;
		}
		Iterator<Object> iterator = list.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			MedicalBoardMemberDto ajax = new MedicalBoardMemberDto();

			if (obj[0] != null) {
				ajax.setForceName(refForceService.getForceNameByForceId(Integer.parseInt(String.valueOf(obj[0]))));
			}
			if (obj[1] != null) {
				ajax.setBoardId(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				ajax.setBoardYear(String.valueOf(obj[2]).trim());
			}

			if (obj[3] != null) {
				ajax.setPlace(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				ajax.setUsedFor(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				ajax.setName(String.valueOf(obj[5]).trim());
			}
			if (obj[6] != null) {
				ajax.setDesignation(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				ajax.setIrlaNumber(String.valueOf(obj[7]).trim());
			}
			if (obj[8] != null) {
				if (Integer.parseInt(String.valueOf(obj[8])) ==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PENDING))) {
					ajax.setAppointMentStatus("Pending");
				}
				if (Integer.parseInt(String.valueOf(obj[8])) ==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED))) {
					ajax.setAppointMentStatus("Completed");
				}
			}
			medicalMemberDtos.add(ajax);

		}
		return medicalMemberDtos;

	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public List<MedicalMemberMappedDto> getAllMemberMappedToBoardByCreatedByForcePersonalWithAppointmentStatus(
			String loginForcePersonalId) {
		
		   List<Object> objects=medicalBoardIndividualMappingRepo.mappedMemberWithAppointmentProvided(loginForcePersonalId);
		   List<MedicalMemberMappedDto> medicalMemberMappedDtos=new ArrayList<>();

			if (objects.isEmpty()) {
				return medicalMemberMappedDtos;
			}
			Iterator<Object> iterator = objects.iterator();

			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				MedicalMemberMappedDto dto = new MedicalMemberMappedDto();

				if (obj[0] != null) {
					dto.setForceName(String.valueOf(obj[0]));
				}
				if(obj[1] != null) {
					dto.setBoardId(String.valueOf(obj[1]));
				}
				if(obj[2] != null) {
					dto.setIrlaNo(String.valueOf(obj[2]));
				}
				if(obj[3] != null) {
					dto.setName(String.valueOf(obj[3]));
				}
				if(obj[4] != null) {
					dto.setBoardPlace(unitRepository.findByUnitId(String.valueOf(obj[4])).get().getUnitFullName());
				}
				if(obj[5] != null) {
					if(String.valueOf(obj[5]).equals(1)) 
						dto.setAppointment("Completed");
					else
						dto.setAppointment("Pending");
						}
				medicalMemberMappedDtos.add(dto);
				
}
			
		   
		return medicalMemberMappedDtos;
	}

	
	
	
	

	@Override
	public List<MedicalMemberMappedDto> getAllMemberMappedToBoardByCreatedByForcePersonalWithApplicationStatusFlow(
			String loginForcePersonalId) {
		 List<Object> objects=ameApplicationFlowStatusRepo.statusOfMeppedMemberToBoard(loginForcePersonalId);
		 List<MedicalMemberMappedDto> medicalMemberMappedDtos=new ArrayList<>();

			if (objects.isEmpty()) {
				return medicalMemberMappedDtos;
			}
			Iterator<Object> iterator = objects.iterator();

			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				MedicalMemberMappedDto dto = new MedicalMemberMappedDto();

				if (obj[0] != null) {
					dto.setForceName(String.valueOf(obj[0]));
				}
				if(obj[1] != null) {
					dto.setBoardId(String.valueOf(obj[1]));
				}
				if(obj[2] != null) {
					dto.setIrlaNo(String.valueOf(obj[2]));
				}
				if(obj[3] != null) {
					dto.setName(String.valueOf(obj[3]));
				}
				if(obj[4] != null) {
					dto.setBoardPlace(String.valueOf(obj[4]));
				}
				if(obj[5] != null) {
					if(Integer.parseInt(String.valueOf(obj[5]))==1) 
						dto.setAppointment("Completed");
					else
						dto.setAppointment("Pending");
						}
				if(obj[6] != null) {
					if(Integer.parseInt(String.valueOf(obj[6]))==1) 
						dto.setDeclaration("Completed");
					else
						dto.setDeclaration("Pending");
						}
				if(obj[7] != null) {
					if(Integer.parseInt(String.valueOf(obj[7]))==1) 
						dto.setAmeFormCheck("Completed");
					else
						dto.setAmeFormCheck("Pending");
						}
				if(obj[8] != null) {
					if(Integer.parseInt(String.valueOf(obj[8]))==1) 
						dto.setAmeFormUpload("completed");
					else
						dto.setAmeFormUpload("Pending");
						}
				if(obj[9] != null) {
					if(Integer.parseInt(String.valueOf(obj[9]))==1) 
						dto.setAmeCheckUpList("Completed");
					else
						dto.setAmeCheckUpList("Pending");
						}
				medicalMemberMappedDtos.add(dto);
				
}
			
		   
		return medicalMemberMappedDtos;
	}

}
