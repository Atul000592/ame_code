package nic.ame.app.esign;




import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode;
import com.itextpdf.xmp.impl.Base64;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Service
public class DeclreationClientServiceForEsign {


	private static final XPath xPath = XPathFactory.newInstance().newXPath();
	
	@Autowired
	private EsignRequestDataRepository esignRequestDataRepository;

	Logger logger = LoggerFactory.getLogger(EsignServiceimpl.class);

	@Autowired
	ServletContext context;
	
	 @Autowired
     private EsignConfigurationUtil esignConfigurationUtil;

	public String createHash(String candidateIrlaNuber, String ameId, String userId, MultipartFile userImage,
			HttpSession session, String baseUrl, String filePath, String fileName) throws Exception {

		String txnCounter = getTxnCounter();
		
		baseUrl = "response?rs=" + baseUrl + "/decleration-response";

		logger.info("session Id (1) >>>>>>>>>>>>>>>" + session.getId());

		

		// Define the file to save the PDF
		File pdfFile = new File(filePath, fileName);
		logger.info("File path :-"+pdfFile);
		session.setAttribute("tempfilePath", filePath+File.separator+fileName);
		session.setAttribute("pdfFile", pdfFile);
		
	
	
		session.setAttribute("candidateForcePersonnnelId", userId);
		session.setAttribute("ameId", ameId);
		
		


		Random randomForTransactionId = new Random();
		int random = randomForTransactionId.nextInt(1000);
		StringBuilder sb = new StringBuilder();
		String transId = sb.append(esignConfigurationUtil.esginMap.get(StringConstants.transaction_id)).append(random).toString();
		// Create timestamp
				String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
				String txnTimeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());

		 
		// Get username based on fileNumber
		// String username = userService.getUsernameByFileNumber(fileNumber);

		// Get document hash
		Map<String, Object> documentMap = getDocumentHash(pdfFile.getAbsolutePath(), pdfFile.getAbsolutePath(), null,
				null, null, session, userId,transId+txnTimeStamp+"-"+txnCounter);
		//Object object=documentMap.get("appearancepdf");
		String hash = (String) documentMap.get("hashdocument");

		

		logger.info("we setting session in the context");
		//context.setAttribute("session", session);
		String url = esignConfigurationUtil.esginMap.get(StringConstants.RESPONSE_URL_ESIGN);
		// https://nic-esignstaging.nic.in/eSign21/response?rs=  
		// Construct XML for eSignature
		String st = "";

		st = "<Esign AuthMode=\"1\" ver=\"2.1\" sc=\"Y\"    ts=\"" + timestamp + "\"  txn =\"" + transId + txnTimeStamp
				+ "-" + txnCounter + "\" ekycId=\"\"  aspId=\"" + esignConfigurationUtil.esginMap.get((StringConstants.aspId))
				+ "\" ekycIdType=\"A\" responseSigType=\"pkcs7\"  responseUrl=\"" + url + baseUrl + "\" ><Docs>"
				+ "<InputHash id=\"1\" hashAlgorithm=\"SHA256\" docInfo=\"hello\">" + hash + "</InputHash>\n"
				+ "</Docs></Esign>";
		st = sign(st);
System.out.println(st);
		EsignRequestData esignRequestData=new EsignRequestData();
		esignRequestData.setAmeId(ameId);
		esignRequestData.setCandidateIrlaNuber(candidateIrlaNuber);
		esignRequestData.setCreatedON(Calendar.getInstance().getTime());
		esignRequestData.setFilePath(pdfFile.getAbsolutePath());
		esignRequestData.setTxn(transId+txnTimeStamp+"-"+txnCounter);
		esignRequestData.setResponseUrl("\\"+url+baseUrl+"\\");
		esignRequestData.setXml(st);
		esignRequestData.setSigneeForcePersonnelId(userId);
		esignRequestData.setSignDocumentType("DECLARATION_FORM");
		esignRequestData.setSessionId(session.getId());
		logger.info(">>>> session id to db 1:"+session.getId());
		esignRequestDataRepository.save(esignRequestData);

		// Sign XML
		return st;
	}

	public String getTxnCounter() {
		Random ran = new Random();
		int number = ran.nextInt(999999);
		return String.format("%06d", number);
	}

	public String sign(String xml) throws NoSuchProviderException, UnrecoverableEntryException, KeyStoreException {
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		Document doc;
		try {
			Reference ref = fac
					.newReference("", fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null),
							Collections.singletonList(fac.newTransform(
									"http://www.w3.org/2000/09/xmldsig#enveloped-signature", (XMLStructure) null)),
							null, null);
			SignedInfo si = fac.newSignedInfo(
					fac.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315",
							(XMLStructure) null),
					fac.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null),
					Collections.singletonList(ref));

			KeyStore ks = KeyStore.getInstance("JKS");
			
			InputStream is = new FileInputStream(esignConfigurationUtil.esginMap.get(StringConstants.path_certFilePath));
			ks.load(is, esignConfigurationUtil.esginMap.get(StringConstants.certFilePwd).toCharArray());

			Enumeration<String> enumAlias = ks.aliases();
			String alias = null;
			while (enumAlias.hasMoreElements()) {
				alias = enumAlias.nextElement();
			}

			KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
					new KeyStore.PasswordProtection(esignConfigurationUtil.esginMap.get(StringConstants.certFilePwd).toCharArray()));

			X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

			KeyInfoFactory kif = fac.getKeyInfoFactory();
			ArrayList<Serializable> x509Content = new ArrayList<>();
			x509Content.add(cert.getSubjectX500Principal().getName());
			x509Content.add(cert);
			X509Data xd = kif.newX509Data(x509Content);
			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));

			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource ins = new InputSource();
			ins.setCharacterStream(new StringReader(xml));
			doc = db.parse(ins);

			DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);
			signature.sign(dsc);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			OutputStream os = new ByteArrayOutputStream();
			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error signing XML", e);
		}
		return EsignUtils.convertDocumentToString(doc);
	}

	private Map<String, Object> getDocumentHash(String inputFilePath, String outputFilePath, String coOrdinates,
			Integer pageNo, String fileNumber, HttpSession session, String username,String tnxId) {
		Map<String, Object> documentMap = new HashMap<>();
		try {
			Calendar cal = Calendar.getInstance();

			FileInputStream fis = new FileInputStream(inputFilePath);
			PdfReader reader = new PdfReader(fis);
			fis.close();
			FileOutputStream fout = new FileOutputStream(outputFilePath);
			PdfStamper stamper = PdfStamper.createSignature(reader, fout, ' ', (File) null, true);
			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
			appearance.setLayer2Text("Digitally signed by " + username + "\nDate: " + sdf.format(cal.getTime()));
			appearance.setRenderingMode(RenderingMode.DESCRIPTION);
			appearance.setAcro6Layers(false);
			appearance.setCertificationLevel(0);
			appearance.setImage((Image) null);
			if (coOrdinates != null && pageNo != null) {
				String[] pdfCordinates = coOrdinates.split(",");
				appearance
						.setVisibleSignature(
								new Rectangle(Float.parseFloat(pdfCordinates[0]), Float.parseFloat(pdfCordinates[1]),
										Float.parseFloat(pdfCordinates[2]), Float.parseFloat(pdfCordinates[3])),
								pageNo, null);
			} else {
				// Assuming 'reader' is your PdfReader and 'appearance' is your PdfSignatureAppearance
				int lastPage = reader.getNumberOfPages(); // Get the last page
				float pageHeight = reader.getPageSize(lastPage).getHeight();
				float pageWidth = reader.getPageSize(lastPage).getWidth();

				// Signature dimensions
				float signatureWidth = 130.0F; // Width of the signature block
				float signatureHeight = 40.0F; // Height of the signature block

				// Custom position for the signature
				float xPosition = 415.0F; // 100 points from the left edge of the page
				float yPosition = 167.0F; // 150 points from the bottom edge of the page

				// Set the visible signature in the calculated position
				appearance.setVisibleSignature(
				    new Rectangle(xPosition, yPosition, xPosition + signatureWidth, yPosition + signatureHeight), 
				    lastPage, 
				    null
				);
				
			}

			int contentEstimated = 8192;
			HashMap<PdfName, Integer> exc1 = new HashMap<>();
			exc1.put(PdfName.CONTENTS, contentEstimated * 2 + 2);
			PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			dic.setDate(new PdfDate(appearance.getSignDate()));
			appearance.setCryptoDictionary(dic);
			appearance.preClose(exc1);

			InputStream is = appearance.getRangeStream();
			byte[] bytes = EsignUtils.toByteArray(is);
			documentMap.put("appearancepdf", appearance);
			//session.setAttribute("appearancepdf", appearance);
			logger.info("Trnas id "+tnxId+"appreaea "+appearance);
			context.setAttribute(tnxId, appearance);

			String generatedHash = EsignUtils.generateHash(bytes);

			documentMap.put("hashdocument", generatedHash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentMap;
	}

	public Map<String, Object> getSignedPdf(String eSignResponseXml, PdfSignatureAppearance appearance)
			throws Exception {
		Map<String, Object> res = new HashMap<>();
		try {
			Document doc = EsignUtils.convertStringToDocument(eSignResponseXml);
			String pkcs7response;
			String WsErrMsg = "";
			String RespStatus = EsignUtils.getXpathValue(xPath, "/EsignResp/@status", doc);

			String name = null;
			try {
				XPathExpression expr = xPath.compile("/EsignResp/Signatures/DocSignature[@id='1']/text()");
				name = (String) expr.evaluate(doc, XPathConstants.STRING);

				EsignUtils.signDocument(Base64.decode(name.getBytes("UTF8")), appearance);
			} catch (XPathExpressionException e) {
				System.out.println("Error message: " + e.getMessage());
			}

			if ("1".equals(RespStatus)) {
				pkcs7response = "1-" + xPath.compile("/EsignResp/Signatures/DocSignature").evaluate(doc);
			} else {
				String errcode = EsignUtils.getXpathValue(xPath, "/EsignResp/@errCode", doc);
				res.put("errcode", errcode);
				WsErrMsg = xPath.compile("/EsignResp/@errMsg").evaluate(doc);
				pkcs7response = "0-" + WsErrMsg;
				res.put("errorMessage", WsErrMsg);
			}

			String[] result = pkcs7response.split("-");
			String pkcsressuccessfailure = result[0];
			String returnedstring = result[1];
			byte[] PKCS7Response = Base64.decode(returnedstring.getBytes("UTF8"));

			if (!"0".equals(pkcsressuccessfailure)) {
				byte[] paddedSig = new byte[8192];
				System.arraycopy(PKCS7Response, 0, paddedSig, 0, PKCS7Response.length);
				PdfDictionary dic2 = new PdfDictionary();
				dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));

				res.put("status", true);
				res.put("errorMessage", "Pdf is signed successfully");
				return res;
			} else {
				res.put("status", false);
				res.put("errorMessage", WsErrMsg);
				return res;
			}
		} catch (Exception ex) {
			res.put("status", false);
			res.put("exMessage", ex.toString());
			return res;
		}
	}

	public byte[] getDocumentByteCode(String fileNumber, String documentCode) {
		byte[] b = null;
		try {
			InputStream inputStream = new FileInputStream(System.getProperty("user.home") + "/" + fileNumber + "/"
					+ fileNumber + "_" + documentCode + ".pdf");
			byte[] buffer = new byte[8192];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
			b = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public String getClientIpAddress(HttpServletRequest request) {
		String ipaddstr = "";
		try {
			String ipadd = request.getHeader("X-FORWARDED-FOR");
			String Clientip = request.getRemoteAddr();
			if (ipadd == null || ipadd.equals("")) {
				ipadd = "";
			}
			String[] ip = ipadd.split(",");
			for (String s : ip) {
				if (s != null && !s.trim().equals("")) {
					ipaddstr = s;
					break;
				}
			}
			if (ipaddstr == null || ipaddstr.equals("")) {
				ipaddstr = Clientip;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ipaddstr;
	}

	public String getMACAddress(HttpServletRequest request) {
		String ip = getClientIpAddress(request);
		String str = "";
		String macAddress = "";
		final int MAX_SIZE = 200000;
		char[] buffer = new char[MAX_SIZE];
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			int value = input.read(buffer, 0, MAX_SIZE);
			while ((value = input.read()) != -1) {
				char c = (char) value;
				str += c;
			}
			if (str.indexOf("MAC Address") > 1) {
				macAddress = str.substring(str.indexOf("MAC Address") + 14).trim();
			} else {
				macAddress = "MAC";
			}
		} catch (IOException e) {
			macAddress = "MAC";
		}
		return macAddress;
	}

//    public void savePdf(ByteArrayOutputStream pdfOutputStream, String fileName, String directoryPath) throws IOException {
//        // Save PDF to directory
//        File directory = new File(directoryPath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        File pdfFile = new File(directory, fileName);
//        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
//            pdfOutputStream.writeTo(fos);
//        }
//
//        // Save PDF to database
//        PdfFile pdf = new PdfFile();
//        pdf.setFileName(fileName);
//        pdf.setFileData(pdfOutputStream.toByteArray());
//        pdffileRepository.save(pdf);
//    }

}
