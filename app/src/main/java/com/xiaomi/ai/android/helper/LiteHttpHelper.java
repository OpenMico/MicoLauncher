package com.xiaomi.ai.android.helper;

import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.transport.d;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/* loaded from: classes2.dex */
public class LiteHttpHelper {
    private OkHttpClient a;

    public LiteHttpHelper(Engine engine) {
        this(engine, null);
    }

    public LiteHttpHelper(Engine engine, OkHttpClient.Builder builder) {
        d dVar = new d(((com.xiaomi.ai.android.core.d) engine).g());
        if (builder == null) {
            Logger.b("LiteHttpHelper", "builder is null");
            builder = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS);
        }
        this.a = builder.addInterceptor(dVar).build();
    }

    public OkHttpClient getClient() {
        return this.a;
    }
}
