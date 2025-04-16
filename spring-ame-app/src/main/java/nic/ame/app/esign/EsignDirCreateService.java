package nic.ame.app.esign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.UserDateUtil;


@Service
public class EsignDirCreateService {
	
	

	
	
	Logger logger=LoggerFactory.getLogger(EsignDirCreateService.class);
	
	
	public String createDirectory(String pathCode,ForcePersonnelDto CandidateforcePersonalDetails,ForcePersonnelDto forcePersonalDetailsForSignee,Map<String, Object> outputStreamForGeneratedPdf,String ameId) {
		logger.info("Creating path ............esign......!");
		
		            String fileName = null;
		            String directoryName=null;
		                // Get the current year for the directory structure
					int year = UserDateUtil.getYear(new Date());
					if(!pathCode.equalsIgnoreCase("d")) {
						logger.info("Creating path ............Final Report Ame......!");
						 directoryName= CommonConstant.AME_FINAL_REPORT_PATH + File.separator + year;

					}
					else {
						logger.info("Creating path ............Declaration Ame......!");
						 directoryName= CommonConstant.FILE_PATH + File.separator + year;

					}

					String candidateName=CandidateforcePersonalDetails.getName();
					candidateName=candidateName.replace(" ", "");
					// Create the directory for the generated PDF
					String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName, String.valueOf(year));
					path = path + File.separator + CandidateforcePersonalDetails.getForceId()+"-"+ameId;
					String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path, CandidateforcePersonalDetails.getForceId()+"-"+ameId);
					String savePath=null;
					// Check if the directory exists
					if (Files.isDirectory(Paths.get(pathForAmeId))) {
						savePath = pathForAmeId;
					     fileName = (String) outputStreamForGeneratedPdf.get("fileName");
					    logger.info("Path Exists..............!");

					    try {
					        // Get the OutputStream from the generated PDF
					        OutputStream pdfOutputStream = (OutputStream) outputStreamForGeneratedPdf.get("fileOutputStreamObject");

					        // Create a ByteArrayOutputStream to hold the PDF data
					        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					        
					        // Write the content from the pdfOutputStream to byteArrayOutputStream
					        // If pdfOutputStream is a ByteArrayOutputStream, you can directly use it
					        if (pdfOutputStream instanceof ByteArrayOutputStream) {
					            byteArrayOutputStream = (ByteArrayOutputStream) pdfOutputStream;
					        } else {
					            // If it's a different type of OutputStream, read from it (assuming you can)
					            // This assumes pdfOutputStream has been populated correctly.
					            // You need a way to read the data into byteArrayOutputStream
					            // For demonstration, assuming you can retrieve an InputStream or read it differently.
					            byte[] buffer = new byte[1024];
					            int bytesRead;
					            try (InputStream pdfInputStream = new ByteArrayInputStream(((ByteArrayOutputStream) pdfOutputStream).toByteArray())) {
					                while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
					                    byteArrayOutputStream.write(buffer, 0, bytesRead);
					                }
					            }
					        }

					        // Convert ByteArrayOutputStream to byte[]
					        byte[] pdfBytes = byteArrayOutputStream.toByteArray();

					        // Write to a file
					        File file = new File(savePath, fileName);
					        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
					            fileOutputStream.write(pdfBytes);
					        }

					    } catch (IOException e) {
					        e.printStackTrace(); // Handle the exception appropriately
					    }
					}
					return savePath;


		
		
	}
	
	
	
	
}
