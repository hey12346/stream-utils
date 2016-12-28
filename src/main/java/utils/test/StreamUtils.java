package utils.test;

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
}
