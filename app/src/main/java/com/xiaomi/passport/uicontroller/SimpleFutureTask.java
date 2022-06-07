package com.xiaomi.passport.uicontroller;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* loaded from: classes4.dex */
public class SimpleFutureTask<T> extends FutureTask<T> {
    private Callback<T> mCallback;

    /* loaded from: classes4.dex */
    public static abstract class Callback<T> {
        public abstract void call(SimpleFutureTask<T> simpleFutureTask);
    }

    public SimpleFutureTask(Callable<T> callable, Callback<T> callback) {
        super(callable);
        this.mCallback = callback;
    }

    @Override // java.util.concurrent.FutureTask
    protected void done() {
        if (!isCancelled() && this.mCallback != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.xiaomi.passport.uicontroller.SimpleFutureTask.1
                @Override // java.lang.Runnable
                public void run() {
                    SimpleFutureTask.this.mCallback.call(SimpleFutureTask.this);
                }
            });
        }
        super.done();
    }
}
