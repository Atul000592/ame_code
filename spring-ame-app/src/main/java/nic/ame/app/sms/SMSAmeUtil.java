package nic.ame.app.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.ToString;

public class SMSAmeUtil {

	
	public static String sendMessage(String countryIsdCode, String mobileNumber, String message,String templateId) {

		Logger logger=LoggerFactory.getLogger(SMSAmeUtil.class);
		
		logger.info("Sending SMS");

		SMSDto dto=new SMSDto();
		
		
		try {

			String encodedUserName = URLEncoder.encode(SMSConfigurationConstantsAME.SMS_GATEWAY_USER_NAME, "UTF-8");
			String encodedPassword = URLEncoder.encode(SMSConfigurationConstantsAME.SMS_GATEWAY_PASSWORD, "UTF-8");
			String encodedSender = URLEncoder.encode(SMSConfigurationConstantsAME.SMS_GATEWAY_SENDER, "UTF-8");
			String encodedDltEntityId = URLEncoder.encode(SMSConfigurationConstantsAME.DLT_ENTITY_ID, "UTF-8");
			String encodedDltTemplateId = URLEncoder.encode(templateId, "UTF-8");
			String encodedSMSMessage = URLEncoder.encode(message.trim(), "UTF-8");

			// TODO: Handle double character spaces e.g. curly bracket characters
			int messageLength = encodedSMSMessage.length();
			String smsGateWayString;

			if(messageLength <= SMSConfigurationConstantsAME.SMS_MAXIMUM_CHARACTERS_FOR_SINGLE_MESSAGE) {

				smsGateWayString = SMSConfigurationConstantsAME.SMS_GATEWAY_URL_HYDRABAD+
						"username=" + encodedUserName
						+ "&pin=" + encodedPassword
						+ "&mnumber=" + countryIsdCode + mobileNumber
						+ "&message=" + encodedSMSMessage
						+ "&signature=" + encodedSender
						+ "&dlt_entity_id=" + encodedDltEntityId
						+ "&dlt_template_id=" + encodedDltTemplateId;
				
				dto.setEncodedDltEntityId(SMSConfigurationConstantsAME.DLT_ENTITY_ID);
				dto.setEncodedDltTemplateId(templateId);
				dto.setEncodedUserName(SMSConfigurationConstantsAME.SMS_GATEWAY_USER_NAME);
				dto.setEncodedPassword(SMSConfigurationConstantsAME.SMS_GATEWAY_PASSWORD);
				dto.setEncodedSender(SMSConfigurationConstantsAME.SMS_GATEWAY_SENDER);
				dto.setEncodedSMSMessage(message.trim());
				dto.setMobileNumber(countryIsdCode+mobileNumber);
										
				
				         logger.info("SMS STRING 1 :-"+smsGateWayString);
			}else {

				smsGateWayString = SMSConfigurationConstantsAME.SMS_GATEWAY_URL_HYDRABAD
						+ "?username=" + encodedUserName
						+ "&pin=" + encodedPassword
						+ "&signature=" + encodedSender
						+ "&dlt_entity_id=" + encodedDltEntityId
						+ "&dlt_template_id=" + encodedDltTemplateId
						+ "&mnumber=" + countryIsdCode + mobileNumber
						+ "&message=" + encodedSMSMessage
						+ "&concat=" + SMSConfigurationConstantsAME.SMS_CONCAT_MULTIPLE_MESSAGES;
				

				dto.setEncodedDltEntityId(SMSConfigurationConstantsAME.DLT_ENTITY_ID);
				dto.setEncodedDltTemplateId(templateId);
				dto.setEncodedUserName(SMSConfigurationConstantsAME.SMS_GATEWAY_USER_NAME);
				dto.setEncodedPassword(SMSConfigurationConstantsAME.SMS_GATEWAY_PASSWORD);
				dto.setEncodedSender(SMSConfigurationConstantsAME.SMS_GATEWAY_SENDER);
				dto.setEncodedSMSMessage(message.trim());
				dto.setMobileNumber(countryIsdCode+mobileNumber);
				logger.info("SMS STRING 2 :-"+smsGateWayString);
			}


			logger.debug("SMS Gateway connection string Before sending .. >>>" + smsGateWayString + "<<<");

			//Development
			//logger.debug("SMS Gateway connection string  >>>" + smsGateWayString + "<<<");
			//String result = "SMS disabled for testing";

			//Production
			//String result = makeHTTPSConnection(smsGateWayString, 10000);
			String result=makeHTTPSPOSTConnection(SMSConfigurationConstantsAME.SMS_GATEWAY_URL_HYDRABAD,dto,10000);
			//TODO: Increase timeout to one minute, if needed
			logger.debug("SMS Gateway connection string  >>>" + smsGateWayString + "<<<");
			logger.debug("SMS Result >>>" + result + "<<<");

			if(result == null || result.isEmpty()) {

				logger.error("Cannot connect to the SMS Gateway.");
			}

			return result;
		}
		catch (Exception e) {

			//TODO: Handle the error case, so that SMS messages are not lost.
			e.printStackTrace();
		}

		return null;
	}

	
	
	
	public static String makeHTTPSPOSTConnection(String URI,SMSDto smsDto,int connectTimeOut) {
	
		System.out.println("in the methos.........");
		String responseMessage="";
		
		try {
		URL url = new URL("https://hydgw.sms.gov.in/failsafe/MLink");
	
		HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
		System.out.println("http "+http);
		SSLUtil.disableCertificateCheck(http);

		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type", "text/plain");

		String data = "username="+smsDto.getEncodedUserName()
		               +"&pin="+smsDto.getEncodedPassword()
		               +"&mnumber="+smsDto.getMobileNumber()
		               +"&message="+smsDto.getEncodedSMSMessage()
		               +"&signature="+smsDto.getEncodedSender()
		               +"&dlt_entity_id="+smsDto.getEncodedDltEntityId()
		               +"&dlt_template_id="+smsDto.getEncodedDltTemplateId();

		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		System.out.println("in the methos.........SData"+data);
		System.out.println("in the methos.........out"+out);

		
		OutputStream stream = http.getOutputStream();
		stream.write(out);

		
		http.disconnect();
		
		System.out.println("SMS Response Message:-"+http.getResponseMessage().toString()+">>SMS Response Code: "+http.getResponseCode());
		responseMessage="Response Message:-"+http.getResponseMessage().toString()+" Response Code "+http.getResponseCode();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}
	
	
	
	
	/*
	 * private static String makeHTTPSConnection(String urlString, int
	 * connectTimeOut) {
	 * 
	 * StringBuffer output = new StringBuffer(); HttpsURLConnection connection =
	 * null; BufferedReader reader = null;
	 * 
	 * try {
	 * 
	 * URL url = new URL(urlString); connection = (HttpsURLConnection)
	 * url.openConnection(); connection.setRequestMethod("POST");
	 * 
	 * //Disabling the SSL certificate check as the Certifying Authority
	 * "Let's Encrypt" of SMS Gateway is not in the Java trust store.
	 * //Additionally, the certificate is being changed every few months, therefore
	 * the SMS functionality breaks frequently even after importing the certificate
	 * into the trust store SSLUtil.disableCertificateCheck(connection);
	 * connection.setDoOutput(true); connection.setRequestProperty("Accept",
	 * "application/json"); connection.setRequestProperty("Content-Type",
	 * "text/plain"); connection.setConnectTimeout(connectTimeOut);
	 * connection.setReadTimeout(connectTimeOut);
	 * 
	 * reader = new BufferedReader(new
	 * InputStreamReader(connection.getInputStream()));
	 * 
	 * output.append(reader.readLine());
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } finally {
	 * 
	 * try { if(reader != null) { reader.close(); } if(connection != null) {
	 * connection.disconnect(); } } catch (Exception e) {
	 * 
	 * reader = null; connection = null; } }
	 * 
	 * // return output.toString(); }
	 */
}
