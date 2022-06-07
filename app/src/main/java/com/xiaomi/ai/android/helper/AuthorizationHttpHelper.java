package com.xiaomi.ai.android.helper;

import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.log.Logger;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class AuthorizationHttpHelper {
    private OkHttpClient a;

    public AuthorizationHttpHelper(Engine engine) {
        this(engine, null);
    }

    public AuthorizationHttpHelper(final Engine engine, OkHttpClient.Builder builder) {
        if (builder == null) {
            Logger.b("AuthorizationHttpHelper", "builder is null");
            builder = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS);
        }
        this.a = builder.addInterceptor(new Interceptor() { // from class: com.xiaomi.ai.android.helper.AuthorizationHttpHelper.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) {
                String str;
                String str2;
                Request request = chain.request();
                if (AuthorizationHttpHelper.this.a(request)) {
                    Logger.b("AuthorizationHttpHelper", "hasAuthorizationHeader");
                } else {
                    String authorization = engine.getAuthorization();
                    if (authorization == null) {
                        str = "AuthorizationHttpHelper";
                        str2 = " getAuthorization is null";
                    } else if (!request.url().isHttps()) {
                        str = "AuthorizationHttpHelper";
                        str2 = "only support https and not add authorization";
                    } else {
                        request = request.newBuilder().header("Authorization", authorization).build();
                    }
                    Logger.d(str, str2);
                }
                return chain.proceed(request);
            }
        }).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Request request) {
        return request.headers().names().contains("Authorization");
    }

    public OkHttpClient getClient() {
        return this.a;
    }
}
