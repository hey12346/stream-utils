package utils.test;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Iterator;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

class PDFUtil {
    private static PDFUtil util;
    
    private PDFUtil() {
        
    }
    
    static {
        util = new PDFUtil();
    }
    
    public static PDFUtil getInstance() {
        return util;
    }
    
    /**
     * This method stamps the provided message as a watermark onto each page of the provided PDF
     * @param pdfIs Must represent a PDF file
     * @param textToStamp The string we should stamp on here
     * @return OutputStream of the PDF
     */
    public static ByteArrayOutputStream stampPdfWithWatermark(InputStream pdfIs, String textToStamp) throws IOException, DocumentException {
    	 ByteArrayOutputStream stampedOs = new ByteArrayOutputStream();
    	 PdfReader reader = new PdfReader(pdfIs);
         PdfStamper stamper = new PdfStamper(reader, stampedOs);
         int n = reader.getNumberOfPages();
         PdfContentByte canvas;
         for (int p = 1; p <= n; p++) {
        	 canvas = stamper.getUnderContent(p);
        	 Font f = new Font(Font.TIMES_ROMAN, 30, Font.NORMAL, Color.LIGHT_GRAY);
	         Phrase ph = new Phrase(textToStamp, f);
	         PdfGState gs1 = new PdfGState();
	         gs1.setFillOpacity(0.5f);
	         canvas.setGState(gs1);
	         ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, ph, 337, 500, 45);          
         }
         stamper.close();
    	
         return stampedOs;
    }
    
    /**
     * Concatenates the passed in PDFs and writes it to the output stream.
     * @param PDFsForConcatenation
     * @param stream
     * @throws IOException
     * @throws DocumentException
     */
    public static void concatPDFs(List<InputStream> PDFsForConcatenation, OutputStream stream) throws IOException, DocumentException {
        Iterator<InputStream> itr = PDFsForConcatenation.iterator();
        Document document = new Document();
            PdfCopy copy = new PdfCopy(document, stream);
            document.open();
            int pageOfCurrentReaderPDF = 0;

            // Loop through the PDF files and add to the output.
            while (itr.hasNext()) {
                InputStream pdf = itr.next();
                PdfReader pdfReader = new PdfReader(pdf);

                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    pageOfCurrentReaderPDF++;
                    copy.addPage(copy.getImportedPage(pdfReader, pageOfCurrentReaderPDF));
                }
                pageOfCurrentReaderPDF = 0;
                pdf.close();
                pdfReader.close();
            }
            stream.flush();
            document.close();
            if (document.isOpen()) {
                document.close();
            }
    }
}
