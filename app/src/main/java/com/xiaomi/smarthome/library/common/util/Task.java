package com.xiaomi.smarthome.library.common.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/* loaded from: classes4.dex */
public abstract class Task extends AsyncTask<Void, Void, Void> {
    private static Handler a;

    public abstract void doInBackground();

    /* JADX INFO: Access modifiers changed from: protected */
    public Void doInBackground(Void... voidArr) {
        doInBackground();
        return null;
    }

    private static Handler a() {
        if (a == null) {
            synchronized (Task.class) {
                if (a == null) {
                    a = new Handler(Looper.getMainLooper());
                }
            }
        }
        return a;
    }

    public void executeDelayed(final Executor executor, long j) {
        a().postDelayed(new Runnable() { // from class: com.xiaomi.smarthome.library.common.util.Task.1
            @Override // java.lang.Runnable
            public void run() {
                Task task = Task.this;
                Executor executor2 = executor;
                if (executor2 == null) {
                    executor2 = AsyncTask.THREAD_POOL_EXECUTOR;
                }
                task.executeOnExecutor(executor2, new Void[0]);
            }
        }, j);
    }

    public void execute(final Executor executor) {
        a().post(new Runnable() { // from class: com.xiaomi.smarthome.library.common.util.Task.2
            @Override // java.lang.Runnable
            public void run() {
                Task task = Task.this;
                Executor executor2 = executor;
                if (executor2 == null) {
                    executor2 = AsyncTask.THREAD_POOL_EXECUTOR;
                }
                task.executeOnExecutor(executor2, new Void[0]);
            }
        });
    }

    public static void execute(Task task, Executor executor) {
        if (task != null) {
            task.execute(executor);
        }
    }

    public static void executeDelayed(Task task, Executor executor, long j) {
        if (task != null) {
            task.executeDelayed(executor, j);
        }
    }

    public static void executeDelayed(final FutureTask futureTask, final Executor executor, long j) {
        if (futureTask != null && executor != null) {
            a().postDelayed(new Runnable() { // from class: com.xiaomi.smarthome.library.common.util.Task.3
                @Override // java.lang.Runnable
                public void run() {
                    executor.execute(futureTask);
                }
            }, j);
        }
    }
}
