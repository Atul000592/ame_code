package nic.ame.app.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import nic.ame.app.master.model.FinalCategoryRemarkTemp;

public interface FinalCategoryRemarkTempRepo extends JpaRepository<FinalCategoryRemarkTemp, Integer> {

	List<FinalCategoryRemarkTemp> findByAmeId(String ameId);

	
	@Query(nativeQuery = true,value = "select * from final_category_remark_temp where ame_id=?1 and category_type=?2")
	Optional<FinalCategoryRemarkTemp> checkExistingCommentByCategory(String ameId, String categoryTypeValue);

	@Modifying
    @Query(nativeQuery = true,value = "update final_category_remark_temp set status=?1 where ame_id=?2")
	void updateFinalCategoryRemarkStatus(int status,String ameId);

}
