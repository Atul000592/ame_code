package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.AmeAppointmentDetails;

public interface AmeAppointmentDetailsRepo extends JpaRepository<AmeAppointmentDetails, Integer > {
	
	@Query(nativeQuery = true,value = "select * from ame_appointment_details  aad where aad.ame_id=?1")
	Optional<AmeAppointmentDetails> findByAmeId(String ameId);
	
	
	
}
