package nic.ame.app.master.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.dto.UserRoleHistoryDto;
import nic.ame.app.master.model.AssignedPrivilege;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTDealingHand;
import nic.ame.app.master.model.TTOrderFileRoleCreation;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.model.UserRoleTemp;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AssignedPrivilegeRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.TTDealingHandRepo;
import nic.ame.app.master.repository.TTUserRoleOrderFileRepo;
import nic.ame.app.master.repository.UserRoleRepo;
import nic.ame.app.master.repository.UserRoleTempRepo;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.UserDateUtil;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	Logger logger = LogManager.getLogger(UserRoleServiceImpl.class);

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;
	
	
	@Autowired
	UserRoleTempRepo userRoleTempRepo; 

	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private TTUserRoleOrderFileRepo ttUserRoleOrderFileRepo;
	
	@Autowired
	private TTDealingHandRepo ttDealingHandRepo;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	
	@Autowired
	private AssignedPrivilegeRepo assignedPrivilegeRepo;
	

	private final int STATUS=1;

	@Override
	public List<MedicalBoardMember> getUserRolefromBoardService(String force_personal_id) {

		List<MedicalBoardMember> boardMembers = medicalBoardMemberRepo.getUserRolefromBoard(force_personal_id);

		return boardMembers;
	}

	@Override
	public Set<String> getRoleByforcePersonalId(String forcePersonalId) {

		Set<String> userRolesList = userRoleRepo.findByForcePersonalId(forcePersonalId,STATUS);

		return userRolesList;
	}

	// Is doctor is in any of the board member
	@Override
	public int getRoleByforcePersonalIdDoctor(String forcePersonalId, String BOARD_MEMBER_ROLE_CODE) {

		int result = userRoleRepo.findByForcePersonalIdDoctor(forcePersonalId, BOARD_MEMBER_ROLE_CODE);

		return result;
	}

	@Override
	public List<UserRoleDto> getListOfUserWithRoles(String loginForcePersonalId) {
		List<Object> getdto = forcePersonnelRepository.ListOfRoleAssignToUser(loginForcePersonalId);

		List<UserRoleDto> userRoleDtosList = new ArrayList<>();

		if (getdto.isEmpty()) {
			return userRoleDtosList;
		}
		Iterator<Object> iterator = getdto.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserRoleDto userRoleDto = new UserRoleDto();

			if (obj[0] != null) {
				userRoleDto.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				userRoleDto.setIrlaNo(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				userRoleDto.setRank(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				userRoleDto.setForceName(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null && obj[5] != null) {
				userRoleDto.setUnitName(refForceService.getUnitNameByUnitId(Integer.parseInt(String.valueOf(obj[4])),
						String.valueOf(obj[5]).trim()));
			}
			if (obj[6] != null) {
				userRoleDto.setRole(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				userRoleDto.setForcePersonalId(String.valueOf(obj[7]).trim());
			}

			userRoleDtosList.add(userRoleDto);
		}
		return userRoleDtosList;
	}

	@Override
	public String saveNewUserRole(List<UserRoleMapDto> objects, String forcePersonal) {

		String message = null;
		Optional<ForcePersonnel> fp = forcePersonnelRepository.getByForcePersonnelId(forcePersonal);

		if (fp.isEmpty()) {
			message = "Invalid operation......";
			return message;
		} else {
			ForcePersonnel personal = fp.get();

			for (UserRoleMapDto userRoleMapDto : objects) {
				UserRole userRole = new UserRole();

				// =================mapped by details================================//
				userRole.setMappedOn(Calendar.getInstance().getTime());
				userRole.setMappedBy(personal.getForcePersonalId());
				userRole.setMappedByForceNo(personal.getForceNo());
				userRole.setMappedByName(personal.getName());
				userRole.setMappedByUnit(Long.parseLong(personal.getUnit()));
				// =================user Role Mapping=================================//

				userRole.setForcePersonalId(userRoleMapDto.getForcePersonalId());
				userRole.setRoleId(String.valueOf(userRoleMapDto.getRoleName()));
				userRole.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));

				logger.info(">>>>>>>>>>>>>>>>>>Before saving role");
				userRoleRepo.save(userRole);
				logger.info(">>>>>>>>>>>>>>>> After saving role");

			}
			message = "data saved successfully";
			return message;

		}

	}

	@Override
	public List<UserRoleHistoryDto> getUserRoleHistory(String forcePersonalId) {
		List<Object> getdto = userRoleRepo.userRoleHistory(forcePersonalId.trim());

		List<UserRoleHistoryDto> userRoleDtosList = new ArrayList<>();

		if (getdto.isEmpty()) {
			return userRoleDtosList;
		}
		Iterator<Object> iterator = getdto.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserRoleHistoryDto userRoleHistoryDto = new UserRoleHistoryDto();

			if (obj[0] != null) {
				userRoleHistoryDto.setRoleName(String.valueOf(obj[0]).trim());
			}

			if (obj[1] != null) {
				if(Integer.parseInt(String.valueOf(obj[1]).trim())==Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)))
				{
				userRoleHistoryDto.setStatus("ACTIVE");
			}else {
				userRoleHistoryDto.setStatus("INACTIVE");

			}
				}
			if (obj[2] != null) {
				userRoleHistoryDto.setForceName(String.valueOf(obj[2]).trim());
			}else {
				userRoleHistoryDto.setForceName("N/A");
			}

			if (obj[3] != null) {
				userRoleHistoryDto.setCreatedBy(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				userRoleHistoryDto.setYear(String.valueOf(obj[4]).trim());
			}
			userRoleDtosList.add(userRoleHistoryDto);
			
		}
		return userRoleDtosList;

	}
	
	
	
	
	//=====================save User Temp Role=============================//
	
	
	
	@Override
	public String saveNewUserRoleTemp(List<UserRoleMapDto> objects, String forcePersonal,String transactionalId) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+transactionalId);
		String message = null;
		Optional<ForcePersonnel> fp = forcePersonnelRepository.getByForcePersonnelId(forcePersonal);

		if (fp.isEmpty()) {
			message = "Invalid operation......";
			return message;
		} else {
			ForcePersonnel personal = fp.get();

			for (UserRoleMapDto userRoleMapDto : objects) {
				UserRoleTemp userRole = new UserRoleTemp();

				// =================mapped by details================================//
				userRole.setMappedOn(Calendar.getInstance().getTime());
				userRole.setMappedBy(personal.getForcePersonalId());
				userRole.setMappedByForceNo(personal.getForceNo());
				userRole.setMappedByName(personal.getName());
				userRole.setMappedByUnit(Long.parseLong(personal.getUnit()));
				// =================user Role Mapping=================================//

				userRole.setForcePersonalId(userRoleMapDto.getForcePersonalId());
				userRole.setRoleId(String.valueOf(userRoleMapDto.getRoleName()));
				userRole.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
				userRole.setTransactionId(new BigInteger(transactionalId.trim()));

				logger.info(">>>>>>>>>>>>>>>>>>Before saving role");
				userRoleTempRepo.save(userRole);
				logger.info(">>>>>>>>>>>>>>>> After saving role");

			}
			message =transactionalId.trim();
			return message;

		}

	}

	@Override
	public List<UserRoleDto> getListOfUserWithRolesTemp(BigInteger transactionalId) {
		List<Object> getdto = forcePersonnelRepository.ListOfRoleAssignToUserTemp(transactionalId);

		List<UserRoleDto> userRoleDtosList = new ArrayList<>();

		if (getdto.isEmpty()) {
			return userRoleDtosList;
		}
		Iterator<Object> iterator = getdto.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserRoleDto userRoleDto = new UserRoleDto();

			if (obj[0] != null) {
				userRoleDto.setName(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				userRoleDto.setIrlaNo(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				userRoleDto.setRank(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				userRoleDto.setForceName(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null && obj[5] != null) {
				userRoleDto.setUnitName(refForceService.getUnitNameByUnitId(Integer.parseInt(String.valueOf(obj[4])),
						String.valueOf(obj[5]).trim()));
			}
			if (obj[6] != null) {
				userRoleDto.setRole(String.valueOf(obj[6]).trim());
			}
			if (obj[7] != null) {
				userRoleDto.setForcePersonalId(String.valueOf(obj[7]).trim());
			}

			userRoleDtosList.add(userRoleDto);
		}
		return userRoleDtosList;
	}

	
	@Override
	@Transactional
	public String saveNewUserRoleWithFile(TTOrderFileRoleCreation ttUserRoleOrderFile, FileUploadDto fileUploadDto,
			List<UserRoleTemp> userRoleTemps) {
	
		for (UserRoleTemp userRoleTemp : userRoleTemps) {
			UserRole userRole=new UserRole();
			userRole.setForcePersonalId(userRoleTemp.getForcePersonalId());
			userRole.setUserRoleId(userRoleTemp.getUserRoleId());
			userRole.setRoleId(userRoleTemp.getRoleId());
			userRole.setMappedToForce(userRoleTemp.getMappedToForce());
			userRole.setMappedBy(userRoleTemp.getMappedBy());
			userRole.setMappedByUnit(userRoleTemp.getMappedByUnit());
			userRole.setStatus(userRoleTemp.getStatus());
			userRole.setTransactionalId(userRoleTemp.getTransactionId());
			userRole.setMappedByName(userRoleTemp.getMappedByName());
			userRole.setMappedOn(Calendar.getInstance().getTime());
			
			userRoleRepo.save(userRole);
            }
		ttUserRoleOrderFile.setFileName(fileUploadDto.getFileName());
		ttUserRoleOrderFile.setFilePath(fileUploadDto.getFilePath());
		ttUserRoleOrderFile.setFileType(fileUploadDto.getFileType());
		ttUserRoleOrderFile.setFileSize(fileUploadDto.getFileSize());
		
		
		int year=UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_USER_ROLE_ORDER+File.separator+year;
		String path=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year)); 
		String savePath=path+File.separator+ttUserRoleOrderFile.getTransactionalId()+fileUploadDto.getFileName();
		  try {
			FileCopyUtils.copy(fileUploadDto.getFileContent(), new
			
					File(savePath));
			ttUserRoleOrderFile.setFilePath(savePath);
			ttUserRoleOrderFileRepo.save(ttUserRoleOrderFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				 
		System.out.println(">>>>>>>>>>>>>>>>"+path);
		return "fileSaved";
	}

	@Override
	public List<UserRoleTemp> getUserRoleByTransactionalId(BigInteger transactionalId) {
		List<UserRoleTemp> roles=userRoleTempRepo.findByTransactionalId(transactionalId);
		return roles;
	}

	@Override
	@Transactional
	public int createNewUserRoleBySuperAdmin(UserRoleMapDto userRoleTemp, FileUploadDto fileUploadDto,
			String transactionalId) {
		//======setting the user role===============//
		
		UserRole userRole=new UserRole();
		userRole.setForcePersonalId(userRoleTemp.getForcePersonalId());
		userRole.setRoleId(userRoleTemp.getRoleId());
		userRole.setMappedToForce(userRoleTemp.getMappedToForce());
		userRole.setMappedBy(userRoleTemp.getMappedBy());
		userRole.setMappedByUnit(userRoleTemp.getMappedByUnit());
		userRole.setStatus(userRoleTemp.getStatus());
	    userRole.setPermissionToAssignRole(0);
		userRole.setTransactionalId(new BigInteger(transactionalId.trim()));
		userRole.setMappedByName(userRoleTemp.getMappedByName());
	    userRole.setMappedOn(Calendar.getInstance().getTime());
	    userRole.setRefAdminFlag(userRoleTemp.getRefAdminFlag());
	  
		
		UserRole userRoleResult=userRoleRepo.save(userRole);
		
		if(userRoleResult!=null) {
			
			//===============setting the privileges ================//
			
			AssignedPrivilege assignedPrivilege=new AssignedPrivilege();
			assignedPrivilege.setTransactionalId(transactionalId);
			assignedPrivilege.setCanAssign(userRoleTemp.getPrivilege().trim());
			assignedPrivilege.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
			//assignedPrivilege.setClientIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient());
			assignedPrivilege.setCreatedOn(Calendar.getInstance().getTime());
			assignedPrivilegeRepo.save(assignedPrivilege);
			
		  
			logger.info("**************privileges assign************");
			
			//======================order no ,order file details===========================//
			
		TTOrderFileRoleCreation ttUserRoleOrderFile=new TTOrderFileRoleCreation();
		ttUserRoleOrderFile.setFileName(fileUploadDto.getFileName());
		ttUserRoleOrderFile.setFilePath(fileUploadDto.getFilePath());
		ttUserRoleOrderFile.setFileType(fileUploadDto.getFileType());
		ttUserRoleOrderFile.setFileSize(fileUploadDto.getFileSize());
		ttUserRoleOrderFile.setTransactionalId(new BigInteger(transactionalId.trim()));
		ttUserRoleOrderFile.setOrderNumber(fileUploadDto.getOrderNo());
		ttUserRoleOrderFile.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile.setDepartment(fileUploadDto.getDepartment());
		ttUserRoleOrderFile.setRank(fileUploadDto.getDesignation());
		ttUserRoleOrderFile.setOrderDate(fileUploadDto.getOrderDate());
		ttUserRoleOrderFile.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile.setUploadOn(Calendar.getInstance().getTime());
		ttUserRoleOrderFile.setUploadBy(userRoleTemp.getMappedBy());
		ttUserRoleOrderFile.setRemark(fileUploadDto.getRemark());
   
		
		
		int year=UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_USER_ROLE_ORDER+File.separator+year;
		
		String path=CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year)); 
		
		String savePath=path+File.separator+ttUserRoleOrderFile.getTransactionalId()+fileUploadDto.getFileName();
		  try {
				ttUserRoleOrderFile.setFilePath(savePath);
				ttUserRoleOrderFile=ttUserRoleOrderFileRepo.save(ttUserRoleOrderFile);
				
				
				if(ttUserRoleOrderFile!=null) {
			    FileCopyUtils.copy(fileUploadDto.getFileContent(), new
			
					File(savePath));
			    logger.info("**************file saved************");
			    }
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
		}
		return 0;
		}

	@Override
	public int checkRoleCount(String roleId) {
		
    	int statusCode=1;
    	int count=userRoleRepo.findRoleStatusByRoleId( roleId.trim(), statusCode);

    	
		return count ;
	}

	
	
	//=====================update user Role Status===========================//
	
	@Override
	@jakarta.transaction.Transactional
	public String updateUserRoleStatus(TTOrderFileRoleCreation ttUserRoleOrderFile, FileUploadDto fileUploadDto,
			String transactionalId,String status,String UserRoleId) {
		 Optional<UserRole> userRoleOptional=userRoleRepo.findbyUserRoleById(Integer.parseInt(UserRoleId));
		 UserRole userRole=new UserRole();
		
		 if(!userRoleOptional.isEmpty()) {
			 userRole=userRoleOptional.get();
		 }
		 
		 userRole.setStatus(Integer.parseInt(status));
		 userRoleRepo.save(userRole);
		logger.info("......USER ROLE IS UPDATED");
		TTOrderFileRoleCreation ttUserRoleOrderFile1=new TTOrderFileRoleCreation();
		
		ttUserRoleOrderFile1.setFileName(fileUploadDto.getFileName());
		ttUserRoleOrderFile1.setFilePath(fileUploadDto.getFilePath());
		ttUserRoleOrderFile1.setFileType(fileUploadDto.getFileType());
		ttUserRoleOrderFile1.setFileSize(fileUploadDto.getFileSize());
		ttUserRoleOrderFile1.setTransactionalId(new BigInteger(transactionalId.trim()));
		ttUserRoleOrderFile1.setOrderNumber(fileUploadDto.getOrderNo());
		ttUserRoleOrderFile1.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile1.setDepartment(fileUploadDto.getDepartment());
		ttUserRoleOrderFile1.setRank(fileUploadDto.getDesignation());
		ttUserRoleOrderFile1.setOrderDate(fileUploadDto.getOrderDate());
		ttUserRoleOrderFile1.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile1.setUploadOn(fileUploadDto.getOrderDate());
		
		ttUserRoleOrderFile1.setRemark(fileUploadDto.getRemark());

		int year = UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_USER_ROLE_ORDER + File.separator + year;
		String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName, String.valueOf(year));
		String savePath = path + File.separator + ttUserRoleOrderFile1.getTransactionalId()
				+ fileUploadDto.getFileName();
		try {
			ttUserRoleOrderFile1.setFilePath(savePath);
			
			ttUserRoleOrderFile1=ttUserRoleOrderFileRepo.save(ttUserRoleOrderFile1);
			//==========check data save status==============//
			
			if(ttUserRoleOrderFile1!=null) {
			 FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
			 logger.info("file save successfully.................!");
              }
			else {
				logger.info("file save fail.................!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return savePath;
	}

	@Override
	@jakarta.transaction.Transactional
	public int createDealingHandByBoardMember(UserRoleMapDto roleMapDto, FileUploadDto fileUploadDto,
			String transactionalId,String boardIdDealingHand,HttpServletRequest request) {
		
		UserRole userRole=new UserRole();
		
		userRole.setMappedBy(roleMapDto.getMappedBy());
		userRole.setMappedOn(Calendar.getInstance().getTime());
		userRole.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
		userRole.setRoleId(roleMapDto.getRoleId().trim());
		userRole.setTransactionalId(new BigInteger(transactionalId));
		userRole.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
		userRole.setForcePersonalId(roleMapDto.getForcePersonalId());
		userRole.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
	    
		userRoleRepo.save(userRole);
		
		//============saving dealing hand to board ============================//
		
		String yearOfBoard=medicalBoardMemberRepo.getBoardYear(boardIdDealingHand);
		
		MedicalBoardMember boardMemberDealingHand=new MedicalBoardMember();
		boardMemberDealingHand.setBoardId(boardIdDealingHand);
		boardMemberDealingHand.setBoardYear(yearOfBoard);
		boardMemberDealingHand.setRoleName(roleMapDto.getRoleId());
		boardMemberDealingHand.setAlternateMedicalRoleId(0);
		boardMemberDealingHand.setStatusCode(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));
        boardMemberDealingHand.setForcePersonalId(roleMapDto.getForcePersonalId());
        boardMemberDealingHand.setName(forcePersonnelRepository.getByForcePersonnelId(roleMapDto.getForcePersonalId()).get().getName());
        boardMemberDealingHand.setForce_no(String.valueOf(fileUploadDto.getDepartment()));
        
		medicalBoardMemberRepo.save(boardMemberDealingHand);
		logger.info("Dealing hand has been saved....................!");
		
        TTOrderFileRoleCreation ttUserRoleOrderFile1=new TTOrderFileRoleCreation();
		
		ttUserRoleOrderFile1.setFileName(fileUploadDto.getFileName());
		ttUserRoleOrderFile1.setFilePath(fileUploadDto.getFilePath());
		ttUserRoleOrderFile1.setFileType(fileUploadDto.getFileType());
		ttUserRoleOrderFile1.setFileSize(fileUploadDto.getFileSize());
		ttUserRoleOrderFile1.setTransactionalId(new BigInteger(transactionalId.trim()));
		ttUserRoleOrderFile1.setOrderNumber(fileUploadDto.getOrderNo());
		ttUserRoleOrderFile1.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile1.setDepartment(fileUploadDto.getDepartment());
		ttUserRoleOrderFile1.setRank(fileUploadDto.getDesignation());
		ttUserRoleOrderFile1.setOrderDate(fileUploadDto.getOrderDate());
		ttUserRoleOrderFile1.setOrderBy(fileUploadDto.getName_IrlaNo());
		ttUserRoleOrderFile1.setUploadOn(Calendar.getInstance().getTime());
		
		ttUserRoleOrderFile1.setRemark(fileUploadDto.getRemark());
  
		TTDealingHand dealingHand=new TTDealingHand();
		dealingHand.setBoardId(boardIdDealingHand);
		dealingHand.setForcePersonalId(roleMapDto.getForcePersonalId());
		dealingHand.setStatus(CommonConstant.USER_ROLE_STATUS_ACTIVE);
		
		TTDealingHand dealingHand2= ttDealingHandRepo.save(dealingHand);
         if(dealingHand2!=null) {
        	 logger.info(">>> Dealing hand details saved in Database.....!");
         }
		
		int year = UserDateUtil.getYear(new Date());
		String directoryName = CommonConstant.File_PATH_USER_ROLE_ORDER + File.separator + year;
		String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName, String.valueOf(year));
		String savePath = path + File.separator + ttUserRoleOrderFile1.getTransactionalId()
				+ fileUploadDto.getFileName();
		try {
			ttUserRoleOrderFile1.setFilePath(savePath);
			
			ttUserRoleOrderFile1=ttUserRoleOrderFileRepo.save(ttUserRoleOrderFile1);
			//==========check data save status==============//
			
			if(ttUserRoleOrderFile1!=null) {
				 logger.info(">>> ttUserRoleOrderFile details saved in Database.....!");
			 FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
			 logger.info("file save successfully.................!");
              }
			else {
				logger.info("file save fail.................!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		return 0;
	}

	@Override
	public List<UserRoleDto> getForcePersonalByControllingRole(String loginForcePersonalId) {
		
		
	 List<Object> getdto = userRoleRepo.findControllingUserRole(loginForcePersonalId,"5",CommonConstant.ACTIVE_FLAG_YES);

		List<UserRoleDto> userRoleDtosList = new ArrayList<>();

		if (getdto.isEmpty()) {
			return userRoleDtosList;
		}
		Iterator<Object> iterator = getdto.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserRoleDto userRoleDto = new UserRoleDto();

			if (obj[0] != null) {
				userRoleDto.setForcePersonalId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				userRoleDto.setRank(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				userRoleDto.setUnitName(String.valueOf(obj[2]).trim());

			}
			if (obj[3] != null) {
				userRoleDto.setForceName(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null ) {
				userRoleDto.setName(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				userRoleDto.setIrlaNo(String.valueOf(obj[5]).trim());
			}
			if (obj[6] != null) {
				userRoleDto.setRoleCode(String.valueOf(obj[6]).trim());
			}

			userRoleDtosList.add(userRoleDto);
		}
		return userRoleDtosList;
	}

	@Override
	public List<UserRoleDto> getForcePersonalByControllingRoleByForcePersonnelId(String loginForcePersonalId,
			String controllingOfficerForcePersonnelId) {
		// TODO Auto-generated method stub
		 List<Object> getdto = userRoleRepo.findControllingUserRoleByForcePersonnelId(loginForcePersonalId,"5",CommonConstant.ACTIVE_FLAG_YES,controllingOfficerForcePersonnelId);

		 List<UserRoleDto> userRoleDtosList = new ArrayList<>();

			if (getdto.isEmpty()) {
				return userRoleDtosList;
			}
			Iterator<Object> iterator = getdto.iterator();

			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				UserRoleDto userRoleDto = new UserRoleDto();

				if (obj[0] != null) {
					userRoleDto.setForcePersonalId(String.valueOf(obj[0]).trim());
				}
				if (obj[1] != null) {
					userRoleDto.setRank(String.valueOf(obj[1]).trim());
				}
				if (obj[2] != null) {
					userRoleDto.setUnitName(String.valueOf(obj[2]).trim());

				}
				if (obj[3] != null) {
					userRoleDto.setForceName(String.valueOf(obj[3]).trim());
				}
				if (obj[4] != null ) {
					userRoleDto.setName(String.valueOf(obj[4]).trim());
				}
				if (obj[5] != null) {
					userRoleDto.setIrlaNo(String.valueOf(obj[5]).trim());
				}
				if (obj[6] != null) {
					userRoleDto.setRoleCode(String.valueOf(obj[6]).trim());
				}

				userRoleDtosList.add(userRoleDto);
			}
			return userRoleDtosList;
	}

	
	
}