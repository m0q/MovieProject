package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("fileimport")
public class FileImportBean implements Serializable{
    private Part file; // +getter+setter
    private String fileName;
    
    public void submit(){
        try (InputStream input = file.getInputStream()) {
            fileName = "CSVDATA_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) +".csv";
            Files.copy(input, new File("/Users/mqul/NetBeansProjects/NovusMovieProject/Data/", fileName).toPath());
            
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            mbl.importData(fileName);
        }
        catch (IOException e) {
            // Show faces message?
        }
        
    }
    
    public Part getFile(){return file;}
    public void setFile(Part file){this.file = file;}
}
