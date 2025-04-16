package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.Abdomen;

public interface AbdomenRepo extends JpaRepository<Abdomen,String> {

	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_abdomen where ame_id=?1")
	  Optional<Abdomen> findByAmeId(String ameId);
	 
}
