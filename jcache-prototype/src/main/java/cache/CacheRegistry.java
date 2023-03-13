package cache;

import java.util.HashMap;

public class CacheRegistry {

    private HashMap<String, CacheManager> registry = new HashMap<String, CacheManager>();
    private static CacheRegistry instance = null;

    private CacheRegistry() {
    }


    public static CacheRegistry getInstance() {
        if (instance == null) {
            instance = new CacheRegistry();
        }
        return instance;
    }

    public void addCacheManager(CacheManager cm) {
        if (cm.getProvider().equals(CacheProviders.REDIS_CACHE_PROVIDER)) {
            registry.put(cm.getRadisCahceConfigurationFileName(), cm);
        }
        if (cm.getProvider().equals(CacheProviders.EHCACHE_PROVIDER)) {
            registry.put(cm.getEhCahceConfigurationFileName(), cm);
        }

    }

    public void removeCacheManager(CacheManager cm) {

        if (cm.getProvider().equals(CacheProviders.REDIS_CACHE_PROVIDER)) {
            registry.remove(cm.getRadisCahceConfigurationFileName());
        }
        if (cm.getProvider().equals(CacheProviders.EHCACHE_PROVIDER)) {
            registry.remove(cm.getEhCahceConfigurationFileName(), cm);
        }


    }

    /**
     * @return the registry
     */
    public HashMap<String, CacheManager> getRegistry() {
        return registry;
    }

    public CacheManager getCacheManager(String configurationFileName) {
        return registry.get(configurationFileName);
    }

}