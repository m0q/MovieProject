/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class WebDefault implements Serializable{
    
    private MovieBusinessLayer mbl = new MovieBusinessLayer();
    private Films films = mbl.getFilmsFromCSV(AppVariables.FILE_PATH);//this.getFilms();
    private String selectedFilm, selectedDirector, selectedActor;
    
    /*public List getFilms(){
        List<SelectItem> siList = new ArrayList();
        siList.add(new SelectItem(null, "--SELECT--"));
        for(Film f : films){
            siList.add(new SelectItem(f.getFilmID(), f.getFilmName()));
        }
        return siList;
    }*/
    
    public List getFilms(){
        if(isPostback() && films.size() == 1){
            List<SelectItem> siList = new ArrayList();
            
            films.stream()
                    .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                    .collect(Collectors.toList());
                    
            return siList;
        }else{
            List<SelectItem> siList = new ArrayList();
            siList.add(new SelectItem(null, "--SELECT--"));
            films.stream()
                    .map(f -> siList.add(new SelectItem(f.getFilmID(), f.getFilmName())))
                    .collect(Collectors.toList());
            
            return siList;
        }
    }
    
    public List getDirectors(){
        return populateDropDownList(mbl.getDistinctDirectorsFromFilms(films, null)); 
    }
    
    public List getActors(){
        return populateDropDownList(mbl.getDistinctActorsFromFilms(films, null));  
    }
    
    private List populateDropDownList(List<? extends Person> curList){
        if(isPostback() && curList.size() == 1){
            List<SelectItem> siList = new ArrayList();
            
            curList.stream()
                    .map(a -> siList.add(new SelectItem(a.getID(), a.getName())))
                    .collect(Collectors.toList());
                    
            return siList;
        }else{
            List<SelectItem> siList = new ArrayList();
            siList.add(new SelectItem(null, "--SELECT--"));

            curList.stream()
                .map(a -> siList.add(new SelectItem(a.getID(), a.getName())))
                .collect(Collectors.toList());

            return siList;
        }
    }
    
    public void submit(){
        if(isPostback()){
            films = (selectedFilm == null ? films : mbl.getFilmSubsetByMovieID(films, selectedFilm));
            films = (selectedDirector==null ? films : mbl.getFilmSubsetByDirectorID(films, selectedDirector));
            films = (selectedActor==null ? films : mbl.getFilmSubsetByActorID(films, selectedActor));
        }
    }
    
    public void reset() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public static boolean isPostback() {
        return FacesContext.getCurrentInstance().isPostback();
    }
    
    public String getSelectedFilm(){return this.selectedFilm;}
    public void setSelectedFilm(String si){this.selectedFilm = si;}
    public String getSelectedDirector(){return this.selectedDirector;}
    public void setSelectedDirector(String si){this.selectedDirector = si;}
    public String getSelectedActor(){return this.selectedActor;}
    public void setSelectedActor(String si){this.selectedActor = si;}
}