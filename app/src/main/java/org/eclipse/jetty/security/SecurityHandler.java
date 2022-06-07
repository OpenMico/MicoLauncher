package org.eclipse.jetty.security;

import java.io.IOException;
import java.io.PrintStream;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class SecurityHandler extends HandlerWrapper implements Authenticator.AuthConfiguration {
    private String _authMethod;
    private Authenticator _authenticator;
    private IdentityService _identityService;
    private LoginService _loginService;
    private boolean _loginServiceShared;
    private String _realmName;
    private static final Logger LOG = Log.getLogger(SecurityHandler.class);
    public static Principal __NO_USER = new Principal() { // from class: org.eclipse.jetty.security.SecurityHandler.2
        @Override // java.security.Principal
        public String getName() {
            return null;
        }

        @Override // java.security.Principal
        public String toString() {
            return "No User";
        }
    };
    public static Principal __NOBODY = new Principal() { // from class: org.eclipse.jetty.security.SecurityHandler.3
        @Override // java.security.Principal
        public String getName() {
            return "Nobody";
        }

        @Override // java.security.Principal
        public String toString() {
            return getName();
        }
    };
    private boolean _checkWelcomeFiles = false;
    private Authenticator.Factory _authenticatorFactory = new DefaultAuthenticatorFactory();
    private final Map<String, String> _initParameters = new HashMap();
    private boolean _renewSession = true;

    protected abstract boolean checkUserDataPermissions(String str, Request request, Response response, Object obj) throws IOException;

    protected abstract boolean checkWebResourcePermissions(String str, Request request, Response response, Object obj, UserIdentity userIdentity) throws IOException;

    protected abstract boolean isAuthMandatory(Request request, Response response, Object obj);

    protected abstract Object prepareConstraintInfo(String str, Request request);

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        if (!isStarted()) {
            this._identityService = identityService;
            return;
        }
        throw new IllegalStateException("Started");
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public LoginService getLoginService() {
        return this._loginService;
    }

    public void setLoginService(LoginService loginService) {
        if (!isStarted()) {
            this._loginService = loginService;
            this._loginServiceShared = false;
            return;
        }
        throw new IllegalStateException("Started");
    }

    public Authenticator getAuthenticator() {
        return this._authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        if (!isStarted()) {
            this._authenticator = authenticator;
            return;
        }
        throw new IllegalStateException("Started");
    }

    public Authenticator.Factory getAuthenticatorFactory() {
        return this._authenticatorFactory;
    }

    public void setAuthenticatorFactory(Authenticator.Factory factory) {
        if (!isRunning()) {
            this._authenticatorFactory = factory;
            return;
        }
        throw new IllegalStateException("running");
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public String getRealmName() {
        return this._realmName;
    }

    public void setRealmName(String str) {
        if (!isRunning()) {
            this._realmName = str;
            return;
        }
        throw new IllegalStateException("running");
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public String getAuthMethod() {
        return this._authMethod;
    }

    public void setAuthMethod(String str) {
        if (!isRunning()) {
            this._authMethod = str;
            return;
        }
        throw new IllegalStateException("running");
    }

    public boolean isCheckWelcomeFiles() {
        return this._checkWelcomeFiles;
    }

    public void setCheckWelcomeFiles(boolean z) {
        if (!isRunning()) {
            this._checkWelcomeFiles = z;
            return;
        }
        throw new IllegalStateException("running");
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public String getInitParameter(String str) {
        return this._initParameters.get(str);
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public Set<String> getInitParameterNames() {
        return this._initParameters.keySet();
    }

    public String setInitParameter(String str, String str2) {
        if (!isRunning()) {
            return this._initParameters.put(str, str2);
        }
        throw new IllegalStateException("running");
    }

    protected LoginService findLoginService() {
        List<LoginService> beans = getServer().getBeans(LoginService.class);
        String realmName = getRealmName();
        if (realmName != null) {
            for (LoginService loginService : beans) {
                if (loginService.getName() != null && loginService.getName().equals(realmName)) {
                    return loginService;
                }
            }
            return null;
        } else if (beans.size() == 1) {
            return (LoginService) beans.get(0);
        } else {
            return null;
        }
    }

    protected IdentityService findIdentityService() {
        return (IdentityService) getServer().getBean(IdentityService.class);
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        Authenticator.Factory factory;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext != null) {
            Enumeration initParameterNames = currentContext.getInitParameterNames();
            while (initParameterNames != null && initParameterNames.hasMoreElements()) {
                String str = (String) initParameterNames.nextElement();
                if (str.startsWith("org.eclipse.jetty.security.") && getInitParameter(str) == null) {
                    setInitParameter(str, currentContext.getInitParameter(str));
                }
            }
            currentContext.getContextHandler().addEventListener(new HttpSessionListener() { // from class: org.eclipse.jetty.security.SecurityHandler.1
                @Override // javax.servlet.http.HttpSessionListener
                public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
                }

                @Override // javax.servlet.http.HttpSessionListener
                public void sessionCreated(HttpSessionEvent httpSessionEvent) {
                    Request request;
                    AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
                    if (currentConnection != null && (request = currentConnection.getRequest()) != null && request.isSecure()) {
                        httpSessionEvent.getSession().setAttribute(AbstractSessionManager.SESSION_KNOWN_ONLY_TO_AUTHENTICATED, Boolean.TRUE);
                    }
                }
            });
        }
        if (this._loginService == null) {
            this._loginService = findLoginService();
            if (this._loginService != null) {
                this._loginServiceShared = true;
            }
        }
        if (this._identityService == null) {
            LoginService loginService = this._loginService;
            if (loginService != null) {
                this._identityService = loginService.getIdentityService();
            }
            PrintStream printStream = System.err;
            printStream.println("Null identity service, trying login service: " + this._identityService);
            if (this._identityService == null) {
                this._identityService = findIdentityService();
            }
            PrintStream printStream2 = System.err;
            printStream2.println("Finding identity service: " + this._identityService);
            if (this._identityService == null && this._realmName != null) {
                this._identityService = new DefaultIdentityService();
            }
        }
        if (this._loginService != null) {
            PrintStream printStream3 = System.err;
            printStream3.println("LoginService=" + this._loginService + " identityService=" + this._identityService);
            if (this._loginService.getIdentityService() == null) {
                this._loginService.setIdentityService(this._identityService);
            } else if (this._loginService.getIdentityService() != this._identityService) {
                throw new IllegalStateException("LoginService has different IdentityService to " + this);
            }
        }
        if (!this._loginServiceShared) {
            LoginService loginService2 = this._loginService;
            if (loginService2 instanceof LifeCycle) {
                ((LifeCycle) loginService2).start();
            }
        }
        if (!(this._authenticator != null || (factory = this._authenticatorFactory) == null || this._identityService == null)) {
            this._authenticator = factory.getAuthenticator(getServer(), ContextHandler.getCurrentContext(), this, this._identityService, this._loginService);
            Authenticator authenticator = this._authenticator;
            if (authenticator != null) {
                this._authMethod = authenticator.getAuthMethod();
            }
        }
        Authenticator authenticator2 = this._authenticator;
        if (authenticator2 != null) {
            authenticator2.setConfiguration(this);
            Authenticator authenticator3 = this._authenticator;
            if (authenticator3 instanceof LifeCycle) {
                ((LifeCycle) authenticator3).start();
            }
        } else if (this._realmName != null) {
            Logger logger = LOG;
            logger.warn("No ServerAuthentication for " + this, new Object[0]);
            throw new IllegalStateException("No ServerAuthentication");
        }
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        if (!this._loginServiceShared) {
            LoginService loginService = this._loginService;
            if (loginService instanceof LifeCycle) {
                ((LifeCycle) loginService).stop();
            }
        }
    }

    protected boolean checkSecurity(Request request) {
        switch (request.getDispatcherType()) {
            case REQUEST:
            case ASYNC:
                return true;
            case FORWARD:
                if (!this._checkWelcomeFiles || request.getAttribute("org.eclipse.jetty.server.welcome") == null) {
                    return false;
                }
                request.removeAttribute("org.eclipse.jetty.server.welcome");
                return true;
            default:
                return false;
        }
    }

    @Override // org.eclipse.jetty.security.Authenticator.AuthConfiguration
    public boolean isSessionRenewedOnAuthentication() {
        return this._renewSession;
    }

    public void setSessionRenewedOnAuthentication(boolean z) {
        this._renewSession = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.eclipse.jetty.security.IdentityService] */
    /* JADX WARN: Type inference failed for: r0v8, types: [org.eclipse.jetty.security.IdentityService] */
    /* JADX WARN: Type inference failed for: r1v1, types: [org.eclipse.jetty.security.IdentityService] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v19, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v40 */
    /* JADX WARN: Type inference failed for: r1v41 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /* JADX WARN: Type inference failed for: r1v43 */
    /* JADX WARN: Type inference failed for: r1v44 */
    /* JADX WARN: Type inference failed for: r1v45 */
    /* JADX WARN: Type inference failed for: r1v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Unknown variable types count: 3 */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handle(java.lang.String r20, org.eclipse.jetty.server.Request r21, javax.servlet.http.HttpServletRequest r22, javax.servlet.http.HttpServletResponse r23) throws java.io.IOException, javax.servlet.ServletException {
        /*
            Method dump skipped, instructions count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.SecurityHandler.handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    public static SecurityHandler getCurrentSecurityHandler() {
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext == null) {
            return null;
        }
        return (SecurityHandler) currentContext.getContextHandler().getChildHandlerByClass(SecurityHandler.class);
    }

    public void logout(Authentication.User user) {
        LOG.debug("logout {}", user);
        LoginService loginService = getLoginService();
        if (loginService != null) {
            loginService.logout(user.getUserIdentity());
        }
        IdentityService identityService = getIdentityService();
        if (identityService != null) {
            identityService.disassociate(null);
        }
    }

    /* loaded from: classes5.dex */
    public class NotChecked implements Principal {
        @Override // java.security.Principal
        public String getName() {
            return null;
        }

        @Override // java.security.Principal
        public String toString() {
            return "NOT CHECKED";
        }

        public NotChecked() {
            SecurityHandler.this = r1;
        }

        public SecurityHandler getSecurityHandler() {
            return SecurityHandler.this;
        }
    }
}
