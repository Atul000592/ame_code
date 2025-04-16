package nic.ame.app.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.InvestigationReportFileUpload;

@Repository
public interface InvestigationReportFileUploadRepo extends JpaRepository<InvestigationReportFileUpload,Integer> {

}
