package Web;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6743261040670113911L;

    private Long id;

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private Locale defaultLocale;

    private String clientId;
    private String sessionId;
    private String ip;
    private Date activationDate;
    private String migrationSessionId;

    private Boolean subscribeToNewsletter;

    private GroupAccessFileHomeDir fileHomeDir;

    private Set<GroupDTO> groups;
    private Map<String, PermissionDTO.Permission> defaultPermissions;

    private Map<String, Object> attributes;

    private Boolean changePassword = true;

    public UserDTO() {
        this(null);
    }

    public UserDTO(String username) {
        setId(-1l);
        setGroups(new HashSet<GroupDTO>());
        setDefaultPermissions(new HashMap<>());
        setSubscribeToNewsletter(false);
        setAttributes(new ConcurrentHashMap<>());

        this.username = username;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return the groups
     */
    public Set<GroupDTO> getGroups() {
        return groups;
    }

    /**
     * @return the subscribeToNewsletter
     */
    public Boolean getSubscribeToNewsletter() {
        return subscribeToNewsletter;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Set<GroupDTO> groups) {
        this.groups = groups;
    }

    /**
     * @param subscribeToNewsletter the subscribeToNewsletter to set
     */
    public void setSubscribeToNewsletter(Boolean subscribeToNewsletter) {
        this.subscribeToNewsletter = subscribeToNewsletter;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the defaultLocale
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * @param defaultLocale the defaultLocale to set
     */
    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * @return the migrationSessionId
     */
    public String getMigrationSessionId() {
        return migrationSessionId;
    }

    /**
     * @param migrationSessionId the migrationSessionId to set
     */
    public void setMigrationSessionId(String migrationSessionId) {
        this.migrationSessionId = migrationSessionId;
    }

    /**
     * @return the fileHomeDir
     */
    public GroupAccessFileHomeDir getFileHomeDir() {
        return fileHomeDir;
    }

    /**
     * @param fileHomeDir the fileHomeDir to set
     */
    public void setFileHomeDir(GroupAccessFileHomeDir fileHomeDir) {
        this.fileHomeDir = fileHomeDir;
    }

    /**
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the defaultPermissions
     */
    public Map<String, PermissionDTO.Permission> getDefaultPermissions() {
        return defaultPermissions;
    }

    /**
     * @param defaultPermissions the defaultPermissions to set
     */
    public void setDefaultPermissions(Map<String, PermissionDTO.Permission> defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }
}