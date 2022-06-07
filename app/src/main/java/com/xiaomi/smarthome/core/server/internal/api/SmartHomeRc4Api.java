package com.xiaomi.smarthome.core.server.internal.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.passport.ui.settings.UserAvatarUpdateActivity;
import com.xiaomi.smarthome.core.entity.net.KeyValuePair;
import com.xiaomi.smarthome.core.entity.net.NetError;
import com.xiaomi.smarthome.core.entity.net.NetRequest;
import com.xiaomi.smarthome.core.entity.net.NetResult;
import com.xiaomi.smarthome.core.server.internal.NetCallback;
import com.xiaomi.smarthome.core.server.internal.NetHandle;
import com.xiaomi.smarthome.core.server.internal.account.AccountManager;
import com.xiaomi.smarthome.core.server.internal.apicache.SmartHomeRc4Cache;
import com.xiaomi.smarthome.core.server.internal.util.KeyValuePairUtil;
import com.xiaomi.smarthome.core.server.internal.util.LogUtil;
import com.xiaomi.smarthome.frame.ErrorCode;
import com.xiaomi.smarthome.globalsetting.GlobalSetting;
import com.xiaomi.smarthome.library.crypto.rc4coder.Coder;
import com.xiaomi.smarthome.library.crypto.rc4coder.RC4DropCoder;
import com.xiaomi.smarthome.library.http.util.CookieUtil;
import com.xiaomi.smarthome.setting.LoginSetting;
import com.xiaomi.smarthome.setting.ServerSetting;
import com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo;
import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class SmartHomeRc4Api {
    public static final String SMART_HOME_API_PREVIEW = "pv.api.io.mi.com";
    public static final String SMART_HOME_API_RELEASE = "api.io.mi.com";
    public static final String TAG = "SmartHomeApi";
    private static SmartHomeRc4Api a;
    private static Object b = new Object();
    private static BroadcastReceiver c;
    private MiServiceTokenInfo d;
    private boolean e = false;
    private OkHttpClient f;
    private CookieManager g;
    private Dispatcher h;

    private SmartHomeRc4Api() {
        String str = GlobalSetting.getAppContext().getFilesDir().getPath() + File.separator + "okhttp3" + File.separator + "cache";
        ArrayList arrayList = new ArrayList();
        arrayList.add(Protocol.HTTP_1_1);
        if (Build.VERSION.SDK_INT > 20) {
            arrayList.add(Protocol.HTTP_2);
        }
        this.h = new Dispatcher(new ThreadPoolExecutor(6, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false)));
        OkHttpClient.Builder protocols = new OkHttpClient.Builder().dispatcher(this.h).connectTimeout(20L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).retryOnConnectionFailure(false).protocols(arrayList);
        CookieManager cookieManager = new CookieManager();
        this.g = cookieManager;
        this.f = protocols.cookieJar(new JavaNetCookieJar(cookieManager)).addNetworkInterceptor(new Interceptor() { // from class: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                if (request.tag() == null || !(request.tag() instanceof String) || request.tag().equals(SmartHomeRc4Api.this.d.ssecurity)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    Response proceed = chain.proceed(request);
                    SmartHomeRc4Api.this.a(request, Math.abs(System.currentTimeMillis() - currentTimeMillis));
                    return proceed;
                }
                throw new IOException();
            }
        }).cache(new Cache(new File(str), 104857600L)).build();
    }

    public static SmartHomeRc4Api getInstance() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new SmartHomeRc4Api();
                }
            }
        }
        return a;
    }

    public boolean isInitialized() {
        boolean z;
        synchronized (b) {
            z = this.e;
        }
        return z;
    }

    public void setInitialized(boolean z) {
        synchronized (b) {
            this.e = z;
        }
    }

    private String a(String str) {
        String str2;
        String server = GlobalSetting.getServer();
        String serverEnv = GlobalSetting.getServerEnv();
        if (TextUtils.isEmpty(str)) {
            str = "";
        } else if (!str.endsWith(".")) {
            str = str + ".";
        }
        if (TextUtils.isEmpty(server) || server.equalsIgnoreCase(ServerSetting.SERVER_CN)) {
            if (TextUtils.isEmpty(serverEnv) || serverEnv.equalsIgnoreCase("release") || !serverEnv.equalsIgnoreCase("preview")) {
                str2 = str + SMART_HOME_API_RELEASE;
            } else {
                str2 = str + SMART_HOME_API_PREVIEW;
            }
        } else if (TextUtils.isEmpty(serverEnv) || serverEnv.equalsIgnoreCase("release") || !serverEnv.equalsIgnoreCase("preview")) {
            str2 = server + "." + str + SMART_HOME_API_RELEASE;
        } else {
            str2 = server + "." + str + SMART_HOME_API_PREVIEW;
        }
        return "https://" + str2;
    }

    private String a() {
        String str;
        String server = GlobalSetting.getServer();
        String serverEnv = GlobalSetting.getServerEnv();
        if (TextUtils.isEmpty(server) || server.equalsIgnoreCase(ServerSetting.SERVER_CN)) {
            str = (TextUtils.isEmpty(serverEnv) || serverEnv.equalsIgnoreCase("release") || !serverEnv.equalsIgnoreCase("preview")) ? SMART_HOME_API_RELEASE : SMART_HOME_API_PREVIEW;
        } else if (TextUtils.isEmpty(serverEnv) || serverEnv.equalsIgnoreCase("release") || !serverEnv.equalsIgnoreCase("preview")) {
            str = server + "." + SMART_HOME_API_RELEASE;
        } else {
            str = server + "." + SMART_HOME_API_PREVIEW;
        }
        return "https://" + str;
    }

    private String a(NetRequest netRequest) {
        if (netRequest == null || TextUtils.isEmpty(netRequest.getPrefix())) {
            return a() + "/app" + netRequest.getPath();
        }
        return a(netRequest.getPrefix()) + netRequest.getPath();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        Locale locale = GlobalSetting.getLocale();
        if (locale == null) {
            CookieUtil.addCookie(this.g, a(), "locale", Locale.getDefault().toString(), LoginSetting.COOKIE_DOMAIN_XIAOMI_IO, "/");
            return;
        }
        CookieUtil.addCookie(this.g, a(), "locale", locale.toString(), LoginSetting.COOKIE_DOMAIN_XIAOMI_IO, "/");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c() {
        /*
            r17 = this;
            r0 = r17
            java.lang.String r1 = ""
            r2 = 0
            com.xiaomi.smarthome.library.commonapi.SystemApi r3 = com.xiaomi.smarthome.library.commonapi.SystemApi.getInstance()     // Catch: UnsupportedEncodingException -> 0x0021
            java.lang.String r3 = r3.getTimeZone()     // Catch: UnsupportedEncodingException -> 0x0021
            java.lang.String r4 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r3, r4)     // Catch: UnsupportedEncodingException -> 0x0021
            java.util.TimeZone r3 = java.util.TimeZone.getDefault()     // Catch: UnsupportedEncodingException -> 0x0021
            boolean r4 = r3.useDaylightTime()     // Catch: UnsupportedEncodingException -> 0x0021
            int r2 = r3.getDSTSavings()     // Catch: UnsupportedEncodingException -> 0x0022
            r8 = r1
            goto L_0x0023
        L_0x0021:
            r4 = r2
        L_0x0022:
            r8 = r1
        L_0x0023:
            java.net.CookieManager r5 = r0.g
            java.lang.String r6 = r17.a()
            java.lang.String r7 = "timezone"
            java.lang.String r9 = ".io.mi.com"
            java.lang.String r10 = "/"
            com.xiaomi.smarthome.library.http.util.CookieUtil.addCookie(r5, r6, r7, r8, r9, r10)
            java.net.CookieManager r11 = r0.g
            java.lang.String r12 = r17.a()
            java.lang.String r13 = "is_daylight"
            if (r4 == 0) goto L_0x003f
            java.lang.String r1 = "1"
            goto L_0x0041
        L_0x003f:
            java.lang.String r1 = "0"
        L_0x0041:
            r14 = r1
            java.lang.String r15 = ".io.mi.com"
            java.lang.String r16 = "/"
            com.xiaomi.smarthome.library.http.util.CookieUtil.addCookie(r11, r12, r13, r14, r15, r16)
            java.net.CookieManager r3 = r0.g
            java.lang.String r4 = r17.a()
            java.lang.String r5 = "dst_offset"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = ""
            r1.append(r6)
            r1.append(r2)
            java.lang.String r6 = r1.toString()
            java.lang.String r7 = ".io.mi.com"
            java.lang.String r8 = "/"
            com.xiaomi.smarthome.library.http.util.CookieUtil.addCookie(r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.c():void");
    }

    private byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    private static String a(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(Coder.decryptBASE64(str2), "HmacSHA256"));
            return Coder.encryptBASE64(instance.doFinal(str.getBytes("UTF-8")));
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0051 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.util.List<com.xiaomi.smarthome.core.entity.net.KeyValuePair>, java.lang.String> b(com.xiaomi.smarthome.core.entity.net.NetRequest r12) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.b(com.xiaomi.smarthome.core.entity.net.NetRequest):android.util.Pair");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.util.Pair<java.util.List<com.xiaomi.smarthome.core.entity.net.KeyValuePair>, java.lang.String> c(com.xiaomi.smarthome.core.entity.net.NetRequest r10) {
        /*
            r9 = this;
            com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo r0 = r9.d
            long r0 = r0.timeDiff
            java.lang.String r0 = com.xiaomi.smarthome.library.crypto.CloudCoder.generateNonce(r0)
            r1 = 0
            com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo r2 = r9.d     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            java.lang.String r2 = r2.ssecurity     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            byte[] r2 = com.xiaomi.smarthome.library.crypto.rc4coder.Coder.decryptBASE64(r2)     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            byte[] r3 = com.xiaomi.smarthome.library.crypto.rc4coder.Coder.decryptBASE64(r0)     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            byte[] r2 = r9.a(r2, r3)     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            byte[] r2 = com.xiaomi.smarthome.library.crypto.rc4coder.Coder.sha256Hash(r2)     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            java.lang.String r2 = com.xiaomi.smarthome.library.crypto.rc4coder.Coder.encryptBASE64(r2)     // Catch: NoSuchAlgorithmException -> 0x0032, InvalidKeyException -> 0x002a, Exception -> 0x0022
            goto L_0x003a
        L_0x0022:
            java.lang.String r2 = "SmartHomeApi"
            java.lang.String r3 = "generate sessionSecurity fail"
            android.util.Log.d(r2, r3)
            goto L_0x0039
        L_0x002a:
            java.lang.String r2 = "SmartHomeApi"
            java.lang.String r3 = "generate sessionSecurity fail:InvalidKeyException"
            android.util.Log.d(r2, r3)
            goto L_0x0039
        L_0x0032:
            java.lang.String r2 = "SmartHomeApi"
            java.lang.String r3 = "generate sessionSecurity fail:NoSuchAlgorithmException"
            android.util.Log.d(r2, r3)
        L_0x0039:
            r2 = r1
        L_0x003a:
            if (r2 != 0) goto L_0x003d
            return r1
        L_0x003d:
            java.util.TreeMap r1 = new java.util.TreeMap
            r1.<init>()
            java.util.TreeMap r3 = new java.util.TreeMap
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            com.xiaomi.smarthome.library.crypto.rc4coder.RC4DropCoder r5 = new com.xiaomi.smarthome.library.crypto.rc4coder.RC4DropCoder
            r5.<init>(r2)
            java.util.List r6 = r10.getQueryParams()
            if (r6 == 0) goto L_0x0087
            java.util.Iterator r6 = r6.iterator()
        L_0x005b:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0087
            java.lang.Object r7 = r6.next()
            com.xiaomi.smarthome.core.entity.net.KeyValuePair r7 = (com.xiaomi.smarthome.core.entity.net.KeyValuePair) r7
            java.lang.String r8 = r7.getKey()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x005b
            java.lang.String r8 = r7.getValue()
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x005b
            java.lang.String r8 = r7.getKey()
            java.lang.String r7 = r7.getValue()
            r3.put(r8, r7)
            goto L_0x005b
        L_0x0087:
            java.lang.String r6 = r10.getMethod()
            java.lang.String r7 = r10.getPath()
            java.lang.String r6 = com.xiaomi.smarthome.library.crypto.CloudCoder.generateSignature(r6, r7, r3, r2)
            java.lang.String r7 = "rc4_hash__"
            r3.put(r7, r6)
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x00a0:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x00cc
            java.lang.Object r6 = r3.next()
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6
            java.lang.Object r7 = r6.getValue()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.String r7 = r5.encrypt(r7)
            java.lang.Object r8 = r6.getKey()
            r1.put(r8, r7)
            com.xiaomi.smarthome.core.entity.net.KeyValuePair r8 = new com.xiaomi.smarthome.core.entity.net.KeyValuePair
            java.lang.Object r6 = r6.getKey()
            java.lang.String r6 = (java.lang.String) r6
            r8.<init>(r6, r7)
            r4.add(r8)
            goto L_0x00a0
        L_0x00cc:
            java.lang.String r3 = r10.getMethod()
            java.lang.String r10 = r10.getPath()
            java.lang.String r10 = com.xiaomi.smarthome.library.crypto.CloudCoder.generateSignature(r3, r10, r1, r2)
            com.xiaomi.smarthome.core.entity.net.KeyValuePair r1 = new com.xiaomi.smarthome.core.entity.net.KeyValuePair
            java.lang.String r2 = "signature"
            r1.<init>(r2, r10)
            r4.add(r1)
            com.xiaomi.smarthome.core.entity.net.KeyValuePair r10 = new com.xiaomi.smarthome.core.entity.net.KeyValuePair
            java.lang.String r1 = "_nonce"
            r10.<init>(r1, r0)
            r4.add(r10)
            com.xiaomi.smarthome.core.entity.net.KeyValuePair r10 = new com.xiaomi.smarthome.core.entity.net.KeyValuePair
            java.lang.String r1 = "ssecurity"
            com.xiaomi.youpin.login.entity.account.MiServiceTokenInfo r2 = r9.d
            java.lang.String r2 = r2.ssecurity
            r10.<init>(r1, r2)
            r4.add(r10)
            android.util.Pair r10 = android.util.Pair.create(r4, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.c(com.xiaomi.smarthome.core.entity.net.NetRequest):android.util.Pair");
    }

    protected String decrypt(String str, String str2) throws SecurityException {
        try {
            String encryptBASE64 = Coder.encryptBASE64(Coder.sha256Hash(a(Coder.decryptBASE64(this.d.ssecurity), Coder.decryptBASE64(str2))));
            if (encryptBASE64 == null) {
                return null;
            }
            try {
                return new RC4DropCoder(encryptBASE64).decrypt(str);
            } catch (Exception unused) {
                return null;
            }
        } catch (InvalidKeyException unused2) {
            return null;
        } catch (NoSuchAlgorithmException unused3) {
            return null;
        } catch (Exception unused4) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, String str2) {
        try {
            a(new JSONObject(str2).optInt("code"));
        } catch (JSONException unused) {
            a(-1);
        }
    }

    private void a(int i) {
        LogUtil.d(TAG, "app_401_errorcode3 doUnAuthorized from=" + i);
        AccountManager.getInstance().doUnAuthorized(i);
        setInitialized(false);
    }

    private void d() {
        if (GlobalSetting.getIsDebug()) {
            try {
                int queuedCallsCount = this.h.queuedCallsCount();
                int runningCallsCount = this.h.runningCallsCount();
                Log.d("forceUpdateAllData", "okhttp queuedCallsCount=" + queuedCallsCount + ",runningCallsCount=" + runningCallsCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Request request, long j) {
        if (j >= 600 && GlobalSetting.getIsDebug()) {
            try {
                Log.d("forceUpdateAllData", request.url().toString() + " takes " + j + "ms to get response");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public NetHandle sendRequest(final NetRequest netRequest, final NetCallback<NetResult, NetError> netCallback) {
        final boolean z;
        final Pair<List<KeyValuePair>, String> pair;
        final Request request;
        d();
        if (netRequest == null) {
            if (netCallback != null) {
                netCallback.onFailure(new NetError(ErrorCode.INVALID.getCode(), ""));
            }
            return new NetHandle(null);
        }
        String a2 = a(netRequest);
        if (!isInitialized()) {
            if (!AccountManager.getInstance().isMiLoggedIn()) {
                if (netCallback != null) {
                    netCallback.onFailure(new NetError(ErrorCode.INVALID.getCode(), "not loggedin"));
                }
                return new NetHandle(null);
            }
            String miId = AccountManager.getInstance().getMiId();
            this.d = AccountManager.getInstance().getMiServiceToken();
            if (TextUtils.isEmpty(miId) || this.d == null) {
                if (netCallback != null) {
                    netCallback.onFailure(new NetError(ErrorCode.INVALID.getCode(), ""));
                }
                return new NetHandle(null);
            }
            CookieUtil.clearAllCookie(this.g);
            CookieUtil.addCookie(this.g, a(), BaseConstants.EXTRA_USER_ID, miId, LoginSetting.COOKIE_DOMAIN_XIAOMI_IO, "/");
            CookieUtil.addCookie(this.g, a(), "yetAnotherServiceToken", this.d.serviceToken, this.d.domain, "/");
            CookieUtil.addCookie(this.g, a(), AuthorizeActivityBase.KEY_SERVICETOKEN, this.d.serviceToken, this.d.domain, "/");
            if (c == null) {
                c = new BroadcastReceiver() { // from class: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.2
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        SmartHomeRc4Api.this.b();
                    }
                };
                GlobalSetting.getAppContext().registerReceiver(c, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
            }
            b();
            c();
            setInitialized(true);
        }
        if (netRequest.getPrefix() == null || (!netRequest.getPrefix().startsWith("connect.camera") && !netRequest.getPrefix().startsWith("processor.smartcamera") && !netRequest.getPrefix().startsWith("business.smartcamera") && !netRequest.getPrefix().startsWith(UserAvatarUpdateActivity.CAMERA))) {
            pair = b(netRequest);
            z = true;
        } else {
            z = false;
            pair = c(netRequest);
        }
        if (pair == null) {
            if (netCallback != null) {
                netCallback.onFailure(new NetError(ErrorCode.INVALID.getCode(), "pair == null"));
            }
            return new NetHandle(null);
        }
        if (netRequest.getMethod().equals("POST")) {
            request = new Request.Builder().url(a2).headers(KeyValuePairUtil.getHeaders(netRequest.getHeaders())).post(KeyValuePairUtil.getRequestBody((List) pair.first)).tag(this.d.ssecurity).build();
        } else {
            request = netRequest.getMethod().equals("GET") ? new Request.Builder().url(KeyValuePairUtil.getUrlWithQueryString(a2, (List) pair.first)).headers(KeyValuePairUtil.getHeaders(netRequest.getHeaders())).build() : null;
        }
        if (request == null) {
            if (netCallback != null) {
                netCallback.onFailure(new NetError(ErrorCode.INVALID.getCode(), "request == null"));
            }
            return new NetHandle(null);
        }
        if (GlobalSetting.getIsDebug()) {
            Log.d("BootRequestCheck", System.currentTimeMillis() + " SmartHomeRc4Api.sendRequest " + request.toString());
        }
        final a aVar = new a();
        SmartHomeRc4Cache.getInstance().getCache(netRequest, new SmartHomeRc4Cache.CacheCallback() { // from class: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.3
            @Override // com.xiaomi.smarthome.core.server.internal.apicache.SmartHomeRc4Cache.CacheCallback
            public void onResult(String str) {
                NetResult netResult = new NetResult();
                netResult.mIsCache = true;
                netResult.mResponse = str;
                if (netCallback != null && !aVar.a()) {
                    netCallback.onCache(netResult);
                }
            }
        });
        Call newCall = this.f.newCall(request);
        newCall.enqueue(new Callback() { // from class: com.xiaomi.smarthome.core.server.internal.api.SmartHomeRc4Api.4
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                aVar.a(true);
                if (GlobalSetting.getIsDebug()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("host:");
                    sb.append(request.url() == null ? null : request.url().host());
                    sb.append(StringUtils.SPACE);
                    sb.append(netRequest.toString());
                    LogUtil.e("MIIO", sb.toString());
                    if (iOException != null) {
                        LogUtil.e("MIIO", iOException.toString());
                    }
                }
                if (iOException instanceof SocketTimeoutException) {
                    GlobalSetting.isNetActiveAndAvailable();
                }
                NetCallback netCallback2 = netCallback;
                if (netCallback2 != null) {
                    netCallback2.onFailure(new NetError(ErrorCode.INVALID.getCode(), iOException == null ? "net request failure" : iOException.getMessage()));
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                aVar.a(true);
                String str = null;
                if (!response.isSuccessful()) {
                    if (GlobalSetting.getIsDebug()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("host:");
                        if (request.url() != null) {
                            str = request.url().host();
                        }
                        sb.append(str);
                        sb.append(StringUtils.SPACE);
                        sb.append(netRequest.toString());
                        LogUtil.e("MIIO", sb.toString());
                        LogUtil.e("MIIO", response.toString());
                    }
                    if (response.code() == 401) {
                        String str2 = "";
                        try {
                            str2 = response.body().string();
                        } catch (Exception unused) {
                        }
                        Log.e("Error", pair.toString());
                        SmartHomeRc4Api.this.b(netRequest.getPath(), str2);
                    }
                    if (netCallback == null) {
                        return;
                    }
                    if (response.request() == null || TextUtils.isEmpty(response.request().url().toString())) {
                        netCallback.onFailure(new NetError(response.code(), response.message()));
                    } else {
                        netCallback.onFailure(new NetError(response.code(), response.message(), response.request().url().toString()));
                    }
                } else {
                    try {
                        String string = response.body().string();
                        if (!z) {
                            string = SmartHomeRc4Api.this.decrypt(string, (String) pair.second);
                        }
                        SmartHomeRc4Cache.getInstance().setCache(netRequest, string);
                        if (GlobalSetting.getIsDebug() && !TextUtils.isEmpty(string)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("host:");
                            if (request.url() != null) {
                                str = request.url().host();
                            }
                            sb2.append(str);
                            sb2.append(StringUtils.SPACE);
                            sb2.append(netRequest.toString());
                            LogUtil.d("MIIO", sb2.toString());
                            LogUtil.d("MIIO", string);
                        }
                        NetResult netResult = new NetResult();
                        netResult.mResponse = string;
                        NetCallback netCallback2 = netCallback;
                        if (netCallback2 != null) {
                            netCallback2.onSuccess(netResult);
                        }
                    } catch (Exception e) {
                        NetCallback netCallback3 = netCallback;
                        if (netCallback3 != null) {
                            netCallback3.onFailure(new NetError(ErrorCode.INVALID.getCode(), e.getMessage()));
                        }
                    }
                }
            }
        });
        return new NetHandle(newCall);
    }

    /* loaded from: classes4.dex */
    class a {
        boolean a = false;

        a() {
        }

        synchronized boolean a() {
            return this.a;
        }

        synchronized void a(boolean z) {
            this.a = z;
        }
    }
}
