package nic.ame.app.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.admin.model.RefRoleMedical;

public interface RefRoleMedicalRepo extends JpaRepository<RefRoleMedical, Long>{

	
	@Query(nativeQuery = true,value = "select rrm.full_name from ref_role_medical rrm where rrm.role_name =?1")
	public String getRoleNamebyRoleId(String roleName);
 
	@Query(nativeQuery = true,value = "select rrm.role_name from ref_role_medical rrm where rrm.id =?1")
	public Optional<String> findRoleNameById(int valueOf);
	
}
