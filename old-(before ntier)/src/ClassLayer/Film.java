/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLayer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mqul
 */
public class Film {
    public String filmID, filmName, imdbRating, filmYear;
    public List<Director> directors;
    public List<Actor> actors;
    
    public Film(){
        directors = new ArrayList<Director>();
        actors = new ArrayList<Actor>();
    }
    
    public Film(String filmID, String filmName, String imdbRating, String filmYear){
        this.filmID = filmID;
        this.filmName = filmName;
        this.imdbRating = imdbRating;
        this.filmYear = filmYear;
        directors = new ArrayList<Director>();
        actors = new ArrayList<Actor>();
    }
    
    public Film(String filmID, String filmName, String imdbRating, 
            List<Director> directors, List<Actor> actors, String filmYear){
        this.filmID = filmID;
        this.filmName = filmName;
        this.imdbRating = imdbRating;
        this.directors = directors;
        this.actors = actors;
        this.filmYear = filmYear;
    }
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public String getFilmRating(){return imdbRating;}
    public String getFilmYear(){return filmYear;}
    public List<Director> getDirectorList(){return directors;}
    public List<Actor> getActorList(){return actors;}
    
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
    public void setFilmRatig(String imdbRating){this.imdbRating = imdbRating;}
    public void setFilmYear(String filmYear){this.filmYear = filmYear;}
    public void setDirectorList(List<Director> directors){this.directors = directors;}
    public void setActorList(List<Actor> actors){this.actors = actors;}
}
