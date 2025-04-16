package nic.ame.app.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.admin.model.Rank;

@Repository
public interface RankMasterRepository extends JpaRepository<Rank, Integer>{

	Rank findByRankCode(String rankCode);
    
	@Query(nativeQuery = true,value = "select * from tm_rank where force_no=?1")
	List<Rank> findByForceId(int forceId);

}
