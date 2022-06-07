package kotlinx.coroutines.scheduling;

import com.umeng.analytics.pro.c;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.ExecutorCoroutineDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Dispatcher.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00020+2\u00020,2\u00020\u001fB)\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\nH\u0016¢\u0006\u0004\b\r\u0010\fJ#\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u000e2\n\u0010\u0012\u001a\u00060\u0010j\u0002`\u0011H\u0016¢\u0006\u0004\b\u0013\u0010\u0014J#\u0010\u0013\u001a\u00020\n2\n\u0010\u0012\u001a\u00060\u0010j\u0002`\u00112\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¢\u0006\u0004\b\u0013\u0010\u0017J#\u0010\u0018\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u000e2\n\u0010\u0012\u001a\u00060\u0010j\u0002`\u0011H\u0016¢\u0006\u0004\b\u0018\u0010\u0014J\u001b\u0010\u001a\u001a\u00020\n2\n\u0010\u0019\u001a\u00060\u0010j\u0002`\u0011H\u0016¢\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u001eR\u0016\u0010\"\u001a\u00020\u001f8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010#R\u0016\u0010\u0004\u001a\u00020\u00038\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010$R \u0010&\u001a\f\u0012\b\u0012\u00060\u0010j\u0002`\u00110%8\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b&\u0010'R\u001c\u0010\u0007\u001a\u00020\u00038\u0016@\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\u0007\u0010$\u001a\u0004\b(\u0010)¨\u0006*"}, d2 = {"Lkotlinx/coroutines/scheduling/LimitingDispatcher;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "dispatcher", "", "parallelism", "", "name", "taskMode", "<init>", "(Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;ILjava/lang/String;I)V", "", "afterTask", "()V", "close", "Lkotlin/coroutines/CoroutineContext;", c.R, "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "", "tailDispatch", "(Ljava/lang/Runnable;Z)V", "dispatchYield", "command", "execute", "(Ljava/lang/Runnable;)V", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/scheduling/ExperimentalCoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "executor", "Ljava/lang/String;", "I", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "queue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "getTaskMode", "()I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/scheduling/TaskContext;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
final class a extends ExecutorCoroutineDispatcher implements Executor, TaskContext {
    private static final /* synthetic */ AtomicIntegerFieldUpdater f = AtomicIntegerFieldUpdater.newUpdater(a.class, "inFlightTasks");
    @NotNull
    private final ExperimentalCoroutineDispatcher a;
    private final int b;
    @Nullable
    private final String c;
    private final int d;
    @NotNull
    private final ConcurrentLinkedQueue<Runnable> e = new ConcurrentLinkedQueue<>();
    @NotNull
    private volatile /* synthetic */ int inFlightTasks = 0;

    @Override // kotlinx.coroutines.scheduling.TaskContext
    public int getTaskMode() {
        return this.d;
    }

    public a(@NotNull ExperimentalCoroutineDispatcher experimentalCoroutineDispatcher, int i, @Nullable String str, int i2) {
        this.a = experimentalCoroutineDispatcher;
        this.b = i;
        this.c = str;
        this.d = i2;
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher
    @NotNull
    public Executor getExecutor() {
        return this;
    }

    @Override // java.util.concurrent.Executor
    public void execute(@NotNull Runnable runnable) {
        a(runnable, false);
    }

    @Override // kotlinx.coroutines.ExecutorCoroutineDispatcher, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        throw new IllegalStateException("Close cannot be invoked on LimitingBlockingDispatcher".toString());
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatch(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
        a(runnable, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void a(java.lang.Runnable r3, boolean r4) {
        /*
            r2 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = kotlinx.coroutines.scheduling.a.f
            int r0 = r0.incrementAndGet(r2)
            int r1 = r2.b
            if (r0 > r1) goto L_0x0013
            kotlinx.coroutines.scheduling.ExperimentalCoroutineDispatcher r0 = r2.a
            r1 = r2
            kotlinx.coroutines.scheduling.TaskContext r1 = (kotlinx.coroutines.scheduling.TaskContext) r1
            r0.dispatchWithContext$kotlinx_coroutines_core(r3, r1, r4)
            return
        L_0x0013:
            java.util.concurrent.ConcurrentLinkedQueue<java.lang.Runnable> r0 = r2.e
            r0.add(r3)
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r3 = kotlinx.coroutines.scheduling.a.f
            int r3 = r3.decrementAndGet(r2)
            int r0 = r2.b
            if (r3 < r0) goto L_0x0023
            return
        L_0x0023:
            java.util.concurrent.ConcurrentLinkedQueue<java.lang.Runnable> r3 = r2.e
            java.lang.Object r3 = r3.poll()
            java.lang.Runnable r3 = (java.lang.Runnable) r3
            if (r3 != 0) goto L_0x0000
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.a.a(java.lang.Runnable, boolean):void");
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public void dispatchYield(@NotNull CoroutineContext coroutineContext, @NotNull Runnable runnable) {
        a(runnable, true);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    @NotNull
    public String toString() {
        String str = this.c;
        if (str != null) {
            return str;
        }
        return super.toString() + "[dispatcher = " + this.a + ']';
    }

    @Override // kotlinx.coroutines.scheduling.TaskContext
    public void afterTask() {
        Runnable poll = this.e.poll();
        if (poll != null) {
            this.a.dispatchWithContext$kotlinx_coroutines_core(poll, this, true);
            return;
        }
        f.decrementAndGet(this);
        Runnable poll2 = this.e.poll();
        if (poll2 != null) {
            a(poll2, true);
        }
    }
}
