package ApplicationVariables;

/**
 *
 * @author mqul
 */
public class AppVariables {
    /**   CSV Index   **/
    public static int filmID = 0;
    public static int filmName = 1;
    public static int imdbRating = 2;
    public static int filmYear = 7;
    public static int directorID = 3;
    public static int directorName = 4;
    public static int actorID = 5;
    public static int actorName = 6;
    
    /**   CSV Path   **/
    public static String FILE_PATH = "/Users/mqul/NetBeansProjects/NovusMovieProject/TestData.csv";
    
    /**   CSV Default   **/
    public static final String DropDownDefault = "--SELECT--";
    
    /**   Database Fields   **/
    public static final String dbImdbID = "imdb_id";
    
    public static final String dbFilmID = "film_id";
    public static final String dbFilmName = "film_name";
    public static final String dbFilmYear = "film_year";
    public static final String dbFilmRating = "imdb_rating";
    
    public static final String dbActorFirstName = "actor_firstNames";
    public static final String dbActorLastName = "actor_lastName";
    
    public static final String dbDirectorFirstName = "director_firstNames";
    public static final String dbDirectorLastName = "director_lastName";
}
