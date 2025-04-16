package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;



public interface AmeDeclarationIndividualDetailsRepo extends JpaRepository<AmeDeclarationIndividualDetails , Integer> {
	
	
	
	
	
	@Query(nativeQuery = true,value = "select adi.* FROM ame_declaration_individual_details adi left join ame_declaration_individual ad on\r\n"
			+ "adi.ame_id=ad.ame_id where ad.flag='Y' and ad.status='P'")
	List<AmeDeclarationIndividualDetails> findAllByFinalDeclaration();
	
	
	
	Optional<AmeDeclarationIndividualDetails>findByAmeId(String ameId);
	
	@Query(value = "select count(*)from tt_ame_declaration_individual adi inner join ame_application_flow_status aafs on adi.ame_id =aafs.ame_id  \r\n"
			+ "where adi.ame_final_status=1 and aafs.board_id =?1",nativeQuery = true)
	 int getAmeCompleteCountByBoardId(String boardId);

	

	
}
