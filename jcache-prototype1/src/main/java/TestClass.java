import cache.CacheProviders;
import cache.GekoPairManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
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
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class TestClass {
    protected static Log log = LogFactory.getLog(TestClass.class);


    @Test
    public void redisCacheTest() throws IOException, URISyntaxException {
        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.REDIS_CACHE_PROVIDER);
        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);


        cache.put("test1", "A");

        cache.put("test2", "B");


        System.out.println("-----------------------------------------------------------");

        Iterator<Cache.Entry<String, String>> previous_entries = cache.iterator();
        while (previous_entries.hasNext()) {
            Cache.Entry<String, String> entry = previous_entries.next();
            System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
        }
    }


    @Test
    public void ehCacheTest() throws ParseException, URISyntaxException, IOException {

        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);

        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);
//        CacheEntryListenerConfiguration<String, String> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(cache.CacheCustomListener.class), null, false, true);
//
//        cache.registerCacheEntryListener(listenerConfiguration);

        System.out.println("-----------------------------------------------------------");

        System.out.println(gekoPairManager.getAsString("test2"));
        System.out.println(gekoPairManager.getAsBoolean("test5"));
        System.out.println(gekoPairManager.getAsInteger("test1"));

        System.out.println("-----------------------------------------------------------");

        Iterator<Cache.Entry<String, String>> previous_entries = cache.iterator();
        while (previous_entries.hasNext()) {
            Cache.Entry<String, String> entry = previous_entries.next();
            System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
        }

        System.out.println("-----------------------------------------------------------");

        System.out.println(gekoPairManager.getAsFloat("test3"));
        System.out.println(gekoPairManager.getAsDate("test4"));
        System.out.println(gekoPairManager.getAsTimestamp("test6"));

        gekoPairManager.put("test2", "2");


        System.out.println("-----------------------------------------------------------");

        Iterator<Cache.Entry<String, String>> entries = cache.iterator();
        while (entries.hasNext()) {
            Cache.Entry<String, String> entry = entries.next();
            System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
        }
    }


    @Test
    public void test1() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = "src/main/resources/test.yaml";


        InputStream file = new FileInputStream(new File("src/main/resources/test.yaml"));
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(file);


        Map<String, Object> cache = (Map<String, Object>) data.get("cache");
        System.out.println(cache);
        Class<?> cl;
        try {
            cl = Class.forName((String) cache.get("key"));
        } catch (ClassNotFoundException ex) {
            cl = Object.class;
        }

        System.out.println(cl);

        String name = (String) cache.get("name");

        MutableConfiguration conf = getConfig(path);

        URL configUrl = TestClass.class.getResource("redisson-jcache.yaml");
        Config cfg = Config.fromYAML(configUrl);
        Configuration configuration = RedissonConfiguration.fromConfig(cfg, conf);
        Cache testCache = Caching.getCachingProvider("org.redisson.jcache.JCachingProvider").getCacheManager()
                .createCache(name, configuration);

        testCache.put("test1", "1");
        System.out.println(testCache.get("test1"));


    }


    @Test
    public void test2() throws ClassNotFoundException, IOException {
//        System.out.println(Class.forName("TestCacheLoader"));
        MutableConfiguration<String, String> conf = new MutableConfiguration<>();
        conf.setReadThrough(true).setCacheLoaderFactory(new FactoryBuilder.ClassFactory<CacheLoader<String, String>>(TestCacheLoader.class.getName()));
        URL configUrl = TestClass.class.getResource("redisson-jcache.yaml");
        Config cfg = Config.fromYAML(configUrl);
        Configuration<String, String> configuration = RedissonConfiguration.fromConfig(cfg, conf);
        Cache<String, String> testCache = Caching.getCachingProvider("org.redisson.jcache.JCachingProvider").getCacheManager()
                .createCache("testCache", configuration);

        testCache.put("test1", "1");
        System.out.println(testCache.get("test1"));

    }


    public static MutableConfiguration getConfig(String path) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        InputStream file = new FileInputStream(new File(path));
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

            Class<?> keyClass;
            Class<?> valueClass;
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


    @Test
    public void test3() throws ParseException, URISyntaxException, IOException {

        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);

        GekoPairManager gekoPairManager1 = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);

        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);

        Cache<String, String> cache1 = gekoPairManager1.getCache(String.class, String.class);
//        cache.put("test6", "A");

        System.out.println(cache.get("k"));
//        System.out.println(cache1.get("test6"));

        Scanner input = new Scanner(System.in);
        while (true) {
            int x;

            System.out.println("1) Add new element cache");
            System.out.println("2) Find Key");
            System.out.println("3) Exit");
            System.out.print("Nr > ");
            x = input.nextInt();
            while (x > 3) {
                System.out.println("Wrong input");
                System.out.print("Enter number again:");
                x = input.nextInt();
            }
            if (x == 1) {
                String key;
                String value;
                System.out.println("Enter key");
                key = input.nextLine().trim();
                System.out.println("Enter value");
                value = input.nextLine().trim();
                cache.put(key, value);
                System.out.println(String.format("You enterd key: %s value: %s in cache", key, cache.get(key)));
            } else if (x == 2) {
                String key;
                System.out.print("Enter key: ");
                key = input.nextLine();
                System.out.println("Value is: " + cache.get(key));
            } else {
                break;
            }
        }
//        cache.invoke("test1", new EntryProcessor<String, String, String>() {
//            @Override
//            public String process(MutableEntry<String, String> mutableEntry, Object... objects) throws EntryProcessorException {
//                if (mutableEntry.exists()) {
//                    mutableEntry.remove();
//                }
//                return null;
//            }
//        });
//
//        System.out.println(cache.get("test1"));
    }

}


