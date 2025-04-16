package nic.ame.app.master.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nic.ame.app.master.service.ImageCaptureDirService;

@Controller
public class FileSystemController {

	@Autowired
	private ImageCaptureDirService captureDirService;
	
	
	//========================capture image and save ====================================//
	   @RequestMapping(path = "/image-upload-ama",method = RequestMethod.POST)
    public String getToCapturePage(String ameId,String candidateForcePersonalId) {
		   System.out.println("AMEID:-"+ameId+"Candidate id"+candidateForcePersonalId);
		   
	    return"medical-sub-ordinate/capture-image";
	    }
	   
	   
	   

	   @RequestMapping(path = "/save-image-local" ,method = RequestMethod.POST)
    public ResponseEntity<?> saveCaptureImage(Model model,@RequestBody String data){
  	System.out.println(data);
  	 byte[] byteArrray = data.getBytes();
 	 int year = Calendar.getInstance().get(Calendar.YEAR);
  	 String forceName="BSF";
  	 String path=captureDirService.DirectoryCreated(forceName,String.valueOf(year));
  	 
  	try {
		FileCopyUtils.copy(byteArrray, new File(path+File.separator+"ama1234daw.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	return new ResponseEntity<>(HttpStatus.ACCEPTED).ok("success");
}
	   
}
