package nic.ame.master.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512Code {

	
	public static String sha512(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(input.getBytes());
        
        // Convert byte array to hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        
        return hexString.toString();
    }
}
