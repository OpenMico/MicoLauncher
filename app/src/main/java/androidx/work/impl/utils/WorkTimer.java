package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkTimer {
    private static final String d = Logger.tagWithPrefix("WorkTimer");
    private final ThreadFactory e = new ThreadFactory() { // from class: androidx.work.impl.utils.WorkTimer.1
        private int b = 0;

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
            newThread.setName("WorkManager-WorkTimer-thread-" + this.b);
            this.b = this.b + 1;
            return newThread;
        }
    };
    final Map<String, WorkTimerRunnable> a = new HashMap();
    final Map<String, TimeLimitExceededListener> b = new HashMap();
    final Object c = new Object();
    private final ScheduledExecutorService f = Executors.newSingleThreadScheduledExecutor(this.e);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public interface TimeLimitExceededListener {
        void onTimeLimitExceeded(@NonNull String str);
    }

    public void startTimer(@NonNull String str, long j, @NonNull TimeLimitExceededListener timeLimitExceededListener) {
        synchronized (this.c) {
            Logger.get().debug(d, String.format("Starting timer for %s", str), new Throwable[0]);
            stopTimer(str);
            WorkTimerRunnable workTimerRunnable = new WorkTimerRunnable(this, str);
            this.a.put(str, workTimerRunnable);
            this.b.put(str, timeLimitExceededListener);
            this.f.schedule(workTimerRunnable, j, TimeUnit.MILLISECONDS);
        }
    }

    public void stopTimer(@NonNull String str) {
        synchronized (this.c) {
            if (this.a.remove(str) != null) {
                Logger.get().debug(d, String.format("Stopping timer for %s", str), new Throwable[0]);
                this.b.remove(str);
            }
        }
    }

    public void onDestroy() {
        if (!this.f.isShutdown()) {
            this.f.shutdownNow();
        }
    }

    @NonNull
    @VisibleForTesting
    public synchronized Map<String, WorkTimerRunnable> getTimerMap() {
        return this.a;
    }

    @NonNull
    @VisibleForTesting
    public synchronized Map<String, TimeLimitExceededListener> getListeners() {
        return this.b;
    }

    @NonNull
    @VisibleForTesting
    public ScheduledExecutorService getExecutorService() {
        return this.f;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes.dex */
    public static class WorkTimerRunnable implements Runnable {
        private final WorkTimer a;
        private final String b;

        WorkTimerRunnable(@NonNull WorkTimer workTimer, @NonNull String str) {
            this.a = workTimer;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.a.c) {
                if (this.a.a.remove(this.b) != null) {
                    TimeLimitExceededListener remove = this.a.b.remove(this.b);
                    if (remove != null) {
                        remove.onTimeLimitExceeded(this.b);
                    }
                } else {
                    Logger.get().debug("WrkTimerRunnable", String.format("Timer with %s is already marked as complete.", this.b), new Throwable[0]);
                }
            }
        }
    }
}
