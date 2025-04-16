package nic.ame.app.admin.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.model.MedicalBoard;




@Repository
public interface MedicalBoardRepo extends JpaRepository<MedicalBoard, String>{
	
	  
	@Query(value = "select * from medical_board where board_id=:boardId", nativeQuery = true)
	public MedicalBoard getByBoardId(String boardId);

	@Query(value = "select tmf.force_name,tu.unit_name ,mb.board_id,mb.board_year,mb.created_on,mb.to_date,mb.from_date,mb.gazetted_flag,mb.used_for  from medical_board mb "
			+ "inner join tm_force tmf on cast(mb.board_at_force_no as int) =tmf.force_no \n"
			+ " inner join tm_unit tu on place =tu.unit_id and cast(mb.board_at_force_no as int)=tu.force_no  where mb.created_by=?1 order by mb.created_on DESC", nativeQuery = true)
	public List<Object> getMedicalBoardByCreatedBy(String loginForcePersonalId);
	
	@Query(value = "select * from medical_board mb where  mb.created_by=?1 and mb.gazetted_flag=?2 ORDER By created_on DESC",nativeQuery = true)
	public List<MedicalBoard> getMedicalBoardByCreatedByForcePersonalByGflag(String loginForcePersonalId,int gFlag);
	
	@Query(value = "select * from medical_board mb where  mb.created_by=?1",nativeQuery = true)
	public List<MedicalBoard> getMedicalBoardByCreatedByForcePersonal(String loginForcePersonalId);
   
	MedicalBoard findByBoardId(String boardId);

	 @Query(nativeQuery = true,value = "select mb.board_id,tmf.force_name,tu.unit_name, mb.used_for,mb.created_on  from medical_board_member mbm \n"
	 		+ "inner join medical_board mb on mbm.board_id=mb.board_id \n"
	 		+ "inner join tm_force tmf on tmf.force_no =cast(mb.board_at_force_no as int) \n"
	 		+ "inner join tm_unit tu on tu.force_no =tmf.force_no and tu.unit_id =mb.place where mb.board_id =?1")
	 List<Object> findByBoardIdDto(String boardId);
    
	@Query(nativeQuery = true, value = "select count(*) from medical_board mb where created_by = ?1")
	public int getBoardCountByForcePersonalId(String loginForcePersonalId);

	@Query(nativeQuery = true, value = "select mbm.board_year  from medical_board mbm where mbm.board_id =?1")
	public String getBoardYear(String boardId);

	@Query(value = "select * from medical_board where board_id=:boardId", nativeQuery = true)
	public Optional<MedicalBoard> getByBoardIdOptional(String boardId);
	
	@Query(value = "select tu.unit_full_name from medical_board mb inner join tm_force tf on tf.force_no = cast(mb.board_at_force_no as int)\n"
			+ "inner join tm_unit tu on tu.force_no = tf.force_no and tu.unit_id = mb.place\n"
			+ "where mb.board_id = ?1", nativeQuery = true)
	public String getBoardPlacebyBoardId(String boardId);
	   
}
