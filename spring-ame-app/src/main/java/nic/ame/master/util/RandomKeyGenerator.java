package nic.ame.master.util;

import java.security.SecureRandom;

import java.security.SecureRandom;

public class RandomKeyGenerator {

    public static void main(String[] args) {
        // Generate random alphanumeric key of length 10
        String randomKey = generateRandomAlphaNumericKey(10);
        System.out.println("Random alphanumeric key: " + randomKey);
    }

    // Method to generate a random alphanumeric key of specified length
    public static String generateRandomAlphaNumericKey(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}

