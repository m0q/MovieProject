/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PresentationLayer;

import ApplicationVariables.AppVariables;
import ClassLayer.Person;
import ClassLayer.SimplisticFilm;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.model.SelectItem;

/**
 *
 * @author mqul
 */
public abstract class BaseBean {
    
    protected <T>List populateDropDownList(List<T> dataSource){
        List<SelectItem> siList = new ArrayList();
         
        if(dataSource.size() > 1){
            //No selection option = <--SELECT--> 
            SelectItem noSelect = new SelectItem(null, AppVariables.WebProperties.dropDownDefault);
            noSelect.setNoSelectionOption(true);
            siList.add(noSelect);
        }
        
        if(dataSource != null && dataSource.get(0) instanceof SimplisticFilm){
            List<SimplisticFilm> tmpList = (List<SimplisticFilm>)dataSource;
            siList.addAll(tmpList.stream()
                 .map(f -> new SelectItem(f.getFilmID(), f.getFilmName()))
                 .collect(Collectors.toList()));
        }else if(dataSource != null && dataSource.get(0) instanceof Person){
            List<Person> tmpList = (List<Person>)dataSource;
            siList.addAll(tmpList.stream()
                 .map(p -> new SelectItem(p.getID(), p.getName()))
                 .collect(Collectors.toList()));
        }else if(dataSource != null && dataSource.get(0) instanceof String){
            List<String> tmpList = (List<String>)dataSource;
            tmpList.stream()
                .map(a -> siList.add(new SelectItem(a)))
                .collect(Collectors.toList());
        }
        
        return siList;
    }
    
}
