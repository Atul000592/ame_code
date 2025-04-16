package nic.ame.app.master.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.dto.UserRoleHistoryDto;
import nic.ame.app.master.model.TTOrderFileRoleCreation;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.model.UserRoleTemp;




public interface UserRoleService {

	 List<MedicalBoardMember> getUserRolefromBoardService(String forcePersonalId);
	 
	 Set<String> getRoleByforcePersonalId(String forcePersonalId);
	 
	int  getRoleByforcePersonalIdDoctor(String forcePersonalId,String BOARD_MEMBER_ROLE_CODE);
	
	List<UserRoleDto> getListOfUserWithRoles(String loginForcePersonalId);
	
	public String saveNewUserRole(List<UserRoleMapDto> objects,String forcePersonal);
	
	public List<UserRoleHistoryDto> getUserRoleHistory(String forcePersonalId);
	
	public String saveNewUserRoleTemp(List<UserRoleMapDto> objects,String forcePersonal,String transactionalId);
	
	//get role from temp userRole service with transactionalId
	List<UserRoleDto> getListOfUserWithRolesTemp(BigInteger transactionalId);
	
	public String saveNewUserRoleWithFile(TTOrderFileRoleCreation ttUserRoleOrderFile,FileUploadDto fileUploadDto,List<UserRoleTemp> userRoleTemp);
	
	List<UserRoleTemp> getUserRoleByTransactionalId(BigInteger transactionalId);
	
	public int createNewUserRoleBySuperAdmin(UserRoleMapDto roleMapDto,FileUploadDto fileUploadDto,String transactionalId);
	
	public int checkRoleCount(String roleId);
	
	public int createDealingHandByBoardMember(UserRoleMapDto roleMapDto,FileUploadDto fileUploadDto,String transactionalId,String boardIdDealingHand,HttpServletRequest request);
	
	public String updateUserRoleStatus(TTOrderFileRoleCreation ttUserRoleOrderFile,FileUploadDto fileUploadDto,String transactionalId,String status,String roleId);

	List<UserRoleDto> getForcePersonalByControllingRoleByForcePersonnelId(String loginForcePersonalId,String controllingOfficerForcePersonnelId);
	
	List<UserRoleDto> getForcePersonalByControllingRole(String loginForcePersonalId);
}
