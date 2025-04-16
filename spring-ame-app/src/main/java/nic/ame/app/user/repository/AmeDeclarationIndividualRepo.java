package nic.ame.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.AmeDeclarationIndividualModel;

@Repository
public interface AmeDeclarationIndividualRepo extends JpaRepository<AmeDeclarationIndividualModel, Integer>
{
	
	@Query(nativeQuery = true,value = "select * from ame_declaration_individual where status in ('P','A')  and force_personal_id=?1")
	Optional<AmeDeclarationIndividualModel>findByStatusRepost(String forcePersonalId);

}
