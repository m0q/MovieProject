package BusinessLayer;

import ClassLayer.*;
import DataLayer.MovieData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
            
            /**
             starting with the list of films, we access each film and stream into 
             the list of directors. From here, we ensure that no director ID in the
             stream already exists in the tmpFilm list. The child stream then returns 
             a stream of directors to the parent which then adds each of the returned
             directors into the tmpList, then turns the stream into a list of directors
             */
            
            films.stream().flatMap(film -> film.directors.stream()
                        .filter(dir -> tmpList.stream()
                                .noneMatch(di -> di.getID().equals(dir.getID())))
                        .map(nDir -> tmpList.add(nDir)))
                        .collect(Collectors.toList());
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
           
            return tmpList;
        }else{
            List <Director> tmpList = new ArrayList();
            
            films.stream().flatMap(film -> film.directors.stream()
                        .filter(dir -> tmpList.stream()
                                .noneMatch(di -> di.getID().equals(dir.getID())) && dir.getID().equals(directorID))
                        .map(nDir -> tmpList.add(nDir)))
                        .collect(Collectors.toList());
            
            return tmpList; 
        }
    }
    
    public List<Actor> getDistinctActorsFromFilms(Films films, String actorID){
        if(actorID == null){
            List <Actor> tmpList = new ArrayList();
            
            films.stream().flatMap(film -> film.actors.stream()
                        .filter(act -> tmpList.stream()
                                .noneMatch(listAct -> listAct.getID().equals(act.getID())))
                        .map(nAct -> tmpList.add(nAct)))
                        .collect(Collectors.toList());
            
            tmpList.sort(Comparator.comparing(c -> c.getName()));
           
            return tmpList;
        }else{
            List <Actor> tmpList = new ArrayList();
            
            films.stream().flatMap(film -> film.actors.stream()
                        .filter(act -> tmpList.stream()
                                .noneMatch(listAct -> listAct.getID().equals(act.getID())) && act.getID().equals(actorID))
                        .map(nAct -> tmpList.add(nAct)))
                        .collect(Collectors.toList());
            
            return tmpList; 
        }
    }
    
    
    public List<SimplisticFilm> getDistinctSimplisticFilmFromFilms(Films films, String filmID){
        List<SimplisticFilm> filmList = films.stream()
               // .filter(film -> film.getFilmID().equals(filmID))
                .map(fi -> new SimplisticFilm(fi.getFilmID(), fi.getFilmName()))
                .collect(Collectors.toList());
        filmList.sort(Comparator.comparing(f -> f.getFilmName()));
        return filmList;
    }
    
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

