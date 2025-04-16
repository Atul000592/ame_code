package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.ViralMakers;

@Repository
public interface ViralMakersRepository extends JpaRepository<ViralMakers,String>{


    @Query(value = "select * from tt_viral_makers where ame_id=?1",nativeQuery = true)
    Optional<ViralMakers> findByAmeId(String ameId);

    
} 
