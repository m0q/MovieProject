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
            
            films.forEach(film -> tmpList.addAll(film.directors.stream()
                    .filter(x -> tmpList.stream()
                        .noneMatch(y -> y.getID().equals(x.getID())))
                    .collect(Collectors.toList())));
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
            
           return tmpList;
        }else{
            List <Director> tmpList = new ArrayList();
            
            films.forEach(film -> {tmpList.addAll(film.directors.stream()
                        .filter(x -> x.getID().equals(directorID) && (tmpList.stream().noneMatch(c -> c.getID().equals(x.getID()))))
                        .collect(Collectors.toList()));
            });
            
            return tmpList; 
        }
    }
    
    public List<Actor> getDistinctActorsFromFilms(Films films, String actorID){
        if(actorID == null){
            
            /*films.stream()
                    .flatMap(film -> film.actors.stream()
                            .map(x -> new Actor(x.getID(), x.getName())))
                    .collect(C ollectors.groupingBy(x -> x.getID()));
            
            List<Actor> map = films.stream()
                    .flatMap(x -> x.actors.stream()
                            .map(i -> new Actor(i.getID(), i.getName())))
                    .collect(Collectors.groupingBy(x -> x.getID()))
                    .values().stream().flatMap(c -> c.stream()).collect(Collectors.toList());;
                       
            for(int i = 0; i<map.size(); i++){
                System.out.println(map.get(i).getName());//.forEach(x -> System.out.println(x.getID() + x.getName()));
            }
            
            return new ArrayList();*/
                    
                    
            List <Actor> tmpList = new ArrayList();
            
            films.forEach(film -> tmpList.addAll(film.actors.stream()
                    .filter(x -> tmpList.stream()
                        .noneMatch(y -> y.getID().equals(x.getID())))
                    .collect(Collectors.toList())));
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
            
           return tmpList;
            
            
           /*traditional method 
            for(Film film : films){
                for(Actor actor : film.actors){
                    if(!tmpList.stream().anyMatch(x -> x.getID().equals(actor.getID()))){
                        tmpList.add(new Actor(actor.getID(), actor.getName()));
                    }     
                }
            }*/ 
        }else{
            List <Actor> tmpList = new ArrayList();
            
            films.forEach(film -> {tmpList.addAll(film.actors.stream()
                        .filter(x -> x.getID().equals(actorID) && (tmpList.stream().noneMatch(c -> c.getID().equals(x.getID()))))
                        .collect(Collectors.toList()));
            });
            
            return tmpList; 
            /*traditional method 
            for(Film film : films){
                for(Actor actor : film.actors.stream().filter(x -> x.getID().equals(actorID)).collect(Collectors.toList())){
                    if(!tmpList.stream().anyMatch(x -> x.getID().equals(actor.getID()))){
                        tmpList.add(new Actor(actor.getID(), actor.getName()));
                    }     
                }
            }*/
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
