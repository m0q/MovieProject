package DataLayer;

import ClassLayer.*;
import ApplicationVariables.AppVariables;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author mqul
 * 
 * extract data from CSV or Database and store in respective Object from ClassLayer
 */
public class MovieData {
    
    public Films getCsvData(String csvPath){
        Films films = new Films();
        String[] line;
        
        try(CSVReader csv = new CSVReader(new FileReader(csvPath));){
            String[] headers = csv.readNext(); //read first line for header strings
           
            while((line = csv.readNext()) != null){
                films = storeLine(line, films);
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return films;
    }
    
    /* call stored procedure using given film_id and store results as objects */
    public Films getDatabaseData() throws ClassNotFoundException, SQLException{
        final String dbURL = "jdbc:mysql://localhost:3306/db_Movie?useSSL=false";//AppVariables.Database.url;
        final String username = "root";// AppVariables.Database.username;
        final String password = "";//AppVariables.Database.password;
         
        //register and load the db driver - must happen before db connection is made
        Class.forName(AppVariables.Database.mysqlDriver); 
        
        Connection conn = DriverManager.getConnection(dbURL,username,password); //establish connection 
        Films films = new Films();
     
        try(CallableStatement cs = conn.prepareCall("{CALL getAllDetails()}")){
            boolean hasResults = cs.execute(); //execute stored procedure
            
            //retrieve the returned data (resultset) 
            try(ResultSet rs = cs.getResultSet()){
                while(rs.next()){
                    String[] line = {rs.getString(1),rs.getString(2),rs.getString(3)
                                    ,rs.getString(4),rs.getString(5),rs.getString(6)
                                    ,rs.getString(7),rs.getString(8)};
                                  
                    films = storeLine(line, films);
                }
            }
            
            return films;    
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            conn.close(); //close connection to db once try block is complete
        }
    } 
    
    Films storeLine(String[] line, Films films){
        Films tmpFilms = films;
        
        //ensure record in not already present
        if(tmpFilms.stream().anyMatch(item -> item.filmID.equals(line[AppVariables.CSV.filmID]))){
            Film tmpFilm = tmpFilms.stream().filter(item -> item.filmID.equals(line[AppVariables.CSV.filmID])).findFirst().get();//.collect(Collectors.toList()).get(0);

            if(tmpFilm.directors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.CSV.directorID]))){

            }else{
                Director director = this.getDirectorFromData(line);
                tmpFilm.directors.add(director);
            }
            if(tmpFilm.actors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.CSV.actorID]))){

            }else{
                Actor actor = this.getActorFromData(line);
                tmpFilm.actors.add(actor);
            }
        }else{
            Film film = this.getFilmFromData(line);
            tmpFilms.add(film);
        }
        
        return tmpFilms;
    }
    
    private Director getDirectorFromData(String[] line){
        Director director = new Director(line[AppVariables.CSV.directorID].trim(), 
                                         line[AppVariables.CSV.directorName].trim());
        return director;
    }
    
    private Actor getActorFromData(String[] line){
        Actor actor = new Actor(line[AppVariables.CSV.actorID].trim(), 
                                line[AppVariables.CSV.actorName].trim());
        return actor;
    }
    
    private Film getFilmFromData(String[] line){
        
        Director director = this.getDirectorFromData(line);
        Actor actor = this.getActorFromData(line);
        
        Film film = new Film(line[AppVariables.CSV.filmID].trim(),
                             line[AppVariables.CSV.filmName].trim(),
                             line[AppVariables.CSV.imdbRating].trim(),
                             line[AppVariables.CSV.filmYear].trim());
        film.directors.add(director);
        film.actors.add(actor);
        
        return film;
    }
}
