package org.eclipse.jetty.server.session;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class AbstractSession implements AbstractSessionManager.SessionIf {
    static final Logger LOG = SessionHandler.LOG;
    private long _accessed;
    private final Map<String, Object> _attributes;
    private final String _clusterId;
    private long _cookieSet;
    private final long _created;
    private boolean _doInvalidate;
    private boolean _idChanged;
    private boolean _invalid;
    private long _lastAccessed;
    private final AbstractSessionManager _manager;
    private long _maxIdleMs;
    private boolean _newSession;
    private final String _nodeId;
    private int _requests;

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager.SessionIf
    public AbstractSession getSession() {
        return this;
    }

    public AbstractSession(AbstractSessionManager abstractSessionManager, HttpServletRequest httpServletRequest) {
        this._attributes = new HashMap();
        this._manager = abstractSessionManager;
        this._newSession = true;
        this._created = System.currentTimeMillis();
        this._clusterId = this._manager._sessionIdManager.newSessionId(httpServletRequest, this._created);
        this._nodeId = this._manager._sessionIdManager.getNodeId(this._clusterId, httpServletRequest);
        long j = this._created;
        this._accessed = j;
        this._lastAccessed = j;
        this._requests = 1;
        this._maxIdleMs = this._manager._dftMaxIdleSecs > 0 ? this._manager._dftMaxIdleSecs * 1000 : -1L;
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("new session & id " + this._nodeId + StringUtils.SPACE + this._clusterId, new Object[0]);
        }
    }

    public AbstractSession(AbstractSessionManager abstractSessionManager, long j, long j2, String str) {
        this._attributes = new HashMap();
        this._manager = abstractSessionManager;
        this._created = j;
        this._clusterId = str;
        this._nodeId = this._manager._sessionIdManager.getNodeId(this._clusterId, null);
        this._accessed = j2;
        this._lastAccessed = j2;
        this._requests = 1;
        this._maxIdleMs = this._manager._dftMaxIdleSecs > 0 ? this._manager._dftMaxIdleSecs * 1000 : -1L;
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("new session " + this._nodeId + StringUtils.SPACE + this._clusterId, new Object[0]);
        }
    }

    public void checkValid() throws IllegalStateException {
        if (this._invalid) {
            throw new IllegalStateException();
        }
    }

    public long getAccessed() {
        long j;
        synchronized (this) {
            j = this._accessed;
        }
        return j;
    }

    @Override // javax.servlet.http.HttpSession
    public Object getAttribute(String str) {
        Object obj;
        synchronized (this) {
            checkValid();
            obj = this._attributes.get(str);
        }
        return obj;
    }

    public int getAttributes() {
        int size;
        synchronized (this) {
            checkValid();
            size = this._attributes.size();
        }
        return size;
    }

    @Override // javax.servlet.http.HttpSession
    public Enumeration<String> getAttributeNames() {
        Enumeration<String> enumeration;
        synchronized (this) {
            checkValid();
            enumeration = Collections.enumeration(this._attributes == null ? Collections.EMPTY_LIST : new ArrayList(this._attributes.keySet()));
        }
        return enumeration;
    }

    public Set<String> getNames() {
        HashSet hashSet;
        synchronized (this) {
            hashSet = new HashSet(this._attributes.keySet());
        }
        return hashSet;
    }

    public long getCookieSetTime() {
        return this._cookieSet;
    }

    @Override // javax.servlet.http.HttpSession
    public long getCreationTime() throws IllegalStateException {
        return this._created;
    }

    @Override // javax.servlet.http.HttpSession
    public String getId() throws IllegalStateException {
        return this._manager._nodeIdInSessionId ? this._nodeId : this._clusterId;
    }

    public String getNodeId() {
        return this._nodeId;
    }

    public String getClusterId() {
        return this._clusterId;
    }

    @Override // javax.servlet.http.HttpSession
    public long getLastAccessedTime() throws IllegalStateException {
        checkValid();
        return this._lastAccessed;
    }

    @Override // javax.servlet.http.HttpSession
    public int getMaxInactiveInterval() {
        checkValid();
        return (int) (this._maxIdleMs / 1000);
    }

    @Override // javax.servlet.http.HttpSession
    public ServletContext getServletContext() {
        return this._manager._context;
    }

    @Override // javax.servlet.http.HttpSession
    @Deprecated
    public HttpSessionContext getSessionContext() throws IllegalStateException {
        checkValid();
        return AbstractSessionManager.__nullSessionContext;
    }

    @Override // javax.servlet.http.HttpSession
    @Deprecated
    public Object getValue(String str) throws IllegalStateException {
        return getAttribute(str);
    }

    @Override // javax.servlet.http.HttpSession
    @Deprecated
    public String[] getValueNames() throws IllegalStateException {
        synchronized (this) {
            checkValid();
            if (this._attributes == null) {
                return new String[0];
            }
            return (String[]) this._attributes.keySet().toArray(new String[this._attributes.size()]);
        }
    }

    protected Map<String, Object> getAttributeMap() {
        return this._attributes;
    }

    protected void addAttributes(Map<String, Object> map) {
        this._attributes.putAll(map);
    }

    public boolean access(long j) {
        synchronized (this) {
            if (this._invalid) {
                return false;
            }
            this._newSession = false;
            this._lastAccessed = this._accessed;
            this._accessed = j;
            if (this._maxIdleMs <= 0 || this._lastAccessed <= 0 || this._lastAccessed + this._maxIdleMs >= j) {
                this._requests++;
                return true;
            }
            invalidate();
            return false;
        }
    }

    public void complete() {
        synchronized (this) {
            this._requests--;
            if (this._doInvalidate && this._requests <= 0) {
                doInvalidate();
            }
        }
    }

    public void timeout() throws IllegalStateException {
        this._manager.removeSession(this, true);
        synchronized (this) {
            if (!this._invalid) {
                if (this._requests <= 0) {
                    doInvalidate();
                } else {
                    this._doInvalidate = true;
                }
            }
        }
    }

    @Override // javax.servlet.http.HttpSession
    public void invalidate() throws IllegalStateException {
        this._manager.removeSession(this, true);
        doInvalidate();
    }

    public void doInvalidate() throws IllegalStateException {
        try {
            LOG.debug("invalidate {}", this._clusterId);
            if (isValid()) {
                clearAttributes();
            }
            synchronized (this) {
                try {
                    this._invalid = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            synchronized (this) {
                try {
                    this._invalid = true;
                    throw th2;
                } catch (Throwable th3) {
                    throw th3;
                }
            }
        }
    }

    public void clearAttributes() {
        ArrayList arrayList;
        Object doPutOrRemove;
        while (true) {
            Map<String, Object> map = this._attributes;
            if (map == null || map.size() <= 0) {
                break;
            }
            synchronized (this) {
                arrayList = new ArrayList(this._attributes.keySet());
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                synchronized (this) {
                    doPutOrRemove = doPutOrRemove(str, null);
                }
                unbindValue(str, doPutOrRemove);
                this._manager.doSessionAttributeListeners(this, str, doPutOrRemove, null);
            }
        }
        Map<String, Object> map2 = this._attributes;
        if (map2 != null) {
            map2.clear();
        }
    }

    public boolean isIdChanged() {
        return this._idChanged;
    }

    @Override // javax.servlet.http.HttpSession
    public boolean isNew() throws IllegalStateException {
        checkValid();
        return this._newSession;
    }

    @Override // javax.servlet.http.HttpSession
    @Deprecated
    public void putValue(String str, Object obj) throws IllegalStateException {
        setAttribute(str, obj);
    }

    @Override // javax.servlet.http.HttpSession
    public void removeAttribute(String str) {
        setAttribute(str, null);
    }

    @Override // javax.servlet.http.HttpSession
    @Deprecated
    public void removeValue(String str) throws IllegalStateException {
        removeAttribute(str);
    }

    protected Object doPutOrRemove(String str, Object obj) {
        return obj == null ? this._attributes.remove(str) : this._attributes.put(str, obj);
    }

    protected Object doGet(String str) {
        return this._attributes.get(str);
    }

    @Override // javax.servlet.http.HttpSession
    public void setAttribute(String str, Object obj) {
        Object doPutOrRemove;
        synchronized (this) {
            checkValid();
            doPutOrRemove = doPutOrRemove(str, obj);
        }
        if (obj == null || !obj.equals(doPutOrRemove)) {
            if (doPutOrRemove != null) {
                unbindValue(str, doPutOrRemove);
            }
            if (obj != null) {
                bindValue(str, obj);
            }
            this._manager.doSessionAttributeListeners(this, str, doPutOrRemove, obj);
        }
    }

    public void setIdChanged(boolean z) {
        this._idChanged = z;
    }

    @Override // javax.servlet.http.HttpSession
    public void setMaxInactiveInterval(int i) {
        this._maxIdleMs = i * 1000;
    }

    public String toString() {
        return getClass().getName() + Constants.COLON_SEPARATOR + getId() + "@" + hashCode();
    }

    public void bindValue(String str, Object obj) {
        if (obj != null && (obj instanceof HttpSessionBindingListener)) {
            ((HttpSessionBindingListener) obj).valueBound(new HttpSessionBindingEvent(this, str));
        }
    }

    public boolean isValid() {
        return !this._invalid;
    }

    public void cookieSet() {
        synchronized (this) {
            this._cookieSet = this._accessed;
        }
    }

    public int getRequests() {
        int i;
        synchronized (this) {
            i = this._requests;
        }
        return i;
    }

    public void setRequests(int i) {
        synchronized (this) {
            this._requests = i;
        }
    }

    public void unbindValue(String str, Object obj) {
        if (obj != null && (obj instanceof HttpSessionBindingListener)) {
            ((HttpSessionBindingListener) obj).valueUnbound(new HttpSessionBindingEvent(this, str));
        }
    }

    public void willPassivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object obj : this._attributes.values()) {
                if (obj instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) obj).sessionWillPassivate(httpSessionEvent);
                }
            }
        }
    }

    public void didActivate() {
        synchronized (this) {
            HttpSessionEvent httpSessionEvent = new HttpSessionEvent(this);
            for (Object obj : this._attributes.values()) {
                if (obj instanceof HttpSessionActivationListener) {
                    ((HttpSessionActivationListener) obj).sessionDidActivate(httpSessionEvent);
                }
            }
        }
    }
}
