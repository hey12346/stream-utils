package utils.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This file class is to guarantee only one inputstream per file.
 * @author kevin.gao
 *
 */
public class File {
    private InputStream stream;
    private String fileName;
    
    public File(java.io.File file) throws FileNotFoundException {
        this.stream = new FileInputStream(file);
        this.fileName = file.getName();
    }

    public InputStream getStream() {
        return stream;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void close() throws IOException {
        stream.close();
    }
}
