package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.BloodSugarRandom;
@Repository
public interface BloodSugarRandomRepository extends JpaRepository<BloodSugarRandom, String>{

	Optional<BloodSugarRandom> findByAmeId(String ameId);

}
