package org.eclipse.jetty.security.authentication;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.session.AbstractSessionManager;

/* loaded from: classes5.dex */
public abstract class LoginAuthenticator implements Authenticator {
    protected IdentityService _identityService;
    protected LoginService _loginService;
    private boolean _renewSession;

    public UserIdentity login(String str, Object obj, ServletRequest servletRequest) {
        UserIdentity login = this._loginService.login(str, obj);
        if (login == null) {
            return null;
        }
        renewSession((HttpServletRequest) servletRequest, null);
        return login;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public void setConfiguration(Authenticator.AuthConfiguration authConfiguration) {
        this._loginService = authConfiguration.getLoginService();
        if (this._loginService != null) {
            this._identityService = authConfiguration.getIdentityService();
            if (this._identityService != null) {
                this._renewSession = authConfiguration.isSessionRenewedOnAuthentication();
                return;
            }
            throw new IllegalStateException("No IdentityService for " + this + " in " + authConfiguration);
        }
        throw new IllegalStateException("No LoginService for " + this + " in " + authConfiguration);
    }

    public LoginService getLoginService() {
        return this._loginService;
    }

    protected HttpSession renewSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession(false);
        if (!(!this._renewSession || session == null || session.getAttribute(AbstractSessionManager.SESSION_KNOWN_ONLY_TO_AUTHENTICATED) == Boolean.TRUE)) {
            synchronized (this) {
                session = AbstractSessionManager.renewSession(httpServletRequest, session, true);
            }
        }
        return session;
    }
}
