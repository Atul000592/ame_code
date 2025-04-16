package nic.ame.app.board.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.RefAppendagesType;

@Repository
public interface RefAppendagesTypeRepo extends JpaRepository<RefAppendagesType, Integer> {

	@Query(nativeQuery = true,value = "select * from ref_appendages_type rat  where rat.type ='l'")
	List<RefAppendagesType> findByTypeLower();
	
	@Query(nativeQuery = true,value = "select * from ref_appendages_type rat  where rat.type ='u'")
	List<RefAppendagesType> findByTypeUpper();

	List<RefAppendagesType> findByType(String type);
	
}
