package nic.ame.app.ama.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.ama.service.SubordinateService_1;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.GetIpAddressClient;


@Service
public class SubordinateService_1_Impl implements SubordinateService_1{

	

	
	
  @Autowired
  private AmeDeclarationFilesRepo ameDeclarationFilesRepo;
	
	
	@Override
	public Optional<PsychologicalAssessmentAsLaidDown> assessmentAsLaidDown(String ameId) {
		
		return Optional.empty();
	}
	


	@Override
	public String uploadAmeDeclarationFile(MultipartFile file, String ameId,String candidateForcePersonalId,HttpServletRequest request) {
		
		AmeDeclarationFiles ameDeclarationFiles=new AmeDeclarationFiles();
		ameDeclarationFiles.setAmeId(ameId);
		ameDeclarationFiles.setFileName(file.getOriginalFilename());
		ameDeclarationFiles.setFileSize(file.getSize()/1024);
		ameDeclarationFiles.setFilePath(CommonConstant.FILE_PATH+File.separator+file.getOriginalFilename());
		ameDeclarationFiles.setFileType(file.getContentType());
		ameDeclarationFiles.setUploadOn(new Date());
		ameDeclarationFiles.setForcePersonalId(candidateForcePersonalId);
		ameDeclarationFiles.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
		
		System.out.println(">>>>>>>>>>>>>>>>>>>"+file.getOriginalFilename());
       
        String uploadPath = CommonConstant.FILE_PATH + File.separator +"";
       // File file2=new File(uploadPath);
        try {
			
			  FileCopyUtils.copy(file.getBytes(), new
			  File(uploadPath+File.separator+file.getOriginalFilename()));
			 
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ameDeclarationFilesRepo.save(ameDeclarationFiles);
        String fileName = file.getOriginalFilename();
      
       
	     return fileName;
	}



	@Override
	public boolean savePsychologicalAssessment(PsychologicalAssessmentAsLaidDown asLaidDown) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean updateAssessmentAsLaidDown(String ameId, PsychologicalAssessmentAsLaidDown asLaidDown) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public String uploadAmeDeclarationFileAfterEsign(File file, String ameId, String candidateForcePersonalId,
			HttpServletRequest request) {
		AmeDeclarationFiles ameDeclarationFiles=new AmeDeclarationFiles();
		ameDeclarationFiles.setAmeId(ameId);
		ameDeclarationFiles.setFileName(file.getName());
		//ameDeclarationFiles.setFileSize(file.getSize()/1024);
		ameDeclarationFiles.setFilePath(file.getAbsolutePath());
		ameDeclarationFiles.setFileType("PDF");
		ameDeclarationFiles.setUploadOn(new Date());
		ameDeclarationFiles.setForcePersonalId(candidateForcePersonalId);
		ameDeclarationFiles.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(request));
		
       if(file.exists()) {
       
        ameDeclarationFilesRepo.save(ameDeclarationFiles);
        String fileName = file.getName();
        return fileName;
       }else {
    	   return null;
       }
       
	    
	}
	

}
