package nic.ame.app.master.dto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAndDateDtoRepo extends JpaRepository<PlaceAndDateDto, String> {

	 
	 @Query(value = " select * from ame_individual_date_place where ame_id=:ameId",nativeQuery = true)     
	 	Optional<PlaceAndDateDto> getPlaceAndDateDto(String ameId);
}
