package org.eclipse.jetty.server.session;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HashedSession extends AbstractSession {
    private static final Logger LOG = Log.getLogger(HashedSession.class);
    private final HashSessionManager _hashSessionManager;
    private transient boolean _idled = false;
    private transient boolean _saveFailed = false;

    /* JADX INFO: Access modifiers changed from: protected */
    public HashedSession(HashSessionManager hashSessionManager, HttpServletRequest httpServletRequest) {
        super(hashSessionManager, httpServletRequest);
        this._hashSessionManager = hashSessionManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public HashedSession(HashSessionManager hashSessionManager, long j, long j2, String str) {
        super(hashSessionManager, j, j2, str);
        this._hashSessionManager = hashSessionManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.session.AbstractSession
    public void checkValid() {
        if (this._hashSessionManager._idleSavePeriodMs != 0) {
            deIdle();
        }
        super.checkValid();
    }

    @Override // org.eclipse.jetty.server.session.AbstractSession, javax.servlet.http.HttpSession
    public void setMaxInactiveInterval(int i) {
        super.setMaxInactiveInterval(i);
        if (getMaxInactiveInterval() > 0 && (getMaxInactiveInterval() * 1000) / 10 < this._hashSessionManager._scavengePeriodMs) {
            this._hashSessionManager.setScavengePeriod((i + 9) / 10);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.session.AbstractSession
    public void doInvalidate() throws IllegalStateException {
        super.doInvalidate();
        if (this._hashSessionManager._storeDir != null && getId() != null) {
            new File(this._hashSessionManager._storeDir, getId()).delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void save(boolean z) {
        Exception e;
        File file;
        FileOutputStream fileOutputStream;
        if (!isIdled() && !this._saveFailed) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Saving {} {}", super.getId(), Boolean.valueOf(z));
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                file = new File(this._hashSessionManager._storeDir, super.getId());
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                file = null;
            }
            try {
                willPassivate();
                save(fileOutputStream);
                if (z) {
                    didActivate();
                } else {
                    clearAttributes();
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream2 = fileOutputStream;
                saveFailed();
                LOG.warn("Problem saving session " + super.getId(), e);
                if (fileOutputStream2 != null) {
                    IO.close(fileOutputStream2);
                    file.delete();
                    this._idled = false;
                }
            }
        }
    }

    public synchronized void save(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(getClusterId());
        dataOutputStream.writeUTF(getNodeId());
        dataOutputStream.writeLong(getCreationTime());
        dataOutputStream.writeLong(getAccessed());
        dataOutputStream.writeInt(getRequests());
        dataOutputStream.writeInt(getAttributes());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
        Enumeration<String> attributeNames = getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            objectOutputStream.writeUTF(nextElement);
            objectOutputStream.writeObject(doGet(nextElement));
        }
        objectOutputStream.close();
    }

    public synchronized void deIdle() {
        Exception e;
        if (isIdled()) {
            access(System.currentTimeMillis());
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Deidling " + super.getId(), new Object[0]);
            }
            FileInputStream fileInputStream = null;
            try {
                File file = new File(this._hashSessionManager._storeDir, super.getId());
                if (!file.exists() || !file.canRead()) {
                    throw new FileNotFoundException(file.getName());
                }
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    this._idled = false;
                    this._hashSessionManager.restoreSession(fileInputStream2, this);
                    didActivate();
                    if (this._hashSessionManager._savePeriodMs == 0) {
                        file.delete();
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    Logger logger2 = LOG;
                    logger2.warn("Problem deidling session " + super.getId(), e);
                    IO.close(fileInputStream);
                    invalidate();
                }
            } catch (Exception e3) {
                e = e3;
            }
        }
    }

    public synchronized void idle() {
        save(false);
    }

    public synchronized boolean isIdled() {
        return this._idled;
    }

    public synchronized boolean isSaveFailed() {
        return this._saveFailed;
    }

    public synchronized void saveFailed() {
        this._saveFailed = true;
    }
}
