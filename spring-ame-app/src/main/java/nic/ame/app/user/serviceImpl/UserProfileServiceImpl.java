package nic.ame.app.user.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.Rank;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.user.service.UserProfileService;


@Service
public class UserProfileServiceImpl implements UserProfileService{
    
@Autowired
private UnitRepository unitRepository;


@Autowired
private ForcePersonnelService forcePersonnelService ;


@Autowired
private RankRepo rankRepo;

@Autowired
private ForcePersonnelRepository forcePersonalRepository;
    	@Override
	public ForcePersonnelDto getLoginUserDetails(String forcePersonalId) {
		
		Optional<ForcePersonnelDto> optionalforcePersonal= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);		
        
            
		return optionalforcePersonal.get();
	}

}
