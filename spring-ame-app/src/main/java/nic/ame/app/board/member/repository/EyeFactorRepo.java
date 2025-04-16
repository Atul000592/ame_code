package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.EyeFactor;


public interface EyeFactorRepo  extends JpaRepository<EyeFactor, String>{
	
	  @Query(nativeQuery = true,value =
	  "SELECT * FROM tt_medical_examination_eyefactor where ame_id=?1")
	  Optional<EyeFactor> findByAmeId(String ameId);
	 
 
	/*
	 * void deleteByAmeId(String ameId);
	 */
}
