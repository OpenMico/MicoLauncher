package com.zhy.http.okhttp.https;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class HttpsUtils {

    /* loaded from: classes4.dex */
    public static class SSLParams {
        public SSLSocketFactory sSLSocketFactory;
        public X509TrustManager trustManager;
    }

    public static SSLParams getSslSocketFactory(InputStream[] inputStreamArr, InputStream inputStream, String str) {
        X509TrustManager x509TrustManager;
        SSLParams sSLParams = new SSLParams();
        try {
            TrustManager[] a2 = a(inputStreamArr);
            KeyManager[] a3 = a(inputStream, str);
            SSLContext instance = SSLContext.getInstance("TLS");
            if (a2 != null) {
                x509TrustManager = new a(b(a2));
            } else {
                x509TrustManager = new b();
            }
            instance.init(a3, new TrustManager[]{x509TrustManager}, null);
            sSLParams.sSLSocketFactory = instance.getSocketFactory();
            sSLParams.trustManager = x509TrustManager;
            return sSLParams;
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e2) {
            throw new AssertionError(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new AssertionError(e3);
        }
    }

    /* loaded from: classes4.dex */
    private static class b implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        private b() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static TrustManager[] a(InputStream... inputStreamArr) {
        if (inputStreamArr == null || inputStreamArr.length <= 0) {
            return null;
        }
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            KeyStore instance2 = KeyStore.getInstance(KeyStore.getDefaultType());
            instance2.load(null);
            int i = 0;
            for (InputStream inputStream : inputStreamArr) {
                i++;
                instance2.setCertificateEntry(Integer.toString(i), instance.generateCertificate(inputStream));
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused) {
                    }
                }
            }
            TrustManagerFactory instance3 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance3.init(instance2);
            return instance3.getTrustManagers();
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (CertificateException e3) {
            e3.printStackTrace();
            return null;
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private static KeyManager[] a(InputStream inputStream, String str) {
        if (inputStream == null || str == null) {
            return null;
        }
        try {
            KeyStore instance = KeyStore.getInstance("BKS");
            instance.load(inputStream, str.toCharArray());
            KeyManagerFactory instance2 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            instance2.init(instance, str.toCharArray());
            return instance2.getKeyManagers();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (KeyStoreException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (UnrecoverableKeyException e4) {
            e4.printStackTrace();
            return null;
        } catch (CertificateException e5) {
            e5.printStackTrace();
            return null;
        } catch (Exception e6) {
            e6.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static X509TrustManager b(TrustManager[] trustManagerArr) {
        for (TrustManager trustManager : trustManagerArr) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    /* loaded from: classes4.dex */
    private static class a implements X509TrustManager {
        private X509TrustManager a;
        private X509TrustManager b;

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        public a(X509TrustManager x509TrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            this.a = HttpsUtils.b(instance.getTrustManagers());
            this.b = x509TrustManager;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            try {
                this.a.checkServerTrusted(x509CertificateArr, str);
            } catch (CertificateException unused) {
                this.b.checkServerTrusted(x509CertificateArr, str);
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
