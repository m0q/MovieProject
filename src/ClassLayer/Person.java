/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassLayer;

/**
 *
 * @author mqul
 */
public class Person {
    public String personID, personName;
    
    public Person(String ID, String name){
        personID = ID;
        personName = name;
    }
    
    public String getID(){return personID;}
    public String getName(){return personName;}
    public void setID(String ID){personID = ID;}
    public void setName(String name){personName = name;}
}
