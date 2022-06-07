package org.eclipse.jetty.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public class SslConnection extends AbstractConnection implements AsyncConnection {
    private static final NIOBuffer __ZERO_BUFFER = new IndirectNIOBuffer(0);
    private static final ThreadLocal<SslBuffers> __buffers = new ThreadLocal<>();
    private AsyncEndPoint _aEndp;
    private int _allocations;
    private boolean _allowRenegotiate;
    private SslBuffers _buffers;
    private AsyncConnection _connection;
    private final SSLEngine _engine;
    private boolean _handshook;
    private NIOBuffer _inbound;
    private boolean _ishut;
    private final Logger _logger;
    private boolean _oshut;
    private NIOBuffer _outbound;
    private final AtomicBoolean _progressed;
    private final SSLSession _session;
    private final SslEndPoint _sslEndPoint;
    private NIOBuffer _unwrapBuf;

    @Override // org.eclipse.jetty.io.Connection
    public boolean isIdle() {
        return false;
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return false;
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class SslBuffers {
        final NIOBuffer _in;
        final NIOBuffer _out;
        final NIOBuffer _unwrap;

        SslBuffers(int i, int i2) {
            this._in = new IndirectNIOBuffer(i);
            this._out = new IndirectNIOBuffer(i);
            this._unwrap = new IndirectNIOBuffer(i2);
        }
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint) {
        this(sSLEngine, endPoint, System.currentTimeMillis());
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint, long j) {
        super(endPoint, j);
        this._logger = Log.getLogger("org.eclipse.jetty.io.nio.ssl");
        this._allowRenegotiate = true;
        this._progressed = new AtomicBoolean();
        this._engine = sSLEngine;
        this._session = this._engine.getSession();
        this._aEndp = (AsyncEndPoint) endPoint;
        this._sslEndPoint = newSslEndPoint();
    }

    protected SslEndPoint newSslEndPoint() {
        return new SslEndPoint();
    }

    public boolean isAllowRenegotiate() {
        return this._allowRenegotiate;
    }

    public void setAllowRenegotiate(boolean z) {
        this._allowRenegotiate = z;
    }

    private void allocateBuffers() {
        synchronized (this) {
            int i = this._allocations;
            this._allocations = i + 1;
            if (i == 0 && this._buffers == null) {
                this._buffers = __buffers.get();
                if (this._buffers == null) {
                    this._buffers = new SslBuffers(this._session.getPacketBufferSize() * 2, this._session.getApplicationBufferSize() * 2);
                }
                this._inbound = this._buffers._in;
                this._outbound = this._buffers._out;
                this._unwrapBuf = this._buffers._unwrap;
                __buffers.set(null);
            }
        }
    }

    private void releaseBuffers() {
        synchronized (this) {
            int i = this._allocations - 1;
            this._allocations = i;
            if (i == 0 && this._buffers != null && this._inbound.length() == 0 && this._outbound.length() == 0 && this._unwrapBuf.length() == 0) {
                this._inbound = null;
                this._outbound = null;
                this._unwrapBuf = null;
                __buffers.set(this._buffers);
                this._buffers = null;
            }
        }
    }

    @Override // org.eclipse.jetty.io.Connection
    public Connection handle() throws IOException {
        try {
            allocateBuffers();
            boolean z = true;
            while (z) {
                z = this._engine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING ? process(null, null) : false;
                AsyncConnection asyncConnection = (AsyncConnection) this._connection.handle();
                if (!(asyncConnection == this._connection || asyncConnection == null)) {
                    this._connection = asyncConnection;
                    z = true;
                }
                this._logger.debug("{} handle {} progress={}", this._session, this, Boolean.valueOf(z));
            }
            return this;
        } finally {
            releaseBuffers();
            if (!this._ishut && this._sslEndPoint.isInputShutdown() && this._sslEndPoint.isOpen()) {
                this._ishut = true;
                try {
                    this._connection.onInputShutdown();
                } catch (Throwable th) {
                    this._logger.warn("onInputShutdown failed", th);
                    try {
                        this._sslEndPoint.close();
                    } catch (IOException e) {
                        this._logger.ignore(e);
                    }
                }
            }
        }
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onClose() {
        Connection connection = this._sslEndPoint.getConnection();
        if (connection != null && connection != this) {
            connection.onClose();
        }
    }

    @Override // org.eclipse.jetty.io.AbstractConnection, org.eclipse.jetty.io.Connection
    public void onIdleExpired(long j) {
        try {
            this._logger.debug("onIdleExpired {}ms on {}", Long.valueOf(j), this);
            if (this._endp.isOutputShutdown()) {
                this._sslEndPoint.close();
            } else {
                this._sslEndPoint.shutdownOutput();
            }
        } catch (IOException e) {
            this._logger.warn(e);
            super.onIdleExpired(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public synchronized boolean process(Buffer buffer, Buffer buffer2) throws IOException {
        boolean z;
        Throwable th;
        int i;
        Throwable th2;
        IOException e;
        int i2;
        Buffer buffer3 = buffer;
        Buffer buffer4 = buffer2;
        synchronized (this) {
            try {
                allocateBuffers();
                if (buffer3 == null) {
                    this._unwrapBuf.compact();
                    buffer3 = this._unwrapBuf;
                } else if (buffer.capacity() < this._session.getApplicationBufferSize()) {
                    boolean process = process(null, buffer4);
                    if (this._unwrapBuf == null || !this._unwrapBuf.hasContent()) {
                        releaseBuffers();
                        return process;
                    }
                    this._unwrapBuf.skip(buffer3.put(this._unwrapBuf));
                    releaseBuffers();
                    return true;
                } else if (this._unwrapBuf != null && this._unwrapBuf.hasContent()) {
                    this._unwrapBuf.skip(buffer3.put(this._unwrapBuf));
                    releaseBuffers();
                    return true;
                }
                if (buffer4 == null) {
                    buffer4 = __ZERO_BUFFER;
                }
                z = false;
                boolean z2 = true;
                while (z2) {
                    try {
                        if (this._inbound.space() > 0) {
                            i = this._endp.fill(this._inbound);
                            z2 = i > 0;
                        } else {
                            i = 0;
                            z2 = false;
                        }
                        try {
                            try {
                                if (this._outbound.hasContent()) {
                                    i2 = this._endp.flush(this._outbound);
                                    if (i2 > 0) {
                                        z2 = true;
                                    }
                                } else {
                                    i2 = 0;
                                }
                                try {
                                    this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(i), Integer.valueOf(this._inbound.length()), Integer.valueOf(i2), Integer.valueOf(this._outbound.length()));
                                    switch (AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[this._engine.getHandshakeStatus().ordinal()]) {
                                        case 1:
                                            throw new IllegalStateException();
                                        case 2:
                                            if (buffer3.space() > 0 && this._inbound.hasContent() && unwrap(buffer3)) {
                                                z2 = true;
                                            }
                                            if (buffer4.hasContent() && this._outbound.space() > 0 && wrap(buffer4)) {
                                                z2 = true;
                                                break;
                                            }
                                            break;
                                        case 3:
                                            while (true) {
                                                Runnable delegatedTask = this._engine.getDelegatedTask();
                                                if (delegatedTask == null) {
                                                    break;
                                                } else {
                                                    delegatedTask.run();
                                                    z2 = true;
                                                }
                                            }
                                        case 4:
                                            if (!this._handshook || this._allowRenegotiate) {
                                                if (wrap(buffer4)) {
                                                    z2 = true;
                                                    break;
                                                }
                                            } else {
                                                this._endp.close();
                                            }
                                            break;
                                        case 5:
                                            if (this._handshook && !this._allowRenegotiate) {
                                                this._endp.close();
                                            } else if (this._inbound.hasContent() || i != -1) {
                                                if (unwrap(buffer3)) {
                                                    z2 = true;
                                                    break;
                                                }
                                            } else {
                                                this._endp.shutdownInput();
                                            }
                                            break;
                                    }
                                    if (this._endp.isOpen() && this._endp.isInputShutdown() && !this._inbound.hasContent()) {
                                        this._engine.closeInbound();
                                    }
                                    if (this._endp.isOpen() && this._engine.isOutboundDone() && !this._outbound.hasContent()) {
                                        this._endp.shutdownOutput();
                                    }
                                    z |= z2;
                                } catch (Throwable th3) {
                                    th = th3;
                                    releaseBuffers();
                                    if (z) {
                                        this._progressed.set(true);
                                    }
                                    throw th;
                                }
                            } catch (IOException e2) {
                                e = e2;
                                this._endp.close();
                                throw e;
                            }
                        } catch (Throwable th4) {
                            th2 = th4;
                            this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(i), Integer.valueOf(this._inbound.length()), 0, Integer.valueOf(this._outbound.length()));
                            throw th2;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        i = 0;
                    } catch (Throwable th5) {
                        th2 = th5;
                        i = 0;
                        this._logger.debug("{} {} {} filled={}/{} flushed={}/{}", this._session, this, this._engine.getHandshakeStatus(), Integer.valueOf(i), Integer.valueOf(this._inbound.length()), 0, Integer.valueOf(this._outbound.length()));
                        throw th2;
                    }
                }
                if (buffer3 == this._unwrapBuf && this._unwrapBuf.hasContent() && !this._connection.isSuspended()) {
                    this._aEndp.dispatch();
                }
                releaseBuffers();
                if (z) {
                    this._progressed.set(true);
                }
                return z;
            } catch (Throwable th6) {
                th = th6;
                z = false;
            }
        }
    }

    private synchronized boolean wrap(Buffer buffer) throws IOException {
        boolean z;
        SSLEngineResult wrap;
        ByteBuffer extractByteBuffer = extractByteBuffer(buffer);
        synchronized (extractByteBuffer) {
            this._outbound.compact();
            ByteBuffer byteBuffer = this._outbound.getByteBuffer();
            synchronized (byteBuffer) {
                z = false;
                try {
                    extractByteBuffer.position(buffer.getIndex());
                    extractByteBuffer.limit(buffer.putIndex());
                    byteBuffer.position(this._outbound.putIndex());
                    byteBuffer.limit(byteBuffer.capacity());
                    wrap = this._engine.wrap(extractByteBuffer, byteBuffer);
                    if (this._logger.isDebugEnabled()) {
                        this._logger.debug("{} wrap {} {} consumed={} produced={}", this._session, wrap.getStatus(), wrap.getHandshakeStatus(), Integer.valueOf(wrap.bytesConsumed()), Integer.valueOf(wrap.bytesProduced()));
                    }
                    buffer.skip(wrap.bytesConsumed());
                    this._outbound.setPutIndex(this._outbound.putIndex() + wrap.bytesProduced());
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    extractByteBuffer.position(0);
                    extractByteBuffer.limit(extractByteBuffer.capacity());
                } catch (SSLException e) {
                    this._logger.debug(String.valueOf(this._endp), e);
                    this._endp.close();
                    throw e;
                }
            }
        }
        switch (AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[wrap.getStatus().ordinal()]) {
            case 1:
                throw new IllegalStateException();
            case 2:
                break;
            case 3:
                if (wrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._handshook = true;
                    break;
                }
                break;
            case 4:
                this._logger.debug("wrap CLOSE {} {}", this, wrap);
                if (wrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._endp.close();
                    break;
                }
                break;
            default:
                this._logger.debug("{} wrap default {}", this._session, wrap);
                throw new IOException(wrap.toString());
        }
        if (wrap.bytesConsumed() > 0 || wrap.bytesProduced() > 0) {
            z = true;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.eclipse.jetty.io.nio.SslConnection$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[SSLEngineResult.Status.values().length];

        static {
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.BUFFER_UNDERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.OK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[SSLEngineResult.HandshakeStatus.values().length];
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    private synchronized boolean unwrap(Buffer buffer) throws IOException {
        SSLEngineResult unwrap;
        boolean z = false;
        if (!this._inbound.hasContent()) {
            return false;
        }
        ByteBuffer extractByteBuffer = extractByteBuffer(buffer);
        synchronized (extractByteBuffer) {
            ByteBuffer byteBuffer = this._inbound.getByteBuffer();
            synchronized (byteBuffer) {
                try {
                    extractByteBuffer.position(buffer.putIndex());
                    extractByteBuffer.limit(buffer.capacity());
                    byteBuffer.position(this._inbound.getIndex());
                    byteBuffer.limit(this._inbound.putIndex());
                    unwrap = this._engine.unwrap(byteBuffer, extractByteBuffer);
                    if (this._logger.isDebugEnabled()) {
                        this._logger.debug("{} unwrap {} {} consumed={} produced={}", this._session, unwrap.getStatus(), unwrap.getHandshakeStatus(), Integer.valueOf(unwrap.bytesConsumed()), Integer.valueOf(unwrap.bytesProduced()));
                    }
                    this._inbound.skip(unwrap.bytesConsumed());
                    this._inbound.compact();
                    buffer.setPutIndex(buffer.putIndex() + unwrap.bytesProduced());
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    extractByteBuffer.position(0);
                    extractByteBuffer.limit(extractByteBuffer.capacity());
                } catch (SSLException e) {
                    this._logger.debug(String.valueOf(this._endp), e);
                    this._endp.close();
                    throw e;
                }
            }
        }
        switch (AnonymousClass1.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[unwrap.getStatus().ordinal()]) {
            case 1:
                if (this._endp.isInputShutdown()) {
                    this._inbound.clear();
                    break;
                }
                break;
            case 2:
                if (this._logger.isDebugEnabled()) {
                    this._logger.debug("{} unwrap {} {}->{}", this._session, unwrap.getStatus(), this._inbound.toDetailString(), buffer.toDetailString());
                    break;
                }
                break;
            case 3:
                if (unwrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._handshook = true;
                    break;
                }
                break;
            case 4:
                this._logger.debug("unwrap CLOSE {} {}", this, unwrap);
                if (unwrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._endp.close();
                    break;
                }
                break;
            default:
                this._logger.debug("{} wrap default {}", this._session, unwrap);
                throw new IOException(unwrap.toString());
        }
        if (unwrap.bytesConsumed() > 0 || unwrap.bytesProduced() > 0) {
            z = true;
        }
        return z;
    }

    private ByteBuffer extractByteBuffer(Buffer buffer) {
        if (buffer.buffer() instanceof NIOBuffer) {
            return ((NIOBuffer) buffer.buffer()).getByteBuffer();
        }
        return ByteBuffer.wrap(buffer.array());
    }

    public AsyncEndPoint getSslEndPoint() {
        return this._sslEndPoint;
    }

    @Override // org.eclipse.jetty.io.AbstractConnection
    public String toString() {
        return String.format("%s %s", super.toString(), this._sslEndPoint);
    }

    /* loaded from: classes5.dex */
    public class SslEndPoint implements AsyncEndPoint {
        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isBlocking() {
            return false;
        }

        public SslEndPoint() {
        }

        public SSLEngine getSslEngine() {
            return SslConnection.this._engine;
        }

        public AsyncEndPoint getEndpoint() {
            return SslConnection.this._aEndp;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void shutdownOutput() throws IOException {
            synchronized (SslConnection.this) {
                SslConnection.this._logger.debug("{} ssl endp.oshut {}", SslConnection.this._session, this);
                SslConnection.this._engine.closeOutbound();
                SslConnection.this._oshut = true;
            }
            flush();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isOutputShutdown() {
            boolean z;
            synchronized (SslConnection.this) {
                if (!SslConnection.this._oshut && isOpen() && !SslConnection.this._engine.isOutboundDone()) {
                    z = false;
                }
                z = true;
            }
            return z;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void shutdownInput() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.ishut!", SslConnection.this._session);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isInputShutdown() {
            boolean z;
            synchronized (SslConnection.this) {
                z = SslConnection.this._endp.isInputShutdown() && (SslConnection.this._unwrapBuf == null || !SslConnection.this._unwrapBuf.hasContent()) && (SslConnection.this._inbound == null || !SslConnection.this._inbound.hasContent());
            }
            return z;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void close() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.close", SslConnection.this._session);
            SslConnection.this._endp.close();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int fill(Buffer buffer) throws IOException {
            int length = buffer.length();
            SslConnection.this.process(buffer, null);
            int length2 = buffer.length() - length;
            if (length2 != 0 || !isInputShutdown()) {
                return length2;
            }
            return -1;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer) throws IOException {
            int length = buffer.length();
            SslConnection.this.process(null, buffer);
            return length - buffer.length();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            if (buffer != null && buffer.hasContent()) {
                return flush(buffer);
            }
            if (buffer2 != null && buffer2.hasContent()) {
                return flush(buffer2);
            }
            if (buffer3 == null || !buffer3.hasContent()) {
                return 0;
            }
            return flush(buffer3);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean blockReadable(long j) throws IOException {
            int i;
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = j > 0 ? j + currentTimeMillis : Long.MAX_VALUE;
            while (true) {
                i = (currentTimeMillis > j2 ? 1 : (currentTimeMillis == j2 ? 0 : -1));
                if (i >= 0 || SslConnection.this.process(null, null)) {
                    break;
                }
                SslConnection.this._endp.blockReadable(j2 - currentTimeMillis);
                currentTimeMillis = System.currentTimeMillis();
            }
            return i < 0;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean blockWritable(long j) throws IOException {
            return SslConnection.this._endp.blockWritable(j);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public boolean isOpen() {
            return SslConnection.this._endp.isOpen();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public Object getTransport() {
            return SslConnection.this._endp;
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void flush() throws IOException {
            SslConnection.this.process(null, null);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void dispatch() {
            SslConnection.this._aEndp.dispatch();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void asyncDispatch() {
            SslConnection.this._aEndp.asyncDispatch();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void scheduleWrite() {
            SslConnection.this._aEndp.scheduleWrite();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void onIdleExpired(long j) {
            SslConnection.this._aEndp.onIdleExpired(j);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void setCheckForIdle(boolean z) {
            SslConnection.this._aEndp.setCheckForIdle(z);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean isCheckForIdle() {
            return SslConnection.this._aEndp.isCheckForIdle();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void scheduleTimeout(Timeout.Task task, long j) {
            SslConnection.this._aEndp.scheduleTimeout(task, j);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public void cancelTimeout(Timeout.Task task) {
            SslConnection.this._aEndp.cancelTimeout(task);
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean isWritable() {
            return SslConnection.this._aEndp.isWritable();
        }

        @Override // org.eclipse.jetty.io.AsyncEndPoint
        public boolean hasProgressed() {
            return SslConnection.this._progressed.getAndSet(false);
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getLocalAddr() {
            return SslConnection.this._aEndp.getLocalAddr();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getLocalHost() {
            return SslConnection.this._aEndp.getLocalHost();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getLocalPort() {
            return SslConnection.this._aEndp.getLocalPort();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getRemoteAddr() {
            return SslConnection.this._aEndp.getRemoteAddr();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public String getRemoteHost() {
            return SslConnection.this._aEndp.getRemoteHost();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getRemotePort() {
            return SslConnection.this._aEndp.getRemotePort();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public int getMaxIdleTime() {
            return SslConnection.this._aEndp.getMaxIdleTime();
        }

        @Override // org.eclipse.jetty.io.EndPoint
        public void setMaxIdleTime(int i) throws IOException {
            SslConnection.this._aEndp.setMaxIdleTime(i);
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public Connection getConnection() {
            return SslConnection.this._connection;
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public void setConnection(Connection connection) {
            SslConnection.this._connection = (AsyncConnection) connection;
        }

        public String toString() {
            NIOBuffer nIOBuffer = SslConnection.this._inbound;
            NIOBuffer nIOBuffer2 = SslConnection.this._outbound;
            NIOBuffer nIOBuffer3 = SslConnection.this._unwrapBuf;
            int i = -1;
            int length = nIOBuffer == null ? -1 : nIOBuffer.length();
            int length2 = nIOBuffer2 == null ? -1 : nIOBuffer2.length();
            if (nIOBuffer3 != null) {
                i = nIOBuffer3.length();
            }
            return String.format("SSL %s i/o/u=%d/%d/%d ishut=%b oshut=%b {%s}", SslConnection.this._engine.getHandshakeStatus(), Integer.valueOf(length), Integer.valueOf(length2), Integer.valueOf(i), Boolean.valueOf(SslConnection.this._ishut), Boolean.valueOf(SslConnection.this._oshut), SslConnection.this._connection);
        }
    }
}
