package io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

@Deprecated
/* loaded from: classes4.dex */
public final class JdkSslServerContext extends JdkSslContext {
    @Deprecated
    public JdkSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str) throws SSLException {
        this(file, file2, str, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, e.a, 0L, 0L);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, IdentityCipherSuiteFilter.INSTANCE, a(a(iterable2), true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, cipherSuiteFilter, a(applicationProtocolConfig, true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this((File) null, (TrustManagerFactory) null, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, str, keyManagerFactory, iterable, cipherSuiteFilter, a(applicationProtocolConfig, true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        super(a(b(file), trustManagerFactory, b(file2), b(file3, str), str, keyManagerFactory, j, j2), false, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JdkSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth) throws SSLException {
        super(a(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, j, j2), false, iterable, cipherSuiteFilter, a(applicationProtocolConfig, true), clientAuth);
    }

    private static SSLContext a(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, long j, long j2) throws SSLException {
        if (privateKey == null && keyManagerFactory == null) {
            throw new NullPointerException("key, keyManagerFactory");
        }
        if (x509CertificateArr != null) {
            try {
                trustManagerFactory = a(x509CertificateArr, trustManagerFactory);
            } catch (Exception e) {
                if (e instanceof SSLException) {
                    throw ((SSLException) e);
                }
                throw new SSLException("failed to initialize the server-side SSL context", e);
            }
        }
        if (privateKey != null) {
            keyManagerFactory = a(x509CertificateArr2, privateKey, str, keyManagerFactory);
        }
        SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(keyManagerFactory.getKeyManagers(), trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), null);
        SSLSessionContext serverSessionContext = instance.getServerSessionContext();
        if (j > 0) {
            serverSessionContext.setSessionCacheSize((int) Math.min(j, 2147483647L));
        }
        if (j2 > 0) {
            serverSessionContext.setSessionTimeout((int) Math.min(j2, 2147483647L));
        }
        return instance;
    }
}
