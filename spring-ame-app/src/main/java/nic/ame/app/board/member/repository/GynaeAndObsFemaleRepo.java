package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.GynaeAndObsFemale;

@Repository
public interface GynaeAndObsFemaleRepo extends JpaRepository<GynaeAndObsFemale, String>{

	
	  @Query(nativeQuery = true,value =
	  "select * from tt_medical_examination_gynae_and_obs_female mgao where mgao.ame_id=?1"
	  ) Optional<GynaeAndObsFemale> findByAmeId(String ameId);
	 
} 
