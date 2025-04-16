package nic.ame.app.master.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.medical.model.AmeFinalReportFileDir;

public interface AmeFinalReportFileDirRepository extends JpaRepository<AmeFinalReportFileDir, String> {

	Optional<AmeFinalReportFileDir>  findByAmeId(String ameId);

}
