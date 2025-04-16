package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.BloodSugarF;
@Repository
public interface BloodSugarFRepository extends JpaRepository<BloodSugarF, String> {

	Optional<BloodSugarF> findByAmeId(String ameId);

}
