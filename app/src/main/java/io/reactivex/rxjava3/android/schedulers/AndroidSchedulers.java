package io.reactivex.rxjava3.android.schedulers;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;

/* loaded from: classes2.dex */
public final class AndroidSchedulers {
    private static final Scheduler a = RxAndroidPlugins.initMainThreadScheduler($$Lambda$AndroidSchedulers$gF6mFTQYsvHI_iWx7eXVtrQ5zg.INSTANCE);

    /* loaded from: classes2.dex */
    public static final class a {
        static final Scheduler a = new a(new Handler(Looper.getMainLooper()), true);
    }

    public static Scheduler mainThread() {
        return RxAndroidPlugins.onMainThreadScheduler(a);
    }

    public static Scheduler from(Looper looper) {
        return from(looper, true);
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
