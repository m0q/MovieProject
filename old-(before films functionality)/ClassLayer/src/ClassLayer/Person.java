package ClassLayer;

/**
 *
 * @author mqul
 * 
 * super class for: Actor/Director class
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
