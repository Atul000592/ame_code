package nic.ame.app.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import nic.ame.app.master.model.AmeDeclarationIndividualDetails;



@Repository
public interface AmeDeclarationDetailRepoUser extends JpaRepository<AmeDeclarationIndividualDetails,Integer> {

	
	Optional<AmeDeclarationIndividualDetails>findByAmeId(String ameId);

	Optional<AmeDeclarationIndividualDetails> getByAmeId(String ameId);
	

	
}
