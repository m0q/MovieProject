package ClassLayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mqul
 * 
 * represents a list of Film objects
 */
public class Films extends ArrayList<Film>{
    
    
    /*public List<SimplisticFilm> toSimplisticFilm(){
        //TODO check how to sort in lambda - Java
        return this.stream().map(film -> film.toSimplisticFilm())
                            .sorted(Comparator.comparing(fi -> fi.getFilmName()))
                            .collect(Collectors.toList());
        
    }*/
    
    
    public List<SimplisticFilm> toListOfSimplisticFilms(){
        List<SimplisticFilm> sFilms = new ArrayList();
        for(Film film : this){
            if(film.isValid()){
                sFilms.add(film.toSimplisticFilm());
            }
        }
        return sFilms;
    }
    
}
