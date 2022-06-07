package kotlinx.coroutines;

import com.umeng.analytics.pro.c;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultExecutor.kt */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÀ\u0002\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\b\u0010\u001d\u001a\u00020\u0010H\u0002J\r\u0010\u001e\u001a\u00020\u001cH\u0000¢\u0006\u0002\b\u001fJ$\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\b2\n\u0010#\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010$\u001a\u00020%H\u0016J\b\u0010&\u001a\u00020\u0014H\u0002J\b\u0010'\u001a\u00020\u001cH\u0016J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0011\u0010\u0004R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006*"}, d2 = {"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "ACTIVE", "", "DEFAULT_KEEP_ALIVE", "", "FRESH", "KEEP_ALIVE_NANOS", "SHUTDOWN_ACK", "SHUTDOWN_REQ", "THREAD_NAME", "", "_thread", "Ljava/lang/Thread;", "get_thread$annotations", "debugStatus", "isShutdownRequested", "", "()Z", "isThreadPresent", "isThreadPresent$kotlinx_coroutines_core", "thread", "getThread", "()Ljava/lang/Thread;", "acknowledgeShutdownIfNeeded", "", "createThreadSync", "ensureStarted", "ensureStarted$kotlinx_coroutines_core", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", c.R, "Lkotlin/coroutines/CoroutineContext;", "notifyStartup", "run", "shutdown", RtspHeaders.Values.TIMEOUT, "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class DefaultExecutor extends EventLoopImplBase implements Runnable {
    @NotNull
    public static final DefaultExecutor INSTANCE = new DefaultExecutor();
    @NotNull
    public static final String THREAD_NAME = "kotlinx.coroutines.DefaultExecutor";
    @Nullable
    private static volatile Thread _thread;
    private static final long a;
    private static volatile int debugStatus;

    private DefaultExecutor() {
    }

    static {
        Long l;
        EventLoop.incrementUseCount$default(INSTANCE, false, 1, null);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        a = timeUnit.toNanos(l.longValue());
    }

    @Override // kotlinx.coroutines.EventLoopImplPlatform
    @NotNull
    protected Thread getThread() {
        Thread thread = _thread;
        return thread == null ? b() : thread;
    }

    private final boolean a() {
        int i = debugStatus;
        return i == 2 || i == 3;
    }

    @Override // kotlinx.coroutines.EventLoopImplBase, kotlinx.coroutines.Delay
    @NotNull
    public DisposableHandle invokeOnTimeout(long j, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
        return scheduleInvokeOnTimeout(j, runnable);
    }

    @Override // java.lang.Runnable
    public void run() {
        ThreadLocalEventLoop.INSTANCE.setEventLoop$kotlinx_coroutines_core(this);
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        if (timeSource != null) {
            timeSource.registerTimeLoopThread();
        }
        try {
            if (c()) {
                long j = Long.MAX_VALUE;
                while (true) {
                    Thread.interrupted();
                    long processNextEvent = processNextEvent();
                    if (processNextEvent == Long.MAX_VALUE) {
                        AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
                        long nanoTime = timeSource2 == null ? System.nanoTime() : timeSource2.nanoTime();
                        if (j == Long.MAX_VALUE) {
                            j = a + nanoTime;
                        }
                        long j2 = j - nanoTime;
                        if (j2 <= 0) {
                            _thread = null;
                            d();
                            AbstractTimeSource timeSource3 = AbstractTimeSourceKt.getTimeSource();
                            if (timeSource3 != null) {
                                timeSource3.unregisterTimeLoopThread();
                            }
                            if (!isEmpty()) {
                                getThread();
                                return;
                            }
                            return;
                        }
                        processNextEvent = RangesKt.coerceAtMost(processNextEvent, j2);
                    } else {
                        j = Long.MAX_VALUE;
                    }
                    if (processNextEvent > 0) {
                        if (a()) {
                            _thread = null;
                            d();
                            AbstractTimeSource timeSource4 = AbstractTimeSourceKt.getTimeSource();
                            if (timeSource4 != null) {
                                timeSource4.unregisterTimeLoopThread();
                            }
                            if (!isEmpty()) {
                                getThread();
                                return;
                            }
                            return;
                        }
                        AbstractTimeSource timeSource5 = AbstractTimeSourceKt.getTimeSource();
                        if (timeSource5 == null) {
                            LockSupport.parkNanos(this, processNextEvent);
                        } else {
                            timeSource5.parkNanos(this, processNextEvent);
                        }
                    }
                }
            }
        } finally {
            _thread = null;
            d();
            AbstractTimeSource timeSource6 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource6 != null) {
                timeSource6.unregisterTimeLoopThread();
            }
            if (!isEmpty()) {
                getThread();
            }
        }
    }

    private final synchronized Thread b() {
        Thread thread;
        thread = _thread;
        if (thread == null) {
            thread = new Thread(this, THREAD_NAME);
            DefaultExecutor defaultExecutor = INSTANCE;
            _thread = thread;
            thread.setDaemon(true);
            thread.start();
        }
        return thread;
    }

    public final synchronized void ensureStarted$kotlinx_coroutines_core() {
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(_thread == null)) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(debugStatus == 0 || debugStatus == 3)) {
                z = false;
            }
            throw new AssertionError();
        }
        debugStatus = 0;
        b();
        while (debugStatus == 0) {
            wait();
        }
    }

    private final synchronized boolean c() {
        if (a()) {
            return false;
        }
        debugStatus = 1;
        notifyAll();
        return true;
    }

    public final synchronized void shutdown(long j) {
        long currentTimeMillis = System.currentTimeMillis() + j;
        if (!a()) {
            debugStatus = 2;
        }
        while (debugStatus != 3 && _thread != null) {
            Thread thread = _thread;
            if (thread != null) {
                AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
                if (timeSource == null) {
                    LockSupport.unpark(thread);
                } else {
                    timeSource.unpark(thread);
                }
            }
            if (currentTimeMillis - System.currentTimeMillis() <= 0) {
                break;
            }
            wait(j);
        }
        debugStatus = 0;
    }

    private final synchronized void d() {
        if (a()) {
            debugStatus = 3;
            resetAll();
            notifyAll();
        }
    }

    public final boolean isThreadPresent$kotlinx_coroutines_core() {
        return _thread != null;
    }
}
