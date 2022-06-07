package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class SerialExecutor implements Executor {
    private final Executor b;
    private volatile Runnable d;
    private final ArrayDeque<a> a = new ArrayDeque<>();
    private final Object c = new Object();

    public SerialExecutor(@NonNull Executor executor) {
        this.b = executor;
    }

    @Override // java.util.concurrent.Executor
    public void execute(@NonNull Runnable runnable) {
        synchronized (this.c) {
            this.a.add(new a(this, runnable));
            if (this.d == null) {
                a();
            }
        }
    }

    void a() {
        synchronized (this.c) {
            a poll = this.a.poll();
            this.d = poll;
            if (poll != null) {
                this.b.execute(this.d);
            }
        }
    }

    public boolean hasPendingTasks() {
        boolean z;
        synchronized (this.c) {
            z = !this.a.isEmpty();
        }
        return z;
    }

    @NonNull
    @VisibleForTesting
    public Executor getDelegatedExecutor() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements Runnable {
        final SerialExecutor a;
        final Runnable b;

        a(@NonNull SerialExecutor serialExecutor, @NonNull Runnable runnable) {
            this.a = serialExecutor;
            this.b = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.b.run();
            } finally {
                this.a.a();
            }
        }
    }
}
