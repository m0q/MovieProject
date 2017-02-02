/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author mqul
 */
@ManagedBean(name="filmDataBean")
@ViewScoped
public class RetrieveFilmData implements Serializable{
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

    public String getFilmID() {return filmID;}
    public void setFilmID(String filmID) {this.filmID = filmID;}

    public String getFilmName() {return filmName;}
    public void setFilmName(String filmName) {this.filmName = filmName;}

    public String getFilmYear() {return filmYear;}
    public void setFilmYear(String filmYear) {this.filmYear = filmYear;}

    public String getImdbRating() {return imdbRating;}
    public void setImdbRating(String imdbRating) {this.imdbRating = imdbRating;}

    public String getActorID() {return actorID;}
    public void setActorID(String actorID) {this.actorID = actorID;}
    public String getActorName() {return actorName;}
    public void setActorName(String actorName) {this.actorName = actorName;}

    public String getDirectorID() {return directorID;}
    public void setDirectorID(String directorID) {this.directorID = directorID;}
    public String getDirectorName() {return directorName;}
    public void setDirectorName(String directorName) {this.directorName = directorName;}
}
