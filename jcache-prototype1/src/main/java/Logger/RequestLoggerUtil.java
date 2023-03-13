package Logger;

public class RequestLoggerUtil {
    private static final ThreadLocal<RequestLogger> loggers = new ThreadLocal();

    public RequestLoggerUtil() {
    }

    public static void setLogger(String loggerName, String client) {
        RequestLogger.initLogger(loggerName);
        RequestLogger l = new RequestLogger();
        l.initMessages(client);
        loggers.set(l);
    }

    public static void removeLogger() {
        loggers.remove();
    }

    public static RequestLogger getLogger() {
        return (RequestLogger) loggers.get();
    }
}
