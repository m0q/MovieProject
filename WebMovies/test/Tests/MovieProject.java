package Tests;

import ApplicationVariables.DataLayerType;
import ClassLayer.*;
import BusinessLayer.MovieBusinessLayer;

/**
 *
 * @author mqul
 */
public class MovieProject {
    public static void main(String[] args) {
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        Films films = mbl.getFilms();//.getFilmsFromCSV(AppVariables.FILE_PATH);
        
        for(Film f:films){
            System.out.println("Film: "+f.filmName);
            for(Director d : f.directors){
                System.out.println(f.filmName + " Director: " + d.personName);
            }

            for(Actor a : f.actors){
                System.out.println(f.filmName + " Actor: " + a.personName);
            }
            System.out.println("");
        }    
    }   
}
