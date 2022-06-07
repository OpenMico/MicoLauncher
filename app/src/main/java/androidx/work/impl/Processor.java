package androidx.work.impl;

import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.work.Configuration;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkerWrapper;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class Processor implements ExecutionListener, ForegroundProcessor {
    private static final String a = Logger.tagWithPrefix("Processor");
    private Context c;
    private Configuration d;
    private TaskExecutor e;
    private WorkDatabase f;
    private List<Scheduler> i;
    private Map<String, WorkerWrapper> h = new HashMap();
    private Map<String, WorkerWrapper> g = new HashMap();
    private Set<String> j = new HashSet();
    private final List<ExecutionListener> k = new ArrayList();
    @Nullable
    private PowerManager.WakeLock b = null;
    private final Object l = new Object();

    public Processor(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkDatabase workDatabase, @NonNull List<Scheduler> list) {
        this.c = context;
        this.d = configuration;
        this.e = taskExecutor;
        this.f = workDatabase;
        this.i = list;
    }

    public boolean startWork(@NonNull String str) {
        return startWork(str, null);
    }

    public boolean startWork(@NonNull String str, @Nullable WorkerParameters.RuntimeExtras runtimeExtras) {
        synchronized (this.l) {
            if (isEnqueued(str)) {
                Logger.get().debug(a, String.format("Work %s is already enqueued for processing", str), new Throwable[0]);
                return false;
            }
            WorkerWrapper build = new WorkerWrapper.Builder(this.c, this.d, this.e, this, this.f, str).withSchedulers(this.i).withRuntimeExtras(runtimeExtras).build();
            ListenableFuture<Boolean> future = build.getFuture();
            future.addListener(new a(this, str, future), this.e.getMainThreadExecutor());
            this.h.put(str, build);
            this.e.getBackgroundExecutor().execute(build);
            Logger.get().debug(a, String.format("%s: processing %s", getClass().getSimpleName(), str), new Throwable[0]);
            return true;
        }
    }

    @Override // androidx.work.impl.foreground.ForegroundProcessor
    public void startForeground(@NonNull String str, @NonNull ForegroundInfo foregroundInfo) {
        synchronized (this.l) {
            Logger.get().info(a, String.format("Moving WorkSpec (%s) to the foreground", str), new Throwable[0]);
            WorkerWrapper remove = this.h.remove(str);
            if (remove != null) {
                if (this.b == null) {
                    this.b = WakeLocks.newWakeLock(this.c, "ProcessorForegroundLck");
                    this.b.acquire();
                }
                this.g.put(str, remove);
                ContextCompat.startForegroundService(this.c, SystemForegroundDispatcher.createStartForegroundIntent(this.c, str, foregroundInfo));
            }
        }
    }

    public boolean stopForegroundWork(@NonNull String str) {
        boolean a2;
        synchronized (this.l) {
            Logger.get().debug(a, String.format("Processor stopping foreground work %s", str), new Throwable[0]);
            a2 = a(str, this.g.remove(str));
        }
        return a2;
    }

    public boolean stopWork(@NonNull String str) {
        boolean a2;
        synchronized (this.l) {
            Logger.get().debug(a, String.format("Processor stopping background work %s", str), new Throwable[0]);
            a2 = a(str, this.h.remove(str));
        }
        return a2;
    }

    public boolean stopAndCancelWork(@NonNull String str) {
        boolean a2;
        synchronized (this.l) {
            boolean z = true;
            Logger.get().debug(a, String.format("Processor cancelling %s", str), new Throwable[0]);
            this.j.add(str);
            WorkerWrapper remove = this.g.remove(str);
            if (remove == null) {
                z = false;
            }
            if (remove == null) {
                remove = this.h.remove(str);
            }
            a2 = a(str, remove);
            if (z) {
                a();
            }
        }
        return a2;
    }

    @Override // androidx.work.impl.foreground.ForegroundProcessor
    public void stopForeground(@NonNull String str) {
        synchronized (this.l) {
            this.g.remove(str);
            a();
        }
    }

    public boolean isCancelled(@NonNull String str) {
        boolean contains;
        synchronized (this.l) {
            contains = this.j.contains(str);
        }
        return contains;
    }

    public boolean hasWork() {
        boolean z;
        synchronized (this.l) {
            if (this.h.isEmpty() && this.g.isEmpty()) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    public boolean isEnqueued(@NonNull String str) {
        boolean z;
        synchronized (this.l) {
            if (!this.h.containsKey(str) && !this.g.containsKey(str)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    public boolean isEnqueuedInForeground(@NonNull String str) {
        boolean containsKey;
        synchronized (this.l) {
            containsKey = this.g.containsKey(str);
        }
        return containsKey;
    }

    public void addExecutionListener(@NonNull ExecutionListener executionListener) {
        synchronized (this.l) {
            this.k.add(executionListener);
        }
    }

    public void removeExecutionListener(@NonNull ExecutionListener executionListener) {
        synchronized (this.l) {
            this.k.remove(executionListener);
        }
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        synchronized (this.l) {
            this.h.remove(str);
            Logger.get().debug(a, String.format("%s %s executed; reschedule = %s", getClass().getSimpleName(), str, Boolean.valueOf(z)), new Throwable[0]);
            for (ExecutionListener executionListener : this.k) {
                executionListener.onExecuted(str, z);
            }
        }
    }

    private void a() {
        synchronized (this.l) {
            if (!(!this.g.isEmpty())) {
                this.c.startService(SystemForegroundDispatcher.createStopForegroundIntent(this.c));
                if (this.b != null) {
                    this.b.release();
                    this.b = null;
                }
            }
        }
    }

    private static boolean a(@NonNull String str, @Nullable WorkerWrapper workerWrapper) {
        if (workerWrapper != null) {
            workerWrapper.interrupt();
            Logger.get().debug(a, String.format("WorkerWrapper interrupted for %s", str), new Throwable[0]);
            return true;
        }
        Logger.get().debug(a, String.format("WorkerWrapper could not be found for %s", str), new Throwable[0]);
        return false;
    }

    /* loaded from: classes.dex */
    public static class a implements Runnable {
        @NonNull
        private ExecutionListener a;
        @NonNull
        private String b;
        @NonNull
        private ListenableFuture<Boolean> c;

        a(@NonNull ExecutionListener executionListener, @NonNull String str, @NonNull ListenableFuture<Boolean> listenableFuture) {
            this.a = executionListener;
            this.b = str;
            this.c = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            try {
                z = this.c.get().booleanValue();
            } catch (InterruptedException | ExecutionException unused) {
                z = true;
            }
            this.a.onExecuted(this.b, z);
        }
    }
}
