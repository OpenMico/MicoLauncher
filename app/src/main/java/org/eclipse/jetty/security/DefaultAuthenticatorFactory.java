package org.eclipse.jetty.security;

import javax.servlet.ServletContext;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.ClientCertAuthenticator;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.security.authentication.SpnegoAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes5.dex */
public class DefaultAuthenticatorFactory implements Authenticator.Factory {
    LoginService _loginService;

    @Override // org.eclipse.jetty.security.Authenticator.Factory
    public Authenticator getAuthenticator(Server server, ServletContext servletContext, Authenticator.AuthConfiguration authConfiguration, IdentityService identityService, LoginService loginService) {
        Authenticator authenticator;
        String authMethod = authConfiguration.getAuthMethod();
        if (authMethod == null || "BASIC".equalsIgnoreCase(authMethod)) {
            authenticator = new BasicAuthenticator();
        } else if ("DIGEST".equalsIgnoreCase(authMethod)) {
            authenticator = new DigestAuthenticator();
        } else if ("FORM".equalsIgnoreCase(authMethod)) {
            authenticator = new FormAuthenticator();
        } else if (Constraint.__SPNEGO_AUTH.equalsIgnoreCase(authMethod)) {
            authenticator = new SpnegoAuthenticator();
        } else {
            authenticator = Constraint.__NEGOTIATE_AUTH.equalsIgnoreCase(authMethod) ? new SpnegoAuthenticator(Constraint.__NEGOTIATE_AUTH) : null;
        }
        return ("CLIENT_CERT".equalsIgnoreCase(authMethod) || Constraint.__CERT_AUTH2.equalsIgnoreCase(authMethod)) ? new ClientCertAuthenticator() : authenticator;
    }

    public LoginService getLoginService() {
        return this._loginService;
    }

    public void setLoginService(LoginService loginService) {
        this._loginService = loginService;
    }
}
