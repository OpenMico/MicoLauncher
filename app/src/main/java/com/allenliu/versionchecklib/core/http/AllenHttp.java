package com.allenliu.versionchecklib.core.http;

import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.v2.builder.RequestVersionBuilder;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AllenHttp {
    private static OkHttpClient a;

    public static OkHttpClient getHttpClient() {
        if (a == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(a());
            builder.hostnameVerifier(new b());
            a = builder.build();
        }
        return a;
    }

    /* loaded from: classes.dex */
    public static class b implements HostnameVerifier {
        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }

        private b() {
        }
    }

    private static SSLSocketFactory a() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{new a()}, new SecureRandom());
            return instance.getSocketFactory();
        } catch (Exception unused) {
            return null;
        }
    }

    /* loaded from: classes.dex */
    public static class a implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        private a() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static String a(String str, HttpParams httpParams) {
        StringBuffer stringBuffer = new StringBuffer(str);
        if (httpParams != null) {
            stringBuffer.append("?");
            for (Map.Entry<String, Object> entry : httpParams.entrySet()) {
                stringBuffer.append(entry.getKey());
                stringBuffer.append("=");
                stringBuffer.append(entry.getValue() + "");
                stringBuffer.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            str = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        ALog.e("url:" + str);
        return str;
    }

    private static String a(HttpParams httpParams) {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, Object> entry : httpParams.entrySet()) {
            try {
                jSONObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String jSONObject2 = jSONObject.toString();
        ALog.e("json:" + jSONObject2);
        return jSONObject2;
    }

    private static <T extends Request.Builder> T a(T t, VersionParams versionParams) {
        HttpHeaders httpHeaders = versionParams.getHttpHeaders();
        if (httpHeaders != null) {
            ALog.e("header:");
            for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                ALog.e(key + "=" + value + "\n");
                t.addHeader(key, value);
            }
        }
        return t;
    }

    public static Request.Builder get(VersionParams versionParams) {
        Request.Builder a2 = a(new Request.Builder(), versionParams);
        a2.url(a(versionParams.getRequestUrl(), versionParams.getRequestParams()));
        return a2;
    }

    public static Request.Builder post(VersionParams versionParams) {
        FormBody a2 = a(versionParams);
        Request.Builder a3 = a(new Request.Builder(), versionParams);
        a3.post(a2).url(versionParams.getRequestUrl());
        return a3;
    }

    public static Request.Builder postJson(VersionParams versionParams) {
        RequestBody create = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), a(versionParams.getRequestParams()));
        Request.Builder a2 = a(new Request.Builder(), versionParams);
        a2.post(create).url(versionParams.getRequestUrl());
        return a2;
    }

    private static FormBody a(VersionParams versionParams) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : versionParams.getRequestParams().entrySet()) {
            builder.add(entry.getKey(), entry.getValue() + "");
            ALog.e("params key:" + entry.getKey() + "-----value:" + entry.getValue());
        }
        return builder.build();
    }

    private static <T extends Request.Builder> T a(T t, RequestVersionBuilder requestVersionBuilder) {
        HttpHeaders httpHeaders = requestVersionBuilder.getHttpHeaders();
        if (httpHeaders != null) {
            ALog.e("header:");
            for (Map.Entry<String, String> entry : httpHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                ALog.e(key + "=" + value + "\n");
                t.addHeader(key, value);
            }
        }
        return t;
    }

    public static Request.Builder get(RequestVersionBuilder requestVersionBuilder) {
        Request.Builder a2 = a(new Request.Builder(), requestVersionBuilder);
        a2.url(a(requestVersionBuilder.getRequestUrl(), requestVersionBuilder.getRequestParams()));
        return a2;
    }

    public static Request.Builder post(RequestVersionBuilder requestVersionBuilder) {
        FormBody a2 = a(requestVersionBuilder);
        Request.Builder a3 = a(new Request.Builder(), requestVersionBuilder);
        a3.post(a2).url(requestVersionBuilder.getRequestUrl());
        return a3;
    }

    public static Request.Builder postJson(RequestVersionBuilder requestVersionBuilder) {
        RequestBody create = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), a(requestVersionBuilder.getRequestParams()));
        Request.Builder a2 = a(new Request.Builder(), requestVersionBuilder);
        a2.post(create).url(requestVersionBuilder.getRequestUrl());
        return a2;
    }

    private static FormBody a(RequestVersionBuilder requestVersionBuilder) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : requestVersionBuilder.getRequestParams().entrySet()) {
            builder.add(entry.getKey(), entry.getValue() + "");
            ALog.e("params key:" + entry.getKey() + "-----value:" + entry.getValue());
        }
        return builder.build();
    }
}
