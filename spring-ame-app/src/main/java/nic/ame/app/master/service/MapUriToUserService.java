package nic.ame.app.master.service;


public interface MapUriToUserService {

	public String getUriForShowingAmeDeclarationFormToUser(int rCode);

	public String getUriForShowingAmeDeclarationListToUser(int rCode);

	public String getUriAfterAMEDeclarationDataCheck(int rCode);

	public String getUriForPendingAndUpLoad(int rCode);

	public String getUriForApplicationUnderProcess(int rCode) throws Exception;

	public String getUriForApplicationUnderProcessAndFillReport(int rCode);
	
	public String getUriForShowingAmeDeclarationListToUserMedicalRole(int rcodeMedical);

	public String getUriForViewUserRoleDetails(int rCode);

	// ======================================================manage-existing-role===================================================//
	public String getUriManageExistingRole(int rCode);

	

	
	
	
	//========================form index page ==========================================================//
	public String getUriForPhysiacalMesurmentFormToUser(int rCode);

	public String getUriForPhychologicalAssessmentAsLaidDown(int rCode);

	public String getUriForHearing(int rCode);

	public String getUriForappendages(int rCode);

	public String getUriForEye(int rCode);

	public String getUriForGeneralExaminationUser(int rCode);

	public String getUriForCNSCNMReflex(int rCode);

	public String getUriForAbdomenUser(int rCode);
	
	
	
	//=====================================view service===============================//

	

	public String getUriForPhysicalMeasurementViewForm(int rCode);

	public String getUriForPsychologyViewForm(int rCode);

	public String getUriForHearingViewForm(int rCode);

	public String getUriForGeneralExaminationViewForm(int rCode);

	public String getUriForCNSReflexesViewForm(int rCode);

	public String getUriForAbdominAndRespiratoryViewForm(int rCode);

	public String getUriForAppendagesViewForm(int rCode);

	public String getUriForEyeFactotrViewForm(int rCode);

	public String getUriForInvestigationUser(int rCode);

	public String getUriForFinalDetailsProcess(int rCode);

	public String getUriForPrintAmeDeclarationFormToUser(int rCode);

	public String getUriForGynecology(int rCode);

	public String getUriForgynecologyViewForm(int rCode);

	public String getUriForUploadDeclarationForm(int rCode);

	public String getUriForUploadDeclarationFinal(int rCode);

	public String getUriForCheckListForCandidate(int rCode);

	public String getUriForAmeResltDisplay(int rCode);
	
	public String getUriForAmePendingStatusAma(int rCode);
	
}
