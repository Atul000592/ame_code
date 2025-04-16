package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.LiverFunctionTest;

public interface LiverFunctionTestRepository extends JpaRepository<LiverFunctionTest, String>{

	Optional<LiverFunctionTest>  getByAmeId(String ameId);

    Optional<LiverFunctionTest> findByAmeId(String ameId);

}
