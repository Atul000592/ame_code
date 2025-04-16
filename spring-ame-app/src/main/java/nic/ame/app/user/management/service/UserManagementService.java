package nic.ame.app.user.management.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.ForcePersonnel;

import nic.ame.app.master.model.MapIndividualAndUnitToReporting;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.user.dto.UserDeclarationDto;

public interface UserManagementService {

	
	public UserRole getUserRoleByUserRoleId(int userRoleId);
	public String updateUserRoleByUserRoleId(UserRole userRole);
	public List<UserDeclarationDto> getUserDeclarationStatusByBoard(String forcePersonalId);
	public List<UserRoleDto> getUserRoleByForcePersonalId(String LoginForcePersonalId);

	public boolean saveIndividualToReporting(List<String> forcePersonelId,String controllingId,HttpSession httpSession,HttpServletRequest request);

	List<ForcePersonnelDto> list(String reportingForcePersonalId);
	public List<DropDownDto> getNameDropDownByForceAndRank(String forceNo, String rankCode);


}
