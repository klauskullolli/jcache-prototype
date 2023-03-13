package cluster;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import utils.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class ClusterManager {

    private static Log log = LogFactory.getLog(ClusterManager.class);

    private static ClusterManager instance;
    private final List<HandlerEntry> handlers = new ArrayList<HandlerEntry>();
    private ClusterReceiver clusterReceiver;

    public static ClusterManager getInstance() {
        if (instance == null) {
            instance = new ClusterManager();
            instance.init();
        }

        return instance;
    }

    private void init() {
        clusterReceiver = new ClusterReceiver(this);
    }

    private static class HandlerEntry {

        private final MessageHandler handler;
        private final String category;

        public HandlerEntry(final MessageHandler handler, final String category) {
            this.handler = handler;
            this.category = category;
        }
    }

    /**
     * Registrazione di un handler che sarà eseguito alla ricezione di ogni
     * messaggio associato alla categoria indicata. Se la categoria è nulla,
     * verrà eseguito per tutte le categorie.
     *
     * @param handler  handler da eseguire
     * @param category categoria
     */
    public void addMessageHandler(final MessageHandler handler, final String category) {
        handlers.add(new HandlerEntry(handler, category));
    }

    /**
     * Rimozione dell'handler.
     *
     * @param handler handler
     */
    public void removeMessageHandler(final MessageHandler handler) {
        for (final Iterator<HandlerEntry> i = handlers.iterator(); i.hasNext(); ) {
            final HandlerEntry h = i.next();
            if (h.handler == handler) {
                i.remove();
            }
        }
    }

    /**
     * Invio del messaggio al cluster.
     *
     * @param message
     */
    public void sendMessage(final RemoteMessage message) {
        clusterReceiver.send(message);
    }

    /**
     * Processo del messaggio.
     *
     * @param message
     */
    protected void processMessage(final RemoteMessage message) {
        for (final HandlerEntry e : handlers) {
            if (StringUtil.isEmpty(message.getCategory()) || message.getCategory().equals(e.category)) {
                log.info("Processing " + message + " using handler " + e.handler);
                e.handler.execute(message);
            }
        }
    }

    /**
     * Avvio del cluster.
     */
    public void start() {
        log.info("Starting cluster...");
        clusterReceiver.start();
        log.info("Starting cluster done.");
    }

    /**
     * Chiusura del cluster.
     */
    public void stop() {
        log.info("Shutting down cluster...");
        clusterReceiver.stop();
        log.info("Shutting down cluster done.");
    }
}