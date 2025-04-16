package nic.ame.app.esign.XMLParse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLReaderRequestAndResponse {

    public static String getEsignRequestData(String xml) {
    
    Logger logger=LoggerFactory.getLogger(XMLReaderRequestAndResponse.class);
    

        try {
            // Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Parse the XML string into a Document
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();

            // Read values from the Esign element
            Element esignElement = document.getDocumentElement();
            String authMode = esignElement.getAttribute("AuthMode");
            String aspId = esignElement.getAttribute("aspId");
            String responseUrl = esignElement.getAttribute("responseUrl");
            String txn = esignElement.getAttribute("txn");
            String docInfo = null,hashAlgorithm = null,inputHashValue=null,signatureValue=null;
            // Read values from the Docs/InputHash element
            NodeList inputHashList = document.getElementsByTagName("InputHash");
            if (inputHashList.getLength() > 0) {
                Element inputHashElement = (Element) inputHashList.item(0);
                 docInfo = inputHashElement.getAttribute("docInfo");
                hashAlgorithm = inputHashElement.getAttribute("hashAlgorithm");
                inputHashValue = inputHashElement.getTextContent();

                // Print InputHash details
                logger.info("InputHash Details:");
                logger.info("Doc Info: " + docInfo);
                logger.info("Hash Algorithm: " + hashAlgorithm);
                logger.info("Input Hash Value: " + inputHashValue);
            }

            // Read values from the Signature element
            NodeList signatureValueList = document.getElementsByTagName("SignatureValue");
            if (signatureValueList.getLength() > 0) {
                Element signatureValueElement = (Element) signatureValueList.item(0);
                 signatureValue = signatureValueElement.getTextContent();
                logger.info("Signature Value: " + signatureValue);
            }

            logger.info("AuthMode "+authMode);
            logger.info("aspId "+aspId);
            logger.info("responseUrl "+responseUrl);
            logger.info("txn "+txn);
          
         
            
            logger.info("======================= END ==================================");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		return new String();
    }
}
