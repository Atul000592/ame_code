package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import nic.ame.app.master.medical.model.Reflexes;


@Repository
public interface ReflexesRepo extends JpaRepository<Reflexes, String> {

	
	@Query(nativeQuery = true,value = "select * from tt_medical_examination_reflexes where ame_id=?1")
	Optional<Reflexes> findByAmeId(String ameId);
	
}
