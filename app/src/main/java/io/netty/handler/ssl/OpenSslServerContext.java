package io.netty.handler.ssl;

import io.netty.handler.ssl.OpenSslContext;
import io.netty.util.internal.ObjectUtil;
import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;

/* loaded from: classes4.dex */
public final class OpenSslServerContext extends OpenSslContext {
    private static final byte[] c = {110, 101, 116, 116, 121};
    private final OpenSslServerSessionContext e;

    @Deprecated
    public OpenSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str) throws SSLException {
        this(file, file2, str, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0L, 0L);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, IdentityCipherSuiteFilter.INSTANCE, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, a(iterable2), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, trustManagerFactory, iterable, a(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, (CipherSuiteFilter) null, openSslApplicationProtocolNegotiator, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this((File) null, (TrustManagerFactory) null, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, str, keyManagerFactory, iterable, cipherSuiteFilter, a(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, a(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this(b(file), trustManagerFactory, b(file2), b(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, ClientAuth.NONE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth) throws SSLException {
        this(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, a(applicationProtocolConfig), j, j2, clientAuth);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v3 */
    private OpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2, ClientAuth clientAuth) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, 1, x509CertificateArr2, clientAuth);
        Throwable th;
        Exception e;
        long a;
        Throwable th2;
        Exception e2;
        TrustManagerFactory trustManagerFactory2;
        try {
            a(keyManagerFactory);
            long j3 = x509CertificateArr2;
            ObjectUtil.checkNotNull(j3, "keyCertChainFile");
            ObjectUtil.checkNotNull(privateKey, "keyFile");
            String str2 = str == null ? "" : str;
            synchronized (OpenSslContext.class) {
                long j4 = this.ctx;
                long j5 = 0;
                SSLContext.setVerify(j4, 0, 10);
                try {
                    j4 = 0;
                } catch (Throwable th3) {
                    th = th3;
                }
                try {
                    a = a(x509CertificateArr2);
                } catch (Exception e3) {
                    e = e3;
                } catch (Throwable th4) {
                    th = th4;
                    j5 = 0;
                    if ((j5 == true ? 1L : 0L) != j4) {
                        SSL.freeBIO(j5);
                    }
                    throw th;
                }
                try {
                    if (!SSLContext.setCertificateChainBio(this.ctx, a, true)) {
                        long lastErrorNumber = SSL.getLastErrorNumber();
                        if (OpenSsl.a(lastErrorNumber)) {
                            throw new SSLException("failed to set certificate chain: " + SSL.getErrorString(lastErrorNumber));
                        }
                    }
                    if (a != 0) {
                        SSL.freeBIO(a);
                    }
                    try {
                        try {
                            a = a(privateKey);
                        } catch (Throwable th5) {
                            th2 = th5;
                        }
                    } catch (SSLException e4) {
                        throw e4;
                    } catch (Exception e5) {
                        e2 = e5;
                    } catch (Throwable th6) {
                        th2 = th6;
                        j3 = 0;
                        a = 0;
                    }
                    try {
                        long a2 = a(x509CertificateArr2);
                        try {
                            if (!SSLContext.setCertificateBio(this.ctx, a2, a, str2, 0)) {
                                long lastErrorNumber2 = SSL.getLastErrorNumber();
                                if (OpenSsl.a(lastErrorNumber2)) {
                                    throw new SSLException("failed to set certificate and key: " + SSL.getErrorString(lastErrorNumber2));
                                }
                            }
                            if (a != 0) {
                                SSL.freeBIO(a);
                            }
                            if (a2 != 0) {
                                SSL.freeBIO(a2);
                            }
                            try {
                                if (x509CertificateArr != null) {
                                    trustManagerFactory2 = a(x509CertificateArr, trustManagerFactory);
                                } else if (trustManagerFactory == null) {
                                    trustManagerFactory2 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                                    trustManagerFactory2.init((KeyStore) null);
                                } else {
                                    trustManagerFactory2 = trustManagerFactory;
                                }
                                final X509TrustManager chooseTrustManager = chooseTrustManager(trustManagerFactory2.getTrustManagers());
                                if (a(chooseTrustManager)) {
                                    final X509ExtendedTrustManager x509ExtendedTrustManager = (X509ExtendedTrustManager) chooseTrustManager;
                                    SSLContext.setCertVerifyCallback(this.ctx, new OpenSslContext.a() { // from class: io.netty.handler.ssl.OpenSslServerContext.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super();
                                        }
                                    });
                                } else {
                                    SSLContext.setCertVerifyCallback(this.ctx, new OpenSslContext.a() { // from class: io.netty.handler.ssl.OpenSslServerContext.2
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super();
                                        }
                                    });
                                }
                            } catch (Exception e6) {
                                throw new SSLException("unable to setup trustmanager", e6);
                            }
                        } catch (SSLException e7) {
                            throw e7;
                        } catch (Exception e8) {
                            e2 = e8;
                            throw new SSLException("failed to set certificate and key", e2);
                        }
                    } catch (SSLException e9) {
                        throw e9;
                    } catch (Exception e10) {
                        e2 = e10;
                    } catch (Throwable th7) {
                        th2 = th7;
                        j3 = 0;
                        if (a != 0) {
                            SSL.freeBIO(a);
                        }
                        if ((j3 == true ? 1L : 0L) != 0) {
                            SSL.freeBIO(j3);
                        }
                        throw th2;
                    }
                } catch (Exception e11) {
                    e = e11;
                    throw new SSLException("failed to set certificate chain", e);
                }
            }
            this.e = new OpenSslServerSessionContext(this.ctx);
            this.e.setSessionIdContext(c);
        } catch (Throwable th8) {
            destroy();
            throw th8;
        }
    }

    @Override // io.netty.handler.ssl.OpenSslContext, io.netty.handler.ssl.SslContext
    public OpenSslServerSessionContext sessionContext() {
        return this.e;
    }
}
