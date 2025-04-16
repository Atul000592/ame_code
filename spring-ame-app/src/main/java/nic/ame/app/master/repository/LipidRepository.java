package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import nic.ame.app.master.medical.model.Lipid;


public interface LipidRepository extends JpaRepository<Lipid, String> {

	Optional<Lipid> findByAmeId(String ameId);
	
	
	

}
