package nic.ame.app.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.InvestigationReport;

@Repository
public interface InvestigationReportRepo extends JpaRepository<InvestigationReport, Integer> {

	@Query(
	  nativeQuery = true,
	  value = "select meir.ame_id,meir.test_code,ritm.test_name,meir.\"value\" from \r\n"
			+ "medical_examination_investigation_report meir inner join \r\n"
			+ "ref_investigation_test_master ritm on \r\n"
			+ " ritm.sub_test_code = meir.sub_test_code\r\n"
			+ "where  meir.ame_id=?1")
    List<Object> getInvestigationReportByAmeId(String ameId);

}
