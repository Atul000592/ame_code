package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.BloodSugarHbA1c;

@Repository
public interface BloodSugarHbA1cRepository extends JpaRepository<BloodSugarHbA1c, String> {

	Optional<BloodSugarHbA1c> findByAmeId(String ameId);

}
