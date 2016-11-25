/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import ClassLayer.*;
import DataLayer.MovieData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *
 * @author mqul
 */
public class MovieBusinessLayer {
    
    public Films getFilmsFromCSV(String csvPath){
        try{
            MovieData md = new MovieData();
            Films films = md.getCsvData(csvPath);
            return films;
        }catch(Exception ex){
            //Do something with error
            return null;
        }
    }
    
    public List<Director> getDistinctDirectorsFromFilms(Films films, String directorID){
        if(directorID == null){
            List <Director> tmpList = new ArrayList();
            
            films.forEach(film -> film.directors.stream()
                        .filter(dir -> tmpList.stream()
                                .noneMatch(di -> di.getID().equals(dir.getID())))
                        .map(nDir -> tmpList.add(nDir))
                        .collect(Collectors.toList()));
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
           
            return tmpList;
        }else{
            List <Director> tmpList = new ArrayList();
            
            films.forEach(film -> film.directors.stream()
                        .filter(dir -> (tmpList.stream()
                            .noneMatch(di -> di.getID().equals(dir.getID()))) && dir.getID().equals(directorID))
                        .map(nDir -> tmpList.add(nDir))
                        .collect(Collectors.toList()));
            
            return tmpList; 
        }
    }
    
    public List<Actor> getDistinctActorsFromFilms(Films films, String actorID){
        if(actorID == null){
            List <Actor> tmpList = new ArrayList();
            
            films.forEach(film -> film.actors.stream()
                        .filter(a -> tmpList.stream()
                                .noneMatch(e -> e.getID().equals(a.getID())))
                        .map(ac -> tmpList.add(ac))
                        .collect(Collectors.toList()));
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
           
            return tmpList;
        }else{
            List <Actor> tmpList = new ArrayList();
            
            films.forEach(film -> film.actors.stream()
                        .filter(a -> (tmpList.stream()
                            .noneMatch(e -> e.getID().equals(a.getID()))) && a.getID().equals(actorID))
                        .map(nDir -> tmpList.add(nDir))
                        .collect(Collectors.toList()));
            
            return tmpList; 
        }
    }
    
    
    //GetDistinctSimplisticFilmFromFilms??
    
    public Films getFilmSubset(String filmID, String directorID, String actorID, Films films){
        Films tmpFilms = new Films();
        
        tmpFilms = getFilmSubsetByMovieID(films, filmID);
        tmpFilms = getFilmSubsetByDirectorID(tmpFilms, directorID);
        tmpFilms = getFilmSubsetByActorID(tmpFilms, actorID);
                
        return tmpFilms;
    }
    
    public Films getFilmSubsetByMovieID(Films films, String filmID){
        Films tmpFilms = new Films();
        if(filmID != null){
            tmpFilms.addAll(films.stream().filter(a -> a.filmID.equals(filmID)).collect(Collectors.toList()));
            return tmpFilms;
        }else{
            return films;
        }
    }
    
    public Films getFilmSubsetByDirectorID(Films films, String directorID){
        Films tmpFilms = new Films();
        if(directorID != null){
            tmpFilms.addAll(films.stream().filter(x -> x.directors.stream().anyMatch(li -> li.getID().equals(directorID))).collect(Collectors.toList()));
            return tmpFilms;
        }else{
            return films;
        }
    }
    
    public Films getFilmSubsetByActorID(Films films, String actorID){
        Films tmpFilms = new Films();
        if(actorID != null){
            tmpFilms.addAll(films.stream().filter(x -> x.actors.stream().anyMatch(li -> li.getID().equals(actorID))).collect(Collectors.toList()));
            return tmpFilms;
        }else{
            return films;
        }
    }
}
