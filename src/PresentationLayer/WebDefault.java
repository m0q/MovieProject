/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import ApplicationVariables.AppVariables;
import BusinessLayer.MovieBusinessLayer;
import ClassLayer.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 *
 * @author mqul
 */
public class WebDefault implements java.io.Serializable{
    
    void page_load(/*...*/){
        //if(FacesContext.getCurrentInstance().isPostback()){
            try{
                MovieBusinessLayer mbl = new MovieBusinessLayer();
                
                String filmID = ""; //(DropDownListFilms.SelectedValue == av.SystemValues.DropDownLists.DefaultValue ? null : DropDownListFilms.SelectedValue);
                String directorID = "";
                String actorID = "";
                
                populateDropDownsWithFilteredData(filmID, directorID, actorID);
            }catch(Exception e){
                //do something with e
            }
        //}else{
            populateDropDownsWithOriginalData();
        //}
    }
    
    private void populateDropDowns(boolean addBlankItem, List<Film> films, List<Director> directors, List<Actor> actors){
        populateDropDownList(true, "something", films, "textfield", "datavalue");
        populateDropDownList(true, "something", directors, "textfield", "datavalue");
        populateDropDownList(true, "something", actors, "textfield", "datavalue");
    }
    
    private <T> void populateDropDownList(boolean addBlankItem, String controlID, List<T> dataSource, String dataTextField, String dataValueField){
        //Create downdown here DropDownList and add fields
        /*
            DropDownList ddl = (DropDownList)Page.FindControl(controlID);
            ddl.DataTextField = dataTextField;
            ddl.DataValueField = dataValueField;
            ddl.DataSource = datasource;
            ddl.DataBind();
            if (datasource.Count > 1 && addBlankItem) { addBlankItemToDropDownList(ref ddl); }
        */
    }
    
    private void addBlankItemToDropDownList(/*ref DropDownList ddlist*/){
        /*
            ddlist.Items.Insert(0, new ListItem(ddl.DefaultText, ddl.DefaultValue));
            ddlist.SelectedIndex = 0;
        */
    }
    
    private Films getFilms(){
        Films films = new Films();
        
        /*if ((cache.UseCache) && (Cache[cache.FilmCacheName] != null)){
            films = Cache[cache.FilmCacheName] as mcl.Films;
        }else{
            try{
                MovieBusinessLayer bl1 = new MovieBusinessLayer();
                
                films = bl1.getFilmsFromCSV(AppVariables.FILE_PATH);
                if (cache.UseCache) Cache[cache.FilmCacheName] = films;
            }catch(Exception e){
                //do something with e
            }
        }*/

        return films;
    }
    
    private void populateDropDownsWithOriginalData(){
        try{
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            Films films = this.getFilms();
            
            List<Director> directors = mbl.getDistinctDirectorsFromFilms(films, null);
            List<Actor> actors = mbl.getDistinctActorsFromFilms(films, null);
            //get distinct movies
            
            this.populateDropDowns(true/*TO-DO*/, films, directors, actors);
            
        }catch(Exception e){
            //do something with e
        }
    }
    
    private void populateDropDownsWithFilteredData(String filmID, String directorID, String actorID){
        Films films = this.getFilms();
        
        try{
            MovieBusinessLayer mbl = new MovieBusinessLayer();
            
            Films tmpFilms = mbl.getFilmSubset(filmID, directorID, actorID, films);
            List<Director> directors = mbl.getDistinctDirectorsFromFilms(tmpFilms, directorID);
            List<Actor> actors = mbl.getDistinctActorsFromFilms(tmpFilms, actorID);
            //List<SimplisticFilm> sFilms = bl1.GetDistinctSimplisticFilmFromFilms(tmp, filmID);
            
            this.populateDropDowns(true /*TO-DO*/, films, directors, actors);
        }catch(Exception e){
            //do something with e
        }
    }
    
    protected void btnReset_Click(ActionEvent event){
        this.populateDropDownsWithOriginalData();
    }
}