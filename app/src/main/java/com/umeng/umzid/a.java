package com.umeng.umzid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: com.umeng.umzid.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static class C0150a implements HostnameVerifier {
        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            if (!TextUtils.isEmpty(str)) {
                return "aaid.umeng.com".equalsIgnoreCase(str) || "pre-aaid.umeng.com".equalsIgnoreCase(str);
            }
            return false;
        }
    }

    public static SharedPreferences a(Context context) {
        if (context != null) {
            return context.getSharedPreferences("umzid_general_config", 0);
        }
        return null;
    }

    public static synchronized String a(String str, String str2) {
        synchronized (a.class) {
            try {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                httpsURLConnection.setHostnameVerifier(new C0150a());
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, null, new SecureRandom());
                httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
                httpsURLConnection.setRequestProperty("Content-Type", "application/json");
                httpsURLConnection.setConnectTimeout(30000);
                httpsURLConnection.setReadTimeout(30000);
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                httpsURLConnection.setDoInput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(str2.getBytes());
                outputStream.flush();
                outputStream.close();
                if (httpsURLConnection.getResponseCode() == 200) {
                    InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        int read = inputStreamReader.read();
                        if (read != -1) {
                            stringBuffer.append((char) read);
                        } else {
                            return stringBuffer.toString();
                        }
                    }
                }
            } catch (Exception unused) {
            }
            return null;
        }
    }
}
