package nic.ame.app.po.service;

import java.util.List;

import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.po.dto.PoServiceDto;

public interface POService {

	
	public List<ForcePersonnel> getDeclarationDetailsForPO(String boardId);
	public int getDeclarationCount(String boardId);
	public List<PoServiceDto> getDeclarationPersonalListToPo(String boardId);
}
