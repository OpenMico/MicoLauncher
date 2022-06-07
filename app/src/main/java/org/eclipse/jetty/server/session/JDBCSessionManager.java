package org.eclipse.jetty.server.session;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class JDBCSessionManager extends AbstractSessionManager {
    private static final Logger LOG = Log.getLogger(JDBCSessionManager.class);
    protected JDBCSessionIdManager _jdbcSessionIdMgr = null;
    protected long _saveIntervalSec = 60;
    private ConcurrentHashMap<String, AbstractSession> _sessions;

    public void cacheInvalidate(Session session) {
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected void invalidateSessions() {
    }

    /* loaded from: classes5.dex */
    public class SessionData {
        private long _accessed;
        private Map<String, Object> _attributes;
        private String _canonicalContext;
        private long _cookieSet;
        private long _created;
        private long _expiryTime;
        private final String _id;
        private long _lastAccessed;
        private String _lastNode;
        private long _lastSaved;
        private long _maxIdleMs;
        private String _rowId;
        private String _virtualHost;

        public SessionData(String str) {
            this._maxIdleMs = -1L;
            this._id = str;
            this._created = System.currentTimeMillis();
            this._accessed = this._created;
            this._attributes = new HashMap();
            this._lastNode = JDBCSessionManager.this.getSessionIdManager().getWorkerName();
        }

        public SessionData(String str, Map<String, Object> map) {
            this._maxIdleMs = -1L;
            this._id = str;
            this._created = System.currentTimeMillis();
            this._accessed = this._created;
            this._attributes = map;
            this._lastNode = JDBCSessionManager.this.getSessionIdManager().getWorkerName();
        }

        public synchronized String getId() {
            return this._id;
        }

        public synchronized long getCreated() {
            return this._created;
        }

        protected synchronized void setCreated(long j) {
            this._created = j;
        }

        public synchronized long getAccessed() {
            return this._accessed;
        }

        protected synchronized void setAccessed(long j) {
            this._accessed = j;
        }

        public synchronized void setMaxIdleMs(long j) {
            this._maxIdleMs = j;
        }

        public synchronized long getMaxIdleMs() {
            return this._maxIdleMs;
        }

        public synchronized void setLastAccessed(long j) {
            this._lastAccessed = j;
        }

        public synchronized long getLastAccessed() {
            return this._lastAccessed;
        }

        public void setCookieSet(long j) {
            this._cookieSet = j;
        }

        public synchronized long getCookieSet() {
            return this._cookieSet;
        }

        public synchronized void setRowId(String str) {
            this._rowId = str;
        }

        protected synchronized String getRowId() {
            return this._rowId;
        }

        protected synchronized Map<String, Object> getAttributeMap() {
            return this._attributes;
        }

        protected synchronized void setAttributeMap(Map<String, Object> map) {
            this._attributes = map;
        }

        public synchronized void setLastNode(String str) {
            this._lastNode = str;
        }

        public synchronized String getLastNode() {
            return this._lastNode;
        }

        public synchronized void setCanonicalContext(String str) {
            this._canonicalContext = str;
        }

        public synchronized String getCanonicalContext() {
            return this._canonicalContext;
        }

        public synchronized long getLastSaved() {
            return this._lastSaved;
        }

        public synchronized void setLastSaved(long j) {
            this._lastSaved = j;
        }

        public synchronized void setExpiryTime(long j) {
            this._expiryTime = j;
        }

        public synchronized long getExpiryTime() {
            return this._expiryTime;
        }

        public synchronized void setVirtualHost(String str) {
            this._virtualHost = str;
        }

        public synchronized String getVirtualHost() {
            return this._virtualHost;
        }

        public String toString() {
            return "Session rowId=" + this._rowId + ",id=" + this._id + ",lastNode=" + this._lastNode + ",created=" + this._created + ",accessed=" + this._accessed + ",lastAccessed=" + this._lastAccessed + ",cookieSet=" + this._cookieSet + "lastSaved=" + this._lastSaved;
        }
    }

    /* loaded from: classes5.dex */
    public class Session extends AbstractSession {
        private static final long serialVersionUID = 5208464051134226143L;
        private final SessionData _data;
        private boolean _dirty = false;

        protected Session(HttpServletRequest httpServletRequest) {
            super(JDBCSessionManager.this, httpServletRequest);
            this._data = new SessionData(getClusterId(), getAttributeMap());
            if (JDBCSessionManager.this._dftMaxIdleSecs > 0) {
                this._data.setMaxIdleMs(JDBCSessionManager.this._dftMaxIdleSecs * 1000);
            }
            this._data.setCanonicalContext(JDBCSessionManager.this.canonicalize(JDBCSessionManager.this._context.getContextPath()));
            this._data.setVirtualHost(JDBCSessionManager.this.getVirtualHost(JDBCSessionManager.this._context));
            int maxInactiveInterval = getMaxInactiveInterval();
            this._data.setExpiryTime(maxInactiveInterval <= 0 ? 0L : System.currentTimeMillis() + (maxInactiveInterval * 1000));
        }

        protected Session(long j, SessionData sessionData) {
            super(JDBCSessionManager.this, sessionData.getCreated(), j, sessionData.getId());
            this._data = sessionData;
            if (JDBCSessionManager.this._dftMaxIdleSecs > 0) {
                this._data.setMaxIdleMs(JDBCSessionManager.this._dftMaxIdleSecs * 1000);
            }
            addAttributes(this._data.getAttributeMap());
            this._data.setAttributeMap(getAttributeMap());
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession, javax.servlet.http.HttpSession
        public void setAttribute(String str, Object obj) {
            super.setAttribute(str, obj);
            this._dirty = true;
        }

        @Override // org.eclipse.jetty.server.session.AbstractSession, javax.servlet.http.HttpSession
        public void removeAttribute(String str) {
            super.removeAttribute(str);
            this._dirty = true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void cookieSet() {
            SessionData sessionData = this._data;
            sessionData.setCookieSet(sessionData.getAccessed());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.server.session.AbstractSession
        public boolean access(long j) {
            if (!super.access(j)) {
                return false;
            }
            SessionData sessionData = this._data;
            sessionData.setLastAccessed(sessionData.getAccessed());
            this._data.setAccessed(j);
            int maxInactiveInterval = getMaxInactiveInterval();
            this._data.setExpiryTime(maxInactiveInterval <= 0 ? 0L : j + (maxInactiveInterval * 1000));
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void complete() {
            super.complete();
            try {
                try {
                    if (this._dirty) {
                        willPassivate();
                        JDBCSessionManager.this.updateSession(this._data);
                        didActivate();
                    } else if (this._data._accessed - this._data._lastSaved >= JDBCSessionManager.this.getSaveInterval() * 1000) {
                        JDBCSessionManager.this.updateSessionAccessTime(this._data);
                    }
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn("Problem persisting changed session data id=" + getId(), e);
                }
            } finally {
                this._dirty = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.server.session.AbstractSession
        public void timeout() throws IllegalStateException {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Timing out session id=" + getClusterId(), new Object[0]);
            }
            super.timeout();
        }
    }

    /* loaded from: classes5.dex */
    protected class ClassLoadingObjectInputStream extends ObjectInputStream {
        public ClassLoadingObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        public ClassLoadingObjectInputStream() throws IOException {
        }

        @Override // java.io.ObjectInputStream
        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            try {
                return Class.forName(objectStreamClass.getName(), false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException unused) {
                return super.resolveClass(objectStreamClass);
            }
        }
    }

    public void setSaveInterval(long j) {
        this._saveIntervalSec = j;
    }

    public long getSaveInterval() {
        return this._saveIntervalSec;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0186 A[Catch: Exception -> 0x0235, all -> 0x0233, TryCatch #1 {Exception -> 0x0235, blocks: (B:5:0x000a, B:8:0x001d, B:11:0x0042, B:12:0x004a, B:13:0x0062, B:16:0x0087, B:17:0x008f, B:19:0x00da, B:22:0x00ec, B:24:0x00fd, B:25:0x012f, B:26:0x0151, B:29:0x0186, B:33:0x019b, B:34:0x01ae, B:36:0x01b6, B:39:0x01bf, B:40:0x01cb, B:42:0x01d3, B:43:0x0207, B:44:0x0223), top: B:57:0x000a, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0223 A[Catch: Exception -> 0x0235, all -> 0x0233, TRY_LEAVE, TryCatch #1 {Exception -> 0x0235, blocks: (B:5:0x000a, B:8:0x001d, B:11:0x0042, B:12:0x004a, B:13:0x0062, B:16:0x0087, B:17:0x008f, B:19:0x00da, B:22:0x00ec, B:24:0x00fd, B:25:0x012f, B:26:0x0151, B:29:0x0186, B:33:0x019b, B:34:0x01ae, B:36:0x01b6, B:39:0x01bf, B:40:0x01cb, B:42:0x01d3, B:43:0x0207, B:44:0x0223), top: B:57:0x000a, outer: #0 }] */
    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.server.session.JDBCSessionManager.Session getSession(java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 577
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionManager.getSession(java.lang.String):org.eclipse.jetty.server.session.JDBCSessionManager$Session");
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public int getSessions() {
        int size;
        synchronized (this) {
            size = this._sessions.size();
        }
        return size;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        if (this._sessionIdManager != null) {
            this._jdbcSessionIdMgr = (JDBCSessionIdManager) this._sessionIdManager;
            this._sessions = new ConcurrentHashMap<>();
            super.doStart();
            return;
        }
        throw new IllegalStateException("No session id manager defined");
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._sessions.clear();
        this._sessions = null;
        super.doStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void invalidateSession(String str) {
        Session session;
        synchronized (this) {
            session = (Session) this._sessions.get(str);
        }
        if (session != null) {
            session.invalidate();
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected boolean removeSession(String str) {
        boolean z;
        synchronized (this) {
            Session session = (Session) this._sessions.remove(str);
            if (session != null) {
                try {
                    deleteSession(session._data);
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn("Problem deleting session id=" + str, e);
                }
            }
            z = session != null;
        }
        return z;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected void addSession(AbstractSession abstractSession) {
        if (abstractSession != null) {
            synchronized (this) {
                this._sessions.put(abstractSession.getClusterId(), abstractSession);
            }
            try {
                abstractSession.willPassivate();
                storeSession(((Session) abstractSession)._data);
                abstractSession.didActivate();
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn("Unable to store new session id=" + abstractSession.getId(), e);
            }
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new Session(httpServletRequest);
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public void removeSession(AbstractSession abstractSession, boolean z) {
        boolean z2;
        synchronized (this) {
            if (getSession(abstractSession.getClusterId()) != null) {
                z2 = true;
                removeSession(abstractSession.getClusterId());
            } else {
                z2 = false;
            }
        }
        if (z2) {
            this._sessionIdManager.removeSession(abstractSession);
            if (z) {
                this._sessionIdManager.invalidateAll(abstractSession.getClusterId());
            }
            if (z && !this._sessionListeners.isEmpty()) {
                HttpSessionEvent httpSessionEvent = new HttpSessionEvent(abstractSession);
                for (HttpSessionListener httpSessionListener : this._sessionListeners) {
                    httpSessionListener.sessionDestroyed(httpSessionEvent);
                }
            }
            if (!z) {
                abstractSession.willPassivate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void expire(List<?> list) {
        if (!isStopping() && !isStopped()) {
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            ListIterator<?> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                try {
                    String str = (String) listIterator.next();
                    if (LOG.isDebugEnabled()) {
                        Logger logger = LOG;
                        logger.debug("Expiring session id " + str, new Object[0]);
                    }
                    Session session = (Session) this._sessions.get(str);
                    if (session != null) {
                        session.timeout();
                        listIterator.remove();
                    } else if (LOG.isDebugEnabled()) {
                        Logger logger2 = LOG;
                        logger2.debug("Unrecognized session id=" + str, new Object[0]);
                    }
                } finally {
                    currentThread.setContextClassLoader(contextClassLoader);
                }
            }
        }
    }

    protected SessionData loadSession(final String str, final String str2, final String str3) throws Exception {
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        Runnable runnable = new Runnable() { // from class: org.eclipse.jetty.server.session.JDBCSessionManager.1
            /* JADX WARN: Removed duplicated region for block: B:32:0x00fd A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 266
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.JDBCSessionManager.AnonymousClass1.run():void");
            }
        };
        if (this._context == null) {
            runnable.run();
        } else {
            this._context.getContextHandler().handle(runnable);
        }
        if (atomicReference2.get() == null) {
            return (SessionData) atomicReference.get();
        }
        throw ((Exception) atomicReference2.get());
    }

    protected void storeSession(SessionData sessionData) throws Exception {
        if (sessionData != null) {
            Connection connection = getConnection();
            try {
                String calculateRowId = calculateRowId(sessionData);
                long currentTimeMillis = System.currentTimeMillis();
                connection.setAutoCommit(true);
                PreparedStatement prepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._insertSession);
                prepareStatement.setString(1, calculateRowId);
                prepareStatement.setString(2, sessionData.getId());
                prepareStatement.setString(3, sessionData.getCanonicalContext());
                prepareStatement.setString(4, sessionData.getVirtualHost());
                prepareStatement.setString(5, getSessionIdManager().getWorkerName());
                prepareStatement.setLong(6, sessionData.getAccessed());
                prepareStatement.setLong(7, sessionData.getLastAccessed());
                prepareStatement.setLong(8, sessionData.getCreated());
                prepareStatement.setLong(9, sessionData.getCookieSet());
                prepareStatement.setLong(10, currentTimeMillis);
                prepareStatement.setLong(11, sessionData.getExpiryTime());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new ObjectOutputStream(byteArrayOutputStream).writeObject(sessionData.getAttributeMap());
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                prepareStatement.setBinaryStream(12, (InputStream) new ByteArrayInputStream(byteArray), byteArray.length);
                prepareStatement.executeUpdate();
                sessionData.setRowId(calculateRowId);
                sessionData.setLastSaved(currentTimeMillis);
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Stored session " + sessionData, new Object[0]);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    protected void updateSession(SessionData sessionData) throws Exception {
        if (sessionData != null) {
            Connection connection = getConnection();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                connection.setAutoCommit(true);
                PreparedStatement prepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSession);
                prepareStatement.setString(1, getSessionIdManager().getWorkerName());
                prepareStatement.setLong(2, sessionData.getAccessed());
                prepareStatement.setLong(3, sessionData.getLastAccessed());
                prepareStatement.setLong(4, currentTimeMillis);
                prepareStatement.setLong(5, sessionData.getExpiryTime());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                new ObjectOutputStream(byteArrayOutputStream).writeObject(sessionData.getAttributeMap());
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                prepareStatement.setBinaryStream(6, (InputStream) new ByteArrayInputStream(byteArray), byteArray.length);
                prepareStatement.setString(7, sessionData.getRowId());
                prepareStatement.executeUpdate();
                sessionData.setLastSaved(currentTimeMillis);
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Updated session " + sessionData, new Object[0]);
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    protected void updateSessionNode(SessionData sessionData) throws Exception {
        String workerName = getSessionIdManager().getWorkerName();
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(true);
            PreparedStatement prepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionNode);
            prepareStatement.setString(1, workerName);
            prepareStatement.setString(2, sessionData.getRowId());
            prepareStatement.executeUpdate();
            prepareStatement.close();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Updated last node for session id=" + sessionData.getId() + ", lastNode = " + workerName, new Object[0]);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSessionAccessTime(SessionData sessionData) throws Exception {
        Connection connection = getConnection();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            connection.setAutoCommit(true);
            PreparedStatement prepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._updateSessionAccessTime);
            prepareStatement.setString(1, getSessionIdManager().getWorkerName());
            prepareStatement.setLong(2, sessionData.getAccessed());
            prepareStatement.setLong(3, sessionData.getLastAccessed());
            prepareStatement.setLong(4, currentTimeMillis);
            prepareStatement.setLong(5, sessionData.getExpiryTime());
            prepareStatement.setString(6, sessionData.getRowId());
            prepareStatement.executeUpdate();
            sessionData.setLastSaved(currentTimeMillis);
            prepareStatement.close();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Updated access time session id=" + sessionData.getId(), new Object[0]);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    protected void deleteSession(SessionData sessionData) throws Exception {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(true);
            PreparedStatement prepareStatement = connection.prepareStatement(this._jdbcSessionIdMgr._deleteSession);
            prepareStatement.setString(1, sessionData.getRowId());
            prepareStatement.executeUpdate();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Deleted Session " + sessionData, new Object[0]);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Connection getConnection() throws SQLException {
        return ((JDBCSessionIdManager) getSessionIdManager()).getConnection();
    }

    private String calculateRowId(SessionData sessionData) {
        return (canonicalize(this._context.getContextPath()) + "_" + getVirtualHost(this._context)) + "_" + sessionData.getId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getVirtualHost(ContextHandler.Context context) {
        String[] virtualHosts;
        if (context == null || (virtualHosts = context.getContextHandler().getVirtualHosts()) == null || virtualHosts.length == 0 || virtualHosts[0] == null) {
            return StringUtil.ALL_INTERFACES;
        }
        return virtualHosts[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String canonicalize(String str) {
        return str == null ? "" : str.replace(JsonPointer.SEPARATOR, '_').replace('.', '_').replace('\\', '_');
    }
}
