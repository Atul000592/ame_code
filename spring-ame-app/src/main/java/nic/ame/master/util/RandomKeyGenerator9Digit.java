package nic.ame.master.util;

import java.security.SecureRandom;

public class RandomKeyGenerator9Digit {
	public static String generateRandomKey(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            key.append(characters.charAt(randomIndex));
        }

        return key.toString();
    }
}
