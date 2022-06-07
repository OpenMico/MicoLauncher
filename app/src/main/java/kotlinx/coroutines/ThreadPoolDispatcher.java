package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ThreadPoolDispatcher.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/ThreadPoolDispatcher;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcherBase;", "nThreads", "", "name", "", "(ILjava/lang/String;)V", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "threadNo", "Ljava/util/concurrent/atomic/AtomicInteger;", "close", "", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ThreadPoolDispatcher extends ExecutorCoroutineDispatcherBase {
    private final int a;
    @NotNull
    private final String b;
    @NotNull
    private final AtomicInteger c = new AtomicInteger();
    @NotNull
    private final Executor d;

    public ThreadPoolDispatcher(int i, @NotNull String str) {
        this.a = i;
        this.b = str;
        this.d = Executors.newScheduledThreadPool(this.a, new ThreadFactory() { // from class: kotlinx.coroutines.-$$Lambda$ThreadPoolDispatcher$2dUq_xnEOH2JTI6JRaXOmEYCEaA
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread a;
                a = ThreadPoolDispatcher.a(ThreadPoolDispatcher.this, runnable);
                return a;
            }
        });
        initFutureCancellation$kotlinx_coroutines_core();
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    @NotNull
    public Executor getExecutor() {
        return this.d;
    }

    public static final Thread a(ThreadPoolDispatcher threadPoolDispatcher, Runnable runnable) {
        String str;
        if (threadPoolDispatcher.a == 1) {
            str = threadPoolDispatcher.b;
        } else {
            str = threadPoolDispatcher.b + '-' + threadPoolDispatcher.c.incrementAndGet();
        }
        return new PoolThread(threadPoolDispatcher, runnable, str);
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcherBase, kotlinx.coroutines.ExecutorCoroutineDispatcher, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        ((ExecutorService) getExecutor()).shutdown();
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcherBase, kotlinx.coroutines.CoroutineDispatcher
    @NotNull
    public String toString() {
        return "ThreadPoolDispatcher[" + this.a + ", " + this.b + ']';
    }
}
