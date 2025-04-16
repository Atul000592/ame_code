package nic.ame.app.dealinghand.service;

import java.util.List;

import nic.ame.app.ama.dto.DealingHandDto;


public interface DealingHandService {

	
	public List<DealingHandDto> getDealingHandDetailsByForcePersonalId(String forcePersonalId,int status);
	
}
