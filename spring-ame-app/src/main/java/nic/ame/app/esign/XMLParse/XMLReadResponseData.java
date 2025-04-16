package nic.ame.app.esign.XMLParse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;



public class XMLReadResponseData {
   static Logger logger=LoggerFactory.getLogger(XMLReaderRequestAndResponse.class);
    
    
    public static String GetTxnId(String xml) {
    	String txn=null;
    	
    	try {
    	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         
         // Parse the XML string into a Document
         InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
         Document document = builder.parse(is);
         document.getDocumentElement().normalize();

         // Read values from the Esign element
         Element esignElement = document.getDocumentElement();
       
         txn = esignElement.getAttribute("txn");
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	return txn; 
    	
    }

}
