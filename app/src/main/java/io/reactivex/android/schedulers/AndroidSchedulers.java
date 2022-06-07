package io.reactivex.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class AndroidSchedulers {
    private static final Scheduler a = RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() { // from class: io.reactivex.android.schedulers.AndroidSchedulers.1
        /* renamed from: a */
        public Scheduler call() throws Exception {
            return a.a;
        }
    });

    /* loaded from: classes4.dex */
    public static final class a {
        static final Scheduler a = new a(new Handler(Looper.getMainLooper()), false);
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(a);
    }

    public static Scheduler from(Looper looper) {
        return from(looper, false);
    }

    @SuppressLint({"NewApi"})
    public static Scheduler from(Looper looper, boolean z) {
        if (looper != null) {
            if (Build.VERSION.SDK_INT < 16) {
                z = false;
            } else if (z && Build.VERSION.SDK_INT < 22) {
                Message obtain = Message.obtain();
                try {
                    obtain.setAsynchronous(true);
                } catch (NoSuchMethodError unused) {
                    z = false;
                }
                obtain.recycle();
            }
            return new a(new Handler(looper), z);
        }
        throw new NullPointerException("looper == null");
    }

    private AndroidSchedulers() {
        throw new AssertionError("No instances.");
    }
}
