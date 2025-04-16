package nic.ame.master.util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nic.ame.constant.CommonConstant;

public class SHA256WithSaltNew {





    // Function to generate a SHA-256 hash with a salt
    public static String sha256WithSalt(String data, String salt) {
        try {
            // Combine the data with the salt
            String combinedData = data + salt;

            // Get a SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Generate the hash
            byte[] hashBytes = digest.digest(combinedData.getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes to a hexadecimal string
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }

            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        // Get the input data from the user
        String data="Password@1234";
        String salt=CommonConstant.SHA_KEY_256;

        // Generate the hash
        String hashedData = sha256WithSalt(data, salt);
        
       
        		
        
        
        if(hashedData.equals("9728827e3af29458d577311849918b22fd9e779db66ca30cb074f2a2c4ba041a")) {
        	System.out.println("both code are macth");
        }
       	String encryptedBcryptedPassword=bCryptPasswordEncoder.encode("9728827e3af29458d577311849918b22fd9e779db66ca30cb074f2a2c4ba041a");
         System.out.println("encryptedBcryptedPassword "+encryptedBcryptedPassword);
       	if(bCryptPasswordEncoder.matches(hashedData, encryptedBcryptedPassword)) {
       		System.out.println("password masdawdawdawdaw.............");
       	}else {
       		System.out.println("not maccvhlk;lk;l");
       	}
       	
        // Display the result
        System.out.println("Hashed Data: " + hashedData);

        scanner.close();
    }
}
