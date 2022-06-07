package kotlinx.coroutines;

import com.umeng.analytics.pro.c;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.time.DurationKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: EventLoop.common.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b \u0018\u00002\u0002092\u00020::\u00044567B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u000f\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0004\u0010\u0002J\u0017\u0010\u0007\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u0006H\u0002¢\u0006\u0004\b\u0007\u0010\bJ!\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t2\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0004\b\f\u0010\rJ\u0019\u0010\u000f\u001a\u00020\u00032\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0012\u001a\u00020\u00112\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006H\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0017\u0010\u0002J\u000f\u0010\u0018\u001a\u00020\u0003H\u0004¢\u0006\u0004\b\u0018\u0010\u0002J\u001d\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001a¢\u0006\u0004\b\u001c\u0010\u001dJ\u001f\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\u00142\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006H\u0004¢\u0006\u0004\b#\u0010$J%\u0010'\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00142\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030%H\u0016¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u001aH\u0002¢\u0006\u0004\b)\u0010*J\u000f\u0010+\u001a\u00020\u0003H\u0014¢\u0006\u0004\b+\u0010\u0002R$\u0010-\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00118B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0016\u00101\u001a\u00020\u00118T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b1\u0010.R\u0016\u00103\u001a\u00020\u00148T@\u0014X\u0094\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0016¨\u00068"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", c.R, "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", com.xiaomi.onetrack.api.b.p, "isCompleted", "()Z", "setCompleted", "(Z)V", "isEmpty", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_queue");
    private static final /* synthetic */ AtomicReferenceFieldUpdater b = AtomicReferenceFieldUpdater.newUpdater(EventLoopImplBase.class, Object.class, "_delayed");
    @NotNull
    private volatile /* synthetic */ Object _queue = null;
    @NotNull
    private volatile /* synthetic */ Object _delayed = null;
    @NotNull
    private volatile /* synthetic */ int _isCompleted = 0;

    @Override // kotlinx.coroutines.Delay
    @Nullable
    public Object delay(long j, @NotNull Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, j, continuation);
    }

    @NotNull
    public DisposableHandle invokeOnTimeout(long j, @NotNull Runnable runnable, @NotNull CoroutineContext coroutineContext) {
        return Delay.DefaultImpls.invokeOnTimeout(this, j, runnable, coroutineContext);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [int, boolean] */
    public final boolean a() {
        return this._isCompleted;
    }

    private final void a(boolean z) {
        this._isCompleted = z ? 1 : 0;
    }

    @Override // kotlinx.coroutines.EventLoop
    protected boolean isEmpty() {
        Symbol symbol;
        if (!isUnconfinedQueueEmpty()) {
            return false;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue != null && !delayedTaskQueue.isEmpty()) {
            return false;
        }
        Object obj = this._queue;
        if (obj == null) {
            return true;
        }
        if (obj instanceof LockFreeTaskQueueCore) {
            return ((LockFreeTaskQueueCore) obj).isEmpty();
        }
        symbol = EventLoop_commonKt.b;
        return obj == symbol;
    }

    @Override // kotlinx.coroutines.EventLoop
    protected long getNextTime() {
        Symbol symbol;
        if (super.getNextTime() == 0) {
            return 0L;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (!(obj instanceof LockFreeTaskQueueCore)) {
                symbol = EventLoop_commonKt.b;
                return obj == symbol ? Long.MAX_VALUE : 0L;
            } else if (!((LockFreeTaskQueueCore) obj).isEmpty()) {
                return 0L;
            }
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        DelayedTask peek = delayedTaskQueue == null ? null : delayedTaskQueue.peek();
        if (peek == null) {
            return Long.MAX_VALUE;
        }
        long j = peek.nanoTime;
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        return RangesKt.coerceAtLeast(j - (timeSource == null ? System.nanoTime() : timeSource.nanoTime()), 0L);
    }

    @Override // kotlinx.coroutines.EventLoop
    protected void shutdown() {
        ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
        a(true);
        c();
        do {
        } while (processNextEvent() <= 0);
        d();
    }

    @Override // kotlinx.coroutines.Delay
    public void scheduleResumeAfterDelay(long j, @NotNull CancellableContinuation<? super Unit> cancellableContinuation) {
        long delayToNanos = EventLoop_commonKt.delayToNanos(j);
        if (delayToNanos < DurationKt.MAX_MILLIS) {
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            long nanoTime = timeSource == null ? System.nanoTime() : timeSource.nanoTime();
            a aVar = new a(delayToNanos + nanoTime, cancellableContinuation);
            CancellableContinuationKt.disposeOnCancellation(cancellableContinuation, aVar);
            schedule(nanoTime, aVar);
        }
    }

    @NotNull
    protected final DisposableHandle scheduleInvokeOnTimeout(long j, @NotNull Runnable runnable) {
        long delayToNanos = EventLoop_commonKt.delayToNanos(j);
        if (delayToNanos >= DurationKt.MAX_MILLIS) {
            return NonDisposableHandle.INSTANCE;
        }
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        long nanoTime = timeSource == null ? System.nanoTime() : timeSource.nanoTime();
        b bVar = new b(delayToNanos + nanoTime, runnable);
        schedule(nanoTime, bVar);
        return bVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005f  */
    @Override // kotlinx.coroutines.EventLoop
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long processNextEvent() {
        /*
            r10 = this;
            boolean r0 = r10.processUnconfinedEvent()
            r1 = 0
            if (r0 == 0) goto L_0x0009
            return r1
        L_0x0009:
            java.lang.Object r0 = r10._delayed
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0
            if (r0 == 0) goto L_0x0055
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L_0x0055
            kotlinx.coroutines.AbstractTimeSource r3 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource()
            if (r3 != 0) goto L_0x0020
            long r3 = java.lang.System.nanoTime()
            goto L_0x0024
        L_0x0020:
            long r3 = r3.nanoTime()
        L_0x0024:
            r5 = r0
            kotlinx.coroutines.internal.ThreadSafeHeap r5 = (kotlinx.coroutines.internal.ThreadSafeHeap) r5
            monitor-enter(r5)
            kotlinx.coroutines.internal.ThreadSafeHeapNode r6 = r5.firstImpl()     // Catch: all -> 0x0052
            r7 = 0
            if (r6 != 0) goto L_0x0031
            monitor-exit(r5)
            goto L_0x004d
        L_0x0031:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r6 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r6     // Catch: all -> 0x0052
            boolean r8 = r6.timeToExecute(r3)     // Catch: all -> 0x0052
            r9 = 0
            if (r8 == 0) goto L_0x0041
            java.lang.Runnable r6 = (java.lang.Runnable) r6     // Catch: all -> 0x0052
            boolean r6 = r10.a(r6)     // Catch: all -> 0x0052
            goto L_0x0042
        L_0x0041:
            r6 = r9
        L_0x0042:
            if (r6 == 0) goto L_0x004a
            kotlinx.coroutines.internal.ThreadSafeHeapNode r6 = r5.removeAtImpl(r9)     // Catch: all -> 0x0052
            r7 = r6
            goto L_0x004c
        L_0x004a:
            kotlinx.coroutines.internal.ThreadSafeHeapNode r7 = (kotlinx.coroutines.internal.ThreadSafeHeapNode) r7     // Catch: all -> 0x0052
        L_0x004c:
            monitor-exit(r5)
        L_0x004d:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r7 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r7
            if (r7 != 0) goto L_0x0024
            goto L_0x0055
        L_0x0052:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        L_0x0055:
            java.lang.Runnable r0 = r10.b()
            if (r0 == 0) goto L_0x005f
            r0.run()
            return r1
        L_0x005f:
            long r0 = r10.getNextTime()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.processNextEvent():long");
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
        enqueue(runnable);
    }

    public final void enqueue(@NotNull Runnable runnable) {
        if (a(runnable)) {
            unpark();
        } else {
            DefaultExecutor.INSTANCE.enqueue(runnable);
        }
    }

    private final void c() {
        Symbol symbol;
        Symbol symbol2;
        if (!DebugKt.getASSERTIONS_ENABLED() || a()) {
            while (true) {
                Object obj = this._queue;
                if (obj == null) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
                    symbol = EventLoop_commonKt.b;
                    if (atomicReferenceFieldUpdater.compareAndSet(this, null, symbol)) {
                        return;
                    }
                } else if (obj instanceof LockFreeTaskQueueCore) {
                    ((LockFreeTaskQueueCore) obj).close();
                    return;
                } else {
                    symbol2 = EventLoop_commonKt.b;
                    if (obj != symbol2) {
                        LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                        if (obj != null) {
                            lockFreeTaskQueueCore.addLast((Runnable) obj);
                            if (a.compareAndSet(this, obj, lockFreeTaskQueueCore)) {
                                return;
                            }
                        } else {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
                        }
                    } else {
                        return;
                    }
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final void schedule(long j, @NotNull DelayedTask delayedTask) {
        switch (a(j, delayedTask)) {
            case 0:
                if (a(delayedTask)) {
                    unpark();
                    return;
                }
                return;
            case 1:
                reschedule(j, delayedTask);
                return;
            case 2:
                return;
            default:
                throw new IllegalStateException("unexpected result".toString());
        }
    }

    private final boolean a(DelayedTask delayedTask) {
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        return (delayedTaskQueue == null ? null : delayedTaskQueue.peek()) == delayedTask;
    }

    private final int a(long j, DelayedTask delayedTask) {
        if (a()) {
            return 1;
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue == null) {
            EventLoopImplBase eventLoopImplBase = this;
            b.compareAndSet(eventLoopImplBase, null, new DelayedTaskQueue(j));
            delayedTaskQueue = (DelayedTaskQueue) eventLoopImplBase._delayed;
            Intrinsics.checkNotNull(delayedTaskQueue);
        }
        return delayedTask.scheduleTask(j, delayedTaskQueue, this);
    }

    protected final void resetAll() {
        this._queue = null;
        this._delayed = null;
    }

    private final void d() {
        AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
        long nanoTime = timeSource == null ? System.nanoTime() : timeSource.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            DelayedTask removeFirstOrNull = delayedTaskQueue == null ? null : delayedTaskQueue.removeFirstOrNull();
            if (removeFirstOrNull != null) {
                reschedule(nanoTime, removeFirstOrNull);
            } else {
                return;
            }
        }
    }

    /* compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b \u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0000H\u0096\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\b\u0010$\u001a\u00020%H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R0\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", com.xiaomi.onetrack.api.b.p, "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", MiSoundBoxCommandExtras.INDEX, "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static abstract class DelayedTask implements Comparable<DelayedTask>, Runnable, DisposableHandle, ThreadSafeHeapNode {
        @Nullable
        private Object a;
        private int b = -1;
        @JvmField
        public long nanoTime;

        public DelayedTask(long j) {
            this.nanoTime = j;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        @Nullable
        public ThreadSafeHeap<?> getHeap() {
            Object obj = this.a;
            if (obj instanceof ThreadSafeHeap) {
                return (ThreadSafeHeap) obj;
            }
            return null;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setHeap(@Nullable ThreadSafeHeap<?> threadSafeHeap) {
            Symbol symbol;
            Object obj = this.a;
            symbol = EventLoop_commonKt.a;
            if (obj != symbol) {
                this.a = threadSafeHeap;
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public int getIndex() {
            return this.b;
        }

        @Override // kotlinx.coroutines.internal.ThreadSafeHeapNode
        public void setIndex(int i) {
            this.b = i;
        }

        public int compareTo(@NotNull DelayedTask delayedTask) {
            int i = ((this.nanoTime - delayedTask.nanoTime) > 0L ? 1 : ((this.nanoTime - delayedTask.nanoTime) == 0L ? 0 : -1));
            if (i > 0) {
                return 1;
            }
            return i < 0 ? -1 : 0;
        }

        public final boolean timeToExecute(long j) {
            return j - this.nanoTime >= 0;
        }

        public final synchronized int scheduleTask(long j, @NotNull DelayedTaskQueue delayedTaskQueue, @NotNull EventLoopImplBase eventLoopImplBase) {
            Symbol symbol;
            Object obj = this.a;
            symbol = EventLoop_commonKt.a;
            if (obj == symbol) {
                return 2;
            }
            DelayedTaskQueue delayedTaskQueue2 = delayedTaskQueue;
            synchronized (delayedTaskQueue2) {
                DelayedTask firstImpl = delayedTaskQueue2.firstImpl();
                if (eventLoopImplBase.a()) {
                    return 1;
                }
                if (firstImpl == null) {
                    delayedTaskQueue.timeNow = j;
                } else {
                    long j2 = firstImpl.nanoTime;
                    if (j2 - j < 0) {
                        j = j2;
                    }
                    if (j - delayedTaskQueue.timeNow > 0) {
                        delayedTaskQueue.timeNow = j;
                    }
                }
                if (this.nanoTime - delayedTaskQueue.timeNow < 0) {
                    this.nanoTime = delayedTaskQueue.timeNow;
                }
                delayedTaskQueue2.addImpl(this);
                return 0;
            }
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final synchronized void dispose() {
            Symbol symbol;
            Symbol symbol2;
            Object obj = this.a;
            symbol = EventLoop_commonKt.a;
            if (obj != symbol) {
                DelayedTaskQueue delayedTaskQueue = obj instanceof DelayedTaskQueue ? (DelayedTaskQueue) obj : null;
                if (delayedTaskQueue != null) {
                    delayedTaskQueue.remove(this);
                }
                symbol2 = EventLoop_commonKt.a;
                this.a = symbol2;
            }
        }

        @NotNull
        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + ']';
        }
    }

    /* compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private final class a extends DelayedTask {
        @NotNull
        private final CancellableContinuation<Unit> b;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public a(long j, CancellableContinuation<? super Unit> cancellableContinuation) {
            super(j);
            EventLoopImplBase.this = r1;
            this.b = cancellableContinuation;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.b.resumeUndispatched(EventLoopImplBase.this, Unit.INSTANCE);
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return Intrinsics.stringPlus(super.toString(), this.b);
        }
    }

    /* compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    private static final class b extends DelayedTask {
        @NotNull
        private final Runnable a;

        public b(long j, @NotNull Runnable runnable) {
            super(j);
            this.a = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.run();
        }

        @Override // kotlinx.coroutines.EventLoopImplBase.DelayedTask
        @NotNull
        public String toString() {
            return Intrinsics.stringPlus(super.toString(), this.a);
        }
    }

    /* compiled from: EventLoop.common.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class DelayedTaskQueue extends ThreadSafeHeap<DelayedTask> {
        @JvmField
        public long timeNow;

        public DelayedTaskQueue(long j) {
            this.timeNow = j;
        }
    }

    private final boolean a(Runnable runnable) {
        Symbol symbol;
        while (true) {
            Object obj = this._queue;
            if (a()) {
                return false;
            }
            if (obj == null) {
                if (a.compareAndSet(this, null, runnable)) {
                    return true;
                }
            } else if (!(obj instanceof LockFreeTaskQueueCore)) {
                symbol = EventLoop_commonKt.b;
                if (obj == symbol) {
                    return false;
                }
                LockFreeTaskQueueCore lockFreeTaskQueueCore = new LockFreeTaskQueueCore(8, true);
                if (obj != null) {
                    lockFreeTaskQueueCore.addLast((Runnable) obj);
                    lockFreeTaskQueueCore.addLast(runnable);
                    if (a.compareAndSet(this, obj, lockFreeTaskQueueCore)) {
                        return true;
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
                }
            } else if (obj != null) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore2 = (LockFreeTaskQueueCore) obj;
                switch (lockFreeTaskQueueCore2.addLast(runnable)) {
                    case 0:
                        return true;
                    case 1:
                        a.compareAndSet(this, obj, lockFreeTaskQueueCore2.next());
                        continue;
                    case 2:
                        return false;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeTaskQueueCore<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }>{ kotlinx.coroutines.EventLoop_commonKt.Queue<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }> }");
            }
        }
    }

    private final Runnable b() {
        Symbol symbol;
        while (true) {
            Object obj = this._queue;
            if (obj == null) {
                return null;
            }
            if (!(obj instanceof LockFreeTaskQueueCore)) {
                symbol = EventLoop_commonKt.b;
                if (obj == symbol) {
                    return null;
                }
                if (a.compareAndSet(this, obj, null)) {
                    if (obj != null) {
                        return (Runnable) obj;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }");
                }
            } else if (obj != null) {
                LockFreeTaskQueueCore lockFreeTaskQueueCore = (LockFreeTaskQueueCore) obj;
                Object removeFirstOrNull = lockFreeTaskQueueCore.removeFirstOrNull();
                if (removeFirstOrNull != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                    return (Runnable) removeFirstOrNull;
                }
                a.compareAndSet(this, obj, lockFreeTaskQueueCore.next());
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeTaskQueueCore<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }>{ kotlinx.coroutines.EventLoop_commonKt.Queue<java.lang.Runnable{ kotlinx.coroutines.RunnableKt.Runnable }> }");
            }
        }
    }
}
