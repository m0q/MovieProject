/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mqul
 */
@ManagedBean(name="bean2")
@SessionScoped
public class PopulateTable implements Serializable{
    private String filmID, filmName, filmYear, imdbRating;
    private String actorID, actorName;
    private String directorID, directorName;
    
    public void populateFields(String filmID, String directorID, String actorID){
        try{
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            Film film = mbl.getFilmFromSimplisticFilm(filmID);
            
            this.filmID = film.getFilmID();
            this.filmName = film.getFilmName();
            this.filmYear = film.getFilmYear();
            this.imdbRating = film.getFilmRating();
            
            Director director = mbl.getDirectorFromSimplisticFilm(film, directorID);
            this.directorID = director.getID();
            this.directorName = director.getName();
            
            Actor actor = mbl.getActorFromSimplisticFilm(film, actorID);
            this.actorID = actor.getID();
            this.actorName = actor.getName();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //JSF read access to fields
    public String getFilmImdbLink() {return String.format(AppVariables.WebProperties.imdbFilmURL, filmID);}
    public String getDirectorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, directorID);}
    public String getActorImdbLink() {return String.format(AppVariables.WebProperties.imdbProfileURL, actorID);}
    public String getFilmID() {return filmID;}
    public String getFilmName() {return filmName;}
    public String getFilmYear() {return filmYear;}
    public String getImdbRating() {return imdbRating;}
    public String getActorID() {return actorID;}
    public String getActorName() {return actorName;}
    public String getDirectorID() {return directorID;}
    public String getDirectorName() {return directorName;}
}
