package nic.ame.app.master.service;

import java.util.List;
import java.util.Optional;


import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.user.dto.UserStatusDto;

public interface ForcePersonalService {

	
	Optional<ForcePersonnelDto> findByForcePersonalId(String forcePersonalId);
	Optional<UserStatusDto> findForcePersonalByBoardMemberId(String candidateForcePersonalId);
	List<ForcePersonnel> findNgoAmaListWithTransactionalId(String transactionalId);
	ForcePersonnelDto getForcePersonalDetails(String forcePersonalId);
	ForcePersonnelDto getForcePersonalDetailsObjectByForceId(String forcePersonalId);
	ForcePersonnel findByForceId(String forceId) throws Exception;
	
	}
