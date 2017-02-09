/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import ClassLayer.Films;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import static org.ehcache.config.builders.UserManagedCacheBuilder.newUserManagedCacheBuilder;

public class SimpleCaching{
    
    static CacheManager cacheManager;
    
    public SimpleCaching() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                            CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Films.class, 
                            ResourcePoolsBuilder.heap(10))) 
                .build(); 
        cacheManager.init();
    }
    
    private static Cache getCache() {
        Cache<String, Films> preConfigured =
        cacheManager.getCache("preConfigured", String.class, Films.class);
        
        return preConfigured;
    }

    public static Films get(String key) {
        final Cache cache = getCache();
        Films value = (Films) cache.get(key);
        
        if(value != null) {
            return value ;
        }else{
            return null;
        }
    }

    public static void put(String key, Films value) {
        final Cache cache = getCache();
        cache.put(key, value);
    }
}
