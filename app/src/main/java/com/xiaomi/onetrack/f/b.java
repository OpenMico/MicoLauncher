package com.xiaomi.onetrack.f;

import android.text.TextUtils;
import com.xiaomi.onetrack.c.d;
import com.xiaomi.onetrack.c.f;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.m;
import com.xiaomi.onetrack.util.p;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {
    public static final int a = 10000;
    public static final int b = 15000;
    public static final String c = "OT_SID";
    public static final String d = "OT_ts";
    public static final String e = "OT_net";
    public static final String f = "OT_sender";
    public static final String g = "OT_protocol";
    private static String h = "HttpUtil";
    private static final String i = "GET";
    private static final String j = "POST";
    private static final String k = "&";
    private static final String l = "=";
    private static final String m = "UTF-8";
    private static final String n = "miui_sdkconfig_jafej!@#)(*e@!#";

    private b() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String a(String str, byte[] bArr) throws IOException {
        Throwable th;
        OutputStream outputStream;
        HttpURLConnection httpURLConnection;
        IOException e2;
        InputStream inputStream;
        p.a(h, "doPost url=" + str + ", len=" + bArr.length);
        InputStream inputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        } catch (IOException e3) {
            e2 = e3;
            httpURLConnection = null;
            outputStream = null;
        } catch (Throwable th2) {
            th = th2;
            httpURLConnection = null;
            outputStream = 0;
        }
        try {
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
            String str2 = f.a().b()[1];
            httpURLConnection.setRequestProperty(c, str2);
            httpURLConnection.setRequestProperty(d, Long.toString(System.currentTimeMillis()));
            httpURLConnection.setRequestProperty(e, c.a(a.a()).toString());
            httpURLConnection.setRequestProperty(f, a.d());
            httpURLConnection.setRequestProperty(g, "3.0");
            p.a(h, "sid:" + str2);
            outputStream = httpURLConnection.getOutputStream();
        } catch (IOException e4) {
            e2 = e4;
            outputStream = 0;
            inputStream = outputStream;
            p.b(h, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e2.getMessage()));
            m.a(inputStream);
            m.a(outputStream);
            m.a(httpURLConnection);
            return null;
        } catch (Throwable th3) {
            th = th3;
            outputStream = 0;
        }
        try {
            outputStream.write(bArr, 0, bArr.length);
            outputStream.flush();
            int responseCode = httpURLConnection.getResponseCode();
            inputStream = httpURLConnection.getInputStream();
            try {
                try {
                    byte[] b2 = m.b(inputStream);
                    p.a(h, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str, Integer.valueOf(responseCode)));
                    String str3 = new String(b2, "UTF-8");
                    m.a(inputStream);
                    m.a(outputStream);
                    m.a(httpURLConnection);
                    return str3;
                } catch (IOException e5) {
                    e2 = e5;
                    p.b(h, String.format("HttpUtils POST 上传失败, url: %s, error: %s", str, e2.getMessage()));
                    m.a(inputStream);
                    m.a(outputStream);
                    m.a(httpURLConnection);
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                inputStream2 = inputStream;
                m.a(inputStream2);
                m.a(outputStream);
                m.a(httpURLConnection);
                throw th;
            }
        } catch (IOException e6) {
            e2 = e6;
            inputStream = null;
        } catch (Throwable th5) {
            th = th5;
            m.a(inputStream2);
            m.a(outputStream);
            m.a(httpURLConnection);
            throw th;
        }
    }

    public static String a(String str) throws IOException {
        return a(str, null, false);
    }

    public static String a(String str, Map<String, String> map) throws IOException {
        return a(str, map, true);
    }

    public static String a(String str, Map<String, String> map, boolean z) throws IOException {
        return a("GET", str, map, z);
    }

    public static String b(String str, Map<String, String> map) throws IOException {
        return b(str, map, true);
    }

    public static String b(String str, Map<String, String> map, boolean z) throws IOException {
        return a("POST", str, map, z);
    }

    private static String a(String str, String str2, Map<String, String> map, boolean z) {
        String a2;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        Throwable th;
        Exception e2;
        InputStream inputStream;
        String str3;
        InputStream inputStream2 = null;
        if (map == null) {
            a2 = null;
        } else {
            try {
                a2 = a(map, z);
            } catch (Exception e3) {
                e2 = e3;
                outputStream = null;
                httpURLConnection = null;
                inputStream = null;
                p.b(h, "HttpUtils POST 上传异常", e2);
                m.a(inputStream);
                m.a(outputStream);
                m.a(httpURLConnection);
                return null;
            } catch (Throwable th2) {
                th = th2;
                outputStream = null;
                httpURLConnection = null;
                m.a(inputStream2);
                m.a(outputStream);
                m.a(httpURLConnection);
                throw th;
            }
        }
        if (!"GET".equals(str) || a2 == null) {
            str3 = str2;
        } else {
            str3 = str2 + "? " + a2;
        }
        httpURLConnection = (HttpURLConnection) new URL(str3).openConnection();
        try {
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            try {
                try {
                    if ("GET".equals(str)) {
                        httpURLConnection.setRequestMethod("GET");
                    } else if ("POST".equals(str) && a2 != null) {
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        httpURLConnection.setDoOutput(true);
                        byte[] bytes = a2.getBytes("UTF-8");
                        outputStream = httpURLConnection.getOutputStream();
                        try {
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.flush();
                            int responseCode = httpURLConnection.getResponseCode();
                            inputStream = httpURLConnection.getInputStream();
                            byte[] b2 = m.b(inputStream);
                            p.a(h, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                            String str4 = new String(b2, "UTF-8");
                            m.a(inputStream);
                            m.a(outputStream);
                            m.a(httpURLConnection);
                            return str4;
                        } catch (Exception e4) {
                            e2 = e4;
                            inputStream = null;
                            p.b(h, "HttpUtils POST 上传异常", e2);
                            m.a(inputStream);
                            m.a(outputStream);
                            m.a(httpURLConnection);
                            return null;
                        } catch (Throwable th3) {
                            th = th3;
                            m.a(inputStream2);
                            m.a(outputStream);
                            m.a(httpURLConnection);
                            throw th;
                        }
                    }
                    byte[] b22 = m.b(inputStream);
                    p.a(h, String.format("HttpUtils POST 上传成功 url: %s, code: %s", str2, Integer.valueOf(responseCode)));
                    String str42 = new String(b22, "UTF-8");
                    m.a(inputStream);
                    m.a(outputStream);
                    m.a(httpURLConnection);
                    return str42;
                } catch (Exception e5) {
                    e2 = e5;
                    p.b(h, "HttpUtils POST 上传异常", e2);
                    m.a(inputStream);
                    m.a(outputStream);
                    m.a(httpURLConnection);
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                inputStream2 = inputStream;
                m.a(inputStream2);
                m.a(outputStream);
                m.a(httpURLConnection);
                throw th;
            }
            outputStream = null;
            int responseCode2 = httpURLConnection.getResponseCode();
            inputStream = httpURLConnection.getInputStream();
        } catch (Exception e6) {
            e2 = e6;
            outputStream = null;
            inputStream = null;
        } catch (Throwable th5) {
            th = th5;
            outputStream = null;
        }
    }

    private static String a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    sb.append(l);
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : entry.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException unused) {
                p.b(h, "format params failed");
            }
        }
        if (z) {
            String a2 = a(map);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode("sign", "UTF-8"));
            sb.append(l);
            sb.append(URLEncoder.encode(a2, "UTF-8"));
        }
        return sb.toString();
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append(map.get(str));
                }
            }
        }
        sb.append(n);
        return d.c(sb.toString());
    }
}
