package org.eclipse.jetty.server.session;

import java.io.IOException;
import java.util.EnumSet;
import java.util.EventListener;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class SessionHandler extends ScopedHandler {
    private SessionManager _sessionManager;
    static final Logger LOG = Log.getLogger("org.eclipse.jetty.server.session");
    public static final EnumSet<SessionTrackingMode> DEFAULT_TRACKING = EnumSet.of(SessionTrackingMode.COOKIE, SessionTrackingMode.URL);

    public SessionHandler() {
        this(new HashSessionManager());
    }

    public SessionHandler(SessionManager sessionManager) {
        setSessionManager(sessionManager);
    }

    public SessionManager getSessionManager() {
        return this._sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        if (!isStarted()) {
            SessionManager sessionManager2 = this._sessionManager;
            if (getServer() != null) {
                getServer().getContainer().update((Object) this, (Object) sessionManager2, (Object) sessionManager, "sessionManager", true);
            }
            if (sessionManager != null) {
                sessionManager.setSessionHandler(this);
            }
            this._sessionManager = sessionManager;
            if (sessionManager2 != null) {
                sessionManager2.setSessionHandler(null);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        Server server2 = getServer();
        if (!(server2 == null || server2 == server)) {
            server2.getContainer().update((Object) this, (Object) this._sessionManager, (Object) null, "sessionManager", true);
        }
        super.setServer(server);
        if (server != null && server != server2) {
            server.getContainer().update((Object) this, (Object) null, (Object) this._sessionManager, "sessionManager", true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._sessionManager.start();
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._sessionManager.stop();
        super.doStop();
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        Throwable th;
        HttpSession httpSession;
        SessionManager sessionManager;
        HttpSession httpSession2;
        HttpSession httpSession3 = null;
        try {
            sessionManager = request.getSessionManager();
            try {
                httpSession = request.getSession(false);
                try {
                    if (sessionManager != this._sessionManager) {
                        request.setSessionManager(this._sessionManager);
                        request.setSession(null);
                        checkRequestedSessionId(request, httpServletRequest);
                    }
                    if (this._sessionManager != null) {
                        httpSession2 = request.getSession(false);
                        if (httpSession2 == null) {
                            httpSession2 = request.recoverNewSession(this._sessionManager);
                            if (httpSession2 != null) {
                                request.setSession(httpSession2);
                            }
                        } else if (httpSession2 != httpSession) {
                            try {
                                HttpCookie access = this._sessionManager.access(httpSession2, httpServletRequest.isSecure());
                                if (access != null) {
                                    request.getResponse().addCookie(access);
                                }
                                httpSession3 = httpSession2;
                            } catch (Throwable th2) {
                                th = th2;
                                httpSession3 = httpSession2;
                                if (httpSession3 != null) {
                                    this._sessionManager.complete(httpSession3);
                                }
                                HttpSession session = request.getSession(false);
                                if (!(session == null || httpSession != null || session == httpSession3)) {
                                    this._sessionManager.complete(session);
                                }
                                if (!(sessionManager == null || sessionManager == this._sessionManager)) {
                                    request.setSessionManager(sessionManager);
                                    request.setSession(httpSession);
                                }
                                throw th;
                            }
                        }
                    } else {
                        httpSession2 = null;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("sessionManager=" + this._sessionManager, new Object[0]);
                        LOG.debug("session=" + httpSession2, new Object[0]);
                    }
                    if (this._nextScope != null) {
                        this._nextScope.doScope(str, request, httpServletRequest, httpServletResponse);
                    } else if (this._outerScope != null) {
                        this._outerScope.doHandle(str, request, httpServletRequest, httpServletResponse);
                    } else {
                        doHandle(str, request, httpServletRequest, httpServletResponse);
                    }
                    if (httpSession3 != null) {
                        this._sessionManager.complete(httpSession3);
                    }
                    HttpSession session2 = request.getSession(false);
                    if (!(session2 == null || httpSession != null || session2 == httpSession3)) {
                        this._sessionManager.complete(session2);
                    }
                    if (sessionManager != null && sessionManager != this._sessionManager) {
                        request.setSessionManager(sessionManager);
                        request.setSession(httpSession);
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
                httpSession = null;
            }
        } catch (Throwable th5) {
            th = th5;
            sessionManager = null;
            httpSession = null;
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (never()) {
            nextHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._nextScope != null && this._nextScope == this._handler) {
            this._nextScope.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._handler != null) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    protected void checkRequestedSessionId(Request request, HttpServletRequest httpServletRequest) {
        boolean z;
        int indexOf;
        char charAt;
        Cookie[] cookies;
        String requestedSessionId = httpServletRequest.getRequestedSessionId();
        SessionManager sessionManager = getSessionManager();
        if (requestedSessionId != null && sessionManager != null) {
            HttpSession httpSession = sessionManager.getHttpSession(requestedSessionId);
            if (httpSession != null && sessionManager.isValid(httpSession)) {
                request.setSession(httpSession);
            }
        } else if (DispatcherType.REQUEST.equals(request.getDispatcherType())) {
            HttpSession httpSession2 = null;
            boolean z2 = true;
            if (this._sessionManager.isUsingCookies() && (cookies = httpServletRequest.getCookies()) != null && cookies.length > 0) {
                String name = sessionManager.getSessionCookieConfig().getName();
                String str = requestedSessionId;
                HttpSession httpSession3 = null;
                int i = 0;
                boolean z3 = false;
                while (true) {
                    if (i >= cookies.length) {
                        z = z3;
                        requestedSessionId = str;
                        httpSession2 = httpSession3;
                        break;
                    }
                    if (name.equalsIgnoreCase(cookies[i].getName())) {
                        String value = cookies[i].getValue();
                        LOG.debug("Got Session ID {} from cookie", value);
                        if (value != null) {
                            HttpSession httpSession4 = sessionManager.getHttpSession(value);
                            if (httpSession4 != null && sessionManager.isValid(httpSession4)) {
                                requestedSessionId = value;
                                z = true;
                                httpSession2 = httpSession4;
                                break;
                            }
                            httpSession3 = httpSession4;
                            str = value;
                            z3 = true;
                        } else {
                            LOG.warn("null session id from cookie", new Object[0]);
                            str = value;
                            z3 = true;
                        }
                    }
                    i++;
                }
            } else {
                z = false;
            }
            if (requestedSessionId == null || httpSession2 == null) {
                String requestURI = httpServletRequest.getRequestURI();
                String sessionIdPathParameterNamePrefix = sessionManager.getSessionIdPathParameterNamePrefix();
                if (sessionIdPathParameterNamePrefix != null && (indexOf = requestURI.indexOf(sessionIdPathParameterNamePrefix)) >= 0) {
                    int length = indexOf + sessionIdPathParameterNamePrefix.length();
                    int i2 = length;
                    while (i2 < requestURI.length() && (charAt = requestURI.charAt(i2)) != ';' && charAt != '#' && charAt != '?' && charAt != '/') {
                        i2++;
                    }
                    requestedSessionId = requestURI.substring(length, i2);
                    httpSession2 = sessionManager.getHttpSession(requestedSessionId);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Got Session ID {} from URL", requestedSessionId);
                    }
                    z = false;
                }
            }
            request.setRequestedSessionId(requestedSessionId);
            if (requestedSessionId == null || !z) {
                z2 = false;
            }
            request.setRequestedSessionIdFromCookie(z2);
            if (httpSession2 != null && sessionManager.isValid(httpSession2)) {
                request.setSession(httpSession2);
            }
        }
    }

    public void addEventListener(EventListener eventListener) {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.addEventListener(eventListener);
        }
    }

    public void clearEventListeners() {
        SessionManager sessionManager = this._sessionManager;
        if (sessionManager != null) {
            sessionManager.clearEventListeners();
        }
    }
}
