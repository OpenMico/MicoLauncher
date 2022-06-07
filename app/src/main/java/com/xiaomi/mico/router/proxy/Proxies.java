package com.xiaomi.mico.router.proxy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Proxies.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/xiaomi/mico/router/proxy/Proxies;", "", "()V", "Instance", "router_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class Proxies {
    @NotNull
    public static final Instance Instance = new Instance(null);
    @NotNull
    public static final String KEY_API_MANAGER = "apiManager";
    @SuppressLint({"StaticFieldLeak"})
    @Nullable
    private static Context a;

    @Nullable
    public static final Context getApp() {
        Instance instance = Instance;
        return a;
    }

    public static final void setApp(@Nullable Context context) {
        Instance.setApp(context);
    }

    /* compiled from: Proxies.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R0\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0086T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/xiaomi/mico/router/proxy/Proxies$Instance;", "", "()V", b.p, "Landroid/content/Context;", "App", "getApp$annotations", "getApp", "()Landroid/content/Context;", "setApp", "(Landroid/content/Context;)V", "KEY_API_MANAGER", "", "router_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes3.dex */
    public static final class Instance {
        @JvmStatic
        public static /* synthetic */ void getApp$annotations() {
        }

        private Instance() {
        }

        public /* synthetic */ Instance(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final Context getApp() {
            return Proxies.a;
        }

        public final void setApp(@Nullable Context context) {
            if ((context instanceof Activity) || (context instanceof Service)) {
                throw new RuntimeException("context cant be Activity or Service");
            }
            Proxies.a = context;
        }
    }
}
