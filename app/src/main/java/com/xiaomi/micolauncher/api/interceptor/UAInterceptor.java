package com.xiaomi.micolauncher.api.interceptor;

import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class UAInterceptor implements Interceptor {
    private static String a;

    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder().header("User-Agent", a()).build());
    }

    private static String a() {
        if (a == null) {
            a = "MICO/" + Hardware.current(MicoApplication.getApp()).getName() + "/" + RomUpdateAdapter.getInstance().getChannel() + "/" + b();
        }
        return a;
    }

    private static String b() {
        return RomUpdateAdapter.getInstance().getVersion().toString();
    }
}
