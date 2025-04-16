package nic.ame.app.master.ref.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import nic.ame.app.master.ref.entity.RefInvestigationTestMaster;

public interface RefInvestigationMasterRepo  extends JpaRepository<RefInvestigationTestMaster, Integer>{

	List<RefInvestigationTestMaster> findByTestCode(String testCode);
	

}
