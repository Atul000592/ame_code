package nic.ame.app.master.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.AmeDeclarationIndividualModel;



@Repository
public interface AmeDeclarationRepository extends JpaRepository<AmeDeclarationIndividualModel, Integer> {

	//Optional<AmeDeclarationIndividualModel>findbyAmeId(String ameID);
	
     AmeDeclarationIndividualModel findByForcePersonalId(String forcepersonalId);
     
     @Query(nativeQuery = true,value = "select ad.* FROM  tt_ame_declaration_individual ad left join tt_tt_ame_declaration_individual_details adi  on\r\n"
     		+ "adi.ame_id=ad.ame_id where ad.flag='Y' and ad.\"status\"='P' and adi.curent_new_unit=?1")
     List<AmeDeclarationIndividualModel>findByFinalDeclaration(String unitName);
     
     @Query(nativeQuery = true,value = "select ad.* FROM  tt_ame_declaration_individual ad left join tt_tt_ame_declaration_individual_details adi  on\r\n"
      		+ "adi.ame_id=ad.ame_id where ad.flag='Y' and ad.\"status\"='A' and adi.curent_new_unit=?1")
      List<AmeDeclarationIndividualModel>findByFinalDeclarationApproved(String unitName);
     
    @Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual ad where ad.force_personal_id=?1 and ame_id=?2")
 	Optional<AmeDeclarationIndividualModel>findByForcePersonalIdData(String forcePersonalId,String ameId);
     
    //getting data for contoller review
     @Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual ad where ad.force_personal_id=?1 and ad.ame_id=?2")
     AmeDeclarationIndividualModel findByForceIdandAmeId(String forcePersonalId,String ameId);
    
     @Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual ad where  ad.ame_id=?1")
     Optional<AmeDeclarationIndividualModel>findByAmeId(String ameId);
     
 	@Query(nativeQuery = true,value = "select adi.* from  tt_ame_declaration_individual adi left join ame_master_status ams on adi.ame_id=ams.ame_id where adi.status='A' and ams.status='P'")
 	List<AmeDeclarationIndividualModel> findByDataLogic(); 
 	
 	
 	@Query(nativeQuery = true,value = "select adi.* from tt_ame_declaration_individual adi \r\n"
 			+ "inner join ame_appointment_details apd on adi.ame_id=apd.ame_id\r\n"
 			+ "where  adi.status='A' and adi.force_personal_id in(select mbim.force_personal_id from medical_board_individual_mapping mbim where mbim.force_no=?1 and mbim.unit_no=?2)\r\n"
 			+ "")
 	List<AmeDeclarationIndividualModel> findDataForDealingHand(Integer forceNo,String unitNo); 
 	
 	//=====================================Waiting for appointment uploaded list====================================//
 
 	
   @Query(nativeQuery = true,value = "select adi.* \r\n"
 			+ "from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
 			+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  \r\n"
 			+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
 			+ "where  taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
 			+ "and aafs.ame_data_check_flag=1 and  aafs.ame_form_upload_flag=0 and taa.board_id=?1")
 	List<AmeDeclarationIndividualModel> findDataForAMAAppointmentList(String boardId); 

/* 	
 	 @Query(nativeQuery = true,value = "select adi.ame_id,adi.force_id,adi.force_personal_id,adi.name,adi.current_new_unit\r\n"
 	 		+ "	  ,adi.previous_unit,adi.date_of_joining,adi.declaration_date,adi.declaration_flag\r\n"
 	 		+ "	  ,adi.last_ame_date,adi.last_ame_place,adi.ame_place,adi.remark,adi.self_certify_above_info\r\n"
 	 		+ "	  ,adi.approved_by_doctor_status,adi.declaration_year,adi.client_from,adi.ame_final_status\r\n"
 	 		+ "	  ,adi.final_upload_flag,tr.sub_rank_code,tr.rank_full_name\r\n"
 	 		+ "from  tt_appointment_ame taa \r\n"
 	 		+ "inner join tm_rank tr\r\n"
 	 		+ "on adi.rank=tr.id \r\n"
 	 		+ "inner join tt_force_personnel fp \r\n"
 	 		+ "on fp.force_personal_id =taa.force_personal_id\r\n"
 	 		+ "inner join ame_application_flow_status aafs \r\n"
 	 		+ "on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id\r\n"
 	 		+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
 	 		+ "where  taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
 	 		+ "and aafs.ame_data_check_flag=1 and aafs.ame_form_upload_flag \r\n"
 	 		+ "is null or aafs.ame_form_upload_flag=0 and taa.board_id=?1")
  	List<Object> findDataForAMAAppointmentList(String boardId); 
 */	 
 	//=====================================Waiting for appointment and file uploaded completed list====================================//
 	
 @Query(nativeQuery = true,value = "select adi.* from tt_ame_declaration_individual adi\r\n"
 		+ "where adi.ame_id in (select adf.ame_id from (select adi.*  from tt_ame_declaration_individual adi\r\n"
 		+ "left join medical_board_individual_mapping mbim on mbim.force_personal_id = adi.force_personal_id\r\n"
 		+ "where  adi.force_personal_id in (select force_personal_id from medical_board_individual_mapping  where board_id\r\n"
 		+ "in (select board_id from medical_board_member where force_personal_id=:forcepersonalId and role_name =:roleName)))ad,ame_declaration_files adf\r\n"
 		+ "where adf.force_personal_id=ad.force_personal_id and ad.ame_id=ad.ame_id)\r\n")
  List<AmeDeclarationIndividualModel> AppointmentCompletedList(String forcepersonalId, String roleName);
 
 
	//=====================================Waiting for appointment,file uploaded ,dealing hand  completed list====================================//

 @Query(nativeQuery = true,value = 
		    "select adi.ame_id,adi.force_id,adi.force_personal_id,adi.name,adi.current_new_unit\r\n"
		    + "	  	   ,adi.previous_unit,adi.date_of_joining,adi.declaration_date,adi.declaration_flag\r\n"
		    + "				 ,adi.last_ame_date,adi.last_ame_place,adi.ame_place,adi.remark,adi.self_certify_above_info\r\n"
		    + "			     ,adi.approved_by_doctor_status,adi.declaration_year,adi.client_from,adi.ame_final_status\r\n"
		    + "		         ,adi.final_upload_flag,tr.sub_rank_code,tr.rank_full_name \r\n"
		    + "			      from  tt_appointment_ame taa\r\n"
		    + "		    inner join tt_force_personnel fp\r\n"
		    + "		    		  on fp.force_personal_id =taa.force_personal_id\r\n"
		    + "				  inner join ame_application_flow_status aafs\r\n"
		    + "		    		  on aafs.board_id =taa.board_id\r\n"
		    + "		    		  and taa.force_personal_id=aafs.forcepersonal_id\r\n"
		    + "		    		  left join tt_ame_declaration_individual adi\r\n"
		    + "				  on aafs.ame_id = adi.ame_id\r\n"
		    + "				  inner join tm_rank tr\r\n"
		    + "				  on adi.designation=tr.id\r\n"
		    + "		    		  where taa.board_id=?1\r\n"
		    + "		    	  and taa.declaration_status=1\r\n"
		    + "		    		  and aafs.appointment_flag=1\r\n"
		    + "		    	  and aafs.ame_data_check_flag=1 and aafs.ame_form_upload_flag=1 and aafs.check_up_list_flag =1\r\n"
		    + "\r\n"
		    + "")
	  List<Object> AmeApplicationUnderProcesList(String boardId);
 
 	
   @Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual ttad inner join ame_application_flow_status aafs\r\n"
   		+ "on ttad.ame_id=aafs.ame_id where  ttad.force_personal_id=?1")
    List<Object> getAmeStatusFLowForIndividual(String candidateForcePersonnelId);
 
 
   
   @Query(nativeQuery = true,value = "select adi.ame_id,adi.force_id,adi.force_personal_id,adi.name,tr.rank_full_name,adi.declaration_date,adi.remark,adi.declaration_year,adi.final_upload_flag,aafs.ame_final_status,aafs.ame_final_esign_by_board_member,aafs.physical_report_upload_by\r\n"
   		+ "from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id inner join ame_application_flow_status aafs\r\n"
   		+ "on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id left join tt_ame_declaration_individual adi\r\n"
   		+ "on aafs.ame_id = adi.ame_id inner join tm_rank tr on adi.designation=tr.id\r\n"
   		+ "where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1 and aafs.ame_data_check_flag=1 and aafs.ame_form_upload_flag=1 and aafs.check_up_list_flag =1\r\n"
   		+ "		    \r\n"
   		+ "		   ")
   List<Object> AmeApplicationStatusForGo(String boardId);
 
 
}
