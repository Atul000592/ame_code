package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.model.AmeStatusCode;

public interface AmeStatusCodeRepository extends JpaRepository<AmeStatusCode,Integer>{
	
	Optional<AmeStatusCode> getByCode(int code);

}
