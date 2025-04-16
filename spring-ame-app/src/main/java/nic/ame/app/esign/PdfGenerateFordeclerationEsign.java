package nic.ame.app.esign;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RankMasterRepository;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.ama.service.AmaDealingHandService;
import nic.ame.app.master.dto.AmeDecleartionDto;
import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeFinalDetailService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeAppointmentService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.user.serviceImpl.DecleartionPdfService;

@Service
public class PdfGenerateFordeclerationEsign {

    @Autowired
    private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;

    @Autowired
    private AmeDeclarationRepository ameDeclarationRepository;

    @Autowired
    private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;

    @Autowired
    private RankRepo rankRepo;

    @Autowired
    private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

    @Autowired
    AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;

    @Autowired
    ForcePersonnelService forcePersonnelService;

    @Autowired
    private RefForceService refForceService;

    @Autowired
    private DeclerationPdfServiceEsign decleartionPdfService;

    public Map<String, Object> generatePdfForDecleartion(String ameId, String forcePersonelId, HttpSession httpSession,
            HttpServletResponse response) {

        Map<String, Object> pdfResponseMap = new HashMap<>();
        ByteArrayOutputStream outputStreamObject = new ByteArrayOutputStream();
        String rank;

        // String gazettedNonGazettedFlag = (String)
        // httpSession.getAttribute("gazettedNonGazettedFlag");
        // model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
        if (!ameId.isEmpty()) {
            Optional<AmeDeclarationIndividualDetails> optional = ameDeclarationIndividualDetailsRepo
                    .findByAmeId(ameId);
            Optional<AmeDeclarationIndividualModel> optional2 = ameDeclarationRepository.findByForcePersonalIdData(forcePersonelId,ameId);
            AmeDeclarationIndividualDetails details = new AmeDeclarationIndividualDetails();
            AmeDeclarationIndividualModel individualModel = new AmeDeclarationIndividualModel();
            Optional<AmeAppointmentDetails> appointmentDetails = ameAssessmentServicePart_2
                    .ameAppointmentDetails(ameId);
            ForcePersonnel forcePersonal = ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonelId);

            int forceNo = forcePersonal.getForceNo();
            String unit = forcePersonal.getUnit();

            Date appointmentDate = null;
            String date = null;

            String amePlace = ameApplicationFlowStatusRepo.getAmePlaceByBoardIdAndAmeId(ameId.trim());

            if (!appointmentDetails.isEmpty()) {
                appointmentDate = appointmentDetails.get().getAppointmentDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date = simpleDateFormat.format(appointmentDate);
            }
            individualModel = optional2.get();
            rank = rankRepo.findById(forcePersonal.getRank()).get().getRankFullName();
            details = optional.get();
            unit = refForceService.getUnitNameByUnitId(forceNo, unit);

            AmeDecleartionDto ame = new AmeDecleartionDto();
            ame.setAmeDeclarationIndividualDetails(details);
            ame.setAmeDeclarationIndividualModel(individualModel);
            // ame.setAppointmentDetails(appointmentDetails.get());
            ame.setForcePersonal(forcePersonal);
            ame.setAmePlace(amePlace);
            ame.setRank(rank);
            ame.setUnit(unit);
            ame.setAmeId(ameId);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.format(new Date());
          //  response.setContentType("application/pdf");
            String filename = "AmeDeclaration-"
                    + forcePersonal.getForceId() + "-" 
                    + forcePersonal.getName().replace(" ", "-") 
                    + "-" + formatter.format(new Date()) + ".pdf";

            outputStreamObject = decleartionPdfService.generatePdfForEsign(outputStreamObject, ame);
            pdfResponseMap.put("fileOutputStreamObject", outputStreamObject);
            pdfResponseMap.put("fileName", filename);

        }
        return pdfResponseMap;

    }
}
