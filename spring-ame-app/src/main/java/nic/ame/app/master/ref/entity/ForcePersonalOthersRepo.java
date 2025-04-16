package nic.ame.app.master.ref.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.model.PersonnelOthers;

@Repository
public interface ForcePersonalOthersRepo extends JpaRepository<PersonnelOthers, Integer>{

	@Query(nativeQuery=true,value="select tpo.name,tr.rank_full_name \r\n"
			+ "from tm_personnel_others tpo \r\n"
			+ "inner join tm_rank tr\r\n"
			+ "on tpo.designation_id= tr.id\r\n"
			+ "where tpo.force_id=?1 and tpo.designation_id=?2") 
	List<Object> getPersonalOthersByForceNoAndDesignation(Integer forceNo,Integer designation);
	 
}
