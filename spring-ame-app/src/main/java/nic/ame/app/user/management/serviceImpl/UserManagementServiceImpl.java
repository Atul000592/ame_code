package nic.ame.app.user.management.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.model.Rank;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.RankMasterRepository;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.AppointmentListDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.MapIndividualAndUnitToReporting;
import nic.ame.app.master.model.ReportingOfficer;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.MapIndividualAndUnitToReportingRepository;
import nic.ame.app.master.repository.ReportingOfficerRepository;
import nic.ame.app.master.repository.SHARandomKeyHolderRepository;
import nic.ame.app.master.repository.UserRoleRepo;
import nic.ame.app.user.dto.UserDeclarationDto;
import nic.ame.app.user.management.service.UserManagementService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.UniqueIdGeneratorForReportingOfficer;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private UserRoleRepo roleRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private MapIndividualAndUnitToReportingRepository mapIndividualAndUnitToReportingRepository;
	
	
	@Autowired
	private ReportingOfficerRepository reportingOfficerRepository;

	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private RankMasterRepository rankMasterRepository;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired 
	private UnitRepository unitRepository;

	@Override
	public UserRole getUserRoleByUserRoleId(int userRoleId) {
		Optional<UserRole> optional = roleRepo.findbyUserRoleById(userRoleId);
		UserRole role = new UserRole();
		if (!optional.isEmpty()) {
			return role = optional.get();
		}
		return role;
	}

	@Override
	public String updateUserRoleByUserRoleId(UserRole userRole) {
		roleRepo.save(userRole);
		return "save";
	}

	@Override
	public List<UserDeclarationDto> getUserDeclarationStatusByBoard(String forcePersonalId) {
		List<UserDeclarationDto> declarationDtosList = new ArrayList<>();

		List<Object> objects = medicalBoardIndividualMappingRepo
				.getForcePersonnelAppointmentDetailsByForcePersonnelId(forcePersonalId.trim());

		if (objects.isEmpty()) {
			return declarationDtosList;
		}
		Iterator<Object> iterator = objects.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserDeclarationDto declarationDto = new UserDeclarationDto();

			if (obj[0] != null) {
				declarationDto.setBoardId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				declarationDto.setUsedFor(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				declarationDto.setYear(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				if (Integer.parseInt(String.valueOf(obj[3]).trim()) == 1) {
					declarationDto.setAppointmentStatusCode(Integer.parseInt(String.valueOf(obj[3])));

					declarationDto.setAppointmentStatus("Y");
				} 
				else if(Integer.parseInt(String.valueOf(obj[3]).trim()) == 5) {
					declarationDto.setAppointmentStatusCode(Integer.parseInt(String.valueOf(obj[3])));

					declarationDto.setAppointmentStatus("N");
				}else if(Integer.parseInt(String.valueOf(obj[3]).trim()) == 6)
				{
					declarationDto.setAppointmentStatusCode(Integer.parseInt(String.valueOf(obj[3])));

					declarationDto.setAppointmentStatus("R");
				}
			}
			if (obj[4] != null) {
				if (Integer.parseInt(String.valueOf(obj[4]).trim()) == 1) {
					declarationDto.setDeclarationStatus("Y");
				} else {
					declarationDto.setDeclarationStatus("N");
				}
			}
			if(obj[5]!=null) {
				declarationDto.setForcePersonalId(String.valueOf(obj[5]).trim());
			}
			if(obj[6]!=null) {
				declarationDto.setFromDate((Date)obj[6]);
			}
			if(obj[7]!=null) {
				declarationDto.setToDate((Date)obj[7]);
			}
			
			
			declarationDtosList.add(declarationDto);

		}
		
		return declarationDtosList;

	}

	@Override
	public List<UserRoleDto> getUserRoleByForcePersonalId(String LoginForcePersonalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean saveIndividualToReporting(List<String> forcePersonelId, String controllingId ,HttpSession httpSession ,HttpServletRequest request) {
		List<ForcePersonnel> list = new ArrayList<>();
		for(String id:forcePersonelId) {
			ForcePersonnel forcePersonal = new ForcePersonnel();
		 forcePersonal=	this.forcePersonnelRepository.findByForceId(id)
				.orElseThrow(()->new RuntimeException("Data Error ForcePersonel is Not Present"+id));	
		list.add(forcePersonal);
		}
		
		for(ForcePersonnel forcePersonal:list) {
			UserManagementServiceImpl.save(forcePersonal, mapIndividualAndUnitToReportingRepository,
					reportingOfficerRepository, httpSession, controllingId,request);
		}

		return true;
		
		
	}

	
	public static ForcePersonnel save(ForcePersonnel forcePersonnel,
			MapIndividualAndUnitToReportingRepository mapIndividualAndUnitToReportingRepository,
			ReportingOfficerRepository reportingOfficerRepository,HttpSession httpSession,String reportingOfficerId,HttpServletRequest request) {
		
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String reportingOfficerTransactionId=null;
	    Optional<ReportingOfficer>  report=reportingOfficerRepository
	    		.findByForceNoAndUnitAndStatus(forcePersonnel.getForceNo(),forcePersonnel.getUnit(),CommonConstant.ACTIVE_FLAG_YES);
		if(report.isPresent()) {
			reportingOfficerTransactionId=report.get().getReportingUniqueId();
		}else {
			reportingOfficerTransactionId=UniqueIdGeneratorForReportingOfficer.genrateReportingOfficerUniqueId(forcePersonnel.getForceNo(),forcePersonnel.getUnit());
		}
		
		MapIndividualAndUnitToReporting mapIndividualAndUnitToReporting = new MapIndividualAndUnitToReporting();
		mapIndividualAndUnitToReporting.setCandidateForcePersonalId(forcePersonnel.getForcePersonalId());
		mapIndividualAndUnitToReporting.setCandidateIrlaNo(forcePersonnel.getForceId());
		mapIndividualAndUnitToReporting.setReportingOfficerUniqueId(reportingOfficerTransactionId);
		mapIndividualAndUnitToReporting.setCreatedBy(loginForcePersonalId);
		mapIndividualAndUnitToReporting.setCreatedOn(new Date());
		mapIndividualAndUnitToReporting.setReportingForcePersonalId(reportingOfficerId);
		mapIndividualAndUnitToReporting.setStatus(CommonConstant.ACTIVE_FLAG_YES);
		MapIndividualAndUnitToReporting mapIndividualAndUnitToReportingsaved=
				mapIndividualAndUnitToReportingRepository.save(mapIndividualAndUnitToReporting);
		
		if(!mapIndividualAndUnitToReportingsaved.equals(null)) {
			Optional<ReportingOfficer> reportingOfficer=reportingOfficerRepository.
					findByReportingUniqueId(mapIndividualAndUnitToReportingsaved.getReportingOfficerUniqueId());
			
			if(reportingOfficer.isEmpty()) {
				ReportingOfficer reportOfficers = new ReportingOfficer();
				reportOfficers.setForceNo(forcePersonnel.getForceNo());
				reportOfficers.setReportingUniqueId(reportingOfficerTransactionId);
				reportOfficers.setStatus(CommonConstant.ACTIVE_FLAG_YES);
				reportOfficers.setCreatedBy(loginForcePersonalId);
				reportOfficers.setCreatedOn(new Date());
				reportOfficers.setUnit(Integer.parseInt(forcePersonnel.getUnit()) );
				reportOfficers.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
				reportingOfficerRepository.save(reportOfficers);
	
			}else {
				
			}
			
			return forcePersonnel;
		}else {
			return null;

		}
		

	}

	@Override
	public List<ForcePersonnelDto> list(String reportingForcePersonalId) {
		
			List<MapIndividualAndUnitToReporting> mapIndividualAndUnitToReporting = this.mapIndividualAndUnitToReportingRepository.findByReportingForcePersionIdId(reportingForcePersonalId);
			List<ForcePersonnelDto> forcePersonals = new ArrayList<>();
			for (MapIndividualAndUnitToReporting mapping : mapIndividualAndUnitToReporting) {
				Optional<ForcePersonnel> optionalForcePersonal = this.forcePersonnelRepository.findById(mapping.getCandidateForcePersonalId());
				if (optionalForcePersonal.isPresent()) {
					ForcePersonnelDto forcePersonnel=new ForcePersonnelDto();
					forcePersonnel.setForcePersonalId(optionalForcePersonal.get().getForcePersonalId());
					forcePersonnel.setName(optionalForcePersonal.get().getName());
					forcePersonnel.setForceId(optionalForcePersonal.get().getForceId());
					forcePersonnel.setRank(rankRepo.findById(optionalForcePersonal.get().getRank()).get().getRankFullName());
					forcePersonnel.setUnit(unitRepository.findById(optionalForcePersonal.get().getUnit()).get().getUnitFullName());
					forcePersonals.add(forcePersonnel);
					
				}
			}
		
			return forcePersonals;
		}

	@Override
	public List<DropDownDto> getNameDropDownByForceAndRank(String forceNo, String rankCode) {
	Rank rankMaster=rankMasterRepository.findByRankCode(rankCode);
			return null;
	}
}
