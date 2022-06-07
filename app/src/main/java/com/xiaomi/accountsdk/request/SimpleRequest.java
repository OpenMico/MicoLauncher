package com.xiaomi.accountsdk.request;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.request.intercept.NetworkInterceptor;
import com.xiaomi.accountsdk.utils.DiagnosisLog;
import com.xiaomi.accountsdk.utils.DiagnosisLogInterface;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.IOUtils;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import com.xiaomi.accountsdk.utils.ServerTimeUtil;
import com.xiaomi.accountsdk.utils.VersionUtils;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.setting.LoginSetting;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
/* loaded from: classes2.dex */
public final class SimpleRequest {
    private static final boolean DEBUG = false;
    public static final String HEADER_KEY_USER_AGENT = "User-Agent";
    public static final String LOCATION = "Location";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final String PARAMETER_SEPARATOR = "&";
    private static final int TIMEOUT = 30000;
    public static final String UTF8 = "utf-8";
    private static ConnectivityListener sConnectivityListener;
    private static final Logger log = Logger.getLogger(SimpleRequest.class.getSimpleName());
    private static HttpURLConnectionFactory sHttpURLConnectionFactory = new HttpURLConnectionFactory() { // from class: com.xiaomi.accountsdk.request.SimpleRequest.1
        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HttpURLConnectionFactory
        public HttpURLConnection makeConn(URL url) throws IOException {
            if (SimpleRequest.sConnectivityListener != null) {
                SimpleRequest.sConnectivityListener.onOpenUrlConnection(url);
            }
            return (HttpURLConnection) url.openConnection();
        }
    };
    private static RequestLoggerForTest sRequestLoggerForTest = null;

    /* loaded from: classes2.dex */
    public interface HttpURLConnectionFactory {
        HttpURLConnection makeConn(URL url) throws IOException;
    }

    /* loaded from: classes2.dex */
    public interface RequestLoggerForTest {
        void log(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num, Map<String, String> map4);
    }

    public static void setConnectivityListener(ConnectivityListener connectivityListener) {
        sConnectivityListener = connectivityListener;
    }

    static void injectHttpURLConnectionFactoryForTest(HttpURLConnectionFactory httpURLConnectionFactory) {
        sHttpURLConnectionFactory = httpURLConnectionFactory;
    }

    static HttpURLConnectionFactory getHttpURLConnectionFactoryForTest() {
        return sHttpURLConnectionFactory;
    }

    public static void setRequestLoggerForTest(RequestLoggerForTest requestLoggerForTest) {
        sRequestLoggerForTest = requestLoggerForTest;
    }

    public static void resetRequestLoggerForTest() {
        setRequestLoggerForTest(null);
    }

    private static String appendUrl(String str, Map<String, String> map) {
        if (str != null) {
            Uri.Builder buildUpon = Uri.parse(str).buildUpon();
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    buildUpon.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            return buildUpon.build().toString();
        }
        throw new NullPointerException("origin is not allowed null");
    }

    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return getAsString(str, map, null, map2, z, null);
    }

    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return getAsString(str, map, map2, map3, z, null);
    }

    /* JADX WARN: Finally extract failed */
    public static StringContent getAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws IOException, AccessDeniedException, AuthenticationFailureException {
        Exception e;
        int responseCode;
        String headerField;
        RequestLoggerForTest requestLoggerForTest = sRequestLoggerForTest;
        if (requestLoggerForTest != null) {
            requestLoggerForTest.log(str, map, map2, map3, z, num, null);
        }
        String appendUrl = appendUrl(str, map);
        String logGetRequest = getDiagnosisLogger().logGetRequest(str, map, appendUrl, map2, map3);
        long currentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(logGetRequest, "GET", str);
        HttpURLConnection makeConn = makeConn(appendUrl, map3, map2, num);
        if (makeConn != null) {
            Integer num2 = null;
            try {
                try {
                    makeConn.setDoInput(true);
                    makeConn.setRequestMethod("GET");
                    makeConn.connect();
                    responseCode = makeConn.getResponseCode();
                } catch (Exception e2) {
                    e = e2;
                }
                try {
                    getDiagnosisLogger().logResponseCode(logGetRequest, responseCode);
                    if (!(ServerTimeUtil.getComputer() == null || (headerField = makeConn.getHeaderField("Date")) == null)) {
                        ServerTimeUtil.getComputer().alignWithServerDateHeader(str, headerField);
                    }
                    if (!(responseCode == 200 || responseCode == 302)) {
                        if (responseCode == 403) {
                            throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                        } else if (responseCode == 401 || responseCode == 400) {
                            AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for get, code: " + responseCode);
                            authenticationFailureException.setWwwAuthenticateHeader(makeConn.getHeaderField("WWW-Authenticate"));
                            authenticationFailureException.setCaDisableSecondsHeader(makeConn.getHeaderField("CA-DISABLE-SECONDS"));
                            throw authenticationFailureException;
                        } else {
                            Logger logger = log;
                            logger.info("http status error when GET: " + responseCode);
                            if (responseCode == 301) {
                                Logger logger2 = log;
                                logger2.info("unexpected redirect from " + makeConn.getURL().getHost() + " to " + makeConn.getHeaderField("Location"));
                            }
                            throw new IOException("unexpected http res code: " + responseCode);
                        }
                    }
                    Map<String, List<String>> headerFields = makeConn.getHeaderFields();
                    CookieManager cookieManager = new CookieManager();
                    URI create = URI.create(appendUrl);
                    cookieManager.put(create, headerFields);
                    Map<String, String> parseCookies = parseCookies(cookieManager.getCookieStore().get(create));
                    StringBuilder sb = new StringBuilder();
                    if (z) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(makeConn.getInputStream()), 1024);
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            } catch (Throwable th) {
                                IOUtils.closeQuietly(bufferedReader);
                                throw th;
                            }
                        }
                        IOUtils.closeQuietly(bufferedReader);
                    }
                    String sb2 = sb.toString();
                    NetworkInterceptor.get().onSuccess(logGetRequest, "GET", str, currentTimeMillis, responseCode, sb2.length());
                    StringContent stringContent = new StringContent(sb2);
                    stringContent.putCookies(parseCookies);
                    stringContent.putHeaders(ObjectUtils.listToMap(headerFields));
                    stringContent.setHttpCode(responseCode);
                    getDiagnosisLogger().logResponse(logGetRequest, sb2, headerFields, parseCookies);
                    return stringContent;
                } catch (Exception e3) {
                    e = e3;
                    num2 = Integer.valueOf(responseCode);
                    NetworkInterceptor.get().onException(logGetRequest, "GET", str, currentTimeMillis, e, num2);
                    getDiagnosisLogger().logRequestException(e);
                    throw e;
                }
            } finally {
                makeConn.disconnect();
            }
        } else {
            log.severe("failed to create URLConnection");
            throw new IOException("failed to create connection");
        }
    }

    private static DiagnosisLogInterface getDiagnosisLogger() {
        return DiagnosisLog.get();
    }

    public static StreamContent getAsStream(String str, Map<String, String> map, Map<String, String> map2) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return getAsStream(str, map, map2, null);
    }

    public static StreamContent getAsStream(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3) throws IOException, AccessDeniedException, AuthenticationFailureException {
        Integer num;
        Exception e;
        int responseCode;
        String appendUrl = appendUrl(str, map);
        String logGetRequest = getDiagnosisLogger().logGetRequest(str, map, appendUrl, map3, map2);
        long currentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(logGetRequest, "GET", str);
        HttpURLConnection makeConn = makeConn(appendUrl, map2, map3, null);
        if (makeConn != null) {
            try {
                try {
                    makeConn.setDoInput(true);
                    makeConn.setRequestMethod("GET");
                    makeConn.setInstanceFollowRedirects(true);
                    makeConn.connect();
                    responseCode = makeConn.getResponseCode();
                } catch (ProtocolException unused) {
                    throw new IOException("protocol error");
                }
            } catch (Exception e2) {
                e = e2;
                num = null;
            }
            try {
                getDiagnosisLogger().logResponseCode(logGetRequest, responseCode);
                if (responseCode == 200) {
                    Map<String, List<String>> headerFields = makeConn.getHeaderFields();
                    CookieManager cookieManager = new CookieManager();
                    URI create = URI.create(appendUrl);
                    cookieManager.put(create, headerFields);
                    Map<String, String> parseCookies = parseCookies(cookieManager.getCookieStore().get(create));
                    parseCookies.putAll(ObjectUtils.listToMap(headerFields));
                    StreamContent streamContent = new StreamContent(makeConn.getInputStream());
                    streamContent.putHeaders(parseCookies);
                    NetworkInterceptor.get().onSuccess(logGetRequest, "GET", str, currentTimeMillis, responseCode, 0);
                    return streamContent;
                } else if (responseCode == 403) {
                    throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                } else if (responseCode == 401 || responseCode == 400) {
                    AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for get, code: " + responseCode);
                    authenticationFailureException.setWwwAuthenticateHeader(makeConn.getHeaderField("WWW-Authenticate"));
                    authenticationFailureException.setCaDisableSecondsHeader(makeConn.getHeaderField("CA-DISABLE-SECONDS"));
                    throw authenticationFailureException;
                } else {
                    log.info("http status error when GET: " + responseCode);
                    if (responseCode == 301) {
                        log.info("unexpected redirect from " + makeConn.getURL().getHost() + " to " + makeConn.getHeaderField("Location"));
                    }
                    throw new IOException("unexpected http res code: " + responseCode);
                }
            } catch (Exception e3) {
                e = e3;
                num = Integer.valueOf(responseCode);
                NetworkInterceptor.get().onException(logGetRequest, "GET", str, currentTimeMillis, e, num);
                getDiagnosisLogger().logRequestException(e);
                throw e;
            }
        } else {
            log.severe("failed to create URLConnection");
            throw new IOException("failed to create connection");
        }
    }

    public static MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return getAsMap(str, map, map2, null, z);
    }

    public static MapContent getAsMap(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return convertStringToMap(getAsString(str, map, map3, map2, z));
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return postAsString(str, map, map2, null, null, z, null);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z, Integer num) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, null, z, num);
    }

    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return postAsString(str, map, map2, map3, map4, z, null);
    }

    /* JADX WARN: Finally extract failed */
    public static StringContent postAsString(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4, boolean z, Integer num) throws IOException, AccessDeniedException, AuthenticationFailureException {
        String str2;
        Integer num2;
        Exception e;
        RequestLoggerForTest requestLoggerForTest = sRequestLoggerForTest;
        if (requestLoggerForTest != null) {
            requestLoggerForTest.log(str, map, map3, map2, z, num, map4);
        }
        if (map4 != null) {
            str2 = appendUrl(str, map4);
        } else {
            str2 = str;
        }
        String logPostRequest = getDiagnosisLogger().logPostRequest(str, map4, str2, map, map2, map3);
        long currentTimeMillis = System.currentTimeMillis();
        NetworkInterceptor.get().onBegin(logPostRequest, "POST", str);
        HttpURLConnection makeConn = makeConn(str2, map2, map3, num);
        if (makeConn != null) {
            try {
                try {
                    try {
                        makeConn.setDoInput(true);
                        makeConn.setDoOutput(true);
                        makeConn.setRequestMethod("POST");
                        makeConn.connect();
                        if (map != null && !map.isEmpty()) {
                            String encodeFormatAndJoinMap = encodeFormatAndJoinMap(map, "&");
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(makeConn.getOutputStream());
                            try {
                                bufferedOutputStream.write(encodeFormatAndJoinMap.getBytes("utf-8"));
                                IOUtils.closeQuietly(bufferedOutputStream);
                            } catch (Throwable th) {
                                IOUtils.closeQuietly(bufferedOutputStream);
                                throw th;
                            }
                        }
                        int responseCode = makeConn.getResponseCode();
                        Integer valueOf = Integer.valueOf(responseCode);
                        try {
                            getDiagnosisLogger().logResponseCode(logPostRequest, responseCode);
                            if (!(responseCode == 200 || responseCode == 302)) {
                                if (responseCode == 403) {
                                    throw new AccessDeniedException(responseCode, "access denied, encrypt error or user is forbidden to access the resource");
                                } else if (responseCode == 401 || responseCode == 400) {
                                    AuthenticationFailureException authenticationFailureException = new AuthenticationFailureException(responseCode, "authentication failure for post, code: " + responseCode);
                                    authenticationFailureException.setWwwAuthenticateHeader(makeConn.getHeaderField("WWW-Authenticate"));
                                    authenticationFailureException.setCaDisableSecondsHeader(makeConn.getHeaderField("CA-DISABLE-SECONDS"));
                                    throw authenticationFailureException;
                                } else {
                                    log.info("http status error when POST: " + responseCode);
                                    if (responseCode == 301) {
                                        log.info("unexpected redirect from " + makeConn.getURL().getHost() + " to " + makeConn.getHeaderField("Location"));
                                    }
                                    throw new IOException("unexpected http res code: " + responseCode);
                                }
                            }
                            Map<String, List<String>> headerFields = makeConn.getHeaderFields();
                            URI create = URI.create(str2);
                            String host = create.getHost();
                            final HashSet hashSet = new HashSet();
                            hashSet.add(host);
                            if (map3 != null && map3.containsKey(b.E)) {
                                hashSet.add(map3.get(b.E));
                            }
                            if (hashSet.contains("c.id.mi.com")) {
                                hashSet.add(LoginSetting.COOKIE_DOMAIN_PASSPORT_API);
                            }
                            CookieManager cookieManager = new CookieManager(null, new CookiePolicy() { // from class: com.xiaomi.accountsdk.request.SimpleRequest.2
                                @Override // java.net.CookiePolicy
                                public boolean shouldAccept(URI uri, HttpCookie httpCookie) {
                                    String domain = httpCookie.getDomain();
                                    Iterator it = hashSet.iterator();
                                    while (it.hasNext()) {
                                        if (HttpCookie.domainMatches(domain, (String) it.next())) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }
                            });
                            cookieManager.put(create, headerFields);
                            HashMap hashMap = new HashMap();
                            CookieStore cookieStore = cookieManager.getCookieStore();
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                Map<String, String> parseCookies = parseCookies(cookieStore.get(URI.create(str2.replaceFirst(host, (String) it.next()))));
                                if (parseCookies != null) {
                                    hashMap.putAll(parseCookies);
                                }
                            }
                            StringBuilder sb = new StringBuilder();
                            if (z) {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(makeConn.getInputStream()), 1024);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        sb.append(readLine);
                                    } catch (Throwable th2) {
                                        IOUtils.closeQuietly(bufferedReader);
                                        throw th2;
                                    }
                                }
                                IOUtils.closeQuietly(bufferedReader);
                            }
                            String sb2 = sb.toString();
                            NetworkInterceptor.get().onSuccess(logPostRequest, "POST", str, currentTimeMillis, responseCode, sb2.length());
                            StringContent stringContent = new StringContent(sb2);
                            stringContent.putCookies(hashMap);
                            stringContent.setHttpCode(responseCode);
                            stringContent.putHeaders(ObjectUtils.listToMap(headerFields));
                            getDiagnosisLogger().logResponse(logPostRequest, sb2, headerFields, hashMap);
                            return stringContent;
                        } catch (Exception e2) {
                            e = e2;
                            num2 = valueOf;
                            NetworkInterceptor.get().onException(logPostRequest, "POST", str, currentTimeMillis, e, num2);
                            getDiagnosisLogger().logRequestException(e);
                            throw e;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        num2 = null;
                    }
                } catch (ProtocolException unused) {
                    throw new IOException("protocol error");
                }
            } finally {
                makeConn.disconnect();
            }
        } else {
            log.severe("failed to create URLConnection");
            throw new IOException("failed to create connection");
        }
    }

    public static MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return postAsMap(str, map, map2, null, z);
    }

    public static MapContent postAsMap(String str, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, boolean z) throws IOException, AccessDeniedException, AuthenticationFailureException {
        return convertStringToMap(postAsString(str, map, map2, map3, (Map<String, String>) null, z));
    }

    public static MapContent convertStringToMap(StringContent stringContent) {
        JSONObject jSONObject;
        if (stringContent == null) {
            return null;
        }
        try {
            jSONObject = new JSONObject(stringContent.getBody());
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        MapContent mapContent = new MapContent(ObjectUtils.jsonToMap(jSONObject));
        mapContent.putHeaders(stringContent.getHeaders());
        return mapContent;
    }

    protected static HttpURLConnection makeConn(String str, Map<String, String> map, Map<String, String> map2, Integer num) {
        URL url;
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }
        if (url == null) {
            log.severe("failed to init url");
            return null;
        }
        if (num == null) {
            num = Integer.valueOf((int) TIMEOUT);
        }
        try {
            CookieHandler.setDefault(null);
            HttpURLConnection makeConn = sHttpURLConnectionFactory.makeConn(url);
            boolean z = false;
            makeConn.setInstanceFollowRedirects(false);
            makeConn.setConnectTimeout(num.intValue());
            makeConn.setReadTimeout(num.intValue());
            makeConn.setUseCaches(false);
            makeConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (map2 == null || TextUtils.isEmpty(map2.get("User-Agent"))) {
                z = true;
            }
            if (z && !TextUtils.isEmpty(XMPassportSettings.getUserAgent())) {
                makeConn.setRequestProperty("User-Agent", XMPassportSettings.getUserAgent());
            }
            if (map == null) {
                map = new EasyMap<>();
            }
            map.put("sdkVersion", VersionUtils.getVersion());
            makeConn.setRequestProperty("Cookie", joinMap(map, "; "));
            if (map2 != null) {
                for (String str2 : map2.keySet()) {
                    makeConn.setRequestProperty(str2, map2.get(str2));
                }
            }
            return makeConn;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String encodeFormatAndJoinMap(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            String encode = encode(entry.getKey());
            String encode2 = !TextUtils.isEmpty(entry.getValue()) ? encode(entry.getValue()) : "";
            sb.append(encode);
            sb.append(NAME_VALUE_SEPARATOR);
            sb.append(encode2);
        }
        return sb.toString();
    }

    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected static String joinMap(Map<String, String> map, String str) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(str);
            }
            sb.append(entry.getKey());
            sb.append(NAME_VALUE_SEPARATOR);
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    protected static Map<String, String> parseCookies(List<HttpCookie> list) {
        HashMap hashMap = new HashMap();
        for (HttpCookie httpCookie : list) {
            if (!httpCookie.hasExpired()) {
                String name = httpCookie.getName();
                String value = httpCookie.getValue();
                if (name != null) {
                    hashMap.put(name, value);
                }
            }
        }
        return hashMap;
    }

    /* loaded from: classes2.dex */
    public static class HeaderContent {
        private int httpCode = -1;
        private final Set<String> cookieKeys = new HashSet();
        private final Map<String, String> headers = new HashMap();

        public void putHeader(String str, String str2) {
            this.headers.put(str, str2);
        }

        public String getHeader(String str) {
            return this.headers.get(str);
        }

        public Map<String, String> getHeaders() {
            return this.headers;
        }

        public void putHeaders(Map<String, String> map) {
            this.headers.putAll(map);
        }

        public void putCookies(Map<String, String> map) {
            putHeaders(map);
            if (map != null) {
                this.cookieKeys.addAll(map.keySet());
            }
        }

        public Set<String> getCookieKeys() {
            return this.cookieKeys;
        }

        public int getHttpCode() {
            return this.httpCode;
        }

        public void setHttpCode(int i) {
            this.httpCode = i;
        }

        public String toString() {
            return "HeaderContent{headers=" + this.headers + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static class StringContent extends HeaderContent {
        private String body;

        public StringContent(String str) {
            this.body = str;
        }

        public String getBody() {
            return this.body;
        }

        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HeaderContent
        public String toString() {
            return "StringContent{body='" + this.body + "'}";
        }
    }

    /* loaded from: classes2.dex */
    public static class MapContent extends HeaderContent {
        private Map<String, Object> bodies;

        public MapContent(Map<String, Object> map) {
            this.bodies = map;
        }

        public Object getFromBody(String str) {
            return this.bodies.get(str);
        }

        @Override // com.xiaomi.accountsdk.request.SimpleRequest.HeaderContent
        public String toString() {
            return "MapContent{bodies=" + this.bodies + '}';
        }
    }

    /* loaded from: classes2.dex */
    public static class StreamContent extends HeaderContent {
        private InputStream stream;

        public StreamContent(InputStream inputStream) {
            this.stream = inputStream;
        }

        public InputStream getStream() {
            return this.stream;
        }

        public void closeStream() {
            IOUtils.closeQuietly(this.stream);
        }
    }
}
