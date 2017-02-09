package ApplicationVariables;

/**
 *
 * @author mqul
 */
public class AppVariables {
    
    public static class CSV{
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
        public static final String dropDownDefault = "--SELECT--";
    }
    
    public static class Database{
        public static String url = "jdbc:mysql://localhost:3306/db_Movie?useSSL=false";
        public static String mysqlDriver = "com.mysql.jdbc.Driver";
        public static String username = "root";
        public static String password = "";
    }
    
    public static class Cache{
        /**   Cache Name   **/ 
        public static String filmCacheName = "Cache_Film";
    }
    
}
