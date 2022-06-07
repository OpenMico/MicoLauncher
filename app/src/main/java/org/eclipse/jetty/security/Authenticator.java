package org.eclipse.jetty.security;

import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Server;

/* loaded from: classes5.dex */
public interface Authenticator {

    /* loaded from: classes5.dex */
    public interface AuthConfiguration {
        String getAuthMethod();

        IdentityService getIdentityService();

        String getInitParameter(String str);

        Set<String> getInitParameterNames();

        LoginService getLoginService();

        String getRealmName();

        boolean isSessionRenewedOnAuthentication();
    }

    /* loaded from: classes5.dex */
    public interface Factory {
        Authenticator getAuthenticator(Server server, ServletContext servletContext, AuthConfiguration authConfiguration, IdentityService identityService, LoginService loginService);
    }

    String getAuthMethod();

    boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException;

    void setConfiguration(AuthConfiguration authConfiguration);

    Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z) throws ServerAuthException;
}
