package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.BloodSugarPP;
@Repository
public interface BloodSugarPPRepository extends JpaRepository<BloodSugarPP,String>{

	Optional<BloodSugarPP> findByAmeId(String ameId);

}
