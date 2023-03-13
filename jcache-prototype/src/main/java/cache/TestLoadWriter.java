package cache;

import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import java.util.Map;

public class TestLoadWriter implements CacheLoaderWriter<Object, Object> {
    @Override
    public Object load(Object o) throws Exception {
        return null;
    }

    @Override
    public Map loadAll(Iterable keys) throws BulkCacheLoadingException, Exception {
        return CacheLoaderWriter.super.loadAll(keys);
    }

    @Override
    public void write(Object o, Object o2) throws Exception {

    }

    @Override
    public void writeAll(Iterable iterable) throws BulkCacheWritingException, Exception {
        CacheLoaderWriter.super.writeAll(iterable);
    }

    @Override
    public void delete(Object o) throws Exception {

    }

    @Override
    public void deleteAll(Iterable keys) throws BulkCacheWritingException, Exception {
        CacheLoaderWriter.super.deleteAll(keys);
    }
}
