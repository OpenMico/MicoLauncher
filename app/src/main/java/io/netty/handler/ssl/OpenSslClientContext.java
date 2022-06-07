package io.netty.handler.ssl;

import java.io.File;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

/* loaded from: classes4.dex */
public final class OpenSslClientContext extends OpenSslContext {
    private final OpenSslSessionContext c;

    @Deprecated
    public OpenSslClientContext() throws SSLException {
        this((File) null, (TrustManagerFactory) null, (File) null, (File) null, (String) null, (KeyManagerFactory) null, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0L, 0L);
    }

    @Deprecated
    public OpenSslClientContext(File file) throws SSLException {
        this(file, null);
    }

    @Deprecated
    public OpenSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        this(null, trustManagerFactory);
    }

    @Deprecated
    public OpenSslClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        this(file, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0L, 0L);
    }

    @Deprecated
    public OpenSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, iterable, IdentityCipherSuiteFilter.INSTANCE, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, (File) null, (File) null, (String) null, (KeyManagerFactory) null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslClientContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(b(file), trustManagerFactory, b(file2), b(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r4v0, types: [io.netty.handler.ssl.ApplicationProtocolConfig] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [long] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OpenSslClientContext(java.security.cert.X509Certificate[] r14, javax.net.ssl.TrustManagerFactory r15, java.security.cert.X509Certificate[] r16, java.security.PrivateKey r17, java.lang.String r18, javax.net.ssl.KeyManagerFactory r19, java.lang.Iterable<java.lang.String> r20, io.netty.handler.ssl.CipherSuiteFilter r21, io.netty.handler.ssl.ApplicationProtocolConfig r22, long r23, long r25) throws javax.net.ssl.SSLException {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.OpenSslClientContext.<init>(java.security.cert.X509Certificate[], javax.net.ssl.TrustManagerFactory, java.security.cert.X509Certificate[], java.security.PrivateKey, java.lang.String, javax.net.ssl.KeyManagerFactory, java.lang.Iterable, io.netty.handler.ssl.CipherSuiteFilter, io.netty.handler.ssl.ApplicationProtocolConfig, long, long):void");
    }

    @Override // io.netty.handler.ssl.OpenSslContext, io.netty.handler.ssl.SslContext
    public OpenSslSessionContext sessionContext() {
        return this.c;
    }

    /* loaded from: classes4.dex */
    private static final class a extends OpenSslSessionContext {
        @Override // javax.net.ssl.SSLSessionContext
        public int getSessionCacheSize() {
            return 0;
        }

        @Override // javax.net.ssl.SSLSessionContext
        public int getSessionTimeout() {
            return 0;
        }

        @Override // io.netty.handler.ssl.OpenSslSessionContext
        public boolean isSessionCacheEnabled() {
            return false;
        }

        @Override // io.netty.handler.ssl.OpenSslSessionContext
        public void setSessionCacheEnabled(boolean z) {
        }

        private a(long j) {
            super(j);
        }

        @Override // javax.net.ssl.SSLSessionContext
        public void setSessionTimeout(int i) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override // javax.net.ssl.SSLSessionContext
        public void setSessionCacheSize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
        }
    }
}
