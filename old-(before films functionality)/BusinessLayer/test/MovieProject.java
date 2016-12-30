/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ClassLayer.*;
import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import java.util.stream.Collectors;
/**
 *
 * @author mqul
 */
public class MovieProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        
        Films films = mbl.getFilmsFromDB();//.getFilmsFromCSV(AppVariables.FILE_PATH);
        
        
           
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
