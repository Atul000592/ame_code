package nic.ame.app.master.serviceImpl;

import java.util.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.dto.IndividuaUnitMappedToControllingAmeAppointment;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.IndividualUnitMappedToControllingRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.AmeAppointmentService;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;

@Service
public class AmeAppointmentServiceImpl implements AmeAppointmentService {
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private IndividualUnitMappedToControllingRepository individualUnitMappedToControllingRepository;
	
	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;
	
	

	@Override
	public List<AmeDeclarationIndividualModel> getAppointmentPendingList(String forcePersonalId, String RoleName) {
		
		return null;
	}

	@Override
	public List<AmeDeclarationIndividualModel> getAppointmentProvidedList(String forcePersonalId, String RoleName) {
		// TODO Auto-generated method stub
		return null;
	}
//----------------------------------------------------------------------------------------------------------------------------//
/*	
	
	@Override
	public List<AmeDeclarationIndividualModel> getAmeAppointmentCompletedDetails(String boardId) {
	List<AmeDeclarationIndividualModel>	appointmentCompletedList=ameDeclarationRepository.AmeApplicationUnderProcesList(boardId);
		return appointmentCompletedList;
	}
*/
	
	@Override
	public List<AmeDeclarationIndividualDto> getAmeAppointmentCompletedDetails(String boardId) {
	List<AmeDeclarationIndividualDto>	appointmentCompletedList=new ArrayList();
	List<Object>   AmeDeclarationIndividualDtoList = ameDeclarationRepository.AmeApplicationUnderProcesList(boardId);
	
	
	Iterator<Object> itr=AmeDeclarationIndividualDtoList.iterator();
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
		
		/*if(obj[13]!=null) {
			ameDeclarationIndividualDtoItem.setsNumber(Integer.parseInt(String.valueOf(obj[13])));
			
		}*/
		
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
		
		
		appointmentCompletedList.add(ameDeclarationIndividualDtoItem);
	//System.out.println(ameDeclarationIndividualDtoItem);
	}
	
		return appointmentCompletedList;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------//	
	
	@Override
	public List<IndividuaUnitMappedToControllingAmeAppointment> findAllWithCompletedAppointment(String controllingOfficer) {
		
		List<String> individuaUnitMappedToControllingAmeAppointmentObjectList = individualUnitMappedToControllingRepository.findAllWithCompletedAppointment(controllingOfficer);
		
		List<IndividuaUnitMappedToControllingAmeAppointment> IndividuaUnitMappedToControllingAmeAppointmentList= new ArrayList<>();
		int appointmentCode=1;
	  
	   for (String  forcePersonnelId: individuaUnitMappedToControllingAmeAppointmentObjectList) {
		   IndividuaUnitMappedToControllingAmeAppointment IndividuaUnitMappedToControllingAmeAppointment=new IndividuaUnitMappedToControllingAmeAppointment();
		   
		  Optional<ForcePersonnel> forcePersonalOptional= forcePersonnelRepository.findByForcePersonalId(forcePersonnelId);
		   
		  Optional<TTAppointmentAme> tTAppointmentAmeopOptional=ttAppointmentAmeRepo.findByForcePersonalIdAndIsAppointmentValidAndDeclarationYear(forcePersonalOptional.get().getForcePersonalId(),appointmentCode,String.valueOf( Year.now()));
		   
		  if (!tTAppointmentAmeopOptional.isEmpty()) {
			  IndividuaUnitMappedToControllingAmeAppointment.setFromDate(tTAppointmentAmeopOptional.get().getFromDate());
			  IndividuaUnitMappedToControllingAmeAppointment.setToDate(tTAppointmentAmeopOptional.get().getToDate());
			  IndividuaUnitMappedToControllingAmeAppointment.setAppointmentStatus("C");
			  
			  if(tTAppointmentAmeopOptional.get().getDeclarationStatus()==1) {
				  IndividuaUnitMappedToControllingAmeAppointment.setDeclarationStatus("C");
				 Optional<AmeDeclarationIndividualModel> optional= ameDeclarationIndividualModelRepoUser.findByForcePersonaldAndYear
				  (Integer.parseInt(tTAppointmentAmeopOptional.get().getDeclarationYear()),forcePersonnelId);
				 IndividuaUnitMappedToControllingAmeAppointment.setDeclarationYear(optional.get().getDeclarationDate());
			  }
			  else {
				  IndividuaUnitMappedToControllingAmeAppointment.setDeclarationStatus("P");
					 IndividuaUnitMappedToControllingAmeAppointment.setDeclarationYear(null);


			  }
		   }
		  else {
			  IndividuaUnitMappedToControllingAmeAppointment.setFromDate(null);
			  IndividuaUnitMappedToControllingAmeAppointment.setToDate(null);
			  IndividuaUnitMappedToControllingAmeAppointment.setAppointmentStatus("P");


		  }
		  

		  
		 
		  IndividuaUnitMappedToControllingAmeAppointment.setCandidateName(forcePersonalOptional.get().getName());
		  IndividuaUnitMappedToControllingAmeAppointment.setCandidateIrlaNo(forcePersonalOptional.get().getForceId());
		 
		  IndividuaUnitMappedToControllingAmeAppointment.setUnitName(unitRepository.findById(forcePersonalOptional.get().getUnit()).get().getUnitFullName());

		  IndividuaUnitMappedToControllingAmeAppointment.setRank(rankRepo.findById(forcePersonalOptional.get().getRank()).get().getRankFullName());
		  IndividuaUnitMappedToControllingAmeAppointmentList.add(IndividuaUnitMappedToControllingAmeAppointment);
		   
	       }
	   
	   
		
			
		return IndividuaUnitMappedToControllingAmeAppointmentList;

		}
		
		
		
		
		
	

	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<IndividuaUnitMappedToControllingAmeAppointment> findAllWithPendingAppointment() {
		
		List<Object> individuaUnitMappedToControllingAmeAppointmentObjectList = individualUnitMappedToControllingRepository.findAllWithPendingAppointment();
		List<IndividuaUnitMappedToControllingAmeAppointment> IndividuaUnitMappedToControllingAmeAppointmentList= new ArrayList<>();
		Iterator<Object> iterator = individuaUnitMappedToControllingAmeAppointmentObjectList.iterator();
		
		while(iterator.hasNext()) {
			Object[] obj = (Object[]) iterator.next();
			IndividuaUnitMappedToControllingAmeAppointment IndividuaUnitMappedToControllingAmeAppointment=new IndividuaUnitMappedToControllingAmeAppointment();
			
			if (obj[0] != null) {
				IndividuaUnitMappedToControllingAmeAppointment.setCandidateForcePersonalId(String.valueOf(obj[0]).trim());
			}
			
			
			if (obj[1] != null) {
				IndividuaUnitMappedToControllingAmeAppointment.setCandidateIrlaNo(String.valueOf(obj[1]).trim());
			}
			if (obj[2] != null) {
				IndividuaUnitMappedToControllingAmeAppointment.setCandidateName(String.valueOf(obj[2]).trim());
			}
			
			if (obj[3] != null) {
				IndividuaUnitMappedToControllingAmeAppointment.setForceName(String.valueOf(obj[3]).trim());
				//IndividuaUnitMappedToControllingAmeAppointment.setForceName(IndividuaUnitMappedToControllingAmeAppointment.getForceNo());
			}
			if (obj[4] != null) {
				IndividuaUnitMappedToControllingAmeAppointment.setUnitName(String.valueOf(obj[4]).trim());
			}
			IndividuaUnitMappedToControllingAmeAppointmentList.add(IndividuaUnitMappedToControllingAmeAppointment);
			
			
		}
		
		
		
		
		
		return IndividuaUnitMappedToControllingAmeAppointmentList;
		
	}

	@Override
	public List<AmeDeclarationIndividualDto> getAmeApplicationStatusDetails(String boardId) {
		List<AmeDeclarationIndividualDto>	appointmentCompletedList=new ArrayList();
		List<Object>   AmeDeclarationIndividualDtoList = ameDeclarationRepository.AmeApplicationStatusForGo(boardId);
		
		
		Iterator<Object> itr=AmeDeclarationIndividualDtoList.iterator();
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
				ameDeclarationIndividualDtoItem.setRank(String.valueOf(obj[4]));
				ameDeclarationIndividualDtoItem.setDesignation(String.valueOf(obj[4]));
			}
		
			
			
			
			if(obj[5]!=null) {
				ameDeclarationIndividualDtoItem.setDeclarationDate((Date)obj[5]);
				
			}
			

			if(obj[6]!=null) {
				ameDeclarationIndividualDtoItem.setRemark(String.valueOf(obj[6]));
				
			}
			
			if(obj[7]!=null) {
				ameDeclarationIndividualDtoItem.setDeclaration_year(Long.parseLong(String.valueOf(obj[7])));
				
			}
			
			if(obj[8]!=null) {
				ameDeclarationIndividualDtoItem.setFinalUploadFlag(Integer.parseInt(String.valueOf(obj[8])));
				
			}
			
			if(obj[9]!=null) {
				ameDeclarationIndividualDtoItem.setAmeFinalStatus(Integer.parseInt(String.valueOf(obj[9])));
				
			}
			if(obj[10]!=null) {
				ameDeclarationIndividualDtoItem.setBoardMemberESignStatus(Integer.parseInt(String.valueOf(obj[10])));
				
			}
			if(obj[11]!=null) {
				ameDeclarationIndividualDtoItem.setPhysicalReportUploadBy(Integer.parseInt(String.valueOf(obj[11])));
				
			}
			
			
			appointmentCompletedList.add(ameDeclarationIndividualDtoItem);
		//System.out.println(ameDeclarationIndividualDtoItem);
		}
		
			return appointmentCompletedList;
	}

}
