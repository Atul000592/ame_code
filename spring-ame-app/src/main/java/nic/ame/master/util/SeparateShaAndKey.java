package nic.ame.master.util;

import java.util.Random;

public class SeparateShaAndKey {
	 public static void main(String[] args) {
	        String input = "9728827e3af29458d577311849918b22fd9e779db66ca30cb074f2a2c4ba041aS3zXxj3W3G";

	        // Remove the last ten characters
	        String result = removeLastTenCharacters(input);
	        System.out.println("Result after removing last ten characters: " + result);
	        
	        Random random = new Random();

		     int randomCode=random.nextInt(900); 
		     System.out.println(randomCode);
	    }

	    // Method to remove the last ten characters of a string
	    public static String removeLastTenCharacters(String input) {
	        if (input.length() <= 13) {
	            return ""; // Return empty string if input length is 10 or less
	        } else {
	            return input.substring(0, input.length() - 13); // Return substring from start to length-10
	        }
	     
	    }
	    public static String removeLastCharacters(String input,int size) {
	        if (input.length() <= size) {
	            return ""; // Return empty string if input length is 10 or less
	        } else {
	            return input.substring(0, input.length() - size); // Return substring from start to length-10
	        }
	     
	    }

}
