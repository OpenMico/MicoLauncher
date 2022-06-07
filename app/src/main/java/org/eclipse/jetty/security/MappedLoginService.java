package org.eclipse.jetty.security;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public abstract class MappedLoginService extends AbstractLifeCycle implements LoginService {
    private static final Logger LOG = Log.getLogger(MappedLoginService.class);
    protected String _name;
    protected IdentityService _identityService = new DefaultIdentityService();
    protected final ConcurrentMap<String, UserIdentity> _users = new ConcurrentHashMap();

    /* loaded from: classes5.dex */
    public static class Anonymous implements Serializable, UserPrincipal {
        private static final long serialVersionUID = 1097640442553284845L;

        @Override // org.eclipse.jetty.security.MappedLoginService.UserPrincipal
        public boolean authenticate(Object obj) {
            return false;
        }

        @Override // java.security.Principal
        public String getName() {
            return "Anonymous";
        }

        @Override // org.eclipse.jetty.security.MappedLoginService.UserPrincipal
        public boolean isAuthenticated() {
            return false;
        }
    }

    /* loaded from: classes5.dex */
    public interface UserPrincipal extends Serializable, Principal {
        boolean authenticate(Object obj);

        boolean isAuthenticated();
    }

    protected abstract UserIdentity loadUser(String str);

    protected abstract void loadUsers() throws IOException;

    @Override // org.eclipse.jetty.security.LoginService
    public String getName() {
        return this._name;
    }

    @Override // org.eclipse.jetty.security.LoginService
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public ConcurrentMap<String, UserIdentity> getUsers() {
        return this._users;
    }

    @Override // org.eclipse.jetty.security.LoginService
    public void setIdentityService(IdentityService identityService) {
        if (!isRunning()) {
            this._identityService = identityService;
            return;
        }
        throw new IllegalStateException("Running");
    }

    public void setName(String str) {
        if (!isRunning()) {
            this._name = str;
            return;
        }
        throw new IllegalStateException("Running");
    }

    public void setUsers(Map<String, UserIdentity> map) {
        if (!isRunning()) {
            this._users.clear();
            this._users.putAll(map);
            return;
        }
        throw new IllegalStateException("Running");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        loadUsers();
        super.doStart();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
    }

    @Override // org.eclipse.jetty.security.LoginService
    public void logout(UserIdentity userIdentity) {
        LOG.debug("logout {}", userIdentity);
    }

    public String toString() {
        return getClass().getSimpleName() + "[" + this._name + "]";
    }

    protected synchronized UserIdentity putUser(String str, Object obj) {
        UserIdentity userIdentity;
        if (obj instanceof UserIdentity) {
            userIdentity = (UserIdentity) obj;
        } else {
            Credential credential = obj instanceof Credential ? (Credential) obj : Credential.getCredential(obj.toString());
            KnownUser knownUser = new KnownUser(str, credential);
            Subject subject = new Subject();
            subject.getPrincipals().add(knownUser);
            subject.getPrivateCredentials().add(credential);
            subject.setReadOnly();
            userIdentity = this._identityService.newUserIdentity(subject, knownUser, IdentityService.NO_ROLES);
        }
        this._users.put(str, userIdentity);
        return userIdentity;
    }

    public synchronized UserIdentity putUser(String str, Credential credential, String[] strArr) {
        UserIdentity newUserIdentity;
        KnownUser knownUser = new KnownUser(str, credential);
        Subject subject = new Subject();
        subject.getPrincipals().add(knownUser);
        subject.getPrivateCredentials().add(credential);
        if (strArr != null) {
            for (String str2 : strArr) {
                subject.getPrincipals().add(new RolePrincipal(str2));
            }
        }
        subject.setReadOnly();
        newUserIdentity = this._identityService.newUserIdentity(subject, knownUser, strArr);
        this._users.put(str, newUserIdentity);
        return newUserIdentity;
    }

    public void removeUser(String str) {
        this._users.remove(str);
    }

    public UserIdentity login(String str, Object obj) {
        UserIdentity userIdentity = this._users.get(str);
        if (userIdentity == null) {
            userIdentity = loadUser(str);
        }
        if (userIdentity == null || !((UserPrincipal) userIdentity.getUserPrincipal()).authenticate(obj)) {
            return null;
        }
        return userIdentity;
    }

    @Override // org.eclipse.jetty.security.LoginService
    public boolean validate(UserIdentity userIdentity) {
        return this._users.containsKey(userIdentity.getUserPrincipal().getName()) || loadUser(userIdentity.getUserPrincipal().getName()) != null;
    }

    /* loaded from: classes5.dex */
    public static class RolePrincipal implements Serializable, Principal {
        private static final long serialVersionUID = 2998397924051854402L;
        private final String _roleName;

        public RolePrincipal(String str) {
            this._roleName = str;
        }

        @Override // java.security.Principal
        public String getName() {
            return this._roleName;
        }
    }

    /* loaded from: classes5.dex */
    public static class KnownUser implements Serializable, UserPrincipal {
        private static final long serialVersionUID = -6226920753748399662L;
        private final Credential _credential;
        private final String _name;

        @Override // org.eclipse.jetty.security.MappedLoginService.UserPrincipal
        public boolean isAuthenticated() {
            return true;
        }

        public KnownUser(String str, Credential credential) {
            this._name = str;
            this._credential = credential;
        }

        @Override // org.eclipse.jetty.security.MappedLoginService.UserPrincipal
        public boolean authenticate(Object obj) {
            Credential credential = this._credential;
            return credential != null && credential.check(obj);
        }

        @Override // java.security.Principal
        public String getName() {
            return this._name;
        }

        @Override // java.security.Principal
        public String toString() {
            return this._name;
        }
    }
}
