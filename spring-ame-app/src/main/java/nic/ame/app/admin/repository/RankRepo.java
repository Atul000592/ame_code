package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.Rank;

@Repository
public interface RankRepo extends JpaRepository<Rank,Integer>{
	
	@Query(nativeQuery = true,value = "select gazetted_nongazetted_flag from ref_rank where rank_name=:rankName")
	public String getGazettedNonGazettedFlag(String rankName);
    
	
	@Query(nativeQuery = true,value = "select * from tm_rank where force_no=?1")
	public List<Rank> findByForceId(int forceId);


	 List<Rank> findByForceNo(int force_id);

}
