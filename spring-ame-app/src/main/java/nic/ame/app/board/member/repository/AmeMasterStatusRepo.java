package nic.ame.app.board.member.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.AmeMasterStatus;


@Repository
public interface AmeMasterStatusRepo extends JpaRepository<AmeMasterStatus, String> {
	
	@Query(nativeQuery = true,value = "select * from ame_master_status where ame_id=?1")
	Optional<AmeMasterStatus> findByAmeId(String ameId);
	
	@Query(nativeQuery = true,value = "select * from ame_master_status where status=?1")
	Optional<AmeMasterStatus> findByAmeIdStatusCompleted(String status);
	

	
	

}
