package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.Force;

@Repository
public interface ForceRepo extends JpaRepository<Force, Integer>{
	
	
	@Query(value = "select force_name from tm_force where force_no=?1" ,nativeQuery = true)
	public String getForceName(int forceNo);

	@Query(value = "select * from tm_force where force_no=?1" ,nativeQuery = true)
	 Force findByForceNo(int forceNo);
	
	@Query(value = "select * from tm_force where force_no=?1" ,nativeQuery = true)
	public List<Force> findForceListByForceId(Integer forceNo);

	
	
	
	

}
