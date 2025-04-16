package nic.ame.app.esign;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.AmePdfFinalReportDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.go.AmeFinalReportBoardMemberDetailsGo;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportBoardMemberDetailsGoRepository;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.repository.AmeFinalReportBoardmemberDetailsRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.BloodSugarFRepository;
import nic.ame.app.master.repository.BloodSugarHbA1cRepository;
import nic.ame.app.master.repository.BloodSugarPPRepository;
import nic.ame.app.master.repository.BloodSugarRandomRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.user.serviceImpl.PdfGeneratorService;

@Service
public class PdfGenerateForEsign {

	Logger logger = LoggerFactory.getLogger(PdfGenerateForEsign.class);

	@Autowired
	private PdfGenerationServiceForEsign pdfGeneratorService;

	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	@Autowired
	private InvestigationMasterService investigationMasterService;
	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	@Autowired
	private ForcePersonalService forcePersonalService;

	@Autowired
	private FinalCategoryRemarkTempRepo finalCategoryRemarkTempRepo;
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	@Autowired
	private RefForceService refForceService;
	@Autowired
	private AmeFinalReportBoardmemberDetailsRepository ameFinalReportBoardmemberDetailsRepository;
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;
	@Autowired
	private BloodSugarFRepository bloodSugarFRepository;
	@Autowired
	private BloodSugarHbA1cRepository bloodSugarHbA1cRepository;
	@Autowired
	private BloodSugarPPRepository bloodSugarPPRepository;
	@Autowired
	private BloodSugarRandomRepository bloodSugarRandomRepository;

	@Autowired
	private ForcePersonnelService forcePersonnelService;
	@Autowired
	private AmeFinalReportBoardMemberDetailsGoRepository ameFinalReportBoardmemberDetailsGoRepository;

	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;

	public Map<String, Object> generatePdf(String ameId, String forcePersonelId, HttpSession httpSession,
			HttpServletResponse response) {

		ForcePersonnel boardMemberDetails = null;
		String forcePersonalIdBM = null;
		String forcePersonalIdPO = null;
		String forcePersonalIdAA = null;
		Map<String, Object> pdfResponseMap = new HashMap<>();
		ByteArrayOutputStream outputStreamObject = new ByteArrayOutputStream();
		// logger.info("ForcePersonnelId>>>>>>>pdf" + forcePersonelId + "ameId" +
		// ameId);

		MedExamDtoRequest examDtoRequest = new MedExamDtoRequest();
		AmePdfFinalReportDto amePdfFinalReportDto = new AmePdfFinalReportDto();
		AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);

		AmeFinalReportDetails ameFinalReportDetails = new AmeFinalReportDetails();
		AmeFinalReportDetailsGo ameFinalReportDetailsGo = new AmeFinalReportDetailsGo();
		Optional<ForcePersonnelDto> boardMemberDetailsOptional = java.util.Optional.empty();

		String boardForcePersonnelId, boardId = null;

		String loginInForcePersonnel = (String) httpSession.getAttribute("forcepersonalId");
		int rCodeMedical = (int) httpSession.getAttribute("rCodeMedical");

		String finalCategoryAwarded = null, remark = null;

		if (rCodeMedical == 3 || rCodeMedical == 4 || rCodeMedical == 15) {
			String roleName = null;
			if (rCodeMedical == 3)
				roleName = "mb1";
			if (rCodeMedical == 4)
				roleName = "mb2";

			Optional<AmeFinalReportDetailsGo> ameFinalReportDetailsOptional = ameFinalReportDetailsGoRepository
					.findByAmeId(ameId);
			List<AmeFinalReportBoardMemberDetailsGo> ameFinalReportBoardMemberDetailsGos = ameFinalReportBoardmemberDetailsGoRepository
					.findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(
							ameFinalReportDetailsOptional.get().getBoardId(), roleName, loginInForcePersonnel);
			for (AmeFinalReportBoardMemberDetailsGo ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetailsGos) {
				if (ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
					boardForcePersonnelId = ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
					boardId = ameFinalReportBoardMemberDetails2.getBoardId();
					boardMemberDetailsOptional = forcePersonnelService
							.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
					boardMemberDetails = ameAssessmentServicePart_1_impl.getforceForcePersonal(boardForcePersonnelId);

					break;
				}

			}
			if (ameFinalReportDetailsOptional.isPresent()) {
				ameFinalReportDetailsGo = ameFinalReportDetailsOptional.get();
				finalCategoryAwarded = ameFinalReportDetailsGo.getFinalCategoryAwarded();
				remark = ameFinalReportDetailsGo.getRemark();
			}

		} else {

			Optional<AmeFinalReportDetails> ameFinalReportDetailsOptional = ameFinalReportDetailsRepository
					.findByAmeId(ameId);
			List<AmeFinalReportBoardMemberDetails> ameFinalReportBoardMemberDetails = ameFinalReportBoardmemberDetailsRepository
					.findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(
							ameFinalReportDetailsOptional.get().getBoardId(), "ama", loginInForcePersonnel);

			for (AmeFinalReportBoardMemberDetails ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetails) {
				if (ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
					boardForcePersonnelId = ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
					boardId = ameFinalReportBoardMemberDetails2.getBoardId();
					boardMemberDetailsOptional = forcePersonnelService
							.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
					boardMemberDetails = ameAssessmentServicePart_1_impl.getforceForcePersonal(boardForcePersonnelId);

					break;
				}

			}

			if (ameFinalReportDetailsOptional.isPresent()) {
				ameFinalReportDetails = ameFinalReportDetailsOptional.get();
				finalCategoryAwarded = ameFinalReportDetails.getFinalCategoryAwarded();
				remark = ameFinalReportDetails.getRemark();
			}

		}

		List<FinalCategoryRemarkTemp> finalCategoryRemarkList = finalCategoryRemarkTempRepo.findByAmeId(ameId);

		examDtoRequest = ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);

		Map<String, Map<String, String>> investigationReportDtosList = investigationMasterService
				.findInvestigationReportByAmeId(ameId);

		investigationMasterService.findAllInvestigationReportByAmeId(ameId);

		InvestigationFinalReportDto investigationFinalReportDto = investigationMasterService
				.findAllInvestigationReportByAmeId(ameId);

		ForcePersonnelDto forcePersonalDetails = forcePersonalService.getForcePersonalDetails(forcePersonelId);

		ForcePersonnel forcePersonal = ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonelId);

		String boardMemberUnitName = refForceService.getUnitNameByUnitId(boardMemberDetails.getForceNo(),
				boardMemberDetails.getUnit());

		String boardMemberForceName = refForceService.getForceNameByForceId(boardMemberDetails.getForceNo());

		MedicalBoard medicalBoardMemberDetails = medicalBoardRepo.getByBoardId(boardId);

		String medicalBoardMemberPlace = refForceService.getUnitNameByUnitId(
				Integer.parseInt(medicalBoardMemberDetails.getBoardAtForceNo()), medicalBoardMemberDetails.getPlace());

		InvestigationDto investigationDto = new InvestigationDto();
		investigationDto = this.investigationMasterService.getReportView(ameId);
		List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps = new ArrayList<>();
		finalCategoryRemarkTemps = finalCategoryRemarkTempRepo.findByAmeId(ameId);

		Optional<BloodSugarF> bloodSugarF = this.bloodSugarFRepository.findByAmeId(ameId);
		Optional<BloodSugarPP> bloodSugarpp = this.bloodSugarPPRepository.findByAmeId(ameId);
		Optional<BloodSugarRandom> bloodSugarRandom = this.bloodSugarRandomRepository.findByAmeId(ameId);
		Optional<BloodSugarHbA1c> bloodSugarHbA1c = this.bloodSugarHbA1cRepository.findByAmeId(ameId);
		List<MedicalBoardMember> medicalBoardMember = this.medicalBoardMemberRepo.findByBoardId(boardId);

		forcePersonalIdBM = medicalBoardMember.stream()
				.filter(e -> e.getRoleName().equals("3") && e.getStatusCode() == (1))
				.map(MedicalBoardMember::getForcePersonalId).findFirst().orElse("DefaultId");
		forcePersonalIdAA = medicalBoardMember.stream().filter(e -> e.getRoleName().equals("15"))
				.map(MedicalBoardMember::getForcePersonalId).findFirst().orElse("DefaultId");
		forcePersonalIdPO = medicalBoardMember.stream().filter(e -> e.getRoleName().equals("2"))
				.map(MedicalBoardMember::getForcePersonalId).findFirst().orElse("DefaultId");

		Optional<ForcePersonnelDto> forcePersonnelBM = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalIdBM);
		Optional<ForcePersonnelDto> forcepersonnelPO = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalIdPO);
		Optional<ForcePersonnelDto> forcepersonnelAA = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalIdAA);
		amePdfFinalReportDto.setFinalCategoryAwarded(remark);
		amePdfFinalReportDto.setFinalCategoryAwarded(finalCategoryAwarded);
		amePdfFinalReportDto.setAmeFinalReportDetails(ameFinalReportDetails);
		amePdfFinalReportDto.setAmeMasterStatus(ameMasterStatus);
		amePdfFinalReportDto.setBoardMemberDetails(boardMemberDetails);
		amePdfFinalReportDto.setExamDtoRequest(examDtoRequest);
		amePdfFinalReportDto.setrCode(rCodeMedical);
		amePdfFinalReportDto.setForcePersonelDtoOptionalBM(forcePersonnelBM);
		amePdfFinalReportDto.setForcePersonelDtoOptionalAA(forcepersonnelAA);
		amePdfFinalReportDto.setForcePersonelDtoOptionalPO(forcepersonnelPO);
		amePdfFinalReportDto.setForcePersonalDetails(forcePersonalDetails);
		amePdfFinalReportDto.setInvestigationFinalReportDto(investigationFinalReportDto);
		amePdfFinalReportDto.setMedicalBoardMemberDetails(medicalBoardMemberDetails);
		amePdfFinalReportDto.setFinalCategoryRemarkList(finalCategoryRemarkList);
		amePdfFinalReportDto.setFinalCategoryRemarkTemps(finalCategoryRemarkTemps);
		amePdfFinalReportDto.setInvestigationDto(investigationDto);
		amePdfFinalReportDto.setBoardMemberUnitName(boardMemberUnitName);
		amePdfFinalReportDto.setBoardMemberForceName(boardMemberForceName);
		amePdfFinalReportDto.setMedicalBoardMemberPlace(medicalBoardMemberPlace);
		amePdfFinalReportDto.setForcePersonal(forcePersonal);
		amePdfFinalReportDto.setRemark(remark);
		amePdfFinalReportDto.setBloodSugarF(bloodSugarF);
		amePdfFinalReportDto.setBloodSugarHbA1c(bloodSugarHbA1c);
		amePdfFinalReportDto.setBloodSugarPP(bloodSugarpp);
		amePdfFinalReportDto.setBloodSugarRandom(bloodSugarRandom);
		logger.info("ForcePersonnelId 4>>>>>>>pdf" + forcePersonelId + "ameId" + ameId);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String filename = "AmeFinalReport-" + forcePersonalDetails.getForceId() + "-"
				+ forcePersonalDetails.getName().replace(" ", "") + "-" + formatter.format(new Date()) + ".pdf";
		outputStreamObject = pdfGeneratorService.generatePdfforEsign(outputStreamObject, amePdfFinalReportDto);
		pdfResponseMap.put("fileOutputStreamObject", outputStreamObject);
		pdfResponseMap.put("fileName", filename);

		return pdfResponseMap;

	}
}
