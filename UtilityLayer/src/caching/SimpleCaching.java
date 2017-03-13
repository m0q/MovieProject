package caching;

import ClassLayer.Films;
import java.util.stream.Collectors;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class SimpleCaching{
    
    static CacheManager cacheManager;
    static Cache<String, Films> cache;
   
    public static Films get(String key) {
        Films value;
        if(cache == null){
            return null;
        }
        
        value = (Films) cache.get(key);
      
        if(value != null) {
            return value ;
        }else{
            return null;
        }
    }
    
    //creates a cache manager instances and inserts new key-value set into the cache
    public static void put(String key, Films value) {
        if(cache != null){
            cache.put(key, value);
            return;
        }
        
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                            CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Films.class, 
                            ResourcePoolsBuilder.heap(10))).build(); 
        cacheManager.init();
        cache = cacheManager.getCache("preConfigured", String.class, Films.class);
        
        cache.put(key, value);
    }
    
    public static void remove(String key){
        cache.remove(key);
    }
}
