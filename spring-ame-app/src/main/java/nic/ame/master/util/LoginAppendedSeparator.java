package nic.ame.master.util;

public class LoginAppendedSeparator {
	
	
	public static String getLastTenLetters(String input,int size) {
	        // Ensure input length is at least ten characters
	        if (input.length() <= size) {
	            return input; // Return the whole string if length is 10 or less
	        } else {
	            // Get substring from the last ten characters
	            return input.substring(input.length() - size);
	        }
	    }
}
