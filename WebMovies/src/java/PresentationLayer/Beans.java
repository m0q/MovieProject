package PresentationLayer;

import ApplicationVariables.DataLayerType;
import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import ClassLayer.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
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
    //private Films films = mbl.getFilms(DataLayerType.CSV, AppVariables.FILE_PATH);
    //private Films films = mbl.getFilms(DataLayerType.DATABASE, null);
    private String selectedFilm, selectedDirector, selectedActor;
    List<Director> directors;
    List<Actor> actors;
    List<SimplisticFilm> sFilms;
    private boolean isSubmitted = false;
    
    @PostConstruct
    protected void load(){
        if (this.isPostback()){
            try{
                String filmID = (selectedFilm == null ? null : selectedFilm);
                String directorID = (selectedDirector == null ? null : selectedDirector);
                String actorID = (selectedActor == null ? null : selectedActor);

                populateDropDownsWithFilteredData(filmID, directorID, actorID);
                
                /*TO-DO:
                    check why auto fill for all fields does not trigger the button
                    previously implemented. - Hiding the button until all fields
                    are complete only work after you select more than one field.
                    Selecting 1 field that completes all other fields does not show
                    the button.
                */
                if(filmID != null && directorID != null && actorID != null && isSubmitted){
                    this.populateFields(filmID, directorID, actorID);
                }
            }catch(Exception e){
                System.out.println(e.getMessage() + " <<<ERROR");
            }
        }else{
            populateDropDownsWithOriginalData();
        }
    }
    
    private void populateDropDownsWithOriginalData(){
        try{
            //Films films = mbl.getFilms(DataLayerType.CSV, AppVariables.FILE_PATH);
            Films films = mbl.getFilms();
            
            directors = mbl.getDistinctDirectorsFromFilms(films);
            actors = mbl.getDistinctActorsFromFilms(films);
            sFilms = mbl.getDistinctSimplisticFilmsFromFilms(films);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    private void populateDropDownsWithFilteredData(String filmID, String directorID, String actorID){
        try{
           //Films films = mbl.getFilms(DataLayerType.CSV, AppVariables.FILE_PATH);
            Films films = mbl.getFilms();
            
            Films tmp = mbl.getFilmsSubset(filmID, directorID, actorID, films);

            actors = (actorID == null) ? mbl.getDistinctActorsFromFilms(tmp) : mbl.getDistinctActor(tmp, actorID);
            directors = (directorID == null) ? mbl.getDistinctDirectorsFromFilms(tmp) : mbl.getDistinctDirector(tmp, directorID);
            sFilms = (filmID == null) ? mbl.getDistinctSimplisticFilmsFromFilms(tmp) : tmp.getDistinctSimplisticFilm(filmID);
        }catch(Exception e){
               
        }
    }
    //--------------------------------------------------------------------------
    public List populateFilms(List<SimplisticFilm> films){
        if(isPostback() && films.size() == 1){
            List<SelectItem> siList = new ArrayList();
            
            films.stream()
                 .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                 .collect(Collectors.toList());
                    
            return siList;
        }else{
            List<SelectItem> siList = new ArrayList();
            
            //<--SELECT--> Option
            SelectItem noSelect = new SelectItem(null, AppVariables.DropDownDefault);
            noSelect.setNoSelectionOption(true);
            siList.add(noSelect);
            
            films.stream()
                 .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                 .collect(Collectors.toList());
            
            return siList;
        }
    }
    
    public List getFilms(){
        return populateFilms(this.sFilms);
    }
    //populate and return a list of directors based on what the films list currently holds
    public List getDirectors(){
        return populateDropDownList(this.directors);
        //return populateDropDownList(mbl.getDistinctDirectorsFromFilms(films)); 
    }
    
    //populate and return a list of actors based on what the films list currently holds
    public List getActors(){
        return populateDropDownList(this.actors);
        //return populateDropDownList(mbl.getDistinctActorsFromFilms(films));  
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
            
            //TODO: <--SELECT--> Option
            siList.add(new SelectItem(null, AppVariables.DropDownDefault));

            curList.stream()
                .map(a -> siList.add(new SelectItem(a.getID(), a.getName())))
                .collect(Collectors.toList());

            return siList;
        }
    }
    
    //http://ruleoftech.com/2012/jsf-1-2-and-getting-selected-value-from-dropdown
    public void valueChangeMethodFilm(ValueChangeEvent e){
        if(isPostback()){
            selectedFilm = e.getNewValue().toString();
            this.load();
            /*films = (selectedFilm == null ? films : mbl.getFilmsSubset(selectedFilm, selectedDirector, selectedActor, films));*/
        }
    }
    
    public void valueChangeMethodDir(ValueChangeEvent e){
        if(isPostback()){
            selectedDirector = e.getNewValue().toString();
            this.load();
            /*films = (selectedDirector == null ? films : mbl.getFilmsSubset(selectedFilm, selectedDirector, selectedActor, films));*/
        }
    }
    
    public void valueChangeMethodAct(ValueChangeEvent e){
        if(isPostback()){
            selectedActor = e.getNewValue().toString();
            this.load();
            /*films = (selectedActor == null ? films : mbl.getFilmsSubset(selectedFilm, selectedDirector, selectedActor, films));*/
        }
    }
    
    //completely refresh the page to initial state
    public void reset() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public void submitForm(){
        setIsSubmitted(true);
        this.load();
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
    public boolean getIsSubmitted(){return this.isSubmitted;}
    public void setIsSubmitted(Boolean isSubmitted){this.isSubmitted = isSubmitted;}
    
    
    //-------------------------------------------------
    //   Populating strings with selected film data
    //-------------------------------------------------
    private String filmID, filmName, filmYear, imdbRating;
    private String actorID, actorName;
    private String directorID, directorName;
    
    public void populateFields(String filmID, String directorID, String actorID){
        try{
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            Film film = mbl.getFilmFromSimplisticFilm(filmID);
            
            this.filmID = film.getFilmID();
            this.filmName = film.getFilmName();
            this.filmYear = film.getFilmYear();
            this.imdbRating = film.getFilmRating();
            
            Director director = mbl.getDirectorFromSimplisticFilm(film, directorID);
            this.directorID = director.getID();
            this.directorName = director.getName();
            
            Actor actor = mbl.getActorFromSimplisticFilm(film, actorID);
            this.actorID = actor.getID();
            this.actorName = actor.getName();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getFilmID() {return filmID;}
    public void setFilmID(String filmID) {this.filmID = filmID;}

    public String getFilmName() {return filmName;}
    public void setFilmName(String filmName) {this.filmName = filmName;}

    public String getFilmYear() {return filmYear;}
    public void setFilmYear(String filmYear) {this.filmYear = filmYear;}

    public String getImdbRating() {return imdbRating;}
    public void setImdbRating(String imdbRating) {this.imdbRating = imdbRating;}

    public String getActorID() {return actorID;}
    public void setActorID(String actorID) {this.actorID = actorID;}
    public String getActorName() {return actorName;}
    public void setActorName(String actorName) {this.actorName = actorName;}

    public String getDirectorID() {return directorID;}
    public void setDirectorID(String directorID) {this.directorID = directorID;}
    public String getDirectorName() {return directorName;}
    public void setDirectorName(String directorName) {this.directorName = directorName;}
}