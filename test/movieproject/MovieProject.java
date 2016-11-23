/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movieproject;

import DataLayer.MovieData;
import ClassLayer.*;
import ApplicationVariables.AppVariables;
import java.util.List;
/**
 *
 * @author mqul
 */
public class MovieProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MovieData md = new MovieData();
        
        try{
            List<Film> films = md.getCsvData(AppVariables.FILE_PATH);
            
            for(Film f:films){
                System.out.println("-----------");
                System.out.println(f.filmName);
                for(Director d : f.directors){
                    System.out.println("----Dir----");
                    System.out.println(d.personName);
                }
                
                for(Actor a : f.actors){
                    System.out.println("----Act----");
                    System.out.println(a.personName);
                    System.out.println("-----------");
                }
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
