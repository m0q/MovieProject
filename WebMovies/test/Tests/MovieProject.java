package Tests;

import ClassLayer.*;
import BusinessLayer.MovieBusinessLayer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 *
 * @author mqul
 */
public class MovieProject {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException{
        Files.write(Paths.get("text.txt"), new String("Hello").getBytes());
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        Films films = mbl.getFilms();
        
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
