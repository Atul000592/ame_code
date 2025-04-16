package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.ThyroidProfile;

@Repository
public interface ThyroidProfileRepository extends JpaRepository<ThyroidProfile,String> {

    Optional<ThyroidProfile> findByAmeId(String ameId);
    
}
