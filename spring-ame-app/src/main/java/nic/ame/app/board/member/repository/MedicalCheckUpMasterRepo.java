package nic.ame.app.board.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.MedicalCheckUpMaster;

@Repository
public interface MedicalCheckUpMasterRepo  extends JpaRepository<MedicalCheckUpMaster, Integer>{

	@Query(nativeQuery = true,value = "SELECT * from tm_medical_check_up_master where test_code not in (select test_code from tt_prescribed_check_up where ame_id=?1)")
	List<MedicalCheckUpMaster>  MedicalCheckUpMasterUnAssign(String ameId);
}
