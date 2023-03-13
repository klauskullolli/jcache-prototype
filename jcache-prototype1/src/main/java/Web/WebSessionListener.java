package Web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.cache.event.*;

public class WebSessionListener implements CacheEntryUpdatedListener<Object, Object>
        , CacheEntryCreatedListener<Object, Object>
        , CacheEntryRemovedListener<Object, Object>
        , CacheEntryExpiredListener<Object, Object> {

    private static Log log = LogFactory.getLog(WebSessionListener.class);

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {

            WebSession s = (WebSession) event.getValue();
            UserDTO user = (UserDTO) s.getAttribute("gekoUser");
            if (user == null) {
                user = (UserDTO) s.getAttribute("gekoFrontendUser");
            }

            log.info("Created new web session: " + s.getId());

//            TrackingManager.saveLogin(user);
        }

    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {

            WebSession s = (WebSession) event.getValue();
            if (s != null) {
                UserDTO user = (UserDTO) s.getAttribute("gekoUser");
                if (user == null) {
                    user = (UserDTO) s.getAttribute("gekoFrontendUser");
                }

                log.info("Removed web session: " + s.getId());

//                TrackingManager.saveLogout(user);
            }

        }
    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
            WebSession s = (WebSession) event.getValue();
            String token = s.getId();

            UserDTO user = (UserDTO) s.getAttribute("gekoUser");
            if (user == null) {
                user = (UserDTO) s.getAttribute("gekoFrontendUser");
            }

            log.info("Expired web session: " + s.getId());

//            PluginManager.runPreUserLogoutPlugins(user, s, null);
//
//            AuthManager.deleteToken(token);
//
//            PluginManager.runPostUserLogoutPlugins(user, s, null);
//
//            HibernateUtil.commitTransaction();
//
//            TrackingManager.saveLogout(user);
        }

    }


    @Override
    public void onUpdated(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
//        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
//         log.info(String.format("Entry-> {%s}  updated successfully", event.getKey()));
//        }
    }
}

