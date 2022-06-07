package org.eclipse.jetty.server.session;

import java.security.SecureRandom;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class AbstractSessionIdManager extends AbstractLifeCycle implements SessionIdManager {
    private static final Logger LOG = Log.getLogger(AbstractSessionIdManager.class);
    private static final String __NEW_SESSION_ID = "org.eclipse.jetty.server.newSessionId";
    protected Random _random;
    protected boolean _weakRandom;
    protected String _workerName;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
    }

    public AbstractSessionIdManager() {
    }

    public AbstractSessionIdManager(Random random) {
        this._random = random;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getWorkerName() {
        return this._workerName;
    }

    public void setWorkerName(String str) {
        if (!str.contains(".")) {
            this._workerName = str;
            return;
        }
        throw new IllegalArgumentException("Name cannot contain '.'");
    }

    public Random getRandom() {
        return this._random;
    }

    public synchronized void setRandom(Random random) {
        this._random = random;
        this._weakRandom = false;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String newSessionId(HttpServletRequest httpServletRequest, long j) {
        synchronized (this) {
            if (httpServletRequest != null) {
                try {
                    String requestedSessionId = httpServletRequest.getRequestedSessionId();
                    if (requestedSessionId != null) {
                        String clusterId = getClusterId(requestedSessionId);
                        if (idInUse(clusterId)) {
                            return clusterId;
                        }
                    }
                    String str = (String) httpServletRequest.getAttribute(__NEW_SESSION_ID);
                    if (str != null && idInUse(str)) {
                        return str;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            String str2 = null;
            while (true) {
                if (!(str2 == null || str2.length() == 0 || idInUse(str2))) {
                    httpServletRequest.setAttribute(__NEW_SESSION_ID, str2);
                    return str2;
                }
                long hashCode = this._weakRandom ? ((hashCode() ^ Runtime.getRuntime().freeMemory()) ^ this._random.nextInt()) ^ (httpServletRequest.hashCode() << 32) : this._random.nextLong();
                if (hashCode < 0) {
                    hashCode = -hashCode;
                }
                long hashCode2 = this._weakRandom ? (httpServletRequest.hashCode() << 32) ^ ((hashCode() ^ Runtime.getRuntime().freeMemory()) ^ this._random.nextInt()) : this._random.nextLong();
                if (hashCode2 < 0) {
                    hashCode2 = -hashCode2;
                }
                str2 = Long.toString(hashCode, 36) + Long.toString(hashCode2, 36);
                if (this._workerName != null) {
                    str2 = this._workerName + str2;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        initRandom();
    }

    public void initRandom() {
        Random random = this._random;
        if (random == null) {
            try {
                this._random = new SecureRandom();
            } catch (Exception e) {
                LOG.warn("Could not generate SecureRandom for session-id randomness", e);
                this._random = new Random();
                this._weakRandom = true;
            }
        } else {
            random.setSeed(((random.nextLong() ^ System.currentTimeMillis()) ^ hashCode()) ^ Runtime.getRuntime().freeMemory());
        }
    }
}
