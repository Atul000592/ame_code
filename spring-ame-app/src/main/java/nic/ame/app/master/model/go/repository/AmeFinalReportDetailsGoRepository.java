package nic.ame.app.master.model.go.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;

public interface AmeFinalReportDetailsGoRepository extends CrudRepository<AmeFinalReportDetailsGo,String>{

	Optional<AmeFinalReportDetailsGo> findByAmeId(String ameId);
 
	List<AmeFinalReportDetailsGo> findUniqueIdByAmeId(String ameId);

	AmeFinalReportDetailsGo findByAmeIdAndUniqueAmeId(String ameId, String uniqueAmeId);
  
	
	@Query(nativeQuery = true,value = "SELECT candidate_force_personnel_id FROM public.ame_final_report_details_go where ame_id=?1")
	String  findForcePersonnelIdByAmeId(String ameId);

	 
	@Query(nativeQuery = true,value = "select afrd.ame_id ,afrd.final_category_awarded ,afrd.board_id,afrfd.file_path,afrd.ame_year  \r\n"
			+ "	from ame_final_report_details_go afrd inner join \r\n"
			+ "		ame_final_report_file_dir_go afrfd on afrd.ame_id =afrfd.ame_id \r\n"
			+ "		where afrd.ame_id=?1")
	List<Object> findCandidateResultByAmeId(String ameId);
	
	@Query(nativeQuery = true,value = "select afrd.ame_id ,afrd.final_category_awarded ,afrd.board_id,afrfd.file_path,afrd.ame_year,afrd.unique_ame_id \r\n"
			+ "	from ame_final_report_details_go afrd inner join \r\n"
			+ "		ame_final_report_file_dir_go afrfd on afrd.ame_id =afrfd.ame_id \r\n"
			+ "		where afrd.candidate_force_personnel_id=?1 order by afrd.created_on desc")
	List<Object> findCandidateResultByForcePersonalId(String forcePersonalId);
	
	@Query(nativeQuery = true,value = "SELECT * FROM public.ame_final_report_details_go where ame_id=?1 and status=?2")
	Optional<AmeFinalReportDetailsGo> findByAmeIdAndStatus(String ameId,int rowEnableStatus);

}
