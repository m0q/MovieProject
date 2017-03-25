package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
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
@Named("actorform")
public class ActorFormBean extends ValidationBean implements Serializable{
    private String actorID, actorName, filmID;
    
    public void submitForm(){
        String message = "";
        boolean isSuccess = false;
        
        try{
            //call to business layer - will store data from page to DB
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            isSuccess = mbl.insertActor(new Actor(actorID, actorName), filmID);
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

    public String getActorID(){return actorID;}
    public String getActorName(){return actorName;}
    public String getFilmID(){return filmID;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setActorID(String actorID){this.actorID = actorID;}
    public void setActorName(String actorName){this.actorName = actorName;}
}
