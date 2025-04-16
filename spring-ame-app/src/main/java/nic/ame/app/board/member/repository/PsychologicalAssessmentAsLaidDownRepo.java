package nic.ame.app.board.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import java.util.List;



@Repository
public interface PsychologicalAssessmentAsLaidDownRepo extends JpaRepository<PsychologicalAssessmentAsLaidDown, String> {
	
	
	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_psychological_assessment_as_laid_down where ame_id=?1"
	  ) Optional<PsychologicalAssessmentAsLaidDown> findByAmeId(String ameId);
	 

	 
}
