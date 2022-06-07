package androidx.core.provider;

import android.os.Handler;
import android.os.Process;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RequestExecutor.java */
/* loaded from: classes.dex */
public class e {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void a(@NonNull Executor executor, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
        executor.execute(new c(b.a(), callable, consumer));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T a(@NonNull ExecutorService executorService, @NonNull Callable<T> callable, @IntRange(from = 0) int i) throws InterruptedException {
        try {
            return executorService.submit(callable).get(i, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e2) {
            throw new RuntimeException(e2);
        } catch (TimeoutException unused) {
            throw new InterruptedException(RtspHeaders.Values.TIMEOUT);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ThreadPoolExecutor a(@NonNull String str, int i, @IntRange(from = 0) int i2) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, i2, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new a(str, i));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Executor a(@NonNull Handler handler) {
        return new b(handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RequestExecutor.java */
    /* loaded from: classes.dex */
    public static class b implements Executor {
        private final Handler a;

        b(@NonNull Handler handler) {
            this.a = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            if (!this.a.post((Runnable) Preconditions.checkNotNull(runnable))) {
                throw new RejectedExecutionException(this.a + " is shutting down");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RequestExecutor.java */
    /* loaded from: classes.dex */
    public static class c<T> implements Runnable {
        @NonNull
        private Callable<T> a;
        @NonNull
        private Consumer<T> b;
        @NonNull
        private Handler c;

        c(@NonNull Handler handler, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
            this.a = callable;
            this.b = consumer;
            this.c = handler;
        }

        @Override // java.lang.Runnable
        public void run() {
            final T t;
            try {
                t = this.a.call();
            } catch (Exception unused) {
                t = null;
            }
            final Consumer<T> consumer = this.b;
            this.c.post(new Runnable() { // from class: androidx.core.provider.e.c.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    consumer.accept(t);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RequestExecutor.java */
    /* loaded from: classes.dex */
    public static class a implements ThreadFactory {
        private String a;
        private int b;

        a(@NonNull String str, int i) {
            this.a = str;
            this.b = i;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new C0010a(runnable, this.a, this.b);
        }

        /* compiled from: RequestExecutor.java */
        /* renamed from: androidx.core.provider.e$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        private static class C0010a extends Thread {
            private final int a;

            C0010a(Runnable runnable, String str, int i) {
                super(runnable, str);
                this.a = i;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Process.setThreadPriority(this.a);
                super.run();
            }
        }
    }
}
