package nic.ame.app.board.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.CheckUpList;

@Repository
public interface CheckUpListRepo extends JpaRepository<CheckUpList, Integer> {

	@Query(nativeQuery = true,value = "select * from tt_prescribed_check_up where ame_id=?1")
	List<CheckUpList>findByAmeId(String ameId);
	
	
	@Query(nativeQuery = true,value = "select * from  tt_prescribed_check_up tpcu where tpcu.ame_id=?1 and  tpcu.upload_flag=?2")
	List<CheckUpList>findCheckUpListByAmeId(String ameId,int status);
	
	@Modifying
	@Query(nativeQuery = true,value = "update  tt_prescribed_check_up set upload_flag=1 where ame_id=?1 and test_code=?2")
	 void updateFileUploadStatus(String ameId,String testCode);

	@Query(nativeQuery = true,value = "select * from  tt_prescribed_check_up tpcu where tpcu.ame_id=?1 and  tpcu.upload_flag=1")
	List<CheckUpList> findCheckUpListByAmeIdUploadFlagTrue(String ameId);
	
	
	
}
