package BusinessLayer;

import ApplicationVariables.AppVariables;
import ClassLayer.*;
import DataLayer.*;
import java.util.List;
import caching.SimpleCaching;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mqul
 */
public class MovieBusinessLayer {
    String message;
    
    //retrieve film list from data source or cache 
    public Films getFilms() throws SQLException, ClassNotFoundException{
        if(SimpleCaching.get(AppVariables.Cache.filmCacheName) == null){
           /* Films films = new MovieData().getFilmData(AppVariables.CSV.EXTENDED_FILE_PATH);
            SimpleCaching.put(AppVariables.Cache.filmCacheName, films);*/
            
            //register and load the db driver - must happen before db connection is made
            Class.forName(AppVariables.Database.mysqlDriver); 

            Connection conn = DriverManager.getConnection(AppVariables.Database.connectionString, AppVariables.Database.username, AppVariables.Database.password);
            Films films = new MovieData().getFilmData(conn);
            SimpleCaching.put(AppVariables.Cache.filmCacheName, films);     
        }   
        return SimpleCaching.get(AppVariables.Cache.filmCacheName);
    }
    
    public boolean importData(String fileName) throws ClassNotFoundException, SQLException{
        Films films = new MovieData().getFilmData("/Users/mqul/NetBeansProjects/NovusMovieProject/Data/" + fileName);
        boolean isSuccess = false;
        
        Class.forName(AppVariables.Database.mysqlDriver); 
        Connection conn = DriverManager.getConnection(AppVariables.Database.connectionString, AppVariables.Database.username, AppVariables.Database.password);
        
        DatabaseAccess md = new DatabaseAccess();
        
        for(Film film : films){
            md.putMovieData(conn, film);//.putFilmData(conn, film, film.actors.get(0), film.directors.get(0));
            
            for(Actor actor : film.actors){
                md.putActorData(conn, film.filmID, actor);
            }
            
            for(Director director : film.directors){
                md.putDirectorData(conn, film.filmID, director);
            }
        }
        
        message = md.getResultMessage();    
        
        if(isSuccess){
            SimpleCaching.remove(AppVariables.Cache.filmCacheName);
        }
        
        return isSuccess;
    }
    
    //Films
    public List<SimplisticFilm> getDistinctSimplisticFilmsFromFilms(Films films){
        return (films == null) ? null : films.toListSimplisticFilm();
    }
    
    public Films getFilmsSubset(String filmID, String directorID, String actorID, String filmYear, String filmRating, Films films){
        return films.getFilmsFilteredSubset(filmID, directorID, actorID, filmYear, filmRating);
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
    
    //Film Years
    public List<String> getDistinctYearsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctYear();
    }
    
    public List<String> getDistinctYear(Films films, String year){
        return films.getDistinctYear(year);
    }
    
    //Film Ratings
    public List<String> getDistinctRatingsFromFilms(Films films){
        return (films == null) ? null : films.toListDistinctFilmRatings();
    }
    
    //information for table once all dropdown fields are selected
    public Film getFilmFromSimplisticFilm(String filmID) throws SQLException, ClassNotFoundException{
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
    
    //film form to film object
    public boolean insertFilm(String filmID, String filmName, String filmRating, String filmYear,
                              String actorID, String actorName, String directorID, String directorName) throws SQLException, ClassNotFoundException{
        
        Film film = new Film(filmID, filmName, filmRating, filmYear);
        Actor actor = new Actor(actorID, actorName);
        Director director = new Director(directorID, directorName);
        
        Class.forName(AppVariables.Database.mysqlDriver); 
        Connection conn = DriverManager.getConnection(AppVariables.Database.connectionString, AppVariables.Database.username, AppVariables.Database.password);
        
        DatabaseAccess md = new DatabaseAccess();
        boolean isSuccess = md.putFilmData(conn, film, actor, director);
        message = md.getResultMessage();    
        
        if(isSuccess){
            SimpleCaching.remove(AppVariables.Cache.filmCacheName);
        }

        return isSuccess;
    }
    
    public String getMessage(){return message;}
}


