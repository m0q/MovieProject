/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.IntStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("filmform")
public class FilmFormBean implements Serializable{
    private Double filmRating;
    private Integer filmYear;
    private String filmID, filmName;//, filmYear, filmRating;
    
    public void submitForm(){
        if(!checkNumberRange(0.0,10.0,filmRating))
            throw new IllegalArgumentException();
        
        if(!checkNumberRange(1000, 2017,filmYear)){
            throw new IllegalArgumentException();
        }
        
        if(!idCheck(filmID)){
            FacesContext.getCurrentInstance().addMessage("InsertForm:rowNum", new FacesMessage("Film ID has an incorrect format."));
            //validateFlag = false;
        }
    }
    
    private boolean checkNumberRange(Double min, Double max, Double number){
        return min <= number && max >= number;
    }
    
    private boolean checkNumberRange(Integer min, Integer max, Integer number){
        return min <= number && max >= number;
    }
    
    private boolean idCheck(String id){
       return id.chars()
                    .filter(c -> Character.isDigit(c))
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString().length() == 7;  
    }
    
    /*private boolean checkNumberRange(Number min, Number max, Number number){
        if(number instanceof Integer){
            return min.intValue() <= number.intValue() && max.intValue() >= number.intValue();
        }else if(number instanceof Double){
            return min.doubleValue() <= number.doubleValue() && max.doubleValue() >= number.doubleValue();
        }else{
            return false;
        }
    }*/
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public Integer getFilmYear(){return filmYear;}
    public Double getFilmRating(){return filmRating;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
    public void setFilmYear(Integer filmYear){this.filmYear = filmYear;}
    public void setFilmRating(Double filmRating){this.filmRating = filmRating;}
}
