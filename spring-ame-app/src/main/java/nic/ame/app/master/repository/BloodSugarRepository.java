package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.BloodSugar;

public interface BloodSugarRepository extends JpaRepository<BloodSugar,String> {

	Optional<BloodSugar> findByAmeId(String ameId);

}
