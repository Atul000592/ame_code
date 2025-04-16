package nic.ame.master.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nic.ame.constant.CommonConstant;

public class SHA256WithSalt {
	
	
    public static String sha256WithSalt(String message, String salt) {
    	
    	
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedMessage = salt + message;
            byte[] hash = digest.digest(saltedMessage.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String message = "Password@1234/.-";
        String salt = CommonConstant.SHA_KEY_256;
        String shaPassword="9728827e3af29458d577311849918b22fd9e779db66ca30cb074f2a2c4ba041a";

        String encryptedMessage=(sha256WithSalt(message, salt));
        System.out.println("encryptedMessage " +encryptedMessage);
        System.out.println(sha256WithSalt(message, salt));
       if( encryptedMessage.equals(shaPassword)) {
    	   System.out.println("true");
       }
       BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
   	
   	String encryptedBcryptedPassword=bCryptPasswordEncoder.encode("9728827e3af29458d577311849918b22fd9e779db66ca30cb074f2a2c4ba041a");
   	
   	System.out.println(encryptedBcryptedPassword);
      
      if( bCryptPasswordEncoder.matches(encryptedMessage, encryptedBcryptedPassword)) {
    	  System.out.println("password matches the password....");
      }else {
    	  System.out.println("NOT matches the password....");

      }
       
       
    }
}

