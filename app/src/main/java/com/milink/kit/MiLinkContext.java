package com.milink.kit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.jackeywong.varhandle.VarHandle;
import com.jackeywong.varhandle.WeakVarHandle;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public abstract class MiLinkContext {
    public static final String REGISTER_MANAGER = "register_manager";
    private static final VarHandle<Handler> a = new WeakVarHandle<Handler>() { // from class: com.milink.kit.MiLinkContext.1
        /* renamed from: a */
        public Handler constructor() {
            return new Handler(Looper.getMainLooper());
        }
    };
    private static final VarHandle<Executor> b = new AnonymousClass2();
    private static MiLinkContext c;
    private final ExecutorService d;
    private final Context e;

    @Nullable
    public abstract <T> T get(@NonNull Class<? extends T> cls);

    @Nullable
    public abstract <T> T get(@NonNull String str);

    public abstract void register(@NonNull String str, @NonNull Object obj);

    public abstract void unregister(@NonNull String str);

    /* renamed from: com.milink.kit.MiLinkContext$2 */
    /* loaded from: classes2.dex */
    class AnonymousClass2 extends WeakVarHandle<Executor> {
        AnonymousClass2() {
        }

        public static /* synthetic */ void a(Runnable runnable) {
            ((Handler) MiLinkContext.a.get()).post(runnable);
        }

        /* renamed from: a */
        public Executor constructor() {
            return $$Lambda$MiLinkContext$2$80EIVyXbip02jkOkEb9RpUPj8o.INSTANCE;
        }
    }

    public MiLinkContext(@NonNull Installer installer) {
        this.e = installer.a;
        this.d = new b(installer.b);
    }

    public static boolean hasMiLinkContext() {
        return c != null;
    }

    @NonNull
    public static MiLinkContext getInstance() {
        MiLinkContext miLinkContext = c;
        if (miLinkContext != null) {
            return miLinkContext;
        }
        throw new NullPointerException("please call Installer init first!");
    }

    public static Handler getMainHandler() {
        return a.get();
    }

    public static Executor getMainExecutor() {
        return b.get();
    }

    @NonNull
    public static MiLinkContext b(@NonNull Installer installer) {
        synchronized (MiLinkContext.class) {
            if (c == null) {
                c = new a(installer);
            } else {
                throw new IllegalStateException("Repeat install MiLinkContext！");
            }
        }
        return c;
    }

    @NonNull
    public final ExecutorService getCallbackExecutor() {
        return this.d;
    }

    @NonNull
    public final Context getAppContext() {
        return this.e;
    }

    @NonNull
    public final <T> T require(@NonNull Class<? extends T> cls) {
        return (T) Objects.requireNonNull(get(cls));
    }

    /* loaded from: classes2.dex */
    public static final class Installer {
        final Context a;
        ExecutorService b;

        public Installer(@NonNull Context context) {
            this.a = ((Context) Objects.requireNonNull(context)).getApplicationContext();
        }

        private static ExecutorService a() {
            return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 15L, TimeUnit.SECONDS, new SynchronousQueue(true));
        }

        public Installer setCallbackExecutor(ExecutorService executorService) {
            this.b = executorService;
            return this;
        }

        @NonNull
        public MiLinkContext install() {
            if (this.b == null) {
                this.b = a();
            }
            return MiLinkContext.b(this);
        }
    }
}
