package org.eclipse.jetty.io;

import java.io.IOException;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public abstract class AbstractConnection implements Connection {
    private static final Logger LOG = Log.getLogger(AbstractConnection.class);
    protected final EndPoint _endp;
    private final long _timeStamp;

    public AbstractConnection(EndPoint endPoint) {
        this._endp = endPoint;
        this._timeStamp = System.currentTimeMillis();
    }

    public AbstractConnection(EndPoint endPoint, long j) {
        this._endp = endPoint;
        this._timeStamp = j;
    }

    @Override // org.eclipse.jetty.io.Connection
    public long getTimeStamp() {
        return this._timeStamp;
    }

    public EndPoint getEndPoint() {
        return this._endp;
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onIdleExpired(long j) {
        try {
            LOG.debug("onIdleExpired {}ms {} {}", Long.valueOf(j), this, this._endp);
            if (!this._endp.isInputShutdown() && !this._endp.isOutputShutdown()) {
                this._endp.shutdownOutput();
            }
            this._endp.close();
        } catch (IOException e) {
            LOG.ignore(e);
            try {
                this._endp.close();
            } catch (IOException e2) {
                LOG.ignore(e2);
            }
        }
    }

    public String toString() {
        return String.format("%s@%x", getClass().getSimpleName(), Integer.valueOf(hashCode()));
    }
}
