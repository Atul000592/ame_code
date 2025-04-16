package com.nic.in.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nic.in.modal.DesignationMaster;


@Repository
public interface DesignationMasterRepository extends JpaRepository<DesignationMaster, Long> {

}
