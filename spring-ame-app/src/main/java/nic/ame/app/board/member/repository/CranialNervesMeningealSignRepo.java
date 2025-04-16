package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.CranialNervesMeningealSign;

@Repository
public interface CranialNervesMeningealSignRepo extends JpaRepository<CranialNervesMeningealSign,String> {
	
	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_cranial_nerves_meningeal_sign where ame_id=?1"
	  ) Optional<CranialNervesMeningealSign> findByAmeId(String ameId);
	 
}
