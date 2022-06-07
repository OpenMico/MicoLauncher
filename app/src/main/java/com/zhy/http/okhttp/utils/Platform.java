package com.zhy.http.okhttp.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class Platform {
    private static final Platform a = a();

    public static Platform get() {
        L.e(a.getClass().toString());
        return a;
    }

    private static Platform a() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new a();
            }
        } catch (ClassNotFoundException unused) {
        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class a extends Platform {
        a() {
        }

        @Override // com.zhy.http.okhttp.utils.Platform
        public Executor defaultCallbackExecutor() {
            return new ExecutorC0199a();
        }

        /* renamed from: com.zhy.http.okhttp.utils.Platform$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        static class ExecutorC0199a implements Executor {
            private final Handler a = new Handler(Looper.getMainLooper());

            ExecutorC0199a() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                this.a.post(runnable);
            }
        }
    }
}
