package nic.ame.app.board.member.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.PhysicalMeasurement;

@Repository
public interface PhysicalMeasurmentRepo extends JpaRepository<PhysicalMeasurement, String>{

	/*f*/
	
}
