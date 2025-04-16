package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.model.ReportingOfficer;

public interface ReportingOfficerRepository extends JpaRepository<ReportingOfficer, Integer> {

	Optional<ReportingOfficer> findByForceNoAndUnitAndStatus(Integer force_no, String unit, Integer activeFlagYes);

	Optional<ReportingOfficer> findByReportingUniqueId(String reportingOfficerUniqueId);

}
