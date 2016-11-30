package ClassLayer;

/**
 *
 * @author mqul
 * 
 * film with minimal detail
 */
public class SimplisticFilm{
    public String filmID, filmName;
    
    public SimplisticFilm(){ }
    public SimplisticFilm(String filmID, String filmName){
        this.filmID = filmID;
        this.filmName = filmName;
    }
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
}
