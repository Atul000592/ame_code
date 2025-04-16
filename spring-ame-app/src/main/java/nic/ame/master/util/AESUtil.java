package nic.ame.master.util;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;

    public static String decrypt(String encryptedPassword, String key) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(encryptedPassword);
        byte[] iv = Arrays.copyOfRange(encryptedData, 0, 12);
        byte[] cipherText = Arrays.copyOfRange(encryptedData, 12, encryptedData.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(cipherText);

        return new String(decryptedBytes);
    }

    
    public static String encrypt(String plaintext, String key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = generateIV();

        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes("UTF-8"));

        // Combine IV and ciphertext and encode using Base64
        byte[] ivAndCipherText = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, ivAndCipherText, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(ivAndCipherText);
    }
    
    
    private static byte[] generateIV() {
        byte[] iv = new byte[12]; // IV length for GCM mode is 12 bytes
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
    
}

