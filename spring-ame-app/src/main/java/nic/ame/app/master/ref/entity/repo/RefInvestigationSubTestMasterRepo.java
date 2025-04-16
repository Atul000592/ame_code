package nic.ame.app.master.ref.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.ref.entity.RefInvestigationSubTestMaster;



public interface RefInvestigationSubTestMasterRepo extends JpaRepository<RefInvestigationSubTestMaster, Integer> {

	List<RefInvestigationSubTestMaster> findBySubTestCode(String testCode);

}
