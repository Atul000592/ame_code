package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.CompleteBloodCount;

public interface CompleteBloodCountCBCRepository extends JpaRepository<CompleteBloodCount, String>{

	Optional<CompleteBloodCount> findByAmeId(String ameId);

}
