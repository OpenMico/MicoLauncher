package org.eclipse.jetty.security;

import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.Subject;
import org.eclipse.jetty.security.MappedLoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.Scanner;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public class PropertyUserStore extends AbstractLifeCycle {
    private static final Logger LOG = Log.getLogger(PropertyUserStore.class);
    private String _config;
    private Resource _configResource;
    private List<UserListener> _listeners;
    private Scanner _scanner;
    private int _refreshInterval = 0;
    private IdentityService _identityService = new DefaultIdentityService();
    private boolean _firstLoad = true;
    private final List<String> _knownUsers = new ArrayList();
    private final Map<String, UserIdentity> _knownUserIdentities = new HashMap();

    /* loaded from: classes5.dex */
    public interface UserListener {
        void remove(String str);

        void update(String str, Credential credential, String[] strArr);
    }

    public String getConfig() {
        return this._config;
    }

    public void setConfig(String str) {
        this._config = str;
    }

    public UserIdentity getUserIdentity(String str) {
        return this._knownUserIdentities.get(str);
    }

    public Resource getConfigResource() throws IOException {
        if (this._configResource == null) {
            this._configResource = Resource.newResource(this._config);
        }
        return this._configResource;
    }

    public void setRefreshInterval(int i) {
        this._refreshInterval = i;
    }

    public int getRefreshInterval() {
        return this._refreshInterval;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadUsers() throws IOException {
        if (this._config != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Load " + this + " from " + this._config, new Object[0]);
            }
            Properties properties = new Properties();
            if (getConfigResource().exists()) {
                properties.load(getConfigResource().getInputStream());
            }
            HashSet hashSet = new HashSet();
            for (Map.Entry entry : properties.entrySet()) {
                String trim = ((String) entry.getKey()).trim();
                String trim2 = ((String) entry.getValue()).trim();
                String str = null;
                int indexOf = trim2.indexOf(44);
                if (indexOf > 0) {
                    str = trim2.substring(indexOf + 1).trim();
                    trim2 = trim2.substring(0, indexOf).trim();
                }
                if (trim != null && trim.length() > 0 && trim2 != null && trim2.length() > 0) {
                    String[] strArr = IdentityService.NO_ROLES;
                    if (str != null && str.length() > 0) {
                        strArr = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                    hashSet.add(trim);
                    Credential credential = Credential.getCredential(trim2);
                    MappedLoginService.KnownUser knownUser = new MappedLoginService.KnownUser(trim, credential);
                    Subject subject = new Subject();
                    subject.getPrincipals().add(knownUser);
                    subject.getPrivateCredentials().add(credential);
                    if (str != null) {
                        for (String str2 : strArr) {
                            subject.getPrincipals().add(new MappedLoginService.RolePrincipal(str2));
                        }
                    }
                    subject.setReadOnly();
                    this._knownUserIdentities.put(trim, this._identityService.newUserIdentity(subject, knownUser, strArr));
                    notifyUpdate(trim, credential, strArr);
                }
            }
            synchronized (this._knownUsers) {
                if (!this._firstLoad) {
                    for (String str3 : this._knownUsers) {
                        if (!hashSet.contains(str3)) {
                            this._knownUserIdentities.remove(str3);
                            notifyRemove(str3);
                        }
                    }
                }
                this._knownUsers.clear();
                this._knownUsers.addAll(hashSet);
            }
            this._firstLoad = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (getRefreshInterval() > 0) {
            this._scanner = new Scanner();
            this._scanner.setScanInterval(getRefreshInterval());
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(getConfigResource().getFile().getParentFile());
            this._scanner.setScanDirs(arrayList);
            this._scanner.setFilenameFilter(new FilenameFilter() { // from class: org.eclipse.jetty.security.PropertyUserStore.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    try {
                        return new File(file, str).compareTo(PropertyUserStore.this.getConfigResource().getFile()) == 0;
                    } catch (IOException unused) {
                        return false;
                    }
                }
            });
            this._scanner.addListener(new Scanner.BulkListener() { // from class: org.eclipse.jetty.security.PropertyUserStore.2
                public String toString() {
                    return "PropertyUserStore$Scanner";
                }

                @Override // org.eclipse.jetty.util.Scanner.BulkListener
                public void filesChanged(List<String> list) throws Exception {
                    if (list != null && !list.isEmpty() && list.size() == 1 && Resource.newResource(list.get(0)).getFile().equals(PropertyUserStore.this._configResource.getFile())) {
                        PropertyUserStore.this.loadUsers();
                    }
                }
            });
            this._scanner.setReportExistingFilesOnStartup(true);
            this._scanner.setRecursive(false);
            this._scanner.start();
            return;
        }
        loadUsers();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        Scanner scanner = this._scanner;
        if (scanner != null) {
            scanner.stop();
        }
        this._scanner = null;
    }

    private void notifyUpdate(String str, Credential credential, String[] strArr) {
        List<UserListener> list = this._listeners;
        if (list != null) {
            for (UserListener userListener : list) {
                userListener.update(str, credential, strArr);
            }
        }
    }

    private void notifyRemove(String str) {
        List<UserListener> list = this._listeners;
        if (list != null) {
            for (UserListener userListener : list) {
                userListener.remove(str);
            }
        }
    }

    public void registerUserListener(UserListener userListener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList();
        }
        this._listeners.add(userListener);
    }
}
