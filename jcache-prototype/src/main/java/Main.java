import cache.CacheProviders;
import cache.GekoPairManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.cache.management.CacheStatisticsMXBean;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Set;

public class Main {

    protected static Log log = LogFactory.getLog(Main.class);

    public static CacheStatisticsMXBean getCacheStatisticsMXBean(final String cacheName) {
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

    public static void main(String[] args) throws URISyntaxException, IOException {
        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);
//        GekoPairManager gekoPairManager1 = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);
//        Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);
//        cache.put("kl", "2");
//        cache.close();
//        gekoPairManager.get("kl") ;
//        System.out.println((String) gekoPairManager.get("kl"));

//        System.out.println((String) gekoPairManager1.get("kl"));
        System.out.println((String) gekoPairManager.get("kl"));
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
                input.nextLine();
                String key;
                String value;
                System.out.print("Enter key: ");
                key = input.nextLine();
                System.out.print("Enter value: ");
                value = input.nextLine().trim();
                gekoPairManager.put(key, value);
                System.out.printf("You entered key: %s value: %s in cache\n", key, gekoPairManager.get(key));
            } else if (x == 2) {
                input.nextLine();
                String key;
                System.out.print("Enter key: ");
                key = input.nextLine().trim();
                System.out.println(key);
                System.out.println("Value is: " + gekoPairManager.get(key));
            } else {
//                gekoPairManager1.close();
                gekoPairManager.close();
                break;
            }
        }

    }


}
