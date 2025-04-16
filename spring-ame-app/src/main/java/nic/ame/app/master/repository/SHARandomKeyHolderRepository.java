package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nic.ame.app.master.model.SHARandomKeyHolder;

public interface SHARandomKeyHolderRepository extends CrudRepository<SHARandomKeyHolder, Integer>  {

	Optional<SHARandomKeyHolder> findById(String id);

	void deleteById(String id);

	
	
}
