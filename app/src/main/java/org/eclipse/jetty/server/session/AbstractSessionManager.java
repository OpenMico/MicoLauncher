package org.eclipse.jetty.server.session;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.util.statistic.SampleStatistic;

/* loaded from: classes5.dex */
public abstract class AbstractSessionManager extends AbstractLifeCycle implements SessionManager {
    public static final String SESSION_KNOWN_ONLY_TO_AUTHENTICATED = "org.eclipse.jetty.security.sessionKnownOnlytoAuthenticated";
    public static final int __distantFuture = 628992000;
    static final Logger __log = SessionHandler.LOG;
    static final HttpSessionContext __nullSessionContext = new HttpSessionContext() { // from class: org.eclipse.jetty.server.session.AbstractSessionManager.1
        @Override // javax.servlet.http.HttpSessionContext
        public HttpSession getSession(String str) {
            return null;
        }

        @Override // javax.servlet.http.HttpSessionContext
        public Enumeration getIds() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
    };
    protected boolean _checkingRemoteSessionIdEncoding;
    protected ContextHandler.Context _context;
    protected ClassLoader _loader;
    protected boolean _nodeIdInSessionId;
    protected int _refreshCookieAge;
    protected String _sessionComment;
    protected String _sessionDomain;
    protected SessionHandler _sessionHandler;
    protected SessionIdManager _sessionIdManager;
    protected String _sessionPath;
    public Set<SessionTrackingMode> _sessionTrackingModes;
    private boolean _usingURLs;
    public Set<SessionTrackingMode> __defaultSessionTrackingModes = Collections.unmodifiableSet(new HashSet(Arrays.asList(SessionTrackingMode.COOKIE, SessionTrackingMode.URL)));
    private boolean _usingCookies = true;
    protected int _dftMaxIdleSecs = -1;
    protected boolean _httpOnly = false;
    protected boolean _secureCookies = false;
    protected boolean _secureRequestOnly = true;
    protected final List<HttpSessionAttributeListener> _sessionAttributeListeners = new CopyOnWriteArrayList();
    protected final List<HttpSessionListener> _sessionListeners = new CopyOnWriteArrayList();
    protected String _sessionCookie = SessionManager.__DefaultSessionCookie;
    protected String _sessionIdPathParameterName = SessionManager.__DefaultSessionIdPathParameterName;
    protected String _sessionIdPathParameterNamePrefix = ";" + this._sessionIdPathParameterName + "=";
    protected int _maxCookieAge = -1;
    protected final CounterStatistic _sessionsStats = new CounterStatistic();
    protected final SampleStatistic _sessionTimeStats = new SampleStatistic();
    private SessionCookieConfig _cookieConfig = new SessionCookieConfig() { // from class: org.eclipse.jetty.server.session.AbstractSessionManager.2
        @Override // javax.servlet.SessionCookieConfig
        public String getComment() {
            return AbstractSessionManager.this._sessionComment;
        }

        @Override // javax.servlet.SessionCookieConfig
        public String getDomain() {
            return AbstractSessionManager.this._sessionDomain;
        }

        @Override // javax.servlet.SessionCookieConfig
        public int getMaxAge() {
            return AbstractSessionManager.this._maxCookieAge;
        }

        @Override // javax.servlet.SessionCookieConfig
        public String getName() {
            return AbstractSessionManager.this._sessionCookie;
        }

        @Override // javax.servlet.SessionCookieConfig
        public String getPath() {
            return AbstractSessionManager.this._sessionPath;
        }

        @Override // javax.servlet.SessionCookieConfig
        public boolean isHttpOnly() {
            return AbstractSessionManager.this._httpOnly;
        }

        @Override // javax.servlet.SessionCookieConfig
        public boolean isSecure() {
            return AbstractSessionManager.this._secureCookies;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setComment(String str) {
            AbstractSessionManager.this._sessionComment = str;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setDomain(String str) {
            AbstractSessionManager.this._sessionDomain = str;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setHttpOnly(boolean z) {
            AbstractSessionManager.this._httpOnly = z;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setMaxAge(int i) {
            AbstractSessionManager.this._maxCookieAge = i;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setName(String str) {
            AbstractSessionManager.this._sessionCookie = str;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setPath(String str) {
            AbstractSessionManager.this._sessionPath = str;
        }

        @Override // javax.servlet.SessionCookieConfig
        public void setSecure(boolean z) {
            AbstractSessionManager.this._secureCookies = z;
        }
    };

    /* loaded from: classes5.dex */
    public interface SessionIf extends HttpSession {
        AbstractSession getSession();
    }

    protected abstract void addSession(AbstractSession abstractSession);

    @Deprecated
    public int getMinSessions() {
        return 0;
    }

    public abstract AbstractSession getSession(String str);

    protected abstract void invalidateSessions() throws Exception;

    protected abstract AbstractSession newSession(HttpServletRequest httpServletRequest);

    protected abstract boolean removeSession(String str);

    public static HttpSession renewSession(HttpServletRequest httpServletRequest, HttpSession httpSession, boolean z) {
        HashMap hashMap = new HashMap();
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            hashMap.put(nextElement, httpSession.getAttribute(nextElement));
            httpSession.removeAttribute(nextElement);
        }
        httpSession.invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        if (z) {
            session.setAttribute(SESSION_KNOWN_ONLY_TO_AUTHENTICATED, Boolean.TRUE);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            session.setAttribute((String) entry.getKey(), entry.getValue());
        }
        return session;
    }

    public AbstractSessionManager() {
        setSessionTrackingModes(this.__defaultSessionTrackingModes);
    }

    public ContextHandler.Context getContext() {
        return this._context;
    }

    public ContextHandler getContextHandler() {
        return this._context.getContextHandler();
    }

    public String getSessionPath() {
        return this._sessionPath;
    }

    public int getMaxCookieAge() {
        return this._maxCookieAge;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public HttpCookie access(HttpSession httpSession, boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        AbstractSession session = ((SessionIf) httpSession).getSession();
        if (!session.access(currentTimeMillis) || !isUsingCookies()) {
            return null;
        }
        if (!session.isIdChanged() && (getSessionCookieConfig().getMaxAge() <= 0 || getRefreshCookieAge() <= 0 || (currentTimeMillis - session.getCookieSetTime()) / 1000 <= getRefreshCookieAge())) {
            return null;
        }
        ContextHandler.Context context = this._context;
        HttpCookie sessionCookie = getSessionCookie(httpSession, context == null ? "/" : context.getContextPath(), z);
        session.cookieSet();
        session.setIdChanged(false);
        return sessionCookie;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void addEventListener(EventListener eventListener) {
        if (eventListener instanceof HttpSessionAttributeListener) {
            this._sessionAttributeListeners.add((HttpSessionAttributeListener) eventListener);
        }
        if (eventListener instanceof HttpSessionListener) {
            this._sessionListeners.add((HttpSessionListener) eventListener);
        }
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void clearEventListeners() {
        this._sessionAttributeListeners.clear();
        this._sessionListeners.clear();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void complete(HttpSession httpSession) {
        ((SessionIf) httpSession).getSession().complete();
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        String initParameter;
        this._context = ContextHandler.getCurrentContext();
        this._loader = Thread.currentThread().getContextClassLoader();
        if (this._sessionIdManager == null) {
            Server server = getSessionHandler().getServer();
            synchronized (server) {
                this._sessionIdManager = server.getSessionIdManager();
                if (this._sessionIdManager == null) {
                    this._sessionIdManager = new HashSessionIdManager();
                    server.setSessionIdManager(this._sessionIdManager);
                }
            }
        }
        if (!this._sessionIdManager.isStarted()) {
            this._sessionIdManager.start();
        }
        ContextHandler.Context context = this._context;
        if (context != null) {
            String initParameter2 = context.getInitParameter(SessionManager.__SessionCookieProperty);
            if (initParameter2 != null) {
                this._sessionCookie = initParameter2;
            }
            String initParameter3 = this._context.getInitParameter(SessionManager.__SessionIdPathParameterNameProperty);
            if (initParameter3 != null) {
                setSessionIdPathParameterName(initParameter3);
            }
            if (this._maxCookieAge == -1 && (initParameter = this._context.getInitParameter(SessionManager.__MaxAgeProperty)) != null) {
                this._maxCookieAge = Integer.parseInt(initParameter.trim());
            }
            if (this._sessionDomain == null) {
                this._sessionDomain = this._context.getInitParameter(SessionManager.__SessionDomainProperty);
            }
            if (this._sessionPath == null) {
                this._sessionPath = this._context.getInitParameter(SessionManager.__SessionPathProperty);
            }
            String initParameter4 = this._context.getInitParameter(SessionManager.__CheckRemoteSessionEncoding);
            if (initParameter4 != null) {
                this._checkingRemoteSessionIdEncoding = Boolean.parseBoolean(initParameter4);
            }
        }
        super.doStart();
    }

    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        invalidateSessions();
        this._loader = null;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public boolean getHttpOnly() {
        return this._httpOnly;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public HttpSession getHttpSession(String str) {
        AbstractSession session = getSession(getSessionIdManager().getClusterId(str));
        if (session != null && !session.getNodeId().equals(str)) {
            session.setIdChanged(true);
        }
        return session;
    }

    public SessionIdManager getIdManager() {
        return getSessionIdManager();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public SessionIdManager getSessionIdManager() {
        return this._sessionIdManager;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public int getMaxInactiveInterval() {
        return this._dftMaxIdleSecs;
    }

    @Deprecated
    public int getMaxSessions() {
        return getSessionsMax();
    }

    public int getSessionsMax() {
        return (int) this._sessionsStats.getMax();
    }

    public int getSessionsTotal() {
        return (int) this._sessionsStats.getTotal();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    @Deprecated
    public SessionIdManager getMetaManager() {
        return getSessionIdManager();
    }

    public int getRefreshCookieAge() {
        return this._refreshCookieAge;
    }

    public boolean getSecureCookies() {
        return this._secureCookies;
    }

    public boolean isSecureRequestOnly() {
        return this._secureRequestOnly;
    }

    public void setSecureRequestOnly(boolean z) {
        this._secureRequestOnly = z;
    }

    public String getSessionCookie() {
        return this._sessionCookie;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public HttpCookie getSessionCookie(HttpSession httpSession, String str, boolean z) {
        HttpCookie httpCookie;
        if (!isUsingCookies()) {
            return null;
        }
        String str2 = this._sessionPath;
        if (str2 != null) {
            str = str2;
        }
        if (str == null || str.length() == 0) {
            str = "/";
        }
        String nodeId = getNodeId(httpSession);
        if (this._sessionComment == null) {
            httpCookie = new HttpCookie(this._sessionCookie, nodeId, this._sessionDomain, str, this._cookieConfig.getMaxAge(), this._cookieConfig.isHttpOnly(), this._cookieConfig.isSecure() || (isSecureRequestOnly() && z));
        } else {
            httpCookie = new HttpCookie(this._sessionCookie, nodeId, this._sessionDomain, str, this._cookieConfig.getMaxAge(), this._cookieConfig.isHttpOnly(), this._cookieConfig.isSecure() || (isSecureRequestOnly() && z), this._sessionComment, 1);
        }
        return httpCookie;
    }

    public String getSessionDomain() {
        return this._sessionDomain;
    }

    public SessionHandler getSessionHandler() {
        return this._sessionHandler;
    }

    public Map getSessionMap() {
        throw new UnsupportedOperationException();
    }

    public int getSessions() {
        return (int) this._sessionsStats.getCurrent();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public String getSessionIdPathParameterName() {
        return this._sessionIdPathParameterName;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public String getSessionIdPathParameterNamePrefix() {
        return this._sessionIdPathParameterNamePrefix;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public boolean isUsingCookies() {
        return this._usingCookies;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public boolean isValid(HttpSession httpSession) {
        return ((SessionIf) httpSession).getSession().isValid();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public String getClusterId(HttpSession httpSession) {
        return ((SessionIf) httpSession).getSession().getClusterId();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public String getNodeId(HttpSession httpSession) {
        return ((SessionIf) httpSession).getSession().getNodeId();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public HttpSession newHttpSession(HttpServletRequest httpServletRequest) {
        AbstractSession newSession = newSession(httpServletRequest);
        newSession.setMaxInactiveInterval(this._dftMaxIdleSecs);
        addSession(newSession, true);
        return newSession;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void removeEventListener(EventListener eventListener) {
        if (eventListener instanceof HttpSessionAttributeListener) {
            this._sessionAttributeListeners.remove(eventListener);
        }
        if (eventListener instanceof HttpSessionListener) {
            this._sessionListeners.remove(eventListener);
        }
    }

    @Deprecated
    public void resetStats() {
        statsReset();
    }

    public void statsReset() {
        this._sessionsStats.reset(getSessions());
        this._sessionTimeStats.reset();
    }

    public void setHttpOnly(boolean z) {
        this._httpOnly = z;
    }

    public void setIdManager(SessionIdManager sessionIdManager) {
        setSessionIdManager(sessionIdManager);
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setSessionIdManager(SessionIdManager sessionIdManager) {
        this._sessionIdManager = sessionIdManager;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setMaxInactiveInterval(int i) {
        this._dftMaxIdleSecs = i;
    }

    public void setRefreshCookieAge(int i) {
        this._refreshCookieAge = i;
    }

    public void setSessionCookie(String str) {
        this._sessionCookie = str;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setSessionHandler(SessionHandler sessionHandler) {
        this._sessionHandler = sessionHandler;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setSessionIdPathParameterName(String str) {
        String str2 = null;
        this._sessionIdPathParameterName = (str == null || "none".equals(str)) ? null : str;
        if (str != null && !"none".equals(str)) {
            str2 = ";" + this._sessionIdPathParameterName + "=";
        }
        this._sessionIdPathParameterNamePrefix = str2;
    }

    public void setUsingCookies(boolean z) {
        this._usingCookies = z;
    }

    protected void addSession(AbstractSession abstractSession, boolean z) {
        synchronized (this._sessionIdManager) {
            this._sessionIdManager.addSession(abstractSession);
            addSession(abstractSession);
        }
        if (z) {
            this._sessionsStats.increment();
            if (this._sessionListeners != null) {
                HttpSessionEvent httpSessionEvent = new HttpSessionEvent(abstractSession);
                for (HttpSessionListener httpSessionListener : this._sessionListeners) {
                    httpSessionListener.sessionCreated(httpSessionEvent);
                }
            }
        }
    }

    public boolean isNodeIdInSessionId() {
        return this._nodeIdInSessionId;
    }

    public void setNodeIdInSessionId(boolean z) {
        this._nodeIdInSessionId = z;
    }

    public void removeSession(HttpSession httpSession, boolean z) {
        removeSession(((SessionIf) httpSession).getSession(), z);
    }

    public void removeSession(AbstractSession abstractSession, boolean z) {
        if (removeSession(abstractSession.getClusterId())) {
            this._sessionsStats.decrement();
            this._sessionTimeStats.set(Math.round((System.currentTimeMillis() - abstractSession.getCreationTime()) / 1000.0d));
            this._sessionIdManager.removeSession(abstractSession);
            if (z) {
                this._sessionIdManager.invalidateAll(abstractSession.getClusterId());
            }
            if (z && this._sessionListeners != null) {
                HttpSessionEvent httpSessionEvent = new HttpSessionEvent(abstractSession);
                for (HttpSessionListener httpSessionListener : this._sessionListeners) {
                    httpSessionListener.sessionDestroyed(httpSessionEvent);
                }
            }
        }
    }

    public long getSessionTimeMax() {
        return this._sessionTimeStats.getMax();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return this.__defaultSessionTrackingModes;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return Collections.unmodifiableSet(this._sessionTrackingModes);
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
        this._sessionTrackingModes = new HashSet(set);
        this._usingCookies = this._sessionTrackingModes.contains(SessionTrackingMode.COOKIE);
        this._usingURLs = this._sessionTrackingModes.contains(SessionTrackingMode.URL);
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public boolean isUsingURLs() {
        return this._usingURLs;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public SessionCookieConfig getSessionCookieConfig() {
        return this._cookieConfig;
    }

    public long getSessionTimeTotal() {
        return this._sessionTimeStats.getTotal();
    }

    public double getSessionTimeMean() {
        return this._sessionTimeStats.getMean();
    }

    public double getSessionTimeStdDev() {
        return this._sessionTimeStats.getStdDev();
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public boolean isCheckingRemoteSessionIdEncoding() {
        return this._checkingRemoteSessionIdEncoding;
    }

    @Override // org.eclipse.jetty.server.SessionManager
    public void setCheckingRemoteSessionIdEncoding(boolean z) {
        this._checkingRemoteSessionIdEncoding = z;
    }

    public void doSessionAttributeListeners(AbstractSession abstractSession, String str, Object obj, Object obj2) {
        if (!this._sessionAttributeListeners.isEmpty()) {
            HttpSessionBindingEvent httpSessionBindingEvent = new HttpSessionBindingEvent(abstractSession, str, obj == null ? obj2 : obj);
            for (HttpSessionAttributeListener httpSessionAttributeListener : this._sessionAttributeListeners) {
                if (obj == null) {
                    httpSessionAttributeListener.attributeAdded(httpSessionBindingEvent);
                } else if (obj2 == null) {
                    httpSessionAttributeListener.attributeRemoved(httpSessionBindingEvent);
                } else {
                    httpSessionAttributeListener.attributeReplaced(httpSessionBindingEvent);
                }
            }
        }
    }
}
