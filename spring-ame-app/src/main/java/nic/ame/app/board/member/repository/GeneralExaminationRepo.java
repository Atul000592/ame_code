package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.GeneralExamination;

@Repository
public interface GeneralExaminationRepo extends JpaRepository<GeneralExamination,String>{

	
	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_general_examination where ame_id=?1")
	  Optional<GeneralExamination> findByAmeId(String ameId);
	 
}
