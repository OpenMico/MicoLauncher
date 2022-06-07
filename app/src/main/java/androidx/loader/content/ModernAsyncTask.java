package androidx.loader.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
abstract class ModernAsyncTask<Params, Progress, Result> {
    private static b f;
    private static final ThreadFactory a = new ThreadFactory() { // from class: androidx.loader.content.ModernAsyncTask.1
        private final AtomicInteger a = new AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "ModernAsyncTask #" + this.a.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> b = new LinkedBlockingQueue(10);
    public static final Executor c = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, b, a);
    private static volatile Executor g = c;
    private volatile Status j = Status.PENDING;
    final AtomicBoolean d = new AtomicBoolean();
    final AtomicBoolean e = new AtomicBoolean();
    private final c<Params, Result> h = new c<Params, Result>() { // from class: androidx.loader.content.ModernAsyncTask.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Result call() throws Exception {
            ModernAsyncTask.this.e.set(true);
            Result result = null;
            try {
                Process.setThreadPriority(10);
                result = (Result) ModernAsyncTask.this.a(this.b);
                Binder.flushPendingCommands();
                return result;
            } finally {
                ModernAsyncTask.this.d(result);
            }
        }
    };
    private final FutureTask<Result> i = new FutureTask<Result>(this.h) { // from class: androidx.loader.content.ModernAsyncTask.3
        @Override // java.util.concurrent.FutureTask
        protected void done() {
            try {
                ModernAsyncTask.this.c(get());
            } catch (InterruptedException e) {
                Log.w("AsyncTask", e);
            } catch (CancellationException unused) {
                ModernAsyncTask.this.c(null);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occurred while executing doInBackground()", e2.getCause());
            } catch (Throwable th) {
                throw new RuntimeException("An error occurred while executing doInBackground()", th);
            }
        }
    };

    /* loaded from: classes.dex */
    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result a(Params... paramsArr);

    protected void a(Result result) {
    }

    protected void b() {
    }

    protected void b(Progress... progressArr) {
    }

    protected void c() {
    }

    private static Handler a() {
        b bVar;
        synchronized (ModernAsyncTask.class) {
            if (f == null) {
                f = new b();
            }
            bVar = f;
        }
        return bVar;
    }

    void c(Result result) {
        if (!this.e.get()) {
            d(result);
        }
    }

    Result d(Result result) {
        a().obtainMessage(1, new a(this, result)).sendToTarget();
        return result;
    }

    protected void b(Result result) {
        c();
    }

    public final boolean d() {
        return this.d.get();
    }

    public final boolean a(boolean z) {
        this.d.set(true);
        return this.i.cancel(z);
    }

    public final ModernAsyncTask<Params, Progress, Result> a(Executor executor, Params... paramsArr) {
        if (this.j != Status.PENDING) {
            switch (this.j) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
                default:
                    throw new IllegalStateException("We should never reach this state");
            }
        } else {
            this.j = Status.RUNNING;
            b();
            this.h.b = paramsArr;
            executor.execute(this.i);
            return this;
        }
    }

    void e(Result result) {
        if (d()) {
            b((ModernAsyncTask<Params, Progress, Result>) result);
        } else {
            a((ModernAsyncTask<Params, Progress, Result>) result);
        }
        this.j = Status.FINISHED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b extends Handler {
        b() {
            super(Looper.getMainLooper());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            a aVar = (a) message.obj;
            switch (message.what) {
                case 1:
                    aVar.a.e(aVar.b[0]);
                    return;
                case 2:
                    aVar.a.b((Object[]) aVar.b);
                    return;
                default:
                    return;
            }
        }
    }

    /* loaded from: classes.dex */
    private static abstract class c<Params, Result> implements Callable<Result> {
        Params[] b;

        c() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a<Data> {
        final ModernAsyncTask a;
        final Data[] b;

        a(ModernAsyncTask modernAsyncTask, Data... dataArr) {
            this.a = modernAsyncTask;
            this.b = dataArr;
        }
    }
}
