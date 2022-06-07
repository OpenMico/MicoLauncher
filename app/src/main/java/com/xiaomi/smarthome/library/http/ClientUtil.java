package com.xiaomi.smarthome.library.http;

import android.text.TextUtils;
import java.io.IOException;
import java.net.CookieManager;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.internal.Util;

/* loaded from: classes4.dex */
public class ClientUtil {
    public static OkHttpClient create() {
        return create(null);
    }

    public static OkHttpClient create(final String str) {
        OkHttpClient.Builder cookieJar = new OkHttpClient.Builder().dispatcher(new Dispatcher(new ThreadPoolExecutor(6, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp Dispatcher", false)))).connectTimeout(20L, TimeUnit.SECONDS).readTimeout(30L, TimeUnit.SECONDS).writeTimeout(30L, TimeUnit.SECONDS).cookieJar(new JavaNetCookieJar(new CookieManager()));
        if (!TextUtils.isEmpty(str)) {
            cookieJar.addNetworkInterceptor(new Interceptor() { // from class: com.xiaomi.smarthome.library.http.ClientUtil.1
                @Override // okhttp3.Interceptor
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    return chain.proceed(chain.request().newBuilder().removeHeader("User-Agent").addHeader("User-Agent", str).build());
                }
            });
        }
        return cookieJar.build();
    }
}
