package Web;


import Logger.RequestLogger;
import Logger.RequestLoggerUtil;
import cache.CacheManager;
import cache.CacheProviders;

import javax.cache.Cache;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import java.io.IOException;
import java.net.URISyntaxException;

public class WebSessionManager extends CacheManager {

    private static WebSessionManager instance;
    private String projectName = null;

    private String provider;
    private static Cache<Object, Object> cache;

    private WebSessionManager(String provider) {
//        CMSConfiguration conf = JNDIResourcesFactory.getInstance().lookup("CMSConfiguration", CMSConfiguration.class);
//        String projectName = Utility.getPackageName(conf.getProject().getProjectName());
//        this.projectName = projectName;
        this.provider = provider;
    }


    public static WebSessionManager getInstance() {

        if (instance == null) {
            instance = new WebSessionManager(CacheProviders.EHCACHE_PROVIDER);
            try {
                instance.buildManager();
                cache = instance.getCache(Object.class, Object.class);
                CacheEntryListenerConfiguration<Object, Object> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(WebSessionListener.class), null, false, true);
                cache.registerCacheEntryListener(listenerConfiguration);
            } catch (URISyntaxException | IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static WebSessionManager getInstance(String provider) {

        if (instance == null) {
            instance = new WebSessionManager(provider);
            try {
                instance.buildManager();
                cache = instance.getCache(Object.class, Object.class);
                CacheEntryListenerConfiguration<Object, Object> listenerConfiguration = new MutableCacheEntryListenerConfiguration<>(FactoryBuilder.factoryOf(WebSessionListener.class), null, false, true);
                cache.registerCacheEntryListener(listenerConfiguration);
            } catch (URISyntaxException | IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

        return instance;
    }

    @Override
    protected String getCacheName() {
        return "WebSession";
    }

    @Override
    protected String getProvider() {
        return provider;
    }

    @Override
    protected String getRedisConfigurationFileName() {
        return null;
    }


//    @Override
//    protected String getConfigurationFileName() {
//        return "/web-session-cache.xml";
//    }

    @Override
    protected String getRadisCahceConfigurationFileName() {
        return null;
    }

    @Override
    protected String getEhCahceConfigurationFileName() {
        return "web-session-cache.xml";
    }

    @Override
    public Object get(Object key) {
        long getTime = System.nanoTime();
        Object res = cache.get(key);
        getTime = System.nanoTime() - getTime;
        float getElapsedTimeMs = (float) (getTime) / 1000000; // ms

        RequestLogger rl = RequestLoggerUtil.getLogger();
        if (rl != null && rl.isDebugEnabled()) {

            String verb = "";
            if (res != null) {
                verb = "hit";
            } else {
                verb = "miss";
            }

            rl.info(verb + " " + key + " get time (ms) " + getElapsedTimeMs);
        }

        return res;
    }

    @Override
    public void put(Object key, Object o) {
        long getTime = System.nanoTime();
        cache.put(key, o);
        getTime = System.nanoTime() - getTime;
        float getElapsedTimeMs = (float) (getTime) / 1000000; // ms

        RequestLogger rl = RequestLoggerUtil.getLogger();
        if (rl != null && rl.isDebugEnabled()) {
            rl.info(key + " put time (ms) " + getElapsedTimeMs);
        }
    }

//
//    public void buildWebSession(User user, AuthToken at, String ip, String clientId) {
//        WebSession s = (WebSession) get(at.getToken());
//        if (s == null) {
//            s = new WebSession(at.getToken());
//        }
//
//        UserDTO u = new UserDTO();
//        u.setId(user.getId());
//        u.setUsername(user.getUsername());
//        u.setName(user.getName());
//        u.setSurname(user.getSurname());
//        u.setEmail(user.getEmail());
//        u.setActivationDate(user.getActivationDate());
//
//        CMSConfiguration cfg = JNDIResourcesFactory.getInstance().lookup(Constants.KEY_CMS_CONFIGURATION,
//                CMSConfiguration.class);
//
//        GekoLocale gl = user.getDefaultLocale();
//        if (gl != null) {
//
//            if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//                Locale l = cfg.getEnvironment().getLocaleGUI(gl.getLanguage(), gl.getCountry());
//                if (l != null) {
//                    u.setDefaultLocale(new java.util.Locale(gl.getLanguage(), gl.getCountry()));
//                }
//            } else {
//                Locale l = cfg.getEnvironment().getLocale(gl.getLanguage(), gl.getCountry());
//                if (l != null) {
//                    u.setDefaultLocale(new java.util.Locale(gl.getLanguage(), gl.getCountry()));
//                }
//            }
//        }
//
//        if (u.getDefaultLocale() == null) {
//            if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//                u.setDefaultLocale(new java.util.Locale("en", "US"));
//            } else {
//                Locale l = cfg.getEnvironment().getProjectDefaultLocale();
//                u.setDefaultLocale(new java.util.Locale(l.getLanguage(), l.getCountry()));
//            }
//        }
//
//        if (user.getGroups() != null) {
//            for (Group g : user.getGroups()) {
//                GroupDTO gd = new GroupDTO();
//                gd.setId(g.getId());
//                gd.setName(g.getName());
//                gd.setDescription(g.getDescription());
//
//                u.getGroups().add(gd);
//
//                if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//                    if (u.getFileHomeDir() == null) {
//                        setHomeDirPath(u, g);
//                    }
//                }
//
//            }
//        }
//
//        u.setClientId(at.getClientId());
//        u.setSessionId(at.getToken());
//        u.setIp(ip);
//
//        if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//            setDefaultPermissions(u);
//        }
//
//        if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//            s.setAttribute(Constants.KEY_CMS_USER, u);
//        } else {
//            s.setAttribute(Constants.KEY_FRONTEND_USER, u);
//        }
//
//        if (clientId.equals(AuthManager.CLIENT_ID_CMS)) {
//            UserMenuDTO menu = new UserMenuDTO(u);
//            Collection<SiteConfiguration> sites = addSites(u);
//
//            s.setAttribute(Constants.KEY_CMS_MENU, menu);
//            s.setAttribute(Constants.KEY_CMS_SITES, (Serializable) sites);
//            s.setAttribute(Constants.KEY_CMS_BOOKMARKS, (Serializable) addBookmarks(u));
//
//            if (PluginManager.isInstalled("com.geko.cms.plugin.search.business.Search")) {
//                s.setAttribute(Constants.KEY_CMS_PLUGIN_SEARCH,
//                        (Serializable) addSearchPluginConfiguration(u, menu, sites));
//            }
//        }
//
//        PluginManager.runUserSessionCreatePlugins(u, s);
//
//        if (PasswordManager.isPasswordExpired(user.getPasswordLastUpdate(), clientId)) {
//            u.setChangePassword(true);
//        } else {
//            u.setChangePassword(false);
//        }
//
//        cache.put(at.getToken(), s);
//
//    }
//
//    private void setHomeDirPath(UserDTO u, Group g) {
//        try {
//            GroupAccessFilePermissions gafp = AccessControlManager.readFilesAccessControls(g.getId());
//            if (gafp != null && gafp.getFileHomeDir() != null) {
//                u.setFileHomeDir(gafp.getFileHomeDir());
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    private void setDefaultPermissions(UserDTO u) {
//        EntityInstancesAccessControl permission = AccessControlManager.readDefaultEntityInstanceAccessControl(u.getId(),
//                "page");
//        u.getDefaultPermissions().put("page",
//                new Permission(permission.getRead() != null ? permission.getRead().booleanValue() : false,
//                        permission.getWrite() != null ? permission.getWrite().booleanValue() : false,
//                        permission.getWritePermissions() != null ? permission.getWritePermissions().booleanValue() : false));
//
//        permission = AccessControlManager.readDefaultEntityInstanceAccessControl(u.getId(), "template");
//        u.getDefaultPermissions().put("template",
//                new Permission(permission.getRead() != null ? permission.getRead().booleanValue() : false,
//                        permission.getWrite() != null ? permission.getWrite().booleanValue() : false,
//                        permission.getWritePermissions() != null ? permission.getWritePermissions().booleanValue() : false));
//
//        permission = AccessControlManager.readDefaultEntityInstanceAccessControl(u.getId(), "sitemapnode");
//        u.getDefaultPermissions().put("sitemapnode",
//                new Permission(permission.getRead() != null ? permission.getRead().booleanValue() : false,
//                        permission.getWrite() != null ? permission.getWrite().booleanValue() : false,
//                        permission.getWritePermissions() != null ? permission.getWritePermissions().booleanValue() : false));
//
//        List<EntitiesAccessControl> entityPermission = AccessControlManager.getEntityAccessControl(u.getId(), "gekoenum");
//        u.getDefaultPermissions().put("gekoenum",
//                new Permission(RBACManager.canReadEntity(entityPermission, "gekoenum"),
//                        RBACManager.canWriteEntity(entityPermission, "gekoenum")
//                                || RBACManager.canDeleteEntity(entityPermission, "gekoenum"),
//                        false));
//
//        boolean sitePermission = RBACManager.canManageSites(u.getId());
//        u.getDefaultPermissions().put("site", new Permission(sitePermission, sitePermission, false));
//    }
//
//    public void buildAnonymousWebSession(AuthToken at, String ip) {
//        WebSession s = (WebSession) get(at.getToken());
//        if (s == null) {
//            s = new WebSession(at.getToken());
//        }
//
//        UserDTO u = new UserDTO();
//        u.setId(-1l);
//        u.setUsername(at.getUsername());
//
//        u.setClientId(at.getClientId());
//        u.setSessionId(at.getToken());
//        u.setIp(ip);
//
//        CMSConfiguration cfg = JNDIResourcesFactory.getInstance().lookup(Constants.KEY_CMS_CONFIGURATION,
//                CMSConfiguration.class);
//        Locale l = cfg.getEnvironment().getProjectDefaultLocale();
//        u.setDefaultLocale(new java.util.Locale(l.getLanguage(), l.getCountry()));
//
//        s.setAttribute(Constants.KEY_FRONTEND_USER, u);
//
//        // Questa put non genera l'evento gestito da
//        // WebSessionListener.notifyElementPut()
//        put(at.getToken(), s);
//
//        PluginManager.runUserSessionCreatePlugins(u, s);
//    }
//
//    public void refreshWebSession(String token) {
//        WebSession s = (WebSession) get(token);
//        if (s != null) {
//            UserDTO u = (UserDTO) s.getAttribute(Constants.KEY_CMS_USER);
//            if (u != null) {
//                UserMenuDTO menu = new UserMenuDTO(u);
//                Collection<SiteConfiguration> sites = addSites(u);
//
//                s.setAttribute(Constants.KEY_CMS_MENU, menu);
//                s.setAttribute(Constants.KEY_CMS_SITES, (Serializable) sites);
//                s.setAttribute(Constants.KEY_CMS_BOOKMARKS, (Serializable) addBookmarks(u));
//
//                if (PluginManager.isInstalled("com.geko.cms.plugin.search.business.Search")) {
//                    s.setAttribute(Constants.KEY_CMS_PLUGIN_SEARCH,
//                            (Serializable) addSearchPluginConfiguration(u, menu, sites));
//                }
//            }
//
////            if (WebSessionManager.getInstance().isCopyOnRead()) {
//            put(token, s);
////            }
//
//            PluginManager.runUserSessionUpdatePlugins(u, s);
//        }
//    }
//
//    public void refreshWebSession(String token, java.util.Locale locale) {
//        WebSession s = (WebSession) get(token);
//        if (s != null) {
//            UserDTO u = (UserDTO) s.getAttribute(Constants.KEY_CMS_USER);
//            if (u != null) {
//                u.setDefaultLocale(locale);
//            }
//
////            if (WebSessionManager.getInstance().isCopyOnRead()) {
//            put(token, s);
////            }
//        }
//    }
//
//    public WebSession refreshWebSession(String token, String remoteHost) {
//        WebSession s = null;
//
//        if (!StringUtil.isEmpty(token)) {
//            s = (WebSession) get(token);
//
//            if (s != null) {
//                log.debug("Web session found");
//                s.touch();
//
////                if (isCopyOnRead()) {
//                put(token, s);
////                }
//            } else {
//                log.debug("Web session not found");
//                AuthToken at = AuthManager.getAuthToken(token);
//                if (at != null && !AuthManager.isTokenExpired(at)) {
//                    log.debug("AuthToken found and not expired, build Web session");
//                    // Il token ricevuto è valido e nel sistema non esiste
//                    // ancora una sessione web per l'utente associato al token
//
//                    if (at.isAnonymous()) {
//                        buildAnonymousWebSession(at, remoteHost);
//                    } else {
//                        User user = DAOFactory.create(UserDAO.class).findByUserName(at.getUsername());
//                        buildWebSession(user, at, remoteHost, at.getClientId());
//                    }
//
//                    s = (WebSession) get(token);
//                    UserDTO sessionUser = null;
//
//                    if (at.isAnonymous()) {
//                        sessionUser = (UserDTO) s.getAttribute(Constants.KEY_FRONTEND_USER);
//                    } else {
//                        sessionUser = (UserDTO) s.getAttribute(Constants.KEY_CMS_USER);
//                    }
//                    PluginManager.runUserLoginPlugins(sessionUser, s, null);
//
//                    // La sessione è stata messa in cache dalla chiamate buildAnonymousWebSession() o buildWebSession().
//                    // Questa ulteriore put è necessaria nel caso in cui i plugin vadano a modificare la sessione
//                    // recuperata in modalità copyOnRead.
////                    if (isCopyOnRead()) {
//                    put(token, s);
////                    }
//                }
//            }
//        }
//
//        return s;
//    }
//
//    public void invalidateWebSession(String token) {
//        WebSession s = (WebSession) get(token);
//        if (s != null) {
//            UserDTO u = (UserDTO) s.getAttribute(Constants.KEY_CMS_USER);
//            if (u != null) {
//                PluginManager.runUserSessionRemovePlugins(u, s);
//            }
//        }
//
//        remove(token);
//    }
//
//    @SuppressWarnings("unchecked")
//    private Collection<SiteConfiguration> addSites(UserDTO u) {
//        List<SiteConfiguration> readableSites = SiteManager
//                .getSitesConfiguration(RBACManager.getReadableSites(u.getId()));
//        List<SiteConfiguration> writableSites = SiteManager
//                .getSitesConfiguration(RBACManager.getWritableSites(u.getId()));
//        List<SiteConfiguration> deletableSites = SiteManager
//                .getSitesConfiguration(RBACManager.getDeletableSites(u.getId()));
//
//        Set<SiteConfiguration> sites = new HashSet<>();
//        sites.addAll(readableSites);
//        sites.addAll(writableSites);
//        sites.addAll(deletableSites);
//
//        for (SiteConfiguration sc : sites) {
//            if (deletableSites.contains(sc)) {
//                sc.setWritable(true);
//                sc.setDeletable(true);
//            } else if (writableSites.contains(sc)) {
//                sc.setWritable(true);
//            }
//        }
//
//        boolean isChecked = false;
//        UserOption uo = UserOptionManager.find(UserOptionManager.SELECTED_SITES, u.getId());
//        if (uo != null && !StringUtil.isEmpty(uo.getValue())) {
//            // value è una lista composta da: [{"id": 1, "locales":
//            // ["en_US", "it_IT"]}, ...]
//            List<Object> uoValue = uo.getValueAsList();
//            if (uoValue != null) {
//                for (Object o : uoValue) {
//                    Map<String, Object> sitesOpt = (Map<String, Object>) o;
//                    Number id = (Number) sitesOpt.get("id");
//                    List<String> locales = (List<String>) sitesOpt.get("locales");
//
//                    for (SiteConfiguration sc : sites) {
//                        if (sc.getId().equals(id.longValue())) {
//                            for (LocaleConfiguration lc : sc.getLocales()) {
//                                for (String l : locales) {
//                                    if (l.equals(lc.toString())) {
//                                        lc.setChecked(true);
//                                        isChecked = true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        Collection<SiteConfiguration> sortedSites = Bean.sortCollectionByBeanProperty(sites, "label");
//
//        if (!isChecked && !sortedSites.isEmpty()) {
//            sortedSites.iterator().next().getLocales().get(0).setChecked(true);
//        }
//
//        return SynchronizedCollection.synchronizedCollection(sortedSites);
//    }
//
//    private List<Bookmark> addBookmarks(UserDTO u) {
//        return BookmarkManager.getBookmarks(u.getUsername());
//    }
//
//    private Map<String, Object> addSearchPluginConfiguration(UserDTO u, UserMenuDTO menu,
//                                                             Collection<SiteConfiguration> sites) {
//
//        CMSConfiguration cfg = JNDIResourcesFactory.getInstance().lookup(Constants.KEY_CMS_CONFIGURATION,
//                CMSConfiguration.class);
//
//        Map<String, Object> indexes = new HashMap<>();
//        Map<String, String> entities = new HashMap<>();
//        Map<String, Object> wfStatuses = new HashMap<>();
//
//        // Entità accessibili dall'utente
//        if (menu.contentsContainsMenuItem(MenuItemType.CONTENTS_SECTION_PAGES, "Pages")) {
//            entities.put("page", "Pages");
//        }
//        for (Entity e : cfg.getEntities()) {
//            if (!e.getSystemEntity() && menu.contentsContainsEntity(e.getName())) {
//                entities.put(e.getName(),
//                        e.getLabel(u.getDefaultLocale().getLanguage(), u.getDefaultLocale().getCountry()));
//            }
//        }
//
//        // Indici accessibili dall'utente
//        if (!entities.isEmpty()) {
//            String projectName = cfg.getProject().getProjectName().replaceAll("[^A-Za-z0-9]", "");
//
//            sites.stream().forEach(s -> {
//                s.getLocales().stream().forEach(l -> {
//                    String indexName = (projectName + "_" + s.getId() + "_" + l.getLanguage() + "_" + l.getCountry())
//                            .toLowerCase();
//
//                    indexes.put(s.getId() + "_" + l.getLanguage() + "_" + l.getCountry(), indexName);
//                });
//            });
//        }
//
//        if (RBACManager.canManageUsers(u.getId())) {
//            indexes.put("user", projectName + "_user");
//            entities.put("user", "Users");
//        }
//
//        if (RBACManager.canManageFiles(u.getId())) {
//            indexes.put("file", projectName + "_file");
//            entities.put("file", "Files");
//        }
//
//        // Stati di workflow
//        WorkflowManager.getWorkflows().stream().forEach(wf -> {
//            wf.getStatuses().stream().forEach(s -> {
//                wfStatuses.put(s.getCode(), s.getLabel());
//            });
//        });
//
//        Map<String, Object> res = new HashMap<>();
//        res.put("indexes", indexes);
//        res.put("entities", entities);
//        res.put("workflows", wfStatuses);
//
//        return res;
//    }

    public boolean isCopyOnRead() {
        return true;
    }
}