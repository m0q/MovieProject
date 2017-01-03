/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import ApplicationVariables.AppVariables;
import ClassLayer.Films;
import DataLayer.MovieData;
import datalayerdb.MovieDataDB;
import org.junit.Test;

/**
 *
 * @author mqul
 */
public class DataTest {
    
    @Test
    public void listContainsDataFromCSV(){
        MovieData md = new MovieData();
        Films films = md.getCsvData(AppVariables.FILE_PATH);
        
        assert(!films.isEmpty());
    }
    
    @Test
    public void listContainsDataFromDB(){
        Films films = null;
        try{
            MovieDataDB md = new MovieDataDB();
            films = md.getFilms();
        }catch(Exception e){e.printStackTrace();}
        
        assert(!films.isEmpty());
    }
    
}
