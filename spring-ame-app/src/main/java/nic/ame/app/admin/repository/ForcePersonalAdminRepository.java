package nic.ame.app.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.PercentEscaper;

import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.PersonnelOthers;



@Repository
@EnableJpaRepositories
public interface ForcePersonalAdminRepository extends JpaRepository<ForcePersonnel,String>{

	

	Optional<ForcePersonnel> getByForcePersonalId(String forcePersonalId);
	
	//Custom query
	 @Query(value = "select * from tt_force_personnel f where f.cadre_medical='YES' and f.force_personal_id like %:keyword% or f.name like %:keyword% or f.designation like %:keyword% ", nativeQuery = true)
	 List<ForcePersonnel> findByKeyword(@Param("keyword") String keyword);
	 
	 
		
		//Custom query
	//	 @Query(value = "select * from tt_force_personnel f where f.force_no=:forceNo and f.unit=:unitId and f.attach_unit is null", nativeQuery = true)
	//	 List<ForcePersonnel> getByForceandUnitId(@Param("forceNo") Integer forceId,@Param("unitId") String unitId);
	 
	 @Query(value = "select * from tt_force_personnel f where f.force_no=?1 and f.unit=?2 and f.attach_unit is null", nativeQuery = true)
	 List<ForcePersonnel> getByForceandUnitId(@Param("forceNo") Integer forceId,@Param("unitId") String unitId);
 
	 
	 
			//Custom query
		 @Query(value = "select * from tt_force_personnel f where f.cadre_medical='YES' ", nativeQuery = true)
		 List<ForcePersonnel> getByForceByCadre();
		 
		 
		 @Query(nativeQuery = true,value = ""
		 		+ "SELECT fp.force_personal_id,fp.force_id,fp.\"name\",fp.force_no,fp.unit,fp.designation,fp.gender FROM tt_force_personnel fp where not exists (SELECT  mbim.force_personal_id  FROM medical_board_individual_mapping mbim\r\n"
		 		+ "		 		 WHERE mbim.force_no=?1 and mbim.unit_no=?2 and fp.force_personal_id=mbim.force_personal_id \r\n"
		 		+ "				  and mbim.is_mapping_valid=1 and mbim.year=?4)\r\n"
		 		+ "		 		 and fp.force_no=?1 and fp.unit=?2 \r\n"
		 		+ "				 and fp.gazetted_non_gazetted_flag=?3 \r\n"
		 		+ "				 and fp.attach_unit is null") 
		 
	     List<Object> getForcePersonalListByForceNoAndUnit(int forceNo,String unit,String gFlag,String year);
		 
		 
		 //======================attach unit==================================//
		 @Query(value = "SELECT fp.* FROM tt_force_personnel fp where not exists "
			 		+ "(SELECT  mbim.force_personal_id  FROM medical_board_individual_mapping mbim"
			 		+ " WHERE mbim.force_no=:forceNo and mbim.unit_no=:attach_unit and fp.force_personal_id=mbim.force_personal_id and mbim.is_mapping_valid=1"
			 		+ " and mbim.year=:boardYear)"
			 		+ " and fp.force_no=:forceNo and fp.attach_unit=:attach_unit and fp.gazetted_non_gazetted_flag=:gFlag ",nativeQuery = true) 
			 
		     List<ForcePersonnel> getForcePersonalListByForceNoAndAttachUnit(int forceNo,String attach_unit,String gFlag,String boardYear);
		 
		 
		 
		 @Query(value = "SELECT fp.* FROM tt_force_personnel fp where not exists "
			 		+ "(SELECT  mbim.force_personal_id  FROM medical_board_individual_mapping mbim"
			 		+ " WHERE mbim.force_no=:forceNo and mbim.unit_no=:unit and fp.force_personal_id=mbim.force_personal_id and mbim.board_id =:boardId)"
			 		+ "and fp.force_no=:forceNo and fp.unit=:unit  ",nativeQuery = true) 
			 
		     List<ForcePersonnel> getMappedMamberByBoardId(int forceNo,String unit,String boardId);
			 

		 
		 @Query(value = "select fp.force_personal_id,fp.force_id,fp.name,fp.force_no,fp.unit,fp.designation,fp.gender \r\n"
		 		+ "FROM tt_force_personnel fp where  exists \r\n"
		 		+ "(select mbim.force_personal_id FROM medical_board_individual_mapping mbim\r\n"
		 		+ "WHERE  fp.force_personal_id=mbim.force_personal_id and mbim.is_mapping_valid=1 and mbim.board_id =?1)",nativeQuery = true) 
			 
		     List<Object> getMappedMamberByBoardIdOnly(String boardId);
		 
		 
		 @Query(nativeQuery = true,value = "select * FROM tt_force_personnel fp inner join "
		 		+ "medical_board_individual_mapping mbim on mbim.force_personal_id = fp.force_personal_id\r\n"
		 		+ "where mbim.board_id=?1 and mbim.appointment_status=?2")
		 List<Object> getTheListOfForcePersonalWithoutAppointment(String boardId,int status);
		 
		  @Query(value = "select  fp.name,fp.force_no,fp.unit,tmr.rank_full_name,fp.force_id,fp.force_personal_id,fp.gender \r\n"
		  		+ "from tt_force_personnel fp \r\n"
		  		+ "inner join tm_rank tmr on tmr.id = fp.designation\r\n"
		  		+ "where fp.unit=?1 and fp.force_no=?2 and fp.doctor_flag='Y' and fp.attach_unit is null ",nativeQuery = true)
		 List<Object> getListOfMedicalCadreForcePersonalByForceNoAndUnit(String unit,int forceNo);
		  
		  @Query(value = "select  fp.name,fp.force_no,fp.unit,fp.designation,fp.force_id,fp.force_personal_id,fp.attach_unit,fp.gender from tt_force_personnel fp"
			 		+ " where fp.attach_unit=?1 and fp.force_no=?2 and fp.doctor_flag='Y'",nativeQuery = true)
			 List<Object> getListOfMedicalCadreForcePersonalByForceNoAndAttachUnit(String attachUnit,int forceNo);

		
		  @Query(nativeQuery = true,value = "select * from tt_force_personnel fp where fp.force_no=2 and fp.unit='360' and (fp.doctor_flag ='N' OR fp.doctor_flag is null)")
		  List<ForcePersonnel> getForcePersonalListByForceNoAndUnitForNonMedic(int force_no, String unit);

		 @Query(nativeQuery = true,value="select * from tt_force_personnel fp where fp.force_no=?1 and fp.attach_unit=?2" ) 
		 List<ForcePersonnel> findByForceNoAndAttachUnit(int force_no, String attachUnit);

		 
		 @Query(nativeQuery = true,value="select * from tt_force_personnel	fp where fp.force_personal_id "
		 		+ "not in (select mpid.candidate_force_personal_id from map_individual_and_unit_to_reporting mpid where mpid.status ='1' )"
		 		+ " and fp.force_no=:force_no and fp.unit=:unitId and fp.attach_unit is null" ) 
		List<ForcePersonnel> getforcePersonnelListForReportingOfficerMapping(int force_no, String unitId);
		 
		@Query(nativeQuery = true,value="select * from tt_force_personnel	fp where fp.force_personal_id "
		+ "not in (select mpid.candidate_force_personal_id from map_individual_and_unit_to_reporting mpid where mpid.status ='1' )"
		+ " and fp.force_no=:force_no and fp.attach_unit=:attachUnitId" ) 
        List<ForcePersonnel> getforcePersonnelListForReportingOfficerMappingAttachUnit(int force_no, String attachUnitId);
		
		
//		------------------------------------getForcePersonnelForRoleAssigning---------------------------------------------
		
		@Query(nativeQuery=true,value="select tfp.force_id,tfp.name,tr.rank_full_name,tfp.unit\r\n"
				+ "from tt_force_personnel tfp\r\n"
				+ "inner join tm_rank tr\r\n"
				+ "on tfp.designation=tr.id\r\n"
				+ "where tfp.force_no=?1 and tfp.designation=?2\r\n"
				+ "")
		List<Object> getForcePersonnelListByForceNoAndDesignation(Integer forceNo,Integer designation);
		
		
		  
		@Query(nativeQuery=true,value="select  mbim.force_personal_id \r\n"
				+ "from medical_board_individual_mapping mbim  \r\n"
				+ "where mbim.force_personal_id \r\n"
				+ "not in \r\n"
				+ "(select taa.force_personal_id \r\n"
				+ "from tt_appointment_ame taa \r\n"
				+ "where taa.is_appointment_valid=1 \r\n"
				+ "and (taa.is_appointment_valid=1 \r\n"
				+ "and  taa.declaration_status='1') \r\n"
				+ "and taa.board_id=?1)\r\n"
				+ "and mbim.is_mapping_valid=1 and mbim.board_id=?1") 
		 List<String> getBoardIdAndForcePersonnelIdForUnMapping(String boardId);
		
		 
		
		@Query(nativeQuery=true,value="update medical_board_individual_mapping mbim set mbim.is_mapping_valid=0 where mbim.board_id=?1 and mbim.force_personal_id=?2")
		 Optional<MedicalBoardIndividualMapping> unmapForcePersonnelFormBoard(String boardId,String forcePersonnelId);

		 @Query(nativeQuery=true, value="select tfp.force_id, tfp.name, tr.rank_full_name, tfp.gender,tfp.force_personal_id " +
		 "from tt_force_personnel tfp " +
		 "inner join tm_rank tr " +
		 "on tfp.designation = tr.id " +
		 "where tfp.force_no = ?1 " +
		 "and tfp.unit = ?2 " +
		 "and tfp.doctor_flag = 'Y'")		 
 List<Object> getListOfboardForcePersonalAssignBoard(int force_no, String unit);
		 
		 @Query(nativeQuery=true, value="select tfp.force_id, tfp.name, tr.rank_full_name, tfp.gender,tfp.force_personal_id " +
				 "from tt_force_personnel tfp " +
				 "inner join tm_rank tr " +
				 "on tfp.designation = tr.id " +
				 "where tfp.force_no = ?1 " +
				 "and tfp.attach_unit = ?2 " +
				 "and tfp.doctor_flag = 'Y'")		 
		 List<Object> getListOfboardForcePersonalAssignBoardAttachUnit(int force_no, String unit);
         
		 @Query(nativeQuery = true,value ="select count(*) from tt_force_personnel fp \r\n"
		 		+ "inner join medical_board_individual_mapping  mbim on mbim.force_personal_id = fp.force_personal_id\r\n"
		 		+ "where fp.force_id=?1 and  YEAR =?2 and mbim.is_mapping_valid=?3")
		int getIsMappedCountByIrlaNoAndBoardYearForForcePersonnel(String irlaNo, String boardYear, int isMappedValid);
 
 
		
		 
		
		 
}
