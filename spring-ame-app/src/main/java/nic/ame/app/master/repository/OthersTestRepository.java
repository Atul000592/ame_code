package nic.ame.app.master.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.Others;

@Repository
public interface OthersTestRepository extends JpaRepository<Others,Long>{

  //  List<Others> findAllByAmeId(String ameId);

    Optional<Others> findAllByAmeIdAndTestCode(String ameId, String string);
    List<Others> findByAmeId(String ameId);

    
} 
