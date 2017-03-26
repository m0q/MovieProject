package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Film;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("filmform")
public class FilmFormBean extends ValidationBean implements Serializable{
    private String filmID, filmName, filmYear, filmRating, directorID, directorName, actorID, actorName;
    private String item = "";
    
    public void submitForm() throws IOException{
        String message = "";
        boolean isSuccess = false;
        
        
        try{
            //filmID, filmName, filmYear, filmRating
            //call to business layer - will store data from page to DB
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            isSuccess = mbl.insertFilm(new Film(filmID, filmName, filmRating, filmYear));
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
   
    public String getItem(){return item;}
    public void setItem(String item){this.item = item;}
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public String getFilmYear(){return filmYear;}
    public String getFilmRating(){return filmRating;}
    public String getDirectorID(){return directorID;}
    public String getDirectorName(){return directorName;}
    public String getActorID(){return actorID;}
    public String getActorName(){return actorName;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
    public void setFilmYear(String filmYear){this.filmYear = filmYear;}
    public void setFilmRating(String filmRating){this.filmRating = filmRating;}
    public void setActorID(String actorID){this.actorID = actorID;}
    public void setActorName(String actorName){this.actorName = actorName;}
    public void setDirectorID(String directorID){this.directorID = directorID;}
    public void setDirectorName(String directorName){this.directorName = directorName;}
}
