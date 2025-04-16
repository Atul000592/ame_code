package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nic.ame.app.master.medical.model.AmeReviewCandidatesList;

public interface AmeReviewCandidatesListRepository extends JpaRepository<AmeReviewCandidatesList,String> {
	
	@Query(nativeQuery = true,value ="select * from ame_review_candidates_list where ame_id=?1 and status_code=?2")
    Optional<AmeReviewCandidatesList> findByAmeIdAndStatusCode(String ameId,int statusCode);
	@Query(nativeQuery= true,value="select * from ame_review_candidates_list \r\n"
			+ "where status_code=1 \r\n"
			+ "and reschedule_flag=true \r\n"
			+ "and current_date\r\n"
			+ "between  review_end_date - interval '7 days' \r\n"
			+ "and review_end_date")
	Page<AmeReviewCandidatesList> getForcePersonnelListForReview(Pageable pageable);

	List<AmeReviewCandidatesList> getByCandidateForcePersonalIdAndYear(String forcePersonnelId,String year);

	Optional<AmeReviewCandidatesList> getByBoardIdAndCandidateForcePersonalIdAndYear(String boardId,String forcePersonnelId,String year);
		
		@Query(nativeQuery = true,value ="select count(*) from ame_review_candidates_list where board_id=?1 and status_code=?2")
		int findCandidateListForReviewByBoardId(String boardId,int statusCode);
	//===============================Count of Total AME Review==================================//
		@Query(nativeQuery = true, value = "SELECT COUNT(arcl.ame_id)\r\n"
		  		+ "FROM medical_board_individual_mapping mbi\r\n"
		  		+ "JOIN ame_review_candidates_list arcl\r\n"
		  		+ "ON mbi.force_personal_id = arcl.candidate_force_personal_id\r\n"
		  		+ "AND mbi.board_id = arcl.board_id\r\n"
		  		+ "JOIN ame_application_flow_status aafs\r\n"
		  		+ "ON arcl.ame_id = aafs.ame_id\r\n"
		  		+ "WHERE aafs.ame_final_status != 0;\r\n")
	    int getTotalAMEReview() ;
		// TODO Auto-generated method stub
		
	  
	//===============================Count of Today AME Review==================================//
	
		@Query(nativeQuery = true, value = "SELECT COUNT(arcl.ame_id)\r\n"
		  		+ "FROM medical_board_individual_mapping mbi\r\n"
		  		+ "JOIN ame_review_candidates_list arcl\r\n"
		  		+ "ON mbi.force_personal_id = arcl.candidate_force_personal_id\r\n"
		  		+ "AND mbi.board_id = arcl.board_id\r\n"
		  		+ "AND DATE(arcl.review_created_on) = CURRENT_DATE\r\n"
		  		+ "JOIN ame_application_flow_status aafs\r\n"
		  		+ "ON arcl.ame_id = aafs.ame_id\r\n"
		  		+ "WHERE aafs.ame_final_status != 0;\r\n")
	   int getTodayAMEReview();
	
	//===============================Count of Total AME Review by Force==================================//
	
		@Query(nativeQuery = true, value = "SELECT COUNT(arcl.ame_id)\r\n"
		  		+ "FROM medical_board_individual_mapping mbi\r\n"
		  		+ "JOIN ame_review_candidates_list arcl\r\n"
		  		+ "ON mbi.force_personal_id = arcl.candidate_force_personal_id\r\n"
		  		+ "AND mbi.board_id = arcl.board_id\r\n"
		  		+ "JOIN ame_application_flow_status aafs\r\n"
		  		+ "ON arcl.ame_id = aafs.ame_id\r\n"
		  		+ "WHERE aafs.ame_final_status != 0\r\n"
		+ "AND mbi.force_no = CAST(? AS INTEGER)")
		   int ForceTotalReview(@Param("forceNo") Integer forceNo);
	
	//===============================Count of Today AME Review by Force==================================//
	
		@Query(nativeQuery = true, value = "SELECT COUNT(arcl.ame_id)\r\n"
		  		+ "FROM medical_board_individual_mapping mbi\r\n"
		  		+ "JOIN ame_review_candidates_list arcl\r\n"
		  		+ "ON mbi.force_personal_id = arcl.candidate_force_personal_id\r\n"
		  		+ "AND mbi.board_id = arcl.board_id\r\n"
		  		+ "AND DATE(arcl.review_created_on) = CURRENT_DATE\r\n"
		  		+ "JOIN ame_application_flow_status aafs\r\n"
		  		+ "ON arcl.ame_id = aafs.ame_id\r\n"
		  		+ "WHERE aafs.ame_final_status != 0\r\n"
		+ "AND mbi.force_no = CAST(? AS INTEGER)")
			   int ForceTodayReview(@Param("forceNo") Integer forceNo);

}
