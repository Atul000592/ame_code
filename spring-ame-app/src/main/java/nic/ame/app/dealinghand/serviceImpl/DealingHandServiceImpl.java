package nic.ame.app.dealinghand.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.dealinghand.service.DealingHandService;
import nic.ame.app.master.repository.TTDealingHandRepo;
import nic.ame.constant.CommonConstant;

@Service
public class DealingHandServiceImpl implements DealingHandService {

	@Autowired
	private TTDealingHandRepo  dealingHandRepo;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Override
	public List<DealingHandDto> getDealingHandDetailsByForcePersonalId(String forcePersonalId,int status) {

		List<Object> objects=dealingHandRepo.getDealingHandByForcePersonalId(forcePersonalId, status);
		
		List<DealingHandDto> dealingHandDtosList=new ArrayList<>();
	    Iterator<Object> iterator=objects.iterator();
		
	   while(iterator.hasNext()) {
		   Object [] obj;
		   obj=(Object[]) iterator.next();
		   
		   DealingHandDto dealingHandDto=new DealingHandDto();
		   if (obj[0] != null) {
				dealingHandDto.setBoardId(String.valueOf(obj[0]).trim());
			MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(String.valueOf(obj[0]).trim());
				dealingHandDto.setBoardYear(medicalBoard.getBoardYear());
				dealingHandDto.setCreatedOn(medicalBoard.getCreatedOn());
			}
		   if (obj[1] != null) {
				dealingHandDto.setForceName(String.valueOf(obj[1]).trim());
			}
		   if (obj[2] != null) {
				dealingHandDto.setUnitName(String.valueOf(obj[2]).trim());
			}
		   if (obj[3] != null) {
				if (Integer.parseInt(String.valueOf(obj[3]).trim())==CommonConstant.USER_ROLE_STATUS_ACTIVE) {
					dealingHandDto.setStatus("Active");
				}
				else {
					dealingHandDto.setStatus("InActive");
				}
			}
		   if (obj[4] != null) {
				dealingHandDto.setForcePersonalId(String.valueOf(obj[4]).trim());
			}
		   dealingHandDtosList.add(dealingHandDto);
		   
	   }
	
	
	
		return dealingHandDtosList;
	}





}
