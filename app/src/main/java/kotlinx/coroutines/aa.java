package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

/* compiled from: Executors.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0007H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/ResumeUndispatchedRunnable;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlinx/coroutines/CancellableContinuation;)V", "run", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
final class aa implements Runnable {
    @NotNull
    private final CoroutineDispatcher a;
    @NotNull
    private final CancellableContinuation<Unit> b;

    /* JADX WARN: Multi-variable type inference failed */
    public aa(@NotNull CoroutineDispatcher coroutineDispatcher, @NotNull CancellableContinuation<? super Unit> cancellableContinuation) {
        this.a = coroutineDispatcher;
        this.b = cancellableContinuation;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.resumeUndispatched(this.a, Unit.INSTANCE);
    }
}
