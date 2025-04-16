package nic.ame.app.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;



@Repository
public interface AmeDeclarationIndividualModelRepoUser extends JpaRepository<AmeDeclarationIndividualModel,Integer> {

	
	List<AmeDeclarationIndividualModel>findByForcePersonalId(String forcePersonalId);
	
	
	
	@Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual where approved_by_doctor_status in ('P','A')  and force_personal_id=?1")
	Optional<AmeDeclarationIndividualModel>findByStatusRepost(String forcePersonalId);
	
	@Query(nativeQuery = true,value = "select * from tt_ame_declaration_individual where  force_personal_id=?1 order BY declaration_year asc")
	List<AmeDeclarationIndividualModel> findListOfDeclarationByUserIncurrentYear(String forcePersonnel);
	
	@Query(nativeQuery = true,value="select * from tt_ame_declaration_individual where approved_by_doctor_status='A' and ame_id=?1")
	Optional<AmeDeclarationIndividualModel>findByStatus(String ameId);
	
	@Query(nativeQuery = true,value = "select count(*) from tt_ame_declaration_individual where force_id=?1 and declaration_year=?2 and declaration_status_valid=true")
	long getCount(String forceId,long year);
	
	/*
	@Query(nativeQuery = true,value="select * from tt_ame_declaration_individual where force_personal_id=?1")
	List<AmeDeclarationIndividualModel> findByForcePersonald(String forcePersonalId);
	*/
	
	@Query(nativeQuery = true,value=""
			+ "select adi.ame_id,adi.force_id,adi.force_personal_id,adi.name,adi.current_new_unit,adi.previous_unit,adi.date_of_joining,adi.declaration_date,adi.declaration_flag,\r\n"
			+ "adi.last_ame_date,adi.last_ame_place,adi.ame_place,adi.remark,\r\n"
			+ "adi.self_certify_above_info,adi.approved_by_doctor_status,adi.declaration_year,adi.client_from,adi.ame_final_status,\r\n"
			+ "adi.final_upload_flag,tr.sub_rank_code,tr.rank_full_name \r\n"
			+ "from  tt_ame_declaration_individual adi\r\n"
			+ "inner join tm_rank tr\r\n"
			+ "on cast(adi.rank as bigint)=tr.id "
			+ "where force_personal_id=?1")
	List<Object> findByForcePersonald(String forcePersonalId);
	
	@Query(nativeQuery = true,value="select count(*) from tt_ame_declaration_individual where force_personal_id=?1")
    Integer myDeclarationCount(String forcePersonalId);
	
	/*
	 * @Query(value =
	 * "select adi.* from ame_declaration_individual adi left join medical_board_individual_mapping mbim on mbim.force_personal_id = adi.force_personal_id\r\n"
	 * +
	 * "where mbim.board_id in (select board_id from medical_board_member where force_personal_id=:forcePersonalId and role_name =:roleName)"
	 * ,nativeQuery = true)
	 * List<AmeDeclarationIndividualModel>findByAMADeclarationList(String
	 * forcePersonalId,String roleName);
	 */
	
	//===============================If File Uploaded===================================//
	/*select adi.* from ame_declaration_individual adi
	left join medical_board_individual_mapping mbim 
	on mbim.force_personal_id = adi.force_personal_id
	left join ame_declaration_files adf
	on adf.ame_id = adi.ame_id
	where mbim.board_id in (select board_id from medical_board_member where force_personal_id='126BSF1' and role_name ='AMA')
	and adi.force_personal_id in (select board_id from medical_board_member where force_personal_id='126BSF1' and role_name ='AMA')
*/
	@Query(value = "select adi.* from tt_ame_declaration_individual adi\r\n"
			+ "	left join medical_board_individual_mapping mbim \r\n"
			+ "	on mbim.force_personal_id = adi.force_personal_id\r\n"
			+ "	left join ame_declaration_files adf\r\n"
			+ "	on adf.ame_id = adi.ame_id\r\n"
			+ "	where mbim.board_id in (select board_id from medical_board_member where force_personal_id=:forcePersonalId and role_name =:roleName)\r\n"
			+ "	and adi.force_personal_id in (select board_id from medical_board_member where force_personal_id=?1 and role_name =?2)" ,nativeQuery = true)
	List<AmeDeclarationIndividualModel>findByAMADeclarationList(String forcePersonalId,String roleName);
	
	
	//======================new method pending request==========================//
	
	@Query(value = "select adi.* from tt_ame_declaration_individual adi \r\n"
			+ "inner join medical_board_individual_mapping mbim \r\n"
			+ "on mbim.force_personal_id = adi.force_personal_id\r\n"
			+ "left join ame_appointment_details aad\r\n"
			+ "on aad.ame_id = adi.ame_id\r\n"
			+ "where mbim.board_id in (select board_id from medical_board_member where force_personal_id=?1 and role_name =?2)\r\n"
			+ "and aad.status_flag is null",nativeQuery = true)
	List<AmeDeclarationIndividualModel>findByAMADeclarationListNew(String forcePersonalId,String roleName);

 
	
	//======================AME Appointment Pending at AMA Count(*)==========================//
	
		@Query(value = " select count(*) from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id "
				+ "inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id  "
				+ "left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id	"
				+ "where taa.board_id=?1  and aafs.appointment_flag=1 and taa.declaration_status=1 "
				+ "	and aafs.ame_data_check_flag is null or aafs.ame_data_check_flag =0",nativeQuery = true)
		Integer findByAMADeclarationPendingListCount(String boardId);
		
		
		
		
		
		
		//==============================AMAAppointmentList but file upload is pending Count(*)======================================//
	 	@Query(nativeQuery = true,value = "	select count(*)\r\n"
	 			+ " 			from  tt_appointment_ame taa inner join tt_force_personnel fp on fp.force_personal_id =taa.force_personal_id\r\n"
	 			+ " 			inner join ame_application_flow_status aafs on aafs.board_id =taa.board_id and taa.force_personal_id=aafs.forcepersonal_id \r\n"
	 			+ " 			left join tt_ame_declaration_individual adi on aafs.ame_id = adi.ame_id\r\n"
	 			+ " 			where taa.board_id=?1 and taa.declaration_status=1 and aafs.appointment_flag=1\r\n"
	 			+ " 			and aafs.ame_data_check_flag=1")
	 	Integer findDataForAMAAppointmentCount(String boardId);  

	 	
	 	//==============================AMAAppointmentList Count(*)======================================//
	 	@Query(nativeQuery = true,value = "select count(*)from tt_ame_declaration_individual adi,ame_appointment_details apd\r\n"
	 			+ "where adi.ame_id not in (select adf.ame_id from (select adi.*  from tt_ame_declaration_individual adi\r\n"
	 			+ "left join medical_board_individual_mapping mbim on mbim.force_personal_id = adi.force_personal_id\r\n"
	 			+ "where  adi.force_personal_id in (select force_personal_id from medical_board_individual_mapping  where board_id\r\n"
	 			+ "in (select board_id from medical_board_member where  mbim.board_id=?1)))ad,ame_declaration_files adf\r\n"
	 			+ "where adf.force_personal_id=ad.force_personal_id and ad.ame_id=ad.ame_id)\r\n"
	 			+ "and  apd.force_personal_id=adi.force_personal_id")
	 	Integer findDataForAMAAppointmentCountByBoardId(String boardId);  
	 	
	 	
	 	
	 	//=====================================findDataForDealingHandCountValue============================//
	 	@Query(nativeQuery = true,value = "select count(*) from tt_ame_declaration_individual adi \r\n"
	 			+ "inner join ame_appointment_details apd on adi.ame_id=apd.ame_id\r\n"
	 			+ "where  adi.status='A' and adi.force_personal_id in(select mbim.force_personal_id from medical_board_individual_mapping mbim where mbim.force_no=?1 and mbim.unit_no=?2)\r\n"
	 			+ "")
	 	Integer findDataForDealingHandCountValue(String unitNo,Integer forceNo);

		void findByAmeId(String ameId);

		
		@Query(value = "select * from tt_ame_declaration_individual where declaration_year=?1 and force_personal_id=?2",nativeQuery = true)
		Optional<AmeDeclarationIndividualModel> findByForcePersonaldAndYear(int declarationYear,String forcePersonnelId);

		 
	 	
		/* 
		 * @Query(value =
		 * "select * from tt_ame_declaration_individual adi where adi.ame_id=:ameId"
		 * ,nativeQuery = true) Optional<AmeDeclarationIndividualModel>
		 * getPlaceAndDateDto(String ameId);
		 * 
		 */
		
		@Query(nativeQuery=true,value = "select ame_id from tt_ame_declaration_individual \r\n"
				+ "				   where declaration_date=(\r\n"
				+ "				   select MAX(declaration_date)\r\n"
				+ "				   from tt_ame_declaration_individual \r\n"
				+ "				   where force_personal_id=?1\r\n"
				+ "				   group by declaration_date\r\n"
				+ "				   limit 1 offset 1)\r\n"
				+ "				   and force_personal_id=?1	")
		String getForcePersonnelPreviousAmeIdByForcePersonnelId(String forcePersonnelId);
         
       
}
