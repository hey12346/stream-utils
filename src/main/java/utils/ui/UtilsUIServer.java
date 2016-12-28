package utils.ui;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.net.MediaType;

import spark.Spark;
import utils.test.StreamUtils;
import de.neuland.jade4j.Jade4J;

public class UtilsUIServer {
    private final static Cache<String, InputStream> PDFCACHE = CacheBuilder
            .newBuilder().build();
    private final static Cache<String, InputStream> ZIPCACHE = CacheBuilder
            .newBuilder().build();
    
    public static void main(String [] args) {
        Spark.staticFileLocation("/public");
        
        get("/",
                (req, res) -> {
                    InputStream template = UtilsUIServer.class.getClassLoader()
                            .getResourceAsStream("templates/index.jade");

                    String jadeTemp = new BufferedReader(new InputStreamReader(
                            template)).lines()
                            .collect(Collectors.joining("\n"));

                    Map<String, Object> context = new HashMap<>();
                    return Jade4J.render(new StringReader(jadeTemp),
                            "index.jade", context);

                });
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
        
        post("/pdf-upload",
                (req, res) -> {
                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
                    req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

                    final Part uploadedFile = req.raw().getPart("file");
                    PDFCACHE.put(uploadedFile.getSubmittedFileName(), uploadedFile.getInputStream());
                    
                    System.out.println(uploadedFile.getSubmittedFileName());
                    
                    return "uploaded successfully";
                });
        
        get("/pdf",
                (req, res) -> {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    
                    StreamUtils.getInstance().mergePDF(new ArrayList<InputStream>(PDFCACHE.asMap().values()), stream);
                    
                    res.type("application/pdf");
                    res.header("Content-Disposition", "attachment; filename=concat.pdf");
                    
                    HttpServletResponse raw = res.raw();

                    raw.getOutputStream().write(stream.toByteArray());
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();
                    
                    stream.close();
                    
                    return res.raw();

                });
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });
        
        post("/zip-upload",
                (req, res) -> {
                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
                    req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

                    final Part uploadedFile = req.raw().getPart("file");
                    ZIPCACHE.put(uploadedFile.getSubmittedFileName(), uploadedFile.getInputStream());
                    
                    System.out.println(uploadedFile.getSubmittedFileName());
                    
                    return "uploaded successfully";
                });
        
        get("/zip",
                (req, res) -> {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    
                    StreamUtils.getInstance().makeZipFile(ZIPCACHE.asMap(), stream);
                    
                    res.type("application/zip");
                    res.header("Content-Disposition", "attachment; filename=allFiles.zip");
                    
                    HttpServletResponse raw = res.raw();

                    raw.getOutputStream().write(stream.toByteArray());
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();
                    
                    stream.close();
                    
                    return res.raw();

                });
        Spark.exception(Exception.class, (exception, request, response) -> {
            exception.printStackTrace();
        });  
    }
}
