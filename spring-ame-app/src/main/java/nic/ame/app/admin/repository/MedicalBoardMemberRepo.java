package nic.ame.app.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.MedicalBoardMemberPK;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.constant.CommonConstant;



@Repository
public interface MedicalBoardMemberRepo extends JpaRepository<MedicalBoardMember, MedicalBoardMemberPK>{
	
	List<MedicalBoardMember> findByForcePersonalId(String forcePersonalId);
	
	 @Query(value = "select *  from medical_board_member mbm where mbm.force_personal_id=:force_personal_id", nativeQuery = true)
	 public List<MedicalBoardMember> getUserRolefromBoard(String force_personal_id);
	
	 @Query(value = "select * from tt_force_personnel where  force_personal_id=:force_personal_id", nativeQuery = true)
	 public Optional<ForcePersonnel> getUserRoleByDesignation(String force_personal_id);
	 
	 @Query(value = "select * from medical_board_member where role_name=:roleName and unit=:unit and force_no=:forceNo" ,nativeQuery = true)
     public Optional<MedicalBoardMember> findNameByboardAndUnit(String roleName,String forceNo,String unit);
	 
	 // List of board member by boardID
	 
	 @Query(value = "select * from medical_board_member where board_id=?1" ,nativeQuery = true)
     public List<MedicalBoardMember> getByBoardId(String boardId);
	 
	@Query(value = "select * from  medical_board_member mb where mb.force_personal_id=?1", nativeQuery = true)
	public List<Object> getMedicalBoardByForcePeronalIdBy(String loginForcePersonalId);
		
	 @Query(value = "select mbm.board_id,mbm.role_name,mb.board_at_force_no,mb.used_for,mb.place,mb.created_on,mbm.force_personal_id,mbm.name from medical_board_member mbm inner join medical_board mb  on mb.board_id = mbm.board_id\n"
	 		+ "where mbm.force_personal_id=?1 and mbm.status_code=?2",nativeQuery = true)
	 List<Object> findBoardIdByForcepersonalId(String forcePersonalId,int statusCode);

       
	 
	 
	 @Query(value = "select mbm.board_id,mbm.role_name,mb.board_at_force_no,mb.used_for,mb.place,mb.created_on,mbm.force_personal_id,mbm.name from medical_board_member mbm inner join medical_board mb  on mb.board_id = mbm.board_id\n"
		 		+ "where mbm.board_id=?1 and mbm.status_code=?2 and mbm.force_personal_id=?3",nativeQuery = true)
		 List<Object> findBoardIdByLoginForcePersonnelBoardId(String boardId,int statusCode,String forcePersonnel);

	 
	 
	 
	 
	 
	 
	 @Query(nativeQuery = true,value = "select distinct mbm.board_year  from medical_board_member mbm where mbm.board_id =?1")
	 public String getBoardYear(String boardId);
	 
	 @Query(nativeQuery = true,value = "select mbm.board_id,mbm.role_name,mbm.status_code,mbm.board_year,\n"
	 		+ "	tmu.unit_name,tmf.force_name,mb.created_by,mb.created_on,mb.used_for,mb.gazetted_flag\n"
	 		+ "	from medical_board_member mbm \n"
	 		+ "	inner join medical_board mb on mbm.board_id =mb.board_id \n"
	 		+ "	inner join tm_force tmf on tmf.force_no =cast(mbm.force_no as int)\n"
	 		+ "	inner join tm_unit tmu on cast(mb.board_at_force_no as int)=tmu.force_no and mb.place=tmu.unit_id\n"
	 		+ "	where mbm.board_id=?1")
	 public List<Object> getBoardDetailByBoardId(String boardId);
	 
	 //============================get board details by boardId and Who created the board========================//
	 @Query(nativeQuery = true,value = "select mb.board_id ,mb.board_year ,fp.force_id ,fp.\"name\",fp.\"rank\",mb.created_on,mb.used_for,\n"
	 		+ "tmf.force_name as created_for,tu.unit_name,mb.gazetted_flag \n"
	 		+ "from medical_board mb \n"
	 		+ "inner join tm_force tmf on cast (mb.board_at_force_no as int) = tmf.force_no\n"
	 		+ "inner join tm_unit tu on tmf.force_no =tu.force_no and tu.unit_id =mb.place \n"
	 		+ "inner join tt_force_personnel fp on fp.force_personal_id =mb.created_by \n"
	 		+ "where mb.board_id=?1")
	 public List<Object> getBoardDetailsByCreatedByAndBoardId(String boardId);
	 
	 
	 @Query(nativeQuery = true,value="select fp.force_personal_id,fp.force_id,fp.name,fp.designation,mbm.status_code,mbm.board_id,tmf.force_name,tu.unit_name,mbm.resereve_flag "
		 		+ " from medical_board_member mbm  inner join tt_force_personnel fp on mbm.force_personal_id =fp.force_personal_id 	"
		 		+ "inner join tm_force tmf on fp.force_no=tmf.force_no  "
		 		+ "inner join tm_unit tu on tu.force_no =tmf.force_no and tu.unit_id =fp.unit  "
		 		+ "where mbm.board_id=?1 and  mbm.role_name=?2 and mbm.status_code!='2'")
		 
		 public List<Object> listOfReservePersonalByBoardIdAndAlternateMedicalRoleId(String boardId,String roleName);
	 
	@Query(nativeQuery = true,value = "select * from medical_board_member mbm WHERE  mbm.force_personal_id=?1 and mbm.board_id=?2 and mbm.role_name=?3") 
	Optional<MedicalBoardMember> findByForcePersonalAndBoardIdToTransferRoleTo(String roleTransferTo, String boardId,String roleId);
//==========================================================new==============================================================//       
	@Query(nativeQuery=true,value="select tmf.force_name,fp.force_id,mbm.name,mbm.designation,rrm.role_name,mbm.force_personal_id,mbm.board_id\n"
			+ "from medical_board_member mbm\n"
			+ "inner join medical_board mb on mb.board_id = mbm.board_id\n"
			+ "inner join tt_force_personnel fp on mbm.force_personal_id = fp.force_personal_id\n"
			+ "inner join ref_role_medical rrm on rrm.id = cast(mbm.role_name as int)\n"
			+ "inner join tm_force tmf on tmf.force_no =cast(mb.board_at_force_no as int)\n"
			+ "where mbm.board_id=?1")
	List<Object> getListOfAllBoardMembersByBoardId(String boardId);
	
//======================================================Only Active Member For Final Report Print====================================================================//
	
/*	@Query(nativeQuery = true,value = "select tmf.force_name,fp.force_id,mbm.name,mbm.designation,rrm.role_name,mbm.force_personal_id,mbm.board_id "
			+ "from medical_board_member mbm "
			+ "inner join medical_board mb on mb.board_id = mbm.board_id "
			+ "inner join tt_force_personnel fp on mbm.force_personal_id = fp.force_personal_id"
			+ " inner join ref_role_medical rrm on rrm.id = cast(mbm.role_name as int)"
			+ " inner join tm_force tmf on tmf.force_no =cast(mb.board_at_force_no as int) where mbm.board_id=?1 and mbm.status_code=1")
	List<Object> getBoardMemberDetailsForFinalSubmission(String boardId);
	
*/
	
	@Query(nativeQuery = true,value = ""
			+ "select tmf.force_name,fp.force_id,fp.name,tr.rank_full_name,rrm.role_name,fp.force_personal_id,mbm.board_id "
			+ "from medical_board_member mbm "
			+ "inner join medical_board mb on mb.board_id = mbm.board_id "
			+ "inner join tt_force_personnel fp on mbm.force_personal_id = fp.force_personal_id "
			+ "inner join tm_rank tr on fp.rank=tr.id"
			+ " inner join ref_role_medical rrm on rrm.id = cast(mbm.role_name as int)"
			+ " inner join tm_force tmf on tmf.force_no =cast(mb.board_at_force_no as int) where mbm.board_id=?1 and mbm.status_code=1")
	List<Object> getBoardMemberDetailsForFinalSubmission(String boardId);
	
	@Query(value = "select mbm.force_personal_id , md.board_id ,tmf.force_name ,tu.unit_name ,md.board_year,md.used_for,mbm.role_name,md.created_on,mbm.status_code\n"
			+ "from medical_board md inner join medical_board_member mbm on mbm.board_id = md.board_id \n"
			+ "inner join tm_force tmf on cast( md.board_at_force_no as int) =tmf.force_no\n"
			+ "inner  join tm_unit tu  on cast(md.board_at_force_no as int) = tu.force_no \n"
			+ "and tu.unit_id = md.place where mbm.force_personal_id=?1 and md.gazetted_flag=0\n"
			+ "order by md.created_on desc",nativeQuery = true)
	List<Object> findAllBoardAssociatedWithForcePersonnelByForcePersonnelId(String forcePersonnel);

	List<MedicalBoardMember> findByForcePersonalIdAndStatusCode(String loginForcePersonnelId, Integer activeFlagYes);

	List<MedicalBoardMember> findByBoardId(String boardId);

	@Query(nativeQuery = true,value="select * from medical_board_member where force_personal_id=?1 and board_id=?2 and role_name=?3")
	Optional<MedicalBoardMember> findbyForcePersonelAndBoardIdAndRoleName(String forcePersonalId, String boardId,
			String i);

	
	@Query(value = "select mbm.force_personal_id , md.board_id ,tmf.force_name ,tu.unit_name ,md.board_year,md.used_for,mbm.role_name,md.created_on,mbm.status_code\n"
			+ "from medical_board md inner join medical_board_member mbm on mbm.board_id = md.board_id \n"
			+ "inner join tm_force tmf on cast( md.board_at_force_no as int) =tmf.force_no\n"
			+ "inner  join tm_unit tu  on cast(md.board_at_force_no as int) = tu.force_no \n"
			+ "and tu.unit_id = md.place where mbm.force_personal_id=?1 and md.gazetted_flag=1 \n"
			+ "order by md.created_on desc",nativeQuery = true)
	List<Object> findAllBoardAssociatedBoardForGOWithForcePersonnelByForcePersonnelId(String forcePersonnelId);	
	
	@Query(value = "select mbm.force_personal_id , md.board_id ,tmf.force_name ,tu.unit_name ,md.board_year,md.used_for,mbm.role_name,md.created_on,mbm.status_code\n"
			+ "from medical_board md inner join medical_board_member mbm on mbm.board_id = md.board_id \n"
			+ "inner join tm_force tmf on cast( md.board_at_force_no as int) =tmf.force_no\n"
			+ "inner  join tm_unit tu  on cast(md.board_at_force_no as int) = tu.force_no \n"
			+ "and tu.unit_id = md.place where mbm.force_personal_id=?1 and mbm.board_id=?2 and md.gazetted_flag=1 \n"
			+ "order by md.created_on desc", nativeQuery = true)
	Optional<Object> findBoardAssociatedBoardForGOWithForcePersonnelByForcePersonnelId(String forcePersonnelId,String boardId);

	@Query(value = "SELECT board_id FROM medical_board_member " +
            "WHERE force_personal_id = :forcePersonalId " +
            "AND role_name = :roleCode " +
            "ORDER BY board_year", 
    nativeQuery = true)
	    List<String> findBoardIdsByForcePersonalIdAndRole(String forcePersonalId, 
	                                                       String roleCode);
}
