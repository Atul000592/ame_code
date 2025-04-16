package nic.ame.app.master.model.go.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nic.ame.app.master.model.go.AmeFinalReportBoardMemberDetailsGo;

public interface AmeFinalReportBoardMemberDetailsGoRepository  extends CrudRepository<AmeFinalReportBoardMemberDetailsGo, Integer>{

	List<AmeFinalReportBoardMemberDetailsGo> findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(String boardId,
			String string, String loginInForcePersonnel);

}
