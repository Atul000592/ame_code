package nic.ame.app.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;


@Repository
public interface MedicalBoardIndividualMappingRepo extends JpaRepository<MedicalBoardIndividualMapping, Long>{
  
	
	@Query(nativeQuery = true,value = "select count(*)  from medical_board_individual_mapping mbim inner join medical_board_member mbm on mbim.board_id =mbm.board_id where mbim.force_personal_id =?1 and mbm.board_year=?2")
    public int getforcePersonalDetailsFormBoardCount(String forcePersonalId,String year);
   
	@Modifying
	@Query(nativeQuery = true,value="update medical_board_individual_mapping set declaration_status='d' where force_personal_id=:forcePersonalId")
	public void updateDeclarationStatus(String forcePersonalId);
	
	
	List<MedicalBoardIndividualMapping> findByBoardId(String boardId);

   @Modifying
   @Query(nativeQuery = true,value = "delete from medical_board_individual_mapping  where force_personal_id =?1 and board_id =?2")
   public void deleteByBoardIdandForcePersonal(String forcePersonalId,String boardId);
	
	@Query(nativeQuery = true,value = "select * from medical_board_individual_mapping where force_personal_id=:forcePersonalId")
    public Optional<MedicalBoardIndividualMapping> getforcePersonalDetailsFormBoard(String forcePersonalId);
		
	@Query(nativeQuery = true,value = "select force_personal_id FROM medical_board_individual_mapping where board_id=?1")
	public List<String> ListOfForcePersonalForAmeAppointment(String boardId);
	
	@Query(nativeQuery = true,value="select mbim.board_id,fp.force_personal_id,fp.name,fp.force_id ,fp.rank,tmf.force_name,tu.unit_name,fp.gender \n"
			+ "from medical_board_individual_mapping mbim  \n"
			+ "inner join tt_force_personnel fp on mbim.force_personal_id =fp.force_personal_id\n"
			+ "inner join tm_force tmf  on tmf.force_no =fp.force_no\n"
			+ "inner join tm_unit tu on tu.force_no =fp.force_no and tu.unit_id =fp.unit\n"
			+ "where mbim.board_id =?1 and mbim.appointment_status=?2 and mbim.is_mapping_valid not in(0,2)")
	public List<Object> getListOfForcePersonalForAmeAppointment(String boardId,int status);
	
	
	@Modifying
	@Query(nativeQuery = true,value="update medical_board_individual_mapping set appointment_status=?1 where force_personal_id=?2 and board_id=?3")
	public void updateAppointmentStatus(int statusCode,String forcePersonalId,String boardId);
	
	@Query(nativeQuery = true,value = "select count(*) from medical_board_member mbm where force_personal_id =?1 and status_code =?2")
	public int boardCount(String forcePersonalId,int statusCode);
	
	
	//==============not in use ==========================================//
	@Query(nativeQuery = true,value = "select mbim.board_id,mb.used_for,mbim.year,mbim.appointment_status,taa.declaration_status,mbim.force_personal_id,taa.from_date,taa.to_date \n"
			+ "from medical_board_individual_mapping mbim  \n"
			+ "inner join tt_appointment_ame taa on taa.force_personal_id=mbim.force_personal_id  \n"
			+ "inner join medical_board mb on mb.board_id = mbim.board_id\n"
			+ "where mbim.force_personal_id =?1 and mbim.is_mapping_valid='1'and  taa.declaration_invalid_flag=0")
	public List<Object> getDeclarationDetailsByBoardAndYear(String forcePersonalId);
	
	
	@Query(nativeQuery = true,value = "select mb.board_id,mb.used_for,mb.board_year,taa.is_appointment_valid ,taa.declaration_status  ,fp.force_personal_id,taa.from_date,taa.to_date from tt_appointment_ame taa \n"
			+ "inner join tt_force_personnel fp on fp.force_personal_id = taa.force_personal_id\n"
			+ "inner join medical_board mb on mb.board_id = taa.board_id where taa.force_personal_id=?1  order By taa.created_on desc")
	public List<Object> getForcePersonnelAppointmentDetailsByForcePersonnelId(String forcePersonnelId);
	
	
	@Query(nativeQuery = true,value = "select count(*) from medical_board_individual_mapping mbim inner join medical_board mb \n"
			+ "on mbim.board_id=mb.board_id where mb.created_by =?1")
	public int getTotalPersonalMappedToBoardCount(String loginForcePersonalId);
	
	

	@Query(nativeQuery = true,value = "\n"
			+ "select mbim.force_no,mbim.board_id,mbim .\"year\" ,mb.place,mb.used_for,fp.\"name\",fp.designation,fp.force_id,mbim.appointment_status \n"
			+ "from medical_board_individual_mapping mbim inner join medical_board mb \n"
			+ "on mbim.board_id=mb.board_id\n"
			+ "inner join tt_force_personnel fp on fp.force_personal_id=mbim.force_personal_id \n"
			+ "where mb.created_by =?1")
	public List<Object> mappedMemberBoardMemberDtos(String loginForcePersonalId);

	
	@Query(nativeQuery = true,value = "select  fp.force_id,fp.force_personal_id,fp.name,"
			+ "mbim.board_id,mbim.appointment_status,mbim.year,taa.declaration_status,mb.place,mb.board_at_force_no from"
			+ "  medical_board_individual_mapping mbim left join tt_appointment_ame taa on mbim.board_id=taa.board_id "
			+ "left join tt_force_personnel fp on mbim.force_personal_id=fp.force_personal_id "
			+ "left join medical_board mb on mbim.board_id=mb.board_id	where mb.created_by=?1")
	public List<Object> getAllMappedMemberToAllBoardByForcePersonalId(String forcePersonalId);
	
	

     /*member mapped to the board and appointment status*/
	
	@Query(nativeQuery = true,value = "select fp.force_no,mb.board_id,fp.force_id,fp.name,mb.place,mbim.appointment_status from medical_board_individual_mapping mbim \n"
			+ "inner join tt_force_personnel fp on fp.force_personal_id = mbim.force_personal_id\n"
			+ "inner join medical_board mb  on mb.board_id =mbim.board_id \n"
			+ "where mbim.force_personal_id =fp.force_personal_id and mbim.appointment_status=0 and mb.created_by=?1")
	public List<Object> mappedMemberWithAppointmentProvided(String forcePersonalId);
	
	
	
	
	@Query(nativeQuery = true,value = "select count(*) from medical_board_individual_mapping mbim where mbim.board_id=?1 \n"
			+ "and mbim.is_mapping_valid=1")
	int getMappedMemberToBoardByBoardId(String boardId);
	
	 Optional<MedicalBoardIndividualMapping> findByBoardIdAndForcePersonalId(String boardId,String forcePersonnelId);
	 
	 @Query(nativeQuery = true,value = "select * from medical_board_individual_mapping mbim where mbim.board_id=?1 and mbim.force_personal_id=?2 and mbim.is_mapping_valid=?3")
     Optional<MedicalBoardIndividualMapping> findByBoardIdAndForcePersonalIdAndIsMappingValid(String boardId,String forcePersonnelId,int isMappingValid);

	 
	 
	 @Query(nativeQuery = true,value = "select * from medical_board_individual_mapping where board_id =?1\n"
	 		+ "and force_personal_id=?2 and appointment_status=?3 and is_mapping_valid=?4")
	 Optional<MedicalBoardIndividualMapping> findByBoardIdAndForcePersonalIdAndAppointmentStatusAndIsMappingValid(String boardId,String forcePersonnelId,int appointmentStatus,int isMappingValid);

		 //====get list of all mapped member to a force personnel(AMA or Board member) ========//
	 @Query(nativeQuery = true,value = "select fp.force_personal_id as candidate_force_personnel_id,fp.force_id as irla_no,mbm.board_year,fp.name,fp.rank,fp.unit,fp.attach_unit,fp.dob from medical_board_individual_mapping mbim \n"
	 		+ "inner join tt_force_personnel fp on fp.force_personal_id = mbim.force_personal_id \n"
	 		+ "inner join medical_board_member mbm on mbm.board_id = mbim.board_id\n"
	 		+ "where mbm.force_personal_id=?1 and mbm.gazetted_flag='0'")
     List<Object> listOfMappedForcePersonnelToBoardMemberNGO(String forcePersonnelId);  
	
	
	
	
	
	
	
	
	
}
