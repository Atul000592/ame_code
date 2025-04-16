package nic.ame.app.admin.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.MedicalBoardIndividualMappingDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.model.RefRoleMedical;
import nic.ame.app.admin.repository.ForcePersonalAdminRepository;
import nic.ame.app.admin.repository.ForceUnitRepo;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.ApplicationStateDescription;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.repository.UserRoleRepo;
import nic.ame.app.master.service.BoardIdGenerationService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.app.sms.SMSConfigurationConstantsAME;
import nic.ame.app.sms.SMSTemplateDto;
import nic.ame.app.sms.SMSTemplateService;
import nic.ame.app.sms.SmsResponse;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.GetIpAddressClient;

@Service
@Transactional
public class MedicalBoardIndividualMappingServiceImpl implements MedicalBoardIndividualMappingService {
	
	Logger logger=LogManager.getLogger(MedicalBoardIndividualMappingServiceImpl.class);
	
	@Autowired
	private ForcePersonalAdminRepository forcePersonalRepository;
	@Autowired
	MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	@Autowired
	private BoardIdGenerationService  boardIdGenerationService;
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo;
	
	@Autowired
	private ForceRepo refForceRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private SMSTemplateService smsTemplateService;
	
	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;

	
	@Autowired
	private ApplicationStateDescriptionRepository applicationStateDescriptionRepository;
	
	
@Autowired
private RefMedicalExamTypeRepo refMedicalExamTypeRepo;
	
	
	
	
	@Override
	public MedicalBoardIndividualMapping getBoardDetailByForcePersonalId(String forcePersonalId) {
		MedicalBoardIndividualMapping boardIndividualMapping=new MedicalBoardIndividualMapping();
		Optional<MedicalBoardIndividualMapping> optional= medicalBoardIndividualMappingRepo.getforcePersonalDetailsFormBoard(forcePersonalId);
		if(!optional.isEmpty()) {
			boardIndividualMapping=optional.get();
		}
		return boardIndividualMapping;
	}

	
	// Save Board Details.
	@Override
	public boolean saveMedicalBoardMember(List<MedicalBoardMemberDto> medicalBoardMemberDtolist,String forcePersonalLogin,HttpServletRequest request) {
		
		
		MedicalBoard medicalBoard =new MedicalBoard();
		MedicalBoardMember medicalBoardMember = new MedicalBoardMember();
		Iterator<MedicalBoardMemberDto> it = medicalBoardMemberDtolist.iterator();
		boolean boardIdcreated=false;
		String boardId=null;
		Optional<ForcePersonnel> forcePersonalLoginDetails = forcePersonalRepository.getByForcePersonalId(forcePersonalLogin);
		
		/*=======RefMedicalRole Reserve List=============================*/
	     List<RefRoleMedical> refRoleMedicalsForReserve=refRoleMedicalRepo.findAll().stream()
		.filter(s->s.getReserveFlag()==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG)))
		.collect(Collectors.toList());
		
	     logger.info(">>>>>>>>Medical List for reserve>>>>>>>>>>>>> ");
	     
	   
	     
		
		while (it.hasNext()) {
			MedicalBoardMemberDto medicalBoardMemberDto = it.next();
			
			
		   // The previous three lines were from the foreach loop
			
		   // Your code inside the foreach loop
			
			
			
			int  boardMemberRole = userRoleService.getRoleByforcePersonalIdDoctor(medicalBoardMemberDto.getForcePersonalId(),
					ameParametersRepository.getAmeParameterValue(CommonConstant.BOARD_MEMBER_ROLE_CODE));
			     System.out.println(boardMemberRole+"value for member====================");
			
			
			     
			if(boardMemberRole==0) {
				UserRole userRoleMember = new UserRole();
				
				
				userRoleMember.setMappedBy(forcePersonalLoginDetails.get().getForcePersonalId());
				userRoleMember.setMappedByForceNo(forcePersonalLoginDetails.get().getForceNo());
				userRoleMember.setMappedByName(forcePersonalLoginDetails.get().getName());
				userRoleMember.setMappedByUnit(Long.parseLong((forcePersonalLoginDetails).get().getUnit()));
				
				userRoleMember.setForcePersonalId(medicalBoardMemberDto.getForcePersonalId());
			
				userRoleMember.setRoleId(ameParametersRepository.getAmeParameterValue(CommonConstant.BOARD_MEMBER_ROLE_ID));
				userRoleMember.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
                userRoleMember.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));  				
				
				userRoleRepo.save(userRoleMember);
				
				
				
				}
			
			
			
			if(!boardIdcreated) {
			if(medicalBoardMemberDto.getUsedFor().equalsIgnoreCase("AME")) {
				boardId=boardIdGenerationService.boardDynamicId(medicalBoardMemberDto.getForceNo().trim() , 1);
				boardIdcreated =true;
				
			}
			if(medicalBoardMemberDto.getUsedFor().equalsIgnoreCase("RMB")) {
				boardId=boardIdGenerationService.boardDynamicId(medicalBoardMemberDto.getForceNo().trim(), 2);
				boardIdcreated =true;
					
				
			}
			if(medicalBoardMemberDto.getUsedFor().equalsIgnoreCase("IMB")) {
				
				boardId=boardIdGenerationService.boardDynamicId(medicalBoardMemberDto.getForceNo().trim(), 3);
				boardIdcreated =true;
				}
			
			}
			
			
			 //============checking for reserver member in the Iterator ================//
			medicalBoardMemberDto=MedicalBoardIndividualMappingServiceImpl.setReserveMedicalMember(medicalBoardMemberDto, refRoleMedicalsForReserve, ameParametersRepository);
			
		
				//===========setting value for member ===================//
				
			    medicalBoardMember.setAlternateMedicalRoleId(medicalBoardMemberDto.getAlternateMedicalId());
			    medicalBoardMember.setResereveFlag(medicalBoardMemberDto.getReserveFlag());
		        medicalBoardMember.setStatusCode(medicalBoardMemberDto.getStatus());
		        
			
			
			   //medicalBoardMember.setRank(forcePersonalRepository.getById(medicalBoardMemberDto.getForcePersonalId()).getRank());
			   
		       
		       medicalBoardMember.setBoardId(boardId);
			   medicalBoardMember.setDesignation(medicalBoardMemberDto.getDesignation());
			   medicalBoardMember.setForce_no(medicalBoardMemberDto.getForceNo());
			   medicalBoardMember.setForcePersonalId(medicalBoardMemberDto.getForcePersonalId());
			   medicalBoardMember.setName(medicalBoardMemberDto.getName());
			   medicalBoardMember.setRoleName(medicalBoardMemberDto.getRoleName());
			   medicalBoardMember.setBoardYear(medicalBoardMemberDto.getBoardYear());
			   medicalBoardMember.setGazettedFlag(medicalBoardMemberDto.getgFlag());
			   medicalBoardMember.setGender(medicalBoardMemberDto.getGender());
		  
	        logger.info("medicalBoard_FromDate ......"+medicalBoardMemberDto.getFromDate());
	        logger.info("medicalBoard_FromTo ......"+medicalBoardMemberDto.getToDate());
	        medicalBoardMemberRepo.save(medicalBoardMember);
	        
	        
             
			
			medicalBoard.setUsedFor(medicalBoardMemberDto.getUsedFor());
			medicalBoard.setBoardAtForceNo(medicalBoardMemberDto.getBoardAtForceNo());
			medicalBoard.setPlace(medicalBoardMemberDto.getPlace());
			medicalBoard.setFromDate(medicalBoardMemberDto.getFromDate());
			medicalBoard.setToDate(medicalBoardMemberDto.getToDate());
			medicalBoard.setCreatedBy(forcePersonalLoginDetails.get().getForcePersonalId());
			medicalBoard.setBoardYear(medicalBoardMemberDto.getBoardYear());
			medicalBoard.setGazettedFlag(medicalBoardMemberDto.getgFlag());
			medicalBoard.setBoardId(boardId);
			medicalBoard.setCreatedOn(Calendar.getInstance().getTime());
			medicalBoard.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
			
		}
		    medicalBoardRepo.save(medicalBoard);
		
            return true;
		}




	private static MedicalBoardMemberDto setReserveMedicalMember(MedicalBoardMemberDto medicalBoardMemberDto,
			List<RefRoleMedical> refRoleMedicalsForReserve,AmeParametersRepository ameParametersRepository) {
		
	         for(int i=0;i<refRoleMedicalsForReserve.size();i++) {
	        	 
	        	 //=============setting parameter for reserve member in the board=======================//
	        	 
	        	 if(refRoleMedicalsForReserve.get(i).getId()==Integer.parseInt(String.valueOf(medicalBoardMemberDto.getRoleName()))){
				medicalBoardMemberDto.setRoleName(String.valueOf(refRoleMedicalsForReserve.get(i).getReserverForId()));
			    medicalBoardMemberDto.setReserveFlag(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)));
			    medicalBoardMemberDto.setAlternateMedicalId(Integer.parseInt(String.valueOf(refRoleMedicalsForReserve.get(i).getId())));
			    medicalBoardMemberDto.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)));
			     }
	        	//=============setting parameter for  member in the board=======================//
	        	 else {
			    	 medicalBoardMemberDto.setReserveFlag(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
					 medicalBoardMemberDto.setAlternateMedicalId(Integer.parseInt(String.valueOf(medicalBoardMemberDto.getRoleName())));
					 medicalBoardMemberDto.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));

	        	 }
			
	         }
		
	   return medicalBoardMemberDto;
	}


	@SuppressWarnings("unused")
	@Override
	public boolean saveBoardunitMapping(MedicalBoardIndividualMappingDto medicalBoardIndividualMappingDto,HttpServletRequest request) {
		
		ForcePersonnel forcePersonal =null;
		
		
		System.out.println("medical board unit "+ medicalBoardIndividualMappingDto.getUnitId());
		List<ForcePersonnel> forcePersonalList=forcePersonalRepository.getByForceandUnitId(medicalBoardIndividualMappingDto.getForceNo(), 
				medicalBoardIndividualMappingDto.getUnitId());
		
		System.out.println(forcePersonalList.size()+"---------------------------");
		if(forcePersonalList!=null) {
			Iterator<ForcePersonnel> it=forcePersonalList.iterator();
			while(it.hasNext()) {
				MedicalBoardIndividualMapping medicalBoardIndividualMapping = new MedicalBoardIndividualMapping();
				medicalBoardIndividualMapping.setBoardId(medicalBoardIndividualMappingDto.getBoardId());
				medicalBoardIndividualMapping.setForceNo(medicalBoardIndividualMappingDto.getForceNo());
				
				medicalBoardIndividualMapping.setUnitNo(medicalBoardIndividualMappingDto.getUnitId());
				medicalBoardIndividualMapping.setUsedFor(medicalBoardIndividualMappingDto.getUsedFor());
				medicalBoardIndividualMapping.setBoardAssignedDate(medicalBoardIndividualMappingDto.getBoardAssignedDate());
				
				forcePersonal = it.next();
				medicalBoardIndividualMapping.setForcePersonalId(forcePersonal.getForcePersonalId());
				medicalBoardIndividualMapping.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
				System.out.println("personal Id"+forcePersonal.getForcePersonalId());
				medicalBoardIndividualMappingRepo.save(medicalBoardIndividualMapping);
				
			}
			
			return true;
		}
		
			return false;
			
		
		
		
		
		
	}

		// ========get Board Details by boardID====================================
	
	
	@Override
	public List<MedicalBoardMemberDto> getMedicalBoardById(String boardId) {
		
		List<MedicalBoardMemberDto> medicalBoardMemberDtoList=new ArrayList<MedicalBoardMemberDto>();
		MedicalBoard medicalBoard =new MedicalBoard();
		
		medicalBoard = medicalBoardRepo.getByBoardId(boardId);
		List<MedicalBoardMember>medicalBoardMemberList= medicalBoardMemberRepo.getByBoardId(boardId);
		Iterator<MedicalBoardMember> it = medicalBoardMemberList.iterator();
		
		while(it.hasNext()) {
			MedicalBoardMember medicalBoardMember = it.next();
			MedicalBoardMemberDto medicalBoardMemberDto = new MedicalBoardMemberDto();
			medicalBoardMemberDto.setCommitteeId(medicalBoardMember.getBoardId());
			medicalBoardMemberDto.setDesignation(medicalBoardMember.getDesignation());
			medicalBoardMemberDto.setForceNo(refForceRepo.getForceName(Integer.parseInt(medicalBoardMember.getForce_no())));
			medicalBoardMemberDto.setForcePersonalId(medicalBoardMember.getForcePersonalId());
			medicalBoardMemberDto.setIrlaNumber(forcePersonalRepository.getByForcePersonalId(medicalBoardMember.getForcePersonalId().trim()).get().getForceId());
			medicalBoardMemberDto.setFromDate(medicalBoard.getFromDate());
			medicalBoardMemberDto.setName(medicalBoardMember.getName());
			medicalBoardMemberDto.setGender(medicalBoardMember.getGender());
			medicalBoardMemberDto.setPlace(refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()),medicalBoard.getPlace()));
			medicalBoardMemberDto.setRank(medicalBoardMember.getRank());
	    	String roleName=refRoleMedicalRepo.findRoleNameById(Integer.parseInt(medicalBoardMember.getRoleName().trim())).get();
			medicalBoardMemberDto.setRoleName(roleName);
			medicalBoardMemberDto.setToDate(medicalBoard.getToDate());
			medicalBoardMemberDto.setUsedFor(medicalBoard.getUsedFor());
			
			medicalBoardMemberDto.setStatusValue(medicalBoardMember.getStatusCode() == 1 ? "Active" : "Inactive");
			medicalBoardMemberDto.setStatus(medicalBoardMember.getStatusCode());
			medicalBoardMemberDtoList.add(medicalBoardMemberDto);
			
			
		}
		
		return medicalBoardMemberDtoList;
		
	}


	@Override
	public void updateDeclarationStatusByForcePersonalId(String forcePersonalId) {
		 medicalBoardIndividualMappingRepo.updateDeclarationStatus(forcePersonalId);
		
	}

	@Override
	public List<MedicalBoardIndividualMapping> getAllForcePersonalByBoardId(String boardId) {
		List<MedicalBoardIndividualMapping> boardIndividualMappings=medicalBoardIndividualMappingRepo.findByBoardId(boardId);
		return boardIndividualMappings;
	}


	@Override
	public void saveForcePersonnelToBoard(List<String> forcePersonnelIdsList, String boardId,int forceNo,String unitId,HttpSession session,HttpServletRequest httpServletRequest) {
		String usedFor=null;
		String loggedInUserForcepersonnelId = (String) session.getAttribute("forcepersonalId");
		
		for (String forcePersonnelId : forcePersonnelIdsList) {
			MedicalBoardIndividualMapping boardIndividualMapping=new MedicalBoardIndividualMapping();
			boardIndividualMapping.setBoardAssignedDate(Calendar.getInstance().getTime());
	        boardIndividualMapping.setBoardId(boardId);
	        boardIndividualMapping.setForceNo(forceNo);
	        boardIndividualMapping.setUnitNo(unitId);
	        boardIndividualMapping.setForcePersonalId(forcePersonnelId);
	        boardIndividualMapping.setUsedFor(usedFor);
	        boardIndividualMapping.setYear(medicalBoardMemberRepo.getBoardYear(boardId).trim());
	        boardIndividualMapping.setIsMappingValid(1);
	        
	        if(medicalBoardIndividualMappingRepo.save(boardIndividualMapping)!=null) {
	        	AlertAndNotification alertAndNotification= new AlertAndNotification();
	        	ForcePersonnelDto candidateForcePersonnel = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonnelId).get();
	        	Optional<ApplicationStateDescription> applicationStateDescription=applicationStateDescriptionRepository.findById(55);
		    	alertAndNotification.setApplicationStateDescription(applicationStateDescription.get());
		    
		    	alertAndNotification.setReceiverForcePersonnelId(candidateForcePersonnel.getForcePersonalId());
		    	alertAndNotification.setReceiverEmail(candidateForcePersonnel.getEmailId());
		    	alertAndNotification.setReceiverMobileNumber(candidateForcePersonnel.getMobileNumber());
		    	Optional<RefMedicalExamType> examType=refMedicalExamTypeRepo.findById(1L);
		    	
		    	alertAndNotification.setExamType(examType.get());
		    	
		    	alertAndNotification.setSendByForcePersonnelId(loggedInUserForcepersonnelId);
		    	alertAndNotification.setSendFrom(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
		    	alertAndNotification.setSendBy(loggedInUserForcepersonnelId);
		    	alertAndNotification.setSendOn(Calendar.getInstance().getTime());
		    	
		    	alertAndNotification.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
		    	alertAndNotification.setLastModifiedBy(loggedInUserForcepersonnelId);
		    	alertAndNotification.setLastModifiedOn(Calendar.getInstance().getTime());
		    	

				SMSTemplateDto smsTemplateDto = new SMSTemplateDto();
				
				MedicalBoard medicalBoardDetail = medicalBoardRepo.getByBoardId(boardId);
				
				String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardDetail.getBoardAtForceNo()),medicalBoardDetail.getPlace());
			
				
			     
				 smsTemplateDto.setIrlaNo(candidateForcePersonnel.getForceId());
				 smsTemplateDto.setRank(candidateForcePersonnel.getRank());
				 smsTemplateDto.setName(candidateForcePersonnel.getName());
				
				 smsTemplateDto.setYear(medicalBoardDetail.getBoardYear());
				 smsTemplateDto.setBoardUnitPlace(boardPlace);
				 smsTemplateDto.setMobileNumber(String.valueOf(candidateForcePersonnel.getMobileNumber()));
				 smsTemplateDto.setCountryIsdCode("91");
				 SmsResponse smsResponse= smsTemplateService.individualMappedToBoardSMS(smsTemplateDto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_MAPPING_SMS);
				 if(smsResponse!=null) {
					 alertAndNotification.setIsSuccessSMS(1);
					 alertAndNotification.setIsSuccessSMSOn(Calendar.getInstance().getTime());
					 alertAndNotification.setMessage(smsResponse.getMessage());
					 alertAndNotificationRepository.save(alertAndNotification);
				 }
				 else {
					 alertAndNotification.setIsSuccessSMS(0);
					 alertAndNotification.setMessage(smsResponse.getMessage());
					 alertAndNotificationRepository.save(alertAndNotification);
				 }
		    	
	        	
	        }
		}
		
		
        
		
	}


	@Override
	public List<ForcePersonnel> getBoardMappedDataByBoardId(String boardId,int forceNo,String unitNo) {
		
		List<ForcePersonnel> getMappedForcePersonal=forcePersonalRepository.getMappedMamberByBoardId(forceNo,unitNo, boardId);
		
		return getMappedForcePersonal;
	}


	@Override
	public List<ForcePersonalResponeseAjax> getMappedMamberByBoardIdOnly(String boardId) {
	     List<Object> forcePersonalsList=forcePersonalRepository.getMappedMamberByBoardIdOnly(boardId);
	     
	     List<ForcePersonalResponeseAjax> forcePersonalResponeseAjax=new ArrayList<>();
	     if(forcePersonalsList.isEmpty()) {
				return forcePersonalResponeseAjax;
			}
	       	Iterator<Object> iterator=forcePersonalsList.iterator();
	       
			while(iterator.hasNext()) {
	       		Object[] obj=(Object[]) iterator.next();
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
					ajax.setForceName(refForceService.getForceNameByForceId(Integer.parseInt(String.valueOf(obj[3]).trim())));

				}
				if (obj[4] != null) {
					ajax.setUnitName(refForceService.getUnitNameByUnitId((Integer.parseInt(String.valueOf(obj[3]).trim())),String.valueOf(obj[4])));

				}
				if (obj[5] != null) {
					ajax.setDesignation(rankRepo.findById(Integer.parseInt(String.valueOf(obj[5]))).get().getRankFullName()	);

				}
				if (obj[6] != null) {
					if(String.valueOf(obj[6]).equalsIgnoreCase("M"))
						ajax.setGender("Male");
					else
						ajax.setGender("Female");

				}
			
				forcePersonalResponeseAjax.add(ajax);
				
			}
	     
	     
	     
		return forcePersonalResponeseAjax;
	}




	@Override
	public int getForcePersonalBoardMappingStatus(String forcePersonalId,String year) {
		int count=medicalBoardIndividualMappingRepo.getforcePersonalDetailsFormBoardCount(forcePersonalId,year);
		return count;
	}




	@Override
	public void deleteExistingMappedForcePersonalFromBoard(String forcePersonal, String boardId) {
		medicalBoardIndividualMappingRepo.deleteByBoardIdandForcePersonal(forcePersonal, boardId);
		logger.info("A row has been deleted from mapping");	
	}


	@Override
	public List<String> getListofForcePersonalForAppointmentData(String forcePersonalId) {
		medicalBoardIndividualMappingRepo.ListOfForcePersonalForAmeAppointment(forcePersonalId);
		return null;
	}


	@Override
	public List<MedicalBoardMemberDto> getAmeAppointmentPendingListByBoardId(String boardId, int statusCode) {
		List<Object> objects= medicalBoardIndividualMappingRepo.getListOfForcePersonalForAmeAppointment(boardId, statusCode);
		
		List<MedicalBoardMemberDto> boardMemberDtos=new ArrayList<>();

		if(objects.isEmpty()) {
			return boardMemberDtos;
		}
       	Iterator<Object> iterator=objects.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		MedicalBoardMemberDto medicalBoardMemberDto=new MedicalBoardMemberDto();
			
			if (obj[0] != null) {
				medicalBoardMemberDto.setBoardId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				medicalBoardMemberDto.setForcePersonalId(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				medicalBoardMemberDto.setName(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				medicalBoardMemberDto.setIrlaNumber(String.valueOf(obj[3]).trim());
			}
			if(obj[4]!=null) {
				medicalBoardMemberDto.setRank(rankRepo.findById(Integer.parseInt(String.valueOf(obj[4]).trim())).get().getRankFullName());
			}
			if(obj[5]!=null)
			{
				medicalBoardMemberDto.setForceName(String.valueOf(obj[5]).trim());
			}
			if(obj[6]!=null)
			{
				medicalBoardMemberDto.setUnitName(String.valueOf(obj[6]).trim());
			}
			if(obj[7]!=null) {
				if(String.valueOf(obj[7]).equalsIgnoreCase("M"))
					medicalBoardMemberDto.setGender("Male");
				else
					medicalBoardMemberDto.setGender("Female");

					
			}
			
			boardMemberDtos.add(medicalBoardMemberDto);
	
	}
		return boardMemberDtos;
		
	}


	@Override
	public List<String> getForcePersonnelIdForUnMapping(String boardId) {
		
		List<String> usingMedicalBoardIndividualMapping = forcePersonalRepository.getBoardIdAndForcePersonnelIdForUnMapping(boardId);
		return usingMedicalBoardIndividualMapping;
	}
	
	

}
