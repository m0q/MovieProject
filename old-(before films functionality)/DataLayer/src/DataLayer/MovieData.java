package DataLayer;

import ClassLayer.*;
import ApplicationVariables.AppVariables;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author mqul
 * 
 * extract details from CSV and store them in Lists/Objects from ClassLayer
 */
public class MovieData {
    
    String[] line; 
    
    public Films getCsvData(String csvPath){
        Films films = new Films();
        
        try(CSVReader csv = new CSVReader(new FileReader(csvPath));){
            
            String[] headers = csv.readNext(); //read first line for header strings
                    
            while((line = csv.readNext()) != null){
                if(films.stream().anyMatch(item -> item.filmID.equals(line[AppVariables.filmID]))){
                    Film tmpFilm = films.stream().filter(item -> item.filmID.equals(line[AppVariables.filmID])).findFirst().get();//.collect(Collectors.toList()).get(0);
                    
                    if(tmpFilm.directors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.directorID]))){
                    
                    }else{
                        Director director = this.getDirectorFromCSV(line);
                        tmpFilm.directors.add(director);
                    }
                    if(tmpFilm.actors.stream().anyMatch(item -> item.getID().equals(line[AppVariables.actorID]))){
                    
                    }else{
                        Actor actor = this.getActorFromCSV(line);
                        tmpFilm.actors.add(actor);
                    }
                }else{
                    Film film = this.getFilmFromCSV(line);
                    films.add(film);
                }
            }
        }catch(IOException ex){
            //do something 
        }
        
        return films;
    }
    
    private Director getDirectorFromCSV(String[] line){
        Director director = new Director(line[AppVariables.directorID].trim(), 
                                         line[AppVariables.directorName].trim());
        return director;
    }
    
    private Actor getActorFromCSV(String[] line){
        Actor actor = new Actor(line[AppVariables.actorID].trim(), 
                                line[AppVariables.actorName].trim());
        return actor;
    }
    
    private Film getFilmFromCSV(String[] line){
        
        Director director = this.getDirectorFromCSV(line);
        Actor actor = this.getActorFromCSV(line);
        
        Film film = new Film(line[AppVariables.filmID].trim(),
                             line[AppVariables.filmName].trim(),
                             line[AppVariables.imdbRating].trim(),
                             line[AppVariables.filmYear].trim());
        film.directors.add(director);
        film.actors.add(actor);
        
        return film;
    }
}
