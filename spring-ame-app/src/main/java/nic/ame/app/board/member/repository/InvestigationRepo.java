package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.Investigation;

public interface InvestigationRepo extends JpaRepository<Investigation, Integer>{
	@Query(nativeQuery = true,value = "SELECT * FROM medical_examination_investigation where ame_id=?1")
	Optional<Investigation> findByAmeId(String ameId);


}
