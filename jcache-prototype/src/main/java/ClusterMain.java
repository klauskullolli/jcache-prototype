import cache.CacheProviders;
import cache.GekoPairManager;
import cluster.ClusterManager;
import cluster.RemoteMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.cache.Cache;
import java.util.Iterator;
import java.util.Scanner;

public class ClusterMain {


    protected static Log log = LogFactory.getLog(ClusterMain.class);


    private static final String JGROUP_CONFIGURATION_FILE_PATH = "cluster.xml";
    private static final String CHANNEL_NAME = "YouserXPCluster";


    public static void main(String[] args) throws Exception {
        GekoPairManager gekoPairManager = GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER);

        Scanner input = new Scanner(System.in);

        ClusterManager cluster = ClusterManager.getInstance();

        cluster.start();
        cluster.addMessageHandler(GekoPairManager.getInstance(CacheProviders.EHCACHE_PROVIDER), GekoPairManager.REMOTE_MESSAGE_CATEGORY);

        while (true) {
            int x;

            System.out.println("1) Add new element cache");
            System.out.println("2) Find Key");
            System.out.println("3) Get all elements");
            System.out.println("4) Clean cache");
            System.out.println("5) Exit");
            System.out.print("Nr > ");
            x = input.nextInt();
            while (x > 5) {
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
            } else if (x == 3) {
                Cache<String, String> cache = gekoPairManager.getCache(String.class, String.class);
                Iterator<Cache.Entry<String, String>> previous_entries = cache.iterator();

                while (previous_entries.hasNext()) {
                    Cache.Entry<String, String> entry = previous_entries.next();
                    System.out.println("-------------------------------------------------");
                    if (entry != null) {
                        System.out.println(String.format("%s --> %s", entry.getKey(), entry.getValue()));
                    }
                    System.out.println("-------------------------------------------------");
                }

            } else if (x == 4) {

                RemoteMessage message = new RemoteMessage(GekoPairManager.REMOTE_MESSAGE_CATEGORY);
                cluster.sendMessage(message);
                Thread.sleep(1 * 1000);
            } else {

                gekoPairManager.close();
                cluster.stop();
                break;
            }
        }

    }

}
