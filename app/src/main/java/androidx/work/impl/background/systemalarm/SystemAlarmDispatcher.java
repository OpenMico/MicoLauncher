package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.utils.SerialExecutor;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.WorkTimer;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SystemAlarmDispatcher implements ExecutionListener {
    static final String a = Logger.tagWithPrefix("SystemAlarmDispatcher");
    final Context b;
    final CommandHandler c;
    final List<Intent> d;
    Intent e;
    private final TaskExecutor f;
    private final WorkTimer g;
    private final Processor h;
    private final WorkManagerImpl i;
    private final Handler j;
    @Nullable
    private b k;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface b {
        void onAllCommandsCompleted();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SystemAlarmDispatcher(@NonNull Context context) {
        this(context, null, null);
    }

    @VisibleForTesting
    SystemAlarmDispatcher(@NonNull Context context, @Nullable Processor processor, @Nullable WorkManagerImpl workManagerImpl) {
        this.b = context.getApplicationContext();
        this.c = new CommandHandler(this.b);
        this.g = new WorkTimer();
        this.i = workManagerImpl == null ? WorkManagerImpl.getInstance(context) : workManagerImpl;
        this.h = processor == null ? this.i.getProcessor() : processor;
        this.f = this.i.getWorkTaskExecutor();
        this.h.addExecutionListener(this);
        this.d = new ArrayList();
        this.e = null;
        this.j = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        Logger.get().debug(a, "Destroying SystemAlarmDispatcher", new Throwable[0]);
        this.h.removeExecutionListener(this);
        this.g.onDestroy();
        this.k = null;
    }

    @Override // androidx.work.impl.ExecutionListener
    public void onExecuted(@NonNull String str, boolean z) {
        a(new a(this, CommandHandler.a(this.b, str, z), 0));
    }

    @MainThread
    public boolean add(@NonNull Intent intent, int i) {
        boolean z = false;
        Logger.get().debug(a, String.format("Adding command %s (%s)", intent, Integer.valueOf(i)), new Throwable[0]);
        h();
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Logger.get().warning(a, "Unknown command. Ignoring", new Throwable[0]);
            return false;
        } else if ("ACTION_CONSTRAINTS_CHANGED".equals(action) && a("ACTION_CONSTRAINTS_CHANGED")) {
            return false;
        } else {
            intent.putExtra("KEY_START_ID", i);
            synchronized (this.d) {
                if (!this.d.isEmpty()) {
                    z = true;
                }
                this.d.add(intent);
                if (!z) {
                    g();
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull b bVar) {
        if (this.k != null) {
            Logger.get().error(a, "A completion listener for SystemAlarmDispatcher already exists.", new Throwable[0]);
        } else {
            this.k = bVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Processor b() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WorkTimer c() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WorkManagerImpl d() {
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TaskExecutor e() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Runnable runnable) {
        this.j.post(runnable);
    }

    @MainThread
    void f() {
        Logger.get().debug(a, "Checking if commands are complete.", new Throwable[0]);
        h();
        synchronized (this.d) {
            if (this.e != null) {
                Logger.get().debug(a, String.format("Removing command %s", this.e), new Throwable[0]);
                if (this.d.remove(0).equals(this.e)) {
                    this.e = null;
                } else {
                    throw new IllegalStateException("Dequeue-d command is not the first.");
                }
            }
            SerialExecutor backgroundExecutor = this.f.getBackgroundExecutor();
            if (!this.c.a() && this.d.isEmpty() && !backgroundExecutor.hasPendingTasks()) {
                Logger.get().debug(a, "No more commands & intents.", new Throwable[0]);
                if (this.k != null) {
                    this.k.onAllCommandsCompleted();
                }
            } else if (!this.d.isEmpty()) {
                g();
            }
        }
    }

    @MainThread
    private void g() {
        h();
        PowerManager.WakeLock newWakeLock = WakeLocks.newWakeLock(this.b, "ProcessCommand");
        try {
            newWakeLock.acquire();
            this.i.getWorkTaskExecutor().executeOnBackgroundThread(new Runnable() { // from class: androidx.work.impl.background.systemalarm.SystemAlarmDispatcher.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (SystemAlarmDispatcher.this.d) {
                        SystemAlarmDispatcher.this.e = SystemAlarmDispatcher.this.d.get(0);
                    }
                    if (SystemAlarmDispatcher.this.e != null) {
                        String action = SystemAlarmDispatcher.this.e.getAction();
                        int intExtra = SystemAlarmDispatcher.this.e.getIntExtra("KEY_START_ID", 0);
                        Logger.get().debug(SystemAlarmDispatcher.a, String.format("Processing command %s, %s", SystemAlarmDispatcher.this.e, Integer.valueOf(intExtra)), new Throwable[0]);
                        PowerManager.WakeLock newWakeLock2 = WakeLocks.newWakeLock(SystemAlarmDispatcher.this.b, String.format("%s (%s)", action, Integer.valueOf(intExtra)));
                        try {
                            Logger.get().debug(SystemAlarmDispatcher.a, String.format("Acquiring operation wake lock (%s) %s", action, newWakeLock2), new Throwable[0]);
                            newWakeLock2.acquire();
                            SystemAlarmDispatcher.this.c.a(SystemAlarmDispatcher.this.e, intExtra, SystemAlarmDispatcher.this);
                            Logger.get().debug(SystemAlarmDispatcher.a, String.format("Releasing operation wake lock (%s) %s", action, newWakeLock2), new Throwable[0]);
                            newWakeLock2.release();
                            SystemAlarmDispatcher systemAlarmDispatcher = SystemAlarmDispatcher.this;
                            systemAlarmDispatcher.a(new c(systemAlarmDispatcher));
                        } catch (Throwable th) {
                            Logger.get().debug(SystemAlarmDispatcher.a, String.format("Releasing operation wake lock (%s) %s", action, newWakeLock2), new Throwable[0]);
                            newWakeLock2.release();
                            SystemAlarmDispatcher systemAlarmDispatcher2 = SystemAlarmDispatcher.this;
                            systemAlarmDispatcher2.a(new c(systemAlarmDispatcher2));
                            throw th;
                        }
                    }
                }
            });
        } finally {
            newWakeLock.release();
        }
    }

    @MainThread
    private boolean a(@NonNull String str) {
        h();
        synchronized (this.d) {
            for (Intent intent : this.d) {
                if (str.equals(intent.getAction())) {
                    return true;
                }
            }
            return false;
        }
    }

    private void h() {
        if (this.j.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Needs to be invoked on the main thread.");
        }
    }

    /* loaded from: classes.dex */
    static class c implements Runnable {
        private final SystemAlarmDispatcher a;

        c(@NonNull SystemAlarmDispatcher systemAlarmDispatcher) {
            this.a = systemAlarmDispatcher;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.f();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements Runnable {
        private final SystemAlarmDispatcher a;
        private final Intent b;
        private final int c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(@NonNull SystemAlarmDispatcher systemAlarmDispatcher, @NonNull Intent intent, int i) {
            this.a = systemAlarmDispatcher;
            this.b = intent;
            this.c = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.add(this.b, this.c);
        }
    }
}
