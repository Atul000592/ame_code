package nic.ame.app.master.medical.service;

import java.util.List;

import nic.ame.app.admin.model.RefRole;
import nic.ame.app.master.dto.RoleStatusDto;
import nic.ame.app.superuser.dto.UserRoleDto;


public interface RoleService {

	List<RefRole> roleList(String forcePersonalId);
	
	List<RefRole> getMasterRoleList(String forcePersonalIdLogin,int roleCodeId);
	
	List<RefRole> getPermissionRoleList(String forcePersonalIdLogin,int roleCodeId);
	
	List<RefRole> getMasterRolePermissionList(String forcePersonalIdLogin,int roleCodeId);
	
	List<RoleStatusDto> getAllRoleByForcePersonalId(String forcePersonalId);
	
	List<UserRoleDto> getListOfRoles();
	
	
}
