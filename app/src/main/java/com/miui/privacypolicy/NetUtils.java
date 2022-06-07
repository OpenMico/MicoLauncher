package com.miui.privacypolicy;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import kotlin.text.Typography;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NetUtils {
    public static final String BASE_URL = "https://data.sec.miui.com";
    private static final boolean DEBUG = false;
    private static final String ENCODE_UTF_8 = "UTF-8";
    private static final String SALT = "2dcd9s0c-ad3f-2fas-0l3a-abzo301jd0s9";
    private static final String TAG = "Privacy_NetUtil";
    private static final String UNKNOWN_STR = "unknown";
    protected static final String MIUI_VERSION_NAME = getSystemProperty("ro.miui.ui.version.name", "unknown");
    protected static final boolean IS_INTERNATIONAL_BUILD = getSystemProperty("ro.product.mod_device", "").contains("_global");

    /* loaded from: classes2.dex */
    protected enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    private NetUtils() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String request(Map<String, String> map, String str, HttpMethod httpMethod, JSONObject jSONObject) {
        ByteArrayOutputStream byteArrayOutputStream;
        Exception e;
        InputStream inputStream = null;
        try {
            if (httpMethod == HttpMethod.GET && map != null) {
                String encodeParameters = encodeParameters(map);
                if (str.contains("?")) {
                    str = str.concat(encodeParameters);
                } else {
                    str = str.concat("?").concat(encodeParameters);
                }
            }
            HttpURLConnection openConnection = openConnection(new URL(str));
            setConnectionParametersForRequest(openConnection, httpMethod, map, jSONObject);
            if (openConnection.getResponseCode() == 200) {
                InputStream inputStream2 = openConnection.getInputStream();
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = inputStream2.read(bArr);
                            if (read != -1) {
                                byteArrayOutputStream.write(bArr, 0, read);
                            } else {
                                String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                                FileUtils.closeQuietly(inputStream2);
                                FileUtils.closeQuietly(byteArrayOutputStream);
                                return byteArrayOutputStream2;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        inputStream = inputStream2;
                        try {
                            e.printStackTrace();
                            FileUtils.closeQuietly(inputStream);
                            FileUtils.closeQuietly(byteArrayOutputStream);
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            FileUtils.closeQuietly(inputStream);
                            FileUtils.closeQuietly(byteArrayOutputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = inputStream2;
                        FileUtils.closeQuietly(inputStream);
                        FileUtils.closeQuietly(byteArrayOutputStream);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                    inputStream = inputStream2;
                    byteArrayOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    inputStream = inputStream2;
                    byteArrayOutputStream = null;
                }
            } else {
                FileUtils.closeQuietly((InputStream) null);
                FileUtils.closeQuietly((OutputStream) null);
                return "";
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream = null;
        }
    }

    private static HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(15000);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    private static void setConnectionParametersForRequest(HttpURLConnection httpURLConnection, HttpMethod httpMethod, Map<String, String> map, JSONObject jSONObject) throws IOException {
        httpURLConnection.addRequestProperty("sign", getParamsSignature(map, SALT));
        httpURLConnection.addRequestProperty("timestamp", map.get("timestamp"));
        httpURLConnection.addRequestProperty("source", "sdk");
        switch (httpMethod) {
            case GET:
                httpURLConnection.setRequestMethod("GET");
                return;
            case DELETE:
                httpURLConnection.setRequestMethod("DELETE");
                return;
            case POST:
                httpURLConnection.setRequestMethod("POST");
                addBodyIfExists(httpURLConnection, jSONObject);
                return;
            case PUT:
                httpURLConnection.setRequestMethod("PUT");
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void addBodyIfExists(HttpURLConnection httpURLConnection, JSONObject jSONObject) throws IOException {
        String jSONObject2 = jSONObject.toString();
        if (!TextUtils.isEmpty(jSONObject2)) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(jSONObject2);
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    private static String encodeParameters(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (!z) {
                    sb.append(Typography.amp);
                }
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                z = false;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + ((Object) sb), e);
        }
    }

    private static String getParamsSignature(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        Collections.sort(arrayList);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NameValuePair nameValuePair = (NameValuePair) arrayList.get(i);
            sb.append(nameValuePair.name);
            sb.append("=");
            sb.append(nameValuePair.value);
        }
        sb.append(str);
        return getMd5Digest(new String(Base64.encodeToString(getBytes(sb.toString()), 2))).toUpperCase();
    }

    private static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    private static String getMd5Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(getBytes(str));
            return String.format("%1$032X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class NameValuePair implements Comparable<NameValuePair> {
        private String name;
        private String value;

        NameValuePair(String str, String str2) {
            this.name = str;
            this.value = str2;
        }

        public int compareTo(NameValuePair nameValuePair) {
            return this.name.compareTo(nameValuePair.name);
        }
    }

    protected static String getSystemProperty(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(BluetoothConstants.GET, String.class, String.class).invoke(cls, str, str2);
        } catch (Exception e) {
            Log.e(TAG, "getSystemProperty error, ", e);
            return str2;
        }
    }
}
