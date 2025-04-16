package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import nic.ame.app.master.model.AmeApprovalProcess;

public interface AmeApprovalProcessRepository extends CrudRepository<AmeApprovalProcess, Integer> {

	@Query(value = "select * from tt_ame_approval_process where fk_receiver_role_id=15 and fk_sender_role_id=2 and is_esign_status=1", nativeQuery = true)
	List<AmeApprovalProcess> findbyBoardId(String boardId);

//	@Query(value = "SELECT t.id, t.ame_id, t.used_for,t.sender_remark, t.is_esign_status, t.status_code, t.fk_board_id, " +
//            "t.fk_receiver_role_id, t.fk_sender_role_id, t.created_on, t.is_active_status, t.created_by, " +
//            "r1.role_name AS receiver_role, r2.role_name AS sender_role " +
//            "FROM tt_ame_approval_process t " +
//            "JOIN public.medical_board b ON t.fk_board_id = b.board_id " + 
//            "JOIN public.ref_role_medical r1 ON t.fk_receiver_role_id = r1.id " +  
//            "JOIN public.ref_role_medical r2 ON t.fk_sender_role_id = r2.id " +  
//            "JOIN public.tm_ame_approval_status s ON t.status_code = s.id " + 
//            "WHERE t.fk_board_id = ?1 AND t.is_esign_status = 1", nativeQuery = true)
//       List<AmeApprovalProcess> findByBoardIdAndEsignStatus(String boardId);

	@Query(value = "SELECT ame_id FROM tt_ame_approval_process " + "WHERE fk_board_id IN (:boardIds) "
			+ "AND fk_receiver_role_id = CAST(:roleCode AS bigint) " + "AND is_esign_status = true "
			+ "AND r_code = '2'", nativeQuery = true)
	List<String> findAmeIdByRoleCodeAndBoardId(@Param("boardIds") List<String> boardIds,
			@Param("roleCode") String roleCode);

	@Query(value = "SELECT ame_id FROM tt_ame_approval_process WHERE fk_board_id IN (:boardIds) AND fk_receiver_role_id = '15' AND fk_sender_role_id = '15'AND is_esign_status = true ", nativeQuery = true)
	List<String> findAmeIdByRoleCodeAndBoardIdGOCompleted(@Param("boardIds") List<String> boardIds);

	AmeApprovalProcess findByAmeId(String ameId);

}
