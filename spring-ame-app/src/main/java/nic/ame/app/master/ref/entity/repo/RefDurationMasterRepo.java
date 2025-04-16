package nic.ame.app.master.ref.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.RefDurationMaster;

@Repository
public interface RefDurationMasterRepo extends JpaRepository<RefDurationMaster,Integer> {

}
