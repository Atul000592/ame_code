package nic.ame.app.esign;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfString;



public class EsignUtils {
    private static int contentEstimated;
    private static PdfSignatureAppearance appearance;

    public static byte[] toByteArray(final InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int reads = is.read(); reads != -1; reads = is.read()) {
            baos.write(reads);
        }
        return baos.toByteArray();
    }

    public static String generateHash(final byte[] dataBytes) throws NoSuchAlgorithmException, NoSuchProviderException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashedBytes = digest.digest(dataBytes);
        return convertByteArrayToHexString(hashedBytes);
    }

    public static String convertByteArrayToHexString(final byte[] arrayBytes) {
        final StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; ++i) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xFF) + 256, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentDateTimeISOFormat() {
        System.out.println("Dummy pdf-------getCurrentDateTimeISOFormat");
        SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date(System.currentTimeMillis() + 3 * 60 * 1000);
        String ts = tsFormat.format(now);
        return ts;
    }
    
    public static Document convertStringToDocument(String xmlStr)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
        return doc;
    }

    public static String getXpathValue(XPath xPath, String RequestPath, Document doc) throws XPathExpressionException {
        String XpathValue = xPath.compile(RequestPath).evaluate(doc);
        xPath.reset();
        return XpathValue;
    }

    public static void signDocument(byte[] pkcs7Response, PdfSignatureAppearance appearance1)
            throws IOException, DocumentException, Exception {
        contentEstimated = 8192;
        appearance = appearance1;
        byte[] paddedSig = new byte[contentEstimated];
        System.arraycopy(pkcs7Response, 0, paddedSig, 0, pkcs7Response.length);
        PdfDictionary dic2 = new PdfDictionary();
        dic2.put(PdfName.CONTENTS, (new PdfString(paddedSig)).setHexWriting(true));
        appearance.close(dic2);
    }

}
