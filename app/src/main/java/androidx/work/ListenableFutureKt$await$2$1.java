package androidx.work;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlinx.coroutines.CancellableContinuation;

/* compiled from: ListenableFuture.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H\n"}, d2 = {"<anonymous>", "", "R"}, k = 3, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ListenableFutureKt$await$2$1 implements Runnable {
    final /* synthetic */ CancellableContinuation<R> a;
    final /* synthetic */ ListenableFuture<R> b;

    /* JADX WARN: Multi-variable type inference failed */
    public ListenableFutureKt$await$2$1(CancellableContinuation<? super R> cancellableContinuation, ListenableFuture<R> listenableFuture) {
        this.a = cancellableContinuation;
        this.b = listenableFuture;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Object obj = this.b.get();
            Result.Companion companion = Result.Companion;
            this.a.resumeWith(Result.m1220constructorimpl(obj));
        } catch (Throwable th) {
            Throwable cause = th.getCause();
            if (cause == null) {
                cause = th;
            }
            if (th instanceof CancellationException) {
                this.a.cancel(cause);
                return;
            }
            Result.Companion companion2 = Result.Companion;
            this.a.resumeWith(Result.m1220constructorimpl(ResultKt.createFailure(cause)));
        }
    }
}
