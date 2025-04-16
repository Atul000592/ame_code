package nic.ame.app.master.medical.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.RefRole;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.RoleStatusDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.superuser.dto.UserRoleDto;

@Service
public class RoleServiceImpl implements RoleService {

	
	@Autowired
	private RefRoleRepo refRoleRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository; 
	
	@Autowired
	private RankRepo rankRepo;
	
	@Override
	public List<RefRole> roleList(String forcePersonalId) {
	
		List<RefRole> refRoles=refRoleRepo.findByUserRoleName(forcePersonalId);
		return refRoles;
	}

	
	
	@Override
	public List<RefRole> getMasterRoleList(String forcePersonalIdLogin,int roleCodeId) {
		
		String rCodeId= String.valueOf(roleCodeId);
		List<RefRole> findList=refRoleRepo.findListByHierarchy(forcePersonalIdLogin,rCodeId.trim());
		return findList;
	}

	@Override
	public List<RoleStatusDto> getAllRoleByForcePersonalId(String forcePersonalIdLogin) {
		
		List<Object> objects=refRoleRepo.findRoleListByForcePersonalId(forcePersonalIdLogin);
		List<RoleStatusDto> roleStatusDtosList = new ArrayList<>();
		if (objects.isEmpty()) {
			return roleStatusDtosList;
		}
		Iterator<Object> iterator = objects.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			RoleStatusDto roleStatusDto=new RoleStatusDto();
			if (obj[0] != null) {
				roleStatusDto.setRoleId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				roleStatusDto.setRoleName(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				roleStatusDto.setName(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				roleStatusDto.setForceName(refForceService.getForceNameByForceId( Integer.parseInt(String.valueOf(obj[3]).trim())));
			}
			if (obj[3] != null && obj[4] != null) {
				roleStatusDto.setUnitName(refForceService.getUnitNameByUnitId(Integer.parseInt(String.valueOf(obj[3]).trim()),String.valueOf(obj[4]).trim()));
				roleStatusDto.setUnit(String.valueOf(obj[4]).trim());
				
			}
			if(obj[5]!=null)
			{
				if(Integer.parseInt(String.valueOf(obj[5]).trim())==1) {
					roleStatusDto.setStatus("Active");
				}else {
					roleStatusDto.setStatus("Inactive");
				}
				
			}
			if(obj[6]!=null)
			{
				roleStatusDto.setIrlaNo(String.valueOf(obj[6]).trim());
			}
			if(obj[7]!=null)
			{
				roleStatusDto.setForcePersonalId(String.valueOf(obj[7]).trim());
			}
            if(obj[8]!=null)
			{
				roleStatusDto.setId(String.valueOf(obj[8]).trim());
			}
			
			if(obj[9]!=null) {
				roleStatusDto.setRank( rankRepo.findById(Integer.parseInt(String.valueOf(obj[9]).trim())).get().getRankFullName());
			}
			
			  if(obj[10]!=null) { roleStatusDto.setMappedOn((Date)obj[10]); }
			  if(obj[11]!=null) { roleStatusDto.setMappedByIrlaNo(forcePersonnelRepository.
			  getByForcePersonnelId(forcePersonalIdLogin).get().getForceId()); }
			  if(obj[12]!=null) {
			  roleStatusDto.setTransactionId(String.valueOf(obj[12]).trim()); }
			 			
			roleStatusDtosList.add(roleStatusDto);
		
		}
		
		return roleStatusDtosList;
	}



	@Override
	public List<RefRole> getPermissionRoleList(String forcePersonalIdLogin, int roleCodeId) {
		String rCodeId= String.valueOf(roleCodeId);
		
		return refRoleRepo.findRolePermissionListAssignList(forcePersonalIdLogin,rCodeId);
	}



	@Override
	public List<RefRole> getMasterRolePermissionList(String forcePersonalIdLogin, int roleCodeId) {
		
        String rCodeId= String.valueOf(roleCodeId);
		return refRoleRepo.findRolemasterPermissionListAssignList(forcePersonalIdLogin,rCodeId);

	}



	@Override
	public List<UserRoleDto> getListOfRoles() {
		List<Object> userRoleDtoObjectList=refRoleRepo.findListOfRoles();
		List<UserRoleDto> userRoleDtoList = new ArrayList<>();
		/*
		 * if (objects.isEmpty()) { return new UserRoleDto(); }
		 */
		Iterator<Object> iterator = userRoleDtoObjectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			UserRoleDto userRoleDto=new UserRoleDto();
			if (obj[0] != null) {
				userRoleDto.setRoleId(Integer.parseInt(String.valueOf(obj[0]).trim()));
			}
			if (obj[1] != null) {
				userRoleDto.setRoleName(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				userRoleDto.setRoleCode(String.valueOf(obj[2]).trim());
			}
			
			
			  
			 			
			userRoleDtoList.add(userRoleDto);
		
		}
		
		return userRoleDtoList;
	}

}
