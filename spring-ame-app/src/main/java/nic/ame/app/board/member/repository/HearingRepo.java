package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.Hearing;

@Repository
public interface HearingRepo extends JpaRepository<Hearing,String> {

	
	
	  @Query(nativeQuery = true,value =
	  "SELECT * FROM tt_medical_examination_hearing where ame_id=?1")
	  Optional<Hearing> findByAmeId(String ameId);
	 
}
