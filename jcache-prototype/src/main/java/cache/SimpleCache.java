package cache;

public class SimpleCache extends CacheManager {


    private String configFile = "ehcache-jsr107-config.xml";
    private String cacheName = "stringCache";

    private String provider;

    public SimpleCache() {
    }

    public SimpleCache(String provider) {
        this.provider = provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    protected String getRadisCahceConfigurationFileName() {
        return null;
    }

    @Override
    protected String getEhCahceConfigurationFileName() {
        return configFile;
    }


    @Override
    protected String getCacheName() {
        return cacheName;
    }

    @Override
    protected String getProvider() {
        return provider;
    }

    @Override
    protected String getRedisConfigurationFileName() {
        return null;
    }
}
