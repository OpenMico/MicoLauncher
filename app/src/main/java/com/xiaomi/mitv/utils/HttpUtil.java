package com.xiaomi.mitv.utils;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mitv.exception.MitvCommonException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes4.dex */
public class HttpUtil {
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final int CONNECT_TIMEOUT = 5000;
    public static final String DELETE_METHOD = "DELETE";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    private static final int READ_TIMEOUT = 10000;

    public static String doGet(String str) throws MitvCommonException {
        return doGetRequest(createConnection(str), str);
    }

    public static String doPost(String str, String str2) throws Exception {
        HttpURLConnection createConnection;
        try {
            createConnection = createConnection(str);
            try {
                createConnection.setRequestMethod("POST");
                createConnection.setDoOutput(true);
                createConnection.setDoInput(true);
                createConnection.setUseCaches(false);
                createConnection.connect();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(createConnection.getOutputStream(), "UTF-8"));
                bufferedWriter.write(str2);
                bufferedWriter.close();
                return handleResponse(createConnection, str, "POST", str2);
            } catch (Exception e) {
                throw e;
            }
        } finally {
            closeConnection(createConnection);
        }
    }

    public static String doMethodRequest(String str, Map<String, Object> map, String str2) throws Exception {
        HttpURLConnection createConnection = createConnection(str);
        try {
            try {
                createConnection.setRequestMethod(str2);
                int i = 1;
                createConnection.setDoOutput(true);
                createConnection.setDoInput(true);
                createConnection.setUseCaches(false);
                createConnection.connect();
                StringBuilder sb = new StringBuilder();
                if (map != null && !map.isEmpty()) {
                    Set<String> keySet = map.keySet();
                    for (String str3 : keySet) {
                        sb.append(str3);
                        sb.append("=");
                        sb.append(map.get(str3));
                        if (i != keySet.size()) {
                            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                        }
                        i++;
                    }
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(createConnection.getOutputStream(), "UTF-8"));
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.close();
                }
                return handleResponse(createConnection, str, str2, sb.toString());
            } catch (Exception e) {
                throw e;
            }
        } finally {
            closeConnection(createConnection);
        }
    }

    public static String doHttpsGetAllowAllSSL(String str) throws MitvCommonException {
        return doGetRequest(createHttpsConnection(str), str);
    }

    private static void closeConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    private static String doGetRequest(HttpURLConnection httpURLConnection, String str) throws MitvCommonException {
        try {
            try {
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                return handleResponse(httpURLConnection, str, "GET", null);
            } catch (Exception e) {
                throw new MitvCommonException(e);
            }
        } finally {
            closeConnection(httpURLConnection);
        }
    }

    private static String handleResponse(HttpURLConnection httpURLConnection, String str, String str2, String str3) throws IOException, MitvCommonException {
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            return io2String(httpURLConnection.getInputStream());
        }
        String io2String = io2String(httpURLConnection.getErrorStream());
        StringBuilder sb = new StringBuilder();
        sb.append("http request fail");
        sb.append(System.lineSeparator());
        sb.append("response code:");
        sb.append(responseCode);
        sb.append(System.lineSeparator());
        sb.append("url:");
        sb.append(str);
        sb.append(System.lineSeparator());
        sb.append("method:");
        sb.append(str2);
        sb.append(System.lineSeparator());
        if (str3 != null && !str3.isEmpty()) {
            sb.append("param:");
            sb.append(str3);
            sb.append(System.lineSeparator());
        }
        sb.append("response error message:");
        sb.append(io2String);
        throw new MitvCommonException(sb.toString());
    }

    private static HttpsURLConnection createHttpsConnection(String str) throws MitvCommonException {
        HttpsURLConnection httpsURLConnection;
        try {
            HTTPSTrustManager.allowAllSSL();
            try {
                httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
            } catch (IOException unused) {
                closeConnection(null);
                httpsURLConnection = null;
            }
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(10000);
            return httpsURLConnection;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new MitvCommonException(e);
        }
    }

    private static HttpURLConnection createConnection(String str) throws MitvCommonException {
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                closeConnection(httpURLConnection);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(10000);
                return httpURLConnection;
            } catch (IOException e) {
                throw new MitvCommonException(e);
            }
        } catch (Throwable th) {
            closeConnection(null);
            throw th;
        }
    }

    private static String io2String(InputStream inputStream) throws IOException {
        Throwable th;
        try {
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuffer.append(readLine);
                }
                String stringBuffer2 = stringBuffer.toString();
                try {
                    bufferedReader.close();
                } catch (IOException unused) {
                }
                return stringBuffer2;
            } catch (IOException e) {
                throw e;
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (IOException e2) {
            throw e2;
        }
    }
}
