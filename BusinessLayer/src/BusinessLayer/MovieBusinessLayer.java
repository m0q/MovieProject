package BusinessLayer;

import ApplicationVariables.AppVariables;
import ClassLayer.*;
import DataLayer.MovieData;
import java.util.List;
import caching.SimpleCaching;
import java.sql.SQLException;

/**
 *
 * @author mqul
 */
public class MovieBusinessLayer {
    
    //retrieve film list from data source or cache 
    public Films getFilms(){
        if(SimpleCaching.get(AppVariables.Cache.filmCacheName) != null){
            return SimpleCaching.get(AppVariables.Cache.filmCacheName);
        }else{
            try{
                //Films films = new MovieData().getCsvData(AppVariables.CSV.FILE_PATH);
                Films films = new MovieData().getDatabaseData();
                SimpleCaching.put(AppVariables.Cache.filmCacheName, films);
                return SimpleCaching.get(AppVariables.Cache.filmCacheName);
            }catch(SQLException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
    }
    
    //Films
    public List<SimplisticFilm> getDistinctSimplisticFilmsFromFilms(Films films){
        return (films == null) ? null : films.toListSimplisticFilm();
    }
    
    public Films getFilmsSubset(String filmID, String directorID, String actorID, Films films){
        return films.getFilmsFilteredSubset(filmID, directorID, actorID);
    }
    
    //Directors
    public List<Director> getDistinctDirectorsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctDirector();
    }

    public List<Director> getDistinctDirector(Films films, String directorID){
       return films.getDistinctDirector(directorID);
    }
    
    //Actors
    public List<Actor> getDistinctActorsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctActor();
    }

    public List<Actor> getDistinctActor(Films films, String actorID){
        return films.getDistinctActor(actorID);
    }
    
    //information for table once all dropdown fields are selected
    public Film getFilmFromSimplisticFilm(String filmID){
        return this.getFilms()
                        .stream()
                        .filter(f -> f.getFilmID().equals(filmID))
                        .findFirst().get();
    }
    
    public Director getDirectorFromSimplisticFilm(Film sFilm, String directorID){
        return sFilm.getDirectorList()
                        .stream()
                        .filter(d -> d.getID().equals(directorID))
                        .findFirst().get();
    }
    
    public Actor getActorFromSimplisticFilm(Film sFilm, String actorID){
        return sFilm.getActorList()
                        .stream()
                        .filter(a -> a.getID().equals(actorID))
                        .findFirst().get();
    }
}

