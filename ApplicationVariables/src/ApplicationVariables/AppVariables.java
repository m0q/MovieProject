package ApplicationVariables;

/**
 *
 * @author mqul
 */
public class AppVariables {
    
    /**   String Array Index   **/
    public static int filmID = 0;
    public static int filmName = 1;
    public static int imdbRating = 2;
    public static int filmYear = 7;
    public static int directorID = 3;
    public static int directorName = 4;
    public static int actorID = 5;
    public static int actorName = 6;
        
    public static class CSV{      
        /**   CSV Path   **/
        public static String FILE_PATH = "/Users/mqul/NetBeansProjects/NovusMovieProject/TestData.csv";
        public static String EXTENDED_FILE_PATH = "/Users/mqul/NetBeansProjects/NovusMovieProject/ExtendedTestData.csv";
    }
    
    public static class Database{
        /**   DB Index   **/
        public static int filmID = 1;
        public static int filmName = 2;
        public static int imdbRating = 3;
        public static int filmYear = 8;
        public static int directorID = 4;
        public static int directorName = 5;
        public static int actorID = 6;
        public static int actorName = 7;
        
        /**   Stored Procedure   **/
        public static String storedProcedureName = "getAllDetails()";
        
        public static String connectionString = "jdbc:mysql://localhost:3306/db_Movie?useSSL=false";
        public static String mysqlDriver = "com.mysql.jdbc.Driver";
        public static String username = "root";
        public static String password = "";
    }
    
    public static class Cache{
        /**   Cache Name   **/ 
        public static String filmCacheName = "Cache_Film";
    }
    
    public static class WebProperties{
        public static String imdbFilmURL = "http://www.imdb.com/title/tt%s";
        public static String imdbProfileURL = "http://www.imdb.com/name/nm%s";
        
        /**   DropDown Default   **/
        public static final String dropDownDefault = "--SELECT--";
    }   
}
