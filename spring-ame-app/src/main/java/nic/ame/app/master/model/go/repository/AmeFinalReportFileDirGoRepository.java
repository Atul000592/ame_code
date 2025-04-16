package nic.ame.app.master.model.go.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nic.ame.app.master.model.go.AmeFinalReportFileDirGo;

public interface AmeFinalReportFileDirGoRepository extends CrudRepository<AmeFinalReportFileDirGo, String>{

	Optional<AmeFinalReportFileDirGo>  findByUniqueAmeId(String uniqueAmeId);

	List<AmeFinalReportFileDirGo> findByAmeId(String ameId);
	

	
}
