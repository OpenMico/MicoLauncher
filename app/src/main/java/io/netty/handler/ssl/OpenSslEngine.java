package io.netty.handler.ssl;

import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import org.apache.tomcat.jni.Buffer;
import org.apache.tomcat.jni.SSL;

/* loaded from: classes4.dex */
public final class OpenSslEngine extends SSLEngine {
    private static final InternalLogger b = InternalLoggerFactory.getInstance(OpenSslEngine.class);
    private static final Certificate[] c = EmptyArrays.EMPTY_CERTIFICATES;
    private static final X509Certificate[] d = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
    private static final SSLException e = new SSLException("engine closed");
    private static final SSLException f = new SSLException("renegotiation unsupported");
    private static final SSLException g = new SSLException("encrypted packet oversized");
    private static final String[] h;
    private static final Set<String> i;
    private static final AtomicIntegerFieldUpdater<OpenSslEngine> j;
    private static final long k;
    private static final SSLEngineResult l;
    private static final SSLEngineResult m;
    private static final SSLEngineResult n;
    private static final SSLEngineResult o;
    private static final SSLEngineResult p;
    private boolean A;
    private final boolean B;
    private final ByteBufAllocator C;
    private final i D;
    private final OpenSslApplicationProtocolNegotiator E;
    private final boolean F;
    private final b G;
    private final Certificate[] H;
    private final ByteBuffer[] I;
    private final ByteBuffer[] J;
    SSLHandshakeException a;
    private long q;
    private long r;
    private a s;
    private boolean t;
    private volatile int u;
    private volatile ClientAuth v;
    private volatile String w;
    private volatile Object x;
    private boolean y;
    private boolean z;

    /* loaded from: classes4.dex */
    public enum a {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED
    }

    @Override // javax.net.ssl.SSLEngine
    public Runnable getDelegatedTask() {
        return null;
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getEnableSessionCreation() {
        return false;
    }

    static {
        e.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        f.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        g.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        AtomicIntegerFieldUpdater<OpenSslEngine> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(OpenSslEngine.class, "destroyed");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(OpenSslEngine.class, ai.aE);
        }
        j = newAtomicIntegerFieldUpdater;
        h = new String[]{"SSLv2Hello", "SSLv2", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"};
        i = new HashSet(Arrays.asList(h));
        k = Buffer.address(Unpooled.EMPTY_BUFFER.nioBuffer());
        l = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        m = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        n = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        o = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        p = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    }

    @Deprecated
    public OpenSslEngine(long j2, ByteBufAllocator byteBufAllocator, String str) {
        this(j2, byteBufAllocator, false, null, OpenSslContext.b, i.a, false, ClientAuth.NONE);
    }

    OpenSslEngine(long j2, ByteBufAllocator byteBufAllocator, boolean z, OpenSslSessionContext openSslSessionContext, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, i iVar, boolean z2, ClientAuth clientAuth) {
        this(j2, byteBufAllocator, z, openSslSessionContext, openSslApplicationProtocolNegotiator, iVar, z2, null, -1, null, clientAuth);
    }

    public OpenSslEngine(long j2, ByteBufAllocator byteBufAllocator, boolean z, OpenSslSessionContext openSslSessionContext, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, i iVar, boolean z2, String str, int i2, Certificate[] certificateArr, ClientAuth clientAuth) {
        super(str, i2);
        this.s = a.NOT_STARTED;
        this.v = ClientAuth.NONE;
        this.I = new ByteBuffer[1];
        this.J = new ByteBuffer[1];
        OpenSsl.ensureAvailability();
        if (j2 != 0) {
            this.C = (ByteBufAllocator) ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
            this.E = (OpenSslApplicationProtocolNegotiator) ObjectUtil.checkNotNull(openSslApplicationProtocolNegotiator, "apn");
            this.q = SSL.newSSL(j2, !z);
            this.G = new b(openSslSessionContext);
            this.r = SSL.makeNetworkBIO(this.q);
            this.B = z;
            this.D = iVar;
            this.F = z2;
            this.H = certificateArr;
            a(z ? ClientAuth.NONE : (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth"));
            if (z && str != null) {
                SSL.setTlsExtHostName(this.q, str);
                return;
            }
            return;
        }
        throw new NullPointerException("sslCtx");
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLSession getHandshakeSession() {
        switch (this.s) {
            case NOT_STARTED:
            case FINISHED:
                return null;
            default:
                return this.G;
        }
    }

    public synchronized long sslPointer() {
        return this.q;
    }

    public synchronized void shutdown() {
        if (j.compareAndSet(this, 0, 1)) {
            this.D.a(this.q);
            SSL.freeSSL(this.q);
            SSL.freeBIO(this.r);
            this.r = 0L;
            this.q = 0L;
            this.A = true;
            this.z = true;
            this.y = true;
        }
        SSL.clearError();
    }

    private int a(ByteBuffer byteBuffer) {
        int i2;
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        int min = Math.min(limit - position, 16384);
        if (byteBuffer.isDirect()) {
            i2 = SSL.writeToSSL(this.q, Buffer.address(byteBuffer) + position, min);
            if (i2 > 0) {
                byteBuffer.position(position + i2);
            }
        } else {
            ByteBuf directBuffer = this.C.directBuffer(min);
            try {
                long a2 = OpenSsl.a(directBuffer);
                byteBuffer.limit(position + min);
                directBuffer.setBytes(0, byteBuffer);
                byteBuffer.limit(limit);
                i2 = SSL.writeToSSL(this.q, a2, min);
                if (i2 > 0) {
                    byteBuffer.position(position + i2);
                } else {
                    byteBuffer.position(position);
                }
            } finally {
                directBuffer.release();
            }
        }
        return i2;
    }

    private int b(ByteBuffer byteBuffer) {
        int i2;
        int position = byteBuffer.position();
        int remaining = byteBuffer.remaining();
        if (byteBuffer.isDirect()) {
            i2 = SSL.writeToBIO(this.r, Buffer.address(byteBuffer) + position, remaining);
            if (i2 >= 0) {
                byteBuffer.position(position + i2);
            }
        } else {
            ByteBuf directBuffer = this.C.directBuffer(remaining);
            try {
                long a2 = OpenSsl.a(directBuffer);
                directBuffer.setBytes(0, byteBuffer);
                i2 = SSL.writeToBIO(this.r, a2, remaining);
                if (i2 >= 0) {
                    byteBuffer.position(position + i2);
                } else {
                    byteBuffer.position(position);
                }
            } finally {
                directBuffer.release();
            }
        }
        return i2;
    }

    private int c(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            int position = byteBuffer.position();
            int readFromSSL = SSL.readFromSSL(this.q, Buffer.address(byteBuffer) + position, byteBuffer.limit() - position);
            if (readFromSSL <= 0) {
                return readFromSSL;
            }
            byteBuffer.position(position + readFromSSL);
            return readFromSSL;
        }
        int position2 = byteBuffer.position();
        int limit = byteBuffer.limit();
        int min = Math.min(18713, limit - position2);
        ByteBuf directBuffer = this.C.directBuffer(min);
        try {
            int readFromSSL2 = SSL.readFromSSL(this.q, OpenSsl.a(directBuffer), min);
            if (readFromSSL2 > 0) {
                byteBuffer.limit(position2 + readFromSSL2);
                directBuffer.getBytes(0, byteBuffer);
                byteBuffer.limit(limit);
            }
            return readFromSSL2;
        } finally {
            directBuffer.release();
        }
    }

    private int a(ByteBuffer byteBuffer, int i2) {
        int i3;
        if (!byteBuffer.isDirect() || byteBuffer.remaining() < i2) {
            ByteBuf directBuffer = this.C.directBuffer(i2);
            try {
                i3 = SSL.readFromBIO(this.r, OpenSsl.a(directBuffer), i2);
                if (i3 > 0) {
                    int limit = byteBuffer.limit();
                    byteBuffer.limit(byteBuffer.position() + i3);
                    directBuffer.getBytes(0, byteBuffer);
                    byteBuffer.limit(limit);
                    return i3;
                }
            } finally {
                directBuffer.release();
            }
        } else {
            int position = byteBuffer.position();
            i3 = SSL.readFromBIO(this.r, Buffer.address(byteBuffer) + position, i2);
            if (i3 > 0) {
                byteBuffer.position(position + i3);
                return i3;
            }
        }
        return i3;
    }

    private SSLEngineResult a(ByteBuffer byteBuffer, int i2, int i3, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        int pendingWrittenBytesInBIO = SSL.pendingWrittenBytesInBIO(this.r);
        if (pendingWrittenBytesInBIO <= 0) {
            return null;
        }
        if (byteBuffer.remaining() < pendingWrittenBytesInBIO) {
            SSLEngineResult.Status status = SSLEngineResult.Status.BUFFER_OVERFLOW;
            if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                handshakeStatus = b(pendingWrittenBytesInBIO);
            }
            return new SSLEngineResult(status, a(handshakeStatus), i2, i3);
        }
        int a2 = a(byteBuffer, pendingWrittenBytesInBIO);
        if (a2 <= 0) {
            SSL.clearError();
        } else {
            i3 += a2;
            pendingWrittenBytesInBIO -= a2;
        }
        if (this.z) {
            shutdown();
        }
        SSLEngineResult.Status j2 = j();
        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
            handshakeStatus = b(pendingWrittenBytesInBIO);
        }
        return new SSLEngineResult(j2, a(handshakeStatus), i2, i3);
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i2, int i3, ByteBuffer byteBuffer) throws SSLException {
        int i4;
        SSLEngineResult a2;
        if (l()) {
            return p;
        }
        if (byteBufferArr == null) {
            throw new IllegalArgumentException("srcs is null");
        } else if (byteBuffer == null) {
            throw new IllegalArgumentException("dst is null");
        } else if (i2 >= byteBufferArr.length || (i4 = i2 + i3) > byteBufferArr.length) {
            throw new IndexOutOfBoundsException("offset: " + i2 + ", length: " + i3 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArr.length + "))");
        } else if (!byteBuffer.isReadOnly()) {
            SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (this.s != a.FINISHED) {
                if (this.s != a.STARTED_EXPLICITLY) {
                    this.s = a.STARTED_IMPLICITLY;
                }
                handshakeStatus = i();
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    return l;
                }
                if (this.A) {
                    return m;
                }
            }
            int i5 = 0;
            while (i2 < i4) {
                ByteBuffer byteBuffer2 = byteBufferArr[i2];
                if (byteBuffer2 != null) {
                    while (byteBuffer2.hasRemaining()) {
                        int a3 = a(byteBuffer2);
                        if (a3 > 0) {
                            i5 += a3;
                            SSLEngineResult a4 = a(byteBuffer, i5, 0, handshakeStatus);
                            if (a4 != null) {
                                return a4;
                            }
                        } else {
                            int error = SSL.getError(this.q, a3);
                            if (error != 6) {
                                switch (error) {
                                    case 2:
                                        SSLEngineResult a5 = a(byteBuffer, i5, 0, handshakeStatus);
                                        if (a5 == null) {
                                            a5 = new SSLEngineResult(j(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, i5, 0);
                                        }
                                        return a5;
                                    case 3:
                                        SSLEngineResult a6 = a(byteBuffer, i5, 0, handshakeStatus);
                                        if (a6 == null) {
                                            a6 = o;
                                        }
                                        return a6;
                                    default:
                                        throw a("SSL_write");
                                }
                            } else {
                                if (!this.t) {
                                    d();
                                }
                                SSLEngineResult a7 = a(byteBuffer, i5, 0, handshakeStatus);
                                if (a7 == null) {
                                    a7 = p;
                                }
                                return a7;
                            }
                        }
                    }
                    i2++;
                } else {
                    throw new IllegalArgumentException("srcs[" + i2 + "] is null");
                }
            }
            return (i5 != 0 || (a2 = a(byteBuffer, 0, 0, handshakeStatus)) == null) ? a(i5, 0, handshakeStatus) : a2;
        } else {
            throw new ReadOnlyBufferException();
        }
    }

    private SSLException a(String str) {
        return a(str, SSL.getLastError());
    }

    private SSLException a(String str, String str2) {
        if (b.isDebugEnabled()) {
            b.debug("{} failed: OpenSSL error: {}", str, str2);
        }
        shutdown();
        if (this.s == a.FINISHED) {
            return new SSLException(str2);
        }
        return new SSLHandshakeException(str2);
    }

    public synchronized SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, int i2, int i3, ByteBuffer[] byteBufferArr2, int i4, int i5) throws SSLException {
        int i6;
        int i7;
        long j2;
        int i8;
        int i9 = i2;
        int i10 = i4;
        synchronized (this) {
            if (l()) {
                return p;
            }
            if (byteBufferArr == null) {
                throw new NullPointerException("srcs");
            } else if (i9 >= byteBufferArr.length || (i6 = i9 + i3) > byteBufferArr.length) {
                throw new IndexOutOfBoundsException("offset: " + i9 + ", length: " + i3 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArr.length + "))");
            } else if (byteBufferArr2 == null) {
                throw new IllegalArgumentException("dsts is null");
            } else if (i10 >= byteBufferArr2.length || (i7 = i10 + i5) > byteBufferArr2.length) {
                throw new IndexOutOfBoundsException("offset: " + i10 + ", length: " + i5 + " (expected: offset <= offset + length <= dsts.length (" + byteBufferArr2.length + "))");
            } else {
                long j3 = 0;
                for (int i11 = i10; i11 < i7; i11++) {
                    ByteBuffer byteBuffer = byteBufferArr2[i11];
                    if (byteBuffer == null) {
                        throw new IllegalArgumentException("dsts[" + i11 + "] is null");
                    } else if (!byteBuffer.isReadOnly()) {
                        j3 += byteBuffer.remaining();
                    } else {
                        throw new ReadOnlyBufferException();
                    }
                }
                SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (this.s != a.FINISHED) {
                    if (this.s != a.STARTED_EXPLICITLY) {
                        this.s = a.STARTED_IMPLICITLY;
                    }
                    handshakeStatus = i();
                    if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                        return n;
                    }
                    if (this.A) {
                        return o;
                    }
                }
                long j4 = 0;
                for (int i12 = i9; i12 < i6; i12++) {
                    ByteBuffer byteBuffer2 = byteBufferArr[i12];
                    if (byteBuffer2 != null) {
                        j4 += byteBuffer2.remaining();
                    } else {
                        throw new IllegalArgumentException("srcs[" + i12 + "] is null");
                    }
                }
                if (j4 <= 18713) {
                    int i13 = 0;
                    if (i9 < i6) {
                        i8 = 0;
                        while (true) {
                            ByteBuffer byteBuffer3 = byteBufferArr[i9];
                            int remaining = byteBuffer3.remaining();
                            if (remaining != 0) {
                                int b2 = b(byteBuffer3);
                                if (b2 <= 0) {
                                    SSL.clearError();
                                    j2 = 0;
                                    break;
                                }
                                i8 += b2;
                                if (b2 != remaining) {
                                    j2 = 0;
                                    break;
                                }
                                i9++;
                                continue;
                            } else {
                                i9++;
                                continue;
                            }
                            if (i9 >= i6) {
                                j2 = 0;
                                break;
                            }
                        }
                    } else {
                        i8 = 0;
                        j2 = 0;
                    }
                    if (j3 > j2) {
                        while (i10 < i7) {
                            ByteBuffer byteBuffer4 = byteBufferArr2[i10];
                            if (!byteBuffer4.hasRemaining()) {
                                i10++;
                            } else {
                                int c2 = c(byteBuffer4);
                                e();
                                if (c2 > 0) {
                                    i13 += c2;
                                    if (byteBuffer4.hasRemaining()) {
                                        return a(i8, i13, handshakeStatus);
                                    }
                                    i10++;
                                } else {
                                    int error = SSL.getError(this.q, c2);
                                    if (error != 6) {
                                        switch (error) {
                                            case 2:
                                            case 3:
                                                break;
                                            default:
                                                return a(SSL.getLastErrorNumber(), i8, i13);
                                        }
                                    } else if (!this.t) {
                                        d();
                                    }
                                    return a(i8, i13, handshakeStatus);
                                }
                            }
                        }
                    } else if (SSL.readFromSSL(this.q, k, 0) <= 0) {
                        int lastErrorNumber = SSL.getLastErrorNumber();
                        if (OpenSsl.a(lastErrorNumber)) {
                            return a(lastErrorNumber, i8, 0);
                        }
                    }
                    if (c() > 0) {
                        SSLEngineResult.Status status = SSLEngineResult.Status.BUFFER_OVERFLOW;
                        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
                            handshakeStatus = getHandshakeStatus();
                        }
                        return new SSLEngineResult(status, a(handshakeStatus), i8, i13);
                    }
                    if (!this.t && (SSL.getShutdown(this.q) & 2) == 2) {
                        d();
                    }
                    return a(i8, i13, handshakeStatus);
                }
                this.y = true;
                this.z = true;
                this.A = true;
                shutdown();
                throw g;
            }
        }
    }

    private SSLEngineResult a(int i2, int i3, int i4) throws SSLException {
        String errorString = SSL.getErrorString(i2);
        if (SSL.pendingWrittenBytesInBIO(this.r) > 0) {
            if (this.a == null && this.s != a.FINISHED) {
                this.a = new SSLHandshakeException(errorString);
            }
            return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, i3, i4);
        }
        throw a("SSL_read", errorString);
    }

    private int c() {
        if (this.s == a.FINISHED) {
            return SSL.pendingReadableBytesInSSL(this.q);
        }
        return 0;
    }

    private SSLEngineResult a(int i2, int i3, SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        SSLEngineResult.Status j2 = j();
        if (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED) {
            handshakeStatus = getHandshakeStatus();
        }
        return new SSLEngineResult(j2, a(handshakeStatus), i2, i3);
    }

    private void d() throws SSLException {
        this.t = true;
        closeOutbound();
        closeInbound();
    }

    private void e() throws SSLHandshakeException {
        if (this.F && SSL.getHandshakeCount(this.q) > 1) {
            shutdown();
            throw new SSLHandshakeException("remote-initiated renegotation not allowed");
        }
    }

    public SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException {
        return unwrap(byteBufferArr, 0, byteBufferArr.length, byteBufferArr2, 0, byteBufferArr2.length);
    }

    private ByteBuffer[] d(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.I;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void f() {
        this.I[0] = null;
    }

    private ByteBuffer[] e(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.J;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void g() {
        this.J[0] = null;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i2, int i3) throws SSLException {
        SSLEngineResult unwrap;
        unwrap = unwrap(d(byteBuffer), 0, 1, byteBufferArr, i2, i3);
        f();
        return unwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult wrap;
        wrap = wrap(d(byteBuffer), byteBuffer2);
        f();
        return wrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult unwrap;
        unwrap = unwrap(d(byteBuffer), e(byteBuffer2));
        f();
        g();
        return unwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        SSLEngineResult unwrap;
        unwrap = unwrap(d(byteBuffer), byteBufferArr);
        f();
        return unwrap;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void closeInbound() throws SSLException {
        if (!this.y) {
            this.y = true;
            this.A = true;
            shutdown();
            if (this.s != a.NOT_STARTED && !this.t) {
                throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized boolean isInboundDone() {
        boolean z;
        if (!this.y) {
            if (!this.A) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void closeOutbound() {
        int shutdownSSL;
        if (!this.z) {
            this.z = true;
            this.A = true;
            if (this.s != a.NOT_STARTED && !l()) {
                if ((SSL.getShutdown(this.q) & 1) != 1 && (shutdownSSL = SSL.shutdownSSL(this.q)) < 0) {
                    switch (SSL.getError(this.q, shutdownSSL)) {
                        case 0:
                        case 2:
                        case 3:
                        case 4:
                        case 6:
                        case 7:
                        case 8:
                            break;
                        case 1:
                        case 5:
                            if (b.isDebugEnabled()) {
                                b.debug("SSL_shutdown failed: OpenSSL error: {}", SSL.getLastError());
                            }
                            shutdown();
                            break;
                        default:
                            SSL.clearError();
                            break;
                    }
                }
            } else {
                shutdown();
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized boolean isOutboundDone() {
        return this.z;
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedCipherSuites() {
        Set<String> availableCipherSuites = OpenSsl.availableCipherSuites();
        return (String[]) availableCipherSuites.toArray(new String[availableCipherSuites.size()]);
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledCipherSuites() {
        synchronized (this) {
            if (!l()) {
                String[] ciphers = SSL.getCiphers(this.q);
                if (ciphers == null) {
                    return EmptyArrays.EMPTY_STRINGS;
                }
                for (int i2 = 0; i2 < ciphers.length; i2++) {
                    String b2 = b(ciphers[i2]);
                    if (b2 != null) {
                        ciphers[i2] = b2;
                    }
                }
                return ciphers;
            }
            return EmptyArrays.EMPTY_STRINGS;
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledCipherSuites(String[] strArr) {
        ObjectUtil.checkNotNull(strArr, "cipherSuites");
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            if (str == null) {
                break;
            }
            String a2 = c.a(str);
            if (a2 == null) {
                a2 = str;
            }
            if (OpenSsl.isCipherSuiteAvailable(a2)) {
                sb.append(a2);
                sb.append(':');
            } else {
                throw new IllegalArgumentException("unsupported cipher suite: " + str + '(' + a2 + ')');
            }
        }
        if (sb.length() != 0) {
            sb.setLength(sb.length() - 1);
            String sb2 = sb.toString();
            synchronized (this) {
                if (!l()) {
                    try {
                        SSL.setCipherSuites(this.q, sb2);
                    } catch (Exception e2) {
                        throw new IllegalStateException("failed to enable cipher suites: " + sb2, e2);
                    }
                } else {
                    throw new IllegalStateException("failed to enable cipher suites: " + sb2);
                }
            }
            return;
        }
        throw new IllegalArgumentException("empty cipher suites");
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getSupportedProtocols() {
        return (String[]) h.clone();
    }

    @Override // javax.net.ssl.SSLEngine
    public String[] getEnabledProtocols() {
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
        arrayList.add("SSLv2Hello");
        synchronized (this) {
            if (!l()) {
                int options = SSL.getOptions(this.q);
                if ((67108864 & options) == 0) {
                    arrayList.add("TLSv1");
                }
                if ((268435456 & options) == 0) {
                    arrayList.add("TLSv1.1");
                }
                if ((134217728 & options) == 0) {
                    arrayList.add("TLSv1.2");
                }
                if ((16777216 & options) == 0) {
                    arrayList.add("SSLv2");
                }
                if ((options & 33554432) == 0) {
                    arrayList.add("SSLv3");
                }
                return (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            return (String[]) arrayList.toArray(new String[1]);
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnabledProtocols(String[] strArr) {
        if (strArr != null) {
            int i2 = 0;
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            for (String str : strArr) {
                if (i.contains(str)) {
                    if (str.equals("SSLv2")) {
                        z = true;
                    } else if (str.equals("SSLv3")) {
                        z2 = true;
                    } else if (str.equals("TLSv1")) {
                        z3 = true;
                    } else if (str.equals("TLSv1.1")) {
                        z4 = true;
                    } else if (str.equals("TLSv1.2")) {
                        z5 = true;
                    }
                } else {
                    throw new IllegalArgumentException("Protocol " + str + " is not supported.");
                }
            }
            synchronized (this) {
                if (!l()) {
                    SSL.setOptions(this.q, (int) IAudioPolicy.AUDIO_SOURCE_TYPE_ALL);
                    SSL.clearOptions(this.q, 520093696);
                    if (!z) {
                        i2 = 16777216;
                    }
                    if (!z2) {
                        i2 |= 33554432;
                    }
                    if (!z3) {
                        i2 |= 67108864;
                    }
                    if (!z4) {
                        i2 |= 268435456;
                    }
                    if (!z5) {
                        i2 |= 134217728;
                    }
                    SSL.setOptions(this.q, i2);
                } else {
                    throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(strArr));
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLSession getSession() {
        return this.G;
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized void beginHandshake() throws SSLException {
        switch (this.s) {
            case FINISHED:
                if (this.B) {
                    throw f;
                } else if (SSL.renegotiate(this.q) == 1 && SSL.doHandshake(this.q) == 1) {
                    SSL.setState(this.q, 8192);
                } else {
                    throw a("renegotiation failed");
                }
                break;
            case NOT_STARTED:
                this.s = a.STARTED_EXPLICITLY;
                i();
                break;
            case STARTED_IMPLICITLY:
                h();
                this.s = a.STARTED_EXPLICITLY;
                break;
            case STARTED_EXPLICITLY:
                break;
            default:
                throw new Error();
        }
    }

    private void h() throws SSLException {
        if (this.A || l()) {
            throw e;
        }
    }

    private static SSLEngineResult.HandshakeStatus a(int i2) {
        return i2 > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    private SSLEngineResult.HandshakeStatus i() throws SSLException {
        if (this.s == a.FINISHED) {
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        h();
        SSLHandshakeException sSLHandshakeException = this.a;
        if (sSLHandshakeException == null) {
            this.D.a(this);
            int doHandshake = SSL.doHandshake(this.q);
            if (doHandshake <= 0) {
                SSLHandshakeException sSLHandshakeException2 = this.a;
                if (sSLHandshakeException2 == null) {
                    switch (SSL.getError(this.q, doHandshake)) {
                        case 2:
                        case 3:
                            return a(SSL.pendingWrittenBytesInBIO(this.r));
                        default:
                            throw a("SSL_do_handshake");
                    }
                } else {
                    this.a = null;
                    shutdown();
                    throw sSLHandshakeException2;
                }
            } else {
                this.G.b();
                return SSLEngineResult.HandshakeStatus.FINISHED;
            }
        } else if (SSL.pendingWrittenBytesInBIO(this.r) > 0) {
            return SSLEngineResult.HandshakeStatus.NEED_WRAP;
        } else {
            this.a = null;
            shutdown();
            throw sSLHandshakeException;
        }
    }

    private SSLEngineResult.Status j() {
        return this.A ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK;
    }

    private SSLEngineResult.HandshakeStatus a(SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        return (handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING || this.s == a.FINISHED) ? handshakeStatus : i();
    }

    @Override // javax.net.ssl.SSLEngine
    public synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return k() ? a(SSL.pendingWrittenBytesInBIO(this.r)) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.HandshakeStatus b(int i2) {
        return k() ? a(i2) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean k() {
        return this.s != a.NOT_STARTED && !l() && (this.s != a.FINISHED || this.A);
    }

    public String b(String str) {
        if (str == null) {
            return null;
        }
        return c.a(str, c(SSL.getVersion(this.q)));
    }

    private static String c(String str) {
        char c2 = 0;
        if (!(str == null || str.length() == 0)) {
            c2 = str.charAt(0);
        }
        switch (c2) {
            case 'S':
                return "SSL";
            case 'T':
                return "TLS";
            default:
                return "UNKNOWN";
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void setUseClientMode(boolean z) {
        if (z != this.B) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getUseClientMode() {
        return this.B;
    }

    @Override // javax.net.ssl.SSLEngine
    public void setNeedClientAuth(boolean z) {
        a(z ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getNeedClientAuth() {
        return this.v == ClientAuth.REQUIRE;
    }

    @Override // javax.net.ssl.SSLEngine
    public void setWantClientAuth(boolean z) {
        a(z ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    @Override // javax.net.ssl.SSLEngine
    public boolean getWantClientAuth() {
        return this.v == ClientAuth.OPTIONAL;
    }

    private void a(ClientAuth clientAuth) {
        if (!this.B) {
            synchronized (this) {
                if (this.v != clientAuth) {
                    switch (clientAuth) {
                        case NONE:
                            SSL.setVerify(this.q, 0, 10);
                            break;
                        case REQUIRE:
                            SSL.setVerify(this.q, 2, 10);
                            break;
                        case OPTIONAL:
                            SSL.setVerify(this.q, 1, 10);
                            break;
                    }
                    this.v = clientAuth;
                }
            }
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public void setEnableSessionCreation(boolean z) {
        if (z) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // javax.net.ssl.SSLEngine
    public SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        if (PlatformDependent.javaVersion() >= 7) {
            sSLParameters.setEndpointIdentificationAlgorithm(this.w);
            m.a(sSLParameters, this.x);
        }
        return sSLParameters;
    }

    @Override // javax.net.ssl.SSLEngine
    public void setSSLParameters(SSLParameters sSLParameters) {
        super.setSSLParameters(sSLParameters);
        if (PlatformDependent.javaVersion() >= 7) {
            this.w = sSLParameters.getEndpointIdentificationAlgorithm();
            this.x = sSLParameters.getAlgorithmConstraints();
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        shutdown();
    }

    public boolean l() {
        return this.u != 0;
    }

    /* loaded from: classes4.dex */
    public final class b implements a, SSLSession {
        static final /* synthetic */ boolean a = !OpenSslEngine.class.desiredAssertionStatus();
        private final OpenSslSessionContext c;
        private X509Certificate[] d;
        private String e;
        private String f;
        private Certificate[] g;
        private String h;
        private byte[] i;
        private long j;
        private Map<String, Object> k;

        @Override // javax.net.ssl.SSLSession
        public int getApplicationBufferSize() {
            return 16384;
        }

        @Override // javax.net.ssl.SSLSession
        public int getPacketBufferSize() {
            return 18713;
        }

        b(OpenSslSessionContext openSslSessionContext) {
            OpenSslEngine.this = r1;
            this.c = openSslSessionContext;
        }

        @Override // javax.net.ssl.SSLSession
        public byte[] getId() {
            synchronized (OpenSslEngine.this) {
                if (this.i == null) {
                    return EmptyArrays.EMPTY_BYTES;
                }
                return (byte[]) this.i.clone();
            }
        }

        @Override // javax.net.ssl.SSLSession
        public SSLSessionContext getSessionContext() {
            return this.c;
        }

        @Override // javax.net.ssl.SSLSession
        public long getCreationTime() {
            synchronized (OpenSslEngine.this) {
                if (this.j == 0 && !OpenSslEngine.this.l()) {
                    this.j = SSL.getTime(OpenSslEngine.this.q) * 1000;
                }
            }
            return this.j;
        }

        @Override // javax.net.ssl.SSLSession
        public long getLastAccessedTime() {
            return getCreationTime();
        }

        @Override // javax.net.ssl.SSLSession
        public void invalidate() {
            synchronized (OpenSslEngine.this) {
                if (!OpenSslEngine.this.l()) {
                    SSL.setTimeout(OpenSslEngine.this.q, 0L);
                }
            }
        }

        @Override // javax.net.ssl.SSLSession
        public boolean isValid() {
            synchronized (OpenSslEngine.this) {
                boolean z = false;
                if (OpenSslEngine.this.l()) {
                    return false;
                }
                if (System.currentTimeMillis() - (SSL.getTimeout(OpenSslEngine.this.q) * 1000) < SSL.getTime(OpenSslEngine.this.q) * 1000) {
                    z = true;
                }
                return z;
            }
        }

        @Override // javax.net.ssl.SSLSession
        public void putValue(String str, Object obj) {
            if (str == null) {
                throw new NullPointerException("name");
            } else if (obj != null) {
                Map map = this.k;
                if (map == null) {
                    map = new HashMap(2);
                    this.k = map;
                }
                Object put = map.put(str, obj);
                if (obj instanceof SSLSessionBindingListener) {
                    ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(this, str));
                }
                a(put, str);
            } else {
                throw new NullPointerException(com.xiaomi.onetrack.api.b.p);
            }
        }

        @Override // javax.net.ssl.SSLSession
        public Object getValue(String str) {
            if (str != null) {
                Map<String, Object> map = this.k;
                if (map == null) {
                    return null;
                }
                return map.get(str);
            }
            throw new NullPointerException("name");
        }

        @Override // javax.net.ssl.SSLSession
        public void removeValue(String str) {
            if (str != null) {
                Map<String, Object> map = this.k;
                if (map != null) {
                    a(map.remove(str), str);
                    return;
                }
                return;
            }
            throw new NullPointerException("name");
        }

        @Override // javax.net.ssl.SSLSession
        public String[] getValueNames() {
            Map<String, Object> map = this.k;
            if (map == null || map.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return (String[]) map.keySet().toArray(new String[map.size()]);
        }

        private void a(Object obj, String str) {
            if (obj instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) obj).valueUnbound(new SSLSessionBindingEvent(this, str));
            }
        }

        void b() throws SSLException {
            synchronized (OpenSslEngine.this) {
                if (!OpenSslEngine.this.l()) {
                    this.i = SSL.getSessionId(OpenSslEngine.this.q);
                    this.h = OpenSslEngine.this.b(SSL.getCipherForSSL(OpenSslEngine.this.q));
                    this.e = SSL.getVersion(OpenSslEngine.this.q);
                    c();
                    d();
                    OpenSslEngine.this.s = a.FINISHED;
                } else {
                    throw new SSLException("Already closed");
                }
            }
        }

        private void c() {
            Certificate[] certificateArr;
            int i;
            byte[][] peerCertChain = SSL.getPeerCertChain(OpenSslEngine.this.q);
            byte[] peerCertificate = !OpenSslEngine.this.B ? SSL.getPeerCertificate(OpenSslEngine.this.q) : null;
            if (peerCertChain == null && peerCertificate == null) {
                this.g = OpenSslEngine.c;
                this.d = OpenSslEngine.d;
                return;
            }
            int length = peerCertChain != null ? peerCertChain.length : 0;
            if (peerCertificate != null) {
                certificateArr = new Certificate[length + 1];
                certificateArr[0] = new k(peerCertificate);
                i = 1;
            } else {
                certificateArr = new Certificate[length];
                i = 0;
            }
            if (peerCertChain != null) {
                X509Certificate[] x509CertificateArr = new X509Certificate[peerCertChain.length];
                for (int i2 = 0; i2 < x509CertificateArr.length; i2++) {
                    byte[] bArr = peerCertChain[i2];
                    x509CertificateArr[i2] = new j(bArr);
                    certificateArr[i] = new k(bArr);
                    i++;
                }
                this.d = x509CertificateArr;
            } else {
                this.d = OpenSslEngine.d;
            }
            this.g = certificateArr;
        }

        private void d() throws SSLException {
            ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior = OpenSslEngine.this.E.selectedListenerFailureBehavior();
            List<String> protocols = OpenSslEngine.this.E.protocols();
            switch (OpenSslEngine.this.E.protocol()) {
                case NONE:
                    return;
                case ALPN:
                    String alpnSelected = SSL.getAlpnSelected(OpenSslEngine.this.q);
                    if (alpnSelected != null) {
                        this.f = a(protocols, selectedListenerFailureBehavior, alpnSelected);
                        return;
                    }
                    return;
                case NPN:
                    String nextProtoNegotiated = SSL.getNextProtoNegotiated(OpenSslEngine.this.q);
                    if (nextProtoNegotiated != null) {
                        this.f = a(protocols, selectedListenerFailureBehavior, nextProtoNegotiated);
                        return;
                    }
                    return;
                case NPN_AND_ALPN:
                    String alpnSelected2 = SSL.getAlpnSelected(OpenSslEngine.this.q);
                    if (alpnSelected2 == null) {
                        alpnSelected2 = SSL.getNextProtoNegotiated(OpenSslEngine.this.q);
                    }
                    if (alpnSelected2 != null) {
                        this.f = a(protocols, selectedListenerFailureBehavior, alpnSelected2);
                        return;
                    }
                    return;
                default:
                    throw new Error();
            }
        }

        private String a(List<String> list, ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior, String str) throws SSLException {
            if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
                return str;
            }
            int size = list.size();
            if (!a && size <= 0) {
                throw new AssertionError();
            } else if (list.contains(str)) {
                return str;
            } else {
                if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                    return list.get(size - 1);
                }
                throw new SSLException("unknown protocol " + str);
            }
        }

        @Override // javax.net.ssl.SSLSession
        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            Certificate[] certificateArr;
            synchronized (OpenSslEngine.this) {
                if (this.g == null || this.g.length == 0) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                certificateArr = this.g;
            }
            return certificateArr;
        }

        @Override // javax.net.ssl.SSLSession
        public Certificate[] getLocalCertificates() {
            if (OpenSslEngine.this.H == null) {
                return null;
            }
            return (Certificate[]) OpenSslEngine.this.H.clone();
        }

        @Override // javax.net.ssl.SSLSession
        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            X509Certificate[] x509CertificateArr;
            synchronized (OpenSslEngine.this) {
                if (this.d == null || this.d.length == 0) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                x509CertificateArr = this.d;
            }
            return x509CertificateArr;
        }

        @Override // javax.net.ssl.SSLSession
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return ((java.security.cert.X509Certificate) getPeerCertificates()[0]).getSubjectX500Principal();
        }

        @Override // javax.net.ssl.SSLSession
        public Principal getLocalPrincipal() {
            Certificate[] certificateArr = OpenSslEngine.this.H;
            if (certificateArr == null || certificateArr.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate) certificateArr[0]).getIssuerX500Principal();
        }

        @Override // javax.net.ssl.SSLSession
        public String getCipherSuite() {
            synchronized (OpenSslEngine.this) {
                if (this.h == null) {
                    return "SSL_NULL_WITH_NULL_NULL";
                }
                return this.h;
            }
        }

        @Override // javax.net.ssl.SSLSession
        public String getProtocol() {
            String str = this.e;
            if (str == null) {
                synchronized (OpenSslEngine.this) {
                    str = !OpenSslEngine.this.l() ? SSL.getVersion(OpenSslEngine.this.q) : "";
                }
            }
            return str;
        }

        @Override // io.netty.handler.ssl.a
        public String a() {
            String str;
            synchronized (OpenSslEngine.this) {
                str = this.f;
            }
            return str;
        }

        @Override // javax.net.ssl.SSLSession
        public String getPeerHost() {
            return OpenSslEngine.this.getPeerHost();
        }

        @Override // javax.net.ssl.SSLSession
        public int getPeerPort() {
            return OpenSslEngine.this.getPeerPort();
        }
    }
}
