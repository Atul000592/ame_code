package nic.ame.app.master.serviceImpl;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import nic.ame.app.master.service.DecryptAESServiceForPasswordEncryption;
import nic.ame.master.util.HexStringToByteArray;

@Service
public class DecryptAESServiceForPasswordEncryptionImpl implements DecryptAESServiceForPasswordEncryption {

	@Override
	public String getDecryptAESPassword(String password, String ivHex, String keyHex) {
		 

	        byte[] keyBytes = HexStringToByteArray.hexStringToByteArray(keyHex);
	        byte[] ivBytes = HexStringToByteArray.hexStringToByteArray(ivHex);

	        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

	        // The encrypted message (Base64 encoded) received from JavaScript
	        String encryptedMessage = password;

	        // Decode the Base64 encoded message
	        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);

	        // Decrypt the message
	        Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        byte[] original = null;
			try {
				original = cipher.doFinal(encryptedBytes);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String decryptedMessage = new String(original);

	      
	        return decryptedMessage;

	}

}
