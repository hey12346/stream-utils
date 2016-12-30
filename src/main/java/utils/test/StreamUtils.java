package utils.test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.lowagie.text.DocumentException;

public class StreamUtils {
    private static StreamUtils myUtil;
    
    private StreamUtils () {
        
    }
    
    static {
        myUtil = new StreamUtils();
    }
    
    public static StreamUtils getInstance() {
        return myUtil;
    }
    
    /**
     * Copy an inputstream to an output stream
     * @param test
     * @param stream
     * @return
     * @throws IOException
     */
    public OutputStream copy(InputStream test, OutputStream stream) throws IOException {
        IOUtils.copy(test, stream);
        return stream;
    }
    
    /**
     * Zips up all files inside input path and outputs the zip file into the output path.
     * @param input
     * @param output
     * @throws StreamUtilException
     */
    public void makeZipFile(Path input, Path output) throws StreamUtilException{
        ZipUtil myZipUtil = ZipUtil.getInstance();
        myZipUtil.makeZipFile(input, output);
    }
    
    /**
     * Takes in a map of files, zips them up and writes to the output stream.
     * @param files
     * @param stream
     * @throws StreamUtilException
     */
    public void makeZipFile(Map <String, InputStream> files, OutputStream stream) throws StreamUtilException{
        ZipUtil myZipUtil = ZipUtil.getInstance();
        myZipUtil.makeZipFile(files, stream);
    }
    
    /**
     * Concats all the PDFs in the list and writes the resultant pdf into the outputstream
     * @param list
     * @param outputStream
     * @throws DocumentException
     * @throws IOException
     */
    public void mergePDF(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
        PDFUtil.getInstance().concatPDFs(list, outputStream);
    }
    
    /**
     * stamps the pdf with the message as the watermark and returns the new pdf as a bytearrayoutputstream.
     * @param pdf
     * @param message
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public ByteArrayOutputStream watermark(InputStream pdf, String message) throws IOException, DocumentException {
    	return PDFUtil.getInstance().stampPdfWithWatermark(pdf, message);
    }
}
