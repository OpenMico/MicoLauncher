package com.xiaomi.smarthome.core;

import android.content.Context;
import com.xiaomi.mico.router.proxy.Proxies;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Constants.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/xiaomi/smarthome/core/Constants;", "", "()V", "MIOT_USE_CACHE", "", "PATH_CACHE", "", "PATH_DATA", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class Constants {
    @NotNull
    public static final Constants INSTANCE = new Constants();
    public static final boolean MIOT_USE_CACHE = true;
    @JvmField
    @NotNull
    public static final String PATH_CACHE;
    private static final String a;

    static {
        StringBuilder sb = new StringBuilder();
        Context app2 = Proxies.Instance.getApp();
        Intrinsics.checkNotNull(app2);
        File cacheDir = app2.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "Proxies.App!!.cacheDir");
        sb.append(cacheDir.getAbsolutePath());
        sb.append(File.separator);
        sb.append("data");
        a = sb.toString();
        PATH_CACHE = a + "/NetCache";
    }

    private Constants() {
    }
}
