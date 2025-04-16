package nic.ame.app.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.RefPsychologicalType;

@Repository
public interface RefPsychologicalTypeRepo extends JpaRepository<RefPsychologicalType, Integer> {

}
