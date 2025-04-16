package nic.ame.app.master.service;

import java.util.Optional;

import nic.ame.app.master.dto.ForcePersonnelDto;

public interface ForcePersonnelService {
	Optional<ForcePersonnelDto> getForcePersonnelDetailsByForcePersonnelId(String forcePersonalId);
	Optional<ForcePersonnelDto> getForcePersonnelDetailsByIrlaNo(String irlaNo);

	
	
}
