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
    
    /**   ensure the name/id values exist   **/
    public boolean isValid(){
        if(this.filmID == null || this.filmID.isEmpty()){
            return false;
        }else if(this.filmName == null || this.filmName.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    public String getFilmID(){return filmID;}
    public String getFilmName(){return filmName;}
    public void setFilmID(String filmID){this.filmID = filmID;}
    public void setFilmName(String filmName){this.filmName = filmName;}
}
