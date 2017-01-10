package utils.test;

import java.io.*;
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
    
    public OutputStream copy(InputStream test, OutputStream stream) throws IOException {
        IOUtils.copy(test, stream);
        return stream;
    }
    
    public void makeZipFile(Path input, Path output) throws StreamUtilException{
        ZipUtil myZipUtil = ZipUtil.getInstance();
        myZipUtil.makeZipFile(input, output);
    }
    
    public void makeZipFile(Map <String, InputStream> files, OutputStream stream) throws StreamUtilException{
        ZipUtil myZipUtil = ZipUtil.getInstance();
        myZipUtil.makeZipFile(files, stream);
    }
    
    public void mergePDF(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
        PDFUtil.getInstance().doMerge(list, outputStream);
    }

    public OutputStream writeObjsAsXls(List <?> myObjs) throws IOException {
        ByteArrayOutputStream myStream = new ByteArrayOutputStream();
        ExcelUtil <?> myUtils = new ExcelUtil<>(myObjs);
        myUtils.getExcelFile(myStream);
        return myStream;
    }
}
