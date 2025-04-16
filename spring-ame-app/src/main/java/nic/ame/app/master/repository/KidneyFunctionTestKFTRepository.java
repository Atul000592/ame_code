package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.KidneyFunctionTest;

public interface KidneyFunctionTestKFTRepository extends JpaRepository<KidneyFunctionTest, String> {

	 Optional<KidneyFunctionTest> findByAmeId(String ameId);
		// TODO Auto-generated method stub
		
	

}
