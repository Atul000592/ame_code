package nic.ame.app.user.serviceImpl;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;




import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import nic.ame.app.master.dto.AmeDecleartionDto;

import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;

@Service
public class DecleartionPdfService {

	// private static final String LOGO_PATH =
	// "/static/images/border-security-force-logo.png";

	private static final Font BOLD = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font BOLDs = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

	public void generatePdf(OutputStream outputStream, AmeDecleartionDto ame) {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, outputStream); // Use the passed output stream
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
			document.add(createITextParagraph2(
					"DECLARATION BY THE PERSONNEL OF CAPFs,NSG & AR TO BE EXAMINED FOR SHAPE CATEGORISATION", BOLD,
					Element.ALIGN_CENTER));

			PdfPTable ameTable = new PdfPTable(2);
			ameTable.setWidthPercentage(100);
			ameTable.setSpacingBefore(1f);
			ameTable.setSpacingAfter(10f);
			addRows(ameTable, ame);
			document.add(ameTable);

			PdfPTable table = new PdfPTable(4);
			float[] columnWidths = { 0.1f, 1f, 6f, 1f };
			table.setWidthPercentage(100);
			table.setWidths(columnWidths);
			table.setSpacingBefore(1f);
			table.setSpacingAfter(10f);
			addameIdRows(table, ame);
			document.add(table);

			PdfPTable ameTableAgree = new PdfPTable(3);
			float[] columnWid = { 4f, 2f, 2f }; // Set column widths as required
			ameTableAgree.setWidths(columnWid); // Apply column widths
			ameTableAgree.setWidthPercentage(100); // Set table width to 100% of page width
			ameTableAgree.setSpacingBefore(1f); // Optional: set spacing before table
			ameTableAgree.setSpacingAfter(3f); // Optional: set spacing after table

			// Populate table with data
			ameTableAgreeData(ameTableAgree, ame);

			// Add the table to the document
			document.add(ameTableAgree);

			document.add(createITextParagraph21(
					"It is further certified that the above facts stated by me are true to my best of knowledge and belief. "
							+ "I have not suppressed any fact concerning my health condition ever in past and as is at present",
					BOLDs, Element.ALIGN_LEFT));

			PdfPTable detail = new PdfPTable(4);
			detail.setWidthPercentage(100);
			detail.setSpacingBefore(10f);
			detail.setSpacingAfter(5f);
			addreportingRow(detail, ame);
			document.add(detail);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addameIdRows(PdfPTable table, AmeDecleartionDto ame) throws DocumentException {
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

		// headerCell = new PdfPCell(new Phrase("", smallFont));
		// headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		// headerCell.setBorder(PdfPCell.NO_BORDER);
		// table.addCell(headerCell);

		headerCell = new PdfPCell(new Phrase("", smallFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
		AmeDeclarationIndividualDetails id = ame.getAmeDeclarationIndividualDetails();
		AmeDeclarationIndividualModel im = ame.getAmeDeclarationIndividualModel();
		String formattedDated = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		if (im.getLastAmeDate() != null) {
			formattedDated = dateFormat.format(im.getLastAmeDate());
		} else {
			formattedDated = "NA";
		}
		// Format the date

		// Adding cells with null checks
		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(1)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Date of Recruitment (Joining in the Force)", dataFont, Element.ALIGN_LEFT);
		// addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		String formattedDate = im.getDate_of_joining() != null ? dateFormat.format(im.getDate_of_joining()) : "N/A";
		addStyledHeaderCellCell(table, formattedDate, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(2)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table,
				"Have you ever been examined for any major ailment or hospitalized during last one year?", dataFont,
				Element.ALIGN_LEFT);
		// addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		String ailmentStatus = "N/A";
		if (id.getMajorailmenthospitalized() != null) {
			if ("Y".equalsIgnoreCase(id.getMajorailmenthospitalized())) {
				ailmentStatus = "Yes";
			} else if ("N".equalsIgnoreCase(id.getMajorailmenthospitalized())) {
				ailmentStatus = "No";
			}
		}
		addStyledHeaderCellCell(table, ailmentStatus, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "Are You a patient of?", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "a)  Hypertension (High Blood Pressure)", dataFont, Element.ALIGN_LEFT);

		String ailmentHBPStatus = "N/A";
		if (id.getHypertension() != null) {
			if ("Y".equalsIgnoreCase(id.getHypertension())) {
				ailmentHBPStatus = "Yes";
			} else if ("N".equalsIgnoreCase(id.getHypertension())) {
				ailmentHBPStatus = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentHBPStatus, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "b)  Ischemic Heart-disease", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusIHD = "N/A";
		if (id.getIchaemicheart() != null) {
			if ("Y".equalsIgnoreCase(id.getIchaemicheart())) {
				ailmentStatusIHD = "Yes";
			} else if ("N".equalsIgnoreCase(id.getIchaemicheart())) {
				ailmentStatusIHD = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatusIHD, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "(3)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "c)  Diabetes Mellitus", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusDm = "N/A";
		if (id.getDiabetesmellitus() != null) {
			if ("Y".equalsIgnoreCase(id.getDiabetesmellitus())) {
				ailmentStatusDm = "Yes";
			} else if ("N".equalsIgnoreCase(id.getDiabetesmellitus())) {
				ailmentStatusDm = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatusDm, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "d)  Chronic Cough/Bronchial asthma/COPD", dataFont, Element.ALIGN_LEFT);

		String ailmentStatuscod = "N/A";
		if (id.getCopd() != null) {
			if ("Y".equalsIgnoreCase(id.getCopd())) {
				ailmentStatuscod = "Yes";
			} else if ("N".equalsIgnoreCase(id.getCopd())) {
				ailmentStatuscod = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatuscod, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "e)  Epilepsy", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusepil = "N/A";
		if (id.getEpilepsy() != null) {
			if ("Y".equalsIgnoreCase(id.getEpilepsy())) {
				ailmentStatusepil = "Yes";
			} else if ("N".equalsIgnoreCase(id.getEpilepsy())) {
				ailmentStatusepil = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatusepil, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "f)  Persistent Headache", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusPh = "N/A";
		if (id.getPersistent_headache() != null) {
			if ("Y".equalsIgnoreCase(id.getPersistent_headache())) {
				ailmentStatusPh = "Yes";
			} else if ("N".equalsIgnoreCase(id.getPersistent_headache())) {
				ailmentStatusPh = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatusPh,
				dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellWithoutRightBorder(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell2(table, "g)  Mental Instability", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusMi = "N/A";
		if (id.getMenatlinstability() != null) {
			if ("Y".equalsIgnoreCase(id.getMenatlinstability())) {
				ailmentStatusMi = "Yes";
			} else if ("N".equalsIgnoreCase(id.getMenatlinstability())) {
				ailmentStatusMi = "No";
			}
		}
		addStyledHeaderCellRight(table, ailmentStatusMi, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(4)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Have you suffered from giddiness at any time?", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusGiideness = "N/A";
		if (id.getGiddiness() != null) {
			if ("Y".equalsIgnoreCase(id.getGiddiness())) {
				ailmentStatusGiideness = "Yes";
			} else if ("N".equalsIgnoreCase(id.getGiddiness())) {
				ailmentStatusGiideness = "No";
			}
		}

		addStyledHeaderCellCell(table, ailmentStatusGiideness, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(5)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Have you suffered from chest pain/palpitation", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusChestpain = "N/A";
		if (id.getChestpainpalpitation() != null) {
			if ("Y".equalsIgnoreCase(id.getChestpainpalpitation())) {
				ailmentStatusChestpain = "Yes";
			} else if ("N".equalsIgnoreCase(id.getChestpainpalpitation())) {
				ailmentStatusChestpain = "No";
			}
		}

		addStyledHeaderCellCell(table, ailmentStatusChestpain,
				dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(6)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Did you ever suffer from Tuberculosis?", dataFont, Element.ALIGN_LEFT);

		String ailmentStatusTuber = "N/A";
		if (id.getTb() != null) {
			if ("Y".equalsIgnoreCase(id.getTb())) {
				ailmentStatusTuber = "Yes";
			} else if ("N".equalsIgnoreCase(id.getTb())) {
				ailmentStatusTuber = "No";
			}
		}
		addStyledHeaderCellCell(table, ailmentStatusTuber, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(7)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Your", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "a) Appetite", dataFont, Element.ALIGN_LEFT);

		addStyledHeaderCellCell(table, id.getAppetite() != null ? id.getAppetite() : "N/A", dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "b) Sleep", dataFont, Element.ALIGN_LEFT);

		addStyledHeaderCellCell(table, id.getSleep() != null ? id.getSleep() : "N/A", dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(8)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Smoking habit", dataFont, Element.ALIGN_LEFT);

		String smokingHabit = "N/A";
		if (id.getSmokinghabit() != null) {
			if ("Y".equalsIgnoreCase(id.getSmokinghabit())) {
				smokingHabit = "Yes";
			} else if ("N".equalsIgnoreCase(id.getSmokinghabit())) {
				smokingHabit = "No";
			}
		}
		addStyledHeaderCellCell(table, smokingHabit, dataFont,
				Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Number of cigarettes per day", dataFont, Element.ALIGN_LEFT);

		addStyledHeaderCellCell(table, id.getNumberofcigarettes() + "", dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(9)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Alcohol intake", dataFont, Element.ALIGN_LEFT);

		String alcholIntake = "N/A";
		if (id.getAlcoholintake() != null) {
			if ("Y".equalsIgnoreCase(id.getAlcoholintake())) {
				alcholIntake = "Yes";
			} else if ("N".equalsIgnoreCase(id.getAlcoholintake())) {
				alcholIntake = "No";
			}
		}

		addStyledHeaderCellCell(table, alcholIntake, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Average quantity per day(ml)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, String.valueOf(id.getQuantityperday()), dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(10)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Any Accident/Injury/major Surgery undergone so far", dataFont,
				Element.ALIGN_LEFT);

		String accidentSurgery = "N/A";
		if (id.getAccidentinjurysurgery() != null) {
			if ("Y".equalsIgnoreCase(id.getAccidentinjurysurgery())) {
				accidentSurgery = "Yes";
			} else if ("N".equalsIgnoreCase(id.getAccidentinjurysurgery())) {
				accidentSurgery = "No";
			}
		}
		addStyledHeaderCellCell(table, accidentSurgery, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(11)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Have you been transferred recently or under order of transfer", dataFont,
				Element.ALIGN_LEFT);
		if (id.getRecentTransferData().equalsIgnoreCase("Y"))
			addStyledHeaderCellCell(table, "Yes", dataFont, Element.ALIGN_LEFT);
		else
			addStyledHeaderCellCell(table, "No", dataFont, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "a) Previous Unit", dataFont, Element.ALIGN_LEFT);

		if (id.getRecentTransferData().equalsIgnoreCase("Y"))
			addStyledHeaderCellCell(table, im.getPrevious_unit(), dataFont, Element.ALIGN_LEFT);
		else
			addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);

		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "b) New Unit", dataFont, Element.ALIGN_LEFT);

		if (id.getRecentTransferData().equalsIgnoreCase("Y"))
			addStyledHeaderCellCell(table, im.getCurent_new_unit(), dataFont, Element.ALIGN_LEFT);
		else
			addStyledHeaderCellCell(table, "", dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(12)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Date of Last AME", dataFont, Element.ALIGN_LEFT);

		addStyledHeaderCellCell(table, formattedDated, dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(13)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Place of  Last AME:", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, im.getLastAmePlace(), dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

		addStyledHeaderCellRight(table, "", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "(14)", dataFont, Element.ALIGN_LEFT);
		addStyledHeaderCellCell(table, "Medical Category of Last AME", dataFont, Element.ALIGN_LEFT);

		addStyledHeaderCellCell(table, id.getLastAmeStatus(), dataFont, Element.ALIGN_LEFT);
		alternate = !alternate;

	}

	private Paragraph createITextParagraph2(String text, Font font, int alignment) {
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph.setSpacingAfter(20f);

		return paragraph;
	}

	private Paragraph createITextParagraph21(String text, Font font, int alignment) {
		Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph.setLeading(12f);
		paragraph.setSpacingAfter(10f);

		return paragraph;
	}

	private void addStyledHeaderCellCell(PdfPTable table, String text, Font font, int alignment) {
		// Font boldFont = new Font(font.getFamily(), font.getSize(), Font.BOLD,
		// font.getColor());

		Phrase phrase = new Phrase(text, font);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(4f);
		cell.setBorderWidth(1f);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(5f);
		cell.setMinimumHeight(5f);
		table.addCell(cell);
	}

	private void addStyledHeaderCellCell2(PdfPTable table, String text, Font font, int alignment) {
		// Font boldFont = new Font(font.getFamily(), font.getSize(), Font.BOLD,
		// font.getColor());

		Phrase phrase = new Phrase(text, font);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(4f);
		cell.setBorderWidth(0f);
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(5f);
		cell.setMinimumHeight(5f);
		table.addCell(cell);
	}

	private void addStyledHeaderCellRight(PdfPTable table, String text, Font font, int alignment) {
		Phrase phrase = new Phrase(text, font);
		PdfPCell cell = new PdfPCell(phrase);

		// Set padding
		cell.setPadding(4f);

		// Apply border only to the right side
		cell.setBorderWidth(0f); // Remove all borders
		cell.setBorder(Rectangle.OUT_RIGHT); // Apply border to the right side
		cell.setBorderWidthRight(1f); // Set width for the right border
		cell.setBorderColorRight(BaseColor.BLACK); // Set color for the right border

		// Set horizontal alignment and height
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(5f);
		cell.setMinimumHeight(5f);

		// Add the cell to the table
		table.addCell(cell);
	}

	private void addStyledHeaderCellWithoutRightBorder(PdfPTable table, String text, Font font, int alignment) {
		Phrase phrase = new Phrase(text, font);
		PdfPCell cell = new PdfPCell(phrase);

		// Set padding
		cell.setPadding(4f);

		// Apply border only to the right side
		cell.setBorderWidth(0f); // Remove all borders
		cell.setBorder(Rectangle.OUT_BOTTOM | Rectangle.OUT_LEFT | Rectangle.OUT_TOP); // Apply border to the right side
		cell.setBorderWidthRight(1f); // Set width for the right border
		cell.setBorderColorRight(BaseColor.BLACK); // Set color for the right border

		// Set horizontal alignment and height
		cell.setHorizontalAlignment(alignment);
		cell.setFixedHeight(5f);
		cell.setMinimumHeight(5f);

		// Add the cell to the table
		table.addCell(cell);
	}

	private void addStyledCell(PdfPTable table, String text, Font font, BaseColor backgroundColor, int alignment) {

		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setPadding(2f);
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setFixedHeight(10f); // Set fixed height
		cell.setMinimumHeight(5f); // Ensure minimum height
		table.addCell(cell);
	}

	private void addreportingRow(PdfPTable table, AmeDecleartionDto ame) {
		AmeDeclarationIndividualModel im = ame.getAmeDeclarationIndividualModel();
		boolean alternate = false;

		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		Font dataFonts = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		String hiv = null;

		if (ame.getAmeDeclarationIndividualDetails().getHivTest().equalsIgnoreCase("y")) {
			hiv = "I Agree";
		} else {
			hiv = "Don't Agree";
		}

		PdfPCell spacerCell = new PdfPCell(new Phrase(" "));
		spacerCell.setBorder(0); // No border
		spacerCell.setColspan(4); // Span across all columns in the row
		spacerCell.setFixedHeight(25f); // Adjust height as needed for spacing
		table.addCell(spacerCell);

		LocalDateTime dateTime = LocalDateTime.now();

		// Formatter for date and time (e.g., dd-MM-yyyy HH:mm:ss)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String date = dateTime.format(formatter);

		addStyledCell(table, "Place", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ame.getAmeDeclarationIndividualModel().getPlace(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Signature", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Date", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, date, dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "IRLA/REGT No", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ame.getForcePersonal().getForceId(), dataFont,
				alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Name", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, im.getName(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Rank/Designation", dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);
		addStyledCell(table, ame.getRank(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

		addStyledCell(table, "", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, " ", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, "Unit", dataFont, alternate ? new BaseColor(230, 230, 230) : null, Element.ALIGN_LEFT);
		addStyledCell(table, ame.getUnit(), dataFont, alternate ? new BaseColor(230, 230, 230) : null,
				Element.ALIGN_LEFT);

	}

	public void addRows(PdfPTable table, AmeDecleartionDto ame) {
		boolean alternate = false;
		addTableCell1(table, "AME ID :-" + ame.getAmeId(), "", alternate);
	}

	private static void addTableCell1(PdfPTable table, String key, String value, boolean alternate) {
		float fontSize = 8f;
		// Check if key is null
		key = key != null ? key : "";
		// Check if value is null
		value = value != null ? value : "";
		PdfPCell cellKey = new PdfPCell(new Phrase(key, FontFactory.getFont(FontFactory.HELVETICA, fontSize)));
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

	private InputStream getResourceFileAsStream(String filePath) {
		try {
			ClassPathResource resource = new ClassPathResource(filePath);
			return resource.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void ameTableAgreeData(PdfPTable table, AmeDecleartionDto ame) throws DocumentException {
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
	
		// Define headers
		PdfPCell headerCell = new PdfPCell(new Phrase("", headerFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
	
		headerCell = new PdfPCell(new Phrase("", headerFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
	
		headerCell = new PdfPCell(new Phrase("", headerFont));
		headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(headerCell);
	
		// Adding data rows
		String hiv;
		if (ame.getAmeDeclarationIndividualDetails().getHivTest().equalsIgnoreCase("y")) {
			hiv = "I Agree";
		} else {
			hiv = "Don't Agree";
		}
	
		PdfPCell dataCell;
	
		dataCell = new PdfPCell(new Phrase("I Agree/Don't agree to undergo HIV Test:", headerFont));
		dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		dataCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(dataCell);
	
		dataCell = new PdfPCell(new Phrase(hiv, headerFont));
		dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		dataCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(dataCell);
	
		dataCell = new PdfPCell(new Phrase("", dataFont));
		dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		dataCell.setBorder(PdfPCell.NO_BORDER);
		table.addCell(dataCell);
	}
	
}
