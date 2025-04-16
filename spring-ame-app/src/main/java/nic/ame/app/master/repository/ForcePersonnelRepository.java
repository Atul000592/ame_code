package nic.ame.app.master.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;





@Repository
public interface ForcePersonnelRepository extends JpaRepository<ForcePersonnel,String>{

	
	@Query(nativeQuery = true,value = "select * from tt_force_personnel where force_personal_id=?1")
	Optional<ForcePersonnel> getByForcePersonnelId(String forcePersonalId);
	
	

	@Query(nativeQuery = true,value = "select fp.force_personal_id,fp.force_id,fp.name,fp.force_no,fp.last_ame_date,fp.joining_date\r\n"
			+ ",fp.doj_present_rank,fp.dob,fp.email_id,fp.gazetted_non_gazetted_flag,fp.mobile_number\r\n"
			+ "				   ,fp.last_ame_shape,fp.previous_ame_category,fp.ame_place\r\n"
			+ "				   ,tr.rank_full_name,tms.m_value,tg.g_value,fp.unit,tu.unit_name,fp.attach_unit\r\n"
			+ "	               ,tjs.value,tf.force_name,tf.force_code_name,tr.sub_rank_code\r\n"
			+ "			\r\n"
			+ "			from tt_force_personnel fp\r\n"
			+ "			inner join tm_rank tr \r\n"
			+ "			on fp.rank=tr.id\r\n"
			+ "			inner join tm_marital_status tms\r\n"
			+ "			on fp.marital_status=tms.m_code and fp.force_no=tms.force_no\r\n"
			+ "			inner join tm_gender tg\r\n"
			+ "			on fp.gender=tg.g_code\r\n"
			+ "			inner join tm_unit tu\r\n"
			+ "			on fp.unit=tu.unit_id\r\n"
			+ "	       \r\n"
			+ "			inner join tm_job_status tjs\r\n"
			+ "			on fp.job_status=tjs.job_status_code\r\n"
			+ "			inner join tm_force tf\r\n"
			+ "			on fp.force_no=tf.force_no\r\n"
			+ "			where force_personal_id=?1")
	Optional<Object> getForcePersonnelDetailsByForcePersonnelId(String forcePersonalId);
	
	@Query(nativeQuery = true,value = "select fp.force_personal_id,fp.force_id,fp.name,fp.force_no,fp.last_ame_date,fp.joining_date,fp.doj_present_rank,fp.dob,fp.email_id,fp.gazetted_non_gazetted_flag,fp.mobile_number,fp.last_ame_shape,fp.previous_ame_category,fp.ame_place,tr.rank_full_name,tms.m_value,tg.g_value,fp.unit,tu.unit_name,fp.attach_unit,tjs.value,tf.force_name,tf.force_code_name,tr.sub_rank_code\r\n"
			+ "from tt_force_personnel fp inner join tm_rank tr on fp.rank=tr.id inner join tm_marital_status tms on fp.marital_status=tms.m_code inner join tm_gender tg on fp.gender=tg.g_code inner join tm_unit tu on fp.unit=tu.unit_id inner join tm_job_status tjs on fp.job_status=tjs.job_status_code\r\n"
			+ "inner join tm_force tf on fp.force_no=tf.force_no where force_id=?1")
	Optional<Object> getForcePersonnelDetailsByIralNo(String irlaNo);
	
	@Query(nativeQuery = true,value="select force_id from tt_force_personnel where force_personal_id=?1")
	String getForceIdByForcePersonalId(String forcePersonalId );
	

	
	@Query(nativeQuery = true,value="select fp.* from tt_force_personnel fp where not exists (select * from tt_ngo_ame_candidate_request_details tnac where \r\n"
			+ "					tnac.candidate_forcepersonal_id=fp.force_personal_id) and fp.force_no=2 and fp.unit='2';")
	List<ForcePersonnel>  getForcePersonalListNGO(String forcePersonalId );
	
	
	@Query(nativeQuery = true,value = "select fp.* from tt_force_personnel fp left join ref_rank rr on fp.rank=rr.rank_name where rr.rank_in_hierarchy<10 and fp.force_no=?1 and fp.unit=?2")
	List<ForcePersonnel> getForcePersonalListNGONew(int forceNo,String unit);
	
	
	@Query(value = "\r\n"
			+ "select * from tt_force_personnel fp where not exists \r\n"
			+ "(select * from medical_board_individual_mapping mbim\r\n"
			+ " where mbim.force_personal_id = fp.force_personal_id \r\n"
			+ " AND  fp.force_no=mbim.force_no and fp.unit=mbim.unit_no)\r\n"
			+ " and fp.force_no=:forceNo and fp.unit=:unit",nativeQuery = true)
	List<ForcePersonnel> getIntialNgoRequestListToControlling(int forceNo,String unit);
	
	
	@Query(nativeQuery = true,value = "select *\r\n"
			+ "from tt_force_personnel fp \r\n"
			+ "inner join tt_ngo_ame_candidate_request_details tnac \r\n"
			+ "on fp.force_personal_id=tnac.candidate_forcepersonal_id\r\n"
			+ "where tnac.transaction_id=?1")
	List<ForcePersonnel> getNgoAmaListWithTransactionalId(String transactionalId);
	
	//get Count of Declaration for PO
	@Query(nativeQuery = true,value = "select count(*)  from tt_force_personnel fp \r\n"
			+ "left join medical_board_individual_mapping mbim on fp.force_personal_id =mbim.force_personal_id \r\n"
			+ "left join ame_application_flow_status aafs on fp.force_personal_id =aafs.forcepersonal_id \r\n"
			+ "where mbim.board_id =?1 and mbim.declaration_status ='d' and aafs.ame_form_upload_flag ='Yes' \r\n"
			+ "and aafs.appointment_flag ='Yes' and aafs.check_up_list_flag ='Yes'")
	    int getCountByBoardid(String boardId);
	
	
	//get declaration for PO
	@Query(nativeQuery = true,value = "select fp.*  from tt_force_personnel fp \r\n"
			+ "left join medical_board_individual_mapping mbim on fp.force_personal_id =mbim.force_personal_id \r\n"
			+ "left join ame_application_flow_status aafs on fp.force_personal_id =aafs.forcepersonal_id \r\n"
			+ "where mbim.board_id =?1 and mbim.declaration_status ='d' and aafs.ame_form_upload_flag ='Yes' \r\n"
			+ "and aafs.appointment_flag ='Yes' and aafs.check_up_list_flag ='Yes'")
	List<ForcePersonnel> getForcePersonalListForPO(String boardId);
	
	
	//get List of Role given to a user
	@Query(nativeQuery = true,value = "select fp.force_personal_id,fp.name,fp.force_id,tf.force_name,tf.force_code_name,fp.force_no,fp.unit,rr.role_name,\r\n"
			+ "	   tr.rank_full_name,tr.sub_rank_code \r\n"
			+ "from tt_force_personnel fp \r\n"
			+ "inner join user_role ur \r\n"
			+ "on fp.force_personal_id =ur.force_personal_id\r\n"
			+ "inner join tm_force tf  \r\n"
			+ "on fp.force_no = tf.force_no\r\n"
			+ "inner join tm_rank tr\r\n"
			+ "on fp.rank=tr.id\r\n"
			+ "inner join ref_role rr \r\n"
			+ "on ur.role_id =rr.role_id 	\r\n"
			+ "where  ur.mapped_by=?1")
	List<Object> ListOfRoleAssignToUser(String loginForcePersonalId);
	
	
	
	//get list of role given to a user temp
	@Query(nativeQuery = true,value = "\r\n"
			+ "select fp.name ,fp.force_id ,fp.rank ,f.force_name_english,fp.force_no,fp.unit,rr.role_name,fp.force_personal_id from tt_force_personnel fp \r\n"
			+ "inner join user_role_temp urt  on fp.force_personal_id =urt.force_personal_id \r\n"
			+ "inner join force f  on fp.force_no = f.force_id \r\n"
			+ "inner join ref_role rr on urt.role_id =rr.role_id where urt.transaction_id =?1")
	List<Object> ListOfRoleAssignToUserTemp(BigInteger transactionId);
	
	
	
	
	
	
	
	
	//get declaration List for PO (New)
	@Query(nativeQuery = true,value = "select aafs.ame_id,fp.force_personal_id ,fp.\"name\" ,fp.force_id ,fp.force_no,fp.unit,taa.board_id  from tt_force_personnel fp \r\n"
			+ "inner join tt_appointment_ame taa on fp.force_personal_id =taa.force_personal_id\r\n"
			+ "inner join ame_application_flow_status aafs on aafs.forcepersonal_id =taa.force_personal_id \r\n"
			+ "where taa.declaration_status=1 and taa.board_id=?1 and aafs.ame_form_upload_flag =0 and aafs.check_up_list_flag=0")
	
	  List<Object> getDeclarationForcePersonalListForPO(String boardId);
	
	
	
	
	//get createdBy details of board by boardId
	@Query(nativeQuery = true,value = "select fp.name,fp.rank,f.force_name,tu.unit_name,fp.force_id from medical_board mb inner join tt_force_personnel fp on mb.created_by=fp.force_personal_id inner join tm_force f on f.force_no=cast(fp.force_no  as int) inner join tm_unit tu on tu.force_no =cast(fp.force_no as int) and tu.unit_id =fp.unit where mb.board_id=?1")
	List<Object> getCreatedByDetailsByBoardId(String boardId);
//================================================Ame Final Details============================================================//
	@Query(nativeQuery = true,value = "select fp.force_id,fp.name,fp.rank,f.force_name,tu.unit_name,fp.dob,fp.gender,fp.joining_date  "
			+ "from tt_force_personnel fp inner join tm_force f on fp.force_no=f.force_no "
			+ "inner  join tm_unit tu on tu.force_no =fp.force_no and tu.unit_id =fp.unit where fp.force_personal_id =?1")
	List<Object> getForcePersonalDetailsObject(String forcePersonalId);
	
//============================================================================================================================//	
	
	@Query(nativeQuery = true,value = "select fp.force_id,fp.name,fp.rank,f.force_name_english,tu.unit_name,fp.gender,fp.dob,\r\n"
			+ "joining_date,adi.declaration_date \r\n"
			+ "from tt_force_personnel fp \r\n"
			+ "inner join \r\n"
			+ "force f \r\n"
			+ "on fp.force_no=f.force_id\r\n"
			+ "inner  join tm_unit tu \r\n"
			+ "on tu.force_no =fp.force_no\r\n"
			+ "inner join tt_ame_declaration_individual adi on\r\n"
			+ "fp.force_id=adi.force_id\r\n"
			+ "and tu.unit_id =fp.unit \r\n"
			+ "where fp.force_id where fp.force_id =?1")
	List<Object> getForcePersonalDetailsObjectByForceId(String forceId);
	
//===========================================================================================================================//
	
	@Query(nativeQuery = true,value = "select fp.\"name\" ,fp.force_id ,fp.force_personal_id ,fp.designation,tmf.force_name,tu.unit_name \r\n"
			+ "from tt_force_personnel fp inner join tm_force tmf on fp.force_no =tmf.force_no \r\n"
			+ "inner join tm_unit tu on tu.unit_id =fp.unit and fp.force_no =cast(tu.force_no as int)\r\n"
			+ "where fp.unit=?1 and fp.force_no =?2")
	List<Object> getListOfForcePersonalToAssignDealingHand(String unit,int forceNo);
//===========================================Get Decalration Date============================================================//	
	@Query(nativeQuery =true,value="select declaration_date from tt_ame_declaration_individual where ame_id=?1")
	Date getDecalarionDate(String ameId);
	
//===========================================Get BoardId by AmeId============================================================//
	@Query(nativeQuery =true,value="select board_id from ame_application_flow_status where ame_id=?1")
	String getBoardId(String ameId);
//===========================================================================================================================//	
     
	@Modifying
    @Query(nativeQuery = true,value = "update tt_force_personnel  set doctor_flag='Y' where force_personal_id=?1")
	void updateMedicalCadre(String id);


	@Query(nativeQuery = true,value = "select * from tt_force_personnel fp where (fp.force_no =?1 and fp.unit =?2 or fp.attach_unit=?2) and fp.doctor_flag ='Y' ")
List<ForcePersonnel> getDoctorsListByForceAndUnit(Integer force_no, String unit);


	Optional<ForcePersonnel> findByForcePersonalId(String candidateForcePersonalId);
	
	@Query(nativeQuery = true,value="select rank_full_name from tm_rank where id=?1")
	String getRankAndDesignationById(Integer id);
	
	@Query(nativeQuery = true,value="select unit_id FROM tm_unit where unit_name=?1")
	String getUnitCodeByUnitName(String unitName);	
	
	@Query(nativeQuery = true, value = "select * from tt_force_personnel where force_id=?1")
	Optional<ForcePersonnel> findByForceId(String forceId);
	
	
}
