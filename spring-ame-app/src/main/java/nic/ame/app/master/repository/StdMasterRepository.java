package nic.ame.app.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.StdMaster;


@Repository
public interface StdMasterRepository extends JpaRepository<StdMaster,Integer>{
    
}
