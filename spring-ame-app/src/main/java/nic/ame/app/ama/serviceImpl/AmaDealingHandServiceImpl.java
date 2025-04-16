package nic.ame.app.ama.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.ama.service.AmaDealingHandService;
import nic.ame.app.master.model.TTDealingHand;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.TTDealingHandRepo;
import nic.ame.constant.CommonConstant;

@Service
@RequiredArgsConstructor
public class AmaDealingHandServiceImpl implements AmaDealingHandService{
	
	@Autowired  
	private  TTDealingHandRepo dealingHandRepo;  
	
	
	@Autowired
	private AmeApplicationFlowStatusRepo AmeApplicationFlowStatusRepo;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private RankRepo rankRepo;

	@Override
	public List<DealingHandDto> getDealingHandByBoardId(String boardId) {
		
		List<Object> objectList=dealingHandRepo.getDealingHandByBoardId(boardId);
		List<DealingHandDto> dealingHandDtos=new ArrayList<>();
		if (objectList.isEmpty()) {
			return dealingHandDtos;
		}
		Iterator<Object> iterator = objectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			DealingHandDto dealingHandDto=new DealingHandDto();
			if (obj[0] != null) {
				dealingHandDto.setIrlaNo(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				dealingHandDto.setName(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				dealingHandDto.setDesignation(
						rankRepo.findById(Integer.parseInt(
						String.valueOf(obj[2]).trim())).get().getRankFullName())	;
				
			}
			if (obj[3] != null) {
				dealingHandDto.setBoardId(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				if( Integer.parseInt(String.valueOf(obj[4]).trim())==4) {
					dealingHandDto.setStatus("Active");
					}
				else {
					dealingHandDto.setStatus("InActive");
				}
			}
		
		dealingHandDtos.add(dealingHandDto);
	}
		
		return  dealingHandDtos;
	}

	@Override
	public int countDeclarationCompletePendingForApproval(String boardId) {
		int count ;
		count =AmeApplicationFlowStatusRepo.countDeclarationCompletePendingForApprovals(boardId,
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED)),
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_COMPLETED)));
		return count;
	}

	@Override
	public int countPendingForUploadAmeAndCheckUpList(String boardId) {
		
		int count;
		count=AmeApplicationFlowStatusRepo.countPendingForUploadAmeAndCheckUpLists(boardId, 
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED)),
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_COMPLETED)), 
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DATA_CHECK_COMPLETE)));
		return count;
	}

	@Override
	public List<DealingHandDto>listOfDeclarationCompletePendingForApproval(String boardId) {
		List<Object> objectList =AmeApplicationFlowStatusRepo.countDeclarationCompletePendingForApprovalsList(boardId,
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED)),
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_COMPLETED)));

		List<DealingHandDto> dealingHandDtos=new ArrayList<>();
		if (objectList.isEmpty()) {
			return dealingHandDtos;
		}
		Iterator<Object> iterator = objectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			DealingHandDto dealingHandDto=new DealingHandDto();
			if (obj[0] != null) {
				dealingHandDto.setAmeId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				dealingHandDto.setBoardId(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				dealingHandDto.setForcePersonalId(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				dealingHandDto.setIrlaNo(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				dealingHandDto.setName(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				dealingHandDto.setDeclarationDate((Date) obj[5]);
			}
		
		
		dealingHandDtos.add(dealingHandDto);
	}
		
		return  dealingHandDtos;
	}


	@Override
	public List<DealingHandDto> listOfPendingForUploadAmeAndCheckUpList(String boardId) {
		List<Object> objectList =AmeApplicationFlowStatusRepo.countPendingForUploadAmeAndCheckUpListsList(boardId,
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PROVIDED)), 
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_COMPLETED)),
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DATA_CHECK_COMPLETE)));


		List<DealingHandDto> dealingHandDtos=new ArrayList<>();
		if (objectList.isEmpty()) {
			return dealingHandDtos;
		}
		Iterator<Object> iterator = objectList.iterator();

		while (iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			DealingHandDto dealingHandDto=new DealingHandDto();
			if (obj[0] != null) {
				dealingHandDto.setAmeId(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				dealingHandDto.setBoardId(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				dealingHandDto.setForcePersonalId(String.valueOf(obj[2]).trim());
			}
			if (obj[3] != null) {
				dealingHandDto.setIrlaNo(String.valueOf(obj[3]).trim());
			}
			if (obj[4] != null) {
				dealingHandDto.setName(String.valueOf(obj[4]).trim());
			}
			if (obj[5] != null) {
				dealingHandDto.setDeclarationDate((Date) obj[5]);
			}
		
		dealingHandDtos.add(dealingHandDto);
	}
		
		return  dealingHandDtos;
	}

	@Override
	public boolean getExistingDealingHandByBoardIdAndForcePersonalId(String boardId, String forcePersonalId) {
		 Optional<TTDealingHand> optional=dealingHandRepo.getExistingDealingHandByBoardIdAndForcePersonalId(boardId,forcePersonalId);
		 if(!optional.isEmpty()) 
			 return true;
		 else 
             return false;
	}
	

}
