package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Director;
import java.io.Serializable;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("directorform")
public class DirectorFormBean extends ValidationBean implements Serializable{
    private String directorID, directorName, filmID;
    
    public void submitForm(){
        String message = "";
        boolean isSuccess = false;
        
        try{
            //call to business layer - will store data from page to DB
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            isSuccess = mbl.insertDirector(new Director(directorID, directorName), filmID);
            message = mbl.getMessage();
        }catch(SQLException | ClassNotFoundException e){
            if(e instanceof SQLException){
                SQLException ex = (SQLException)e;
                message += "Failed | "; 
                message += "Error Code: " + ex.getErrorCode() + " | ";
                message += "Message: " + e.getMessage();
            }
        }
        
        if(isSuccess){
            FacesContext.getCurrentInstance().addMessage("resultMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
            //FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }else{
            FacesContext.getCurrentInstance().addMessage("resultMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, message , null));
        }
    }

    public String getDirectorID(){return directorID;}
    public String getDirectorName(){return directorName;}
    public String getFilmID(){return filmID;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setDirectorID(String directorID){this.directorID = directorID;}
    public void setDirectorName(String directorName){this.directorName = directorName;}
}
