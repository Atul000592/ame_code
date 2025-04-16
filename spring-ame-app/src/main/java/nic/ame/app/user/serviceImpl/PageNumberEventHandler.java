package nic.ame.app.user.serviceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PageNumberEventHandler extends PdfPageEventHelper {

    private PdfTemplate template;
    private BaseFont baseFont;
    private PdfWriter writer;
    private int totalPages = 0; // Counter for total pages

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        this.writer = writer;
        template = writer.getDirectContent().createTemplate(50, 50);
        try {
            baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

	    @Override
	    public void onEndPage(PdfWriter writer, Document document) {
	        totalPages = writer.getPageNumber(); // Update the total page count
	        PdfContentByte cb = writer.getDirectContent();
	        Rectangle pageSize = document.getPageSize();
	
	        int pageNumber = writer.getPageNumber();
	        String footerText = "Page " + pageNumber + " of ";
	
	        // Calculate the width of the footer text
	        float footerTextWidth = baseFont.getWidthPoint(footerText, 8);
	
	        // Calculate the center position for the footer text
	        float x = (pageSize.getLeft() + pageSize.getRight()) / 2 - (footerTextWidth + 50) / 2;
	        float y = pageSize.getBottom() + 20; // Y-coordinate: Adjust as needed
	
	        // Draw the footer text at the calculated center position
	        cb.beginText();
	        cb.setFontAndSize(baseFont, 8);
	        cb.showTextAligned(Element.ALIGN_LEFT, footerText, x, y, 0);
	        cb.endText();
	
	        // Place the template immediately after the "of" text
	        cb.addTemplate(template, x + footerTextWidth, y); // Place the template correctly
	    }
	
	    @Override
	    public void onCloseDocument(PdfWriter writer, Document document) {
	        PdfContentByte cb = writer.getDirectContent();
	        // Add total page count to the template
	        template.beginText();
	        template.setFontAndSize(baseFont, 8);
	        template.showText(String.valueOf(totalPages)); // Total pages
	        template.endText();
	    }
}
