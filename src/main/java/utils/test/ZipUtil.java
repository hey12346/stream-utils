package utils.test;

import java.io.ByteArrayOutputStream;
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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;

class ZipUtil {
    private static ZipUtil myZipUtil;
    
    private ZipUtil() {
        
    }
    static {
        myZipUtil = new ZipUtil();
    }
    
    public static ZipUtil getInstance() {return myZipUtil;}
    
    private void addFileToZip (ZipOutputStream zipOut, String fileName, InputStream file) throws StreamUtilException{
        try {    
            InputStream is = file;
        
            zipOut.putNextEntry(new ZipEntry(fileName));

            byte[] buf = new byte[8192];
            int len;
            while ((len = is.read(buf)) > 0) {
                zipOut.write(buf, 0, len);
            }

            zipOut.closeEntry();
        } catch(IOException ex) {
            throw new StreamUtilException(ex.getMessage());
        }
    }
    
    private void createZipFile (Path outputPath, List <File> files) throws StreamUtilException{
        try {
            FileOutputStream bytes = new FileOutputStream(outputPath.toString());
            ZipOutputStream out = new ZipOutputStream(bytes);
            for (File file : files) {
                addFileToZip(out, file.getFileName(), file.getStream());
            }
    
            out.finish();
            out.close();
            bytes.close();
        } catch (IOException ex) {
            throw new StreamUtilException(ex.getMessage());
        }
    }

    public void makeZipFile(Path input, Path output) throws StreamUtilException{
        if(input == null || output == null) {
            throw new IllegalArgumentException("Input path and output path cannot be null.");
        }
        
        java.io.File folder = input.toFile();
        
        if(!folder.exists()) {
            throw new StreamUtilException("Input folder is empty.");
        }
        
        
        try {
            java.io.File[] listOfFiles = folder.listFiles();
            
            if(listOfFiles == null || listOfFiles.length == 0) {
                throw new StreamUtilException("Input folder is empty.");
            } else if(!output.endsWith(".zip")) {
                output = output.resolve("default.zip");
            }
            
            List <File> files = new ArrayList<>();
            
            for(java.io.File file: listOfFiles) {
                files.add(new File(file));
            }
            
            createZipFile(output, files);
            
            for(File file: files) {
                file.close();
            }
        } catch (IOException ex) {
            throw new StreamUtilException(ex.getMessage());
        }
    }
    
    public void makeZipFile(Map <String, InputStream> files, OutputStream bytes) throws StreamUtilException {
        try{
        ZipOutputStream out = new ZipOutputStream(bytes);
        for(String key: files.keySet()) {
            addFileToZip(out, key, files.get(key));
        }
        out.finish();
        } catch (IOException ex) {
            throw new StreamUtilException(ex.getMessage());
        }
    }
}
