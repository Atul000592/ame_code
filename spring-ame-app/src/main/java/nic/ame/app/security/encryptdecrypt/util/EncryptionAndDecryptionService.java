package nic.ame.app.security.encryptdecrypt.util;

import java.util.Map;

public interface EncryptionAndDecryptionService {

	
	public Map<String, Object> encryptedText(String input);
	public String decryptedText(Map<String, Object> map);
}
