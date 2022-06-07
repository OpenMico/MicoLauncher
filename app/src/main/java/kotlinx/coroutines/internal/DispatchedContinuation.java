package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.c;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.onetrack.api.b;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletedWithCancellation;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DispatchedContinuation.kt */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000P2\u00060@j\u0002`A2\b\u0012\u0004\u0012\u00028\u00000\u0004B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ!\u0010\u0011\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0010¢\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0012¢\u0006\u0004\b\u0013\u0010\u0014J\u001f\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00028\u0000H\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001d\u001a\n\u0018\u00010\u001bj\u0004\u0018\u0001`\u001cH\u0016¢\u0006\u0004\b\u001d\u0010\u001eJ\u0019\u0010!\u001a\u00020 2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u0012¢\u0006\u0004\b!\u0010\"J\u0015\u0010#\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0004\b#\u0010$J\r\u0010%\u001a\u00020\b¢\u0006\u0004\b%\u0010\nJH\u0010,\u001a\u00020\b2\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&2%\b\b\u0010+\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b)\u0012\b\b*\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b\u0018\u00010(H\u0086\bø\u0001\u0000¢\u0006\u0004\b,\u0010-J\u001a\u0010/\u001a\u00020 2\b\u0010.\u001a\u0004\u0018\u00010\u000bH\u0086\b¢\u0006\u0004\b/\u00100J!\u00101\u001a\u00020\b2\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&H\u0086\bø\u0001\u0000¢\u0006\u0004\b1\u00102J \u00103\u001a\u00020\b2\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&H\u0016ø\u0001\u0000¢\u0006\u0004\b3\u00102J\u0011\u00106\u001a\u0004\u0018\u00010\u000bH\u0010¢\u0006\u0004\b4\u00105J\u000f\u00108\u001a\u000207H\u0016¢\u0006\u0004\b8\u00109J\u001b\u0010;\u001a\u0004\u0018\u00010\r2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030:¢\u0006\u0004\b;\u0010<R\u001e\u0010=\u001a\u0004\u0018\u00010\u000b8\u0000@\u0000X\u0081\u000e¢\u0006\f\n\u0004\b=\u0010>\u0012\u0004\b?\u0010\nR\u001e\u0010D\u001a\n\u0018\u00010@j\u0004\u0018\u0001`A8V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0016\u0010\u0016\u001a\u00020\u00158\u0016@\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\bE\u0010FR\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0006@\u0007X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010GR\u0016\u0010H\u001a\u00020\u000b8\u0000@\u0001X\u0081\u0004¢\u0006\u0006\n\u0004\bH\u0010>R\u001c\u0010K\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048P@\u0010X\u0090\u0004¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0016\u0010\u0003\u001a\u00020\u00028\u0006@\u0007X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010LR\u0019\u0010N\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00128F@\u0006¢\u0006\u0006\u001a\u0004\bM\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006O"}, d2 = {"Lkotlinx/coroutines/internal/DispatchedContinuation;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CoroutineDispatcher;", "dispatcher", "Lkotlin/coroutines/Continuation;", "continuation", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "", "awaitReusability", "()V", "", "takenState", "", "cause", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "Lkotlinx/coroutines/CancellableContinuationImpl;", "claimReusableCancellableContinuation", "()Lkotlinx/coroutines/CancellableContinuationImpl;", "Lkotlin/coroutines/CoroutineContext;", c.R, b.p, "dispatchYield$kotlinx_coroutines_core", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "dispatchYield", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "requester", "", "isReusable", "(Lkotlinx/coroutines/CancellableContinuationImpl;)Z", "postponeCancellation", "(Ljava/lang/Throwable;)Z", "release", "Lkotlin/Result;", "result", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "onCancellation", "resumeCancellableWith", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", XiaomiOAuthConstants.EXTRA_STATE_2, "resumeCancelled", "(Ljava/lang/Object;)Z", "resumeUndispatchedWith", "(Ljava/lang/Object;)V", "resumeWith", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/CancellableContinuation;", "tryReleaseClaimedContinuation", "(Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Throwable;", "_state", "Ljava/lang/Object;", "get_state$kotlinx_coroutines_core$annotations", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "countOrElement", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "delegate", "Lkotlinx/coroutines/CoroutineDispatcher;", "getReusableCancellableContinuation", "reusableCancellableContinuation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class DispatchedContinuation<T> extends DispatchedTask<T> implements Continuation<T>, CoroutineStackFrame {
    private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(DispatchedContinuation.class, Object.class, "_reusableCancellableContinuation");
    @JvmField
    @NotNull
    public final Continuation<T> continuation;
    @JvmField
    @NotNull
    public final CoroutineDispatcher dispatcher;
    @JvmField
    @Nullable
    public Object _state = DispatchedContinuationKt.access$getUNDEFINED$p();
    @JvmField
    @NotNull
    public final Object countOrElement = ThreadContextKt.threadContextElements(getContext());
    @NotNull
    private volatile /* synthetic */ Object _reusableCancellableContinuation = null;

    public static /* synthetic */ void get_state$kotlinx_coroutines_core$annotations() {
    }

    @Override // kotlin.coroutines.Continuation
    @NotNull
    public CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DispatchedContinuation(@NotNull CoroutineDispatcher coroutineDispatcher, @NotNull Continuation<? super T> continuation) {
        super(-1);
        Symbol symbol;
        this.dispatcher = coroutineDispatcher;
        this.continuation = continuation;
        symbol = DispatchedContinuationKt.a;
        this._state = symbol;
    }

    @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
    @Nullable
    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.continuation;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    @Nullable
    public final CancellableContinuationImpl<?> getReusableCancellableContinuation() {
        Object obj = this._reusableCancellableContinuation;
        if (obj instanceof CancellableContinuationImpl) {
            return (CancellableContinuationImpl) obj;
        }
        return null;
    }

    public final boolean isReusable(@NotNull CancellableContinuationImpl<?> cancellableContinuationImpl) {
        Object obj = this._reusableCancellableContinuation;
        if (obj == null) {
            return false;
        }
        return !(obj instanceof CancellableContinuationImpl) || obj == cancellableContinuationImpl;
    }

    public final void release() {
        awaitReusability();
        CancellableContinuationImpl<?> reusableCancellableContinuation = getReusableCancellableContinuation();
        if (reusableCancellableContinuation != null) {
            reusableCancellableContinuation.detachChild$kotlinx_coroutines_core();
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @Nullable
    public Object takeState$kotlinx_coroutines_core() {
        Symbol symbol;
        Symbol symbol2;
        Object obj = this._state;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            symbol2 = DispatchedContinuationKt.a;
            if (!(obj != symbol2)) {
                throw new AssertionError();
            }
        }
        symbol = DispatchedContinuationKt.a;
        this._state = symbol;
        return obj;
    }

    @Override // kotlinx.coroutines.DispatchedTask
    @NotNull
    public Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(@NotNull Object obj) {
        CoroutineContext context = this.continuation.getContext();
        Object state$default = CompletionStateKt.toState$default(obj, null, 1, null);
        if (this.dispatcher.isDispatchNeeded(context)) {
            this._state = state$default;
            this.resumeMode = 0;
            this.dispatcher.dispatch(context, this);
            return;
        }
        DebugKt.getASSERTIONS_ENABLED();
        EventLoop eventLoop$kotlinx_coroutines_core = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$kotlinx_coroutines_core.isUnconfinedLoopActive()) {
            this._state = state$default;
            this.resumeMode = 0;
            eventLoop$kotlinx_coroutines_core.dispatchUnconfined(this);
            return;
        }
        eventLoop$kotlinx_coroutines_core.incrementUseCount(true);
        try {
            CoroutineContext context2 = getContext();
            Object updateThreadContext = ThreadContextKt.updateThreadContext(context2, this.countOrElement);
            try {
                this.continuation.resumeWith(obj);
                Unit unit = Unit.INSTANCE;
                do {
                } while (eventLoop$kotlinx_coroutines_core.processUnconfinedEvent());
            } finally {
                ThreadContextKt.restoreThreadContext(context2, updateThreadContext);
            }
        } finally {
            eventLoop$kotlinx_coroutines_core.decrementUseCount(true);
        }
    }

    /* JADX WARN: Finally extract failed */
    public final void resumeCancellableWith(@NotNull Object obj, @Nullable Function1<? super Throwable, Unit> function1) {
        boolean z;
        UndispatchedCoroutine<?> undispatchedCoroutine;
        Object state = CompletionStateKt.toState(obj, function1);
        if (this.dispatcher.isDispatchNeeded(getContext())) {
            this._state = state;
            this.resumeMode = 1;
            this.dispatcher.dispatch(getContext(), this);
            return;
        }
        DebugKt.getASSERTIONS_ENABLED();
        EventLoop eventLoop$kotlinx_coroutines_core = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$kotlinx_coroutines_core.isUnconfinedLoopActive()) {
            this._state = state;
            this.resumeMode = 1;
            eventLoop$kotlinx_coroutines_core.dispatchUnconfined(this);
            return;
        }
        eventLoop$kotlinx_coroutines_core.incrementUseCount(true);
        try {
            Job job = (Job) getContext().get(Job.Key);
            if (job == null || job.isActive()) {
                z = false;
            } else {
                CancellationException cancellationException = job.getCancellationException();
                cancelCompletedResult$kotlinx_coroutines_core(state, cancellationException);
                Result.Companion companion = Result.Companion;
                resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(cancellationException)));
                z = true;
            }
            if (!z) {
                Continuation<T> continuation = this.continuation;
                Object obj2 = this.countOrElement;
                CoroutineContext context = continuation.getContext();
                Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
                if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
                    undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
                } else {
                    undispatchedCoroutine = null;
                }
                try {
                    this.continuation.resumeWith(obj);
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                        ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                    }
                    InlineMarker.finallyEnd(1);
                } catch (Throwable th) {
                    InlineMarker.finallyStart(1);
                    if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                        ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                    }
                    InlineMarker.finallyEnd(1);
                    throw th;
                }
            }
            do {
            } while (eventLoop$kotlinx_coroutines_core.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
            eventLoop$kotlinx_coroutines_core.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th2) {
            InlineMarker.finallyStart(1);
            eventLoop$kotlinx_coroutines_core.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th2;
        }
    }

    @Override // kotlinx.coroutines.DispatchedTask
    public void cancelCompletedResult$kotlinx_coroutines_core(@Nullable Object obj, @NotNull Throwable th) {
        if (obj instanceof CompletedWithCancellation) {
            ((CompletedWithCancellation) obj).onCancellation.invoke(th);
        }
    }

    public final boolean resumeCancelled(@Nullable Object obj) {
        Job job = (Job) getContext().get(Job.Key);
        if (job == null || job.isActive()) {
            return false;
        }
        CancellationException cancellationException = job.getCancellationException();
        cancelCompletedResult$kotlinx_coroutines_core(obj, cancellationException);
        Result.Companion companion = Result.Companion;
        resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(cancellationException)));
        return true;
    }

    public final void resumeUndispatchedWith(@NotNull Object obj) {
        UndispatchedCoroutine<?> undispatchedCoroutine;
        Continuation<T> continuation = this.continuation;
        Object obj2 = this.countOrElement;
        CoroutineContext context = continuation.getContext();
        Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj2);
        if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
        } else {
            undispatchedCoroutine = null;
        }
        try {
            this.continuation.resumeWith(obj);
            Unit unit = Unit.INSTANCE;
        } finally {
            InlineMarker.finallyStart(1);
            if (undispatchedCoroutine == null || undispatchedCoroutine.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, updateThreadContext);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public final void dispatchYield$kotlinx_coroutines_core(@NotNull CoroutineContext coroutineContext, T t) {
        this._state = t;
        this.resumeMode = 1;
        this.dispatcher.dispatchYield(coroutineContext, this);
    }

    @NotNull
    public String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + ']';
    }

    public final void awaitReusability() {
        do {
        } while (this._reusableCancellableContinuation == DispatchedContinuationKt.REUSABLE_CLAIMED);
    }

    @Nullable
    public final CancellableContinuationImpl<T> claimReusableCancellableContinuation() {
        while (true) {
            Object obj = this._reusableCancellableContinuation;
            if (obj == null) {
                this._reusableCancellableContinuation = DispatchedContinuationKt.REUSABLE_CLAIMED;
                return null;
            } else if (obj instanceof CancellableContinuationImpl) {
                if (a.compareAndSet(this, obj, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    return (CancellableContinuationImpl) obj;
                }
            } else if (obj != DispatchedContinuationKt.REUSABLE_CLAIMED && !(obj instanceof Throwable)) {
                throw new IllegalStateException(Intrinsics.stringPlus("Inconsistent state ", obj).toString());
            }
        }
    }

    @Nullable
    public final Throwable tryReleaseClaimedContinuation(@NotNull CancellableContinuation<?> cancellableContinuation) {
        do {
            Object obj = this._reusableCancellableContinuation;
            if (obj != DispatchedContinuationKt.REUSABLE_CLAIMED) {
                if (!(obj instanceof Throwable)) {
                    throw new IllegalStateException(Intrinsics.stringPlus("Inconsistent state ", obj).toString());
                } else if (a.compareAndSet(this, obj, null)) {
                    return (Throwable) obj;
                } else {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
        } while (!a.compareAndSet(this, DispatchedContinuationKt.REUSABLE_CLAIMED, cancellableContinuation));
        return null;
    }

    public final boolean postponeCancellation(@NotNull Throwable th) {
        while (true) {
            Object obj = this._reusableCancellableContinuation;
            if (Intrinsics.areEqual(obj, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                if (a.compareAndSet(this, DispatchedContinuationKt.REUSABLE_CLAIMED, th)) {
                    return true;
                }
            } else if (obj instanceof Throwable) {
                return true;
            } else {
                if (a.compareAndSet(this, obj, null)) {
                    return false;
                }
            }
        }
    }
}
