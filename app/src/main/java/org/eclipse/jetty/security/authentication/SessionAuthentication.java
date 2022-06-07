package org.eclipse.jetty.security.authentication;

import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class SessionAuthentication implements Serializable, HttpSessionActivationListener, HttpSessionBindingListener, Authentication.User {
    private static final Logger LOG = Log.getLogger(SessionAuthentication.class);
    public static final String __J_AUTHENTICATED = "org.eclipse.jetty.security.UserIdentity";
    private static final long serialVersionUID = -4643200685888258706L;
    private final Object _credentials;
    private final String _method;
    private final String _name;
    private transient HttpSession _session;
    private transient UserIdentity _userIdentity;

    @Override // javax.servlet.http.HttpSessionActivationListener
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
    }

    public SessionAuthentication(String str, UserIdentity userIdentity, Object obj) {
        this._method = str;
        this._userIdentity = userIdentity;
        this._name = this._userIdentity.getUserPrincipal().getName();
        this._credentials = obj;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public String getAuthMethod() {
        return this._method;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public UserIdentity getUserIdentity() {
        return this._userIdentity;
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public boolean isUserInRole(UserIdentity.Scope scope, String str) {
        return this._userIdentity.isUserInRole(str, scope);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        SecurityHandler currentSecurityHandler = SecurityHandler.getCurrentSecurityHandler();
        if (currentSecurityHandler != null) {
            LoginService loginService = currentSecurityHandler.getLoginService();
            if (loginService != null) {
                this._userIdentity = loginService.login(this._name, this._credentials);
                LOG.debug("Deserialized and relogged in {}", this);
                return;
            }
            throw new IllegalStateException("!LoginService");
        }
        throw new IllegalStateException("!SecurityHandler");
    }

    @Override // org.eclipse.jetty.server.Authentication.User
    public void logout() {
        HttpSession httpSession = this._session;
        if (!(httpSession == null || httpSession.getAttribute(__J_AUTHENTICATED) == null)) {
            this._session.removeAttribute(__J_AUTHENTICATED);
        }
        doLogout();
    }

    private void doLogout() {
        SecurityHandler currentSecurityHandler = SecurityHandler.getCurrentSecurityHandler();
        if (currentSecurityHandler != null) {
            currentSecurityHandler.logout(this);
        }
        HttpSession httpSession = this._session;
        if (httpSession != null) {
            httpSession.removeAttribute(AbstractSessionManager.SESSION_KNOWN_ONLY_TO_AUTHENTICATED);
        }
    }

    public String toString() {
        return RtspHeaders.Names.SESSION + super.toString();
    }

    @Override // javax.servlet.http.HttpSessionActivationListener
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        if (this._session == null) {
            this._session = httpSessionEvent.getSession();
        }
    }

    @Override // javax.servlet.http.HttpSessionBindingListener
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (this._session == null) {
            this._session = httpSessionBindingEvent.getSession();
        }
    }

    @Override // javax.servlet.http.HttpSessionBindingListener
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        doLogout();
    }
}
