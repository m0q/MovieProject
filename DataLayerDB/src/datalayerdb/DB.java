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
public class DB {
    static final String dbURL = "jdbc:mysql://localhost:3306/db_Movie?useSSL=false";
    static final String username = "root", password = "";
    static Connection conn = null; 
    static ResultSet rs = null;
    Films films = null;
    
    public DB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); //register the db driver 
        conn = DriverManager.getConnection(dbURL,username,password); //establish connection
        films = new Films();
    }
    
    /* call stored procedure using given film_id and store results as objects */
    public Films getDBData() throws SQLException{
        
        try(CallableStatement cs = conn.prepareCall("{CALL getAllDetails()}")){

            boolean hasResults = cs.execute();
            
            rs = cs.getResultSet();
            
            while(rs.next()){
                
                String[] line = {rs.getString(1)
                                ,rs.getString(2)
                                ,rs.getString(3)
                                ,rs.getString(4)
                                ,rs.getString(5)
                                ,rs.getString(6)
                                ,rs.getString(7)
                                ,rs.getString(8)};
                
                 if(films.stream().anyMatch(item -> item.filmID.equals(line[AppVariables.filmID]))){
                    Film tmpFilm = films.stream().filter(item -> item.filmID.equals(line[AppVariables.filmID])).findFirst().get();//.collect(Collectors.toList()).get(0);
                    
                    if(tmpFilm.directors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.directorID]))){
                    
                    }else{
                        Director director = this.getDirectorFromDB(line);
                        tmpFilm.directors.add(director);
                    }
                    if(tmpFilm.actors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.actorID]))){
                    
                    }else{
                        Actor actor = this.getActorFromDB(line);
                        tmpFilm.actors.add(actor);
                    }
                }else{
                    Film film = this.getFilmFromDB(line);
                    films.add(film);
                }
                
            }
            
            return films;
            
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    } 
    
    private Director getDirectorFromDB(String[] line){
        Director director = new Director(line[AppVariables.directorID].trim(), 
                                         line[AppVariables.directorName].trim());
        return director;
    }
    
    private Actor getActorFromDB(String[] line){
        Actor actor = new Actor(line[AppVariables.actorID].trim(), 
                                line[AppVariables.actorName].trim());
        return actor;
    }
    
    private Film getFilmFromDB(String[] line){
        
        Director director = this.getDirectorFromDB(line);
        Actor actor = this.getActorFromDB(line);
        
        Film film = new Film(line[AppVariables.filmID].trim(),
                             line[AppVariables.filmName].trim(),
                             line[AppVariables.imdbRating].trim(),
                             line[AppVariables.filmYear].trim());
        film.directors.add(director);
        film.actors.add(actor);
        
        return film;
    }
}


