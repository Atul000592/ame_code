package nic.ame.app.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.model.ExaminationApplicationFlowHistory;

@Repository
public interface ExaminationApplicationFlowHistoryRepo extends JpaRepository<ExaminationApplicationFlowHistory, Integer>{

}
