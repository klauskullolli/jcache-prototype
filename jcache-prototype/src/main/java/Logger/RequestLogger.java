package Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestLogger {
    private static Log log = null;
    private String client;
    private long key = System.currentTimeMillis();

    public RequestLogger() {
    }

    public static void initLogger(String loggerName) {
        if (log == null) {
            log = LogFactory.getLog(loggerName);
        }

    }

    public void initMessages(String client) {
        this.client = client;
    }

    public void debug(String msg) {
        log.debug(getMessage(msg));
    }

    public void debug(String msg, Throwable t) {
        log.debug(getMessage(msg), t);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public void error(String msg) {
        log.error(getMessage(msg));
    }

    public void error(String msg, Throwable t) {
        log.error(getMessage(msg), t);
    }

    public void info(String msg) {
        log.info(getMessage(msg));
    }

    public void info(String msg, Throwable t) {
        log.info(getMessage(msg), t);
    }

    public void warn(String msg) {
        log.warn(getMessage(msg));
    }

    public void warn(String msg, Throwable t) {
        log.warn(getMessage(msg), t);
    }

    private String getMessage(String msg) {
        return key + "_" + client + ": " + msg;
    }
}
