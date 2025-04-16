package nic.ame.app.esign;

import com.itextpdf.text.*;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.swing.text.StyleConstants.FontConstants;

import nic.ame.app.admin.model.Rank;
import nic.ame.app.admin.repository.RankMasterRepository;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.AmePdfFinalReportDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.model.ViralMakers;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.user.serviceImpl.PageNumberEventHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfGenerationServiceForEsign {

	@Autowired
	private RankRepo rankRepo;
	private static final Font BOLD = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

	public ByteArrayOutputStream generatePdfforEsign(OutputStream outputStream, AmePdfFinalReportDto reportDto) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			PageNumberEventHandler eventHelper = new PageNumberEventHandler();
			writer.setPageEvent(eventHelper);

			document.open();

			PdfPTable headerTable = new PdfPTable(1); // Single column for the logo
			headerTable.setWidthPercentage(100); // Set to full width

			// Load and add the right logo
			addLogoToHeader(headerTable, "/static/images/border-security-force-logo.png", Element.ALIGN_LEFT);

			// Add the header table to the document
			document.add(headerTable);
			LineSeparator separator = new LineSeparator();
			document.add(createITextParagraph2("Annual Medical Examination", BOLD, Element.ALIGN_CENTER));
			Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);

			document.add(createITextParagraph("CAPFs,NSG & AR", boldFont, Element.ALIGN_CENTER));
			separator.setLineColor(new BaseColor(0, 0, 0));

			separator.setLineWidth(1f);
			document.add(separator);

			PdfPTable ame = new PdfPTable(2);
			ame.setWidthPercentage(100);
			ame.setSpacingBefore(1f);
			ame.setSpacingAfter(10f);
			addameIdRows(ame, reportDto);
			document.add(ame);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			addPersonalDetailsRows(table, reportDto);
			document.add(table);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Physical Measurement", BOLD, Element.ALIGN_LEFT));
			PdfPTable physicalMeasurementTable = new PdfPTable(4);
			physicalMeasurementTable.setWidthPercentage(100);
			physicalMeasurementTable.setSpacingBefore(10f);
			physicalMeasurementTable.setSpacingAfter(20f);
			addPhysicalMeasurementRows(physicalMeasurementTable, reportDto);
			document.add(physicalMeasurementTable);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Hearing (H)", BOLD, Element.ALIGN_LEFT));
			PdfPTable hearing = new PdfPTable(4);
			hearing.setWidthPercentage(100);
			hearing.setSpacingBefore(10f);
			hearing.setSpacingAfter(20f);
			addhearingRows(hearing, reportDto);
			document.add(hearing);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Psychological Assessment As Laid Down (S)", BOLD, Element.ALIGN_LEFT));
			PdfPTable psychologicalAssessmentTable = new PdfPTable(10);
			psychologicalAssessmentTable.setWidthPercentage(100);
			psychologicalAssessmentTable.setSpacingBefore(20f);
			psychologicalAssessmentTable.setSpacingAfter(25f);
			addPsychologicalAssessmentRows(psychologicalAssessmentTable, reportDto);
			document.add(psychologicalAssessmentTable);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			// document.add(separator);
			document.newPage();

			document.add(createITextParagraph(" Appendages (A)", BOLD, Element.ALIGN_LEFT));
			PdfPTable appendages = new PdfPTable(4);
			appendages.setWidthPercentage(100);
			appendages.setSpacingBefore(20f);
			appendages.setSpacingAfter(25f);
			addappendagesRows(appendages, reportDto);
			document.add(appendages);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);

			document.add(createITextParagraph("Physical(P) ", BOLD, Element.ALIGN_LEFT));
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);
			document.add(createITextParagraph("General Examination ", BOLD, Element.ALIGN_LEFT));
			PdfPTable generalExam = new PdfPTable(4);
			generalExam.setWidthPercentage(100);
			generalExam.setSpacingBefore(10f);
			generalExam.setSpacingAfter(8f);
			addgeneralAssessmentRows(generalExam, reportDto);
			document.add(generalExam);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Central Nervous System and Cranial Nerves ", BOLD, Element.ALIGN_LEFT));
			PdfPTable nervousSystem = new PdfPTable(4);

			nervousSystem.setWidthPercentage(100);
			nervousSystem.setSpacingBefore(10f);
			nervousSystem.setSpacingAfter(10f);
			addnervousSystemAssessmentRows(nervousSystem, reportDto);
			document.add(nervousSystem);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Abdomen and Respiratory Systems ", BOLD, Element.ALIGN_LEFT));
			PdfPTable Abdomen = new PdfPTable(4);
			Abdomen.setWidthPercentage(100);
			Abdomen.setSpacingBefore(10f);
			Abdomen.setSpacingAfter(5f);
			addAbdomenAssessmentRows(Abdomen, reportDto);
			document.add(Abdomen);

			document.add(createITextParagraph("Eye Factor(E) ", BOLD, Element.ALIGN_LEFT));
			PdfPTable eyefactor = new PdfPTable(5);
			eyefactor.setWidthPercentage(100);
			eyefactor.setSpacingBefore(10f);
			eyefactor.setSpacingAfter(10f);
			addeyefactorAssessmentRows(eyefactor, reportDto);
			document.add(eyefactor);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			if (reportDto.getForcePersonalDetails().getGender() == "Female") {
				if (reportDto.getExamDtoRequest().getGynaeAndObsFemale() != null) {
					document.add(createITextParagraph("Gynecology & Obstetrics (Female)", BOLD, Element.ALIGN_LEFT));
					PdfPTable gyno = new PdfPTable(4);
					gyno.setWidthPercentage(100);
					gyno.setSpacingBefore(10f);
					gyno.setSpacingAfter(10f);
					addgynoAssesmentRow(gyno, reportDto);
					document.add(gyno);
					separator.setLineColor(new BaseColor(0, 0, 0));
					separator.setLineWidth(1f);
					document.add(separator);
				}
			}

			document.newPage();
			document.add(createITextParagraph("Investigation Report View", BOLD, Element.ALIGN_LEFT));
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			LiverFunctionTest lft = reportDto.getInvestigationDto().getLiverFunctionTest();
			if (lft != null && hasLFTData(lft)) {
				document.add(createITextParagraph("Liver Function Test", BOLD, Element.ALIGN_LEFT));
				PdfPTable liverFunctionTest = new PdfPTable(4);
				liverFunctionTest.setWidthPercentage(100);
				liverFunctionTest.setSpacingBefore(10f);
				liverFunctionTest.setSpacingAfter(10f);
				addlftAssessmentRows(liverFunctionTest, reportDto);
				document.add(liverFunctionTest);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			KidneyFunctionTest kftdata = reportDto.getInvestigationDto().getKft();
			if (kftdata != null && hasKFTData(kftdata)) {

				document.add(createITextParagraph("Kidney Function Test", BOLD, Element.ALIGN_LEFT));
				PdfPTable kft = new PdfPTable(4);
				kft.setWidthPercentage(100);
				kft.setSpacingBefore(10f);
				kft.setSpacingAfter(10f);
				addkftAssessmentRows(kft, reportDto);
				document.add(kft);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			
			CompleteBloodCount cbcData = reportDto.getInvestigationDto().getCompleteBloodCountCBC();
			if (cbcData.getAmeId() != null) {
				document.add(createITextParagraph("Complete Blood Count (CBC)", BOLD, Element.ALIGN_LEFT));
				PdfPTable cbc = new PdfPTable(4);
				cbc.setWidthPercentage(100);
				cbc.setSpacingBefore(10f);
				cbc.setSpacingAfter(10f);
				addcbcAssesmentRow(cbc, reportDto);
				document.add(cbc);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			if (reportDto.getInvestigationDto().getUrineTestPhysicalMicroscopic().getAmeId() != null) {
				document.add(createITextParagraph("Urine Physical/Urine Microscope", BOLD, Element.ALIGN_LEFT));
				PdfPTable urine = new PdfPTable(4);
				urine.setWidthPercentage(100);
				urine.setSpacingBefore(10f);
				urine.setSpacingAfter(10f);
				addurineAssesmentRow(urine, reportDto);
				document.add(urine);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}
			ThyroidProfile tps = reportDto.getInvestigationDto().getThyroidProfile();
			if (tps != null && hasThyroidProfileData(tps)) {
				document.add(createITextParagraph("Thyroid Profile", BOLD, Element.ALIGN_LEFT));
				PdfPTable tp = new PdfPTable(4);
				tp.setWidthPercentage(100);
				tp.setSpacingBefore(10f);
				tp.setSpacingAfter(10f);
				addtpAssesmentRow(tp, reportDto);
				document.add(tp);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			ViralMakers viral = reportDto.getInvestigationDto().getViralMakers();
			if (viral != null && hasviralData(viral)) {
				document.add(createITextParagraph("Viral Markers", BOLD, Element.ALIGN_LEFT));
				PdfPTable vm = new PdfPTable(4);
				vm.setWidthPercentage(100);
				vm.setSpacingBefore(10f);
				vm.setSpacingAfter(10f);
				addvmAssesmentRow(vm, reportDto);
				document.add(vm);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}
			if (!reportDto.getBloodSugarF().isEmpty() || !reportDto.getBloodSugarHbA1c().isEmpty()
					|| !reportDto.getBloodSugarPP().isEmpty() || !reportDto.getBloodSugarRandom().isEmpty()) {
				document.add(createITextParagraph("Blood Sugar", BOLD, Element.ALIGN_LEFT));
				PdfPTable tpss = new PdfPTable(4);
				tpss.setWidthPercentage(100);
				tpss.setSpacingBefore(10f);
				tpss.setSpacingAfter(10f);
				addbloodsugarAssesmentRow(tpss, reportDto);
				document.add(tpss);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}
			if (reportDto.getInvestigationDto().getLipid().getAmeId() != null) {
				document.add(createITextParagraph("Lipid Profile", BOLD, Element.ALIGN_LEFT));
				PdfPTable lipid = new PdfPTable(4);
				lipid.setWidthPercentage(100);
				lipid.setSpacingBefore(10f);
				lipid.setSpacingAfter(10f);
				addlipidAssesmentRow(lipid, reportDto);
				document.add(lipid);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			if (reportDto.getInvestigationDto().getOthers() != null) {
				document.add(createITextParagraph("Other Test", BOLD, Element.ALIGN_LEFT));
				PdfPTable other = new PdfPTable(4);
				other.setWidthPercentage(100);
				other.setSpacingBefore(10f);
				other.setSpacingAfter(10f);
				addotherAssesmentRow(other, reportDto);
				document.add(other);
				separator.setLineColor(new BaseColor(0, 0, 0));
				separator.setLineWidth(1f);
				document.add(separator);
			}

			Paragraph space = new Paragraph();
			space.setSpacingBefore(30f); // Adjust this value for the desired space
			document.add(space);

			document.add(createITextParagraph("Categorization", BOLD, Element.ALIGN_LEFT));
			PdfPTable category = new PdfPTable(4);
			category.setWidthPercentage(100);
			category.setSpacingBefore(10f);
			category.setSpacingAfter(10f);
			addcategoryAssesmentRow(category, reportDto);
			document.add(category);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.add(createITextParagraph("Final Category Remark", BOLD, Element.ALIGN_LEFT));
			PdfPTable finalCategory = new PdfPTable(4);
			finalCategory.setWidthPercentage(100);
			finalCategory.setSpacingBefore(10f);
			finalCategory.setSpacingAfter(10f);
			addfinalCategoryRow(finalCategory, reportDto);
			document.add(finalCategory);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);
			document.newPage();

			document.add(createITextParagraph("", BOLD, Element.ALIGN_LEFT));
			PdfPTable blank = new PdfPTable(4);
			blank.setWidthPercentage(100);
			blank.setSpacingBefore(10f);
			blank.setSpacingAfter(10f);
			addfinalCategoryRowdata(blank, reportDto);
			document.add(blank);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			PdfPTable reporting = new PdfPTable(4);
			reporting.setWidthPercentage(100);
			reporting.setSpacingBefore(50f);
			reporting.setSpacingAfter(10f);
			addreportingRow(reporting, reportDto);
			document.add(reporting);
//			separator.setLineColor(new BaseColor(0, 0, 0));
//			separator.setLineWidth(1f);
//			document.add(separator);

//			PdfPTable reportingPO = new PdfPTable(4);
//			reportingPO.setWidthPercentage(100);
//			reportingPO.setSpacingBefore(50f);
//			reportingPO.setSpacingAfter(10f);
//			addreportingRowPO(reportingPO, reportDto);
//			document.add(reportingPO);
//			separator.setLineColor(new BaseColor(0, 0, 0));
//			separator.setLineWidth(1f);
//			document.add(separator);

			PdfPTable reportingAA = new PdfPTable(4);
			reportingAA.setWidthPercentage(100);
			reportingAA.setSpacingBefore(50f);
			reportingAA.setSpacingAfter(10f);
			addreportingRowAA(reportingAA, reportDto);
			document.add(reportingAA);
			separator.setLineColor(new BaseColor(0, 0, 0));
			separator.setLineWidth(1f);
			document.add(separator);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos;
	}

	private Paragraph createITextParagraph(String text, Font font, int alignment) {
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(alignment);
		paragraph.setSpacingAfter(15f);
		return paragraph;
	}

	private Paragraph createITextParagraph2(String text, Font font, int alignment) {
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(alignment);
		paragraph.setSpacingAfter(2f);
		return paragraph;
	}

	private static void addPersonalDetailsRows(PdfPTable table, AmePdfFinalReportDto reportDto) {

		boolean alternate = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		String irlaNumber = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getForceId()
				: "";
		String name = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getName()
				: "";
		String rank = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getRank()
				: "";
		String forceName = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getForceName()
				: "";
		String unitName = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getUnitName()
				: "";
		String gender = reportDto != null && reportDto.getForcePersonalDetails() != null
				? reportDto.getForcePersonalDetails().getGender()
				: "";
		String doj = reportDto != null && reportDto.getForcePersonalDetails() != null
				? dateFormat.format(reportDto.getForcePersonalDetails().getDojPresentRank()) + ""
				: "";
		String dob = reportDto != null && reportDto.getForcePersonalDetails() != null
				? dateFormat.format(reportDto.getForcePersonalDetails().getDob()) + ""
				: "";

		addTableCell1(table, "IRLA / REGT No.", irlaNumber, alternate);
		alternate = !alternate;
		addTableCell1(table, "Name", name, alternate);
		alternate = !alternate;
		addTableCell1(table, "Rank/Designation", rank, alternate);
		alternate = !alternate;
		addTableCell1(table, "Force", forceName, alternate);
		alternate = !alternate;
		addTableCell1(table, "Unit", unitName, alternate);
		alternate = !alternate;
		addTableCell1(table, "Date Of Joining", doj, alternate);
		alternate = !alternate;
		addTableCell1(table, "Date Of Birth", dob, alternate);
		alternate = !alternate;
		addTableCell1(table, "Gender", gender, alternate);
	}

	private static void addTableCell1(PdfPTable table, String key, String value, boolean alternate) {
		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, fontSize)));
		cellKey.setPadding(2f);

		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0f); // Remove border
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
		cellValue.setPadding(5f);
		cellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellValue.setBorderWidth(0); // Remove border

		table.addCell(cellValue);
	}

	private static void addTablefinalCategoryCell1(PdfPTable table, String key, String value, boolean alternate) {
		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, fontSize)));
		cellKey.setPadding(2f);

		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0f); // Remove border
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
		cellValue.setPadding(5f);
		cellValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellValue.setBorderWidth(0); // Remove border

		table.addCell(cellValue);
	}

	private static void addgeneralExaminationTableCell1(PdfPTable table, String key, String value, boolean alternate) {
		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, fontSize)));
		cellKey.setPadding(2f);

		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0f); // Remove border
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
		cellValue.setPadding(5f);
		cellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellValue.setBorderWidth(0); // Remove border

		table.addCell(cellValue);
	}

	private static void psychologicaladdCell(PdfPTable table, String key, String value, boolean alternate) {

		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, fontSize)));
		cellKey.setPadding(5f); // Adjust padding as needed
		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0f); // Remove border
		cellKey.setFixedHeight(20f); // Set fixed height
		cellKey.setMinimumHeight(20f); // Ensure minimum height
		cellKey.setColspan(7); // Make sure it spans only one column
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
		cellValue.setPadding(5f); // Adjust padding as needed
		cellValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellValue.setBorderWidth(0); // Remove border
		cellValue.setFixedHeight(20f); // Set fixed height
		cellValue.setMinimumHeight(20f); // Ensure minimum height
		cellValue.setColspan(3); // Make sure it spans only one column
		table.addCell(cellValue);
	}

	private static void addSpecialCaseCell(PdfPTable table, String key, String value) {
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
		cellKey.setPadding(2f);
		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0);
		cellKey.setColspan(4); // Span across all columns
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, 10)));
		cellValue.setPadding(2f);
		cellValue.setBorderWidth(0); // Remove border
		cellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellValue.setColspan(4); // Span across all columns
		table.addCell(cellValue);
	}

	private static void addTableCell(PdfPTable table, String key, String value, int colspan, boolean alternate) {
		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA_BOLD, fontSize)));
		cellKey.setPadding(10f);
		cellKey.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellKey.setBorderWidth(0);

		if (alternate) {
			cellKey.setBackgroundColor(BaseColor.LIGHT_GRAY);
		}
		table.addCell(cellKey);

		PdfPCell cellValue = new PdfPCell(new Phrase(value, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
		cellValue.setColspan(colspan);
		cellValue.setPadding(5f);
		cellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellValue.setBorderWidth(0); // Remove border
		if (alternate) {
			cellValue.setBackgroundColor(BaseColor.LIGHT_GRAY);
		}
		table.addCell(cellValue);
	}

	// Usage example:
	// addTableCell(table, "Distance covered in 12 minutes run/walk (Meters):",
	// generalExamination.getDistanceCoveredInMin(), 1, alternate);

	private void addStyledCell(PdfPTable table, String text, Font font, BaseColor backgroundColor, int alignment) {

		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setPadding(2f);
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setFixedHeight(5f); // Set fixed height
		cell.setMinimumHeight(5f); // Ensure minimum height
		table.addCell(cell);
	}

	private void addStyledHeaderCellCell(PdfPTable table, String text, Font font, int alignment) {
		Font boldFont = new Font(font.getFamily(), font.getSize(), Font.BOLD, font.getColor());

		Phrase phrase = new Phrase(text, boldFont);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(4f);
		cell.setBorder(0);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(5f);
		cell.setMinimumHeight(5f);
		table.addCell(cell);
	}

	private void addPhysicalMeasurementRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;

		// Get the PhysicalMeasurement object
		PhysicalMeasurement physicalMeasurement = reportDto.getExamDtoRequest().getPhysicalMeasurement();

		// Handle null PhysicalMeasurement object
		if (physicalMeasurement == null) {
			// Handle case where physicalMeasurement is null
			addTableCell1(table, "Height", "", alternate);
			addTableCell1(table, "Weight", "", alternate);
			addTableCell1(table, "Chest UnExpanded", "", alternate);
			addTableCell1(table, "Chest expanded", "", alternate);
			addTableCell1(table, "Abdominal Girth", "", alternate);
			addTableCell1(table, "Trans-TroChanteril Girth", "", alternate);
			addTableCell1(table, "Ratio A/T", "", alternate);
			addTableCell1(table, "BMI", "", alternate);
		} else {
			// Add table cells with null checks
			addTableCell1(table, "Height", getStringValue(physicalMeasurement.getHeight()), alternate);
			addTableCell1(table, "Weight", getStringValue(physicalMeasurement.getWeight()), alternate);
			addTableCell1(table, "Chest Expanded", getStringValue(physicalMeasurement.getChestExpanded()), alternate);
			addTableCell1(table, "Chest Unexpanded", getStringValue(physicalMeasurement.getChestUnexpanded()),
					alternate);
			addTableCell1(table, "Abdominal Girth", getStringValue(physicalMeasurement.getAbdominalGirth()), alternate);
			addTableCell1(table, "Trans Trochanteric Girth",
					getStringValue(physicalMeasurement.getTrasTroChantericGirth()), alternate);
			addTableCell1(table, "Ratio A/T", getStringValue(physicalMeasurement.getRatioAT()), alternate);
			addTableCell1(table, "BMI", getStringValue(physicalMeasurement.getBmi()), alternate);
		}
	}

	// Helper method to handle null values and convert to string
	private String getStringValue(Object value) {
		return value == null ? "" : value.toString();
	}

	private void addPsychologicalAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;
		psychologicaladdCell(table, "Any Past history of psychiatric illness, if so details:",
				reportDto.getExamDtoRequest().getPhychologicalAssessmentAsLaidDown().getPsychiatricIllnessHistory(),
				alternate);
		psychologicaladdCell(table,
				"Any History of breakdown/outburst or taking wrong decision indecisiveness leading to public reaction or castigation of civil authority:",
				reportDto.getExamDtoRequest().getPhychologicalAssessmentAsLaidDown()
						.getWrong_decision_public_castigation(),
				alternate);
		psychologicaladdCell(table, "History of any alcoholic/drug abuse:",
				reportDto.getExamDtoRequest().getPhychologicalAssessmentAsLaidDown().getAlcholic_drug_abuse_history(),
				alternate);
		psychologicaladdCell(
				table, "History of head injury/infective/Metabolic encephalopathy:", reportDto.getExamDtoRequest()
						.getPhychologicalAssessmentAsLaidDown().getInjury_infective_metabolic_encephalopathy_history(),
				alternate);
		psychologicaladdCell(table, "Objective Psychometric scale if any applied and result thereof:",
				reportDto.getExamDtoRequest().getPhychologicalAssessmentAsLaidDown().getObjective_psychometric_scale(),
				alternate);
	}

	private void addgeneralAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {

		GeneralExamination ge = reportDto.getExamDtoRequest().getGeneralExamination();
		boolean alternate = false;

		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8);
		PdfPCell headerCell;

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

		addStyledHeaderCellCell(table, "Distance covered in 12 minutes run/walk (Meters)", dataFont,
				Element.ALIGN_LEFT);
		addStyledCell(table, ge.getDistanceCoveredInMin() + "", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Body Built", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, ge.getBodyBuild(), dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "BP (mmHg) Systolic", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getBpSystolic() + "", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "BP (mmHg) Diastolic", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getBpDiastolic() + "", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Tongue", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getTounge(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Pulse", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getPulseRate(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Anemia", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getAnaemia(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Temperature (C)", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getTemperature(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Cyanosis", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getCyanosis(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Icterus", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getIcterus(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Respiration", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getRespiration(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Oedema", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getOedema(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Clubbing", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getClubbing(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "koilonychia", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getKoilonychia(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Lymph glands palpable", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getLymphGlandsPalpable(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Tonsils", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getTonsils(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "JVP", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getJvp(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Teeth/Denture", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getTeethDenture(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Thyroid", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getThyroid(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Throat", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getThroat(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Spleen", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getSpleen(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Liver", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getLiver(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "C.V.S", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "S1", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getS1(), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "S2", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getS2(), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Murmur if any:", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ge.getMurmur(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;

	}

	private void addnervousSystemAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;
		CentralNervousSystem cns = reportDto.getExamDtoRequest().getCns();
		CranialNervesMeningealSign cnms = reportDto.getExamDtoRequest().getCranialNervesMeningealSign();
		SensorySystem ss = reportDto.getExamDtoRequest().getSensorysystem();

		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		// Add header cells
		PdfPCell headerCell;

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);

		addStyledHeaderCellCell(table, "Higher Function:", smallFont, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Memory(Recent & Remote)", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cns.getMemoryRecentRemote(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Intelligence", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cns.getIntelligence(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Personality", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cns.getPersonality(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Orientation(time,place &person)", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cns.getOrientation(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Cranial Nerves", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getCranialNerves(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Meningeal Sign if any", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getMeningealSignIfAny(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Motor System:", smallFont, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellCell(table, "Nutrition of Muscles", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getNutritionOfMuscles(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Wasting", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getWasting(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Tone", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getTone(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Coordination", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getCoordination(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Abnormal movement/Fasciculation", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getAbnormalMovementFasiculation(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Power", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getPower(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "DTR", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, cnms.getDtr(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Sensory System", smallFont, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Reflexes", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getReflexes(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Romberg's Sign", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getRombergSign(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "SLR(Right)", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getSlrTestLimbRight(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "SLR(LEFT)", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getSlrTestLimbLeft(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Finger Nose Test", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getFingerNoseTest(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Skull & Bone ", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, ss.getSkullAndBone(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Reflexes", smallFont, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Plantar-Reflexes", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getExamDtoRequest().getReflexes().getPlantar(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Abdominal & cremasteric", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getExamDtoRequest().getReflexes().getAbdominal(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		alternate = !alternate;
		addStyledHeaderCellCell(table, "Cerebellar Signs", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getExamDtoRequest().getReflexes().getCerebellarSign(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Gower's Sign", dataFont, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getExamDtoRequest().getReflexes().getGowerSign(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addAbdomenAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;
		Abdomen abdomen = reportDto.getExamDtoRequest() != null ? reportDto.getExamDtoRequest().getAbdomen() : null;
		Font smallFont = new Font(Font.FontFamily.COURIER, 8f, Font.BOLD);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
		RespiratorySystem rs = reportDto.getExamDtoRequest().getRespiratorySystem();
		Abdomen ab = reportDto.getExamDtoRequest().getAbdomen();

		addStyledCell(table, "Respiratory System:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "", smallFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "Any Deformity of Chest:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, rs.getChestDeformityAny(), smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "Percussion", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, rs.getPercussion(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "Breath sounds", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, rs.getBreathSounds(), smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "Adventitious Sounds: ", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, rs.getAdventitiousSounds(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "Abdomen", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "", smallFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "Any mass Palpable", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ab.getAnyMassPalpable(), smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "Any other abnormality:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ab.getAnyOtherAbnormality(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "Piles Fissure ", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ab.getPilesFissure(), smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "Fistula", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ab.getFitsula(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "Prolapse Rectum:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ab.getProlapseRectum(), smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addeyefactorAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		EyeFactor ey = reportDto.getExamDtoRequest().getEyeFactor();
		PdfPCell headerCell;

		Font boldFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		boldFont.setColor(BaseColor.BLACK);
		// Add header cells
		headerCell = new PdfPCell(new Phrase("S.No", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Examination", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Right", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Left", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		// Row 1
		addStyledCell(table, "a)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "Distance Vision", boldFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 2
		addStyledCell(table, "i)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "With Glasses", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getDistant_vision_rt_wg(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getDistant_vision_lt_wg(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 3
		addStyledCell(table, "ii)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "Without Glasses", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getDistant_vision_rt_wog(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getDistant_vision_lt_wog(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 4
		addStyledCell(table, "b)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "Near Vision", boldFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 5
		addStyledCell(table, "i)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "With Glasses", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getNear_vision_rt_wg(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getNear_vision_lt_wg(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);

		alternate = !alternate;

		// Row 6
		addStyledCell(table, "ii)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_CENTER);
		addStyledCell(table, "Without Glasses", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getNear_vision_rt_wog(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);
		addStyledCell(table, ey.getNear_vision_lt_wog(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_CENTER);

		alternate = !alternate;

		// Row 7
		addStyledCell(table, "c)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Colour Vision", boldFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ey.getColor_vision_rt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getColor_vision_lt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 8
		addStyledCell(table, "d)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Field of Vision", boldFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ey.getField_of_vision_rt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getField_of_vision_lt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 9
		addStyledCell(table, "e)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Any other Pathology", boldFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ey.getAny_other_pathology_rt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getAny_other_pathology_lt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		// Row 10
		addStyledCell(table, "f)", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "IOL", boldFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ey.getIol_rt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ey.getIol_lt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

	}

	public void addlftAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		LiverFunctionTest lft = reportDto.getInvestigationDto().getLiverFunctionTest();
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Bilirubin", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getBilirubin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "0.2-1.1", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Direct Bilirubin", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getDirectBilirubin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "0.0-0.30", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "Indirect Bilirubin", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getIndirectBilirubin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "0.2-0.8", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "SGOT", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, lft.getsGOT(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "08-40", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Units/l", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "SGPT", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, lft.getsGPT(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "05-35", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Units/l", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "ALP", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, lft.getaLP(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "60-270", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Units/l", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Total Protein", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getTotalProtein(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "6.0-8.0", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Units/l", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Albumin", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, lft.getAlbumin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "3.7-5.0", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "gm/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Globulins", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getGlobulin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "2.3-3.6", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "gm/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "A:G Ratio", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lft.getAGRatio(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	public void addkftAssessmentRows(PdfPTable table, AmePdfFinalReportDto reportDto) {

		KidneyFunctionTest kft = reportDto.getInvestigationDto().getKft();
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;
		addStyledCell(table, "Blood Urea:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, kft.getBloodUrea(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "13-45", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Serum creatinine:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, kft.getScrumCreatinine(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "0.8-1.4", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Uric Acid:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, kft.getUricAcid(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "Male(2.4-7.0)/ Female(1.5-6.0)", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	public void addcbcAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		CompleteBloodCount cbc = reportDto.getInvestigationDto().getCompleteBloodCountCBC();
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Haemoglobin", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getHaemoglobin(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "M-12.5-18 / F-11.5-14", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "gm%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Total Leukocyte Count (TLC)", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getTotalLeukocyteCount(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "4000-11000", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "per cum", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Neutrophils", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getNeutrophilsOrPolymorphs(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "40-70", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Monocytes", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getMonocytes(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "02-10", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Lymphocyte", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getLymphocytes(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "20-45", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Basophils", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getBasophils(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "00-10", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Eosinophils", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getEosinophils(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "01-06", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Erythrocyte Sedimentation Rate (ESR)", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getErythrocyteSedimentationRate(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "0-20", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mm in 1st hour", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Platelet Count", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, cbc.getPlateletCount(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "150-450", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "10^3/ul", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addurineAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		UrineTestPhysicalMicroscopic urine = (reportDto.getInvestigationDto() != null)
				? reportDto.getInvestigationDto().getUrineTestPhysicalMicroscopic()
				: null;
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Epithelial Cells", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getEpithelialCells(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "15-20", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "HPF", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Cast Cells", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getCastCells(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Pus Cells", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getPusCells(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "RBC", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getRbc(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Crystal", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getCrystal(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Colour", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getColour(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Transparency", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getTransparency(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Reaction", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getReaction(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Albumin", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getAlbuminCells(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "NIL/TRACE/++++", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Sugar", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getSugar(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "NIL/TRACE/++++", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Bile Salt", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getBileSalt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Negative/Positive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Ketones", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, urine.getKetones(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Negative/Positive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Specific Gravity", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, urine.getSpecificGravity(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		if (reportDto.getForcePersonalDetails().getGender() == "Female") {
			addStyledCell(table, "UPT", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
			addStyledCell(table, urine.getUpt(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
					Element.ALIGN_LEFT);
			addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
			addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		}

	}

	private void addtpAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		ThyroidProfile tp = (reportDto.getInvestigationDto() != null)
				? reportDto.getInvestigationDto().getThyroidProfile()
				: null;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "T3", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, tp.getT3(), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "100-200", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "ng/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "T4", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, tp.getT4(), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "4.5-11.2", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mcg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "TSH", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, tp.getTsh(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "0.4-5.0", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "MIU/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addbloodsugarAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		Optional<BloodSugarF> bloodSugarF = (reportDto.getBloodSugarF() != null) ? reportDto.getBloodSugarF() : null;

		Optional<BloodSugarPP> bloodSugarpp = (reportDto.getBloodSugarPP() != null) ? reportDto.getBloodSugarPP()
				: null;
		Optional<BloodSugarRandom> bloodSugarRandom = (reportDto.getBloodSugarRandom() != null)
				? reportDto.getBloodSugarRandom()
				: null;
		Optional<BloodSugarHbA1c> bloodSugarHbA1C = (reportDto.getBloodSugarHbA1c() != null)
				? reportDto.getBloodSugarHbA1c()
				: null;

		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Blood Sugar F", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		if (!bloodSugarF.isEmpty()) {
			addStyledCell(table, bloodSugarF.get().getBloodSugarF(), dataFont,
					alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		} else {
			addStyledCell(table, "-", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		}
		addStyledCell(table, "60-140", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Blood Sugar Random", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		if (!bloodSugarRandom.isEmpty()) {
			addStyledCell(table, bloodSugarRandom.get().getBloodSugarRandom(), dataFont,
					alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		} else {
			addStyledCell(table, "-", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		}
		addStyledCell(table, "60-140", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Blood Sugar PP", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		if (!bloodSugarpp.isEmpty()) {
			addStyledCell(table, bloodSugarpp.get().getBloodSugarPP(), dataFont,
					alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		} else {
			addStyledCell(table, "-", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		}
		addStyledCell(table, "60-140", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "HbA1c", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		if (!bloodSugarHbA1C.isEmpty()) {
			addStyledCell(table, bloodSugarHbA1C.get().getHbA1c(), dataFont,
					alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		} else {
			addStyledCell(table, "-", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		}
		addStyledCell(table, "4.2-6.07", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "%", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addvmAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);

		ViralMakers vm = (reportDto.getInvestigationDto() != null) ? reportDto.getInvestigationDto().getViralMakers()
				: null;
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		// Add cells with alternating background colors
		addStyledCell(table, "HIV1", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, vm.getHiv1(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Reactive/Non Reactive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "HIV2", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, vm.getHiv2(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Reactive/Non Reactive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "HBSAG", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, vm.getHbsAg(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Reactive/Non Reactive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "VDRL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, vm.getVdrl(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Reactive/Non Reactive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "HCV", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, vm.getHcv(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "--", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Reactive/Non Reactive", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

	}

	private void addlipidAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		Lipid lipid = (reportDto.getInvestigationDto() != null) ? reportDto.getInvestigationDto().getLipid() : null;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);

		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Serum cholesterol", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.getSerumCcholesterol(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "130-200", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "LDL cholesterol", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.getlDLCholesterol(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "65-180", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "HDL Cholesterol", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.gethDLCholesterol(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "30-70", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "VLDL cholesterol", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.getvLDLcholesterol(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "02-38", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "Triglycerides", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.getTriglycerides(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "30-170", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "mg/dL", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "LDL/HDL Ratio", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, lipid.getlDLhDLRatio(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "1.2-2.0", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "ratio", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addcategoryAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		String sfactor = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getPsycological_shape()
				: "";
		String hfactor = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getHearingShape()
				: "";
		String afactor = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getUpperLimbShape()
				: "";
		String physicalShape = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getPhysicalShape()
				: "";
		String lowerlimb = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getLowerLimbShape()
				: "";
		String spine = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getSpineShape()
				: "";
		String efactor = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getEyeShape()
				: "";
		String gfactor = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getGynaecologyShape() + ""
				: "";

		PdfPCell headerCell;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f);
		smallFont.setColor(BaseColor.RED);
		// Add header cells
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);

		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);

		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

		boolean alternate = false;

		addStyledCell(table, "`S` Factor", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, sfactor, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;
		addStyledCell(table, "`H` Factor", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, hfactor, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "`A` Factor", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Upper limb", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Lower limb", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Spine", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, afactor, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, lowerlimb, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, spine, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;
		addStyledCell(table, "`P` Factor", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, physicalShape, dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;
		addStyledCell(table, "`E` Factor", font, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, efactor, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		alternate = !alternate;

	}

	private void addfinalCategoryRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		java.util.List<FinalCategoryRemarkTemp> list = reportDto.getFinalCategoryRemarkTemps();

		PdfPCell headerCell;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);

		headerCell = new PdfPCell(new Phrase("Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Shape", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Remark", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		if (!list.isEmpty()) {
			for (FinalCategoryRemarkTemp temp : list) {

				addStyledCell(table, temp.getCategoryTypeName(), dataFont,
						alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
				addStyledCell(table, temp.getSubCategoryType(), dataFont,
						alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
				addStyledCell(table, temp.getComment(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
						Element.ALIGN_LEFT);
				addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);


			}
		}

	}

	private void addfinalCategoryRowdata(PdfPTable table, AmePdfFinalReportDto reportDto) {
		PdfPCell headerCell;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;

		addStyledCell(table, "Final Categorization :", smallFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getFinalCategoryAwarded(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "ADVICE/EMPLOYABILITY RESTRICTION(S) IF ANY :", smallFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getRemark(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private void addreportingRow(PdfPTable table, AmePdfFinalReportDto reportDto) throws Exception {

		boolean alternate = false;
		String rank = reportDto != null && reportDto.getBoardMemberDetails() != null
				? rankRepo.findById(reportDto.getBoardMemberDetails().getRank()).get().getRankFullName()
				: "";

		String name = reportDto != null && reportDto.getBoardMemberDetails() != null
				? reportDto.getBoardMemberDetails().getName()
				: "";
		String forceId = reportDto != null && reportDto.getBoardMemberDetails() != null
				? reportDto.getBoardMemberDetails().getForceId()
				: "";
		String unit = reportDto != null && reportDto.getBoardMemberDetails() != null
				? reportDto.getBoardMemberUnitName()
				: "";
		String force = reportDto != null && reportDto.getBoardMemberDetails() != null
				? reportDto.getBoardMemberForceName()
				: "";
		String date = reportDto != null && reportDto.getBoardMemberDetails() != null
				? reportDto.getBoardMemberDetails().getLastAmeDate() + ""
				: "";
		String combinedText = name + " [" + forceId + "]";
		
		
		//for Po
		Optional<ForcePersonnelDto> optionalForcePersonal = reportDto.getForcePersonelDtoOptionalPO();
		if (optionalForcePersonal.isEmpty()) {
			throw new Exception("No Data Present" + optionalForcePersonal);
		}

		ForcePersonnelDto forcePersonnelDto = optionalForcePersonal.get();
		String rankPO = forcePersonnelDto.getRank();
		String namePO = forcePersonnelDto.getName();
		String unitPO = forcePersonnelDto.getUnitName();
		String forcePO = forcePersonnelDto.getForceName();
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		String forceIdPO = forcePersonnelDto != null && forcePersonnelDto.getForceId() != null
				? forcePersonnelDto.getForceId()
				: "";

		String combinedTextPO = namePO + " [" + forceIdPO + "]";
		

		addStyledCell(table, "Signature:", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Signature:", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "Name [IRLA/REGT.NO]", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, combinedText, dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "Name [IRLA/REGT.NO]", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,combinedTextPO, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		
		addStyledCell(table, "Rank/Designation", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, rank, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "Rank/Designation", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, rankPO, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		
		addStyledCell(table, "Force", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, force, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Force", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,forcePO, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		
		addStyledCell(table, "Unit", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, unit, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Unit", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, unitPO, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		addStyledCell(table, "Place", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, reportDto.getMedicalBoardMemberPlace(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Place", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,reportDto.getMedicalBoardMemberPlace(), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		addStyledCell(table, "Date", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Date", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		
	}

	public void addameIdRows(PdfPTable table, AmePdfFinalReportDto reportDto) {

		String ameId = reportDto != null && reportDto.getAmeMasterStatus() != null
				? reportDto.getAmeMasterStatus().getAmeId()
				: "";
		boolean alternate = false;
		addTableCell1(table, "Personal Details", "AME ID :-" + ameId, alternate);
	}

	private void addhearingRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;

		// Get the Hearing object
		Hearing hearing = reportDto.getExamDtoRequest().getHearing();

		// Handle null Hearing object
		if (hearing == null) {
			// Handle case where hearing is null
			addTableCell1(table, "Normal in both ears", "", alternate);
			addTableCell1(table, "Moderate defect in one ear", "", alternate);
			addTableCell1(table, "Partial defect in both ear", "", alternate);
			addTableCell1(table, "Any Other combinations", "", alternate);
			addTableCell1(table, "Auroscopy", "", alternate);
			addTableCell1(table, "Rennie's Test", "", alternate);
			addTableCell1(table, "Weber's Test", "", alternate);
			addTableCell1(table, "Audiometry(if indicated)", "", alternate);
		} else {
			// Add table cells with null checks
			addTableCell1(table, "Normal in both ears", getStringValue(hearing.getNormal_in_both_ears()), alternate);
			addTableCell1(table, "Moderate defect in one ear", getStringValue(hearing.getModerate_defect_in_one_ear()),
					alternate);
			addTableCell1(table, "Partial defect in both ear", getStringValue(hearing.getPartial_defect_in_both_ears()),
					alternate);
			addTableCell1(table, "Any Other combinations", getStringValue(hearing.getAny_other_combination()),
					alternate);
			addTableCell1(table, "Auroscopy", getStringValue(hearing.getAuroscopy()), alternate);
			addTableCell1(table, "Rennie's Test", getStringValue(hearing.getRennie_test()), alternate);
			addTableCell1(table, "Weber's Test", getStringValue(hearing.getWeber_test()), alternate);
			addTableCell1(table, "Audiometry(if indicated)", getStringValue(hearing.getAudiometry()), alternate);
		}
	}

	private void addappendagesRows(PdfPTable table, AmePdfFinalReportDto reportDto) {
		boolean alternate = false;
		PdfPCell headerCell;
		Font boldFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
		boldFont.setColor(BaseColor.BLACK);

		headerCell = new PdfPCell(new Phrase("Upper Limb ", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase(" ", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Left", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Right", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		// Get the Limb and Spine condition object
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);
		Appendages limb = reportDto.getExamDtoRequest().getAppendages();

		addStyledCell(table, "Any Loss/infirmity in any joint or part must be indicated in detail", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, limb.getAnyLossInfirmityDetailsUpperLeftLimb(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, limb.getAnyLossInfirmityDetailsUpperRightLimb(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		headerCell = new PdfPCell(new Phrase("Lower Limb ", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase(" ", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Left", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Right", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		// Get the Limb and Spine condition object

		addStyledCell(table, "Any Loss/infirmity in any joint or part must be indicated in detail", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, limb.getAnyLossInfirmityDetailsLowerLeftLimb(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, limb.getAnyLossInfirmityDetailsLowerRightLimb(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		headerCell = new PdfPCell(new Phrase("Spine", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase(" ", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("", boldFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		addStyledCell(table, "For conditions related to spine", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, limb.getSpine(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

	private boolean hasThyroidProfileData(ThyroidProfile tp) {
		return (tp.getT3() != null && !tp.getT3().isEmpty()) || (tp.getT4() != null && !tp.getT4().isEmpty())
				|| (tp.getTsh() != null && !tp.getTsh().isEmpty());
	}

	private boolean hasviralData(ViralMakers vm) {
		return (vm.getHiv1() != null && !vm.getHiv1().isEmpty()) || (vm.getHiv2() != null && !vm.getHiv2().isEmpty())
				|| (vm.getHbsAg() != null && !vm.getHbsAg().isEmpty())
				|| (vm.getVdrl() != null && !vm.getVdrl().isEmpty()) || (vm.getHcv() != null && !vm.getHcv().isEmpty());
	}

	private boolean hasLFTData(LiverFunctionTest lft) {
		return (lft.getBilirubin() != null && !lft.getBilirubin().isEmpty())
				|| (lft.getDirectBilirubin() != null && !lft.getDirectBilirubin().isEmpty())
				|| (lft.getIndirectBilirubin() != null && !lft.getIndirectBilirubin().isEmpty())
				|| (lft.getsGOT() != null && !lft.getsGOT().isEmpty())
				|| (lft.getsGPT() != null && !lft.getsGPT().isEmpty())
				|| (lft.getaLP() != null && !lft.getaLP().isEmpty())
				|| (lft.getTotalProtein() != null && !lft.getTotalProtein().isEmpty())
				|| (lft.getAlbumin() != null && !lft.getAlbumin().isEmpty())
				|| (lft.getGlobulin() != null && !lft.getGlobulin().isEmpty())
				|| (lft.getAGRatio() != null && !lft.getAGRatio().isEmpty());
	}

	private boolean hasKFTData(KidneyFunctionTest kft) {
		return (kft.getBloodUrea() != null && !kft.getBloodUrea().isEmpty())
				|| (kft.getScrumCreatinine() != null && !kft.getScrumCreatinine().isEmpty())
				|| (kft.getUricAcid() != null && !kft.getUricAcid().isEmpty());
	}

	private void addLogoToHeader(PdfPTable headerTable, String logoPath, int alignment) {
		try (InputStream logoStream = getClass().getResourceAsStream(logoPath)) {
			if (logoStream != null) {
				byte[] logoBytes = logoStream.readAllBytes();
				Image logo = Image.getInstance(logoBytes);
				logo.scaleToFit(50, 50); // Scale logo to desired size

				PdfPCell logoCell = new PdfPCell(logo, false);
				logoCell.setBorderWidth(0);
				logoCell.setHorizontalAlignment(alignment); // Align left or right

				// Add the logo to the header table
				headerTable.addCell(logoCell);
			} else {
				System.err.println("Logo image not found at specified path: " + logoPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addgynoAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		GynaeAndObsFemale gp = (reportDto.getExamDtoRequest() != null)
				? reportDto.getExamDtoRequest().getGynaeAndObsFemale()
				: null;
		Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
		smallFont.setColor(BaseColor.BLACK);
		PdfPCell headerCell;
		// Add header cells
		headerCell = new PdfPCell(new Phrase("Test Name", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Result", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		headerCell = new PdfPCell(new Phrase("Range", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("Unit", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		boolean alternate = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		addStyledCell(table, "1. LMP ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, simpleDateFormat.format((gp.getLmp())) + "", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "2. Menstrual History ", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, gp.getMenstrualHistory(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "3. Vaginal Discharge(if any) ", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, gp.getVaginalDischarge(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "	4.UV Prolapse,(if any) ", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, gp.getUvProlapse(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "5. USG abdomen,(if any):", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, gp.getUsgAbdomen(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "6. Other ailment,(if any):", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, gp.getOtherAilment(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledCell(table, "7. Obstetrics History(If any)	", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, gp.getObstetricsHistory(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "	8. Date of last confinement", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		if (gp.getDateOfLastConfinement() != null) {
			addStyledCell(table, simpleDateFormat.format(gp.getDateOfLastConfinement()) + "", dataFont,
					alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		} else {
			addStyledCell(table, "N/A", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		}

	}

	private void addotherAssesmentRow(PdfPTable table, AmePdfFinalReportDto reportDto) {

		java.util.List<Others> list = reportDto.getInvestigationDto().getOthers();
		if (!list.isEmpty()) {

			for (Others other : list) {

				Font smallFont = new Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD);
				smallFont.setColor(BaseColor.BLACK);
				PdfPCell headerCell;
				// Add header cells
				headerCell = new PdfPCell(new Phrase("", smallFont));
				headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(headerCell);
				headerCell = new PdfPCell(new Phrase("", smallFont));
				headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(headerCell);
				headerCell = new PdfPCell(new Phrase("", smallFont));
				headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(headerCell);

				headerCell = new PdfPCell(new Phrase("", smallFont));
				headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				headerCell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(headerCell);
				Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
				boolean alternate = false;

				addStyledCell(table, other.getTestCode().toUpperCase(), dataFont,
						alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

				addStyledCell(table, other.getTestName(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
						Element.ALIGN_LEFT);

				addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
				addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

			}
		}
	}
	
	private void addreportingRowAA(PdfPTable table, AmePdfFinalReportDto reportDto) throws Exception {

		boolean alternate = false;

		Optional<ForcePersonnelDto> optionalForcePersonal = reportDto.getForcePersonelDtoOptionalAA();
		if (optionalForcePersonal.isEmpty()) {
			throw new Exception("No Data Present" + optionalForcePersonal);
		}

		ForcePersonnelDto forcePersonnelDto = optionalForcePersonal.get();

		String rank = forcePersonnelDto.getRank();
		String name = forcePersonnelDto.getName();
		String unit = forcePersonnelDto.getUnitName();
		String force = forcePersonnelDto.getForceName();
		
		String forceId = forcePersonnelDto != null && forcePersonnelDto.getForceId() != null
				? forcePersonnelDto.getForceId()
				: "";

		String combinedTextAA = name + " [" + forceId + "]";
		
		String combinedText = name + " [" + forceId + "]";
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);

		
		
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,"Signature:" , dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Name", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, combinedTextAA,dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,"Rank/Designation", dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, rank, dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Force", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,force, dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Unit", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, unit, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,"", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Date", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) , dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Place", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table,reportDto.getMedicalBoardMemberPlace() , dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

	}

}
