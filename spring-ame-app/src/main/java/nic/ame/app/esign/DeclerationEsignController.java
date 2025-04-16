 package nic.ame.app.esign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.PdfSignatureAppearance;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.ama.service.SubordinateService_1;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.esign.XMLParse.XMLReadResponseData;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.session.SessionManagement;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.UserDateUtil;


@Controller
public class DeclerationEsignController {

    Logger logger = LoggerFactory.getLogger(DeclerationEsignController.class);

    @Autowired
    PdfGenerateFordeclerationEsign pdfGenerateFordeclerationEsign;
    @Autowired
    ServletContext context;

    @Autowired	
    DeclreationClientServiceForEsign esignClientServiceImp;

    @Autowired
    private ForcePersonalService forcePersonalService;
    
    @Autowired
    private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

    @Autowired
    private SubordinateService_1  subordinateService_1;
    
    @Autowired
	private SessionManagement sessionManagement;
    
    @Autowired
    private EsignRequestDataRepository esignRequestDataRepository;
    
    @Autowired
    private EsignConfigurationUtil esignConfigurationUtil;
    
    @GetMapping("esign-decleration-pdf")
    public ModelAndView esign(@RequestParam("ameId") String ameId,
            @RequestParam("forcepersonalId") String forcepersonalId, HttpServletRequest request, HttpSession session,
            @RequestParam("_csrf") String _csrf) throws Exception {

        ModelAndView model = new ModelAndView("esign/response");
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        logger.info("in declaration esign Controller");
        String esignUrl = esignConfigurationUtil.esginMap.get(StringConstants.url_gatewayUrl);
        
        String clientId = null;
        MultipartFile userImage = null;
        String userId = null;

        String forcePersonalId = (String) session.getAttribute("forcepersonalId");
        ForcePersonnelDto forcePersonalDetailsForSignee = forcePersonalService.getForcePersonalDetails(forcepersonalId);

        String filePath = null;
        String fileName = null;

        Map<String, Object> outputStreamForGeneratedPdf = pdfGenerateFordeclerationEsign.generatePdfForDecleartion(
                ameId, forcePersonalId,
                session, null);

        int year = UserDateUtil.getYear(new Date());
        String directoryName = CommonConstant.FILE_PATH + File.separator + year;

        // Create the directory for the generated PDF
        String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
                String.valueOf(year));
        path = path + File.separator + ameId;
        String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path, ameId);
        String savePath = null;
        // Check if the directory exists
        if (Files.isDirectory(Paths.get(pathForAmeId))) {
            savePath = pathForAmeId;
            fileName = (String) outputStreamForGeneratedPdf.get("fileName");
            logger.info("Path Exists..............!");
            try {
                // Get the OutputStream from the generated PDF
                OutputStream pdfOutputStream = (OutputStream) outputStreamForGeneratedPdf
                        .get("fileOutputStreamObject");

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
                    // For demonstration, assuming you can retrieve an InputStream or read it
                    // differently.
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    try (InputStream pdfInputStream = new ByteArrayInputStream(
                            ((ByteArrayOutputStream) pdfOutputStream).toByteArray())) {
                        while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                            byteArrayOutputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
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
        String st = esignClientServiceImp.createHash(null, ameId, forcePersonalDetailsForSignee.getName(),
                userImage, session, baseUrl, savePath, outputStreamForGeneratedPdf.get("fileName").toString());
        String transId = (String) context.getAttribute("transId");
        logger.info(transId);
        model.addObject("username", forcePersonalDetailsForSignee.getName());
        model.addObject("xml", st);
        model.addObject("acceptClientAction", esignUrl);
        model.addObject("responeUrl", baseUrl + "/response");
        logger.info("session Id (2) >>>>>>>>>>>>>>>" + session.getId() + "ctime(2) " + session.getCreationTime());
      
        return model;
    }


    @PostMapping(value = "decleration-response")
    @CrossOrigin(originPatterns = { StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL,StringConstants.CROSS_ORIGIN_ESIGN_GATEWAY_URL}, allowCredentials = "true")
	public String decleationResponse(@RequestParam(value = "respon", required = false) String respon,
			HttpServletResponse response, HttpServletRequest request,
			RedirectAttributes redirectAttributes,Model model) throws Exception, FileNotFoundException {

		
		
		HttpSession httpSession = request.getSession(false);

		String tnxId = null;
		tnxId = XMLReadResponseData.GetTxnId(respon);
		
		logger.info("Trans id After response:"+tnxId);
		
		if(httpSession==null) {
			
			logger.info("original session is null in declaration.....!");
			
			if (tnxId != null) {
				
				Optional<EsignRequestData> EsignRequestDataOptional = esignRequestDataRepository.findByTxn(tnxId);
		           
				Session savedSession = sessionManagement.getSessionById(EsignRequestDataOptional.get().getSessionId());
				
				
				if (savedSession != null) {
					try {
						PdfSignatureAppearance appearance = (PdfSignatureAppearance) context.getAttribute(tnxId);
					    String	filePath = (String) savedSession.getAttribute("tempfilePath");
						

						String ameId = null, candidateForcePersonnelId = null, fileName = null, filePathForSave = null;

					
						

						ameId=(String) savedSession.getAttribute("ameId");
						candidateForcePersonnelId=(String) savedSession.getAttribute("candidateForcePersonnnelId");
						Optional<AmeApplicationFlowStatus> optional= ameApplicationFlowStatusRepo.findByAmeId(ameId);
					   logger.info("appeabbaba>>"+appearance);
						logger.info("setting session......and sending it....");
						Map<String, Object> signedMap = esignClientServiceImp.getSignedPdf(respon, appearance);
						String msg = (String) signedMap.get("errorMessage");
						String errcode = (String) signedMap.get("errcode");
						String exMessage = (String) signedMap.get("exMessage");
						boolean status = (boolean) signedMap.get("status");
						if (status) {
						AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
						if(!optional.isEmpty()) {
							ameApplicationFlowStatus=optional.get();
							ameApplicationFlowStatus.setAmeFormUploadFlag(1);
							ameApplicationFlowStatus.setEsignByCandidate(1);
							ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
							logger.info("AmeApplication Status Flow updated......!");
							model.addAttribute("message", "AME Declaration Form  eSigned successfully...Ame Report updated...");
							model.addAttribute("msg", "You will be redirected to the main page ..............!");
							File declarationFilePath=new File(filePath);
							String fileNameSaved=subordinateService_1.uploadAmeDeclarationFileAfterEsign(declarationFilePath, ameId, candidateForcePersonnelId, request);
						   logger.info("File Name Saved After Esign By Candidate:"+fileNameSaved);
						   context.removeAttribute(tnxId);
						}
						
						
						} else {
							model.addAttribute("message", "AME Final Report eSigned unsuccessful...Please try again...after some time");
							model.addAttribute("msg", "You will be redirected to the main page ..............!");
							// model.addAttribute("pdfUrl", filePathForSave);
							logger.info("esign unsuccess......" + filePathForSave);
							if (errcode.isEmpty()) {
								System.out.println("exception in esign");
							}
						}

					} catch (NullPointerException e) {
						model.addAttribute("message", "AME Declaration Form  eSigned Unsuccessfully...Ame Report updated...");
						model.addAttribute("msg", "You will be redirected to the main page ..............!");

					} catch (Exception e) {

					} finally {

						System.out.println("Exception Occur in Esign");
					}
							return "UserMenu/esign-response";
				}else {
					model.addAttribute("message", "AME Final Report eSigned unsuccessful...Please try again...after some time");
					model.addAttribute("msg", "You will be redirected to the main page ..............!");
					return "UserMenu/esign-response";

				}
		
			
			
			}
		}
		
		
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>" + httpSession.getId() + " " + httpSession.getCreationTime() +"Trans Id >>>"+tnxId);
		String filePath = null;
		try {
			PdfSignatureAppearance appearance = (PdfSignatureAppearance) context.getAttribute(tnxId);
			filePath = (String) httpSession.getAttribute("tempfilePath");
			

			String ameId = null, candidateForcePersonnelId = null, fileName = null, filePathForSave = null;

		
			

			ameId=(String) httpSession.getAttribute("ameId");
			candidateForcePersonnelId=(String) httpSession.getAttribute("candidateForcePersonnnelId");
			Optional<AmeApplicationFlowStatus> optional= ameApplicationFlowStatusRepo.findByAmeId(ameId);
		
			logger.info("setting session......and sending it....");
			logger.info("appearnce >>>>>>>>>>>>>main "+appearance);
			Map<String, Object> signedMap = esignClientServiceImp.getSignedPdf(respon, appearance);
			String msg = (String) signedMap.get("errorMessage");
			String errcode = (String) signedMap.get("errcode");
			String exMessage = (String) signedMap.get("exMessage");
			boolean status = (boolean) signedMap.get("status");
			if (status) {
			AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
			if(!optional.isEmpty()) {
				ameApplicationFlowStatus=optional.get();
				ameApplicationFlowStatus.setAmeFormUploadFlag(1);
				ameApplicationFlowStatus.setEsignByCandidate(1);
				ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
				logger.info("AmeApplication Status Flow updated......!");
				model.addAttribute("message", "AME Declaration Form  eSigned successfully...Ame Report updated...");
				model.addAttribute("msg", "You will be redirected to the main page ..............!");
				File declarationFilePath=new File(filePath);
				String fileNameSaved=subordinateService_1.uploadAmeDeclarationFileAfterEsign(declarationFilePath, ameId, candidateForcePersonnelId, request);
			   logger.info("File Name Saved After Esign By Candidate:"+fileNameSaved);
			   context.removeAttribute(tnxId);
			}
			
			
			} else {
				model.addAttribute("message", "AME Final Report eSigned unsuccessful...Please try again...after some time");
				model.addAttribute("msg", "You will be redirected to the main page ..............!");
				// model.addAttribute("pdfUrl", filePathForSave);
				logger.info("esign unsuccess......" + filePathForSave);
				if (errcode.isEmpty()) {
					System.out.println("exception in esign");
				}
			}

		} catch (NullPointerException e) {
			model.addAttribute("message", "AME Declaration Form  eSigned Unsuccessfully...Ame Report updated...");
			model.addAttribute("msg", "You will be redirected to the main page ..............!");

		} catch (Exception e) {

		} finally {

			System.out.println("Exception Occur in Esign");
		}
				return "UserMenu/esign-response";

	}



    @GetMapping("generatePdf")
    public String generatePdfDeclaration(
            @RequestParam String forcepersonalId,
            @RequestParam String ameId,
            Model model) {
        model.addAttribute("forcepersonalId", forcepersonalId);
        model.addAttribute("ameId", ameId);
        return "UserMenu/viewpdf"; // Return the view name
    }
   

    @GetMapping("viewPdf")
    public void generatePdfViewDeclaration(@RequestParam("ameId") String ameId,
                            @RequestParam("forcepersonalId") String forcepersonalId,
                            HttpSession session,
                            HttpServletResponse response) {
        try {
            // Generate the PDF
            Map<String, Object> pdfResponse = pdfGenerateFordeclerationEsign.generatePdfForDecleartion(
                    ameId, forcepersonalId, session, response);
            System.out.println("Generated PDF size: " + (pdfResponse != null ? pdfResponse.size() : "null"));
    
            // Prepare the response
            ByteArrayOutputStream outputStream = (ByteArrayOutputStream) pdfResponse.get("fileOutputStreamObject");
            byte[] pdfBytes = outputStream.toByteArray();
    
            // Set response headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=generated.pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
    
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
