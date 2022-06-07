package com.xiaomi.micolauncher.application;

import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.common.L;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/* loaded from: classes3.dex */
public class GlideOkHttpUtil {
    private static OkHttpClient a;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str, SSLSession sSLSession) {
        return true;
    }

    public static OkHttpClient getGlideConfigOkHttpClient() {
        OkHttpClient okHttpClient = a;
        if (okHttpClient != null) {
            return okHttpClient;
        }
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            TrustManager[] trustManagers = instance.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];
            SSLContext instance2 = SSLContext.getInstance("TLS");
            instance2.init(null, new TrustManager[]{x509TrustManager}, null);
            SSLSocketFactory socketFactory = instance2.getSocketFactory();
            OkHttpClient.Builder createOkHttpClientBuilderWithNetworkInterceptor = ApiManager.createOkHttpClientBuilderWithNetworkInterceptor();
            createOkHttpClientBuilderWithNetworkInterceptor.sslSocketFactory(socketFactory, x509TrustManager);
            createOkHttpClientBuilderWithNetworkInterceptor.hostnameVerifier($$Lambda$GlideOkHttpUtil$7TWCbsUzbrT5bviAi_zgYVcq8.INSTANCE);
            createOkHttpClientBuilderWithNetworkInterceptor.connectTimeout(20L, TimeUnit.SECONDS);
            createOkHttpClientBuilderWithNetworkInterceptor.readTimeout(20L, TimeUnit.SECONDS);
            createOkHttpClientBuilderWithNetworkInterceptor.writeTimeout(20L, TimeUnit.SECONDS);
            createOkHttpClientBuilderWithNetworkInterceptor.dispatcher(new Dispatcher(Threads.getIoThreadPool()));
            a = createOkHttpClientBuilderWithNetworkInterceptor.build();
            return a;
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException e) {
            L.base.e("failed to init Glide okhttp ", e);
            return null;
        }
    }
}
