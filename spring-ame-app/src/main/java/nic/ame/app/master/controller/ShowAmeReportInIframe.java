package nic.ame.app.master.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.ama.service.SubordinateService_3;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.model.go.AmeFinalReportFileDirGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.model.go.repository.AmeFinalReportFileDirGoRepository;
import nic.ame.app.master.repository.AmeFinalReportFileDirRepository;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.user.dto.AmeFinalResultCandidate;
import nic.ame.app.user.service.UserService;
import nic.ame.constant.CommonConstant;

@Controller
public class ShowAmeReportInIframe {
	
  
	Logger logger=LoggerFactory.getLogger(ShowAmeReportInIframe.class);
	@Autowired
	private AmeFinalReportFileDirRepository ameFinalReportFileDirRepository;
	
	@Autowired
	private AmeDeclarationFilesRepo ameDeclarationFilesRepo;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private SubordinateService_3 subordinateService_3;
	
	@Autowired
	private AmeFinalReportFileDirGoRepository ameFinalReportFileDirGoRepository;
	
	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping("/view-final-report-ame")
    public String viewPdf(@RequestParam("ameId") String ameId, Model model,HttpSession httpSession) {
    	
    	String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		 String filePath=null;
		 Optional<AmeFinalReportFileDir> ameFinalReportFileDirOptional =ameFinalReportFileDirRepository.findByAmeId(ameId);
			String fileName = null;
			if(!ameFinalReportFileDirOptional.isEmpty()) {
				if(ameFinalReportFileDirOptional.get().getFilePath()!=null) {
					
				File file=new File(ameFinalReportFileDirOptional.get().getFilePath().toString());
				if(file.exists()) {
					filePath=ameFinalReportFileDirOptional.get().getFilePath();
					fileName=ameFinalReportFileDirOptional.get().getFileName();
				}else {
					filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
				}
           }else {
    			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
    		}
				}
			try {
				filePath=URLEncoder.encode(filePath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// Add the file path as a model attribute
        model.addAttribute("filePath", filePath);
        model.addAttribute("fileName",fileName );
        return "medical-sub-ordinate/view/report/pdfView";
    }

    @CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping("/file")
    public void getPdf(@RequestParam String filePath, HttpServletResponse response) throws IOException {
        // Dynamically load the PDF based on the provided file path
    	
    try {
            // Read the file into a byte array
        	 String encodedFilePath = URLDecoder.decode(filePath, "UTF-8");
        	 
        	 logger.info("Final File download :"+encodedFilePath);
            Path path = Paths.get(encodedFilePath).toAbsolutePath();
            byte[] pdfBytes = Files.readAllBytes(path);

            // Set response headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + path.getFileName().toString());
            response.setContentLength(pdfBytes.length);

            // Write the PDF file to the output stream
            OutputStream out = response.getOutputStream();
            out.write(pdfBytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception (e.g., log or return an error response)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        
        
    }
	
	@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping("/view-declaration-pdf-ame")
    public String viewPdfDeclaration(@RequestParam("ameId") String ameId, Model model,HttpSession httpSession) {
    	
    	String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		 String filePath=null;
		 Optional<AmeFinalReportFileDir> ameFinalReportFileDirOptional =ameFinalReportFileDirRepository.findByAmeId(ameId);
		 AmeDeclarationFiles ameDeclarationFiles  = ameDeclarationFilesRepo.findFileNameByAmeId(ameId);
			String fileName = null;
			int rCodeMedical = (int) httpSession.getAttribute("rCodeMedical");
			if(ameDeclarationFiles!=null) {
				if(ameDeclarationFiles.getFilePath()!=null) {
					
				File file=new File(ameDeclarationFiles.getFilePath().toString());
				if(file.exists()) {
					filePath=ameDeclarationFiles.getFilePath();
					fileName=ameDeclarationFiles.getFileName();
				}else {
					filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
				}
           }else {
    			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
    		}
				}
			try {
				filePath=URLEncoder.encode(filePath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// Add the file path as a model attribute
        model.addAttribute("filePath", filePath);
        model.addAttribute("fileName",fileName );
        
        if(rCodeMedical==3||rCodeMedical==4) {
        	 return "medical-bm/view/report/pdfViewDeclaration";
        }
        
        return "medical-sub-ordinate/view/report/pdfViewDeclaration";
    }
	
	
	
	
	@GetMapping("view-investigation-report-pdf-file")
	public String viewInvestigationReportPdfFile(@RequestParam("ameId") String ameId,
			@RequestParam("testCode") String testCode,Model model,HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		String fileName = null,filePath=null;
		filePath=subordinateService_3.getInvestidationReport(ameId, testCode);
		if(filePath==null) 
			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
			
		try {
			filePath=URLEncoder.encode(filePath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	// Add the file path as a model attribute
    model.addAttribute("filePath", filePath);
    model.addAttribute("fileName",fileName );
	return "medical-sub-ordinate/view/investigation-reports/pdf-view-investigation";
	
	}
	
	
	@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping("/view-final-report-ame-go")
    public String viewFinalReportAmeGo(@RequestParam("ameId") String ameId,
    		@RequestParam(name="uniqueAmeId",required = false) String uniqueAmeId 
    		, Model model,HttpSession httpSession) {
    	
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
	     if(uniqueAmeId==null) {
		List<AmeFinalReportFileDirGo> ameFinalReportFileDirList = ameFinalReportFileDirGoRepository.findByAmeId(ameId);
     
			if (ameFinalReportFileDirList.size() == 1) {

				Optional<AmeFinalReportFileDirGo> ameFinalReportFileDirOptional = ameFinalReportFileDirGoRepository
						.findByUniqueAmeId(ameFinalReportFileDirList.get(0).getUniqueAmeId());
				Map<String, String> response = getFilePathForAmeFinalResult(ameFinalReportFileDirOptional);

				model.addAttribute("filePath", response.get("filePath"));
				model.addAttribute("fileName", response.get("fileName"));
				return "medical-bm/view/report/pdfView";

			}
			else {
				String candidateForcePersonnelId=ameFinalReportDetailsGoRepository.findForcePersonnelIdByAmeId(ameId);
				 List<AmeFinalResultCandidate> ameFinalResultCandidates =new ArrayList<>(); 
				 if(userService.getAmeResultForCandidateForcePersonalId(candidateForcePersonnelId).size()>0) {
					 ameFinalResultCandidates = userService.getAmeResultForCandidateForcePersonalId(candidateForcePersonnelId); 
				 }
				 
				 model.addAttribute("ameFinalResultCandidates", ameFinalResultCandidates);
				
				 model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonnelId));
				 ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonnelId);
				 model.addAttribute("candidateForcePersonalId", candidateForcePersonnelId);
				 
		    
				model.addAttribute("candidateDetails", forcePersonal);
				Date dob = forcePersonal.getDob();
				int age = ageCalculatorService.getAge(dob);
				Date doj = forcePersonal.getJoiningDate();
				String dateOfJoining = ageCalculatorService.calculateYearsMothsandDays(doj);
				model.addAttribute("age", age);
				model.addAttribute("doj", dateOfJoining);
				
				return "medical-bm/report/candidate-ame-results";
			}
			}
	     else {
				Optional<AmeFinalReportFileDirGo> ameFinalReportFileDirOptional = ameFinalReportFileDirGoRepository
						.findByUniqueAmeId(uniqueAmeId);
				Map<String, String> response = getFilePathForAmeFinalResult(ameFinalReportFileDirOptional);
                   
				model.addAttribute("filePath", response.get("filePath"));
				model.addAttribute("fileName", response.get("fileName"));
				return "medical-bm/view/report/pdfView";
			}
	}
	
	
	public static Map<String,String> getFilePathForAmeFinalResult(Optional<AmeFinalReportFileDirGo>ameFinalReportFileDirOptional) {
	    String fileName = null,filePath = null;
		
		if(!ameFinalReportFileDirOptional.isEmpty()) {
	    	
	    	
					if(ameFinalReportFileDirOptional.get().getFilePath()!=null) {
						
					File file=new File(ameFinalReportFileDirOptional.get().getFilePath().toString());
					if(file.exists()) {
						filePath=ameFinalReportFileDirOptional.get().getFilePath();
						fileName=ameFinalReportFileDirOptional.get().getFileName();
					}else {
						filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
					}
	           }else {
	    			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
	    		}
					}
				try {
					filePath=URLEncoder.encode(filePath, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// Add the file path as a model attribute
	   
	           Map<String , String> response=new HashMap();
	           response.put("filePath", filePath);
	           response.put("fileName", fileName);
	           
	           return response;
	}
	
}
