package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.model.TTAppointmentAme;

@Repository
public interface TTAppointmentAmeRepo extends JpaRepository<TTAppointmentAme,Integer> {
	
	@Query(nativeQuery = true,value = "Select * from tt_appointment_ame where force_personal_id=?1 and board_Id=?2 and declaration_invalid_flag=0 and is_appointment_valid !=0")
	Optional<TTAppointmentAme> getAppointmentStatusForForcePersonal(String forcePersonalId,String boardId);
	
	@Query(nativeQuery = true,value="select taa.board_id,fp.force_id ,fp.name,f.force_name,tu.unit_name,\r\n"
			+ "					taa.declaration_status,taa.from_date,taa.to_date,fp.gender,taa.is_appointment_valid,fp.force_personal_id,taa.reschedule_flag\r\n"
			+ "			from tt_force_personnel fp\r\n"
			+ "			inner join tt_appointment_ame taa on fp.force_personal_id =taa.force_personal_id\r\n"
			+ "			inner join tm_force f  on f.force_no =fp.force_no\r\n"
			+ "			inner join tm_unit tu on tu.force_no =fp.force_no and tu.unit_id =fp.unit \r\n"
			+ "			where taa.board_id =?1")
	List<Object> getListOfForcePersonalWithAppointmentProvided(String boardId);
	
	
	@Query(nativeQuery = true,value = "Select count(*) from tt_appointment_ame where force_personal_id=?1")
	int getAppointmentStatusCount(String forcePersonalId);
	
	
	
	
	@Query(nativeQuery = true,value = "select count(*) from medical_board_individual_mapping mbim  where mbim.appointment_status=?1 and  board_id =?2")
	int getAppointmentCompleteCount(int status,String boardId);
	
	@Query(nativeQuery = true,value = "select count(*)  from medical_board_individual_mapping mbim  where mbim.appointment_status=?1 and  board_id =?2")
	int getAppointmentPendingCount(int status,String boardId);

	@Modifying
	@Query(nativeQuery = true,value = "update tt_appointment_ame set declaration_status=?1,declaration_year=?2 where force_personal_id=?3 and board_id =?4 and declaration_invalid_flag=0")
	public void updateDeclaationStatusInTTAppointment(int dStatus,String year,String forcePersonalId,String boardId);
	
	
	@Query(nativeQuery = true,value = "select count(*) from tt_appointment_ame tta where tta.board_id=?1 and tta.is_appointment_valid=1")
	int getAppointmentCountByBoardId(String boardId);

	Optional<TTAppointmentAme> findByForcePersonalIdAndIsAppointmentValidAndDeclarationYear(
	String forcePersonnelId, int appointmentCode,String year);
	
	@Query(nativeQuery = true,value = "select * from tt_appointment_ame where declaration_status=0 and is_appointment_valid=1 and to_date < Current_Date")
	Page<TTAppointmentAme> findAllForcePersonnel(Pageable pageable);
	
	@Modifying
	@Query(nativeQuery = true,value = "update tt_appointment_ame set reschedule_flag=false, declaration_invalid_flag=1, declaration_status=910 where force_personal_id=?1 and board_id =?2 and reschedule_flag=true")
	public void updateRescheduleFlag(String forcePersonnelId,String boardId);
	 
	Optional<TTAppointmentAme> findByBoardIdAndForcePersonalId(String boardId,String forcePersonnelId);
	
	@Query(nativeQuery = true, value="select * from tt_appointment_ame where board_id =?1 and force_personal_id=?2 and (declaration_year IS NULL OR declaration_year=?3) and is_appointment_valid=?4")
	List<TTAppointmentAme> getByBoardIdAndForcePersonalIdAndDeclarationYearAndIsAppointmentValid(String boardId,String forcePersonnelId,String year,int isAppointmentValid); 
	
	@Query(nativeQuery = true, value="select * from tt_appointment_ame where board_id =?1 and force_personal_id=?2 and is_appointment_valid=?3")
	Optional<TTAppointmentAme> findByBoardIdAndForcePersonalIdAndIsAppointmentValid(String boardId,String forcePersonnelId,int isAppointmentValid); 
	
	
	@Query(nativeQuery = true, value="select * from tt_appointment_ame where board_id =?1 and force_personal_id=?2 and (declaration_year IS NULL OR declaration_year=?3) and reschedule_flag=true")
	Optional<TTAppointmentAme> getByBoardIdAndForcePersonalIdAndDeclarationYear(String boardId,String forcePersonnelId,String year);
	
	@Query(nativeQuery = true, value="select * from tt_appointment_ame where board_id =?1 and force_personal_id=?2 and declaration_invalid_flag=?3 and declaration_status=?4")
	Optional<TTAppointmentAme> getByBoardIdAndForcePersonnelIdAndDeclarationInvalidFlagAndDeclarationStatus(String boardId,String forcePersonnelId,int decalarationInvalidFlag,int declarationStatus);
	
	
}
