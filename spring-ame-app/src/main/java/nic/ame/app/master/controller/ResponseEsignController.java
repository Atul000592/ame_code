package nic.ame.app.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/response-digial-sign")
public class ResponseEsignController {

	
	Logger logger =LoggerFactory.getLogger(ResponseEsignController.class);
	
	  @GetMapping("/view")
	    public String viewPdf( Model model,HttpServletRequest httpServletRequest) {
	        // Add the file path as a model attribute
	        String filePath=null,fileName=null;
	    	
            HttpSession httpSession=httpServletRequest.getSession(false);
			
				@SuppressWarnings("unchecked")
				Map<String,Object> inputFlashMap = (Map<String, Object>) RequestContextUtils.getInputFlashMap(httpServletRequest);
			    if (inputFlashMap != null) {
			    	

			    	httpSession =  (HttpSession) inputFlashMap.get("session");
			    	filePath=(String) inputFlashMap.get("filePath");
			    	fileName=(String) inputFlashMap.get("fileName");

			    	logger.info("Restored old session  after esign :");
			    	logger.info("filePath"+filePath);
			    	

			    	
			    }
				filePath = (String) httpSession.getAttribute("tempfilePath");
                 logger.info("File Path from session:>>>>after sign :"+filePath);
			
	    	model.addAttribute("filePath", filePath);
	        model.addAttribute("fileName",fileName);
	        
	        return "medical-sub-ordinate/NGO/view-esign-doc";
	    }

	   
	  
	  
	  
	  
	  @GetMapping("/file")
	  public ResponseEntity<Resource> getFile(@RequestParam String filePath, HttpServletResponse response) throws IOException {
	      // Create a File object from the file path
	      File pdfFile = new File(filePath);

	      // Check if the file exists
	      if (!pdfFile.exists()) {
	          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	          response.getWriter().write("File not found");
	          return ResponseEntity.notFound().build();
	      }

	      // Set response headers
	      response.setContentType("application/pdf");
	      response.setHeader("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"");

	      // Stream the file content
	      try (FileInputStream fis = new FileInputStream(pdfFile);
	           ServletOutputStream outputStream = response.getOutputStream()) {

	          byte[] buffer = new byte[1024];
	          int bytesRead;

	          // Read and write the file content in chunks
	          while ((bytesRead = fis.read(buffer)) != -1) {
	              outputStream.write(buffer, 0, bytesRead);
	          }
	          outputStream.flush();

	          return ResponseEntity.ok().build(); // Return OK response after sending the file
	      } catch (IOException e) {
	          // Handle exceptions
	          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	          response.getWriter().write("Error reading or sending the file: " + e.getMessage());
	          return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(null);
	      }
	  }


	    
}
