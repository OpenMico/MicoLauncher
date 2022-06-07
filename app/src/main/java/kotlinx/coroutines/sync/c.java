package kotlinx.coroutines.sync;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Semaphore.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00020\u001eB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\u0007\u001a\u00020\u0006H\u0096@ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\t\u001a\u00020\u0006H\u0082@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\bJ\u001d\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\fH\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0013\u0010\u0012J\u0019\u0010\u0014\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\u0014\u0010\u000eR\u0016\u0010\u0017\u001a\u00020\u00018V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00060\u00188\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0016\u0010\u0002\u001a\u00020\u00018\u0002@\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u001c\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/sync/SemaphoreImpl;", "", "permits", "acquiredPermits", "<init>", "(II)V", "", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireSlowPath", "Lkotlinx/coroutines/CancellableContinuation;", "cont", "", "addAcquireToQueue", "(Lkotlinx/coroutines/CancellableContinuation;)Z", "release", "()V", "tryAcquire", "()Z", "tryResumeNextFromQueue", "tryResumeAcquire", "getAvailablePermits", "()I", "availablePermits", "Lkotlin/Function1;", "", "onCancellationRelease", "Lkotlin/jvm/functions/Function1;", "I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/sync/Semaphore;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class c implements Semaphore {
    @NotNull
    volatile /* synthetic */ int _availablePermits;
    private final int b;
    @NotNull
    private volatile /* synthetic */ long deqIdx = 0;
    @NotNull
    private volatile /* synthetic */ long enqIdx = 0;
    @NotNull
    private final Function1<Throwable, Unit> g;
    @NotNull
    private volatile /* synthetic */ Object head;
    @NotNull
    private volatile /* synthetic */ Object tail;
    private static final /* synthetic */ AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "head");
    private static final /* synthetic */ AtomicLongFieldUpdater d = AtomicLongFieldUpdater.newUpdater(c.class, "deqIdx");
    private static final /* synthetic */ AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "tail");
    private static final /* synthetic */ AtomicLongFieldUpdater f = AtomicLongFieldUpdater.newUpdater(c.class, "enqIdx");
    static final /* synthetic */ AtomicIntegerFieldUpdater a = AtomicIntegerFieldUpdater.newUpdater(c.class, "_availablePermits");

    public c(int i, int i2) {
        this.b = i;
        boolean z = false;
        if (this.b > 0) {
            if (i2 >= 0 && i2 <= this.b) {
                z = true;
            }
            if (z) {
                d dVar = new d(0L, null, 2);
                this.head = dVar;
                this.tail = dVar;
                this._availablePermits = this.b - i2;
                this.g = new a();
                return;
            }
            throw new IllegalArgumentException(Intrinsics.stringPlus("The number of acquired permits should be in 0..", Integer.valueOf(this.b)).toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Semaphore should have at least 1 permit, but had ", Integer.valueOf(this.b)).toString());
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public int getAvailablePermits() {
        return Math.max(this._availablePermits, 0);
    }

    /* compiled from: Semaphore.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "<anonymous parameter 0>", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    static final class a extends Lambda implements Function1<Throwable, Unit> {
        a() {
            super(1);
        }

        public final void a(@NotNull Throwable th) {
            c.this.release();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    @Nullable
    public Object acquire(@NotNull Continuation<? super Unit> continuation) {
        if (a.getAndDecrement(this) > 0) {
            return Unit.INSTANCE;
        }
        Object a2 = a(continuation);
        return a2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? a2 : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v13, types: [kotlinx.coroutines.internal.Segment] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(kotlinx.coroutines.CancellableContinuation<? super kotlin.Unit> r15) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.c.a(kotlinx.coroutines.CancellableContinuation):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v13, types: [kotlinx.coroutines.internal.Segment] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final boolean a() {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.c.a():boolean");
    }

    private final boolean b(CancellableContinuation<? super Unit> cancellableContinuation) {
        Object tryResume = cancellableContinuation.tryResume(Unit.INSTANCE, null, this.g);
        if (tryResume == null) {
            return false;
        }
        cancellableContinuation.completeResume(tryResume);
        return true;
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public boolean tryAcquire() {
        int i;
        do {
            i = this._availablePermits;
            if (i <= 0) {
                return false;
            }
        } while (!a.compareAndSet(this, i, i - 1));
        return true;
    }

    private final Object a(Continuation<? super Unit> continuation) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation;
        while (true) {
            if (!a((CancellableContinuation<? super Unit>) cancellableContinuationImpl)) {
                if (a.getAndDecrement(this) > 0) {
                    cancellableContinuationImpl.resume(Unit.INSTANCE, this.g);
                    break;
                }
            } else {
                break;
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public void release() {
        while (true) {
            int i = this._availablePermits;
            if (i < this.b) {
                if (a.compareAndSet(this, i, i + 1) && (i >= 0 || a())) {
                    return;
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("The number of released permits cannot be greater than ", Integer.valueOf(this.b)).toString());
            }
        }
    }
}
