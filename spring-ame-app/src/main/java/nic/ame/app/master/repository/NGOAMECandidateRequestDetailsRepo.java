package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import nic.ame.app.master.medical.model.NGOAMECandidateRequestDetails;


@Repository
public interface NGOAMECandidateRequestDetailsRepo extends JpaRepository<NGOAMECandidateRequestDetails,Integer>{

	@Query(nativeQuery = true,value = "Select candidate_forcepersonal_id from tt_ngo_ame_candidate_request_details where transaction_id=?1")
	List<String> getNgoListWithTransationalId(String transationalId);

 
	//delete the record from the tt_ngo_ame_candidate_request_details
    @Modifying
    @Query(value="delete from tt_ngo_ame_candidate_request_details b where b.transaction_id=?1",nativeQuery = true)
    void deleteBooks(String transationalId); 
    
	
}

