package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.TTDealingHand;

public interface TTDealingHandRepo extends JpaRepository<TTDealingHand, Integer>{

	
	@Query(nativeQuery = true,value = "select fp.force_id,fp.\"name\",fp.designation,tda.board_id,tda.status from tt_dealing_assistant tda inner join tt_force_personnel fp on tda.force_personal_id=fp.force_personal_id\r\n"
			+ "where tda.board_id=?1")
	List<Object> getDealingHandByBoardId(String boardId);

	
	
	@Query(nativeQuery = true,value = "select tda.board_id ,tmf.force_name,tu.unit_name ,tda.status,tda.force_personal_id from tt_dealing_assistant tda inner join medical_board mb on tda.board_id=mb.board_id \r\n"
			+ "inner join tm_force tmf on tmf.force_no =cast(mb.board_at_force_no as int)\r\n"
			+ "inner join tm_unit tu on tu.force_no =tmf.force_no and tu.unit_id = mb.place where tda.force_personal_id=?1 and tda.status=?2")
	List<Object> getDealingHandByForcePersonalId(String forcePersonalId,int status);
	
	@Query(nativeQuery = true,value = "select * from tt_dealing_assistant tda where tda.board_id=?1 and tda.force_personal_id=?2")
	Optional<TTDealingHand> getExistingDealingHandByBoardIdAndForcePersonalId(String boardId,String forcePersonalId);
}
