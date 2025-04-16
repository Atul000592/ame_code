package nic.ame.app.master.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.AmeParameters;

@Repository
public interface AmeParametersRepository extends JpaRepository<AmeParameters, Integer>{

	
	@Query(nativeQuery = true,value = "select ttap.value from ref_tt_ame_parameters ttap where ttap.id=?1")
	public String getAmeParameterValue(int id);
}
