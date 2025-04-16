package nic.ame.constant;

import org.springframework.stereotype.Component;

@Component
public interface CommonConstant {

	// ============Development======================//

	public static final String INSERT_STATUS_YES = "Yes";
	public static final String INSERT_STATUS_NO = "NO";
    public static final int CLOSE_OR_COMPLETED=3;
	
    
	public static final int BOARD_MEMBER_ESIGN =204;
	public static final int ESIGN_BY_BM_PO =205;
	public static final int ESIGN_BY_BM_PO_AND_AA =206;
	/*
	 * public static final String FILE_PATH = "C:\\ImageAndFile"; public static
	 * final String File_PATH_NOTIFICATION = "C:\\Notification"; public static final
	 * String File_PATH_USER_ROLE_ORDER = "C:\\UserRoleOrder"; public static final
	 * String File_PATH_INVESTIGATION_FILE_UPLOAD = "C:\\InvestigationReport";
	 * public static final String AME_FINAL_REPORT_PATH = "C:\\finalReport"; public
	 * static final String DEFAULT_FILE_PATH_NAME = "C:\\default.pdf";
	 */
	  
//	 
    
    //Local//
    
	public static final String FILE_PATH = "C:\\ImageAndFile";
	public static final String File_PATH_NOTIFICATION = "C:\\Notification";
	public static final String File_PATH_IMAGE_CAPTURE = "C:\\BaseFolder";
	public static final String File_PATH_USER_ROLE_ORDER = "C:\\UserRoleOrder";
	public static final String File_PATH_INVESTIGATION_FILE_UPLOAD = "C:\\InvestigationReport";
	public static final String File_PATH_USER_MANUAL_UPLOAD = "C:\\UserManual";
	public static final String AME_FINAL_REPORT_PATH = "C:\\finalReport";
	public static final String DEFAULT_FILE_PATH_NAME = "C:\\Default\\default.pdf";
	 
		
		/*
		 * public static final String FILE_PATH = "E:\\ImageAndFile"; public static
		 * final String File_PATH_NOTIFICATION = "E:\\Notification"; public static final
		 * String File_PATH_IMAGE_CAPTURE = "E:\\BaseFolder"; public static final String
		 * File_PATH_USER_ROLE_ORDER = "E:\\UserRoleOrder"; public static final String
		 * File_PATH_INVESTIGATION_FILE_UPLOAD = "E:\\InvestigationReport"; public
		 * static final String AME_FINAL_REPORT_PATH = "E:\\finalReport"; public static
		 * final String DEFAULT_FILE_PATH_NAME = "E:\\Default\\default.pdf";
		 */
		 
	 
	 

	// ===========Staging Live======================//

	/*
	 * public static final String FILE_PATH = "/mnt/ame/imageandfile"; public static
	 * final String File_PATH_IMAGE_CAPTURE = "/mnt/ame/BaseFolder"; public static
	 * final String File_PATH_USER_ROLE_ORDER = "/mnt/ame/userRoleOrder"; public
	 * static final String File_PATH_NOTIFICATION = "/mnt/ame/Notification"; public
	 * static final String File_PATH_INVESTIGATION_FILE_UPLOAD
	 * ="/mnt/ame/InvestigationReport"; public static final String
	 * AME_FINAL_REPORT_PATH = "/mnt/ame/finalReport"; public static final String
	 * DEFAULT_FILE_PATH_NAME = "/mnt/ame/default.pdf";
	 */
	 

	// ===========Staging Demo======================//

//	
//	public static final String FILE_PATH = "/var/demoame/imageandfile";
//	public static final String File_PATH_IMAGE_CAPTURE = "/var/demoame/BaseFolder";
//	public static final String File_PATH_USER_ROLE_ORDER = "/var/demoame/userRoleOrder";
//	public static final String File_PATH_NOTIFICATION = "/var/demoame/Notification";
//	public static final String File_PATH_INVESTIGATION_FILE_UPLOAD = "/var/demoame/InvestigationReport";
//	public static final String AME_FINAL_REPORT_PATH = "/var/demoame/finalReport";
//	public static final String DEFAULT_FILE_PATH_NAME = "/var/demoame/default.pdf";
//	public static final String File_PATH_USER_MANUAL_UPLOAD = "/var/demoame/usermanual";

	// =======================common constant============================//
	public static final Integer BOARD_MEMBER_ROLE_ID = 1;
	public static final Integer BOARD_MEMBER_ROLE_CODE = 2;
	public static final Integer USER_ROLE_STATUS_INACTIVE = 3;
	public static final Integer USER_ROLE_STATUS_ACTIVE = 4;
	public static final Integer OTP_LENGTH = 5;
	public static final Integer AME_APPOINTMENT_PENDING = 6;
	public static final Integer AME_APPOINTMENT_PROVIDED = 7;
	public static final Integer AME_APPOINTMENT_BOARD_CHANGE = 8;
	public static final Integer AME_DECLARAION_PENDING = 9;
	public static final Integer AME_DECLARAION_NA = 910;
	public static final Integer AME_DECLARAION_COMPLETED = 10;
	public static final Integer AME_STATUS_YES_FLAG = 11;
	public static final Integer AME_STATUS_YES_NO = 12;
	public static final Integer GAZETTED = 13;
	public static final Integer NON_GAZETTED = 14;
	public static final Integer AME_DECLARATION_UPLOAD_COMPLETE = 15;
	public static final Integer AME_DECLARATION_UPLOAD_INCOMPLETE = 16;
	public static final Integer CHECKUPLIST_PROVIDED_COMPLETE = 17;
	public static final Integer CHECKUPLIST_PROVIDED_INCOMPLETE = 18;
	public static final Integer AME_DATA_CHECK_COMPLETE = 19;
	public static final Integer AME_DATA_CHECK_INCOMPLETE = 20;
	public static final Integer DEALING_HAND_ROLE_CODE = 21;
	public static final Integer MEDICAL_DEALING_HAND_ROLE_CODE = 50;

	// ==================================================================================//

	public static final Integer DEFAULT_URI_FOR_UNAUTHORIZED_ACCESS = 299;

	// =====================URI-FILLED_AME_DECLARATION_FORM===============================//
	public static final Integer AME_DECLARATION_FORM_TO_DEALING_HAND_URI = 22;
	public static final Integer AME_DECLARATION_FORM_TO_BOARD_MEMEBER_URI = 23;
	public static final Integer AME_DECLARATION_FORM_TO_AME_URI = 24;
	public static final Integer AME_DECLARATION_FORM_TO_AME_PO = 125;

	// ========================URI-AME_DECLARATION_LIST===============================//
	public static final Integer AME_DECLARATION_LIST_TO_DEALING_HAND_URI = 25;
	public static final Integer AME_DECLARATION_LIST_TO_BOARD_MEMEBER_URI = 26;
	public static final Integer AME_DECLARATION_LIST_TO_AME_URI = 27;
	public static final Integer AME_DECLARATION_LIST_TO_AME_PO = 124;

	// ========================URI-AME_DECLARATION_DATA_CHECK===============================//
	public static final Integer AME_DECLARATION_DATA_CHECK_TO_DEALING_HAND_URI = 28;
	public static final Integer AME_DECLARATION_DATA_CHECK_BOARD_MEMEBER_URI = 29;
	public static final Integer AME_DECLARATION_DATA_CHECK_AME_URI = 30;

	// ========================URI-AME_DECLARATION_DATA_CHECK===============================//
	public static final Integer PENDING_FOR_CHECKUP_UPOAD_DEALINGHAND_URI = 31;
	public static final Integer PENDING_FOR_CHECKUP_UPOAD_BOARD_MEMBER_URI = 32;
	public static final Integer PENDING_FOR_CHECKUP_UPOAD_AME_URI = 33;
	public static final Integer PENDING_FOR_CHECKUP_UPOAD_AME_PO = 127;

	// ========================//
	// ========================URI-AME_DECLARATION_DATA_CHECK===============================//
	public static final Integer APPLICATION_UNDER_PROCESS_DEALING_HAND_URI = 34;
	public static final Integer APPLICATION_UNDER_PROCESS_BOARD_MEMEBER_URI = 35;
	public static final Integer APPLICATION_UNDER_PROCESS_AMA_URI = 36;
	public static final Integer APPLICATION_UNDER_PROCESS_PO_URI = 129;
	public static final Integer APPLICATION_UNDER_PROCESS_AA_URI = 210;

	// ========================//
	// ========================URI-AME_DECLARATION_DATA_CHECK===============================//
	public static final int APPLICATION_UNDER_PROCESS_FILL_REPORT_DEALING_HAND_URI = 37;
	public static final int APPLICATION_UNDER_PROCESS_FILL_REPORT_BOARD_MEMBER_URI = 38;
	public static final int APPLICATION_UNDER_PROCESS_FILL_REPORT_AME_URI = 39;
	public static final int APPLICATION_UNDER_PROCESS_FILL_REPORT_PO_URI = 131;

	// ===============================================view -user -role
	// -profile===========================================//

	public static final Integer URI_FOR_VIEW_USER_ROLE_DETAILS_BOARD_DETAILING_AUTHORITY = 40;
	public static final Integer URI_FOR_VIEW_USER_ROLE_DETAILS_SUPER_ADMIN = 41;
	public static final Integer URI_FOR_VIEW_USER_ROLE_DETAILS_CAPF_ADMIN = 42;
	public static final Integer URI_FOR_VIEW_USER_ROLE_DETAILS_FORCE_ADMIN = 43;
	public static final Integer URI_FOR_VIEW_USER_ROLE_DETAILS_UNIT_ADMIN = 43;

	// ===========================================manage-existing-user========================================================//
	public static final Integer URI_FOR_MANAGING_EXISTING_USER_SUPER_ADMIN = 45;
	public static final Integer URI_FOR_MANAGING_EXISTING_USER_CAPF_ADMIN = 46;
	public static final Integer URI_FOR_MANAGING_EXISTING_USER_FORCE_ADMIN = 47;
	public static final Integer URI_FOR_MANAGING_EXISTING_USER_UNIT_ADMIN = 47;
	public static final Integer URI_FOR_MANAGING_EXISTING_USER_BDA_ADMIN = 49;

	// ===========================================role-code-name===============================================================//

	public static final String ROLE_NAME_SP_ADMIN = "spu";
	public static final String ROLE_NAME_CAPF_AD = "capfad";
	public static final String ROLE_NAME_FORCE_AD = "fad";
	public static final String ROLE_NAME_UNIT_AD = "uad";
	public static final String ROLE_NAME_BDA_AD = "bda";
	public static final String ROLE_NAME_BM_AD = "bm";
	public static final String ROLE_NAME_AMA_AD = "ama";
	public static final String ROLE_NAME_AMA_PO = "po";
	public static final String ROLE_NAME_DHM = "dhm";
	public static final String ROLE_NAME_RO = "ro";
	public static final String ROLE_NAME_AA = "AA";
	public static final String ROLE_NAME_CHO = "cho";

	public static final Integer ROLE_ADMIN_FLAG_YES = 1;
	public static final Integer ROLE_ADMIN_FLAG_NO = 0;

	public static final Integer ACTIVE_FLAG_NO = 0;
	public static final Integer ACTIVE_FLAG_YES = 1;

	public static final Integer AME_NORMAL_CLOSE = 1;
	public static final Integer AME_NORMAL_IN_PROCCESS = 0;
	public static final Integer AME_FINAL_FLAG_UPLOAD_COMPLETED = 1;
	public static final Integer AME_FINAL_FLAG_UPLOAD_PENDING = 0;

	// ==========================================assign-role-to-user======================================================//

	public static final Integer URI_FOR_ASSIGNING_NEW_ROLE_TO_USER_BY_BDA = 50;
	public static final Integer URI_FOR_ASSIGNING_NEW_ROLE_TO_USER_BY_USER_SUPER_ADMIN = 51;
	public static final Integer URI_FOR_ASSIGNING_NEW_ROLE_TO_CAPF_ADMIN = 52;
	public static final Integer URI_FOR_ASSIGNING_NEW_ROLE_TO_USER_BY_FORCE_ADMIN = 53;
	public static final Integer URI_FOR_ASSIGNING_NEW_ROLE_TO_USER_BY_UNIT_ADMIN = 54;

	// ================================ShapeDropDownCode================================================//

	public static final String SHAPE_TYPE_CODE_FOR_PSYHCOLIGY = "s";
	public static final String SHAPE_TYPE_CODE_FOR_HEARING = "h";
	public static final String SHAPE_TYPE_CODE_FOR_PHYSICAL = "p";
	public static final String SHAPE_TYPE_CODE_FOR_APPENDAGES = "a";
	public static final String SHAPE_TYPE_CODE_FOR_EYE = "e";
	public static final String SHAPE_TYPE_CODE_FOR_GYNO = "g";

	// ==========================================physical Form Display
	// Form==============================//
	public static final int PHYSICAL_MEASURMENT_MEDICAL_DEALING_HAND_URI = 53;
	public static final int PHYSICAL_MEASURMENT_BOARD_MEMBER_URI = 54;
	public static final int PHYSICAL_MEASURMENT_AMA_URI = 52;
	public static final int PHYSICAL_MEASURMENT_PO_URI = 132;

//==================================PSYCHOLOGICALASSESSMENTASLAIDDOWN==========================================//

	public static final int PSYCHOLOGICAL_ASSESSMENT_AS_LAID_DOWN_DEALING_HAND_URI = 55;
	public static final int PSYCHOLOGICAL_ASSESSMENT_AS_LAID_DOWN_BOARD_MEMBER_URI = 56;
	public static final int PSYCHOLOGICAL_ASSESSMENT_AS_LAID_DOWN_AMA_URI = 57;
	public static final int PSYCHOLOGICAL_ASSESSMENT_AS_LAID_DOWN_PO_URI = 133;

//==========================================hearing =======================================================================//

	public static final int HEARING_DEALING_HAND_URI = 58;
	public static final int HEARING_BOARD_MEMBER_URI = 59;
	public static final int HEARING_DOWN_AMA_URI = 60;
	public static final int HEARING_DOWN_PO_URI = 134;
	// ===========================================//
	public static final int APPENDAGES_DEALING_HAND_URI = 61;
	public static final int APPENDAGES_BOARD_MEMBER_URI = 62;
	public static final int APPENDAGES_DOWN_AMA_URI = 63;
	public static final int APPENDAGES_DOWN_PO_URI = 135;
	// ===============================EYE=========================================//

	public static final int EYE_DEALING_HAND_URI = 64;
	public static final int EYE_BOARD_MEMBER_URI = 65;
	public static final int EYE_DOWN_AMA_URI = 66;
	public static final int EYE_DOWN_PO_URI = 136;

	// ================================GENERAL_EXAMINATION_DEALING_HAND_URI==============================

	public static final int GENERAL_EXAMINATION_DEALING_HAND_URI = 67;
	public static final int GENERAL_EXAMINATION_BOARD_MEMBER_URI = 68;
	public static final int GENERAL_EXAMINATION_DOWN_AMA_URI = 69;
	public static final int GENERAL_EXAMINATION_DOWN_PO_URI = 137;

	// =========================CNS_CNM_DEALING_HAND_URI========================================//

	public static final int CNS_CNM_DEALING_HAND_URI = 70;
	public static final int CNS_CNM_BOARD_MEMBER_URI = 71;
	public static final int CNS_CNM_DOWN_AMA_URI = 72;
	public static final int CNS_CNM_DOWN_PO_URI = 138;

	// ==========================ABDOMIN_DEALING_HAND_URI=======================================//
	public static final int ABDOMIN_DEALING_HAND_URI = 73;
	public static final int ABDOMIN_BOARD_MEMBER_URI = 74;
	public static final int ABDOMIN_DOWN_AMA_URI = 75;
	public static final int ABDOMIN_DOWN_PO_URI = 139;

	// ========================PHYSICAL_MEASURMENT_VIEW_DEALING_HAND_URI==================================//

	public static final int PHYSICAL_MEASURMENT_VIEW_DEALING_HAND_URI = 76;
	public static final int PHYSICAL_MEASURMENT_VIEW_BOARD_MEMBER_URI = 77;
	public static final int PHYSICAL_MEASURMENT_VIEW_DOWN_AMA_URI = 78;
	public static final int PHYSICAL_MEASURMENT_VIEW_DOWN_PO_URI = 143;

	// ================================PSYCHOLOGY_VIEW_DEALING_HAND_URI=====================================
	public static final int PSYCHOLOGY_VIEW_DEALING_HAND_URI = 79;
	public static final int PSYCHOLOGY_VIEW_BOARD_MEMBER_URI = 80;
	public static final int PSYCHOLOGY_VIEW_DOWN_AMA_URI = 81;
	public static final int PSYCHOLOGY_VIEW_DOWN_PO_URI = 144;

	// ===============================HEARING===========================================//

	public static final int HEARING_VIEW_DEALING_HAND_URI = 82;
	public static final int HEARING_VIEW_BOARD_MEMBER_URI = 83;
	public static final int HEARING_VIEW_DOWN_AMA_URI = 84;
	public static final int HEARING_VIEW_DOWN_PO_URI = 145;

	// =======================General_Examination_VIEW_DEALING_HAND_URI=========================================//

	public static final int General_Examination_VIEW_DEALING_HAND_URI = 85;
	public static final int General_Examination_VIEW_BOARD_MEMBER_URI = 86;
	public static final int General_Examination_VIEW_DOWN_AMA_URI = 87;
	public static final int General_Examination_VIEW_DOWN_PO_URI = 146;

	// ==============================CNS_REFELEX=====================================================//
	public static final int CNS_REFELEX_VIEW_DEALING_HAND_URI = 88;
	public static final int CNS_REFELEX_VIEW_BOARD_MEMBER_URI = 89;
	public static final int CNS_REFELEX_VIEW_DOWN_AMA_URI = 90;
	public static final int CNS_REFELEX_VIEW_DOWN_PO_URI = 147;

	// ==============================ABDOMIN_AND_RESPIRATORY_VIEW===================================================//

	public static final int ABDOMIN_AND_RESPIRATORY_VIEW_DEALING_HAND_URI = 91;
	public static final int ABDOMIN_AND_RESPIRATORY_VIEW_BOARD_MEMBER_URI = 92;
	public static final int ABDOMIN_AND_RESPIRATORY_VIEW_DOWN_AMA_URI = 93;
	public static final int ABDOMIN_AND_RESPIRATORY_VIEW_DOWN_PO_URI = 148;

	// =========================APPENDAGES_VIEW======================================//

	public static final int APPENDAGES_VIEW_DEALING_HAND_URI = 94;
	public static final int APPENDAGES_VIEW_BOARD_MEMBER_URI = 95;
	public static final int APPENDAGES_VIEW_DOWN_AMA_URI = 96;
	public static final int APPENDAGES_VIEW_DOWN_PO_URI = 149;

	// ============================EYE_FACTOR_VIEW=================================//

	public static final int EYE_FACTOR_VIEW_DEALING_HAND_URI = 97;
	public static final int EYE_FACTOR_VIEW_BOARD_MEMBER_URI = 98;
	public static final int EYE_FACTOR_VIEW_DOWN_AMA_URI = 99;
	public static final int EYE_FACTOR_VIEW_DOWN_PO_URI = 150;

	// =======================INVESTIGATION_DEALING_HAND_URI=======================//

	public static final int INVESTIGATION_DEALING_HAND_URI = 100;
	public static final int INVESTIGATION_BOARD_MEMBER_URI = 101;
	public static final int INVESTIGATION_DOWN_AMA_URI = 102;
	public static final int INVESTIGATION_DOWN_PO_URI = 140;

	// ============================FINAL_DEATILS======================================//

	public static final int FINAL_DEATILS_DEALING_HAND_URI = 103;
	public static final int FINAL_DEATILS_BOARD_MEMBER_URI = 104;
	public static final int FINAL_DEATILS_AMA_URI = 105;
	public static final int FINAL_DEATILS_PO_URI = 141;

	// ==================================PRINT_AME_DECLARATION======================================//
	public static final int PRINT_AME_DECLARATION_DEALING_HAND_URI = 106;
	public static final int PRINT_AME_DECLARATION_BOARD_MEMBER_URI = 107;
	public static final int PRINT_AME_DECLARATION_AMA_URI = 108;

	// =======================GYNECOLOGY_FORM_DEALING_HAND_URI============================================//
	public static final int GYNECOLOGY_FORM_DEALING_HAND_URI = 109;
	public static final int GYNECOLOGY_FORM__BOARD_MEMBER_URI = 110;
	public static final int GYNECOLOGY_FORM__AMA_URI = 111;
	public static final int GYNECOLOGY_FORM__PO_URI =300;
	
	

//=================================GYNECOLOGY_VIEW============================//
	public static final int GYNECOLOGY_VIEW_DEALING_HAND_URI = 112;
	public static final int GYNECOLOGY_VIEW__BOARD_MEMBER_URI = 113;
	public static final int GYNECOLOGY_VIEW__AMA_URI = 114;
	public static final int GYNECOLOGY_VIEW__PO_URI = 301;

	// =========================declaration Upload
	// file===============================//
	public static final int UPLOAD_DECLARATION_FROM_DAM = 115;
	public static final int UPLOAD_DECLARATION_FROM_BM = 116;
	public static final int UPLOAD_DECLARATION_FROM_AMA = 117;
	public static final int UPLOAD_DECLARATION_FROM_PO = 128;

	// =========================declaration Upload
	// file===============================//
	public static final int UPLOAD_DECLARATION_FROM_FINAL_DAM = 118;
	public static final int UPLOAD_DECLARATION_FROM_FINAL_BM = 119;
	public static final int UPLOAD_DECLARATION_FROM_FINAL_AMA = 120;
	public static final int UPLOAD_DECLARATION_FROM_FINAL_PO = 142;

	// =========================declaration Upload
	// file===============================//
	public static final int UPLOAD_MEDICAL_CHECK_UP_PAGE_DH = 121;
	public static final int UPLOAD_MEDICAL_CHECK_UP_PAGE_BM = 122;
	public static final int UPLOAD_MEDICAL_CHECK_UP_PAGE_AMA = 123;
	public static final int UPLOAD_MEDICAL_CHECK_UP_PAGE_PO = 130;

	// ================= AME_RESULT_FOR_========//
	public static final int AME_RESULT_FOR_DEALING_HAND_URI = 124;
	public static final int AME_RESULT_FOR_BOARD_MEMEBER_URI = 125;
	public static final int AME_RESULT_FOR_AMA_URI = 126;

	// ======================================================================================================//
	public final static String APPENDAGES_UPPER_CATEGORY = "u";
	public final static String APPENDAGES_LOWER_CATEGORY = "l";
	public final static String APPENDAGES_SPINE_CATEGORY = "s";

	// ============================================================================//
	public final static int USER_ACCOUNT_LOCK = 0;
	public final static int USER_ACCOUNT_UNLOCK = 1;

	public final static String LOGIN_SUCCESSFULL = "LOGIN_SUCCESSFULL";
	public final static String LOGOUT_SUCCESSFULL = "LOGOUT_SUCCESSFULL";
	public final static String LOGIN_ATTEMPT_FAIL = "LOGIN_ATTEMPT_FAIL";

	public final static String SHA_KEY_256 = "8uc.%|NT=_0IKG1Aa{U83TY8yk+Kt4t}";

	public static final Integer GET_ID_CODE = 5;
	public static final Integer GET_HASH_PASSWORD = 15;

	public static final Integer REVIEW_PENDING = 1;
	public static final Integer REVIEW_COMPLETED = 2;

	public static final String NO_RECORD_AVAILABLE = "NO RECORD AVAILABLE";
	public static final String DATA_LOADED_SUCCESSFULLY = "DATA LOADED SUCCESSFULLY !";

	public static final Integer REVIEW_PENDING_RESCHEDULE = 1;
	public static final Integer REVIEW_COMPLETED_RESCHEDULE = 0;

	public static final Integer GAZETTED_FLAG = 1;
	public static final Integer NON_GAZETTED_FLAG = 0;
	
	//================code for go process=================//
	public static final int AME_FILLED_AND_SUBMITTED_BY_BOARD_MEMBER_WITHOUT_ESIGN = 1;
	public static final int E_SIGNED_BY_BOARD_MEMBER=5;
	public static final int AME_FILLED_AND_SUBMITTED_BY_PO_WITHOUT_ESIGN=10;
	public static final int E_SIGNED_BY_PO=15;
	public static final int APPROVED_AND_E_SIGNED_BY_APPROVING_AUTHORITY=20;
	public static final int NOT_APPROVED_BY_APPROVING_AUTHORITY_E_SIGNED=25;
	// =======================================================================================//

	
	public static final int OPEN=0;
	public static final int AME_COMPLETED_AND_CLOSED=1;
	public static final int AME_COMPLETED_AND_CLOSED_BY_BM=5;
	public static final int AME_COMPLETED_AND_CLOSED_BY_BM_AND_PO=10;
	public static final int AME_COMPLETED_AND_CLOSED_BY_BM_PO_AND_AA=15;
	
	
	//=================esign Code========================//
	
	public static final int E_SIGN_PENDING=0;
	public static final int E_SIGN_COMPLETED_BY_AMA_=1;
	public static final int E_SIGN_COMPLETED_BY_BM=5;
	public static final int E_SIGN_COMPLETED_BY_BM_AND_PO=10;
	public static final int E_SIGN_COMPLETED_BY_BM_PO_AND_AA=15;
	
	public static final int ACTION_NOT_REQUIRED_NOW=4;

//	
//	  public static final String AME_FINAL_REPORT_PATH =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/finalReport"; public static
//	  final String FILE_PATH =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/ImageAndFile"; public static
//	  final String File_PATH_NOTIFICATION =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/Notification"; public static
//	  final String File_PATH_IMAGE_CAPTURE =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/BaseFolder"; public static
//	  final String File_PATH_USER_ROLE_ORDER =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/UserRoleOrder"; public static
//	  final String File_PATH_INVESTIGATION_FILE_UPLOAD =
//	  "/Users/brijeshkumar/Desktop/FileUploadFolder/InvestigationReport"; public
//	  static final String DEFAULT_FILE_PATH_NAME = "/var/ame/default.pdf";
	 
	
	
	//===== Gaurav===========
	public static final int APPLICATION_UNDER_PENDING_STATUS_AME_URI = 201;
}
