import Web.UserDTO;
import Web.WebSession;
import Web.WebSessionManager;
import cache.CacheProviders;
import org.junit.Test;

public class WebSessionListenerTest {
    @Test
    public void test() {
        WebSession webSession1 = new WebSession("hello1");
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(2L);
        userDTO1.setUsername("Test1");
        userDTO1.setSessionId("gekoUser");
        userDTO1.setEmail("test1@gamil.com");
        webSession1.setAttribute("gekoUser", userDTO1);
        WebSessionManager webSessionManager = WebSessionManager.getInstance(CacheProviders.EHCACHE_PROVIDER);
        webSessionManager.put("1", webSession1);

        System.out.println((WebSession) webSessionManager.get("1"));

        WebSession webSession2 = new WebSession("hello2");
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setUsername("Test2");
        userDTO2.setSessionId("gekoUser");
        userDTO2.setEmail("test2@gamil.com");
        webSession2.setAttribute("gekoUser", userDTO2);

        webSessionManager.put("2", webSession2);

        System.out.println((WebSession) webSessionManager.get("2"));

        webSessionManager.remove("1");

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println((WebSession) webSessionManager.get("2"));
        webSessionManager.shutdown();
    }
}
