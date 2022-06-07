package io.netty.handler.ssl;

import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.tomcat.jni.CertificateVerifier;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;

/* loaded from: classes4.dex */
public abstract class OpenSslContext extends SslContext {
    protected static final int VERIFY_DEPTH = 10;
    private static final List<String> j;
    long a;
    protected volatile long ctx;
    private volatile boolean k;
    private final List<String> l;
    private final long m;
    private final long n;
    private final i o;
    private final OpenSslApplicationProtocolNegotiator p;
    private final int q;
    private final Certificate[] r;
    private final ClientAuth s;
    private static final byte[] c = "-----BEGIN CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] e = "\n-----END CERTIFICATE-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] f = "-----BEGIN PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final byte[] g = "\n-----END PRIVATE KEY-----\n".getBytes(CharsetUtil.US_ASCII);
    private static final InternalLogger h = InternalLoggerFactory.getInstance(OpenSslContext.class);
    private static final boolean i = SystemPropertyUtil.getBoolean("jdk.tls.rejectClientInitiatedRenegotiation", false);
    static final OpenSslApplicationProtocolNegotiator b = new OpenSslApplicationProtocolNegotiator() { // from class: io.netty.handler.ssl.OpenSslContext.1
        @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
        public ApplicationProtocolConfig.Protocol protocol() {
            return ApplicationProtocolConfig.Protocol.NONE;
        }

        @Override // io.netty.handler.ssl.ApplicationProtocolNegotiator
        public List<String> protocols() {
            return Collections.emptyList();
        }

        @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
        public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
            return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        }

        @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
        public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
            return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
        }
    };

    @Override // io.netty.handler.ssl.SslContext
    public abstract OpenSslSessionContext sessionContext();

    static {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "AES128-SHA", "AES256-SHA", "DES-CBC3-SHA");
        j = Collections.unmodifiableList(arrayList);
        if (h.isDebugEnabled()) {
            h.debug("Default cipher suite (OpenSSL): " + arrayList);
        }
    }

    public OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j2, long j3, int i2, Certificate[] certificateArr, ClientAuth clientAuth) throws SSLException {
        this(iterable, cipherSuiteFilter, a(applicationProtocolConfig), j2, j3, i2, certificateArr, clientAuth);
    }

    public OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j2, long j3, int i2, Certificate[] certificateArr, ClientAuth clientAuth) throws SSLException {
        String next;
        ArrayList arrayList = null;
        this.o = new b();
        OpenSsl.ensureAvailability();
        if (i2 == 1 || i2 == 0) {
            this.q = i2;
            this.s = isServer() ? (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
            if (i2 == 1) {
                this.k = i;
            }
            this.r = certificateArr == null ? null : (Certificate[]) certificateArr.clone();
            if (iterable != null) {
                arrayList = new ArrayList();
                Iterator<String> it = iterable.iterator();
                while (it.hasNext() && (next = it.next()) != null) {
                    String a2 = c.a(next);
                    if (a2 != null) {
                        next = a2;
                    }
                    arrayList.add(next);
                }
            }
            this.l = Arrays.asList(((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter")).filterCipherSuites(arrayList, j, OpenSsl.availableCipherSuites()));
            this.p = (OpenSslApplicationProtocolNegotiator) ObjectUtil.checkNotNull(openSslApplicationProtocolNegotiator, "apn");
            this.a = Pool.create(0L);
            try {
                synchronized (OpenSslContext.class) {
                    try {
                        this.ctx = SSLContext.make(this.a, 31, i2);
                        SSLContext.setOptions(this.ctx, (int) IAudioPolicy.AUDIO_SOURCE_TYPE_ALL);
                        SSLContext.setOptions(this.ctx, 16777216);
                        SSLContext.setOptions(this.ctx, 33554432);
                        SSLContext.setOptions(this.ctx, 4194304);
                        SSLContext.setOptions(this.ctx, 524288);
                        SSLContext.setOptions(this.ctx, 1048576);
                        SSLContext.setOptions(this.ctx, 65536);
                        SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | 2);
                        try {
                            SSLContext.setCipherSuite(this.ctx, c.a(this.l));
                            List<String> protocols = openSslApplicationProtocolNegotiator.protocols();
                            if (!protocols.isEmpty()) {
                                String[] strArr = (String[]) protocols.toArray(new String[protocols.size()]);
                                int a3 = a(openSslApplicationProtocolNegotiator.selectorFailureBehavior());
                                switch (openSslApplicationProtocolNegotiator.protocol()) {
                                    case NPN:
                                        SSLContext.setNpnProtos(this.ctx, strArr, a3);
                                        break;
                                    case ALPN:
                                        SSLContext.setAlpnProtos(this.ctx, strArr, a3);
                                        break;
                                    case NPN_AND_ALPN:
                                        SSLContext.setNpnProtos(this.ctx, strArr, a3);
                                        SSLContext.setAlpnProtos(this.ctx, strArr, a3);
                                        break;
                                    default:
                                        throw new Error();
                                }
                            }
                            if (j2 > 0) {
                                this.m = j2;
                                SSLContext.setSessionCacheSize(this.ctx, j2);
                            } else {
                                long sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
                                this.m = sessionCacheSize;
                                SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
                            }
                            if (j3 > 0) {
                                this.n = j3;
                                SSLContext.setSessionCacheTimeout(this.ctx, j3);
                            } else {
                                long sessionCacheTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
                                this.n = sessionCacheTimeout;
                                SSLContext.setSessionCacheTimeout(this.ctx, sessionCacheTimeout);
                            }
                        } catch (SSLException e2) {
                            throw e2;
                        } catch (Exception e3) {
                            throw new SSLException("failed to set cipher suite: " + this.l, e3);
                        }
                    } catch (Exception e4) {
                        throw new SSLException("failed to create an SSL_CTX", e4);
                    }
                }
            } catch (Throwable th) {
                destroy();
                throw th;
            }
        } else {
            throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
        }
    }

    private static int a(ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior) {
        switch (selectorFailureBehavior) {
            case NO_ADVERTISE:
                return 0;
            case CHOOSE_MY_LAST_PROTOCOL:
                return 1;
            default:
                throw new Error();
        }
    }

    @Override // io.netty.handler.ssl.SslContext
    public final List<String> cipherSuites() {
        return this.l;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final long sessionCacheSize() {
        return this.m;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final long sessionTimeout() {
        return this.n;
    }

    @Override // io.netty.handler.ssl.SslContext
    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.p;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final boolean isClient() {
        return this.q == 0;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i2) {
        return new OpenSslEngine(this.ctx, byteBufAllocator, isClient(), sessionContext(), this.p, this.o, this.k, str, i2, this.r, this.s);
    }

    @Override // io.netty.handler.ssl.SslContext
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return newEngine(byteBufAllocator, null, -1);
    }

    @Deprecated
    public final long context() {
        return this.ctx;
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return sessionContext().stats();
    }

    public void setRejectRemoteInitiatedRenegotiation(boolean z) {
        this.k = z;
    }

    protected final void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    @Deprecated
    public final void setTicketKeys(byte[] bArr) {
        sessionContext().setTicketKeys(bArr);
    }

    public final long sslCtxPointer() {
        return this.ctx;
    }

    protected final void destroy() {
        synchronized (OpenSslContext.class) {
            if (this.ctx != 0) {
                SSLContext.free(this.ctx);
                this.ctx = 0L;
            }
            if (this.a != 0) {
                Pool.destroy(this.a);
                this.a = 0L;
            }
        }
    }

    protected static X509Certificate[] certificates(byte[][] bArr) {
        X509Certificate[] x509CertificateArr = new X509Certificate[bArr.length];
        for (int i2 = 0; i2 < x509CertificateArr.length; i2++) {
            x509CertificateArr[i2] = new k(bArr[i2]);
        }
        return x509CertificateArr;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] trustManagerArr) {
        for (TrustManager trustManager : trustManagerArr) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    static OpenSslApplicationProtocolNegotiator a(ApplicationProtocolConfig applicationProtocolConfig) {
        if (applicationProtocolConfig == null) {
            return b;
        }
        switch (applicationProtocolConfig.protocol()) {
            case NPN:
            case ALPN:
            case NPN_AND_ALPN:
                switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                    case CHOOSE_MY_LAST_PROTOCOL:
                    case ACCEPT:
                        switch (applicationProtocolConfig.selectorFailureBehavior()) {
                            case NO_ADVERTISE:
                            case CHOOSE_MY_LAST_PROTOCOL:
                                return new OpenSslDefaultApplicationProtocolNegotiator(applicationProtocolConfig);
                            default:
                                throw new UnsupportedOperationException("OpenSSL provider does not support " + applicationProtocolConfig.selectorFailureBehavior() + " behavior");
                        }
                    default:
                        throw new UnsupportedOperationException("OpenSSL provider does not support " + applicationProtocolConfig.selectedListenerFailureBehavior() + " behavior");
                }
            case NONE:
                return b;
            default:
                throw new Error();
        }
    }

    static boolean a(X509TrustManager x509TrustManager) {
        return PlatformDependent.javaVersion() >= 7 && (x509TrustManager instanceof X509ExtendedTrustManager);
    }

    /* loaded from: classes4.dex */
    public abstract class a implements CertificateVerifier {
        public a() {
            OpenSslContext.this = r1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b implements i {
        private final Map<Long, OpenSslEngine> b;

        private b() {
            this.b = PlatformDependent.newConcurrentHashMap();
        }

        @Override // io.netty.handler.ssl.i
        public OpenSslEngine a(long j) {
            return this.b.remove(Long.valueOf(j));
        }

        @Override // io.netty.handler.ssl.i
        public void a(OpenSslEngine openSslEngine) {
            this.b.put(Long.valueOf(openSslEngine.sslPointer()), openSslEngine);
        }
    }

    static long a(PrivateKey privateKey) throws Exception {
        if (privateKey == null) {
            return 0L;
        }
        ByteBuf directBuffer = Unpooled.directBuffer();
        try {
            directBuffer.writeBytes(f);
            ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(privateKey.getEncoded());
            ByteBuf encode = Base64.encode(wrappedBuffer, true);
            directBuffer.writeBytes(encode);
            a(encode);
            a(wrappedBuffer);
            directBuffer.writeBytes(g);
            return b(directBuffer);
        } finally {
            a(directBuffer);
        }
    }

    private static void a(ByteBuf byteBuf) {
        byteBuf.setZero(0, byteBuf.capacity());
        byteBuf.release();
    }

    static long a(X509Certificate[] x509CertificateArr) throws Exception {
        if (x509CertificateArr == null) {
            return 0L;
        }
        ByteBuf directBuffer = Unpooled.directBuffer();
        try {
            for (X509Certificate x509Certificate : x509CertificateArr) {
                directBuffer.writeBytes(c);
                ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(x509Certificate.getEncoded());
                directBuffer = Base64.encode(wrappedBuffer, true);
                try {
                    directBuffer.writeBytes(directBuffer);
                    directBuffer.release();
                    wrappedBuffer.release();
                    directBuffer.writeBytes(e);
                } finally {
                }
            }
            return b(directBuffer);
        } finally {
        }
    }

    private static long b(ByteBuf byteBuf) throws Exception {
        long newMemBIO = SSL.newMemBIO();
        int readableBytes = byteBuf.readableBytes();
        if (SSL.writeToBIO(newMemBIO, OpenSsl.a(byteBuf), readableBytes) == readableBytes) {
            return newMemBIO;
        }
        SSL.freeBIO(newMemBIO);
        throw new IllegalStateException("Could not write data to memory BIO");
    }

    static void a(KeyManagerFactory keyManagerFactory) {
        if (keyManagerFactory != null) {
            throw new IllegalArgumentException("KeyManagerFactory is currently not supported with OpenSslContext");
        }
    }
}
