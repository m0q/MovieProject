package datalayerdb;

import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import ClassLayer.Films;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mqul
 */
public class MovieDataDB {
    static final String dbURL = "jdbc:mysql://localhost:3306/db_Movie?useSSL=false";
    static final String username = "root", password = "";
    static Connection conn = null; 
    static ResultSet rs = null;
    Films films = null;
    
    public MovieDataDB() throws SQLException{
        conn = DriverManager.getConnection(dbURL,username,password);
        films = new Films();
    }
    
    public Films getFilms() throws SQLException{
        ResultSet tmpRS = conn.prepareStatement("SELECT film_id FROM Films").executeQuery();
        
        while(tmpRS.next()){
            getDBData(Integer.parseInt(tmpRS.getString("film_id")));
        }
        tmpRS.close();
        return films;
    }
    
    private void getDBData(int filmID) throws SQLException{
        Film film = new Film();
        
        try(CallableStatement cs = conn.prepareCall("{CALL getFilmDetails(?)}")){
            
            cs.setInt(1, filmID);
            
            boolean hasResults = cs.execute();
            int rsCount = 1;
            
            do{
               /* ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount(); */
               
                rs = cs.getResultSet(); //TO-DO: Close rs connection
                
                switch(rsCount){
                    case 1: 
                        film = getFilm(rs); 
                        rsCount++; 
                        break;
                    case 2: 
                        film.actors.add(getActor(rs)); 
                        rsCount++; 
                        break;
                    case 3: 
                        film.directors.add(getDirector(rs)); 
                        break;
                }
            }while(hasResults = cs.getMoreResults());
            
            films.add(film);
            
        }
    } 
    
    private Film getFilm(ResultSet rs) throws SQLException{
        String filmID = null, filmName = null, imdbRating = null, filmYear = null;
        
        if(rs.next()){
            filmID = rs.getString(4); 
            filmName = rs.getString(2);
            imdbRating = rs.getString(5);
            filmYear = rs.getString(3);
            return new Film(filmID, filmName, imdbRating, filmYear);
        }else{
            return new Film("[archived]","[archived]","[archived]","[archived]");
        }
    }
    
    private Actor getActor(ResultSet rs) throws SQLException{
        String actorName = null, actorID = null;
        
        if(rs.next()){
            actorName = String.format("%s %s", rs.getString(2), rs.getString(3)); 
            actorID = rs.getString(4);
            return new Actor(actorID, actorName);
        }else{
            return null;
        }
    }
    
    private Director getDirector(ResultSet rs) throws SQLException{
        String directorName = null, directorID = null;
        
        if(rs.next()){
            directorName = String.format("%s %s", rs.getString(2), rs.getString(3)); 
            directorID = rs.getString(4);
            return new Director(directorID, directorName);
        }else{
            return null;
        }
    }
}


