package nic.ame.master.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternHanlder {
	
	private static final String ALLOWED_PATTERN="^[a-zA-Z0-9()\\-\\ /]*$";

    private static final String DISALLOWED_PATTERN=".*--.*";

    public static boolean isValidString(String inputString) {
        // Compile the regex pattern for allowed characters
        Pattern allowedPatternCompiled = Pattern.compile(ALLOWED_PATTERN);
        Matcher allowedMatcher = allowedPatternCompiled.matcher(inputString);

        // Compile the regex pattern for disallowed sequences
        Pattern disallowedPatternCompiled = Pattern.compile(DISALLOWED_PATTERN);
        Matcher disallowedMatcher = disallowedPatternCompiled.matcher(inputString);

        // Check if the entire string matches the allowed pattern
        boolean isAllowed = allowedMatcher.matches();
        // Check if the string contains disallowed sequences
        boolean containsDisallowed = disallowedMatcher.find();

        // Final result
        return isAllowed && !containsDisallowed;
    }
    
   

}
