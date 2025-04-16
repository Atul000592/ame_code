package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.AmeDetails;

public interface AmeDetailsRepository extends JpaRepository<AmeDetails,String> {
	
	@Query(nativeQuery = true,value="select "
			+ "tad.force_personnel_id,tad.force_id,tad.name"
			+ ",tad.dob,tad.age,tu.unit_name,tf.force_name\r\n"
			+ ",tf.force_code_name,tad.last_ame_date,tad.ame_place,tad.joining_date\r\n"
			+ ",tad.gazetted_non_gazetted_flag\r\n"
			+ ",tr.rank_full_name\r\n"
			+ ",tg.g_value,tr.sub_rank_code\r\n"
			+ "from tt_ame_details tad\r\n"
			+ "inner join tm_rank tr\r\n"
			+ "on tad.rank=tr.id\r\n"
			+ "inner join tm_gender tg\r\n"
			+ "on tad.gender=tg.g_code\r\n"
			+ "inner join tm_unit tu\r\n"
			+ "on tad.unit=tu.unit_id\r\n"
			+ "\r\n"
			+ "inner join tm_force tf\r\n"
			+ "on tad.force_no=tf.force_no\r\n"
			+ "where ame_id=?1")
	Optional<Object> getCandidateAmeDetailsByAmeId(String ameId);

}
