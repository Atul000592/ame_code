package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.AmePendingStatus;

public interface AmePendingStatusRepository extends JpaRepository<AmePendingStatus, Integer> {
	
	 
	Optional<AmePendingStatus> findByAmeId(String ameId);
	
	//AmePendingStatus findByAmeId(String ameId);

}
