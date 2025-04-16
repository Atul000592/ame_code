package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.IndividualUnitMappedToControlling;

public interface IndividualUnitMappedToControllingRepository extends JpaRepository<IndividualUnitMappedToControlling, Integer>{

	@Query(nativeQuery = true,value="select miu.candidate_force_personal_id from reporting_officer ro inner join map_individual_and_unit_to_reporting miu\r\n"
			+ "on ro.reporting_unique_id=miu.reporting_officer_unique_id\r\n"
			+ "where ro.status='1' and miu.status='1' and miu.reporting_force_personal_id=?1")
	List<String> findAllWithCompletedAppointment(String controllingOfficer);
	
	@Query(nativeQuery = true,value="select iumtc.candidate_force_personal_id,iumtc.candidate_irla_no,fp.name,rf.force_code_name,tu.unit_name\r\n"
			+ "from individual_unit_mapped_to_controlling as iumtc\r\n"
			+ "left join tt_appointment_ame as taa \r\n"
			+ "on iumtc.candidate_force_personal_id = taa.force_personal_id\r\n"
			+ "inner join tt_force_personnel as fp\r\n"
			+ "on fp.force_personal_id=iumtc.candidate_force_personal_id\r\n"
			+ "inner join tm_force as rf\r\n"
			+ "on rf.force_no=fp.force_no\r\n"
			+ "inner join tm_unit as tu\r\n"
			+ "on tu.unit_id=fp.unit\r\n"
			+ "where taa.force_personal_id is NULL")
	List<Object> findAllWithPendingAppointment();
	
	
	@Query(nativeQuery=true,value="select fp.name,iumtc.candidate_irla_no,iumtc.candidate_irla_no,afrd.final_category_awarded,\r\n"
			+ "afrd.created_on,adf.file_path,afrfd.file_path , afrd.board_id from map_individual_and_unit_to_reporting as  iumtc inner join ame_final_report_details as afrd\r\n"
			+ "on afrd.candidateforce_personal_id=iumtc.candidate_force_personal_id inner join ame_declaration_files as adf on afrd.ame_id=adf.ame_id\r\n"
			+ "inner join ame_final_report_file_dir as afrfd on afrd.ame_id=afrfd.ame_id inner join tt_force_personnel as fp\r\n"
			+ "on fp.force_personal_id=iumtc.candidate_force_personal_id  where iumtc.reporting_force_personal_id=?1 and iumtc.status='1'")
	List<Object> getResultStatus(String forcePersonnel);
	
	
}
