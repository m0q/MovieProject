package PresentationLayer;

import ApplicationVariables.AppVariables;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("filmform")
public class FilmFormBean implements Serializable{
    private String filmID, filmName, filmYear, filmRating;
    
    public void submitForm(){
        //filmID, filmName, filmYear, filmRating
        //call to business layer - will store data from page to DB
    }
    
    //ensure the entered id meets condition: numeric with length 4 
    public void validateID(FacesContext context, UIComponent comp,Object value) {
	String id = (String) value;

	if (!digitAndLengthCheck(id, 7)) {
            displayErrorMessage(context, comp, "incorrect format");
        }
    }
    
    //ensure the entered year meets conditions: numeric with length 4, within specified range
    public void validateYear(FacesContext context, UIComponent comp,Object value) {
	String year = (String) value;

	if (!digitAndLengthCheck(year, 4)) {
            displayErrorMessage(context, comp, "incorrect year format");
        }else if(!isWithinRange(toDouble(AppVariables.WebProperties.yearMin), toDouble(AppVariables.WebProperties.yearMax), toDouble(year))){
            displayErrorMessage(context, comp, String.format("out of range (%s - %s)", 
                                                                  AppVariables.WebProperties.yearMin, 
                                                                  AppVariables.WebProperties.yearMax));
        }
    }
    
    //ensure the entered rating meets conditions: numeric (double) value, within range on 0-10
    public void validateRating(FacesContext context, UIComponent comp,Object value) {
	String rating = (String) value;
        
        if(!isDouble(rating)){
            displayErrorMessage(context, comp, "enter a valid number");
        }else if (!isWithinRange(0.0, 10.0, toDouble(rating))) {
            displayErrorMessage(context, comp, "out of range (0.0 - 10.0)");
        }
    }
    
    //error message to be display
    private void displayErrorMessage(FacesContext context, UIComponent comp, String message){
        ((UIInput) comp).setValid(false);
        context.addMessage(comp.getClientId(context), new FacesMessage(message));
    }
    
    //convert string to double
    private Double toDouble(String s){return Double.parseDouble(s);}
    
    //check if a string is convertable to a double
    private boolean isDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    
    //check if double is within specified range
    private boolean isWithinRange(Double min, Double max, Double number){
        return min <= number && max >= number;
    }
    
    //check if contents of string are all numbers and match a specified length 
    private boolean digitAndLengthCheck(String id, int requiredLength){
       return id.chars()
                    .filter(c -> Character.isDigit(c))
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString().length() == requiredLength;  
    }
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public String getFilmYear(){return filmYear;}
    public String getFilmRating(){return filmRating;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
    public void setFilmYear(String filmYear){this.filmYear = filmYear;}
    public void setFilmRating(String filmRating){this.filmRating = filmRating;}
}
