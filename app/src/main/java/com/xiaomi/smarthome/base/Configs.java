package com.xiaomi.smarthome.base;

import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Configs.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/xiaomi/smarthome/base/Configs;", "", "()V", "Instance", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class Configs {
    @NotNull
    public static final Instance Instance = new Instance(null);
    private static boolean a;

    /* compiled from: Configs.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/xiaomi/smarthome/base/Configs$Instance;", "", "()V", "isErrorHandlerConfig", "", "()Z", "setErrorHandlerConfig", "(Z)V", "setupRxJavaErrorHandler", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Instance {
        private Instance() {
        }

        public /* synthetic */ Instance(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isErrorHandlerConfig() {
            return Configs.a;
        }

        public final void setErrorHandlerConfig(boolean z) {
            Configs.a = z;
        }

        public final void setupRxJavaErrorHandler() {
            Instance instance = this;
            if (!instance.isErrorHandlerConfig()) {
                RxJavaPlugins.setErrorHandler(a.a);
                instance.setErrorHandlerConfig(true);
            }
        }

        /* compiled from: Configs.kt */
        @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "", "kotlin.jvm.PlatformType", "accept"}, k = 3, mv = {1, 4, 2})
        /* loaded from: classes4.dex */
        static final class a<T> implements Consumer<Throwable> {
            public static final a a = new a();

            a() {
            }

            /* renamed from: a */
            public final void accept(Throwable th) {
                Logger logger = L.smarthome;
                logger.e("RxJava3 onError occurred,error=" + th);
            }
        }
    }
}
