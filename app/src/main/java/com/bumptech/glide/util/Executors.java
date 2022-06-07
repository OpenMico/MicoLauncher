package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class Executors {
    private static final Executor a = new Executor() { // from class: com.bumptech.glide.util.Executors.1
        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            Util.postOnUiThread(runnable);
        }
    };
    private static final Executor b = new Executor() { // from class: com.bumptech.glide.util.Executors.2
        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            runnable.run();
        }
    };

    private Executors() {
    }

    public static Executor mainThreadExecutor() {
        return a;
    }

    public static Executor directExecutor() {
        return b;
    }

    @VisibleForTesting
    public static void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(5L, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(5L, TimeUnit.SECONDS)) {
                    throw new RuntimeException("Failed to shutdown");
                }
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
