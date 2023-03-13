package cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.cache.event.*;

public class CacheCustomListener implements CacheEntryCreatedListener<Object, Object>,
        CacheEntryExpiredListener<Object, Object>,
        CacheEntryRemovedListener<Object, Object>,
        CacheEntryUpdatedListener<Object, Object> {

    private static Log log = LogFactory.getLog(CacheCustomListener.class);

    @Override
    public void onCreated(Iterable<CacheEntryEvent<? extends Object, ? extends Object>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
            log.info(String.format("Entry-> {%s}  created successfully", event.getKey()));
        }

    }

    @Override
    public void onExpired(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
            log.info(String.format("Entry-> {%s}  expired successfully", event.getKey()));
        }

    }

    @Override
    public void onRemoved(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
            log.info(String.format("Entry-> {%s}  removed successfully", event.getKey()));
        }
    }

    @Override
    public void onUpdated(Iterable<CacheEntryEvent<?, ?>> iterable) throws CacheEntryListenerException {
        for (CacheEntryEvent<? extends Object, ? extends Object> event : iterable) {
            log.info(String.format("Entry-> {%s}  updated successfully", event.getKey()));
        }
    }
}