package nic.ame.app.master.medical.serviceImpl;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.board.member.repository.AmeMasterStatusRepo;
import nic.ame.app.master.dto.AmeFinalDetailDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeFinalDetailService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeApprovalProcess;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.AmeStatusCode;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.go.AmeFinalReportBoardMemberDetailsGo;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeApprovalProcessService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.IncreaseT12Weeks;
import nic.ame.master.util.RandomKeyGenerator9Digit;

@Service
@EnableTransactionManagement
public class AmeFinalDetailServiceImpl  implements AmeFinalDetailService{
	
	private static Logger logger= LoggerFactory.getLogger(AmeFinalDetailServiceImpl.class);


	@Autowired
	private AmeMasterStatusRepo ameMasterStatusRepo;
	
	@Autowired
	private AmeDeclarationRepository AmeDeclarationRepository;
	
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private FinalCategoryRemarkTempRepo finalCategoryRemarkTempRepo;
	
	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	@Autowired
	private InvestigationMasterService investigationMasterService;
	
	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	
	
	
	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired 
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;
	
	@Autowired
	private AmeApprovalProcessService ameApprovalProcessService;
	
	
	String boardId=null,boardmemberForcePersonnelId=null;
	
	
	@Transactional
	@Override
	public boolean saveAmeFinalDetail(AmeFinalDetailDto ameFinalDetailDto,List<MedicalBoardMemberDto> 
	                         medicalBoardMemberDtos,int categoryCode12,int categoryCode24) {
		
		
		
		List<AmeFinalReportBoardMemberDetails> ameFinalReportBoardMemberDetails=new ArrayList<>();
		
	  
		
		
		AmeFinalReportDetails ameFinalReportDetails=new AmeFinalReportDetails();
		ameFinalReportDetails.setAmeId(ameFinalDetailDto.getAmeId());
		ameFinalReportDetails.setAmeYear(ameFinalDetailDto.getAmeYear());
		ameFinalReportDetails.setCandidateforcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
		ameFinalReportDetails.setClientIpAddress(ameFinalDetailDto.getIpClientAddress());
		ameFinalReportDetails.setFinalCategoryAwarded(ameFinalDetailDto.getAmeFinalCategoryAwarded());
		ameFinalReportDetails.setCreatedOn(ameFinalDetailDto.getCreatedOn());
		ameFinalReportDetails.setRemark(ameFinalDetailDto.getRemark());
		ameFinalReportDetails.setStatus(1);
		//MedicalBoard  medicalBoardToSave=medicalBoardRepo.findByBoardId(ameFinalDetailDto.getBoardId());
		
		ameFinalReportDetails.setBoardId(ameFinalDetailDto.getBoardId());
		
		for (MedicalBoardMemberDto medicalBoardMemberDto : medicalBoardMemberDtos) {
			AmeFinalReportBoardMemberDetails boardMemberDetails=new AmeFinalReportBoardMemberDetails();
			boardMemberDetails.setBoardId(medicalBoardMemberDto.getBoardId());
			boardId=medicalBoardMemberDto.getBoardId();
			boardmemberForcePersonnelId=medicalBoardMemberDto.getForcePersonalId();
			boardMemberDetails.setBoardMemberForcePersonalId(medicalBoardMemberDto.getForcePersonalId());
			boardMemberDetails.setBoardMemberIrlaNo(medicalBoardMemberDto.getForceId());
			boardMemberDetails.setRoleName(medicalBoardMemberDto.getRoleName());
			boardMemberDetails.setBoardId(ameFinalDetailDto.getBoardId());
			ameFinalReportBoardMemberDetails.add(boardMemberDetails);
		}
		ameFinalReportDetails.setAmeFinalReportBoardMemberDetails(ameFinalReportBoardMemberDetails);
		
		AmeFinalReportDetails  ameFinalReportDetailsSavedObject=ameFinalReportDetailsRepository.save(ameFinalReportDetails);
		
		if(ameFinalReportDetailsSavedObject!=null) {
			
			 Optional<AmeMasterStatus> optional=ameMasterStatusRepo.findByAmeId(ameFinalDetailDto.getAmeId().trim());		
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     
			 if(!optional.isEmpty()) {
				 ameMasterStatus=optional.get();
				 ameMasterStatus.setStatus("1");
				 
				 if(ameMasterStatusRepo.save(ameMasterStatus)!=null) {
					 
				   Optional<AmeDeclarationIndividualModel> AmeDeclarationIndividualModelOptional=  AmeDeclarationRepository.findByAmeId(ameFinalDetailDto.getAmeId().trim());
				
				    if(!AmeDeclarationIndividualModelOptional.isEmpty()) {
				    	AmeDeclarationIndividualModel ameDeclarationIndividualModel=AmeDeclarationIndividualModelOptional.get();
				    	ameDeclarationIndividualModel.setAmeFinalStatus(1);
				    	AmeDeclarationRepository.save(ameDeclarationIndividualModel);
				    	if(finalCategoryRemarkTempRepo.findByAmeId(ameFinalDetailDto.getAmeId()).size()>0) {
				    	finalCategoryRemarkTempRepo.updateFinalCategoryRemarkStatus(CommonConstant.ACTIVE_FLAG_YES,ameFinalDetailDto.getAmeId());
				    	}
				    	
				    	
				    }
				       Optional<ForcePersonnel> forcePersonalOptional = forcePersonalRepository.findByForcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
				      if(!forcePersonalOptional.isEmpty()) {
				    	  
				    	
				    		  ForcePersonnel forcePersonal=forcePersonalOptional.get();
					    	  forcePersonal.setLastAmeShape(ameFinalDetailDto.getAmeFinalCategoryAwarded());
					    	  forcePersonal.setLastAmeDate(Calendar.getInstance().getTime());
					    	  forcePersonal.setAmePlace(medicalBoardRepo.getBoardPlacebyBoardId(boardId));
					    	  if(forcePersonalRepository.save(forcePersonal)!=null)
					    	  {
					    		  logger.info("ForcePersonal Table update with forcePersonal Id: "+ameFinalDetailDto.getCandidateForcePersonalId());		    		  
					    	  }
				    	    
				    	  
				    	
				    	  
				    	  if(categoryCode12==1) {
				    		  logger.info("Candidate given T-12 Time because of Category Down And Created on :"+Calendar.getInstance().getTime());
				    		  AmeReviewCandidatesList ameReviewCandidatesList=new AmeReviewCandidatesList();
				    		  ameReviewCandidatesList.setAmeId(ameFinalDetailDto.getAmeId());
				    		  ameReviewCandidatesList.setBoardId(boardId);
				    		  ameReviewCandidatesList.setCreatedBy(boardmemberForcePersonnelId);
				    		  AmeStatusCode ameStatusCode=new AmeStatusCode();
				    		  ameStatusCode.setCode(1);
				    		  ameReviewCandidatesList.setAmeStatusCode(ameStatusCode);
				    		  ameReviewCandidatesList.setCandidateForcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
				    		  ameReviewCandidatesList.setReviewCreatedOn(Calendar.getInstance().getTime());
				    		  ameReviewCandidatesList.setReviewEndDate(IncreaseT12Weeks.getT12Date());
				    		  ameReviewCandidatesList.setYear(medicalBoardRepo.getBoardYear(boardId));
				    		  ameReviewCandidatesList.setRescheduleFlag(true);
				    		  ameReviewCandidatesListRepository.save(ameReviewCandidatesList);
				    		  
				    		  logger.info("ameReviewCandidatesList Updated......!");
				    		   }
				    	  
				    	
				    	  
				      }
				 
				 }
						 
			 }
		            return true;
		}
		    else {
			       return false;
		}
		
		
		
	}




	@Override
	public List<AmeFinalDetailDto> listOfAmeStatusCompletedOrUnderReview(String boardId,int ameFinalStatus,int finalUploadFlag) {
	List<Object> objects=ameApplicationFlowStatusRepo.listOfCompletedAmeOrUnderReviewData(boardId,ameFinalStatus,finalUploadFlag);
		

	Iterator<Object> itr = objects.iterator();
	
	List<AmeFinalDetailDto> ameFinalDetailDtosList=new ArrayList<>();
	
	
	while(itr.hasNext())
	{   
		Object[] obj = (Object[]) itr.next();
      AmeFinalDetailDto ameFinalDetailDto=new AmeFinalDetailDto();
      
		if(obj[0]!=null) {
			ameFinalDetailDto.setName(String.valueOf(obj[0]).trim());
		}
		if(obj[1]!=null) {
			String rankOrDesignation = forcePersonalRepository.getRankAndDesignationById(Integer.parseInt(String.valueOf(obj[1]).trim()));
			ameFinalDetailDto.setCandidateRank(rankOrDesignation);
		}
		if(obj[2]!=null) {
			ameFinalDetailDto.setCandidateIrlaNo(String.valueOf(obj[2]).trim());
		}
		if(obj[3]!=null) {
			ameFinalDetailDto.setAmeId(String.valueOf(obj[3]).trim());
		}
		if(obj[4]!=null) {
			ameFinalDetailDto.setBoardId(String.valueOf(obj[4]).trim());
		}
		if(obj[5]!=null) {
			
			if(Integer.parseInt(String.valueOf(obj[5]).trim())==1)
			   ameFinalDetailDto.setStatusValue("Completed");
			else if(Integer.parseInt(String.valueOf(obj[5]).trim())==2)
		      ameFinalDetailDto.setStatusValue("UnderReview");

		}
		if(obj[6]!=null) {
			ameFinalDetailDto.setCandidateForcePersonalId(String.valueOf(obj[6]).trim());
		}
		if(obj[7]!=null) {
			ameFinalDetailDto.setAmeYear(String.valueOf(obj[7]).trim());
		}
		if(obj[8]!=null) {
			ameFinalDetailDto.setDeclarationDate( (Date) (obj[8]));
		}
		
		ameFinalDetailDtosList.add(ameFinalDetailDto);
	}
	return ameFinalDetailDtosList;
	

}




	@Override
	public AmeFinalDetailDto ameFinalDeclarationMap(String candidateForcePersonalId, String ameId) {
        
		Optional<AmeDeclarationIndividualDetails> optional = ameDeclarationIndividualDetailsRepo.findByAmeId(ameId);
		Optional<AmeDeclarationIndividualModel> optional2 = ameDeclarationRepository
				.findByForcePersonalIdData(candidateForcePersonalId,ameId);
		AmeDeclarationIndividualDetails details = new AmeDeclarationIndividualDetails();
		AmeDeclarationIndividualModel individualModel = new AmeDeclarationIndividualModel();
		Optional<AmeAppointmentDetails> appointmentDetails = ameAssessmentServicePart_2.ameAppointmentDetails(ameId);
		Date appointmentDate = null;
		String date = null;
			
		String amePlace = ameApplicationFlowStatusRepo.getAmePlaceByBoardIdAndAmeId(ameId.trim());
		Map<String, Object> resultMap = new HashMap<>();

		if (!appointmentDetails.isEmpty()) {
			appointmentDate = appointmentDetails.get().getAppointmentDate();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			date = simpleDateFormat.format(appointmentDate);
		}

		
		AmeFinalDetailDto ameFinalDetailDto=new AmeFinalDetailDto();
		individualModel = optional2.get();
		details = optional.get();

		ameFinalDetailDto.setAmeDeclarationIndividualDetails(details);
		ameFinalDetailDto.setAmeDeclarationIndividualModel(individualModel);
		ameFinalDetailDto.setAmePlace(amePlace);
		ameFinalDetailDto.setDate(date);
		
		return ameFinalDetailDto;
	}




	@Override
	public AmeFinalDetailDto ameFinalFilledReportDisplay(String candidateForcePersonalId, String ameId) {
		investigationMasterService.findAllInvestigationReportByAmeId(ameId);
		InvestigationFinalReportDto investigationFinalReportDto= investigationMasterService.findAllInvestigationReportByAmeId(ameId);
		
		MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
		AmeMasterStatus ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
		Optional<ForcePersonnelDto> forcePersonal=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonalId);
		examDtoRequest=ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
		
		AmeFinalDetailDto ameFinalDetailDto=new AmeFinalDetailDto();
		ameFinalDetailDto.setExamDtoRequest(examDtoRequest);
		ameFinalDetailDto.setAmeMasterStatus(ameMasterStatus);
		ameFinalDetailDto.setForcePersonnelDto(forcePersonal.get());
		ameFinalDetailDto.setInvestigationFinalReportDto(investigationFinalReportDto);
		
		return ameFinalDetailDto;
	}




	@Override
	public Optional<AmeFinalReportDetails> findByAmeId(String ameId) {
		
	    Optional<AmeFinalReportDetails> optional=ameFinalReportDetailsRepository.findByAmeId(ameId);
		if(optional.isEmpty())
			return Optional.empty();
		else
			return optional;
	}




	@Override
	@Transactional
	public boolean saveAmeFinalDetailGO(AmeFinalDetailDto ameFinalDetailDto,
			List<MedicalBoardMemberDto> medicalBoardMemberDtos, int categoryCode12, int categoryDownCode24,
			String rCode,String loggedInUserForcepersonnelId) {

		String senderForcePersonnelId = null,receiverRoleCode=null,senderRoleCode = null;
		AmeFinalReportDetailsGo  ameFinalReportDetailsSavedObject;
		
		Optional<AmeFinalReportDetailsGo> ameFinalReportDetailsGoOptional = ameFinalReportDetailsGoRepository.findByAmeIdAndStatus(ameFinalDetailDto.getAmeId(),1);
		if(ameFinalReportDetailsGoOptional.isPresent()) {
			AmeFinalReportDetailsGo	ameFinalReportDetailsGo=ameFinalReportDetailsGoOptional.get();
			
			ameFinalReportDetailsGo.setAmeYear(ameFinalDetailDto.getAmeYear());
            ameFinalReportDetailsGo.setClientIpAddress(ameFinalDetailDto.getIpClientAddress());
			ameFinalReportDetailsGo.setFinalCategoryAwarded(ameFinalDetailDto.getAmeFinalCategoryAwarded());
			ameFinalReportDetailsGo.setCreatedOn(ameFinalDetailDto.getCreatedOn());
			ameFinalReportDetailsGo.setRemark(ameFinalDetailDto.getRemark());
			ameFinalReportDetailsGo.setStatus(1);
            ameFinalReportDetailsSavedObject = ameFinalReportDetailsGoRepository.save(ameFinalReportDetailsGo);

			//MedicalBoard  medicalBoardToSave=medicalBoardRepo.findByBoardId(ameFinalDetailDto.getBoardId());
			
			
			/*
			 * String uniqueKeyString=RandomKeyGenerator9Digit.generateRandomKey(4);
			 * ameFinalReportDetailsGo.setUniqueAmeId(ameFinalDetailDto.getAmeId()+"-"+
			 * uniqueKeyString);
			 * 
			 * List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetails=new
			 * ArrayList<>();
			 * 
			 * for (MedicalBoardMemberDto medicalBoardMemberDto : medicalBoardMemberDtos) {
			 * AmeFinalReportBoardMemberDetailsGo boardMemberDetails=new
			 * AmeFinalReportBoardMemberDetailsGo();
			 * boardMemberDetails.setBoardId(medicalBoardMemberDto.getBoardId());
			 * boardId=medicalBoardMemberDto.getBoardId();
			 * boardmemberForcePersonnelId=medicalBoardMemberDto.getForcePersonalId();
			 * boardMemberDetails.setBoardMemberForcePersonalId(medicalBoardMemberDto.
			 * getForcePersonalId());
			 * boardMemberDetails.setBoardMemberIrlaNo(medicalBoardMemberDto.getForceId());
			 * boardMemberDetails.setRoleName(medicalBoardMemberDto.getRoleName());
			 * boardMemberDetails.setBoardId(ameFinalDetailDto.getBoardId());
			 * ameFinalReportBoardMemberDetails.add(boardMemberDetails); }
			 * ameFinalReportDetailsGo.setAmeFinalReportBoardMemberDetailsGo(
			 * ameFinalReportBoardMemberDetails);
			 */  
			
			 }
		
		else {
			
			AmeFinalReportDetailsGo ameFinalReportDetailsGo=new AmeFinalReportDetailsGo();
			
			ameFinalReportDetailsGo.setAmeId(ameFinalDetailDto.getAmeId());
			ameFinalReportDetailsGo.setAmeYear(ameFinalDetailDto.getAmeYear());
			ameFinalReportDetailsGo.setCandidateforcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
			ameFinalReportDetailsGo.setClientIpAddress(ameFinalDetailDto.getIpClientAddress());
			ameFinalReportDetailsGo.setFinalCategoryAwarded(ameFinalDetailDto.getAmeFinalCategoryAwarded());
			ameFinalReportDetailsGo.setCreatedOn(ameFinalDetailDto.getCreatedOn());
			ameFinalReportDetailsGo.setRemark(ameFinalDetailDto.getRemark());
			ameFinalReportDetailsGo.setStatus(1);
			//MedicalBoard  medicalBoardToSave=medicalBoardRepo.findByBoardId(ameFinalDetailDto.getBoardId());
			
			ameFinalReportDetailsGo.setBoardId(ameFinalDetailDto.getBoardId());
			String uniqueKeyString=RandomKeyGenerator9Digit.generateRandomKey(4);
			ameFinalReportDetailsGo.setUniqueAmeId(ameFinalDetailDto.getAmeId()+"-"+uniqueKeyString);
			
			List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetails=new ArrayList<>();
			
			for (MedicalBoardMemberDto medicalBoardMemberDto : medicalBoardMemberDtos) {
				AmeFinalReportBoardMemberDetailsGo boardMemberDetails=new AmeFinalReportBoardMemberDetailsGo();
				boardMemberDetails.setBoardId(medicalBoardMemberDto.getBoardId());
				boardId=medicalBoardMemberDto.getBoardId();
				boardmemberForcePersonnelId=medicalBoardMemberDto.getForcePersonalId();
				boardMemberDetails.setBoardMemberForcePersonalId(medicalBoardMemberDto.getForcePersonalId());
				boardMemberDetails.setBoardMemberIrlaNo(medicalBoardMemberDto.getForceId());
				boardMemberDetails.setRoleName(medicalBoardMemberDto.getRoleName());
				boardMemberDetails.setBoardId(ameFinalDetailDto.getBoardId());
				ameFinalReportBoardMemberDetails.add(boardMemberDetails);
			}
			ameFinalReportDetailsGo.setAmeFinalReportBoardMemberDetailsGo(ameFinalReportBoardMemberDetails);
			
	        ameFinalReportDetailsSavedObject=ameFinalReportDetailsGoRepository.save(ameFinalReportDetailsGo);

			
		}
		
	
		if(ameFinalReportDetailsSavedObject!=null) {
			
			 Optional<AmeMasterStatus> optional=ameMasterStatusRepo.findByAmeId(ameFinalDetailDto.getAmeId().trim());		
		     AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		     Optional<AmeApplicationFlowStatus>ameApplicationFlowStatusOptional= ameApplicationFlowStatusRepo.findByAmeId(ameFinalDetailDto.getAmeId().trim());
		     AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
		     if(!optional.isEmpty()&&!ameApplicationFlowStatusOptional.isEmpty()) {
		    	 ameApplicationFlowStatus=ameApplicationFlowStatusOptional.get();
		    	 ameMasterStatus=optional.get();
				 ameMasterStatus.setStatus("1");
				 
				 if(ameMasterStatusRepo.save(ameMasterStatus)!=null) {
					 
				   Optional<AmeDeclarationIndividualModel> AmeDeclarationIndividualModelOptional=  AmeDeclarationRepository.findByAmeId(ameFinalDetailDto.getAmeId().trim());
				
				    if(!AmeDeclarationIndividualModelOptional.isEmpty()) {
				    	AmeDeclarationIndividualModel ameDeclarationIndividualModel=AmeDeclarationIndividualModelOptional.get();
				    	ameDeclarationIndividualModel.setAmeFinalStatus(1);
				    	AmeDeclarationRepository.save(ameDeclarationIndividualModel);
				    	
				    	if(finalCategoryRemarkTempRepo.findByAmeId(ameFinalDetailDto.getAmeId()).size()>0) {
				    	
				    		finalCategoryRemarkTempRepo.updateFinalCategoryRemarkStatus(CommonConstant.ACTIVE_FLAG_YES,ameFinalDetailDto.getAmeId());
				    	}
				    	
				    	
				    }
				    
	//updating application process flow 
				    String remark;
				    int statusCode=0,ameFinalStatus = 0,esignStatusCode=0;
				    senderRoleCode=rCode;
				    senderForcePersonnelId=loggedInUserForcepersonnelId;
				    if(rCode.equals("3")||rCode.equals("4")) {
				    	// BM1 And BM2 
				    	remark="Approved and Saved By Board Member";
				    	statusCode=CommonConstant.AME_FILLED_AND_SUBMITTED_BY_BOARD_MEMBER_WITHOUT_ESIGN;
				    	ameFinalStatus=CommonConstant.AME_COMPLETED_AND_CLOSED_BY_BM;
				    	//esignStatusCode=CommonConstant.E_SIGN_COMPLETED_BY_BM;
				    	receiverRoleCode="2";
				    }else {
				    	remark="Approved and Saved By PO";
				    	statusCode=CommonConstant.AME_FILLED_AND_SUBMITTED_BY_PO_WITHOUT_ESIGN;
				    	ameFinalStatus=CommonConstant.AME_COMPLETED_AND_CLOSED_BY_BM_AND_PO;
				       // esignStatusCode=CommonConstant.E_SIGN_COMPLETED_BY_BM_AND_PO;
				    	receiverRoleCode="2";
				    }
				    ameApplicationFlowStatus.setAmeFinalStatus(ameFinalStatus);
				   // ameApplicationFlowStatus.setAmeFinalEsignByBoardMember(esignStatusCode);
				    ameApplicationFlowStatus.setRowIsValid(true);
				    ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
				    
		AmeApprovalProcess ameApprovalProcess= ameApprovalProcessService.updateStatus
				(senderForcePersonnelId,receiverRoleCode,remark,statusCode,ameFinalDetailDto.getAmeId(),senderRoleCode, boardId);
		if(ameApprovalProcess==null)
		{
			logger.info(">>>>>>>>>>>>>>  ameApprovalProcess Not Saved................!");
		}
				    
				       Optional<ForcePersonnel> forcePersonalOptional = forcePersonalRepository.findByForcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
				      if(!forcePersonalOptional.isEmpty()) {
				    	  ForcePersonnel forcePersonal=forcePersonalOptional.get();
					       forcePersonal.setLastAmeShape(ameFinalDetailDto.getAmeFinalCategoryAwarded());
					    	  forcePersonal.setLastAmeDate(Calendar.getInstance().getTime());
					    	  forcePersonal.setAmePlace(medicalBoardRepo.getBoardPlacebyBoardId(boardId));
					    	  if(forcePersonalRepository.save(forcePersonal)!=null)
					    	  {
					    		  logger.info("ForcePersonal Table update with forcePersonal Id: "+ameFinalDetailDto.getCandidateForcePersonalId());		    		  
					    	  }
				    	    
				    	  
				    	
				    	  
				    	  if(categoryCode12==1) {
				    		  logger.info("Candidate given T-12 Time because of Category Down And Created on :"+Calendar.getInstance().getTime());
				    		  AmeReviewCandidatesList ameReviewCandidatesList=new AmeReviewCandidatesList();
				    		  ameReviewCandidatesList.setAmeId(ameFinalDetailDto.getAmeId());
				    		  ameReviewCandidatesList.setBoardId(boardId);
				    		  ameReviewCandidatesList.setCreatedBy(boardmemberForcePersonnelId);
				    		  AmeStatusCode ameStatusCode=new AmeStatusCode();
				    		  ameStatusCode.setCode(1);
				    		  ameReviewCandidatesList.setAmeStatusCode(ameStatusCode);
				    		  ameReviewCandidatesList.setCandidateForcePersonalId(ameFinalDetailDto.getCandidateForcePersonalId());
				    		  ameReviewCandidatesList.setReviewCreatedOn(Calendar.getInstance().getTime());
				    		  ameReviewCandidatesList.setReviewEndDate(IncreaseT12Weeks.getT12Date());
				    		  ameReviewCandidatesList.setYear(medicalBoardRepo.getBoardYear(boardId));
				    		  ameReviewCandidatesList.setRescheduleFlag(true);
				    		  ameReviewCandidatesListRepository.save(ameReviewCandidatesList);
				    		  
				    		  logger.info("ameReviewCandidatesList Updated......!");
				    		  
				    		  
				    		   }
				    	  
				    	
				    	  
				      }
				 
				      
				      
				 }
						 
			 }
		            return true;
		}
		return false;
	}




	
	
	
	
	
	
}