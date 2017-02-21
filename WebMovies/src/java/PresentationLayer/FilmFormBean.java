/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("filmform")
public class FilmFormBean implements Serializable{
    private Double filmRating;
    private Integer filmID, filmYear;
    private String filmName;//, filmYear, filmRating;
    
    public void submitForm(){
        if(!checkNumberRange(0.0,10.0,filmRating))
            throw new IllegalArgumentException();
        
        if(!checkNumberRange(1000, 2017,filmYear)){
            throw new IllegalArgumentException();
        }
    }
    
    private boolean checkNumberRange(Double min, Double max, Double number){
        return min <= number && max >= number;
    }
    
    private boolean checkNumberRange(Integer min, Integer max, Integer number){
        return min <= number && max >= number;
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
    
    public Integer getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public Integer getFilmYear(){return filmYear;}
    public Double getFilmRating(){return filmRating;}
    
    public void setFilmID(Integer filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
    public void setFilmYear(Integer filmYear){this.filmYear = filmYear;}
    public void setFilmRating(Double filmRating){this.filmRating = filmRating;}
}
