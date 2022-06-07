package com.xiaomi.smarthome.library.common.util;

import android.os.AsyncTask;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class SerialExecutor implements Executor {
    final ArrayDeque<Runnable> a = new ArrayDeque<>();
    Runnable b;

    @Override // java.util.concurrent.Executor
    public synchronized void execute(final Runnable runnable) {
        this.a.offer(new Runnable() { // from class: com.xiaomi.smarthome.library.common.util.SerialExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnable.run();
                } finally {
                    SerialExecutor.this.scheduleNext();
                }
            }
        });
        if (this.b == null) {
            scheduleNext();
        }
    }

    protected synchronized void scheduleNext() {
        Runnable poll = this.a.poll();
        this.b = poll;
        if (poll != null) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this.b);
        }
    }
}
