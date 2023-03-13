package cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.yaml.snakeyaml.Yaml;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.integration.CacheWriter;
import javax.cache.management.CacheStatisticsMXBean;
import javax.cache.spi.CachingProvider;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public abstract class CacheManager {

    protected static Log log = LogFactory.getLog(CacheManager.class);
    protected javax.cache.CacheManager manager;
    protected CacheRegistry registry = null;
    private Class keyClass;
    private Class valueClass;

    protected CacheManager() {

    }

    public void buildManager() throws URISyntaxException, IOException {


        if (getProvider().equals(CacheProviders.EHCACHE_PROVIDER)) {
            log.debug("Ehcache Configuration: " + getEhCahceConfigurationFileName());
            CachingProvider cachingProvider = Caching.getCachingProvider(getProvider());
            URL url = getClass().getClassLoader().getResource(getEhCahceConfigurationFileName());
            manager = cachingProvider.getCacheManager(url.toURI(), getClass().getClassLoader());
            registry = CacheRegistry.getInstance();
            registry.addCacheManager(this);
            log.info("Cache configurate: " + manager.getCacheNames());
        } else if (getProvider().equals(CacheProviders.REDIS_CACHE_PROVIDER)) {
            log.debug("Redis Configuration: " + getRedisConfigurationFileName());


            MutableConfiguration conf = getConfig();

            URL configUrl = getClass().getClassLoader().getResource(getRedisConfigurationFileName());
            Config cfg = Config.fromYAML(configUrl);
            Configuration configuration = RedissonConfiguration.fromConfig(cfg, conf);
            manager = Caching.getCachingProvider("org.redisson.jcache.JCachingProvider").getCacheManager();
            registry = CacheRegistry.getInstance();
            registry.addCacheManager(this);
            manager.createCache(getCacheName(), configuration);
            log.info("Cache configurate: " + manager.getCacheNames());
        } else {
            log.error("This jcache provider is not correct");
            throw new RuntimeException("This jcache provider is not correct");
        }

    }


    protected MutableConfiguration getConfig() throws FileNotFoundException {

        InputStream file = new FileInputStream(getRadisCahceConfigurationFileName());
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(file);
        Map<String, Object> cacheConf = (Map<String, Object>) data.get("cache");
        MutableConfiguration configuration = new MutableConfiguration();
        if (cacheConf != null) {
            String key = (String) cacheConf.get("key");
            String value = (String) cacheConf.get("value");
            Map<String, Object> ttl = (Map<String, Object>) cacheConf.get("ttl");
            Boolean readThrough = (Boolean) cacheConf.get("readThrough");
            Boolean writeThrough = (Boolean) cacheConf.get("writeThrough");
            Boolean managementEnable = (Boolean) cacheConf.get("managementEnable");
            Boolean statisticEnable = (Boolean) cacheConf.get("statisticEnable");
            String loaderFactory = (String) cacheConf.get("loaderFactory");
            String writerFactory = (String) cacheConf.get("writerFactory");


            if (key != null && !key.isEmpty()) {
                try {
                    keyClass = Class.forName(key);
                } catch (ClassNotFoundException ex) {
                    keyClass = Object.class;
                }
            } else {
                keyClass = Object.class;
            }

            if (value != null && !value.isEmpty()) {
                try {
                    valueClass = Class.forName(key);
                } catch (ClassNotFoundException ex) {
                    valueClass = Object.class;
                }
            } else {
                valueClass = Object.class;
            }

            configuration.setTypes(keyClass, valueClass);

            if (ttl != null) {
                String unit = (String) ttl.get("unit");
                Integer time;
                try {
                    time = (Integer) ttl.get("time");
                } catch (Exception ex) {
                    log.error("Time in configuration file is not set correctly");
                    throw ex;

                }

                if (time == null) {
                    log.error("Time is not set in configuration file");
                    throw new RuntimeException("Time is not set in configuration file");
                }
                if (unit.equals("hours")) {
                    configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, time)));
                }
                if (unit.equals("minutes")) {
                    configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, time)));
                }

                if (unit.equals("seconds")) {
                    configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, time)));
                } else {
                    configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, time)));
                }

            }

            if (readThrough != null) {
                try {

                    configuration.setReadThrough(readThrough);

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }

            if (writeThrough != null) {
                try {

                    configuration.setWriteThrough(writeThrough);

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw ex;
                }
            }

            if (managementEnable != null) {
                try {
                    configuration.setManagementEnabled(managementEnable);

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw ex;
                }
            }

            if (statisticEnable != null) {
                try {
                    configuration.setStatisticsEnabled(statisticEnable);

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    throw ex;
                }
            }

            if (loaderFactory != null && !loaderFactory.isEmpty()) {
                configuration.setCacheLoaderFactory(new FactoryBuilder.ClassFactory<CacheWriter>(loaderFactory));
            }

            if (writerFactory != null && !writerFactory.isEmpty()) {
                configuration.setCacheWriterFactory(new FactoryBuilder.ClassFactory<CacheWriter>(writerFactory));
            }

            return configuration;

        }
        return null;
    }


    public void shutdown() {
        registry.removeCacheManager(this);
        manager.close();
    }

//    public void flush() {
//        flush(getCacheName());
//    }
//
//    /**
//     * Flushes all cache items from memory to the disk store, and from the DiskStore to disk.
//     *
//     * @param cacheName
//     */
//    public void flush(String cacheName) {
//        manager.getCache(cacheName);
//    }

    public <K, V> Cache<K, V> getCache(Class<K> var2, Class<V> var3) {
        return getCache(getCacheName(), var2, var3);
    }


    public <K, V> Cache<K, V> getCache(String name, Class<K> var2, Class<V> var3) {
        return manager.getCache(name, var2, var3);
    }

    public void removeAll() {
        removeAll(getCacheName());
    }


    public void removeAll(String cacheName) {
        manager.getCache(cacheName).removeAll();
    }


    public Iterable<String> getCacheNames() {
        return manager.getCacheNames();
    }


    public boolean isCacheConfigured() {
        return manager.getCache(getCacheName()) != null;
    }


    public <K, V> V get(K key) {
        return get(key, getCacheName());
    }


    public <K, V> V get(K key, String cacheName) {
        long startTime = System.nanoTime();

        V val;
        if (getProvider().equals(CacheProviders.EHCACHE_PROVIDER))
            val = (V) manager.getCache(cacheName).get(key);
        else
            val = (V) manager.getCache(cacheName, keyClass, valueClass).get(key);

        long elapsedTime = System.nanoTime() - startTime;
        float elapsedTimeMs = (float) (elapsedTime) / 1000000; // ms

        onGet(key, val, elapsedTimeMs);

        return val != null ? (V) val : null;
    }


    protected <K, V> void onGet(K key, V val, float elapsedTimeMs) {
        // do nothing...
    }

    /**
     * Put an element in the cache.
     *
     * @param key
     * @param o
     */
    public <K, V> void put(K key, V o) {
        put(key, o, getCacheName());
    }

    /**
     * Put an element in the cache.
     *
     * @param key
     * @param o
     * @param cacheName
     */
    public <K, V> void put(K key, V o, String cacheName) {
        if (getProvider().equals(CacheProviders.EHCACHE_PROVIDER))
            manager.getCache(cacheName).put(key, o);
        else
            manager.getCache(cacheName, keyClass, valueClass).put(key, o);

    }


    /**
     * Removes an element from the cache.
     *
     * @param key
     * @return
     */
    public <K> boolean remove(K key) {
        return remove(key, getCacheName());
    }

    /**
     * Removes an element from the cache.
     *
     * @param key
     * @param cacheName
     * @return
     */
    public <K> boolean remove(K key, String cacheName) {
        if (getProvider().equals(CacheProviders.EHCACHE_PROVIDER))
            return manager.getCache(cacheName).remove(key);
        else
            return manager.getCache(cacheName, keyClass, valueClass).remove(key);
    }


    public void emptyCache() {
        emptyCache(getCacheName());
    }

    /**
     * Removes all elements from cache.
     *
     * @param cacheName
     */
    public void emptyCache(String cacheName) {
        manager.getCache(cacheName).removeAll();

        if (log.isDebugEnabled()) {
            log.debug("Removed all elements from cache " + cacheName);
        }
    }


    public CacheStatisticsMXBean getCacheStatisticsMXBean() {

        return getCacheStatisticsMXBean(getCacheName());
    }


    public CacheStatisticsMXBean getCacheStatisticsMXBean(final String cacheName) {
        final MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = null;
        try {
            name = new ObjectName("*:type=CacheStatistics,*,Cache=" + cacheName);
        } catch (MalformedObjectNameException ex) {
            log.error("Someting wrong with ObjectName {}", ex);
        }
        Set<ObjectName> beans = mbeanServer.queryNames(name, null);
        if (beans.isEmpty()) {
            log.info("Cache Statistics Bean not found");
            return null;
        }
        ObjectName[] objArray = beans.toArray(new ObjectName[beans.size()]);
        return JMX.newMBeanProxy(mbeanServer, objArray[0], CacheStatisticsMXBean.class);
    }


    public long getHits() {
        return getHits(getCacheName());
    }


    public long getHits(String cacheName) {
        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);
        return cacheStatisticsMXBean.getCacheHits();
    }


    public long getMiss() {
        return getMiss(getCacheName());
    }


    public long getMiss(String cacheName) {

        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);
        return cacheStatisticsMXBean.getCacheMisses();
    }

    public double getHitRatio() {
        return getHitRatio(getCacheName());
    }


    public double getHitRatio(String cacheName) {

        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);

        return cacheStatisticsMXBean.getCacheHitPercentage();
    }


    public double getMissRatio() {
        return getMissRatio(getCacheName());
    }


    public double getMissRatio(String cacheName) {

        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);

        return cacheStatisticsMXBean.getCacheMissPercentage();
    }


    public double getAverageGetTime() {
        return getAverageGetTime(getCacheName());
    }


    public double getAverageGetTime(String cacheName) {

        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);

        return cacheStatisticsMXBean.getAverageGetTime();
    }


    public long getEvictionCount() {
        return getEvictionCount(getCacheName());
    }


    public long getEvictionCount(String cacheName) {
        CacheStatisticsMXBean cacheStatisticsMXBean = getCacheStatisticsMXBean(cacheName);
        return cacheStatisticsMXBean.getCacheEvictions();
    }

    public void close() {
        if (getProvider().equals(CacheProviders.EHCACHE_PROVIDER))
            if (manager.getCache(getCacheName()) != null) {
                manager.getCache(getCacheName()).close();
            } else if (manager.getCache(getCacheName(), keyClass, valueClass) != null) {
                manager.getCache(getCacheName(), keyClass, valueClass).close();
            }
    }

    protected abstract String getRadisCahceConfigurationFileName();

    protected abstract String getEhCahceConfigurationFileName();

    protected abstract String getCacheName();

    protected abstract String getProvider();

    protected abstract String getRedisConfigurationFileName();
}
