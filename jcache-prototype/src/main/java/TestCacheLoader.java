import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.util.HashMap;
import java.util.Map;

public class TestCacheLoader implements CacheLoader<String, String> {


    public TestCacheLoader() {
    }

    @Override
    public String load(String o) throws CacheLoaderException {
        return "Test cache " + o;
    }

    @Override
    public Map<String, String> loadAll(Iterable iterable) throws CacheLoaderException {
        Map<String, String> data = new HashMap<>();
        for (Object key : iterable) {
            data.put((String) key, load((String) key));
        }
        return data;
    }

}
