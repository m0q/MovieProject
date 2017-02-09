package BusinessLayer;

import ApplicationVariables.AppVariables;
import ApplicationVariables.DataLayerType;
import ClassLayer.*;
import DataLayer.MovieData;
import datalayerdb.MovieDataDB;
import datalayerdb.DB;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import utilities.SimpleCaching;

/**
 *
 * @author mqul
 */
public class MovieBusinessLayer {
    
    static SimpleCaching sc = new SimpleCaching();
    
    public Films getFilms(){
        if(sc.get(AppVariables.cacheName) != null){
            return sc.get(AppVariables.cacheName);
        }else{
            try{
                Films films = new DB().getDBData();
                sc.put(AppVariables.cacheName, films);
                return sc.get(AppVariables.cacheName);
            }catch(Exception e){
                
                e.printStackTrace();
                return null;
                
            }
        }
    }
    
    /*retrieve film list from data source
    public Films getFilms(DataLayerType dataType, String csvPath){
        try{ 
            switch(dataType){
                case CSV: 
                    return new MovieData().getCsvData(csvPath);
                case DATABASE:
                    //return new MovieDataDB().getFilms();
                    return new DB().getDBData();
                default:
                    throw new RuntimeException("Unknown Data Type: " + dataType.toString());
            }
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
    }*/
    
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
    
    //--------------------------
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

