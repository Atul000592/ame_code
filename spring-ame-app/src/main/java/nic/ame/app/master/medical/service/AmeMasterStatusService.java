package nic.ame.app.master.medical.service;

import nic.ame.app.master.medical.dto.AmeShapeDto;
import nic.ame.app.master.medical.model.AmeMasterStatus;

public interface AmeMasterStatusService {

	
  public AmeMasterStatus getAmeMasterStatus(String ameId); 
  
  public AmeShapeDto CheckForShapeStatusAndValues(String shape);
  
}
