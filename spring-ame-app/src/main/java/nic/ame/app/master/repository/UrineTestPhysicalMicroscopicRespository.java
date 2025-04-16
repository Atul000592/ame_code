package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;

public interface UrineTestPhysicalMicroscopicRespository extends JpaRepository<UrineTestPhysicalMicroscopic,String> {

	Optional<UrineTestPhysicalMicroscopic> findByAmeId(String ameId);

}
