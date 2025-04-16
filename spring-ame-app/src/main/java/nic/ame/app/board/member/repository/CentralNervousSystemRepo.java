package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.CentralNervousSystem;


@Repository
public interface CentralNervousSystemRepo extends JpaRepository<CentralNervousSystem, String> {

	
	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_central_nervous_system where ame_id=?1"
	  ) Optional<CentralNervousSystem> findByAmeId(String ameId);
	 
}
