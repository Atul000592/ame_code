package nic.ame.app.security.encryptdecrypt.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class EncryptionAndDecryptionUtil implements EncryptionAndDecryptionService {

	@Override
	public Map<String, Object> encryptedText(String input) {

		Map<String, Object> map = new HashMap<>();
		SecretKey key = null;
		try {
			key = EncryptionDecryptionAlgoUtil.generateKey(128);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IvParameterSpec ivParameterSpec = EncryptionDecryptionAlgoUtil.generateIv();
		String algorithm = "AES/CBC/PKCS5Padding";
		String cipherText = null;
		try {
			cipherText = EncryptionDecryptionAlgoUtil.encrypt(algorithm, input, key, ivParameterSpec);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("secretKey", key);
		map.put("ivParameterSpec", ivParameterSpec);
		map.put("cipherText", cipherText);
		return map;
	}

	public String decryptedText(Map<String, Object> map) {

		SecretKey key = (SecretKey) map.get("secretKey");
		IvParameterSpec ivParameterSpec = (IvParameterSpec) map.get("ivParameterSpec");
		String cipherText = (String) map.get("cipherText");
		String algorithm = "AES/CBC/PKCS5Padding";
		String plainText = null;
		try {
			plainText = EncryptionDecryptionAlgoUtil.decrypt(algorithm, cipherText, key, ivParameterSpec);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return plainText;
	}

}
