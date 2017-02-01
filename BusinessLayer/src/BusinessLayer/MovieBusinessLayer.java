package BusinessLayer;

import ApplicationVariables.DataLayerType;
import ClassLayer.*;
import DataLayer.MovieData;
import datalayerdb.MovieDataDB;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mqul
 */
public class MovieBusinessLayer {
    
    //retrieve film list from data source
    public Films getFilms(DataLayerType dataType, String csvPath){
        try{ 
            switch(dataType){
                case CSV: 
                    return new MovieData().getCsvData(csvPath);
                case DATABASE:
                    return new MovieDataDB().getFilms();
                default:
                    throw new RuntimeException("Unknown Data Type: " + dataType.toString());
            }
        }catch(SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            return null;
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
    
    //--------------------------
    public Film getFilmFromSimplisticFilm(String filmID){
        return this.getFilms(DataLayerType.CSV, null)
                        .stream()
                        .filter(f -> f.filmID.equals(filmID))
                        .findFirst().get();
    }
}

