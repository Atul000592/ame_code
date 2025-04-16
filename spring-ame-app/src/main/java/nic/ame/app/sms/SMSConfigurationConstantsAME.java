package nic.ame.app.sms;

public interface SMSConfigurationConstantsAME {

	public static final String SMS_GATEWAY_URL_DELHI = "https://smsgw.sms.gov.in/failsafe/MLink";
	public static final String SMS_GATEWAY_URL_HYDRABAD = "https://hydgw.sms.gov.in/failsafe/MLink";

	public static final String SMS_GATEWAY_USER_NAME = "bsfame.sms";
	public static final String SMS_GATEWAY_PASSWORD = "mF1XfCQg";

	public static final String SMS_GATEWAY_SENDER = "BSFAME";

	public static final String DLT_ENTITY_ID = "1701160101013178098";

	public static final String DLT_TEMPLATE_ID_MAPPING_SMS = "1707172838030415988";

	public static final String DLT_TEMPLATE_ID_PRE_AME_SMS = "1707172838051406124";

	public static final String DLT_TEMPLATE_ID_AME_SMS = "1707172838140805383";

	public static final String DLT_TEMPLATE_ID_REVIEW_MEDICAL_BOARD = "1707172838177265040";

	public static final String DLT_TEMPLATE_ID_FORGET_PASSWORD = "1707172838254211755";
	public static final String DLT_TEMPLATE_ID_RESCHEDULING_APPOINTMENT = "1707173190784118932";

	public static final String IRLA_NUMBER = "irlaNo";
	public static final String FORCE_PERSONNEL_RANK = "rank";
	public static final String FORCE_PERSONNEL_NAME = "name";
	public static final String AME_COMPLETED_ON_DATE = "ameCompletedOnDate";
	public static final String MEDICAL_CATEGORY_AWARDED = "categoryAwarded";
	public static final String AME_YEAR = "ameYear";
	public static final String AME_BOARD_PLACE = "boardPlace";
	public static final String AME_BOARD_PLACE1 = "boardPlace1";
	public static final String CATEGORY_AWARDED_PART1="categoryAwarded";
	public static final String CATEGORY_AWARDED_PART2="categoryAwarded1";
	public static final String CATEGORY_AWARDED_PART3="categoryAwarded2";

	

	public static final String TO_DATE = "toDate";
	public static final String FROM_DATE = "fromDate";
	public static final String OTP="otp";
	public static final String OTP_REFERENCE_ID="referenceId"; 
	public static final String OTP_VALID_FOR="otpValidFor";
	public static final String AME_REVIEW_DATE="ameReviewDate";

	/*
	 * Maximum character limits:
	 *
	 * For Single SMS:
	 *
	 * Bits in Character | Number of Maximum Allowed Characters 7 bit characters |
	 * 160 8 bit characters | 140 16 bit characters | 70
	 *
	 * For Concatenated SMS:
	 *
	 * Maximum number of segments per SMS | 3 NOTE: The value 3 is based on the API
	 * documentation, however during testing it was found that the limit is higher
	 *
	 * Bits in Character | Number of Maximum Allowed Characters Per Segment | Total
	 * Number of Maximum Allowed Characters (Previous value * 3) 7 bit characters |
	 * 153 | 459 8 bit characters | 134 | 402 16 bit characters | 67 | 201
	 */

	public static final int SMS_MAXIMUM_CHARACTERS_FOR_SINGLE_MESSAGE = 160;
	// public static final int SMS_MAXIMUM_CHARACTERS_FOR_CONCATENATED_MESSAGE =
	// 459; //This value is based on the SMS Gateway API documentation. However
	// during testing it was found that the actual limit is higher.

	public static final int SMS_CONCAT_MULTIPLE_MESSAGES = 1;
}
