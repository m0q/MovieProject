package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import ClassLayer.Films;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("associatebean")
public class AssociateBean implements Serializable{
    private String selectedFilm;
    private List<String> actorList, directorList;
    
    public List<String> getActorList(){return actorList;}
    public List<String> getDirectorList(){return directorList;}
    public void setActorList(List<String> actorList){this.actorList = actorList;}
    public void setDirectorList(List<String> directorList){this.directorList = directorList;}
    
    public String getSelectedFilm(){return selectedFilm;}
    public void setSelectedFilm(String film){this.selectedFilm = film;}
    
    public List<SelectItem> getFilms() throws SQLException, ClassNotFoundException{
        List<SelectItem> tmpList = new ArrayList<SelectItem>();
        tmpList.add(new SelectItem("<--Select-->"));
        tmpList.addAll(new MovieBusinessLayer().getDistinctFilmsFromDB().stream()
               .map(f -> new SelectItem(f.filmID, f.filmName)).collect(Collectors.toList()));
        
        return tmpList;
    }
    
    public List<SelectItem> getActors(){
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        try{
            List<SelectItem> tmpItems = new ArrayList<>();
            mbl.getDistinctActorsFromDB().stream()
                    .map(actor -> tmpItems.add(new SelectItem(actor.personID, actor.personName)))
                    .collect(Collectors.toList());
            
            return tmpItems;
        }catch(SQLException | ClassNotFoundException se){
            se.printStackTrace();
            return null;
        }
    }
    
    public List<SelectItem> getDirectors(){
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        try{
            List<SelectItem> tmpItems = new ArrayList<>();
            mbl.getDistinctDirectorsFromDB().stream()
                    .map(director -> tmpItems.add(new SelectItem(director.personID, director.personName)))
                    .collect(Collectors.toList());
            
            return tmpItems;
        }catch(SQLException | ClassNotFoundException se){
            se.printStackTrace();
            return null;
        }
    }
    
    public void filmValueChanged(ValueChangeEvent e) throws SQLException, ClassNotFoundException{
        selectedFilm = e.getNewValue().toString();
        
        MovieBusinessLayer mbl = new MovieBusinessLayer();
        
        Films films = mbl.getFilmsSubset(selectedFilm, null, null, null, null, mbl.getFilms());
       
        this.submitForm();
    //    actorList = films.toListDistinctActor().stream().map(a -> a.personID).collect(Collectors.toList());
    //    directorList = films.toListDistinctDirector().stream().map(d -> d.personID).collect(Collectors.toList());
    }
    
    public void submitForm() throws ClassNotFoundException, SQLException{
       actorList = this.getActors().subList(5, 10).stream().map(c -> (String)c.getValue()).collect(Collectors.toList());
        //MovieBusinessLayer mbl = new MovieBusinessLayer();
       //mbl.associateActorsWithFilm(actorList, selectedFilm);
       //mbl.associateDirectorsWithFilm(directorList, selectedFilm);
    }
}
