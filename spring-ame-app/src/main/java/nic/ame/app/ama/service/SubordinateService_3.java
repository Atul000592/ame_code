package nic.ame.app.ama.service;

import java.util.List;

import nic.ame.app.master.dto.ForcePersonnelDto;

public interface SubordinateService_3 {

	
	public List<ForcePersonnelDto> getDealingHandAssignmentList(String unit,int forceNo);
	public String getInvestidationReport(String ameId,String testCode);
	
}
