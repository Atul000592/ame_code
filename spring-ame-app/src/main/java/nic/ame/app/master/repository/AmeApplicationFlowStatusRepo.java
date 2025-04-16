package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;


@Repository
public interface AmeApplicationFlowStatusRepo extends JpaRepository<AmeApplicationFlowStatus, Integer> {

	
	//==========================create application flow ===========================//
       @Query(value =
	  "select aafs.check_up_list_flag,adi.force_id,adi.\"name\",adi.rank,adi.designation,adi.declaration_date,adi.force_personal_id,adi.ame_id\r\n"
	  + "from ame_application_flow_status aafs left join tt_ame_declaration_individual adi\r\n"
	  + "on aafs.ame_id = adi.ame_id\r\n"
	  + "where\r\n"
	  + "aafs.ame_form_upload_flag=1 \r\n"
	  + "and aafs.appointment_flag=1\r\n"
	  + "and aafs.unit=?1 and aafs.force_no=?2"
	  ,nativeQuery = true)
       List<Object> getAmeApplicationFlowStatusList(String unit ,Integer forceNo);
       
       @Query(nativeQuery = true,value = "select aafs.* from ame_application_flow_status  aafs inner join tt_ame_declaration_individual tadi on tadi.ame_id = aafs.ame_id\r\n"
   			+ "where tadi.force_personal_id=?1")
   	List<AmeApplicationFlowStatus> getAmeSignatureAndReportStatus(String candidateForcePersonnelId); 

   	//==========================create application flow ===========================//
          @Query(value ="select aafs.check_up_list_flag,adi.force_id,adi.name,adi.rank,adi.designation,adi.declaration_date,adi.force_personal_id,adi.ame_id,aafs.board_id,fp.force_id from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id   left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id   where taa.board_id=?1 and (taa.declaration_status=1 and aafs.appointment_flag=1  and aafs.ame_data_check_flag=1 and aafs.ame_form_upload_flag =0 or aafs.ame_form_upload_flag =1)"
   	  ,nativeQuery = true)
          List<Object> getAmeApplicationFlowStatusListByBoardId(String boardId);
          
          
      //========================================upload completed list===============================================//
          @Query(value ="select aafs.check_up_list_flag,adi.force_id,adi.name,adi.rank,adi.designation,adi.declaration_date,adi.force_personal_id,adi.ame_id,aafs.board_id\r\n"
            		+ "   	  , fp.force_id from  tt_appointment_ame taa\r\n"
            		+ "   	   inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
            		+ "   	  inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
            		+ "   	  left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
            		+ "   	  where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
            		+ "   	  and aafs.ame_data_check_flag=1 and aafs.ame_form_upload_flag=1"
     	  ,nativeQuery = true)
            List<Object> getAmeApplicationFlowStatusListUploadCompletedByBoardId(String boardId);

          
          
          
          
  //================================list of ame upload pending Count ==========================================//
          
          @Query(value ="select count(*) from  tt_appointment_ame taa\r\n"
          		+ "inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
          		+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id\r\n"
          		+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
          		+ " where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
          		+ " and aafs.ame_data_check_flag=1  and aafs.ame_form_upload_flag =0"
        	   	  ,nativeQuery = true)
        	          int getAmeApplicationFlowStatusOfAmeFormUploadPendingByBoardId(String boardId);
          
          
          
          
       
     //================================list of ame upload completed Count ==========================================//
          
          @Query(value =
        	   	  "select count(*)\r\n"
        	   	  + "from  tt_appointment_ame taa"
        	   	  + " inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
        	   	  + "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
        	   	  + "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
        	   	  + "where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
        	   	  + "and aafs.ame_data_check_flag=1  and aafs.ame_form_upload_flag =1"
        	   	  ,nativeQuery = true)
        	          int getAmeApplicationFlowStatusOfAmeFormUploadListByBoardId(String boardId);
          
          
          
  //============================================================list of AME CheckUp list Pending  Count===========================================//   
          
          @Query(value =
        	   	  "select count(*)\r\n"
        	   	  + "from  tt_appointment_ame taa"
        	   	  + " inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
        	   	  + "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
        	   	  + "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
        	   	  + "where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
        	   	  + "and aafs.ame_data_check_flag=1  and aafs.ame_form_upload_flag =1 and aafs.check_up_list_flag =0"
        	   	  ,nativeQuery = true)
        	        int getAmeApplicationFlowStatusOfAmeCheckUpListPendingByBoardId(String boardId);
          
          
          
          
          
       //============================================================list of AME CheckUp list uploaded  Count===========================================//   
          
          @Query(value =
        	   	  "select count(*)\r\n"
        	   	  + "from  tt_appointment_ame taa"
        	   	  + " inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
        	   	  + "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
        	   	  + "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
        	   	  + "where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
        	   	  + "and aafs.ame_data_check_flag=1  and aafs.ame_form_upload_flag =1 and aafs.check_up_list_flag =1"
        	   	  ,nativeQuery = true)
        	        int getAmeApplicationFlowStatusOfAmeCheckUpListByBoardId(String boardId);
          
          
          
          
          
       //=====================================List of candidate for Dealing-Hand ===============================//
       @Query(value =
    			  "select adi.force_id,adi.\"name\",adi.rank,adi.designation,adi.declaration_date,adi.force_personal_id,adi.ame_id\r\n"
    			  + "	from ame_application_flow_status aafs left join tt_ame_declaration_individual adi\r\n"
    			  + "	on aafs.ame_id = adi.ame_id\r\n"
    			  + "	where aafs.check_up_list_flag=1\r\n"
    			  + "	 and aafs.ame_form_upload_flag=1 \r\n"
    			  + "	  and aafs.appointment_flag=1\r\n"
    			  + "and aafs.unit=?1 and aafs.force_no=?2"
    			  ,nativeQuery = true)
    		       List<Object> getWaitingListOfCandidateForDealingHand(String unit ,Integer forceNo);
	
	
	
	  
	  //================================List of candidate for dealinghand count ======================//
	  @Query(value =
			  "select  count(*)"
			  + "from ame_application_flow_status aafs left join tt_ame_declaration_individual adi\r\n"
			  + "on aafs.ame_id = adi.ame_id\r\n"
			  + "where aafs.check_up_list_flag=1\r\n"
			  + "and aafs.ame_form_upload_flag=1 \r\n"
			  + "and aafs.appointment_flag=1\r\n"
			  + "and aafs.unit=?1 and aafs.force_no=?2"
			  ,nativeQuery = true) 
	             Integer candidateListForDealingHand(String unit ,Integer forceNo);
			

	
	  //=============get complete count of declaration under process by boardId=========================//
	  
	  @Query(nativeQuery = true,value = "select count(*)\r\n"
	  		+ "	  	from ame_application_flow_status aafs left join tt_ame_declaration_individual adi\r\n"
	  		+ "	  		on aafs.ame_id = adi.ame_id\r\n"
	  		+ "	  		where\r\n"
	  		+ "	  		aafs.ame_form_upload_flag=1\r\n"
	  		+ "	  		and aafs.appointment_flag=1\r\n"
	  		+ "	  		and aafs .check_up_list_flag=1\r\n"
	  		+ "	  		and aafs.board_id=?1")
	  Integer getCompleteDeclarationCountComplete(String boardId);
	  
	  
	  
	  
	  
	/*
	 * @Query(nativeQuery = true,value =
	 * "select  aafs.* , adi.name ,adi.designation, adi.rank, adi.declaration_date\r\n"
	 * +
	 * "from ame_application_flow_status aafs left join ame_declaration_individual adi\r\n"
	 * + "on aafs.ame_id = adi.ame_id\r\n" +
	 * "where aafs.check_up_list_flag is null  and  exists (select adi.ame_id from \r\n"
	 * + "ame_declaration_individual adi left join \r\n" +
	 * "tt_force_personnel fp on fp.\"name\" = adi.\"name\"\r\n" +
	 * "where adi.force_personal_id=:CandidateForcePersonalId and fp.unit=:unit and fp.force_no=:forceNo and aafs.ame_id= adi.ame_id )"
	 * ) List<Object> getAmeApplicationFlowStatusList(String
	 * CandidateForcePersonalId,String unit,Integer forceNo);
	 */
	  
	//==========================find by ameId===========================//
	  Optional<AmeApplicationFlowStatus> findByAmeId(String ameId);

	  Optional<AmeApplicationFlowStatus> findByAmeIdAndRowIsValid(String ameId,boolean rowIsValid);
	  
	  @Query(nativeQuery = true,value = "select tu.unit_name from ame_application_flow_status aafs inner join medical_board mb on aafs.board_id=mb.board_id \r\n"
	  		+ "inner join tm_unit tu on mb.place=tu.unit_id where aafs.ame_id =?1")
	  String getAmePlaceByBoardIdAndAmeId(String ameId);
	  
	  
	  
	  
	  
	  
	  //===============================Count of Declaration Complete Pending For Approvals===================================//
	  
	  
	  @Query(nativeQuery = true,value = "select count(*) from  tt_appointment_ame taa "
	  		+ "inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id "
	  		+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id "
	  		+ "  left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id where taa.board_id=?1  "
	  		+ "and aafs.appointment_flag=?2 and taa.declaration_status=?3 and aafs.ame_data_check_flag=0")
	  int countDeclarationCompletePendingForApprovals(String boardId,int appointmentFlag,int declarationStatus);
	  
	  
	  //===============================Count of Pending For Upload AME And CheckUpList==================================//
	 
	  
	  @Query(nativeQuery = true,value = "select count(*) from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id where taa.board_id=?1 	and aafs.appointment_flag=?2 and taa.declaration_status=?3 and  aafs.ame_data_check_flag=?4 and aafs.check_up_list_flag is null")
		  int countPendingForUploadAmeAndCheckUpLists(String boardId,int appointmentFlag,int declarationStatus,int ameDataCheckFlag);
	  
	  //=====================================List of Declaration Complete Pending For Approvals===================================//
	  
	  @Query(nativeQuery = true,value = "select aafs.ame_id ,aafs.board_id ,aafs.forcepersonal_id,fp.force_id ,fp.name,adi.declaration_date  "
	  		+ "from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
	  		+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
	  		+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
	  		+ "where taa.board_id=?1  and aafs.appointment_flag=?2 and taa.declaration_status=?3 and aafs.ame_data_check_flag is null")
		  List<Object> countDeclarationCompletePendingForApprovalsList(String boardId,int appointmentFlag,int declarationStatus);
	  
	  
	  
	  //===============================List of Pending For AME FORM UPLOAD Upload ==================================//
	  
	  @Query(nativeQuery = true,value = "select aafs.ame_id ,aafs.board_id ,aafs.forcepersonal_id,fp.force_id ,fp.name,adi.declaration_date  "
	  		+ "from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id"
	  		+ "	inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id "
	  		+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id"
	  		+ "where taa.board_id=?1  and aafs.appointment_flag=?2 and taa.declaration_status=?3 and  aafs.ame_data_check_flag=?4")
	  List<Object> countPendingForUploadAmeAndCheckUpListsList(String boardId,int appointmentFlag,int declarationStatus,int ameDataCheckFlag);
	  
	  
	  //===============================List of Pending For AME FORM CheckUp Upload ==================================//
	  
	  @Query(nativeQuery = true,value = "select count(*) from  tt_appointment_ame taa"
	  		+ " inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id"
	  		+ "	inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id"
	  		+ "	left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id"
	  		+ "	where taa.board_id=?1  and aafs.appointment_flag=?2 and taa.declaration_status=?3 and  aafs.ame_data_check_flag=?4")
	  List<Object> countPendingForUpCheckUpListList(String boardId,int appointmentFlag,int declarationStatus,int ameDataCheckFlag);
	  
	  
	  //===============================List of Pending For AME FORM UPLOAD Upload ==================================//
	  
	  @Query(nativeQuery = true,value = "select aafs.ame_id ,aafs.board_id ,aafs.forcepersonal_id,fp.force_id ,fp.name,adi.declaration_date "
	  		+ " from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id "
	  		+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id "
	  		+ "	left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id "
	  		+ "	where taa.board_id=?1  and aafs.appointment_flag=?2 and taa.declaration_status=?3 and  aafs.ame_data_check_flag=?4")
	  List<Object> PendingForCheckUpListsList(String boardId,int appointmentFlag,int declarationStatus,int ameDataCheckFlag);
	  
	  
	  /* member with decleration and appointmnet complete*/
	  @Query(value="select rf.force_code_name,mb.board_id,fp.force_id,fp.name,tu.unit_name,aafs.appointment_flag,taa.declaration_status,aafs.ame_data_check_flag,\r\n"
	  		+ "aafs.ame_form_upload_flag,aafs.check_up_list_flag \r\n"
	  		+ "from ame_application_flow_status aafs \r\n"
	  		+ "inner join tt_appointment_ame taa on aafs.forcepersonal_id=taa.force_personal_id  \r\n"
	  		+ "inner join tt_force_personnel fp  on fp.force_personal_id=aafs.forcepersonal_id \r\n"
	  		+ "inner join medical_board mb on mb.board_id=aafs.board_id \r\n"
	  		+ "inner join tm_force rf on rf.force_no=fp.force_no \r\n"
	  		+ "inner join tm_unit tu on mb.place=tu.unit_id and cast(mb.board_at_force_no as int) = tu.force_no \r\n"
	  		+ "where mb.created_by=?1",nativeQuery = true)
	 List<Object> statusOfMeppedMemberToBoard(String forcePersonalId);
	  
	  @Modifying
	   @Query(nativeQuery = true,value="update ame_application_flow_status set check_up_list_flag=1 where ame_id=?1")
		void updateCheckUplistFlag(String ameId);
		  
	  
	  
	  
	  @Query(nativeQuery = true,value = "select fp.\"name\",fp.\"rank\" ,fp.force_id,adi.ame_id ,ams.board_id,adi.ame_final_status,fp.force_personal_id,adi.declaration_year,adi.declaration_date \r\n"
	  		+ "	  from tt_ame_declaration_individual adi \r\n"
	  		+ "	  inner join tt_ame_declaration_individual_details adid on adi.ame_id =adid.ame_id\r\n"
	  		+ "	  inner join ame_master_status ams on ams.ame_id =adi.ame_id \r\n"
	  		+ "	  inner join  tt_force_personnel fp  on fp.force_personal_id = adi.force_personal_id \r\n"
	  		+ "	  where  ams.board_id=?1 and adi.ame_final_status=?2 and adi.final_upload_flag=?3")
	  List<Object> listOfCompletedAmeOrUnderReviewData(String boardId,int ameFinalStatus,int finalUploadFlag);
	  
	  
	//===============================Count of Total AME==================================//
	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND afr.ame_id NOT IN ("
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")

	  public int getTotalAMECount();

	//===============================Count of Today AME==================================//
	//  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id)\r\n"
	 // 		+ "FROM medical_board_individual_mapping mbi\r\n"
	  //		+ "JOIN ame_final_report_details afr\r\n"
	  //		+ "    ON mbi.force_personal_id = afr.candidateforce_personal_id\r\n"
	  //		+ "    AND mbi.board_id = afr.board_id\r\n"
	  	//	+ "    AND DATE(afr.created_on) = CURRENT_DATE\r\n"
	  	//	+ "JOIN ame_application_flow_status aafs\r\n"
	  //		+ "    ON afr.ame_id = aafs.ame_id\r\n"
	  //		+ "WHERE aafs.ame_final_status != 0;\r\n")
	  
	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "    AND DATE(afr.created_on) = CURRENT_DATE "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND afr.ame_id NOT IN ("
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")
	  public int getTodayAMECount();
	  
	  

	  //===============================Count of Total Appointment Scheduled==================================//
	  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame")
	  public int getTotalAMEAppointment();

	  //===============================Count of Total Declaration==================================//
	  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame WHERE declaration_status != 0")
	  public int getTotalDeclaration();

	  //===============================Count of Total AME Pending==================================//
	  @Query(nativeQuery = true, value = 
			    "SELECT COUNT(*) "
			    + "FROM medical_board_individual_mapping mbim "
			    + "LEFT JOIN ame_application_flow_status aafs "
			    + "ON mbim.force_personal_id = aafs.forcepersonal_id "
			    + "AND mbim.board_id = aafs.board_id "
			    + "WHERE mbim.is_mapping_valid = 1 "
			    + "AND (aafs.forcepersonal_id IS NULL "
			    + "OR aafs.ame_final_status = 0 "
			    + "OR (aafs.ame_final_status != 0 "
			    + "AND EXISTS (SELECT 1 "
			    + "FROM ame_review_candidates_list arcl "
			    + "WHERE arcl.ame_id = aafs.ame_id)))")
	  public int getTotalAMEPending();

	  //===============================Count of Total AME Completed==================================//
	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND afr.ame_id NOT IN ("
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")
	  public int getTotalAMECompleted();
	  
	  

	  //===============================Count of Today Appointment Scheduled==================================//
	  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame WHERE DATE(created_on) = CURRENT_DATE")
	  public int getTodayAMEAppointment();

	  //===============================Count of Today Declaration==================================//
	  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame WHERE declaration_status != 0 AND DATE(created_on) = CURRENT_DATE")
	  public int getTodayDeclaration();

	  //===============================Count of Today AME Pending==================================//
		/*
		 * @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
		 * "FROM medical_board_individual_mapping mbim " +
		 * "LEFT JOIN ame_application_flow_status aafs " +
		 * "ON mbim.force_personal_id = aafs.forcepersonal_id " +
		 * "AND mbim.board_id = aafs.board_id " + "WHERE mbim.is_mapping_valid = 1 " +
		 * "AND (aafs.forcepersonal_id IS NULL OR aafs.ame_final_status = 0)")
		 */
	  
	  @Query(nativeQuery = true, value = 
			    "SELECT COUNT(*) "
			    + "FROM medical_board_individual_mapping mbim "
			    + "LEFT JOIN ame_application_flow_status aafs "
			    + "ON mbim.force_personal_id = aafs.forcepersonal_id "
			    + "AND mbim.board_id = aafs.board_id "
			    + "WHERE mbim.is_mapping_valid = 1 "
			    + "AND (aafs.forcepersonal_id IS NULL "
			    + "OR aafs.ame_final_status = 0 "
			    + "OR (aafs.ame_final_status != 0 "
			    + "AND EXISTS (SELECT 1 "
			    + "FROM ame_review_candidates_list arcl "
			    + "WHERE arcl.ame_id = aafs.ame_id)))")
	  public int getTodayAMEPending();
	  
	//===============================Count of Today AME Completed==================================//
	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "    AND DATE(afr.created_on) = CURRENT_DATE "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND afr.ame_id NOT IN ("
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")
	  public int getTodayAMECompleted();


     //===============================Count of Total AME Completed by Force==================================//

	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND mbi.force_no = CAST(? AS INTEGER) "
		        + "AND afr.ame_id NOT IN ( "
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")

		int getTotalAMEByForce(@Param("forceNo") Integer forceNo);

//===============================Count of Today AME Completed by Force==================================//

@Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
        + "FROM medical_board_individual_mapping mbi "
        + "JOIN ame_final_report_details afr "
        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
        + "    AND mbi.board_id = afr.board_id "
        + "    AND DATE(afr.created_on) = CURRENT_DATE "
        + "JOIN ame_application_flow_status aafs "
        + "    ON afr.ame_id = aafs.ame_id "
        + "WHERE aafs.ame_final_status != 0 "
        + "AND mbi.force_no = CAST(? AS INTEGER) "
        + "AND afr.ame_id NOT IN ( "
        + "    SELECT ame_id "
        + "    FROM ame_review_candidates_list"
        + ");")
     
     public int getTodayAMEByForce(@Param("forceNo") Integer forceNo);
     
     //===============================Count of Total Appointment Scheduled==================================//
     @Query(value = "SELECT COUNT(*) FROM tt_appointment_ame WHERE force_no = ?1", nativeQuery = true)
     int ForceTotalAMEAppointment(@Param("forceNo") Integer forceNo);
     
   //===============================Count of Total Declaration by Force==================================//
	  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame WHERE declaration_status != 0 AND force_no = ?1")
	  public int ForceTotalDeclaration(@Param("forceNo") Integer forceNo);
	  
	  //===============================Count of Total AME Pending by Force==================================//
	  
	  @Query(nativeQuery = true, value = 
			    "SELECT COUNT(*) "
			    + "FROM medical_board_individual_mapping mbim "
			    + "LEFT JOIN ame_application_flow_status aafs "
			    + "ON mbim.force_personal_id = aafs.forcepersonal_id "
			    + "AND mbim.board_id = aafs.board_id "
			    + "WHERE mbim.is_mapping_valid = 1 "
			    + "AND mbim.force_no = CAST(? AS INTEGER)\r\n"
			    + "AND (aafs.forcepersonal_id IS NULL "
			    + "OR aafs.ame_final_status = 0 "
			    + "OR (aafs.ame_final_status != 0 "
			    + "AND EXISTS (SELECT 1 "
			    + "FROM ame_review_candidates_list arcl "
			    + "WHERE arcl.ame_id = aafs.ame_id)))")

			public int ForceTotalPending(@Param("forceNo") Integer forceNo);

	//===============================Count of Total AME Completed by Force==================================//

	  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
		        + "FROM medical_board_individual_mapping mbi "
		        + "JOIN ame_final_report_details afr "
		        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
		        + "    AND mbi.board_id = afr.board_id "
		        + "JOIN ame_application_flow_status aafs "
		        + "    ON afr.ame_id = aafs.ame_id "
		        + "WHERE aafs.ame_final_status != 0 "
		        + "AND mbi.force_no = CAST(? AS INTEGER) "
		        + "AND afr.ame_id NOT IN ( "
		        + "    SELECT ame_id "
		        + "    FROM ame_review_candidates_list"
		        + ");")
	  		int ForceTotalCompleted(@Param("forceNo") Integer forceNo);
	  
	//===============================Count of Today Appointment Scheduled==================================//
	  @Query(value = "SELECT COUNT(*) FROM tt_appointment_ame WHERE force_no = ?1 AND DATE(created_on) = CURRENT_DATE", nativeQuery = true)
	     int ForceTodayAMEAppointment(@Param("forceNo") Integer forceNo);
	     
	   //===============================Count of Today Declaration by Force==================================//
		  @Query(nativeQuery = true, value = "SELECT count(*) FROM tt_appointment_ame WHERE declaration_status != 0 AND force_no = ?1 AND DATE(created_on) = CURRENT_DATE")
		  public int ForceTodayDeclaration(@Param("forceNo") Integer forceNo);
		  
		  //===============================Count of Today AME Pending by Force==================================//

		  
			/*
			 * @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
			 * "FROM medical_board_individual_mapping mbim " +
			 * "LEFT JOIN ame_application_flow_status aafs " +
			 * "ON mbim.force_personal_id = aafs.forcepersonal_id " +
			 * "AND mbim.board_id = aafs.board_id " + "WHERE mbim.is_mapping_valid = 1 " +
			 * "AND mbim.force_no = CAST(? AS INTEGER)\r\n" +
			 * "AND (aafs.forcepersonal_id IS NULL OR aafs.ame_final_status = 0)")
			 */
		  
		  
		  @Query(nativeQuery = true, value = 
		    "SELECT COUNT(*) "
		    + "FROM medical_board_individual_mapping mbim "
		    + "LEFT JOIN ame_application_flow_status aafs "
		    + "ON mbim.force_personal_id = aafs.forcepersonal_id "
		    + "AND mbim.board_id = aafs.board_id "
		    + "WHERE mbim.is_mapping_valid = 1 "
		    + "AND mbim.force_no = CAST(? AS INTEGER)\r\n"
		    + "AND (aafs.forcepersonal_id IS NULL "
		    + "OR aafs.ame_final_status = 0 "
		    + "OR (aafs.ame_final_status != 0 "
		    + "AND EXISTS (SELECT 1 "
		    + "FROM ame_review_candidates_list arcl "
		    + "WHERE arcl.ame_id = aafs.ame_id)))")

				public int ForceTodayPending(@Param("forceNo") Integer forceNo);
		 

		//===============================Count of Today AME Completed by Force==================================//

		  @Query(nativeQuery = true, value = "SELECT COUNT(afr.ame_id) "
			        + "FROM medical_board_individual_mapping mbi "
			        + "JOIN ame_final_report_details afr "
			        + "    ON mbi.force_personal_id = afr.candidateforce_personal_id "
			        + "    AND mbi.board_id = afr.board_id "
			        + "    AND DATE(afr.created_on) = CURRENT_DATE "
			        + "JOIN ame_application_flow_status aafs "
			        + "    ON afr.ame_id = aafs.ame_id "
			        + "WHERE aafs.ame_final_status != 0 "
			        + "AND mbi.force_no = CAST(? AS INTEGER) "
			        + "AND afr.ame_id NOT IN ( "
			        + "    SELECT ame_id "
			        + "    FROM ame_review_candidates_list"
			        + ");")
		  		int ForceTodayCompleted(@Param("forceNo") Integer forceNo);
		  
		  
		  @Query(value="SELECT a.status FROM AmeApplicationFlowStatus a " +
			       "WHERE a.ameFinalEsignByBoardMember IN (:ameIds)",nativeQuery = true)
		    List<String> findStatusByAmeId(@Param("ameIds") List<String> ameIds);
		  
		    @Query(value="SELECT * FROM ame_application_flow_status a WHERE a.ame_id IN :ameIds",nativeQuery = true)
		    List<AmeApplicationFlowStatus> findByAmeIds(@Param("ameIds") List<String> ameIds);
}
