package nic.ame.app.master.repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole,Integer> {

	@Query(nativeQuery=true, value = "select rr.role_code from ref_role rr left join user_role ur on ur.role_id = rr.role_id\r\n"
			+ "where ur.force_personal_id=?1 and ur.status=?2")
	Set<String> findByForcePersonalId(String forcePersonalId,int status);
	
	
	@Query(nativeQuery=true, value = "select count(*)from ref_role rr left join user_role ur on ur.role_id = rr.role_id\r\n"
			+ "where ur.force_personal_id=?1 and ur.status='1' and rr.role_code=?2 ")
	int findByForcePersonalIdDoctor(String forcePersonalId,String BOARD_MEMBER_ROLE_CODE);
	
	
	
	@Query(nativeQuery = true,value = "select fp.force_personal_id,fp.\"name\" from user_role ur left join tt_force_personnel fp on fp.force_personal_id = ur.force_personal_id\r\n"
			+ "where ur.mapped_by_force_no=2 and ur.allocated_unit=2 and ur.role_id='3'")
    Optional<Object> getAmaByForceAndUnitMapping(String forcePersonalId);
	
	@Query(nativeQuery = true,value="select * from  user_role where user_name=?1")
	List<UserRole> findByUserName(String userName);
	
	 @Query(value = "select * from user_role ur where ur.user_role_id=?1 ",nativeQuery = true)
	  Optional<UserRole> findbyUserRoleById(int userRoleId);
	
	 @Query(value = "select rr.role_name,ur.status,ur.mapped_to_force,ur.mapped_by_name,ur.mapped_on  from user_role ur inner join ref_role rr on ur.role_id =rr.role_id \r\n"
	 		+ "where ur.role_id in ('3','10','11','8','9','5')and ur.force_personal_id=?1",nativeQuery = true)
	 List<Object> userRoleHistory(String forcePersonalId);
	 
	 
	 @Query(value = "select count(*) from user_role ur where ur.role_id=?1 and ur.status=?2",nativeQuery = true)
	    int findRoleStatusByRoleId(String roleId,int statusCode);
	 
	 @Query(value = "select count(*)  from user_role ur where ur.force_personal_id=?1 and ur.role_id=?2 and ur.status='1'",nativeQuery = true)
     int getRoleExistStatus(String forcePersonalId,String roleId);
	 
	 @Modifying
	 @Query(value = "update user_role  set status=?1 where user_role_id=?2" ,nativeQuery = true)
	 public void updateUserStatusByUserRoleId(int status,int userRoleId);
	 
	 
	 @Query(value = "select ur.transactional_id from user_role ur where ur.role_id =?1 and ur.force_personal_id =?2 and ur.status=?3",nativeQuery = true)
	 Optional<String> findTransactionalIdByRoleIdAndForcePersonalId(String roleId,String forcePersonalId,int adminFlag,int activeInavtiveStatus);


	UserRole findByUserRoleId(int parseInt);
	
	
	@Query(nativeQuery = true,value = "select * from user_role ur  where  ur.mapped_by =?1 and ur.role_id =?2 and ur.allocated_unit =?3 and ur.mapped_to_force =?4")
	List<UserRole> getControllingOfficerListByMappedBy(String forcePersonalId,String roleId,long unit,String forceNo);


	@Query(value="select fp.force_personal_id ,fp.force_id,fp.name,fp.designation,f.force_name_english,tu.unit_name,ur.role_id \n" + //
				"\tfrom user_role ur \n" + //
				"\tinner join tt_force_personnel fp on ur.force_personal_id=fp.force_personal_id\n" + //
				"\tinner join force f on f.force_id=fp.force_no\n" + //
				"\tinner join tm_unit tu on tu.force_id=fp.force_no and tu.unit_id=fp.unit\n" + //
				"\twhere ur.mapped_by=?1 and ur.role_id=?2 and ur.status=?3",nativeQuery = true)
    List<Object> findByForcePersonalId(String loginForcePersonalId, String roleCode, int roleStatus);

    @Query(value ="select fp.force_personal_id ,tr.rank_full_name,tu.unit_name ,rf.force_name,fp.name,fp.force_id ,ur.role_id from user_role ur inner join tt_force_personnel fp on fp.force_personal_id =ur.force_personal_id inner join  tm_force rf on rf.force_no  =fp.force_no inner join tm_unit tu on fp.force_no =tu.force_no and tu.unit_id  =fp.unit  inner join tm_rank tr on tr.id=fp.rank\r\n"
    		+ " where ur.mapped_by =?1 and ur.role_id =?2 and ur.status =?3 " ,nativeQuery = true)
	List<Object> findControllingUserRole(String loginForcePersonalId, String roleId, Integer activeFlagYes);
    
    @Query(value ="select fp.force_personal_id ,tr.rank_full_name,tu.unit_name ,rf.force_name,fp.name,fp.force_id ,ur.role_id from user_role ur inner join tt_force_personnel fp on fp.force_personal_id =ur.force_personal_id inner join  tm_force rf on rf.force_no  =fp.force_no inner join tm_unit tu on fp.force_no =tu.force_no and tu.unit_id  =fp.unit  inner join tm_rank tr on tr.id=fp.rank\r\n"
    		+ " where ur.mapped_by =?1 and ur.role_id =?2 and ur.status =?3 and ur.force_personal_id=?4" ,nativeQuery = true)
	List<Object> findControllingUserRoleByForcePersonnelId(String loginForcePersonalId, String roleId, Integer activeFlagYes,String controllingOfficerForcePersonnelId);


   @Query(value = "select * from user_role where force_personal_id=?1",nativeQuery = true)
	List<UserRole> findByForcePersonalId(String forcePersonalId);

	
	
     
} 
