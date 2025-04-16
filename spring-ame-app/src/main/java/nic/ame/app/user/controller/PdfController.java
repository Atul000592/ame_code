package nic.ame.app.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itextpdf.text.DocumentException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.AmePdfFinalReportDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.go.AmeFinalReportBoardMemberDetailsGo;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportBoardMemberDetailsGoRepository;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.repository.AmeFinalReportBoardmemberDetailsRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.BloodSugarFRepository;
import nic.ame.app.master.repository.BloodSugarHbA1cRepository;
import nic.ame.app.master.repository.BloodSugarPPRepository;
import nic.ame.app.master.repository.BloodSugarRandomRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeFormDropDownService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.user.serviceImpl.PdfGeneratorService;

@Controller
public class PdfController {

	Logger logger = LoggerFactory.getLogger(PdfController.class);

	@Autowired
	private PdfGeneratorService pdfGeneratorService;

	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	@Autowired
	private InvestigationMasterService investigationMasterService;
	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	@Autowired
	private ForcePersonalService forcePersonalService;
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	@Autowired
	private LoginUserDetails loginUserDetails;
	@Autowired
	private MapUriToUserService mapUriToUserService;
	@Autowired
	private AmeFormDropDownService ameFormDropDownService;
	@Autowired
	private FinalCategoryRemarkTempRepo finalCategoryRemarkTempRepo;
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	@Autowired
	private RefForceService refForceService;
	@Autowired
	private AmeFinalReportBoardmemberDetailsRepository ameFinalReportBoardmemberDetailsRepository;
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	@Autowired
	private BloodSugarFRepository bloodSugarFRepository;
	@Autowired
	private BloodSugarHbA1cRepository bloodSugarHbA1cRepository;
	@Autowired
	private BloodSugarPPRepository bloodSugarPPRepository;
	@Autowired
	private BloodSugarRandomRepository bloodSugarRandomRepository;
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private AmeFinalReportBoardMemberDetailsGoRepository ameFinalReportBoardmemberDetailsGoRepository;

	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;
	@GetMapping("/generate-pdf")
    public void getpdf(@RequestParam("ameId") String ameId,Model model,
			@RequestParam("forcepersonalId") String forcePersonalId,
			HttpSession httpSession,HttpServletResponse response) throws DocumentException, IOException {
    	
    	
    	logger.info("ForcePersonnelId>>>>>>>pdf"+forcePersonalId+"ameId"+ameId);
    	
       	MedExamDtoRequest examDtoRequest = new MedExamDtoRequest();
        AmePdfFinalReportDto amePdfFinalReportDto= new AmePdfFinalReportDto();
		AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		
		
		
		AmeFinalReportDetails ameFinalReportDetails =new AmeFinalReportDetails();
		AmeFinalReportDetailsGo ameFinalReportDetailsGo =new AmeFinalReportDetailsGo();
		Optional<ForcePersonnelDto> boardMemberDetailsOptional = java.util.Optional.empty();
		
		
		String boardForcePersonnelId, boardId=null;
		ForcePersonnel boardMemberDetails = null;
		String loginInForcePersonnel=(String) httpSession.getAttribute("forcepersonalId");
        int rCodeMedical = (int) httpSession.getAttribute("rCodeMedical");
       
    	String finalCategoryAwarded = null,remark=null;
        
        if(rCodeMedical==3||rCodeMedical==4) {
        	String roleName = null;
        	if(rCodeMedical==3)
        		roleName="mb1";
        	if(rCodeMedical==4)
        		roleName="mb2";
        	Optional<AmeFinalReportDetailsGo> ameFinalReportDetailsOptional =ameFinalReportDetailsGoRepository.findByAmeId(ameId);
        	List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetailsGos =ameFinalReportBoardmemberDetailsGoRepository.
    				findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(ameFinalReportDetailsOptional.get().getBoardId(),roleName,loginInForcePersonnel);
        	for (AmeFinalReportBoardMemberDetailsGo ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetailsGos) {
    			if(ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
    			boardForcePersonnelId=ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
    			boardId=ameFinalReportBoardMemberDetails2.getBoardId();
    			boardMemberDetailsOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
    			boardMemberDetails = ameAssessmentServicePart_1_impl
    						.getforceForcePersonal(boardForcePersonnelId);

    			break;
    			}
    			
    			
    		}
        	if(ameFinalReportDetailsOptional.isPresent()) {
        		ameFinalReportDetailsGo=ameFinalReportDetailsOptional.get();
    			finalCategoryAwarded=ameFinalReportDetailsGo.getFinalCategoryAwarded();
    			remark=ameFinalReportDetailsGo.getRemark();
              }
        	
        	
    		
        
        }
        
        else  if(rCodeMedical==2) {
        	String roleName = "po";
        	
        	Optional<AmeFinalReportDetailsGo> ameFinalReportDetailsOptional =ameFinalReportDetailsGoRepository.findByAmeId(ameId);
        	List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetailsGos =ameFinalReportBoardmemberDetailsGoRepository.
    				findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(ameFinalReportDetailsOptional.get().getBoardId(),roleName,loginInForcePersonnel);
        	for (AmeFinalReportBoardMemberDetailsGo ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetailsGos) {
    			if(ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
    			boardForcePersonnelId=ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
    			boardId=ameFinalReportBoardMemberDetails2.getBoardId();
    			boardMemberDetailsOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
    			boardMemberDetails = ameAssessmentServicePart_1_impl
    						.getforceForcePersonal(boardForcePersonnelId);

    			break;
    			}
    			
    			
    		}
        	if(ameFinalReportDetailsOptional.isPresent()) {
        		ameFinalReportDetailsGo=ameFinalReportDetailsOptional.get();
    			finalCategoryAwarded=ameFinalReportDetailsGo.getFinalCategoryAwarded();
    			remark=ameFinalReportDetailsGo.getRemark();
              }
        	
        	
    		
        
        }
        
        
        
        
        else {
        	
        	Optional<AmeFinalReportDetails> ameFinalReportDetailsOptional =ameFinalReportDetailsRepository.findByAmeId(ameId);
        	List<AmeFinalReportBoardMemberDetails> ameFinalReportBoardMemberDetails =ameFinalReportBoardmemberDetailsRepository.
    				findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(ameFinalReportDetailsOptional.get().getBoardId(),"ama",loginInForcePersonnel);
      
        	for (AmeFinalReportBoardMemberDetails ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetails) {
    			if(ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
    			boardForcePersonnelId=ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
    			boardId=ameFinalReportBoardMemberDetails2.getBoardId();
    			boardMemberDetailsOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
    			boardMemberDetails = ameAssessmentServicePart_1_impl.getforceForcePersonal(boardForcePersonnelId);

    			break;
    			}
    			
    			
    		}

    		if(ameFinalReportDetailsOptional.isPresent()) {
    			ameFinalReportDetails=ameFinalReportDetailsOptional.get();
    			finalCategoryAwarded=ameFinalReportDetails.getFinalCategoryAwarded();
    			remark=ameFinalReportDetails.getRemark();
              }
    		
        }
		
		
		
		
	
	
		
		
	
		
		
		List<FinalCategoryRemarkTemp> finalCategoryRemarkList=finalCategoryRemarkTempRepo.findByAmeId(ameId);
        
		examDtoRequest = ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
		
		Map<String, Map<String, String>> investigationReportDtosList = investigationMasterService.findInvestigationReportByAmeId(ameId);

		investigationMasterService.findAllInvestigationReportByAmeId(ameId);
		
		InvestigationFinalReportDto investigationFinalReportDto = investigationMasterService.findAllInvestigationReportByAmeId(ameId);

		ForcePersonnelDto forcePersonalDetails = forcePersonalService.getForcePersonalDetails(forcePersonalId);
		
		ForcePersonnel forcePersonal=ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonalId);
    	logger.info("ForcePersonnelId 2>>>>>>>pdf"+forcePersonalId+"ameId"+ameId);

		String boardMemberUnitName=refForceService.getUnitNameByUnitId(boardMemberDetails.getForceNo(), boardMemberDetails.getUnit());
		
		String boardMemberForceName=refForceService.getForceNameByForceId(boardMemberDetails.getForceNo());
		
		MedicalBoard medicalBoardMemberDetails= medicalBoardRepo.getByBoardId(boardId);
		
		String medicalBoardMemberPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardMemberDetails.getBoardAtForceNo()),medicalBoardMemberDetails.getPlace());
		
		InvestigationDto investigationDto = new InvestigationDto();
		investigationDto = this.investigationMasterService.getReportView(ameId);
		List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps = new ArrayList<>();
		finalCategoryRemarkTemps = finalCategoryRemarkTempRepo.findByAmeId(ameId);

    	logger.info("ForcePersonnelId 3 >>>>>>>pdf"+forcePersonalId+"ameId"+ameId);
        Optional<BloodSugarF> bloodSugarF=	this.bloodSugarFRepository.findByAmeId(ameId);
        Optional<BloodSugarPP> bloodSugarpp=	this.bloodSugarPPRepository.findByAmeId(ameId);
        Optional<BloodSugarRandom> bloodSugarRandom=	this.bloodSugarRandomRepository.findByAmeId(ameId);
        Optional<BloodSugarHbA1c> bloodSugarHbA1c=	this.bloodSugarHbA1cRepository.findByAmeId(ameId);
    	

       
        
        
        amePdfFinalReportDto.setFinalCategoryAwarded(remark);
        amePdfFinalReportDto.setFinalCategoryAwarded(finalCategoryAwarded);
        amePdfFinalReportDto.setAmeFinalReportDetails(ameFinalReportDetails);
      //  amePdfFinalReportDto.setAmeFinalReportBoardMemberDetails(ameFinalReportBoardMemberDetails);
        amePdfFinalReportDto.setAmeMasterStatus(ameMasterStatus);
        amePdfFinalReportDto.setBoardMemberDetails(boardMemberDetails);
        amePdfFinalReportDto.setExamDtoRequest(examDtoRequest);
       
         amePdfFinalReportDto.setForcePersonalDetails(forcePersonalDetails);
        amePdfFinalReportDto.setInvestigationFinalReportDto(investigationFinalReportDto);
        amePdfFinalReportDto.setMedicalBoardMemberDetails(medicalBoardMemberDetails);
        amePdfFinalReportDto.setFinalCategoryRemarkList(finalCategoryRemarkList);
        amePdfFinalReportDto.setFinalCategoryRemarkTemps(finalCategoryRemarkTemps);
        amePdfFinalReportDto.setInvestigationDto(investigationDto);
        amePdfFinalReportDto.setBoardMemberUnitName(boardMemberUnitName);
        amePdfFinalReportDto.setBoardMemberForceName(boardMemberForceName);
        amePdfFinalReportDto.setMedicalBoardMemberPlace(medicalBoardMemberPlace);
        amePdfFinalReportDto.setForcePersonal(forcePersonal);
        amePdfFinalReportDto.setRemark(remark);	
        amePdfFinalReportDto.setBloodSugarF(bloodSugarF);
        amePdfFinalReportDto.setBloodSugarHbA1c(bloodSugarHbA1c);
        amePdfFinalReportDto.setBloodSugarPP(bloodSugarpp);
        amePdfFinalReportDto.setBloodSugarRandom(bloodSugarRandom);
    	logger.info("ForcePersonnelId 4>>>>>>>pdf"+forcePersonalId+"ameId"+ameId);


	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"AmeFinalReport-"
        +forcePersonalDetails.getForceId()+"-"+forcePersonalDetails.getName()+"-"
        +formatter.format(new Date())+".pdf\"");
        try(OutputStream outputStream = response.getOutputStream()) {
            pdfGeneratorService.generatePdf(outputStream,amePdfFinalReportDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
