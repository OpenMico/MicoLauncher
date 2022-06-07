package com.metv.component.simple_http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class SimpleHttpExecutor {
    private ExecutorService a;

    public SimpleHttpExecutor(int i) {
        i = i <= 0 ? 1 : i;
        if (i == 1) {
            this.a = Executors.newSingleThreadExecutor();
        } else {
            this.a = Executors.newScheduledThreadPool(i);
        }
    }

    public boolean execute(Runnable runnable) {
        if (isReleased()) {
            return false;
        }
        this.a.execute(runnable);
        return true;
    }

    public synchronized void release() {
        if (this.a != null) {
            this.a.shutdown();
            this.a = null;
        }
    }

    public synchronized boolean isReleased() {
        return this.a == null;
    }
}
