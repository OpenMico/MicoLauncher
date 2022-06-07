package org.eclipse.jetty.server.session;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HashSessionManager extends AbstractSessionManager {
    private static int __id;
    static final Logger __log = SessionHandler.LOG;
    private TimerTask _saveTask;
    File _storeDir;
    private TimerTask _task;
    private Timer _timer;
    protected final ConcurrentMap<String, HashedSession> _sessions = new ConcurrentHashMap();
    private boolean _timerStop = false;
    long _scavengePeriodMs = 30000;
    long _savePeriodMs = 0;
    long _idleSavePeriodMs = 0;
    private boolean _lazyLoad = false;
    private volatile boolean _sessionsLoaded = false;
    private boolean _deleteUnrestorableSessions = false;

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        this._timerStop = false;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext != null) {
            this._timer = (Timer) currentContext.getAttribute("org.eclipse.jetty.server.session.timer");
        }
        if (this._timer == null) {
            this._timerStop = true;
            StringBuilder sb = new StringBuilder();
            sb.append("HashSessionScavenger-");
            int i = __id;
            __id = i + 1;
            sb.append(i);
            this._timer = new Timer(sb.toString(), true);
        }
        setScavengePeriod(getScavengePeriod());
        File file = this._storeDir;
        if (file != null) {
            if (!file.exists()) {
                this._storeDir.mkdirs();
            }
            if (!this._lazyLoad) {
                restoreSessions();
            }
        }
        setSavePeriod(getSavePeriod());
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        synchronized (this) {
            if (this._saveTask != null) {
                this._saveTask.cancel();
            }
            this._saveTask = null;
            if (this._task != null) {
                this._task.cancel();
            }
            this._task = null;
            if (this._timer != null && this._timerStop) {
                this._timer.cancel();
            }
            this._timer = null;
        }
        super.doStop();
        this._sessions.clear();
    }

    public int getScavengePeriod() {
        return (int) (this._scavengePeriodMs / 1000);
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public int getSessions() {
        int sessions = super.getSessions();
        if (__log.isDebugEnabled() && this._sessions.size() != sessions) {
            Logger logger = __log;
            logger.warn("sessions: " + this._sessions.size() + "!=" + sessions, new Object[0]);
        }
        return sessions;
    }

    public int getIdleSavePeriod() {
        long j = this._idleSavePeriodMs;
        if (j <= 0) {
            return 0;
        }
        return (int) (j / 1000);
    }

    public void setIdleSavePeriod(int i) {
        this._idleSavePeriodMs = i * 1000;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager, org.eclipse.jetty.server.SessionManager
    public void setMaxInactiveInterval(int i) {
        super.setMaxInactiveInterval(i);
        if (this._dftMaxIdleSecs > 0 && this._scavengePeriodMs > this._dftMaxIdleSecs * 1000) {
            setScavengePeriod((this._dftMaxIdleSecs + 9) / 10);
        }
    }

    public void setSavePeriod(int i) {
        long j = i * 1000;
        if (j < 0) {
            j = 0;
        }
        this._savePeriodMs = j;
        if (this._timer != null) {
            synchronized (this) {
                if (this._saveTask != null) {
                    this._saveTask.cancel();
                }
                if (this._savePeriodMs > 0 && this._storeDir != null) {
                    this._saveTask = new TimerTask() { // from class: org.eclipse.jetty.server.session.HashSessionManager.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            try {
                                HashSessionManager.this.saveSessions(true);
                            } catch (Exception e) {
                                HashSessionManager.__log.warn(e);
                            }
                        }
                    };
                    this._timer.schedule(this._saveTask, this._savePeriodMs, this._savePeriodMs);
                }
            }
        }
    }

    public int getSavePeriod() {
        long j = this._savePeriodMs;
        if (j <= 0) {
            return 0;
        }
        return (int) (j / 1000);
    }

    public void setScavengePeriod(int i) {
        if (i == 0) {
            i = 60;
        }
        long j = this._scavengePeriodMs;
        long j2 = i * 1000;
        if (j2 > 60000) {
            j2 = 60000;
        }
        if (j2 < 1000) {
            j2 = 1000;
        }
        this._scavengePeriodMs = j2;
        if (this._timer == null) {
            return;
        }
        if (j2 != j || this._task == null) {
            synchronized (this) {
                if (this._task != null) {
                    this._task.cancel();
                }
                this._task = new TimerTask() { // from class: org.eclipse.jetty.server.session.HashSessionManager.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        HashSessionManager.this.scavenge();
                    }
                };
                this._timer.schedule(this._task, this._scavengePeriodMs, this._scavengePeriodMs);
            }
        }
    }

    protected void scavenge() {
        if (!isStopping() && !isStopped()) {
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            try {
                if (this._loader != null) {
                    currentThread.setContextClassLoader(this._loader);
                }
                long currentTimeMillis = System.currentTimeMillis();
                for (HashedSession hashedSession : this._sessions.values()) {
                    long maxInactiveInterval = hashedSession.getMaxInactiveInterval() * 1000;
                    if (maxInactiveInterval > 0 && hashedSession.getAccessed() + maxInactiveInterval < currentTimeMillis) {
                        hashedSession.timeout();
                    } else if (this._idleSavePeriodMs > 0 && hashedSession.getAccessed() + this._idleSavePeriodMs < currentTimeMillis) {
                        hashedSession.idle();
                    }
                }
            } finally {
                currentThread.setContextClassLoader(contextClassLoader);
            }
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected void addSession(AbstractSession abstractSession) {
        if (isRunning()) {
            this._sessions.put(abstractSession.getClusterId(), (HashedSession) abstractSession);
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    public AbstractSession getSession(String str) {
        if (this._lazyLoad && !this._sessionsLoaded) {
            try {
                restoreSessions();
            } catch (Exception e) {
                __log.warn(e);
            }
        }
        ConcurrentMap<String, HashedSession> concurrentMap = this._sessions;
        if (concurrentMap == null) {
            return null;
        }
        HashedSession hashedSession = concurrentMap.get(str);
        if (hashedSession == null && this._lazyLoad) {
            hashedSession = restoreSession(str);
        }
        if (hashedSession == null) {
            return null;
        }
        if (this._idleSavePeriodMs != 0) {
            hashedSession.deIdle();
        }
        return hashedSession;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected void invalidateSessions() throws Exception {
        File file;
        ArrayList arrayList = new ArrayList(this._sessions.values());
        int i = 100;
        while (arrayList.size() > 0) {
            i--;
            if (i > 0) {
                if (!isStopping() || (file = this._storeDir) == null || !file.exists() || !this._storeDir.canWrite()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((HashedSession) it.next()).invalidate();
                    }
                } else {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        HashedSession hashedSession = (HashedSession) it2.next();
                        hashedSession.save(false);
                        removeSession((AbstractSession) hashedSession, false);
                    }
                }
                arrayList = new ArrayList(this._sessions.values());
            } else {
                return;
            }
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new HashedSession(this, httpServletRequest);
    }

    protected AbstractSession newSession(long j, long j2, String str) {
        return new HashedSession(this, j, j2, str);
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionManager
    protected boolean removeSession(String str) {
        return this._sessions.remove(str) != null;
    }

    public void setStoreDirectory(File file) {
        this._storeDir = file;
    }

    public File getStoreDirectory() {
        return this._storeDir;
    }

    public void setLazyLoad(boolean z) {
        this._lazyLoad = z;
    }

    public boolean isLazyLoad() {
        return this._lazyLoad;
    }

    public boolean isDeleteUnrestorableSessions() {
        return this._deleteUnrestorableSessions;
    }

    public void setDeleteUnrestorableSessions(boolean z) {
        this._deleteUnrestorableSessions = z;
    }

    public void restoreSessions() throws Exception {
        this._sessionsLoaded = true;
        File file = this._storeDir;
        if (file != null && file.exists()) {
            if (!this._storeDir.canRead()) {
                Logger logger = __log;
                logger.warn("Unable to restore Sessions: Cannot read from Session storage directory " + this._storeDir.getAbsolutePath(), new Object[0]);
                return;
            }
            String[] list = this._storeDir.list();
            for (int i = 0; list != null && i < list.length; i++) {
                restoreSession(list[i]);
            }
        }
    }

    protected synchronized HashedSession restoreSession(String str) {
        Exception e;
        FileInputStream fileInputStream;
        Throwable th;
        File file = new File(this._storeDir, str);
        FileInputStream fileInputStream2 = null;
        try {
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        if (file.exists()) {
            fileInputStream = new FileInputStream(file);
            try {
                HashedSession restoreSession = restoreSession(fileInputStream, null);
                addSession(restoreSession, false);
                restoreSession.didActivate();
                try {
                    fileInputStream.close();
                } catch (Exception e3) {
                    __log.ignore(e3);
                }
                file.delete();
                return restoreSession;
            } catch (Exception e4) {
                e = e4;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e5) {
                        __log.ignore(e5);
                    }
                }
                if (!isDeleteUnrestorableSessions() || !file.exists()) {
                    __log.warn("Problem restoring session " + str, e);
                } else {
                    file.delete();
                    __log.warn("Deleting file for unrestorable session " + str, e);
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e6) {
                        __log.ignore(e6);
                    }
                }
                file.delete();
                throw th;
            }
        } else {
            file.delete();
            return null;
        }
    }

    public void saveSessions(boolean z) throws Exception {
        File file = this._storeDir;
        if (file != null && file.exists()) {
            if (!this._storeDir.canWrite()) {
                Logger logger = __log;
                logger.warn("Unable to save Sessions: Session persistence storage directory " + this._storeDir.getAbsolutePath() + " is not writeable", new Object[0]);
                return;
            }
            for (HashedSession hashedSession : this._sessions.values()) {
                hashedSession.save(true);
            }
        }
    }

    public HashedSession restoreSession(InputStream inputStream, HashedSession hashedSession) throws Exception {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String readUTF = dataInputStream.readUTF();
        dataInputStream.readUTF();
        long readLong = dataInputStream.readLong();
        long readLong2 = dataInputStream.readLong();
        int readInt = dataInputStream.readInt();
        if (hashedSession == null) {
            hashedSession = (HashedSession) newSession(readLong, readLong2, readUTF);
        }
        hashedSession.setRequests(readInt);
        int readInt2 = dataInputStream.readInt();
        if (readInt2 > 0) {
            ClassLoadingObjectInputStream classLoadingObjectInputStream = new ClassLoadingObjectInputStream(dataInputStream);
            for (int i = 0; i < readInt2; i++) {
                hashedSession.setAttribute(classLoadingObjectInputStream.readUTF(), classLoadingObjectInputStream.readObject());
            }
            classLoadingObjectInputStream.close();
        } else {
            dataInputStream.close();
        }
        return hashedSession;
    }

    /* loaded from: classes5.dex */
    public class ClassLoadingObjectInputStream extends ObjectInputStream {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ClassLoadingObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
            HashSessionManager.this = r1;
        }

        public ClassLoadingObjectInputStream() throws IOException {
            HashSessionManager.this = r1;
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
}
