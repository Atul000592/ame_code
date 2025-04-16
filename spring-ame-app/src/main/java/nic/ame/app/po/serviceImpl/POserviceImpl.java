package nic.ame.app.po.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.po.dto.PoServiceDto;
import nic.ame.app.po.service.POService;

@Service
public class POserviceImpl implements POService {

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	
	@Override
	public List<ForcePersonnel> getDeclarationDetailsForPO(String boardId) {
		List<ForcePersonnel> getForcePersonalList=forcePersonalRepository.getForcePersonalListForPO(boardId);
		return getForcePersonalList;
	}

	@Override
	public int getDeclarationCount(String boardId) {
		int count=forcePersonalRepository.getCountByBoardid(boardId);
		return count;
	}

	@Override
	public List<PoServiceDto> getDeclarationPersonalListToPo(String boardId) {
		List<Object> objects=forcePersonalRepository.getDeclarationForcePersonalListForPO(boardId.trim());
		
		List<PoServiceDto>poServiceDtosList=new ArrayList<>();
		Iterator<Object> iterator=objects.iterator();
		
		
		while(iterator.hasNext()) {
			
			Object[] obj=(Object[]) iterator.next();
			PoServiceDto poServiceDto=new PoServiceDto();
			if(obj[0]!=null) {
				poServiceDto.setAmeId(String.valueOf(obj[0]).trim());
			}
			if(obj[1]!=null) {
				poServiceDto.setForcePersonalId(String.valueOf(obj[1]).trim());
			}
			if(obj[2]!=null) {
				poServiceDto.setName(String.valueOf(obj[2]).trim());
			}
			if(obj[3]!=null) {
				poServiceDto.setIrlaNumber(String.valueOf(obj[3]).trim());
			}
			if(obj[4]!=null) {
				poServiceDto.setForceNumber(Integer.parseInt(String.valueOf(obj[4]).trim()));
			}
			if(obj[5]!=null) {
				poServiceDto.setUnitNumber(Integer.parseInt(String.valueOf(obj[5]).trim()));
			}
			if(obj[6]!=null) {
				poServiceDto.setBoardId(String.valueOf(obj[6]).trim());
			}
			
			poServiceDtosList.add(poServiceDto);
		}
		
		
		
		return poServiceDtosList;
	}

	

	
	
}
