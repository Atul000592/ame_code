package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;

public interface AmeFinalReportBoardmemberDetailsRepository extends JpaRepository<AmeFinalReportBoardMemberDetails, Integer>{
	
	@Query(nativeQuery = true,value="select * from ame_final_report_board_member_details where fk_ame_id=?1 and role_name=?2")
	AmeFinalReportBoardMemberDetails findByAmeIdAndRoleName(String ameId,String roleName);
    
	
	List<AmeFinalReportBoardMemberDetails> findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(String boardId,String roleName,String boardMemberForcePersonnelId);
	
}
