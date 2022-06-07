package com.xiaomi.mitv.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class HTTPSTrustManager implements X509TrustManager {
    private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[0];
    private static TrustManager[] trustManagers;

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return _AcceptedIssuers;
    }

    public static void allowAllSSL() throws NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { // from class: com.xiaomi.mitv.utils.HTTPSTrustManager.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        });
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new HTTPSTrustManager()};
        }
        SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, trustManagers, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(instance.getSocketFactory());
    }
}
