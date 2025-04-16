package nic.ame.app.board.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.RefDropDownRange;

@Repository
public interface RefDropDownRangeRepo extends JpaRepository<RefDropDownRange, Integer> {

	@Query(name = "select * from ref_drop_down_range ddr where ddr.name=?1", nativeQuery = true)
     Optional<RefDropDownRange> findByName(String name);
}
