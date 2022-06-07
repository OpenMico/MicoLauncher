package com.xiaomi.account.auth;

import android.content.Context;
import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes2.dex */
public class AuthorizeApi {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final String METHOD_GET = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final String UTF8 = "UTF-8";
    private static final String HOST = XiaomiOAuthConstants.OAUTH2_API_HOST;
    private static final String OAUTH_URL_BASE = XiaomiOAuthConstants.OAUTH2_API_URL_BASE;

    @Deprecated
    public static String doHttpGet(Context context, String str, long j, String str2) throws XMAuthericationException {
        return doHttpGet(str, j, str2, null, null);
    }

    public static String doHttpGet(String str, long j, String str2) throws XMAuthericationException {
        return doHttpGet(str, j, str2, null, null);
    }

    @Deprecated
    public static String doHttpGet(Context context, String str, long j, String str2, String str3, String str4) throws XMAuthericationException {
        return doHttpGet(str, j, str2, str3, str4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [long] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v9 */
    public static String doHttpGet(String str, long j, String str2, String str3, String str4) throws XMAuthericationException {
        Throwable th;
        UnsupportedEncodingException e;
        IOException e2;
        NoSuchAlgorithmException e3;
        InvalidKeyException e4;
        InputStream inputStream;
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("clientId", String.valueOf((long) j)));
            arrayList.add(new BasicNameValuePair("token", str2));
            try {
                try {
                    URL url = new URL(AuthorizeHelper.generateUrl(OAUTH_URL_BASE + str, arrayList));
                    HttpURLConnection.setFollowRedirects(true);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setReadTimeout(15000);
                    HashMap<String, String> makeHeaders = makeHeaders(str, str2, str3, str4, arrayList);
                    if (makeHeaders != null) {
                        for (String str5 : makeHeaders.keySet()) {
                            httpURLConnection.setRequestProperty(str5, makeHeaders.get(str5));
                        }
                    }
                    httpURLConnection.connect();
                    try {
                        inputStream = httpURLConnection.getInputStream();
                    } catch (IOException unused) {
                        inputStream = httpURLConnection.getErrorStream();
                    }
                    if (inputStream != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String readLine = bufferedReader.readLine();
                                if (readLine != null) {
                                    sb.append(readLine);
                                } else {
                                    String sb2 = sb.toString();
                                    closeQuietly(bufferedReader);
                                    return sb2;
                                }
                            }
                        } catch (UnsupportedEncodingException e5) {
                            e = e5;
                            throw new XMAuthericationException(e);
                        } catch (IOException e6) {
                            e2 = e6;
                            throw new XMAuthericationException(e2);
                        } catch (InvalidKeyException e7) {
                            e4 = e7;
                            throw new XMAuthericationException(e4);
                        } catch (NoSuchAlgorithmException e8) {
                            e3 = e8;
                            throw new XMAuthericationException(e3);
                        } catch (Throwable th2) {
                            th = th2;
                            j = bufferedReader;
                            closeQuietly(j);
                            throw th;
                        }
                    } else {
                        throw new XMAuthericationException(new IOException());
                    }
                } catch (IOException e9) {
                    e2 = e9;
                }
            } catch (UnsupportedEncodingException e10) {
                e = e10;
            } catch (InvalidKeyException e11) {
                e4 = e11;
            } catch (NoSuchAlgorithmException e12) {
                e3 = e12;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static void closeQuietly(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException unused) {
            }
        }
    }

    private static HashMap<String, String> makeHeaders(String str, String str2, String str3, String str4, List<NameValuePair> list) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str3 == null && str4 == null) {
            return null;
        }
        String generateNonce = AuthorizeHelper.generateNonce();
        return AuthorizeHelper.buildMacRequestHead(str2, generateNonce, AuthorizeHelper.getMacAccessTokenSignatureString(generateNonce, "GET", HOST, str, URLEncodedUtils.format(list, "UTF-8"), str3, str4));
    }
}
