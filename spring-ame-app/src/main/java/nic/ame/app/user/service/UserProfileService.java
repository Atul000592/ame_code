package nic.ame.app.user.service;


import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;

public interface UserProfileService {
    
    public ForcePersonnelDto getLoginUserDetails(String forcePersonalId);
}
