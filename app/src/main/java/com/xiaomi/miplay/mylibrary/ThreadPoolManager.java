package com.xiaomi.miplay.mylibrary;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public class ThreadPoolManager {
    private ExecutorService a;

    /* loaded from: classes4.dex */
    private static class a {
        private static ThreadPoolManager a = new ThreadPoolManager();
    }

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return a.a;
    }

    public void initThreadPool() {
        Logger.d("ThreadPoolManager", "initThreadPool.", new Object[0]);
        if (this.a == null) {
            this.a = Executors.newSingleThreadExecutor();
        }
    }

    public void executeRunable(Runnable runnable) {
        try {
            if (this.a == null || this.a.isShutdown()) {
                initThreadPool();
                this.a.execute(runnable);
            } else {
                this.a.execute(runnable);
            }
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    public void shutdownExecutor() {
        ExecutorService executorService = this.a;
        if (executorService != null && !executorService.isShutdown()) {
            this.a.shutdown();
            this.a = null;
        }
    }
}
