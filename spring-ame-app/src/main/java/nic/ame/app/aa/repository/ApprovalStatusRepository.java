package nic.ame.app.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.aa.model.ApprovalStatus;


@Repository
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Integer>{

}
