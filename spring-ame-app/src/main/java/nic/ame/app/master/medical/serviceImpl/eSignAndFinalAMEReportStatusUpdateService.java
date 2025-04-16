package nic.ame.app.master.medical.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;
import nic.ame.app.master.model.go.AmeFinalReportFileDirGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.model.go.repository.AmeFinalReportFileDirGoRepository;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.AmeFinalReportFileDirRepository;
import nic.ame.constant.CommonConstant;

@Service
@EnableTransactionManagement
public class eSignAndFinalAMEReportStatusUpdateService {
	
	@Autowired
	private   AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private   AmeDeclarationRepository ameDeclarationRepository;	
	
	@Autowired
	private AmeFinalReportFileDirRepository ameFinalReportFileDirRepository;
	
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	
	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;
	
	@Autowired
	private AmeFinalReportFileDirGoRepository ameFinalReportFileDirGoRepository;
	

	Logger logger=LoggerFactory.getLogger(eSignAndFinalAMEReportStatusUpdateService.class);
	
	
	String uniqueAmeId=null;
	/**
	 * @param AmeId
	 * @param candidateForcePersonnelId
	 * @param fileName
	 * @param filePath
	 */
	public  String updateAmeFinalResultAfterEsign(String AmeId,String candidateForcePersonnelId,String fileName,String filePath) {
		
		String AMEFinalCategoryAwarded=null;
		AmeFinalReportFileDir ameFinalReportFileDir = new AmeFinalReportFileDir();
	    AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
	    ameFinalReportFileDir.setAmeId(AmeId);
		ameApplicationFlowStatus=ameApplicationFlowStatusRepo.findByAmeId(AmeId).get();
		
		 Optional<AmeDeclarationIndividualModel> ameDeclarationIndividualModelOptional = ameDeclarationRepository.findByAmeId(ameFinalReportFileDir.getAmeId());
		 ameFinalReportFileDir.setFileName(fileName);
			ameFinalReportFileDir.setFileType("PDF");
			ameFinalReportFileDir.setFilePath(filePath);
			ameFinalReportFileDir.setFileSize(3);
			ameFinalReportFileDirRepository.save(ameFinalReportFileDir);
			
		if(ameDeclarationIndividualModelOptional.isPresent()){
			AmeDeclarationIndividualModel ameDeclarationIndividualModel=new AmeDeclarationIndividualModel();
			ameDeclarationIndividualModel=ameDeclarationIndividualModelOptional.get();
			ameDeclarationIndividualModel.setFinalUploadFlag(1);
			ameApplicationFlowStatus.setAmeFinalUplaod(1);
			ameApplicationFlowStatus.setAmeFinalStatus(1);
			ameApplicationFlowStatus.setAmeYear(ameDeclarationIndividualModelOptional.get().getDeclaration_year());
			if(ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus)!=null) {
				AMEFinalCategoryAwarded=ameFinalReportDetailsRepository.findByAmeId(AmeId).get().getFinalCategoryAwarded();
			}
			logger.info("AmeApplication Status Flow updated......"+ameApplicationFlowStatus);
		}
		
		 return AMEFinalCategoryAwarded;
		
		
	}
	
	
	public String getIndividualFinalAMECategory(String ameId,String candidateForcePersonnelId) {
		String finalCategory=null;
		
		Optional<String> finalCategoryOptional=ameFinalReportDetailsRepository.findByAmeIdAndCandidateforcePersonalId(ameId,candidateForcePersonnelId);
		
		
		if(!finalCategoryOptional.isEmpty())
			finalCategory=finalCategoryOptional.get();
		else
			finalCategory=null;
			
		logger.info("finalCategory>>>>>>>>"+finalCategory);
		
		return finalCategory;
		
	}


	public String updateAmeFinalResultAfterEsignBM(String ameId, String medicalCategory, String fileName,
			String filePath) {
		String AMEFinalCategoryAwarded=null;
		AmeFinalReportFileDirGo ameFinalReportFileDirGo = new AmeFinalReportFileDirGo();
	    AmeApplicationFlowStatus ameApplicationFlowStatus=ameApplicationFlowStatusRepo.findByAmeId(ameId).get();
	    List<AmeFinalReportDetailsGo> ameFinalReportDetailsGos=ameFinalReportDetailsGoRepository.findUniqueIdByAmeId(ameId);
	    for (AmeFinalReportDetailsGo ameFinalReportDetailsGo : ameFinalReportDetailsGos) {
	    	Optional<AmeFinalReportFileDirGo> optional=ameFinalReportFileDirGoRepository.findByUniqueAmeId(ameFinalReportDetailsGo.getUniqueAmeId());
	    	if(optional.isEmpty()) {
	    		uniqueAmeId=ameFinalReportDetailsGo.getUniqueAmeId();
	    		ameFinalReportFileDirGo.setUniqueAmeId(ameFinalReportDetailsGo.getUniqueAmeId());
	    	}
		}
	    Optional<AmeDeclarationIndividualModel> ameDeclarationIndividualModelOptional = ameDeclarationRepository.findByAmeId(ameId);
	    
	    
	    ameFinalReportFileDirGo.setFileName(fileName);
	    ameFinalReportFileDirGo.setFileType("PDF");
	    ameFinalReportFileDirGo.setFilePath(filePath);
	    ameFinalReportFileDirGo.setFileSize(3);
	    ameFinalReportFileDirGo.setAmeId(ameId);
	    ameFinalReportFileDirGo.setCreatedOn(Calendar.getInstance().getTime());
		ameFinalReportFileDirGoRepository.save(ameFinalReportFileDirGo);
		
		if(ameDeclarationIndividualModelOptional.isPresent()){
			AmeDeclarationIndividualModel ameDeclarationIndividualModel=new AmeDeclarationIndividualModel();
			ameDeclarationIndividualModel=ameDeclarationIndividualModelOptional.get();
			ameDeclarationIndividualModel.setFinalUploadFlag(1);
			ameApplicationFlowStatus.setAmeFinalUplaod(1);
			//ameApplicationFlowStatus.setAmeFinalStatus(CommonConstant.AME_COMPLETED_AND_CLOSED_BY_BM);
			ameApplicationFlowStatus.setAmeFinalEsignByBoardMember(CommonConstant.E_SIGN_COMPLETED_BY_BM);
			ameApplicationFlowStatus.setAmeYear(ameDeclarationIndividualModelOptional.get().getDeclaration_year());
			if(ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus)!=null) {
				AMEFinalCategoryAwarded=ameFinalReportDetailsGoRepository.findByAmeIdAndUniqueAmeId(ameId,uniqueAmeId).getFinalCategoryAwarded();
			}
			logger.info("AmeApplication Status Flow updated......"+ameApplicationFlowStatus);
		}
		
		 return AMEFinalCategoryAwarded;
		
	    
	}


	public void updateAmeFinalResultAfterEsignPO(String ameId, String medicalCategory, String fileName,
			String filePath,String originalPath) 
	{
        File originalFile = new File(originalPath);
        File esignedFile=new File(filePath);
		
        Path sourcePath = esignedFile.toPath();
        Path destinationPath = originalFile.toPath();

        // Move the file
        try {
			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		    AmeApplicationFlowStatus ameApplicationFlowStatus=ameApplicationFlowStatusRepo.findByAmeId(ameId).get();
		    ameApplicationFlowStatus.setAmeFinalStatus(CommonConstant.AME_COMPLETED_AND_CLOSED_BY_BM_AND_PO);
			ameApplicationFlowStatus.setAmeFinalEsignByBoardMember(CommonConstant.E_SIGN_COMPLETED_BY_BM_AND_PO);
			ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
		// TODO Auto-generated method stub
		
	}


	public void updateAmeFinalResultAfterEsignAA(String ameId, String medicalCategory, String fileName,
			String filePath,String originalPath) {
		
	        File originalFile = new File(originalPath);
	        File esignedFile=new File(filePath);
			
	        Path sourcePath = esignedFile.toPath();
	        Path destinationPath = originalFile.toPath();

	        // Move the file
	        try {
				Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			    AmeApplicationFlowStatus ameApplicationFlowStatus=ameApplicationFlowStatusRepo.findByAmeId(ameId).get();
			    ameApplicationFlowStatus.setAmeFinalStatus(CommonConstant.AME_COMPLETED_AND_CLOSED_BY_BM_PO_AND_AA);
				ameApplicationFlowStatus.setAmeFinalEsignByBoardMember(CommonConstant.E_SIGN_COMPLETED_BY_BM_PO_AND_AA);
				ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}
	

	public void addContentWithoutInvalidatingSignature(String filePath, String additionalText) throws IOException, DocumentException {
        // Open the original PDF with PdfReader
        PdfReader reader = new PdfReader(filePath);
        
        // Create a temporary file to write the modified PDF content
        File tempFile = new File(filePath + ".temp");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tempFile));
        
        // Set up the font and size for the added text
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, false);
        Font font = new Font(baseFont, 12);

        // Modify only the non-signed content (such as a new page or specific area)
        // Example: Adding content to a new page, without affecting the signed page
        int lastPageNumber = reader.getNumberOfPages();
        PdfContentByte canvas = stamper.getOverContent(lastPageNumber); // Access the content of the last page

        // Start adding text
        canvas.beginText();
        canvas.setFontAndSize(baseFont, 12);
        canvas.showText(additionalText);
        canvas.endText();

        // Close the stamper and reader to apply changes
        stamper.close();
        reader.close();

        // Replace the original PDF with the modified file
        File originalFile = new File(filePath);
        if (originalFile.exists()) {
            if (!originalFile.delete()) {
                throw new IOException("Failed to delete the original file: " + originalFile.getAbsolutePath());
            }
        }

        // Rename the temporary file to the original file name
        if (!tempFile.renameTo(originalFile)) {
            throw new IOException("Failed to rename the temporary file to the original file: " + originalFile.getAbsolutePath());
        }
    }

	
}
