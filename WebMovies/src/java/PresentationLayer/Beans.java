package PresentationLayer;

import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import ClassLayer.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mqul
 */
@ManagedBean(name="bean")
@ViewScoped
public class Beans implements Serializable{
    
    private MovieBusinessLayer mbl = new MovieBusinessLayer();
    private Films films = mbl.getFilmsFromCSV("/Users/mqul/NetBeansProjects/NovusMovieProject/TestData.csv");//AppVariables.FILE_PATH);
    private String selectedFilm, selectedDirector, selectedActor;
    
    public List getFilms(){
        if(isPostback() && films.size() == 1){
            List<SelectItem> siList = new ArrayList();
            
            mbl.getDistinctSimplisticFilmFromFilms(films, null).stream()
                    .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                    .collect(Collectors.toList());
                    
            return siList;
        }else{
            List<SelectItem> siList = new ArrayList();
            siList.add(new SelectItem(null, AppVariables.DropDownDefault));
            mbl.getDistinctSimplisticFilmFromFilms(films, null).stream()
                    .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                    .collect(Collectors.toList());
            
            return siList;
        }
    }
    
    //populate and return a list of directors based on what the films list currently holds
    public List getDirectors(){
        return populateDropDownList(mbl.getDistinctDirectorsFromFilms(films, null)); 
    }
    
    //populate and return a list of actors based on what the films list currently holds
    public List getActors(){
        return populateDropDownList(mbl.getDistinctActorsFromFilms(films, null));  
    }
    
    /*
        takes a generalised collector list - either Actor or Director
        converters each set of actor/director into a list of selectitem for the
        dropdown. If theres only a single item in the list, display it on the
        dropdown as a default.
        
        generalised collector: http://stackoverflow.com/questions/17834145/whats-the-use-of-saying-extends-someobject-instead-of-someobject/17834223
    */
    private List populateDropDownList(List<? extends Person> curList){
        if(isPostback() && curList.size() == 1){
            List<SelectItem> siList = new ArrayList();
            
            curList.stream()
                    .map(a -> siList.add(new SelectItem(a.getID(), a.getName())))
                    .collect(Collectors.toList());
                    
            return siList;
        }else{
            List<SelectItem> siList = new ArrayList();
            siList.add(new SelectItem(null, AppVariables.DropDownDefault));

            curList.stream()
                .map(a -> siList.add(new SelectItem(a.getID(), a.getName())))
                .collect(Collectors.toList());

            return siList;
        }
    }
    
    //action handler on postback
    public void submit(){
        if(isPostback()){
            films = (selectedFilm == null ? films : mbl.getFilmSubsetByMovieID(films, selectedFilm));
            films = (selectedDirector==null ? films : mbl.getFilmSubsetByDirectorID(films, selectedDirector));
            films = (selectedActor==null ? films : mbl.getFilmSubsetByActorID(films, selectedActor));
        }
    }
    
    //completely refresh the page to initial state
    public void reset() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    //check if status of page is postback
    public static boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }
    
    //+getters, +setters
    public String getSelectedFilm(){return this.selectedFilm;}
    public void setSelectedFilm(String si){this.selectedFilm = si;}
    public String getSelectedDirector(){return this.selectedDirector;}
    public void setSelectedDirector(String si){this.selectedDirector = si;}
    public String getSelectedActor(){return this.selectedActor;}
    public void setSelectedActor(String si){this.selectedActor = si;}
}