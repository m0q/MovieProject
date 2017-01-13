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
    
    public Films() { }

    public Films(List<Film> films){
        this.addAll(films);
    }
    
    //Films
    public Films getFilmsFilteredSubset(String filmID, String directorID, String actorID){
        Films tmpFilms = new Films();
        tmpFilms.addAll(this.stream().filter(f -> f.filmID.equals((filmID == null) ? f.filmID : filmID))
                                     .filter(f -> f.directors.stream().anyMatch(p -> p.getID().equals((directorID == null) ? p.getID() : directorID)))
                                     .filter(f -> f.actors.stream().anyMatch(p -> p.getID().equals((actorID == null) ? p.getID() : actorID)))
                                     .sorted(Comparator.comparing(f -> f.getFilmName()))
                                     .collect(Collectors.toList()));
        return tmpFilms;
    }
    
    
    public List<SimplisticFilm> toListSimplisticFilm(){
        return this.stream().sorted(Comparator.comparing(fi -> fi.getFilmName()))
                            .collect(Collectors.toList());
        
    }
    
    public List<SimplisticFilm> getDistinctSimplisticFilm(String filmID){
        return this.stream().filter(f -> f.filmID.equals(filmID))
                            .collect(Collectors.toList());
    }
    
    //Directors
    public List<Director> toListDistinctDirector(){
        List <Director> tmpList = new ArrayList();
            
        this.stream().flatMap(film -> film.directors.stream()
                    .filter(dir -> tmpList.stream()
                            .noneMatch(di -> di.getID().equals(dir.getID())))
                    .map(nDir -> tmpList.add(nDir)))
                    .collect(Collectors.toList());

        tmpList.sort(Comparator.comparing(c -> c.getName()));

        return tmpList;   
    }
    
    public List<Director> getDistinctDirector(String directorID){
        List <Director> tmpList = new ArrayList();

        this.stream().flatMap(film -> film.directors.stream()
                    .filter(dir -> tmpList.stream()
                            .noneMatch(di -> di.getID().equals(dir.getID())) && dir.getID().equals(directorID))
                    .map(nDir -> tmpList.add(nDir)))
                    .collect(Collectors.toList());

        return tmpList;  
    }
    
    //Actors
    public List<Actor> toListDistinctActor(){
        List <Actor> tmpList = new ArrayList();
            
        this.stream().flatMap(film -> film.actors.stream()
                    .filter(act -> tmpList.stream()
                            .noneMatch(ac -> ac.getID().equals(act.getID())))
                    .map(nAct -> tmpList.add(nAct)))
                    .collect(Collectors.toList());

        tmpList.sort(Comparator.comparing(c -> c.getName()));

        return tmpList;   
    }
    
    public List<Actor> getDistinctActor(String actorID){
        List <Actor> tmpList = new ArrayList();

        this.stream().flatMap(film -> film.actors.stream()
                    .filter(act -> tmpList.stream()
                            .noneMatch(ac -> ac.getID().equals(act.getID())) && act.getID().equals(actorID))
                    .map(nAct -> tmpList.add(nAct)))
                    .collect(Collectors.toList());

        return tmpList;  
    }
}
