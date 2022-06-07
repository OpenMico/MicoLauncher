package com.xiaomi.micolauncher.common.rxhelpers;

import android.os.Looper;
import com.xiaomi.mico.utils.ThreadUtil;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class MicoSchedulers {
    private static final Scheduler a = Schedulers.from(ThreadUtil.getIoThreadPool());
    private static final Scheduler b = Schedulers.from(ThreadUtil.getComputationThreadPool());

    public static Scheduler io() {
        return a;
    }

    public static Scheduler computation() {
        return b;
    }

    public static Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public static Scheduler fromLooper(Looper looper) {
        return AndroidSchedulers.from(looper);
    }
}
