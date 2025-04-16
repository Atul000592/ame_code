package nic.ame.master.util;

import java.security.SecureRandom;

public class HexStringToByteArray {

	
	public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
	
	 public static String keyGeneraterHex(int size) {
   	  

	      	SecureRandom secureRandom = new SecureRandom();

	          // Generate a 256-bit (32-byte) random key
	          byte[] key = new byte[size];
	          secureRandom.nextBytes(key);

	          // Convert the key to a hexadecimal string
	          StringBuilder keyHex = new StringBuilder();
	          for (byte b : key) {
	              keyHex.append(String.format("%02x", b));
	          }

	          System.out.println("Generated key (Hex): " + keyHex.toString());
	          return keyHex.toString();
	      }
}
