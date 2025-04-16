package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.NGOAMERequest;

public interface NGOAMERequestRepo extends JpaRepository<NGOAMERequest, String> {
	
	@Query(value = "select transaction_id from tt_ngo_ame_request where forwarded_to=?1",nativeQuery = true)
	List<String> findByForwardedToList(String forwardedTo);

}
