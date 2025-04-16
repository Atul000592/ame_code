package nic.ame.app.master.ref.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.ref.entity.RefGeneralExamination;


@Repository
public interface RefGeneralExaminationRepo  extends JpaRepository<RefGeneralExamination, Integer> {

}
