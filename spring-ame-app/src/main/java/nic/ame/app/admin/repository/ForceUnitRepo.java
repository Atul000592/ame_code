package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.UnitModel;



@Repository
public interface ForceUnitRepo extends JpaRepository<UnitModel, String> {
	
	 @Query(value = "select * from tm_unit u where u.force_no=?1 order by  u.unit_id ASC", nativeQuery = true)
	 List<nic.ame.app.admin.model.UnitModel> findByForceId(@Param("forceId") long forceId);

}
