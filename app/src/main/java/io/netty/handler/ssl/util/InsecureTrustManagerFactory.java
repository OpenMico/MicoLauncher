package io.netty.handler.ssl.util;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public final class InsecureTrustManagerFactory extends SimpleTrustManagerFactory {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(InsecureTrustManagerFactory.class);
    public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();
    private static final TrustManager b = new X509TrustManager() { // from class: io.netty.handler.ssl.util.InsecureTrustManagerFactory.1
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            InternalLogger internalLogger = InsecureTrustManagerFactory.a;
            internalLogger.debug("Accepting a client certificate: " + x509CertificateArr[0].getSubjectDN());
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            InternalLogger internalLogger = InsecureTrustManagerFactory.a;
            internalLogger.debug("Accepting a server certificate: " + x509CertificateArr[0].getSubjectDN());
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return EmptyArrays.EMPTY_X509_CERTIFICATES;
        }
    };

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected void engineInit(KeyStore keyStore) throws Exception {
    }

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }

    private InsecureTrustManagerFactory() {
    }

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{b};
    }
}
