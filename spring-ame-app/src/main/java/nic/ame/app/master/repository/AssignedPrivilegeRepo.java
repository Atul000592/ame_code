package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.AssignedPrivilege;

public interface AssignedPrivilegeRepo extends JpaRepository<AssignedPrivilege,Integer> {

	
	@Query(nativeQuery = true,value = "select can_assign from tt_assigned_privilege where transactional_id=?1")
	Optional<String> findAssignPrivilegeByTranstionalId(String TId);

	AssignedPrivilege findByTransactionalId(String transactionId);
    
	

}
