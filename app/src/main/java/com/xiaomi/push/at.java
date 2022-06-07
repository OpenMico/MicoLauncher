package com.xiaomi.push;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class at {
    public static final Pattern a = Pattern.compile("([^\\s;]+)(.*)");
    public static final Pattern b = Pattern.compile("(.*?charset\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);
    public static final Pattern c = Pattern.compile("(\\<\\?xml\\s+.*?encoding\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    /* loaded from: classes4.dex */
    public static final class a extends FilterInputStream {
        private boolean a;

        public a(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            int read;
            if (!this.a && (read = super.read(bArr, i, i2)) != -1) {
                return read;
            }
            this.a = true;
            return -1;
        }
    }

    /* loaded from: classes4.dex */
    public static class b {
        public int a;

        /* renamed from: a  reason: collision with other field name */
        public Map<String, String> f10a;

        public String toString() {
            return String.format("resCode = %1$d, headers = %2$s", Integer.valueOf(this.a), this.f10a.toString());
        }
    }

    public static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return -1;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return -1;
            }
            return activeNetworkInfo.getType();
        } catch (Exception unused) {
            return -1;
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static NetworkInfo m755a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v4 */
    public static ar a(Context context, String str, String str2, Map<String, String> map, String str3) {
        Throwable th;
        IOException e;
        Throwable th2;
        BufferedReader bufferedReader;
        try {
            ar arVar = new ar();
            OutputStream outputStream = null;
            try {
                try {
                    HttpURLConnection a2 = m757a(context, b(str));
                    a2.setConnectTimeout(10000);
                    a2.setReadTimeout(15000);
                    String str4 = str2;
                    if (str2 == 0) {
                        str4 = "GET";
                    }
                    a2.setRequestMethod(str4);
                    if (map != null) {
                        for (String str5 : map.keySet()) {
                            a2.setRequestProperty(str5, map.get(str5));
                        }
                    }
                    int i = 0;
                    if (!TextUtils.isEmpty(str3)) {
                        a2.setDoOutput(true);
                        byte[] bytes = str3.getBytes();
                        OutputStream outputStream2 = a2.getOutputStream();
                        try {
                            outputStream2.write(bytes, 0, bytes.length);
                            outputStream2.flush();
                            outputStream2.close();
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("err while request " + str + Constants.COLON_SEPARATOR + e.getClass().getSimpleName());
                        } catch (Throwable th3) {
                            th2 = th3;
                            throw new IOException(th2.getMessage());
                        }
                    }
                    arVar.a = a2.getResponseCode();
                    Log.d("com.xiaomi.common.Network", "Http POST Response Code: " + arVar.a);
                    while (true) {
                        String headerFieldKey = a2.getHeaderFieldKey(i);
                        String headerField = a2.getHeaderField(i);
                        if (headerFieldKey == null && headerField == null) {
                            try {
                                break;
                            } catch (IOException unused) {
                                bufferedReader = new BufferedReader(new InputStreamReader(new a(a2.getErrorStream())));
                            }
                        } else {
                            arVar.f9a.put(headerFieldKey, headerField);
                            i = i + 1 + 1;
                        }
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(new a(a2.getInputStream())));
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th4) {
                th2 = th4;
            }
            try {
                StringBuffer stringBuffer = new StringBuffer();
                String property = System.getProperty("line.separator");
                for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                    stringBuffer.append(readLine);
                    stringBuffer.append(property);
                }
                arVar.f8a = stringBuffer.toString();
                bufferedReader.close();
                z.a((Closeable) null);
                z.a((Closeable) null);
                return arVar;
            } catch (IOException e4) {
                e = e4;
                throw new IOException("err while request " + str + Constants.COLON_SEPARATOR + e.getClass().getSimpleName());
            } catch (Throwable th5) {
                th2 = th5;
                throw new IOException(th2.getMessage());
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public static ar a(Context context, String str, Map<String, String> map) {
        return a(context, str, "POST", (Map<String, String>) null, a(map));
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2) {
        return a(context, url, z, str, str2, null, null);
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2, Map<String, String> map, b bVar) {
        if (context == null) {
            throw new IllegalArgumentException(c.R);
        } else if (url != null) {
            URL url2 = !z ? new URL(a(url.toString())) : url;
            try {
                HttpURLConnection.setFollowRedirects(true);
                HttpURLConnection a2 = m757a(context, url2);
                a2.setConnectTimeout(10000);
                a2.setReadTimeout(15000);
                if (!TextUtils.isEmpty(str)) {
                    a2.setRequestProperty("User-Agent", str);
                }
                if (str2 != null) {
                    a2.setRequestProperty("Cookie", str2);
                }
                if (map != null) {
                    for (String str3 : map.keySet()) {
                        a2.setRequestProperty(str3, map.get(str3));
                    }
                }
                if (bVar != null && (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
                    bVar.a = a2.getResponseCode();
                    if (bVar.f10a == null) {
                        bVar.f10a = new HashMap();
                    }
                    int i = 0;
                    while (true) {
                        String headerFieldKey = a2.getHeaderFieldKey(i);
                        String headerField = a2.getHeaderField(i);
                        if (headerFieldKey == null && headerField == null) {
                            break;
                        }
                        if (!TextUtils.isEmpty(headerFieldKey) && !TextUtils.isEmpty(headerField)) {
                            bVar.f10a.put(headerFieldKey, headerField);
                        }
                        i++;
                    }
                }
                return new a(a2.getInputStream());
            } catch (IOException e) {
                throw new IOException("IOException:" + e.getClass().getSimpleName());
            } catch (Throwable th) {
                throw new IOException(th.getMessage());
            }
        } else {
            throw new IllegalArgumentException("url");
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static String m756a(Context context) {
        if (d(context)) {
            return Network.NETWORK_TYPE_WIFI;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return "";
                }
                return (activeNetworkInfo.getTypeName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + activeNetworkInfo.getSubtypeName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + activeNetworkInfo.getExtraInfo()).toLowerCase();
            } catch (Exception unused) {
                return "";
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    public static String a(Context context, URL url) {
        return a(context, url, false, null, "UTF-8", null);
    }

    public static String a(Context context, URL url, boolean z, String str, String str2, String str3) {
        Throwable th;
        InputStream inputStream;
        try {
            inputStream = a(context, url, z, str, str3);
            try {
                StringBuilder sb = new StringBuilder(1024);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str2));
                char[] cArr = new char[4096];
                while (true) {
                    int read = bufferedReader.read(cArr);
                    if (-1 != read) {
                        sb.append(cArr, 0, read);
                    } else {
                        z.a(inputStream);
                        return sb.toString();
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                z.a(inputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new String();
        return String.format("%s&key=%s", str, ay.a(String.format("%sbe988a6134bc8254465424e5a70ef037", str)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r7v16, types: [java.io.Closeable, java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    public static String a(String str, Map<String, String> map, File file, String str2) {
        Throwable th;
        IOException e;
        Throwable th2;
        FileInputStream fileInputStream = null;
        if (!file.exists()) {
            return null;
        }
        try {
            String name = file.getName();
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setFixedLengthStreamingMode(name.length() + 77 + ((int) file.length()) + str2.length());
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes("--*****\r\n");
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\";filename=\"" + file.getName() + "\"\r\n");
                dataOutputStream.writeBytes("\r\n");
                FileInputStream fileInputStream2 = new FileInputStream((File) file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream2.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        dataOutputStream.write(bArr, 0, read);
                        dataOutputStream.flush();
                    }
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes("*****");
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.flush();
                    StringBuffer stringBuffer = new StringBuffer();
                    file = new BufferedReader(new InputStreamReader(new a(httpURLConnection.getInputStream())));
                    while (true) {
                        try {
                            String readLine = file.readLine();
                            if (readLine != null) {
                                stringBuffer.append(readLine);
                            } else {
                                String stringBuffer2 = stringBuffer.toString();
                                z.a(fileInputStream2);
                                z.a((Closeable) file);
                                return stringBuffer2;
                            }
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("IOException:" + e.getClass().getSimpleName());
                        } catch (Throwable th3) {
                            th = th3;
                            fileInputStream = fileInputStream2;
                            z.a(fileInputStream);
                            z.a((Closeable) file);
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (Throwable th4) {
                    th2 = th4;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (Throwable th5) {
                th2 = th5;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!(entry.getKey() == null || entry.getValue() == null)) {
                try {
                    stringBuffer.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    stringBuffer.append("=");
                    stringBuffer.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    stringBuffer.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                } catch (UnsupportedEncodingException e) {
                    Log.d("com.xiaomi.common.Network", "Failed to convert from params map to string: " + e.toString());
                    Log.d("com.xiaomi.common.Network", "map: " + map.toString());
                    return null;
                }
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    /* renamed from: a  reason: collision with other method in class */
    public static HttpURLConnection m757a(Context context, URL url) {
        return (HttpURLConnection) (("http".equals(url.getProtocol()) && m758a(context)) ? url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80))) : url.openConnection());
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m758a(Context context) {
        ConnectivityManager connectivityManager;
        if (!"CN".equalsIgnoreCase(((TelephonyManager) context.getSystemService("phone")).getSimCountryIso())) {
            return false;
        }
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception unused) {
        }
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        String extraInfo = activeNetworkInfo.getExtraInfo();
        return !TextUtils.isEmpty(extraInfo) && extraInfo.length() >= 3 && extraInfo.contains("ctwap");
    }

    private static URL b(String str) {
        return new URL(str);
    }

    public static boolean b(Context context) {
        return a(context) >= 0;
    }

    public static boolean c(Context context) {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            networkInfo = null;
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean d(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && 1 == activeNetworkInfo.getType();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean e(Context context) {
        return f(context) || g(context) || h(context);
    }

    public static boolean f(Context context) {
        NetworkInfo a2 = m755a(context);
        return a2 != null && a2.getType() == 0 && 13 == a2.getSubtype();
    }

    public static boolean g(Context context) {
        NetworkInfo a2 = m755a(context);
        if (a2 == null || a2.getType() != 0) {
            return false;
        }
        String subtypeName = a2.getSubtypeName();
        if (!"TD-SCDMA".equalsIgnoreCase(subtypeName) && !"CDMA2000".equalsIgnoreCase(subtypeName) && !"WCDMA".equalsIgnoreCase(subtypeName)) {
            switch (a2.getSubtype()) {
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    break;
                case 4:
                case 7:
                case 11:
                case 13:
                default:
                    return false;
            }
        }
        return true;
    }

    public static boolean h(Context context) {
        NetworkInfo a2 = m755a(context);
        if (a2 == null || a2.getType() != 0) {
            return false;
        }
        int subtype = a2.getSubtype();
        if (subtype == 4 || subtype == 7 || subtype == 11) {
            return true;
        }
        switch (subtype) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }
}
