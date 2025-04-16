package nic.ame.app.esign;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class EsignConfigurationUtil {

	
	@Value("${spring.esign.key.aspId}")
	private String aspId;
	@Value("${spring.esign.key.transaction_id}")
	private String transaction_id;
	@Value("${spring.esign.key.certFilePwd}")
	private String certFilePwd;
	@Value("${spring.esign.key.LOCAL_HOST_URL}")
	private String LOCAL_HOST_URL;
	@Value("${spring.esign.key.AME_BASE_URL}")
	private String AME_BASE_URL;
	@Value("${spring.esign.key.path_certFilePath}")
	private String path_certFilePath;
	@Value("${spring.esign.key.url_gatewayUrl}")
	private String url_gatewayUrl;
	@Value("${spring.esign.key.RESPONSE_URL_ESIGN}")
	private String RESPONSE_URL_ESIGN;
	@Value("${spring.esign.key.ESIGN_GATEWAY_URL}")
	private String ESIGN_GATEWAY_URL;
	
	public Map<String,String> esginMap=new HashMap<>();
	
	public Map<String, String> getEsginMap() {
		return esginMap;
	}



	public void setEsginMap(Map<String, String> esginMap) {
		this.esginMap = esginMap;
	}



	@PostConstruct
    public void init() {
		esginMap.put("aspId", aspId);
		esginMap.put("transaction_id", transaction_id);
		esginMap.put("certFilePwd", certFilePwd);
		esginMap.put("LOCAL_HOST_URL", LOCAL_HOST_URL);
		esginMap.put("AME_BASE_URL", AME_BASE_URL);
		esginMap.put("path_certFilePath", path_certFilePath);
		esginMap.put("url_gatewayUrl", url_gatewayUrl);
		esginMap.put("RESPONSE_URL_ESIGN", RESPONSE_URL_ESIGN);
		esginMap.put("ESIGN_GATEWAY_URL", ESIGN_GATEWAY_URL);
    }

	

	@Override
	public String toString() {
		return "EsignConfigurationUtil [aspId=" + aspId + ", transaction_id=" + transaction_id + ", certFilePwd="
				+ certFilePwd + ", LOCAL_HOST_URL=" + LOCAL_HOST_URL + ", AME_BASE_URL=" + AME_BASE_URL
				+ ", path_certFilePath=" + path_certFilePath + ", url_gatewayUrl=" + url_gatewayUrl
				+ ", RESPONSE_URL_ESIGN=" + RESPONSE_URL_ESIGN + ", ESIGN_GATEWAY_URL=" + ESIGN_GATEWAY_URL
				+ ", esginMap=" + esginMap + "]";
	}
	
	
}
