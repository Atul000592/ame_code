package nic.ame.app.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import nic.ame.app.master.medical.model.OtherTest;

public interface OtherTestRepository extends JpaRepository<OtherTest, Integer>{
	
	
}
