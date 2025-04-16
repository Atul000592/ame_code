package nic.ame.app.user.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.user.dto.AmeFinalResultCandidate;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.app.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
    private AmeDeclarationIndividualModelRepoUser declarationIndividualModelRepoUser;
	
	@Autowired
	private AmeDeclarationFilesRepo ameDeclarationFilesRepo;
	
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;
	
	@Override
	public String getStatus(String ameId, String forcePersonalId) {
		
		return null; 
	}

	@Override
	public List<AmeDeclarationFiles> findByAmeId(String ameId) {
		
		List<AmeDeclarationFiles> getFileopOptional=ameDeclarationFilesRepo.findByAmeId(ameId);
		if(!getFileopOptional.isEmpty())
		{
			
			return getFileopOptional;
		}
		return null;
	}

	@Override
	public boolean findFileExist(String ameId) {
		
		List<AmeDeclarationFiles> optional=ameDeclarationFilesRepo.findByAmeIdFileExist(ameId);
		if(!optional.isEmpty()) {
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean getAmeDeclartionForCurrentYear(String forceId, long currentYear) {
		long count=declarationIndividualModelRepoUser.getCount(forceId, currentYear);
		if(count==0) {
			return true;
		}
		return false;
	}

	@Override
	public List<AmeDeclarationIndividualDto> showUserAllDeclaration(String forcePersonalId) {
	List<Object>allDeclarationObjectList=declarationIndividualModelRepoUser.findByForcePersonald(forcePersonalId);
	
	List<AmeDeclarationIndividualDto>	allDeclarationList=new ArrayList();
	
	
	Iterator<Object> itr=allDeclarationObjectList.iterator();
	while(itr.hasNext()) {
		AmeDeclarationIndividualDto ameDeclarationIndividualDtoItem= new AmeDeclarationIndividualDto();
		Object[] obj=(Object[])itr.next();
		if(obj[0]!=null) {
			ameDeclarationIndividualDtoItem.setAmeId(String.valueOf(obj[0]));
			
		}
		
		if(obj[1]!=null) {
			ameDeclarationIndividualDtoItem.setForceId(String.valueOf(obj[1]));
			
		}
		

		if(obj[2]!=null) {
			ameDeclarationIndividualDtoItem.setForcePersonalId(String.valueOf(obj[2]));
			
		}
		
		if(obj[3]!=null) {
			ameDeclarationIndividualDtoItem.setName(String.valueOf(obj[3]));
			
		}
		
		if(obj[4]!=null) {
			ameDeclarationIndividualDtoItem.setCurent_new_unit(String.valueOf(obj[4]));
			
		}
		
		if(obj[5]!=null) {
			ameDeclarationIndividualDtoItem.setPrevious_unit(String.valueOf(obj[5]));
			
		}
		
		if(obj[6]!=null) {
			ameDeclarationIndividualDtoItem.setDate_of_joining((Date)obj[6]);
			
		}
		
		if(obj[7]!=null) {
			ameDeclarationIndividualDtoItem.setDeclarationDate((Date)obj[7]);
			
		}
		
		if(obj[8]!=null) {
			ameDeclarationIndividualDtoItem.setFlag(String.valueOf(obj[8]));
			
		}
		
		if(obj[9]!=null) {
			ameDeclarationIndividualDtoItem.setLastAmeDate((Date)obj[9]);
			
		}
		
		if(obj[10]!=null) {
			ameDeclarationIndividualDtoItem.setLastAmePlace(String.valueOf(obj[10]));
			
		}
		
		if(obj[11]!=null) {
			ameDeclarationIndividualDtoItem.setPlace(String.valueOf(obj[11]));
			
		}
		
		if(obj[12]!=null) {
			ameDeclarationIndividualDtoItem.setRemark(String.valueOf(obj[12]));
			
		}
		/*
		 * if(obj[13]!=null) {
		 * ameDeclarationIndividualDtoItem.setsNumber(Integer.parseInt(String.valueOf(
		 * obj[13])));
		 * 
		 * }
		 */
		
		if(obj[13]!=null) {
			ameDeclarationIndividualDtoItem.setSelfCertifyAboveInfo(String.valueOf(obj[13]));
			
		}
		
		if(obj[14]!=null) {
			ameDeclarationIndividualDtoItem.setStatus(String.valueOf(obj[14]));
			
		}
		
		if(obj[15]!=null) {
			ameDeclarationIndividualDtoItem.setDeclaration_year(Long.parseLong(String.valueOf(obj[15])));
			
		}
		
		if(obj[16]!=null) {
			ameDeclarationIndividualDtoItem.setAmeFinalStatus(Integer.parseInt(String.valueOf(obj[16])));
			
		}
		

		if(obj[17]!=null) {
			ameDeclarationIndividualDtoItem.setAmeFinalStatus(Integer.parseInt(String.valueOf(obj[17])));
			
		}
		
		if(obj[18]!=null) {
			ameDeclarationIndividualDtoItem.setFinalUploadFlag(Integer.parseInt(String.valueOf(obj[18])));
			
		}
		if(obj[19]!=null) {
			ameDeclarationIndividualDtoItem.setSubRankcode(Integer.parseInt(String.valueOf(obj[19])));
			
		}
		
		if(obj[20]!=null) {
			ameDeclarationIndividualDtoItem.setRank(String.valueOf(obj[20]));
			ameDeclarationIndividualDtoItem.setDesignation(String.valueOf(obj[20]));
		}
		
		
		allDeclarationList.add(ameDeclarationIndividualDtoItem);
	System.out.println(ameDeclarationIndividualDtoItem);
	}
	
		return allDeclarationList;
	
	
	
		
		//  if(allDeclarationList.size()!=0) { return allDeclarationList; }
		 
		//return null;
	}

	@Override
	public List<AmeFinalResultCandidate> getAmeResultForCandidateForcePersonalId(String forcePersonalId) {
		
		List<Object> getdto=ameFinalReportDetailsRepository.findCandidateResultByForcePersonalId(forcePersonalId);
		List<AmeFinalResultCandidate> AmeFinalResultCandidateDto = new ArrayList<>();
		
		if(getdto.isEmpty()) {
			return AmeFinalResultCandidateDto;
		}
       	Iterator<Object> iterator=getdto.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AmeFinalResultCandidate AmeFinalResultCandidate=new AmeFinalResultCandidate();
			
			if (obj[0] != null) {
				AmeFinalResultCandidate.setAmeId(String.valueOf(obj[0]));
				}
			if (obj[1] != null) {
				AmeFinalResultCandidate.setShapeCategoryAwarded(String.valueOf(obj[1]));
				}
			if (obj[2] != null) {
				AmeFinalResultCandidate.setBoardId(String.valueOf(obj[2]));
				AmeFinalResultCandidate.setAmeYear(medicalBoardRepo.getBoardYear(String.valueOf(obj[2])));
				AmeFinalResultCandidate.setPlace(medicalBoardRepo.getBoardPlacebyBoardId(String.valueOf(obj[2])));
				}
			if (obj[3] != null) {
				AmeFinalResultCandidate.setPath(String.valueOf(obj[3]));
				}
			if (obj[4] != null) {
				//AmeFinalResultCandidate.setAmeYear(String.valueOf(obj[4]));
				}
		
			
			
			
			
			AmeFinalResultCandidateDto.add(AmeFinalResultCandidate);
       	}
		return AmeFinalResultCandidateDto;
	}

	@Override
	public List<AmeFinalResultCandidate> getAmeResultForSameYearCandidateForcePersonalId(String ameId) {
		
		List<Object> getdto=ameFinalReportDetailsGoRepository.findCandidateResultByAmeId(ameId);
		List<AmeFinalResultCandidate> AmeFinalResultCandidateDto = new ArrayList<>();
		
		if(getdto.isEmpty()) {
			return AmeFinalResultCandidateDto;
		}
       	Iterator<Object> iterator=getdto.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AmeFinalResultCandidate AmeFinalResultCandidate=new AmeFinalResultCandidate();
			
			if (obj[0] != null) {
				AmeFinalResultCandidate.setAmeId(String.valueOf(obj[0]));
				}
			if (obj[1] != null) {
				AmeFinalResultCandidate.setShapeCategoryAwarded(String.valueOf(obj[1]));
				}
			if (obj[2] != null) {
				AmeFinalResultCandidate.setBoardId(String.valueOf(obj[2]));
				AmeFinalResultCandidate.setAmeYear(medicalBoardRepo.getBoardYear(String.valueOf(obj[2])));
				AmeFinalResultCandidate.setPlace(medicalBoardRepo.getBoardPlacebyBoardId(String.valueOf(obj[2])));
				}
			if (obj[3] != null) {
				AmeFinalResultCandidate.setPath(String.valueOf(obj[3]));
				}
			if (obj[4] != null) {
				//AmeFinalResultCandidate.setAmeYear(String.valueOf(obj[4]));
				}
		
			
			
			
			
			AmeFinalResultCandidateDto.add(AmeFinalResultCandidate);
       	}
		return AmeFinalResultCandidateDto;
	}

	@Override
	public List<AmeFinalResultCandidate> getAmeResultForCandidateForcePersonalIdGo(String forcePersonalId) {
		
			List<Object> getdto=ameFinalReportDetailsGoRepository.findCandidateResultByForcePersonalId(forcePersonalId);
			List<AmeFinalResultCandidate> AmeFinalResultCandidateDto = new ArrayList<>();
			
			if(getdto.isEmpty()) {
				return AmeFinalResultCandidateDto;
			}
	       	Iterator<Object> iterator=getdto.iterator();
	       
			while(iterator.hasNext()) {
	       		Object[] obj=(Object[]) iterator.next();
	       		AmeFinalResultCandidate AmeFinalResultCandidate=new AmeFinalResultCandidate();
				
				if (obj[0] != null) {
					AmeFinalResultCandidate.setAmeId(String.valueOf(obj[0]));
					}
				if (obj[1] != null) {
					AmeFinalResultCandidate.setShapeCategoryAwarded(String.valueOf(obj[1]));
					}
				if (obj[2] != null) {
					AmeFinalResultCandidate.setBoardId(String.valueOf(obj[2]));
					AmeFinalResultCandidate.setAmeYear(medicalBoardRepo.getBoardYear(String.valueOf(obj[2])));
					AmeFinalResultCandidate.setPlace(medicalBoardRepo.getBoardPlacebyBoardId(String.valueOf(obj[2])));
					}
				if (obj[3] != null) {
					AmeFinalResultCandidate.setPath(String.valueOf(obj[3]));
					}
				if (obj[4] != null) {
					//AmeFinalResultCandidate.setAmeYear(String.valueOf(obj[4]));
					}
				if (obj[5] != null) {
					AmeFinalResultCandidate.setUniqueAmeId(String.valueOf(obj[5]));
					}
				
				
				
				
				AmeFinalResultCandidateDto.add(AmeFinalResultCandidate);
	       	}
			return AmeFinalResultCandidateDto;
		}
	

}
