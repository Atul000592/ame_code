package nic.ame.app.master.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.repository.AmeFinalReportFileDirRepository;
import nic.ame.constant.CommonConstant;

@Controller
public class ReportControllerAME {

	Logger logger=LoggerFactory.getLogger(ReportControllerAME.class);
	
	@Autowired
	private AmeDeclarationFilesRepo ameDeclarationFilesRepo;
	
	@Autowired
	private AmeFinalReportFileDirRepository ameFinalReportFileDirRepository;
	
	
	
//===================get the path of saved declaration file===============================================//
	
@PostMapping("get-file-data-path-declaration")
public  ResponseEntity<?> getFilePathOfDeclaration(
		@RequestParam("candidateForcePersonnelId") String candidateForcePersonnelId,
		@RequestParam("ameId") String ameId){
	   
	
	String filePath=null;
	   AmeDeclarationFiles ameDeclarationFiles  = ameDeclarationFilesRepo.findFileNameByAmeId(ameId);
	if(ameDeclarationFiles!=null) {
		if(ameDeclarationFiles.getFilePath()!=null)
			filePath=ameDeclarationFiles.getFilePath();
		else
			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
	}
	File file=new File(filePath);
	
	if (file == null || !file.exists()) {
		 file=new File(CommonConstant.DEFAULT_FILE_PATH_NAME);
		
	}
    Map<String,String> responseMap=new HashMap<>();
    responseMap.put("filePath", filePath);
    responseMap.put("fileName", ameDeclarationFiles.getFileName());
    
	return new  ResponseEntity<>(responseMap,HttpStatus.ACCEPTED);
}

//===================get-file-data-path-ame-final-report===============================================//

@PostMapping("get-file-data-path-ame-final-report")
public  ResponseEntity<?> getFilePathOfAmeFinalReport(
		@RequestParam("candidateForcePersonnelId") String candidateForcePersonnelId,
		@RequestParam("ameId") String ameId){
	
	  Optional<AmeFinalReportFileDir> ameFinalReportFileDirOptional =ameFinalReportFileDirRepository.findByAmeId(ameId);
	  String filePath=null;
		if(!ameFinalReportFileDirOptional.isEmpty()) {
			if(ameFinalReportFileDirOptional.get().getFilePath()!=null)
				filePath=ameFinalReportFileDirOptional.get().getFilePath();
			else
				filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
		}
		File file=new File(filePath);
		
		if (file == null || !file.exists()) {
			 file=new File(CommonConstant.DEFAULT_FILE_PATH_NAME);
			
		}
    Map<String,String> responseMap=new HashMap<>();
    responseMap.put("filePath", filePath);
    responseMap.put("fileName", ameFinalReportFileDirOptional.get().getFileName());
	return new  ResponseEntity<>(responseMap,HttpStatus.ACCEPTED);
}


//=========================ViewDocuments======================================//

@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL,"https://ame.capf.gov.in"},allowCredentials = "true") 
@GetMapping("/ViewDocuments")
public ResponseEntity<FileSystemResource> viewDocument(@RequestParam("documentPath") String encodedPath) {
    
    try {
        // Decode the URL-encoded document path
        String decodedPath = URLDecoder.decode(encodedPath, "UTF-8");

        // Create a File object using the decoded path
        File file = new File(decodedPath);

        // Check if the file exists
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Return 404 if file does not exist
        }

        // Prepare the HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Return the file in the response body with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new FileSystemResource(file));

    } catch (UnsupportedEncodingException e) {
        // Handle any encoding errors
        return ResponseEntity.badRequest().build();
    }
}

//=========================view-ame-final-pdf======================================//

@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL,"https://ame.capf.gov.in"},allowCredentials = "true") 
@GetMapping("/view-ame-final-pdf")
public ResponseEntity<FileSystemResource> viewAmeFinalPdf(@RequestParam("documentPath") String encodedPath) {
    
    try {
        // Decode the URL-encoded document path
        String decodedPath = URLDecoder.decode(encodedPath, "UTF-8");

        // Create a File object using the decoded path
        File file = new File(decodedPath);

        // Check if the file exists
        if (!file.exists()) {
            return ResponseEntity.notFound().build(); // Return 404 if file does not exist
        }

        // Prepare the HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // Return the file in the response body with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new FileSystemResource(file));

    } catch (UnsupportedEncodingException e) {
        // Handle any encoding errors
        return ResponseEntity.badRequest().build();
    }
}
	
	
}
