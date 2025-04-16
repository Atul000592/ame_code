package nic.ame.app.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.UnitModel;



@Repository
public interface UnitRepository extends JpaRepository<UnitModel, String>{

	
	
	@Query(nativeQuery = true,value = "select * from tm_unit where force_no=?1 order BY unit_id ASC")
	List<UnitModel> getUnitDataByForceId(int forceId);
	
	@Query(nativeQuery = true,value = "select tu.unit_name  from tm_unit tu where tu.unit_id=?1 and tu.force_no =?2")
	String getUnitNameByUnitIdAndForceNo(String unitId,int forceNo);
	
	
	@Query(nativeQuery = true,value = "select * from tm_unit tmu where tmu.unit_id in(select DISTINCT(attach_unit) \r\n"
			+ "as attach_unit_id from tt_force_personnel fp where fp.attach_unit is not null and fp.force_no=?1 )order by tmu.unit_id asc")
	String getAttachUnitWithDataPresentIntheTable(int forceNo);

	
	@Query(nativeQuery = true,value = "select * from tm_unit tmu where tmu.unit_id in(\r\n"
			+ "select DISTINCT(unit) as unitId from tt_force_personnel where force_no=?1) \r\n"
			+ "order by tmu.unit_id asc")
	String getUnitWithDataPresentIntheTable(int forceNo);

	Optional<UnitModel> findByUnitId(String parseInt);

}
