import cache.CacheManager;

public class TestCacheManager extends CacheManager {

    private String provider;

    public TestCacheManager() {
    }

    public TestCacheManager(String provider) {
        this.provider = provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    protected String getRadisCahceConfigurationFileName() {
        return "src/main/resources/test.yaml";
    }

    @Override
    protected String getEhCahceConfigurationFileName() {
        return null;
    }

    @Override
    protected String getCacheName() {
        return "test";
    }

    @Override
    protected String getProvider() {
        return provider;
    }

    @Override
    protected String getRedisConfigurationFileName() {
        return "redisson-jcache.yaml";
    }


}
