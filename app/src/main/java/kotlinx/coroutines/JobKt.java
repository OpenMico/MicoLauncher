package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"kotlinx/coroutines/JobKt__FutureKt", "kotlinx/coroutines/JobKt__JobKt"}, k = 4, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class JobKt {
    @InternalCoroutinesApi
    @NotNull
    public static final DisposableHandle DisposableHandle(@NotNull Function0<Unit> function0) {
        return u.a(function0);
    }

    @NotNull
    public static final CompletableJob Job(@Nullable Job job) {
        return u.a(job);
    }

    public static final void cancel(@NotNull CoroutineContext coroutineContext, @Nullable CancellationException cancellationException) {
        u.a(coroutineContext, cancellationException);
    }

    public static final void cancel(@NotNull Job job, @NotNull String str, @Nullable Throwable th) {
        u.a(job, str, th);
    }

    @Nullable
    public static final Object cancelAndJoin(@NotNull Job job, @NotNull Continuation<? super Unit> continuation) {
        return u.a(job, continuation);
    }

    public static final void cancelChildren(@NotNull CoroutineContext coroutineContext, @Nullable CancellationException cancellationException) {
        u.b(coroutineContext, cancellationException);
    }

    public static final void cancelChildren(@NotNull Job job, @Nullable CancellationException cancellationException) {
        u.a(job, cancellationException);
    }

    public static final void cancelFutureOnCancellation(@NotNull CancellableContinuation<?> cancellableContinuation, @NotNull Future<?> future) {
        t.a(cancellableContinuation, future);
    }

    @InternalCoroutinesApi
    @NotNull
    public static final DisposableHandle cancelFutureOnCompletion(@NotNull Job job, @NotNull Future<?> future) {
        return t.a(job, future);
    }

    @NotNull
    public static final DisposableHandle disposeOnCompletion(@NotNull Job job, @NotNull DisposableHandle disposableHandle) {
        return u.a(job, disposableHandle);
    }

    public static final void ensureActive(@NotNull CoroutineContext coroutineContext) {
        u.c(coroutineContext);
    }

    public static final void ensureActive(@NotNull Job job) {
        u.d(job);
    }

    @NotNull
    public static final Job getJob(@NotNull CoroutineContext coroutineContext) {
        return u.e(coroutineContext);
    }

    public static final boolean isActive(@NotNull CoroutineContext coroutineContext) {
        return u.a(coroutineContext);
    }
}
