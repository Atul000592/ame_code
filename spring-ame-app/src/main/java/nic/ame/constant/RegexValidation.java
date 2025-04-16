package nic.ame.constant;

public interface RegexValidation {

	
	public  static final String REGEX_ALPHANUMERIC="^[a-zA-Z0-9]+$";
	public static final String REGEX_ALPANUMARIC_SPACE="^(?!.*--)(?!.*\\s{2})(?!.*[^a-zA-Z0-9\\s-])(?!.*[^-]*-.*-)[a-zA-Z0-9]+([\\s-][a-zA-Z0-9]+)*$";
	public static final String EMPTY_FIELD_MESSAGE="mandatory field cannot be left blank";
	public static final String SPECIAL_CHARATER_ERROR_MESSAGE="special characters not allowed ( . ,& ? < > # { } % ~ \\ )";
	public static final String ALLOW_NULL_VALUE_MESSAGE="For given input only null values is allowed!";
	public static final String FROM_DATE_MESSAGE="Must be before 'to Date'";
	public static final String TO_DATE_MESSAGE="Must be after 'from Date'";
	public static final String EQUAL_DATE_MESSAGE="dates are equal";
}
