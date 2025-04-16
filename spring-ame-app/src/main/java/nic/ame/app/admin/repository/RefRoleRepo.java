package nic.ame.app.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.RefRole;

@Repository
public interface RefRoleRepo extends JpaRepository<RefRole, String>{

	
	
	
	@Query(nativeQuery = true,value = "Select * from ref_role rr where EXISTS\r\n"
			+ "(select ur.role_id from user_role ur where  ur.force_personal_id=?1 and ur.role_id=rr.role_id)")
	List<RefRole> findByUserRoleName(String forcePersonalId);
	
	
	/*
	 * @Query(nativeQuery = true,
	 * value="select * from ref_role rr where rr.hierarchy_in_role >(select min(rr1.hierarchy_in_role) from ref_role rr1,user_role ur  where rr1.role_id=ur.role_id and  ur.force_personal_id=?1\r\n"
	 * + ")") List<RefRole> findListByHierarchy(String forcePersonalIdLogin);
	 */
	
	@Query(nativeQuery = true,value="select * from ref_role rr \r\n"
			+ "where rr.hierarchy_in_role =(select min(rr1.hierarchy_in_role)+1 from ref_role rr1,user_role ur \r\n"
			+ "where rr1.role_id=ur.role_id and  ur.force_personal_id=?1 and ur.role_id=?2)")
	List<RefRole> findListByHierarchy(String forcePersonalIdLogin,String roleCodeId);
	
	
//======================role assign  permission list=====================// 
	@Query(nativeQuery = true,value="select * from ref_role rr where rr.hierarchy_in_role >=(select (ur.permission_to_assign_role)+1 "
			+ " from ref_role rr1,user_role ur where rr1.role_id=ur.role_id and  ur.force_personal_id=?1 and ur.role_id=?2)")
	List<RefRole> findRolePermissionListAssignList(String forcePersonalIdLogin,String roleCodeId);
	
	//==========================role assignment master roleList=======================//
	@Query(nativeQuery = true,value="select * from ref_role rr where rr.hierarchy_in_role >=(select (ur.permission_to_assign_role)"
			+ " from ref_role rr1,user_role ur where rr1.role_id=ur.role_id and  ur.force_personal_id=?1 and ur.role_id=?2)")
	List<RefRole> findRolemasterPermissionListAssignList(String forcePersonalIdLogin,String roleCodeId);
	
	
	
	
	@Query(nativeQuery = true,value="select rr.role_id ,rr.role_name ,fp.name,fp.force_no ,"
			+ "fp.unit,ur.status,fp.force_id,fp.force_personal_id,ur.user_role_id,fp.rank,ur.mapped_on,ur.mapped_by,ur.transactional_id\r\n"
			+ "from user_role ur inner join ref_role rr on ur.role_id=rr.role_id\r\n"
			+ "inner join tt_force_personnel fp on ur.force_personal_id =fp.force_personal_id \r\n"
			+ "where  ur.mapped_by=?1 and ur.role_id in ('3','10','11','8','9','5')")
	List<Object> findRoleListByForcePersonalId(String loginForcePersonalId);

	@Query(nativeQuery = true,value="select count(*)from user_role ur inner join ref_role rr on ur.role_id=rr.role_id "
			+ "inner join tt_force_personnel fp on ur.force_personal_id =fp.force_personal_id where  ur.mapped_by=?1 and ur.status=1")
	int activeRoleCount(String loginForcePersonalId);
	
	@Query(nativeQuery = true,value="select count(*)from user_role ur inner join ref_role rr on ur.role_id=rr.role_id\r\n"
			+ "inner join tt_force_personnel fp on ur.force_personal_id =fp.force_personal_id where  ur.mapped_by=?1 and ur.status=0")
	int inActiveRoleCount (String loginForcePersonalId);

	@Query(nativeQuery = true,value = "select * from ref_role rr where rr.hierarchy_in_role>=0 and rr.hierarchy_in_role <=4 order by rr.hierarchy_in_role  asc")
	List<RefRole> getAllRoleByHierarchy();
	
	@Query(nativeQuery = true,value = "select rr.hierarchy_in_role  from ref_role rr where rr.role_id =?1")
	int getHierarchyInRole(String roleId);
	
	
	@Query(nativeQuery = true,value = "select rr.role_id  from ref_role rr where rr.role_code=?1")
	String getRoleIdByRoleCode(String roleCode);

    @Query(nativeQuery = true,value = "select role_name from ref_role where role_id=?1")
	String findByRoleId(String rCode);
    
    @Query(nativeQuery = true,value = "select role_code from ref_role where role_id=?1")
    Optional<String> findRoleCodeByRoleId(String role_id);
    
    
    @Query(nativeQuery = true,value = "select * from ref_role where role_code=?1")
    List<RefRole> findListOfRoleByRoleCode(String roleCode);

    @Query(nativeQuery = true,value = "select * from ref_role where ref_admin_flag=?1")
	List<RefRole> findRoleByAdminFlag(Integer roleAdminFlagYes);
    
  
    @Query(nativeQuery = true,value = "select role_id,role_name,role_code from ref_role")
   	List<Object> findListOfRoles();
   
    
}
