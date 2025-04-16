package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.MapIndividualAndUnitToReporting;

public interface MapIndividualAndUnitToReportingRepository extends JpaRepository<MapIndividualAndUnitToReporting, Integer>{

	@Query(nativeQuery = true,value="select iumtc.candidate_force_personal_id,iumtc.candidate_irla_no,fp.name,rf.force_code_name,tu.unit_name,taa.from_date,taa.to_date\r\n"
			+ "from individual_unit_mapped_to_controlling as iumtc\r\n"
			+ "inner join tt_appointment_ame as taa \r\n"
			+ "on iumtc.candidate_force_personal_id = taa.force_personal_id\r\n"
			+ "inner join tt_force_personnel as fp\r\n"
			+ "on fp.force_personal_id = taa.force_personal_id\r\n"
			+ "inner join ref_force as rf\r\n"
			+ "on rf.force_id = taa.force_no\r\n"
			+ "inner join tm_unit as tu\r\n"
			+ "on tu.unit_id = CAST( taa.unit_no AS CHARACTER)")
	List<Object> findAllWithCompletedAppointment();
	
	@Query(nativeQuery = true,value="select iumtc.candidate_force_personal_id,iumtc.candidate_irla_no,fp.name,rf.force_code_name,tu.unit_name\r\n"
			+ "from individual_unit_mapped_to_controlling as iumtc\r\n"
			+ "left join tt_appointment_ame as taa \r\n"
			+ "on iumtc.candidate_force_personal_id = taa.force_personal_id\r\n"
			+ "inner join tt_force_personnel as fp\r\n"
			+ "on fp.force_personal_id=iumtc.candidate_force_personal_id\r\n"
			+ "inner join ref_force as rf\r\n"
			+ "on rf.force_id=fp.force_no\r\n"
			+ "inner join tm_unit as tu\r\n"
			+ "on tu.unit_id=fp.unit\r\n"
			+ "where taa.force_personal_id is NULL")
	List<Object> findAllWithPendingAppointment();
	
	
	@Query(nativeQuery=true,value="select fp.name,iumtc.candidate_force_personal_id,iumtc.candidate_irla_no,afrd.final_category_awarded,afrd.created_on,adf.file_path,afrfd.file_path \r\n"
			+ "from individual_unit_mapped_to_controlling as  iumtc\r\n"
			+ "inner join ame_final_report_details as afrd\r\n"
			+ "on afrd.candidateforce_personal_id=iumtc.candidate_force_personal_id\r\n"
			+ "inner join ame_declaration_files as adf on\r\n"
			+ "afrd.ame_id=adf.ame_id\r\n"
			+ "inner join ame_final_report_file_dir as afrfd\r\n"
			+ "on afrd.ame_id=afrfd.ame_id\r\n"
			+ "inner join tt_force_personnel as fp\r\n"
			+ "on fp.force_personal_id=iumtc.candidate_force_personal_id")
	List<Object> getResultStatus();


	@Query(value = "select * from map_individual_and_unit_to_reporting where reporting_force_personal_id=:reportingForcePersonalId",nativeQuery = true)
	List<MapIndividualAndUnitToReporting> findByReportingForcePersionIdId(String reportingForcePersonalId);
	
	@Query(value = "select count(*) from map_individual_and_unit_to_reporting where reporting_force_personal_id=:reportingForcePersonalId",nativeQuery = true)
	int  findByReportingForcePersionIdIdCandidateCount(String reportingForcePersonalId);

	@Query(value = "select count(*) from map_individual_and_unit_to_reporting misut inner join ame_final_report_details afrd\r\n"
			+ "on misut.candidate_force_personal_id=afrd.candidateforce_personal_id where misut.reporting_force_personal_id=?1",nativeQuery = true)
    int findAmeCompletedCount(String reportingForcePersonalId );
	
	
}
