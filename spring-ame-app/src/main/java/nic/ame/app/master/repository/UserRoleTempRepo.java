package nic.ame.app.master.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.UserRoleTemp;

@Repository
public interface UserRoleTempRepo extends JpaRepository<UserRoleTemp, Integer> {

	
	@Query(nativeQuery = true,value = "select * from user_role_temp where transaction_id=?1")
	public List<UserRoleTemp> findByTransactionalId(BigInteger transactionalId);
	
}
