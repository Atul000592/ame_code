package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;

public interface AmeFinalReportDetailsRepository extends JpaRepository<AmeFinalReportDetails,String> {

	
	@Query(nativeQuery = true,value = "select afrd.ame_id ,afrd.final_category_awarded ,afrd.board_id,afrfd.file_path,afrd.ame_year  \r\n"
			+ "from ame_final_report_details afrd inner join \r\n"
			+ "ame_final_report_file_dir afrfd on afrd.ame_id =afrfd.ame_id \r\n"
			+ "where afrd.candidateforce_personal_id =?1")
	List<Object> findCandidateResultByForcePersonalId(String forcePersonalId);

	Optional<AmeFinalReportDetails> findByAmeId(String ameId);


	Optional<String>  findByAmeIdAndCandidateforcePersonalId(String ameId, String candidateForcePersonnelId);
	
	@Query(nativeQuery = true,value = "SELECT * FROM public.ame_final_report_details where ame_id=?1 and status=?2")
	Optional<AmeFinalReportDetails> findByAmeIdAndStatus(String ameId,int rowEnableStatus);

}

