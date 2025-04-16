package nic.ame.app.master.service;

import java.util.List;



import nic.ame.app.master.dto.AmeResultStatusDto;


public interface AmeResultStatusService {
 
 List<AmeResultStatusDto> getAmeResultStatus(String forcePersonnelId);

}
