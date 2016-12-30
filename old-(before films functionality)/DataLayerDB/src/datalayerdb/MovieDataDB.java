package datalayerdb;

import ApplicationVariables.AppVariables;
import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import ClassLayer.Films;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public MovieDataDB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); //register the db driver 
        conn = DriverManager.getConnection(dbURL,username,password); //establish connection
        films = new Films();
    }
    
    public Films getFilms() throws SQLException{
        ResultSet tmpRS = conn.prepareStatement("SELECT film_id FROM Films WHERE is_archived = FALSE").executeQuery();

        while(tmpRS.next()){
            getDBData(Integer.parseInt(tmpRS.getString(AppVariables.dbFilmID)));
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
                
                //TO-DO: fix the problems where you should be able to add multiple
                //       actors and directors to a single film
                switch(rsCount){
                    case 1: 
                        film = getFilm(rs); 
                        rsCount++; 
                        break;
                    case 2: 
                        film.actors.addAll(getActor(rs)); 
                        rsCount++; 
                        break;
                    case 3: 
                        film.directors.addAll(getDirector(rs)); 
                        break;
                }
            }while(hasResults = cs.getMoreResults());
            
            films.add(film);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    private Film getFilm(ResultSet rs) throws SQLException{
        String filmID = null, filmName = null, imdbRating = null, filmYear = null;
        
        if(rs.next()){
            filmID = rs.getString(AppVariables.dbImdbID); 
            filmName = rs.getString(AppVariables.dbFilmName);
            imdbRating = rs.getString(AppVariables.dbFilmRating);
            filmYear = rs.getString(AppVariables.dbFilmYear);
            return new Film(filmID, filmName, imdbRating, filmYear);
        }else{
            return null;
        }
    }
    
    private List<Actor> getActor(ResultSet rs) throws SQLException{
        String actorName = null, actorID = null;
        List<Actor> tmpList = new ArrayList<Actor>();
        
        while(rs.next()){
            actorName = String.format("%s %s", rs.getString(AppVariables.dbActorFirstName), 
                                               rs.getString(AppVariables.dbActorLastName)); 
            actorID = rs.getString(AppVariables.dbImdbID);
            tmpList.add(new Actor(actorID, actorName));
        }
        
        return tmpList;
    }
    
    private List<Director> getDirector(ResultSet rs) throws SQLException{
        String directorName = null, directorID = null;
        List<Director> tmpList = new ArrayList<Director>();
        
        while(rs.next()){
            directorName = String.format("%s %s", rs.getString(AppVariables.dbDirectorFirstName), 
                                                  rs.getString(AppVariables.dbDirectorLastName)); 
            directorID = rs.getString(AppVariables.dbImdbID);
            tmpList.add(new Director(directorID, directorName));
        }
        
        return tmpList;
    }
}


