import cache.CacheCustomListener;
import cache.CacheProviders;
import cache.GekoPairManager;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.jsr107.Eh107Configuration;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;


public class EventListenerTestClass {

    public <T extends Object> Object methode(T k) {
        return "hello" + k.toString();
    }

    @Test
    public void RedissCacheListenerTest() throws URISyntaxException, IOException {
        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.REDIS_CACHE_PROVIDER);
        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);

        CacheEntryListenerConfiguration<String, String> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(CacheCustomListener.class), null, false, true);

        cache.registerCacheEntryListener(listenerConfiguration);
//        cache.put("test1", "A");
        System.out.println((String) gekoPairManager.get("test1"));
        gekoPairManager.put("test2", "B");
        gekoPairManager.put("test2", "D");
        gekoPairManager.remove("test1");
//        gekoPairManager.shutdown();
//        gekoPairManager.close();
//        cache.close();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------------------------------");

        Iterator<Cache.Entry<String, String>> previous_entries = cache.iterator();
        while (previous_entries.hasNext()) {
            Cache.Entry<String, String> entry = previous_entries.next();
            System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
        }
        gekoPairManager.close();
    }

    @Test
    public void ehCacheEventListenerTest() throws URISyntaxException, IOException {
        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);
        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);

        CacheEntryListenerConfiguration<String, String> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(CacheCustomListener.class), null, false, true);

        cache.registerCacheEntryListener(listenerConfiguration);
        Eh107Configuration<String, String> configuration = cache.getConfiguration(Eh107Configuration.class);
        System.out.println(configuration.unwrap(CacheRuntimeConfiguration.class).getExpiryPolicy().getExpiryForCreation(String.class, String.class));
        cache.put("test4", "L");
        cache.put("test1", "A");
        cache.put("test2", "B");
        cache.put("test2", "D");
        cache.remove("test1");
        cache.remove("test3");
//        cache.removeAll();


        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cache.putIfAbsent("test3", "C");
        System.out.println("-----------------------------------------------------------");
        Iterator<Cache.Entry<String, String>> previous_entries = cache.iterator();

        while (previous_entries.hasNext()) {
            Cache.Entry<String, String> entry = previous_entries.next();
            if (entry != null) {
                System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
            }
        }
    }

    @Test
    public void test3() throws URISyntaxException, IOException {
//        cache.GekoPairManager gekoPairManager1 = cache.GekoPairManager.getInstance(cache.CacheProviders.EHCACHE_PROVIDER);
//        cache.GekoPairManager gekoPairManager2 = cache.GekoPairManager.getInstance(cache.CacheProviders.EHCACHE_PROVIDER);
//        Cache<String, String> cache = gekoPairManager1.getCache(String.class, String.class);
//        CacheEntryListenerConfiguration<String, String> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(cache.CacheCustomListener.class), null, false, true);
//
//        cache.registerCacheEntryListener(listenerConfiguration);
//        gekoPairManager1.put("test6", "A");
//        gekoPairManager2.put("test7", "B");
//
//        System.out.println(gekoPairManager2.get("test6"));
        System.out.println(methode("hello"));
    }

}
