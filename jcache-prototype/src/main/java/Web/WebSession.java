package Web;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSession implements Serializable {

    private static final long serialVersionUID = -7182938935664592198L;

    private String id;
    private Date creationTime;
    private Date lastAccessTime;
    private Map<String, Serializable> attributes = new ConcurrentHashMap<>();

    public WebSession(String id) {
        this.id = id;
        creationTime = new Date();
        lastAccessTime = new Date();
    }

    public void touch() {
        lastAccessTime = new Date();
    }

    public Serializable getAttribute(String s) {
        synchronized (attributes) {
            return attributes.get(s);
        }
    }

    public void setAttribute(String s, Serializable obj) {
        synchronized (attributes) {
            attributes.put(s, obj);
        }
    }

    public Serializable removeAttribute(String s) {
        synchronized (attributes) {
            return attributes.remove(s);
        }
    }

    public Map<String, Serializable> getAttributes() {
        synchronized (attributes) {
            return attributes;
        }
    }

    @JsonIgnore
    public Set<String> getAttributeNames() {
        synchronized (attributes) {
            return attributes.keySet();
        }
    }

    public String getId() {
        return id;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "WebSession{" +
                "id='" + id + '\'' +
                ", creationTime=" + creationTime +
                ", lastAccessTime=" + lastAccessTime +
                ", attributes=" + attributes +
                '}';
    }
}