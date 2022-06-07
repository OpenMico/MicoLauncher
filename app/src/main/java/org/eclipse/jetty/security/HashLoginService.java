package org.eclipse.jetty.security;

import java.io.IOException;
import org.eclipse.jetty.security.PropertyUserStore;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.Scanner;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public class HashLoginService extends MappedLoginService implements PropertyUserStore.UserListener {
    private static final Logger LOG = Log.getLogger(HashLoginService.class);
    private String _config;
    private Resource _configResource;
    private PropertyUserStore _propertyUserStore;
    private int _refreshInterval = 0;
    private Scanner _scanner;

    @Override // org.eclipse.jetty.security.MappedLoginService
    protected UserIdentity loadUser(String str) {
        return null;
    }

    @Override // org.eclipse.jetty.security.MappedLoginService
    public void loadUsers() throws IOException {
    }

    public HashLoginService() {
    }

    public HashLoginService(String str) {
        setName(str);
    }

    public HashLoginService(String str, String str2) {
        setName(str);
        setConfig(str2);
    }

    public String getConfig() {
        return this._config;
    }

    public void getConfig(String str) {
        this._config = str;
    }

    public Resource getConfigResource() {
        return this._configResource;
    }

    public void setConfig(String str) {
        this._config = str;
    }

    public void setRefreshInterval(int i) {
        this._refreshInterval = i;
    }

    public int getRefreshInterval() {
        return this._refreshInterval;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.security.MappedLoginService, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (this._propertyUserStore == null) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("doStart: Starting new PropertyUserStore. PropertiesFile: " + this._config + " refreshInterval: " + this._refreshInterval, new Object[0]);
            }
            this._propertyUserStore = new PropertyUserStore();
            this._propertyUserStore.setRefreshInterval(this._refreshInterval);
            this._propertyUserStore.setConfig(this._config);
            this._propertyUserStore.registerUserListener(this);
            this._propertyUserStore.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.security.MappedLoginService, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        Scanner scanner = this._scanner;
        if (scanner != null) {
            scanner.stop();
        }
        this._scanner = null;
    }

    @Override // org.eclipse.jetty.security.PropertyUserStore.UserListener
    public void update(String str, Credential credential, String[] strArr) {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("update: " + str + " Roles: " + strArr.length, new Object[0]);
        }
        putUser(str, credential, strArr);
    }

    @Override // org.eclipse.jetty.security.PropertyUserStore.UserListener
    public void remove(String str) {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("remove: " + str, new Object[0]);
        }
        removeUser(str);
    }
}
