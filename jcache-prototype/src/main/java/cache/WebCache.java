package cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class WebCache extends CacheManager {

    private static WebCache instance;
    private static Log log = LogFactory.getLog(WebCache.class);
    private String provider;

    private WebCache() {

    }

    public WebCache(String provider) {
        this.provider = provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public static WebCache getInstance() throws URISyntaxException, IOException {
        if (instance == null) {
            instance = new WebCache();
            instance.buildManager();
        }
        return instance;
    }

    @Override
    public Object get(Object key) {
        long getTime = System.nanoTime();
        Object res = super.get(key);
        getTime = System.nanoTime() - getTime;
        float getElapsedTimeMs = (float) (getTime) / 1000000; // ms

//        RequestLogger rl = RequestLoggerUtil.getLogger();

//        if (rl != null && rl.isDebugEnabled()) {

        String verb = "";
        if (res != null) {
            verb = "hit";
        } else {
            verb = "miss";
        }

        log.info(key + " " + verb + ", get time (ms): " + getElapsedTimeMs);
//        }

        return res;
    }

    @Override
    public void put(Object key, Object o) {
        long getTime = System.nanoTime();
        super.put(key, o);
        getTime = System.nanoTime() - getTime;
        float getElapsedTimeMs = (float) (getTime) / 1000000; // ms

//        RequestLogger rl = RequestLoggerUtil.getLogger();

//        if (rl != null && rl.isDebugEnabled()) {
        log.info(key + " put time (ms): " + getElapsedTimeMs);
//        }
    }

    @Override
    protected String getRadisCahceConfigurationFileName() {
        return null;
    }

    @Override
    protected String getEhCahceConfigurationFileName() {
        return "/web-cache.xml";
    }


    @Override
    protected String getCacheName() {
        return "cache.WebCache";
    }

    @Override
    protected String getProvider() {
        return "org.ehcache.jsr107.EhcacheCachingProvider";
    }

    @Override
    protected String getRedisConfigurationFileName() {
        return null;
    }
}
