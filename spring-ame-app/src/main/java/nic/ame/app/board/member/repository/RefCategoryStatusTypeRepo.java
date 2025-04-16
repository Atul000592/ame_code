package nic.ame.app.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.CategoryTypeMaster;

@Repository
public interface RefCategoryStatusTypeRepo extends JpaRepository<CategoryTypeMaster, Integer>{

}
