package org.eclipse.jetty.io.nio;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.nio.SelectorManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public class SelectChannelEndPoint extends ChannelEndPoint implements AsyncEndPoint, ConnectedEndPoint {
    public static final Logger LOG = Log.getLogger("org.eclipse.jetty.io.nio");
    private boolean _asyncDispatch;
    private volatile AsyncConnection _connection;
    private boolean _dispatched;
    private volatile long _idleTimestamp;
    private int _interestOps;
    private boolean _ishut;
    private SelectionKey _key;
    private final SelectorManager _manager;
    private boolean _readBlocked;
    private final SelectorManager.SelectSet _selectSet;
    private boolean _writeBlocked;
    private final boolean WORK_AROUND_JVM_BUG_6346658 = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win");
    private final Runnable _handler = new Runnable() { // from class: org.eclipse.jetty.io.nio.SelectChannelEndPoint.1
        @Override // java.lang.Runnable
        public void run() {
            SelectChannelEndPoint.this.handle();
        }
    };
    private volatile boolean _writable = true;
    private boolean _open = true;

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public boolean hasProgressed() {
        return false;
    }

    public SelectChannelEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey, int i) throws IOException {
        super(socketChannel, i);
        this._dispatched = false;
        this._asyncDispatch = false;
        this._manager = selectSet.getManager();
        this._selectSet = selectSet;
        this._dispatched = false;
        this._asyncDispatch = false;
        this._key = selectionKey;
        setCheckForIdle(true);
    }

    public SelectionKey getSelectionKey() {
        SelectionKey selectionKey;
        synchronized (this) {
            selectionKey = this._key;
        }
        return selectionKey;
    }

    public SelectorManager getSelectManager() {
        return this._manager;
    }

    @Override // org.eclipse.jetty.io.ConnectedEndPoint
    public Connection getConnection() {
        return this._connection;
    }

    @Override // org.eclipse.jetty.io.ConnectedEndPoint
    public void setConnection(Connection connection) {
        AsyncConnection asyncConnection = this._connection;
        this._connection = (AsyncConnection) connection;
        if (asyncConnection != null && asyncConnection != this._connection) {
            this._manager.endPointUpgraded(this, asyncConnection);
        }
    }

    public long getIdleTimestamp() {
        return this._idleTimestamp;
    }

    public void schedule() {
        synchronized (this) {
            if (this._key != null && this._key.isValid()) {
                if (!this._readBlocked && !this._writeBlocked) {
                    if ((this._key.readyOps() & 4) == 4 && (this._key.interestOps() & 4) == 4) {
                        this._interestOps = this._key.interestOps() & (-5);
                        this._key.interestOps(this._interestOps);
                        this._writable = true;
                    }
                    if (this._dispatched) {
                        this._key.interestOps(0);
                    } else {
                        dispatch();
                        if (this._dispatched && !this._selectSet.getManager().isDeferringInterestedOps0()) {
                            this._key.interestOps(0);
                        }
                    }
                    return;
                }
                if (this._readBlocked && this._key.isReadable()) {
                    this._readBlocked = false;
                }
                if (this._writeBlocked && this._key.isWritable()) {
                    this._writeBlocked = false;
                }
                notifyAll();
                this._key.interestOps(0);
                if (!this._dispatched) {
                    updateKey();
                }
                return;
            }
            this._readBlocked = false;
            this._writeBlocked = false;
            notifyAll();
        }
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void asyncDispatch() {
        synchronized (this) {
            if (this._dispatched) {
                this._asyncDispatch = true;
            } else {
                dispatch();
            }
        }
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void dispatch() {
        synchronized (this) {
            if (!this._dispatched) {
                this._dispatched = true;
                if (!this._manager.dispatch(this._handler)) {
                    this._dispatched = false;
                    Logger logger = LOG;
                    logger.warn("Dispatched Failed! " + this + " to " + this._manager, new Object[0]);
                    updateKey();
                }
            }
        }
    }

    protected boolean undispatch() {
        synchronized (this) {
            if (this._asyncDispatch) {
                this._asyncDispatch = false;
                return false;
            }
            this._dispatched = false;
            updateKey();
            return true;
        }
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void cancelTimeout(Timeout.Task task) {
        getSelectSet().cancelTimeout(task);
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void scheduleTimeout(Timeout.Task task, long j) {
        getSelectSet().scheduleTimeout(task, j);
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void setCheckForIdle(boolean z) {
        this._idleTimestamp = z ? System.currentTimeMillis() : 0L;
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public boolean isCheckForIdle() {
        return this._idleTimestamp != 0;
    }

    protected void notIdle() {
        if (this._idleTimestamp != 0) {
            this._idleTimestamp = System.currentTimeMillis();
        }
    }

    public void checkIdleTimestamp(long j) {
        long j2 = this._idleTimestamp;
        if (j2 != 0 && this._maxIdleTime > 0) {
            final long j3 = j - j2;
            if (j3 > this._maxIdleTime) {
                setCheckForIdle(false);
                this._manager.dispatch(new Runnable() { // from class: org.eclipse.jetty.io.nio.SelectChannelEndPoint.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            SelectChannelEndPoint.this.onIdleExpired(j3);
                        } finally {
                            SelectChannelEndPoint.this.setCheckForIdle(true);
                        }
                    }
                });
            }
        }
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void onIdleExpired(long j) {
        this._connection.onIdleExpired(j);
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public int fill(Buffer buffer) throws IOException {
        int fill = super.fill(buffer);
        if (fill > 0) {
            notIdle();
        }
        return fill;
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        int flush = super.flush(buffer, buffer2, buffer3);
        if (flush == 0 && ((buffer != null && buffer.hasContent()) || ((buffer2 != null && buffer2.hasContent()) || (buffer3 != null && buffer3.hasContent())))) {
            synchronized (this) {
                this._writable = false;
                if (!this._dispatched) {
                    updateKey();
                }
            }
        } else if (flush > 0) {
            this._writable = true;
            notIdle();
        }
        return flush;
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        int flush = super.flush(buffer);
        if (flush == 0 && buffer != null && buffer.hasContent()) {
            synchronized (this) {
                this._writable = false;
                if (!this._dispatched) {
                    updateKey();
                }
            }
        } else if (flush > 0) {
            this._writable = true;
            notIdle();
        }
        return flush;
    }

    /* JADX WARN: Finally extract failed */
    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public boolean blockReadable(long j) throws IOException {
        SelectorManager.SelectSet selectSet;
        synchronized (this) {
            if (!isInputShutdown()) {
                long now = this._selectSet.getNow();
                long j2 = now + j;
                boolean isCheckForIdle = isCheckForIdle();
                setCheckForIdle(true);
                this._readBlocked = true;
                while (!isInputShutdown() && this._readBlocked) {
                    try {
                        try {
                            updateKey();
                            wait(j > 0 ? j2 - now : 10000L);
                            selectSet = this._selectSet;
                        } catch (InterruptedException e) {
                            LOG.warn(e);
                            selectSet = this._selectSet;
                        }
                        now = selectSet.getNow();
                        if (this._readBlocked && j > 0 && now >= j2) {
                            this._readBlocked = false;
                            setCheckForIdle(isCheckForIdle);
                            return false;
                        }
                    } catch (Throwable th) {
                        this._selectSet.getNow();
                        throw th;
                    }
                }
                this._readBlocked = false;
                setCheckForIdle(isCheckForIdle);
                return true;
            }
            throw new EofException();
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public boolean blockWritable(long j) throws IOException {
        SelectorManager.SelectSet selectSet;
        synchronized (this) {
            if (!isOutputShutdown()) {
                long now = this._selectSet.getNow();
                long j2 = now + j;
                boolean isCheckForIdle = isCheckForIdle();
                setCheckForIdle(true);
                this._writeBlocked = true;
                while (this._writeBlocked && !isOutputShutdown()) {
                    try {
                        try {
                            updateKey();
                            wait(j > 0 ? j2 - now : 10000L);
                            selectSet = this._selectSet;
                        } catch (InterruptedException e) {
                            LOG.warn(e);
                            selectSet = this._selectSet;
                        }
                        now = selectSet.getNow();
                        if (this._writeBlocked && j > 0 && now >= j2) {
                            this._writeBlocked = false;
                            setCheckForIdle(isCheckForIdle);
                            return false;
                        }
                    } catch (Throwable th) {
                        this._selectSet.getNow();
                        throw th;
                    }
                }
                this._writeBlocked = false;
                setCheckForIdle(isCheckForIdle);
                return true;
            }
            throw new EofException();
        }
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public void scheduleWrite() {
        if (this._writable) {
            LOG.debug("Required scheduleWrite {}", this);
        }
        this._writable = false;
        updateKey();
    }

    @Override // org.eclipse.jetty.io.AsyncEndPoint
    public boolean isWritable() {
        return this._writable;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x003a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0047 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0053 A[Catch: Exception -> 0x0063, all -> 0x0080, TryCatch #1 {Exception -> 0x0063, blocks: (B:34:0x004f, B:36:0x0053, B:38:0x005b), top: B:56:0x004f, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateKey() {
        /*
            r6 = this;
            monitor-enter(r6)
            java.nio.channels.ByteChannel r0 = r6.getChannel()     // Catch: all -> 0x0080
            boolean r0 = r0.isOpen()     // Catch: all -> 0x0080
            r1 = -1
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x006c
            boolean r0 = r6._readBlocked     // Catch: all -> 0x0080
            if (r0 != 0) goto L_0x0021
            boolean r0 = r6._dispatched     // Catch: all -> 0x0080
            if (r0 != 0) goto L_0x001f
            org.eclipse.jetty.io.nio.AsyncConnection r0 = r6._connection     // Catch: all -> 0x0080
            boolean r0 = r0.isSuspended()     // Catch: all -> 0x0080
            if (r0 != 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            r0 = r3
            goto L_0x0022
        L_0x0021:
            r0 = r2
        L_0x0022:
            boolean r4 = r6._writeBlocked     // Catch: all -> 0x0080
            if (r4 != 0) goto L_0x0031
            boolean r4 = r6._dispatched     // Catch: all -> 0x0080
            if (r4 != 0) goto L_0x002f
            boolean r4 = r6._writable     // Catch: all -> 0x0080
            if (r4 != 0) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r4 = r3
            goto L_0x0032
        L_0x0031:
            r4 = r2
        L_0x0032:
            java.net.Socket r5 = r6._socket     // Catch: all -> 0x0080
            boolean r5 = r5.isInputShutdown()     // Catch: all -> 0x0080
            if (r5 != 0) goto L_0x003e
            if (r0 == 0) goto L_0x003e
            r0 = r2
            goto L_0x003f
        L_0x003e:
            r0 = r3
        L_0x003f:
            java.net.Socket r5 = r6._socket     // Catch: all -> 0x0080
            boolean r5 = r5.isOutputShutdown()     // Catch: all -> 0x0080
            if (r5 != 0) goto L_0x004b
            if (r4 == 0) goto L_0x004b
            r4 = 4
            goto L_0x004c
        L_0x004b:
            r4 = r3
        L_0x004c:
            r0 = r0 | r4
            r6._interestOps = r0     // Catch: all -> 0x0080
            java.nio.channels.SelectionKey r0 = r6._key     // Catch: Exception -> 0x0063, all -> 0x0080
            if (r0 == 0) goto L_0x006c
            java.nio.channels.SelectionKey r0 = r6._key     // Catch: Exception -> 0x0063, all -> 0x0080
            boolean r0 = r0.isValid()     // Catch: Exception -> 0x0063, all -> 0x0080
            if (r0 == 0) goto L_0x006c
            java.nio.channels.SelectionKey r0 = r6._key     // Catch: Exception -> 0x0063, all -> 0x0080
            int r0 = r0.interestOps()     // Catch: Exception -> 0x0063, all -> 0x0080
            r1 = r0
            goto L_0x006c
        L_0x0063:
            r0 = move-exception
            r4 = 0
            r6._key = r4     // Catch: all -> 0x0080
            org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.io.nio.SelectChannelEndPoint.LOG     // Catch: all -> 0x0080
            r4.ignore(r0)     // Catch: all -> 0x0080
        L_0x006c:
            int r0 = r6._interestOps     // Catch: all -> 0x0080
            if (r0 == r1) goto L_0x0071
            goto L_0x0072
        L_0x0071:
            r2 = r3
        L_0x0072:
            monitor-exit(r6)     // Catch: all -> 0x0080
            if (r2 == 0) goto L_0x007f
            org.eclipse.jetty.io.nio.SelectorManager$SelectSet r0 = r6._selectSet
            r0.addChange(r6)
            org.eclipse.jetty.io.nio.SelectorManager$SelectSet r0 = r6._selectSet
            r0.wakeup()
        L_0x007f:
            return
        L_0x0080:
            r0 = move-exception
            monitor-exit(r6)     // Catch: all -> 0x0080
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.io.nio.SelectChannelEndPoint.updateKey():void");
    }

    public void doUpdateKey() {
        synchronized (this) {
            if (!getChannel().isOpen()) {
                if (this._key != null && this._key.isValid()) {
                    this._key.cancel();
                }
                if (this._open) {
                    this._open = false;
                    this._selectSet.destroyEndPoint(this);
                }
                this._key = null;
            } else if (this._interestOps > 0) {
                if (this._key != null && this._key.isValid()) {
                    this._key.interestOps(this._interestOps);
                }
                if (((SelectableChannel) getChannel()).isRegistered()) {
                    updateKey();
                } else {
                    try {
                        this._key = ((SelectableChannel) getChannel()).register(this._selectSet.getSelector(), this._interestOps, this);
                    } catch (Exception e) {
                        LOG.ignore(e);
                        if (this._key != null && this._key.isValid()) {
                            this._key.cancel();
                        }
                        if (this._open) {
                            this._selectSet.destroyEndPoint(this);
                        }
                        this._open = false;
                        this._key = null;
                    }
                }
            } else if (this._key == null || !this._key.isValid()) {
                this._key = null;
            } else {
                this._key.interestOps(0);
            }
        }
    }

    protected void handle() {
        boolean z = true;
        while (z) {
            while (true) {
                try {
                    try {
                        try {
                            AsyncConnection asyncConnection = (AsyncConnection) this._connection.handle();
                            if (asyncConnection != this._connection) {
                                LOG.debug("{} replaced {}", asyncConnection, this._connection);
                                AsyncConnection asyncConnection2 = this._connection;
                                this._connection = asyncConnection;
                                this._manager.endPointUpgraded(this, asyncConnection2);
                            } else {
                                try {
                                    break;
                                } catch (Throwable th) {
                                    if (z) {
                                        boolean z2 = !undispatch();
                                        while (z2) {
                                            LOG.warn("SCEP.run() finally DISPATCHED", new Object[0]);
                                            z2 = !undispatch();
                                        }
                                    }
                                    throw th;
                                }
                            }
                        } catch (ClosedChannelException e) {
                            LOG.ignore(e);
                            if (!this._ishut && isInputShutdown() && isOpen()) {
                                this._ishut = true;
                                this._connection.onInputShutdown();
                                updateKey();
                            }
                        }
                    } catch (EofException e2) {
                        LOG.debug("EOF", e2);
                        try {
                            close();
                        } catch (IOException e3) {
                            LOG.ignore(e3);
                        }
                        if (!this._ishut && isInputShutdown() && isOpen()) {
                            this._ishut = true;
                            this._connection.onInputShutdown();
                            updateKey();
                        }
                    }
                } catch (IOException e4) {
                    LOG.warn(e4.toString(), new Object[0]);
                    try {
                        close();
                    } catch (IOException e5) {
                        LOG.ignore(e5);
                    }
                    if (!this._ishut && isInputShutdown() && isOpen()) {
                        this._ishut = true;
                        try {
                            this._connection.onInputShutdown();
                        } catch (Throwable th2) {
                            LOG.warn("onInputShutdown failed", th2);
                            try {
                                close();
                            } catch (IOException e6) {
                                LOG.ignore(e6);
                            }
                        }
                        updateKey();
                    }
                } catch (Throwable th3) {
                    LOG.warn("handle failed", th3);
                    try {
                        close();
                    } catch (IOException e7) {
                        LOG.ignore(e7);
                    }
                    if (!this._ishut && isInputShutdown() && isOpen()) {
                        this._ishut = true;
                        try {
                            this._connection.onInputShutdown();
                        } catch (Throwable th4) {
                            LOG.warn("onInputShutdown failed", th4);
                            try {
                                close();
                            } catch (IOException e8) {
                                LOG.ignore(e8);
                            }
                        }
                        updateKey();
                    }
                }
            }
            if (!this._ishut && isInputShutdown() && isOpen()) {
                this._ishut = true;
                try {
                    this._connection.onInputShutdown();
                } catch (Throwable th5) {
                    LOG.warn("onInputShutdown failed", th5);
                    try {
                        close();
                    } catch (IOException e9) {
                        LOG.ignore(e9);
                    }
                }
                updateKey();
            }
            z = !undispatch();
        }
        if (z) {
            boolean z3 = !undispatch();
            while (z3) {
                LOG.warn("SCEP.run() finally DISPATCHED", new Object[0]);
                z3 = !undispatch();
            }
        }
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public void close() throws IOException {
        if (this.WORK_AROUND_JVM_BUG_6346658) {
            try {
                SelectionKey selectionKey = this._key;
                if (selectionKey != null) {
                    selectionKey.cancel();
                }
            } catch (Throwable th) {
                LOG.ignore(th);
            }
        }
        try {
            try {
                super.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        } finally {
            updateKey();
        }
    }

    public String toString() {
        SelectionKey selectionKey = this._key;
        String str = "";
        if (selectionKey == null) {
            str = str + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        } else if (selectionKey.isValid()) {
            if (selectionKey.isReadable()) {
                str = str + "r";
            }
            if (selectionKey.isWritable()) {
                str = str + "w";
            }
        } else {
            str = str + "!";
        }
        return String.format("SCEP@%x{l(%s)<->r(%s),d=%b,open=%b,ishut=%b,oshut=%b,rb=%b,wb=%b,w=%b,i=%d%s}-{%s}", Integer.valueOf(hashCode()), this._socket.getRemoteSocketAddress(), this._socket.getLocalSocketAddress(), Boolean.valueOf(this._dispatched), Boolean.valueOf(isOpen()), Boolean.valueOf(isInputShutdown()), Boolean.valueOf(isOutputShutdown()), Boolean.valueOf(this._readBlocked), Boolean.valueOf(this._writeBlocked), Boolean.valueOf(this._writable), Integer.valueOf(this._interestOps), str, this._connection);
    }

    public SelectorManager.SelectSet getSelectSet() {
        return this._selectSet;
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public void setMaxIdleTime(int i) throws IOException {
        this._maxIdleTime = i;
    }
}
