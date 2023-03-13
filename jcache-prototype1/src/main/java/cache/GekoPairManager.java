package cache;

import cluster.MessageHandler;
import cluster.RemoteMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GekoPairManager extends CacheManager implements MessageHandler {

    private static final Log log = LogFactory.getLog(GekoPairManager.class);

    private static GekoPairManager instance;

    private static final String NO_HIT = "NO_HIT";

    private static GekoPairDAO gekoPairDAO = DAOFactory.create(GekoPairDAO.class);


    public static final String REMOTE_MESSAGE_CATEGORY = "GekoPairManagerCategory";

    private String provider;

    private GekoPairManager() {

    }

    public GekoPairManager(String provider) {
        this.provider = provider;
    }

    public static GekoPairManager getInstance(String provider) throws URISyntaxException, IOException {
        if (instance == null) {
            instance = new GekoPairManager(provider);
            instance.buildManager();
        }

        return instance;
    }

    @Override
    protected String getRadisCahceConfigurationFileName() {
        return "src/main/resources/gekopair-cache.yaml";
    }

    @Override
    protected String getEhCahceConfigurationFileName() {
        return "gekopair-cache.xml";
    }

    @Override
    protected String getCacheName() {
        return "GekoPairCache";
    }

    @Override
    protected String getProvider() {
        return provider;
    }

    @Override
    protected String getRedisConfigurationFileName() {
        return "redisson-jcache.yaml";
    }


    public void setProvider(String provider) {
        this.provider = provider;
    }

    private String getValue(String name) {
        String res = (String) get(name);
        if (res == null) {
            GekoPair p = DAOFactory.create(GekoPairDAO.class).findByName(name);
            String key = p != null ? p.getName() : name;
            String value = p != null ? p.getValue() : null;

            put(key, value != null ? value : NO_HIT);
            res = value;
        } else if (res.equals(NO_HIT)) {
            res = null;
        }

        return res;
    }

    public String getAsString(String name) {
        return getValue(name);
    }

    public Integer getAsInteger(String name) {
        Integer res = null;

        String value = getValue(name);
        if (!value.isEmpty()) {
            res = Integer.valueOf(value);
        }

        return res;
    }

    public Float getAsFloat(String name) {
        return getAsFloat(name, null);
    }

    public Float getAsFloat(String name, String format) {
        Float res = null;

        String value = getValue(name);
        if (!value.isEmpty()) {
            if (format != null) {
                Number number = null;
                try {
                    number = new DecimalFormat(format).parse(value);
                } catch (Exception e) {
                    log.error(e, e);
                }
                res = number != null ? number.floatValue() : null;
            } else {
                res = Float.valueOf(value);
            }
        }

        return res;
    }


    public Boolean getAsBoolean(String name) {
        Boolean res = null;

        String value = getValue(name);
        if (!value.isEmpty()) {
            res = Boolean.valueOf(value);
        }

        return res;
    }

    public Date getAsDate(String name) throws ParseException {
        return getAsDate(name, DateUtil.DDMMYYYY_SLASH_FORMAT);
    }

    public Date getAsDate(String name, String format) throws ParseException {
        Date res = null;

        String value = getValue(name);
        if (!value.isEmpty()) {
            res = new SimpleDateFormat(format).parse(value);
        }

        return res;
    }

    public Timestamp getAsTimestamp(String name) throws ParseException {
        return getAsTimestamp(name, DateUtil.DDMMYYYY_HHMMSS_SLASH_FORMAT);
    }

    public Timestamp getAsTimestamp(String name, String format) throws ParseException {
        Timestamp res = null;

        String value = getValue(name);
        if (!value.isEmpty()) {
            Date parsedDate = new SimpleDateFormat(format).parse(value);
            res = new Timestamp(parsedDate.getTime());
        }

        return res;
    }

    public void put(String name, String value) {
        String currValue = (String) get(name);
        gekoPairDAO.addGekoPair(name, value);
        super.put(name, value);
    }

    public void remove(String name) {
        String value = (String) get(name);
        gekoPairDAO.removeGekoPair(name);
        super.remove(name);
    }

    @Override
    public void execute(RemoteMessage message) {
        if (message.getCategory().equals(REMOTE_MESSAGE_CATEGORY)) {
            emptyCache();
        }
    }
}
