package nic.ame.app.test.controller;


import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import nic.ame.app.board.member.repository.MedicalCheckUpMasterRepo;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.sms.SMSConfigurationConstantsAME;
import nic.ame.app.sms.SMSTemplateDto;
import nic.ame.app.sms.SMSTemplateService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.io.UrlResource;

import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class TestController1 {

	@Autowired 
	private MedicalCheckUpMasterRepo checkUpMasterRepo;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	@Autowired 
	private SMSTemplateService smsTemplateService;
	
	@GetMapping("/list-check-up")
	public String checkupList(Model model) {
		
		List<MedicalCheckUpMaster> checkUpMasteres=checkUpMasterRepo.findAll();
		model.addAttribute("checkUpMasteres", checkUpMasteres);
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList=new ArrayList<>();
		
		return"medical-sub-ordinate/check-up-list";
     }
	  
	
	@GetMapping("/test-multiple")
	public String testMultipleData() {
		return"test-template/multiple-checkbox";
	}   
	 
	@PostMapping("/SpringAjaxQuery")
	public ResponseEntity<?> SpringAjaxQuery(@RequestBody String  data) {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+data);
		return ResponseEntity.status(HttpStatus.OK).body("true");
	}
	
	
	
	@GetMapping("/upload-file-test")
	public String uploadFileTest() {
		return"test-template/file-upload-size-test";
	}
	
	@GetMapping("/test-sms")
	public String testSMS() {
		SMSTemplateDto dto=new SMSTemplateDto();
		dto.setIrlaNo("909090");
		dto.setName("Ajay");
		dto.setRank("ASI");
		dto.setCountryIsdCode("91");
		dto.setMobileNumber("9999253767");
		dto.setYear("2024");
		dto.setBoardUnitPlace("FHQ");
		smsTemplateService.individualMappedToBoardSMS(dto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_MAPPING_SMS);
		return "redirect:/dashboard-user";
	}
	
	

	@GetMapping("open-pdf-in-frame")
    @ResponseBody
    public Resource servePdf() throws Exception {
		String pdfPathValue="/Users/brijeshkumar/Desktop/FileUploadFolder/finalReport/2024/AME22024OR112988018/AME22024OR112988018-Ame FinalReport_01.pdf";

        Path pdfPath = Paths.get(pdfPathValue);  // Path to your PDF file
        return new UrlResource(pdfPath.toUri());
    }
	
	
}
