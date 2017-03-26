package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Director;
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
@Named("personBean")
public class PersonFormBean extends ValidationBean implements Serializable{
    private String personID, personName, selectedType;
    
    public void submitForm(){
        String message = "";
        boolean isSuccess = false;
        
        try{
            //call to business layer - will store data from page to DB
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            
            if(selectedType.equals("actor")){
                isSuccess = mbl.insertActor(new Actor(personID, personName));
                message = mbl.getMessage();
            }else if(selectedType.equals("director")){
                isSuccess = mbl.insertDirector(new Director(personID, personName));
                message = mbl.getMessage();
            }else{
                //something went wrong
            }
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

    public String getPersonID(){return personID;}
    public String getPersonName(){return personName;}
    public String getSelectedType(){return selectedType;}
    
    public void setSelectedType(String selectedType){this.selectedType = selectedType;}
    public void setPersonID(String personID){this.personID = personID;}
    public void setPersonName(String personName){this.personName = personName;}
}
