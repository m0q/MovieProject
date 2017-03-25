package PresentationLayer;

import BusinessLayer.MovieBusinessLayer;
import ClassLayer.Actor;
import ClassLayer.Director;
import ClassLayer.Film;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author mqul
 */
@RequestScoped
@Named("associatebean")
public class AssociateBean implements Serializable{
    private String selectedFilm;
    private List<String> actors, directors;
    
    public List<String> getActors(){return actors;}
    public List<String> getDirectors(){return directors;}
    public void setActors(List<String> actors){this.actors = actors;}
    public void setDirectors(List<String> directors){this.directors = directors;}
    
    public String getSelectedFilm(){return selectedFilm;}
    public void setSelectedFilm(String film){this.selectedFilm = film;}
    
    public List<SelectItem> getFilms() throws SQLException, ClassNotFoundException{
        List<SelectItem> tmpList = new ArrayList<SelectItem>();
        tmpList.add(new SelectItem("<--Select-->"));
        tmpList.addAll(new MovieBusinessLayer().getDistinctFilmsFromDB().stream()
               .map(f -> new SelectItem(f.filmID, f.filmName)).collect(Collectors.toList()));
        
        return tmpList;
    }
    
    public void submitForm() throws ClassNotFoundException, SQLException{
       MovieBusinessLayer mbl = new MovieBusinessLayer();
       mbl.associateActorsWithFilm(actors, selectedFilm);
       mbl.associateDirectorsWithFilm(directors, selectedFilm);
    }
}
